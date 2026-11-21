/*
 * Copyright 2026 Davils
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.davils.kiro.gallery.demo.signal

import com.davils.kiro.ui.audio.signal.WaveformPeaks
import kotlin.math.floor
import kotlin.math.tanh
import kotlin.time.Duration.Companion.seconds

internal const val SAMPLE_RATE: Int = 32_000

private const val BEAT: Float = 0.5f
private const val EIGHTH: Float = 0.25f
private const val BEATS_PER_BAR: Int = 4
private const val BARS: Int = 8
private const val BUCKETS: Int = 1_600
private const val HEADROOM: Float = 0.97f
private const val DRIVE: Float = 2.2f
private const val DRUMS_FROM: Int = 2
private const val LEAD_FROM: Int = 4
private const val BASS_LENGTH: Float = 0.22f
private const val OCTAVE: Int = 12
private const val HAT_LEFT: Float = 0.75f
private const val LEAD_RIGHT: Float = 0.55f
private const val REST: Int = -1

private val roots: List<Int> = listOf(45, 41, 48, 43, 45, 41, 48, 40)
private val chords: List<List<Int>> = listOf(
    listOf(57, 60, 64),
    listOf(53, 57, 60),
    listOf(55, 60, 64),
    listOf(55, 59, 62),
    listOf(57, 60, 64),
    listOf(53, 57, 60),
    listOf(55, 60, 64),
    listOf(56, 59, 64),
)
private val scale: List<Int> = listOf(69, 72, 74, 76, 79, 81)
private val melody: List<Int> = listOf(0, REST, 2, 3, REST, 4, 3, 1)
private val bassline: List<Int> = listOf(0, 0, OCTAVE, 0, 0, OCTAVE, 0, OCTAVE)

internal val demoRecording: DemoRecording by lazy { DemoSong().render() }

internal class DemoSong {
    private val bar: Float = BEAT * BEATS_PER_BAR
    private val total: Int = (bar * BARS * SAMPLE_RATE).toInt()
    private val left = FloatArray(total)
    private val right = FloatArray(total)
    private val drums = OutlineBuilder(buckets = BUCKETS, total = total)
    private val bass = OutlineBuilder(buckets = BUCKETS, total = total)
    private val keys = OutlineBuilder(buckets = BUCKETS, total = total)

    fun render(): DemoRecording {
        for (index in 0 until total) {
            renderSample(index)
            left[index] = tanh(left[index] * DRIVE)
            right[index] = tanh(right[index] * DRIVE)
        }
        val loudest = OutlineBuilder.loudestOf(left = left, right = right)
        val scale = HEADROOM / loudest.coerceAtLeast(minimumValue = HEADROOM)
        val mixLeft = OutlineBuilder(buckets = BUCKETS, total = total)
        val mixRight = OutlineBuilder(buckets = BUCKETS, total = total)
        for (index in 0 until total) {
            left[index] *= scale
            right[index] *= scale
            mixLeft.add(index = index, value = left[index])
            mixRight.add(index = index, value = right[index])
        }
        val duration = (bar * BARS).toDouble().seconds
        val stems = listOf(
            DemoStem(
                name = "Drums",
                peaks = WaveformPeaks(channels = listOf(drums.build(scale * DRIVE)), duration = duration),
            ),
            DemoStem(
                name = "Bass",
                peaks = WaveformPeaks(channels = listOf(bass.build(scale * DRIVE)), duration = duration),
            ),
            DemoStem(
                name = "Keys",
                peaks = WaveformPeaks(channels = listOf(keys.build(scale * DRIVE)), duration = duration),
            ),
        )
        val mix = WaveformPeaks(
            channels = listOf(mixLeft.build(scale = 1f), mixRight.build(scale = 1f)),
            duration = duration,
        )
        return DemoRecording(left = left, right = right, sampleRate = SAMPLE_RATE, mix = mix, stems = stems)
    }

    private fun renderSample(index: Int) {
        val time = index.toFloat() / SAMPLE_RATE
        val bars = floor(time / bar).toInt()
        val inBar = time - bars * bar
        val beat = floor(inBar / BEAT).toInt()
        val eighth = floor(inBar / EIGHTH).toInt()
        val sinceBeat = inBar - beat * BEAT
        val sinceEighth = inBar - eighth * EIGHTH
        val sample = index.toLong()
        val hasDrums = bars >= DRUMS_FROM
        val kick = kick(sinceBeat).takeIf { hasDrums } ?: 0f
        val sinceSnare = sinceBeat + BEAT * ((beat + 1) % 2)
        val snare = snare(time = sinceSnare, index = sample).takeIf { hasDrums } ?: 0f
        val hat = hat(time = sinceEighth, index = sample, isOpen = eighth % 2 == 1)
        val low = bass(
            time = sinceEighth,
            frequency = frequencyOf(roots[bars] + bassline[eighth]),
            length = BASS_LENGTH,
        )
        val chord = chords[bars]
        val padLeft = pad(time = inBar, notes = chord, side = -1f, length = bar)
        val padRight = pad(time = inBar, notes = chord, side = 1f, length = bar)
        val step = melody[(eighth + bars * 3) % melody.size]
        val tune = scale.getOrNull(step)?.takeIf { bars >= LEAD_FROM }
        val melodic = tune?.let { note -> lead(time = sinceEighth, frequency = frequencyOf(note)) } ?: 0f
        val center = kick + snare + low
        left[index] = center + hat * HAT_LEFT + padLeft + melodic
        right[index] = center + hat + padRight + melodic * LEAD_RIGHT
        drums.add(index = index, value = kick + snare + hat)
        bass.add(index = index, value = low)
        keys.add(index = index, value = (padLeft + padRight) / 2f + melodic)
    }
}
