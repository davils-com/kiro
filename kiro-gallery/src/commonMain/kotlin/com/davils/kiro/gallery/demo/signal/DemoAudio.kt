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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import com.davils.kiro.ui.audio.signal.AudioLevels
import com.davils.kiro.ui.audio.signal.AudioScope
import com.davils.kiro.ui.audio.signal.AudioSpectrum
import com.davils.kiro.ui.audio.signal.ChannelLevel
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.audio.signal.Samples
import com.davils.kiro.ui.audio.signal.WaveformPeaks
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.sqrt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val BLOCK: Int = 2_048
private const val SCOPE: Int = 1_024
private const val TRACE: Int = 640
private const val NANOS_PER_SECOND: Float = 1e9f
private const val LONGEST_FRAME: Float = 0.1f
private const val SILENCE: Float = -120f
private const val MILLIS_PER_SECOND: Long = 1_000L

@Stable
internal class DemoAudio {
    private var recording: DemoRecording? = null
    private val fourier = Fourier(size = BLOCK)
    private val ballistics = listOf(LevelBallistics(), LevelBallistics())
    private val bands = mutableMapOf<Int, SpectrumBands>()
    private val mono = FloatArray(BLOCK)
    private var cursor: Int by mutableIntStateOf(0)
    private var leader: Any? = null

    var levels: AudioLevels by mutableStateOf(AudioLevels(channels = listOf(silent, silent)))
        private set

    var scope: AudioScope by mutableStateOf(AudioScope(left = Samples.Empty, right = Samples.Empty))
        private set

    var peaks: WaveformPeaks by mutableStateOf(WaveformPeaks(channels = emptyList(), duration = Duration.ZERO))
        private set

    var stems: List<DemoStem> by mutableStateOf(emptyList())
        private set

    val position: Duration
        get() = (cursor.toDouble() / SAMPLE_RATE).seconds

    val progress: Float
        get() = cursor.toFloat() / (recording?.run { left.size } ?: 1).coerceAtLeast(minimumValue = 1)

    fun spectrum(bands: Int): AudioSpectrum {
        val analysis = this.bands.getOrPut(
            bands,
        ) { SpectrumBands(bands = bands, binWidth = SAMPLE_RATE.toFloat() / BLOCK) }
        return analysis.spectrum
    }

    fun seek(to: Duration) {
        val total = recording?.run { left.size } ?: return
        val target = (to.inWholeMilliseconds * SAMPLE_RATE / MILLIS_PER_SECOND).toInt()
        cursor = target.coerceIn(minimumValue = 0, maximumValue = total - 1)
    }

    fun resetClip() {
        ballistics.forEach(LevelBallistics::resetClip)
    }

    suspend fun play(dispatcher: CoroutineDispatcher = Dispatchers.Default) {
        val loaded = withContext(dispatcher) { demoRecording }
        recording = loaded
        peaks = loaded.mix
        stems = loaded.stems
        val player = Any()
        var last = 0L
        try {
            while (true) {
                withFrameNanos { now ->
                    leader = leader ?: player
                    val seconds = ((now - last) / NANOS_PER_SECOND).takeIf { last > 0L }?.coerceAtMost(
                        LONGEST_FRAME,
                    ) ?: 0f
                    last = now
                    if (leader === player) advance(recording = loaded, seconds = seconds)
                }
            }
        } finally {
            leader = leader.takeUnless { it === player }
        }
    }

    private fun advance(recording: DemoRecording, seconds: Float) {
        val total = recording.left.size
        val step = (seconds * SAMPLE_RATE).toInt()
        cursor = (cursor + step) % total
        val left = window(source = recording.left, end = cursor, length = BLOCK)
        val right = window(source = recording.right, end = cursor, length = BLOCK)
        val latest = step.coerceIn(minimumValue = 1, maximumValue = BLOCK)
        levels = AudioLevels(
            channels = listOf(
                ballistics[0].update(
                    latest = left.copyOfRange(fromIndex = BLOCK - latest, toIndex = BLOCK),
                    block = left,
                    seconds = seconds,
                ),
                ballistics[1].update(
                    latest = right.copyOfRange(fromIndex = BLOCK - latest, toIndex = BLOCK),
                    block = right,
                    seconds = seconds,
                ),
            ),
        )
        for (index in 0 until BLOCK) {
            mono[index] = (left[index] + right[index]) / 2f
        }
        fourier.analyze(mono)
        bands.values.forEach { band -> band.update(decibels = fourier.decibels, seconds = seconds) }
        scope = scopeOf(left = left, right = right)
    }

    private fun scopeOf(left: FloatArray, right: FloatArray): AudioScope {
        var trigger = BLOCK - SCOPE - TRACE
        while (trigger < BLOCK - TRACE - 1 && !(mono[trigger] <= 0f && mono[trigger + 1] > 0f)) {
            trigger += 1
        }
        var products = 0f
        var leftSquares = 0f
        var rightSquares = 0f
        for (index in BLOCK - SCOPE until BLOCK) {
            products += left[index] * right[index]
            leftSquares += left[index] * left[index]
            rightSquares += right[index] * right[index]
        }
        val energy = sqrt(leftSquares * rightSquares)
        val correlation = (products / energy).takeIf { energy > 0f }?.coerceIn(
            minimumValue = -1f,
            maximumValue = 1f,
        ) ?: 0f
        return AudioScope(
            left = Samples(left.copyOfRange(fromIndex = trigger, toIndex = trigger + TRACE)),
            right = Samples(right.copyOfRange(fromIndex = trigger, toIndex = trigger + TRACE)),
            correlation = correlation,
        )
    }

    private fun window(source: FloatArray, end: Int, length: Int): FloatArray = FloatArray(length) { index ->
        source[(end - length + index).mod(source.size)]
    }

    private companion object {
        val silent: ChannelLevel = ChannelLevel(peak = Decibels(SILENCE), rms = Decibels(SILENCE))
    }
}

private val shared: DemoAudio by lazy { DemoAudio() }

@Composable
internal fun rememberDemoAudio(): DemoAudio {
    LaunchedEffect(shared) { shared.play() }
    return shared
}
