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

package com.davils.kiro.ui.audio

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.ambient.RadialSpectrumStyles
import com.davils.kiro.ui.audio.ambient.SpectrumBarsStyles
import com.davils.kiro.ui.audio.ambient.SpectrumWaveStyles
import com.davils.kiro.ui.audio.fixture.ValueSample
import com.davils.kiro.ui.audio.meter.CorrelationMeterStyles
import com.davils.kiro.ui.audio.meter.LevelMeterStyles
import com.davils.kiro.ui.audio.scope.GoniometerStyles
import com.davils.kiro.ui.audio.scope.OscilloscopeStyles
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.audio.spectrum.SpectrogramStyles
import com.davils.kiro.ui.audio.spectrum.SpectrumAnalyzerStyles
import com.davils.kiro.ui.audio.timeline.AudioClipStyles
import com.davils.kiro.ui.audio.timeline.ClipTimelineStyles
import com.davils.kiro.ui.audio.timeline.ClipTrackStyles
import com.davils.kiro.ui.audio.timeline.TimeRulerStyles
import com.davils.kiro.ui.audio.waveform.WaveformStyles
import com.davils.kiro.ui.core.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import kotlin.time.Duration.Companion.seconds

private const val STEREO: Int = 2
private const val HALF: Float = 0.5f
private const val KILO: Float = 1_000f

private val theme: Theme = Theme.dark()

private fun meterSamples(): List<ValueSample> {
    val level = LevelMeterStyles.Default.resolve(theme)
    val correlation = CorrelationMeterStyles.Default.resolve(theme)
    val goniometer = GoniometerStyles.Default.resolve(theme)
    val oscilloscope = OscilloscopeStyles.Default.resolve(theme)
    return listOf(
        ValueSample(name = "LevelMeterStyle", value = level, copy = level.copy(), changed = level.copy(length = 1.dp)),
        ValueSample(
            name = "CorrelationMeterStyle",
            value = correlation,
            copy = correlation.copy(),
            changed = correlation.copy(center = "o"),
        ),
        ValueSample(
            name = "GoniometerStyle",
            value = goniometer,
            copy = goniometer.copy(),
            changed = goniometer.copy(gain = 1f),
        ),
        ValueSample(
            name = "OscilloscopeStyle",
            value = oscilloscope,
            copy = oscilloscope.copy(),
            changed = oscilloscope.copy(rows = 1),
        ),
    )
}

private fun spectrumSamples(): List<ValueSample> {
    val analyzer = SpectrumAnalyzerStyles.Default.resolve(theme)
    val spectrogram = SpectrogramStyles.Default.resolve(theme)
    val bars = SpectrumBarsStyles.Default.resolve(theme)
    val radial = RadialSpectrumStyles.Default.resolve(theme)
    val wave = SpectrumWaveStyles.Default.resolve(theme)
    return listOf(
        ValueSample(
            name = "SpectrumAnalyzerStyle",
            value = analyzer,
            copy = analyzer.copy(),
            changed = analyzer.copy(height = 1.dp),
        ),
        ValueSample(
            name = "SpectrogramStyle",
            value = spectrogram,
            copy = spectrogram.copy(),
            changed = spectrogram.copy(radius = 1.dp),
        ),
        ValueSample(name = "SpectrumBarsStyle", value = bars, copy = bars.copy(), changed = bars.copy(gap = 0f)),
        ValueSample(
            name = "RadialSpectrumStyle",
            value = radial,
            copy = radial.copy(),
            changed = radial.copy(pulse = 0f),
        ),
        ValueSample(name = "SpectrumWaveStyle", value = wave, copy = wave.copy(), changed = wave.copy(falloff = 0f)),
    )
}

private fun timelineSamples(): List<ValueSample> {
    val waveform = WaveformStyles.Default.resolve(theme)
    val clip = AudioClipStyles.Default.resolve(theme)
    val ruler = TimeRulerStyles.Default.resolve(theme)
    val track = ClipTrackStyles.Default.resolve(theme)
    val timeline = ClipTimelineStyles.Default.resolve(theme)
    return listOf(
        ValueSample(
            name = "WaveformStyle",
            value = waveform,
            copy = waveform.copy(),
            changed = waveform.copy(seekStep = 1.seconds),
        ),
        ValueSample(name = "AudioClipStyle", value = clip, copy = clip.copy(), changed = clip.copy(radius = 0.dp)),
        ValueSample(name = "TimeRulerStyle", value = ruler, copy = ruler.copy(), changed = ruler.copy(minorCount = 0)),
        ValueSample(name = "ClipTrackStyle", value = track, copy = track.copy(), changed = track.copy(mute = "X")),
        ValueSample(
            name = "ClipTimelineStyle",
            value = timeline,
            copy = timeline.copy(),
            changed = timeline.copy(zoomStep = 2f),
        ),
    )
}

class VisualizerStylesTest : FunSpec({
    context("every style of a visualizer") {
        (meterSamples() + spectrumSamples() + timelineSamples()).forEach { sample ->
            test("${sample.name} equals its copy and differs from a copy with a changed value") {
                sample.copy shouldBe sample.value
                sample.copy.hashCode() shouldBe sample.value.hashCode()
                sample.changed shouldNotBe sample.value
                sample.value shouldNotBe Any()
                sample.value.toString() shouldStartWith sample.name
            }
        }
    }

    test("names stereo channels by side and other channels by number") {
        val style = LevelMeterStyles.Default.resolve(theme)
        style.channel(0, STEREO) shouldBe "L"
        style.channel(1, STEREO) shouldBe "R"
        style.channel(STEREO, STEREO + 1) shouldBe "3"
        style.scaleLabel(Decibels(value = -12f)) shouldBe "-12"
        style.readout(Decibels(value = -3.26f)) shouldBe "-3.3"
    }

    test("writes the default texts of the analyzers and the timeline") {
        CorrelationMeterStyles.Default.resolve(theme).readout(HALF) shouldBe "+0.5"
        val analyzer = SpectrumAnalyzerStyles.Default.resolve(theme)
        analyzer.decibels(Decibels(value = -24f)) shouldBe "-24"
        analyzer.readout(KILO, Decibels(value = -6f)) shouldBe "1kHz  -6.0 dB"
        AudioClipStyles.Default.resolve(theme).gainLabel(Decibels(value = -3f)) shouldBe "-3.0 dB"
        TimeRulerStyles.Default.resolve(theme).time(2.seconds) shouldBe "00:00:02:00"
    }

    test("refuses styles without colors") {
        val spectrogram = SpectrogramStyles.Default.resolve(theme)
        shouldThrow<IllegalArgumentException> { spectrogram.copy(ramp = emptyList()) }
        shouldThrow<IllegalArgumentException> { SpectrumBarsStyles.Default.resolve(theme).copy(colors = emptyList()) }
        shouldThrow<IllegalArgumentException> { RadialSpectrumStyles.Default.resolve(theme).copy(colors = emptyList()) }
        shouldThrow<IllegalArgumentException> { SpectrumWaveStyles.Default.resolve(theme).copy(colors = emptyList()) }
        val clip = AudioClipStyles.Default.resolve(theme)
        shouldThrow<IllegalArgumentException> { clip.copy(tints = emptyList<Color>()) }
    }
})
