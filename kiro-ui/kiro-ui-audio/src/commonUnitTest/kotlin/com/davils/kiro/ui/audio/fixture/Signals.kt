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

package com.davils.kiro.ui.audio.fixture

import com.davils.kiro.ui.audio.signal.AudioLevels
import com.davils.kiro.ui.audio.signal.AudioScope
import com.davils.kiro.ui.audio.signal.AudioSpectrum
import com.davils.kiro.ui.audio.signal.ChannelLevel
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.audio.signal.FrequencyRange
import com.davils.kiro.ui.audio.signal.Samples
import com.davils.kiro.ui.audio.signal.WaveformChannel
import com.davils.kiro.ui.audio.signal.WaveformPeaks
import com.davils.kiro.ui.audio.timeline.Clip
import com.davils.kiro.ui.audio.timeline.ClipId
import com.davils.kiro.ui.audio.timeline.TrackLane
import com.davils.kiro.ui.audio.timeline.TrackLaneId
import kotlin.math.PI
import kotlin.math.sin
import kotlin.time.Duration.Companion.seconds

private const val BANDS: Int = 24
private const val SAMPLES: Int = 256
private const val BUCKETS: Int = 400
private const val CYCLES: Float = 6f

internal val stereo: AudioLevels = AudioLevels(
    channels = listOf(
        ChannelLevel(peak = Decibels(-3f), rms = Decibels(-12f), hold = Decibels(-1f), isClipping = true),
        ChannelLevel(peak = Decibels(-9f), rms = Decibels(-20f), hold = Decibels(-6f)),
    ),
)

internal val surround: AudioLevels = AudioLevels(
    channels = List(size = 3) { channel -> ChannelLevel(peak = Decibels(-6f * channel), rms = Decibels(-24f)) },
)

internal val range: FrequencyRange = FrequencyRange(lowest = 40f, highest = 16_000f)

internal fun spectrumOf(bands: Int = BANDS, withHolds: Boolean = true): AudioSpectrum {
    val magnitudes = FloatArray(bands) { band -> -6f - 60f * band / bands }
    val holds = Samples(FloatArray(bands) { band -> magnitudes[band] + 6f }).takeIf { withHolds }
    return AudioSpectrum(magnitudes = Samples(magnitudes), range = range, holds = holds)
}

internal fun scopeOf(size: Int = SAMPLES): AudioScope {
    val left = FloatArray(size) { index -> sin(2f * PI.toFloat() * CYCLES * index / size) * 0.8f }
    val right = FloatArray(size) { index -> sin(2f * PI.toFloat() * CYCLES * index / size + 0.4f) * 0.6f }
    return AudioScope(left = Samples(left), right = Samples(right), correlation = 0.7f)
}

internal fun channelOf(buckets: Int = BUCKETS): WaveformChannel {
    val maximums = FloatArray(buckets) { bucket -> 0.2f + 0.7f * sin(PI.toFloat() * bucket / buckets) }
    return WaveformChannel(
        minimums = Samples(FloatArray(buckets) { bucket -> -maximums[bucket] }),
        maximums = Samples(maximums),
        rms = Samples(FloatArray(buckets) { bucket -> maximums[bucket] / 2f }),
    )
}

internal val peaks: WaveformPeaks = WaveformPeaks(channels = listOf(channelOf(), channelOf()), duration = 16.seconds)

internal fun clipOf(
    id: String = "clip",
    start: Int = 2,
    length: Int = 4,
    offset: Int = 2,
    isLocked: Boolean = false,
): Clip = Clip(
    id = ClipId(id),
    name = id,
    start = start.seconds,
    length = length.seconds,
    peaks = peaks,
    sourceOffset = offset.seconds,
    fadeIn = 1.seconds,
    fadeOut = 1.seconds,
    isLocked = isLocked,
)

internal fun lanesOf(): List<TrackLane> = listOf(
    TrackLane(id = TrackLaneId("one"), name = "One", clips = listOf(clipOf(id = "a"), clipOf(id = "b", start = 8))),
    TrackLane(id = TrackLaneId("two"), name = "Two", clips = listOf(clipOf(id = "locked", isLocked = true))),
)
