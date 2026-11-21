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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.davils.kiro.ui.audio.signal.AudioSpectrum
import com.davils.kiro.ui.audio.signal.FrequencyRange
import com.davils.kiro.ui.audio.signal.Samples
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log2
import kotlin.math.max
import kotlin.math.pow

private const val LOWEST: Float = 30f
private const val HIGHEST: Float = 15_000f
private const val TILT_PER_OCTAVE: Float = 3f
private const val PIVOT: Float = 1_000f
private const val SILENCE: Float = -120f
private const val RISE: Float = 0.65f
private const val FALL_PER_SECOND: Float = 42f
private const val HOLD_SECONDS: Float = 0.7f
private const val HOLD_FALL_PER_SECOND: Float = 24f
private const val HALF: Float = 0.5f

internal class SpectrumBands(private val bands: Int, binWidth: Float) {
    private val firstBins = IntArray(bands)
    private val lastBins = IntArray(bands)
    private val tilts = FloatArray(bands)
    private val shown = FloatArray(bands) { SILENCE }
    private val holds = FloatArray(bands) { SILENCE }
    private val holding = FloatArray(bands)
    private val range = FrequencyRange(lowest = edgeOf(HALF), highest = edgeOf(bands - HALF))

    var spectrum: AudioSpectrum by mutableStateOf(
        AudioSpectrum(magnitudes = Samples(shown), range = range, holds = Samples(holds)),
    )
        private set

    init {
        for (band in 0 until bands) {
            val low = edgeOf(band.toFloat()) / binWidth
            val high = edgeOf(band + 1f) / binWidth
            val first = floor(low).toInt()
            firstBins[band] = first.coerceAtLeast(minimumValue = 1)
            lastBins[band] = max(a = firstBins[band], b = ceil(high).toInt())
            tilts[band] = TILT_PER_OCTAVE * log2(edgeOf(band + HALF) / PIVOT)
        }
    }

    fun update(decibels: FloatArray, seconds: Float) {
        for (band in 0 until bands) {
            var loudest = SILENCE
            for (bin in firstBins[band]..lastBins[band].coerceAtMost(maximumValue = decibels.size - 1)) {
                loudest = max(a = loudest, b = decibels[bin])
            }
            follow(band = band, level = loudest + tilts[band], seconds = seconds)
        }
        spectrum = AudioSpectrum(magnitudes = Samples(shown), range = range, holds = Samples(holds))
    }

    private fun follow(band: Int, level: Float, seconds: Float) {
        val previous = shown[band]
        val rising = previous + (level - previous) * RISE
        shown[band] = rising.takeIf { level > previous } ?: max(a = level, b = previous - FALL_PER_SECOND * seconds)
        holding[band] -= seconds
        if (shown[band] >= holds[band]) {
            holds[band] = shown[band]
            holding[band] = HOLD_SECONDS
        }
        if (holding[band] < 0f) holds[band] = max(a = shown[band], b = holds[band] - HOLD_FALL_PER_SECOND * seconds)
    }

    private fun edgeOf(position: Float): Float = LOWEST * (HIGHEST / LOWEST).pow(position / bands)
}
