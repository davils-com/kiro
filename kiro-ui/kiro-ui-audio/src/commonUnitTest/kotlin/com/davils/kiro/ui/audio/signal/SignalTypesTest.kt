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

package com.davils.kiro.ui.audio.signal

import com.davils.kiro.ui.audio.fixture.ValueSample
import com.davils.kiro.ui.audio.fixture.channelOf
import com.davils.kiro.ui.audio.fixture.peaks
import com.davils.kiro.ui.audio.fixture.range
import com.davils.kiro.ui.audio.fixture.scopeOf
import com.davils.kiro.ui.audio.fixture.spectrumOf
import com.davils.kiro.ui.audio.fixture.stereo
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldStartWith
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val CHANGED: Float = 9f
private const val HALF: Float = 0.5f
private const val OCTAVE: Float = 200f
private const val MIDDLE: Float = 250f

private const val TOLERANCE: Float = 0.001f

private fun samples(): List<ValueSample> {
    val level = stereo.channels.first()
    val decibels = DecibelRange(floor = Decibels(value = -60f), ceiling = Decibels(value = 0f))
    val spectrum = spectrumOf()
    val scope = scopeOf()
    val channel = channelOf()
    return listOf(
        ValueSample(
            name = "ChannelLevel",
            value = level,
            copy = level.copy(),
            changed = level.copy(peak = Decibels(value = 0f)),
        ),
        ValueSample(
            name = "AudioLevels",
            value = stereo,
            copy = stereo.copy(),
            changed = stereo.copy(channels = emptyList()),
        ),
        ValueSample(
            name = "DecibelRange",
            value = decibels,
            copy = decibels.copy(),
            changed = decibels.copy(floor = Decibels(value = -90f)),
        ),
        ValueSample(
            name = "FrequencyRange",
            value = range,
            copy = range.copy(),
            changed = range.copy(spacing = FrequencySpacing.Linear),
        ),
        ValueSample(
            name = "AudioSpectrum",
            value = spectrum,
            copy = spectrum.copy(),
            changed = spectrum.copy(holds = null),
        ),
        ValueSample(name = "AudioScope", value = scope, copy = scope.copy(), changed = scope.copy(correlation = -1f)),
        ValueSample(
            name = "WaveformChannel",
            value = channel,
            copy = channel.copy(),
            changed = channel.copy(rms = channel.maximums),
        ),
        ValueSample(
            name = "WaveformPeaks",
            value = peaks,
            copy = peaks.copy(),
            changed = peaks.copy(duration = 1.seconds),
        ),
        ValueSample(
            name = "Samples",
            value = Samples.of(1f, 2f),
            copy = Samples.of(1f, 2f),
            changed = Samples.of(2f, 1f),
        ),
    )
}

class SignalTypesTest : FunSpec({
    context("every value type of a signal") {
        samples().forEach { sample ->
            test("${sample.name} equals its copy and differs from a copy with a changed value") {
                sample.copy shouldBe sample.value
                sample.copy.hashCode() shouldBe sample.value.hashCode()
                sample.changed shouldNotBe sample.value
                sample.value shouldNotBe Any()
                sample.value.toString() shouldStartWith sample.name
            }
        }
    }

    test("keeps its own copy of the values") {
        val values = floatArrayOf(1f, 2f, 3f)
        val samples = Samples(values)
        values[0] = CHANGED
        samples[0] shouldBe 1f
        samples.size shouldBe values.size
        samples.isEmpty() shouldBe false
        Samples.Empty.isEmpty() shouldBe true
        samples.toFloatArray().also { copy -> copy[1] = CHANGED }
        samples[1] shouldBe 2f
    }

    test("names a missing hold") {
        val last = stereo.channels.last()
        last.copy(hold = null).toString() shouldContain "hold=none"
        spectrumOf(withHolds = false).toString() shouldContain "holds=none"
    }

    test("places levels within a decibel range") {
        val decibels = DecibelRange(floor = Decibels(value = -60f), ceiling = Decibels(value = 0f))
        decibels.fractionOf(Decibels(value = -30f)) shouldBe HALF
        decibels.fractionOf(Decibels(value = 6f)) shouldBe 1f
        decibels.fractionOf(Decibels(Float.NEGATIVE_INFINITY)) shouldBe 0f
        decibels.fractionOf(Decibels(Float.NaN)) shouldBe 0f
    }

    test("places frequencies on a logarithmic and a linear axis") {
        val octaves = FrequencyRange(lowest = 100f, highest = 400f)
        octaves.fractionOf(frequency = 200f) shouldBe (HALF plusOrMinus TOLERANCE)
        octaves.frequencyAt(fraction = HALF) shouldBe (OCTAVE plusOrMinus TOLERANCE)
        val linear = octaves.copy(spacing = FrequencySpacing.Linear)
        linear.fractionOf(frequency = 250f) shouldBe (HALF plusOrMinus TOLERANCE)
        linear.frequencyAt(fraction = HALF) shouldBe (MIDDLE plusOrMinus TOLERANCE)
    }

    test("counts the buckets of an outline") {
        peaks.buckets shouldBe channelOf().size
        WaveformPeaks(channels = emptyList(), duration = Duration.ZERO).buckets shouldBe 0
    }

    test("refuses values that cannot be shown") {
        shouldThrow<IllegalArgumentException> {
            DecibelRange(
                floor = Decibels(value = 0f),
                ceiling = Decibels(value = 0f),
            )
        }
        shouldThrow<IllegalArgumentException> { FrequencyRange(lowest = 0f, highest = 10f) }
        shouldThrow<IllegalArgumentException> { FrequencyRange(lowest = 10f, highest = 10f) }
        shouldThrow<IllegalArgumentException> {
            AudioSpectrum(magnitudes = Samples.of(1f), range = range, holds = Samples.of(1f, 2f))
        }
        shouldThrow<IllegalArgumentException> { AudioScope(left = Samples.of(1f), right = Samples.Empty) }
        shouldThrow<IllegalArgumentException> {
            AudioScope(
                left = Samples.Empty,
                right = Samples.Empty,
                correlation = 2f,
            )
        }
        shouldThrow<IllegalArgumentException> {
            WaveformChannel(minimums = Samples.of(1f), maximums = Samples.Empty, rms = Samples.Empty)
        }
        shouldThrow<IllegalArgumentException> {
            WaveformChannel(minimums = Samples.Empty, maximums = Samples.Empty, rms = Samples.of(1f))
        }
        shouldThrow<IllegalArgumentException> {
            WaveformPeaks(channels = listOf(channelOf(buckets = 2), channelOf(buckets = 3)), duration = Duration.ZERO)
        }
        shouldThrow<IllegalArgumentException> { WaveformPeaks(channels = emptyList(), duration = (-1).seconds) }
    }
})
