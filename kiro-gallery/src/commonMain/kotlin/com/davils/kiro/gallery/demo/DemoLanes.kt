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

package com.davils.kiro.gallery.demo

import com.davils.kiro.gallery.demo.signal.DemoStem
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.audio.signal.WaveformPeaks
import com.davils.kiro.ui.audio.timeline.Clip
import com.davils.kiro.ui.audio.timeline.ClipId
import com.davils.kiro.ui.audio.timeline.TrackLane
import com.davils.kiro.ui.audio.timeline.TrackLaneId
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private const val STEMS: Int = 3
private const val QUIETER: Float = -4f
private const val LOUDER: Float = 3f
private const val BASS_TINT: Int = 1
private const val PAD_TINT: Int = 2
private const val LEAD_TINT: Int = 3
private const val FX_TINT: Int = 4

private val half: Duration = 8.seconds

internal fun demoLanes(stems: List<DemoStem>): List<TrackLane> {
    val peaks = stems.map { stem -> stem.peaks }.takeIf { all -> all.size == STEMS } ?: return emptyList()
    val (drums, bass, keys) = peaks
    return listOf(drumsLane(drums), bassLane(bass), keysLane(keys), effectsLane(keys))
}

private fun drumsLane(peaks: WaveformPeaks): TrackLane = TrackLane(
    id = TrackLaneId("drums"),
    name = "Drums",
    clips = listOf(
        Clip(
            id = ClipId("drums"),
            name = "Drums",
            start = 4.seconds,
            length = 12.seconds,
            peaks = peaks,
            sourceOffset = 4.seconds,
            fadeOut = 1_500.milliseconds,
        ),
    ),
)

private fun bassLane(peaks: WaveformPeaks): TrackLane = TrackLane(
    id = TrackLaneId("bass"),
    name = "Bass",
    clips = listOf(
        Clip(id = ClipId("bass"), name = "Bass", start = Duration.ZERO, length = half, peaks = peaks, tint = BASS_TINT),
        Clip(
            id = ClipId("bass-chorus"),
            name = "Bass Chorus",
            start = half,
            length = half,
            peaks = peaks,
            sourceOffset = half,
            gain = Decibels(value = QUIETER),
            tint = BASS_TINT,
            isLocked = true,
        ),
    ),
)

private fun keysLane(peaks: WaveformPeaks): TrackLane = TrackLane(
    id = TrackLaneId("keys"),
    name = "Keys",
    isSoloed = true,
    clips = listOf(
        Clip(
            id = ClipId("pad"),
            name = "Pad",
            start = Duration.ZERO,
            length = half,
            peaks = peaks,
            fadeIn = 2.seconds,
            tint = PAD_TINT,
        ),
        Clip(
            id = ClipId("lead"),
            name = "Lead",
            start = half,
            length = half,
            peaks = peaks,
            sourceOffset = half,
            gain = Decibels(value = LOUDER),
            tint = LEAD_TINT,
        ),
    ),
)

private fun effectsLane(peaks: WaveformPeaks): TrackLane = TrackLane(
    id = TrackLaneId("fx"),
    name = "FX",
    isMuted = true,
    clips = listOf(
        Clip(
            id = ClipId("riser"),
            name = "Riser",
            start = 5.seconds,
            length = 3.seconds,
            peaks = peaks,
            sourceOffset = 5.seconds,
            fadeIn = 3.seconds,
            tint = FX_TINT,
            isMuted = true,
        ),
    ),
)
