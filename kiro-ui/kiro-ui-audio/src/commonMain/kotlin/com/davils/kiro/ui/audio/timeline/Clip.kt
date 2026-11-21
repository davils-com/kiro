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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.audio.signal.WaveformPeaks
import kotlin.time.Duration

/**
 * A clip on a [TrackLane], the bar an editing program shows for a piece of a recording: where it sits on the timeline,
 * which part of the recording it plays, how it fades in and out and how loud it is. It describes what to show; Kiro
 * edits no audio.
 *
 * @property id the identifier of the clip.
 * @property name the name shown in the header of the clip.
 * @property start where the clip begins on the timeline.
 * @property length how long the clip plays.
 * @property peaks the outline of the whole recording the clip plays a part of.
 * @property sourceOffset where in the recording the clip begins.
 * @property fadeIn how long the clip fades in at its start.
 * @property fadeOut how long the clip fades out at its end.
 * @property gain the gain applied to the clip.
 * @property tint the position of the color of the clip in the tints of its style.
 * @property isMuted whether the clip is muted.
 * @property isLocked whether the clip is locked against moving, trimming, fading and gain.
 * @throws IllegalArgumentException if [start], [length], [sourceOffset], [fadeIn] or [fadeOut] is negative.
 * @since 1.0.0
 */
@Immutable
public class Clip(
    public val id: ClipId,
    public val name: String,
    public val start: Duration,
    public val length: Duration,
    public val peaks: WaveformPeaks,
    public val sourceOffset: Duration = Duration.ZERO,
    public val fadeIn: Duration = Duration.ZERO,
    public val fadeOut: Duration = Duration.ZERO,
    public val gain: Decibels = Decibels(0f),
    public val tint: Int = 0,
    public val isMuted: Boolean = false,
    public val isLocked: Boolean = false,
) {
    init {
        val durations = listOf(start, length, sourceOffset, fadeIn, fadeOut)
        require(
            durations.none { duration -> duration.isNegative() },
        ) { "The times of the clip $id must not be negative." }
    }

    /**
     * Where the clip ends on the timeline.
     *
     * @since 1.0.0
     */
    public val end: Duration
        get() = start + length

    /**
     * Returns a copy of this [Clip] with the given values replaced and every other value kept.
     *
     * @param id the new value of [id].
     * @param name the new value of [name].
     * @param start the new value of [start].
     * @param length the new value of [length].
     * @param peaks the new value of [peaks].
     * @param sourceOffset the new value of [sourceOffset].
     * @param fadeIn the new value of [fadeIn].
     * @param fadeOut the new value of [fadeOut].
     * @param gain the new value of [gain].
     * @param tint the new value of [tint].
     * @param isMuted the new value of [isMuted].
     * @param isLocked the new value of [isLocked].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        id: ClipId = this.id,
        name: String = this.name,
        start: Duration = this.start,
        length: Duration = this.length,
        peaks: WaveformPeaks = this.peaks,
        sourceOffset: Duration = this.sourceOffset,
        fadeIn: Duration = this.fadeIn,
        fadeOut: Duration = this.fadeOut,
        gain: Decibels = this.gain,
        tint: Int = this.tint,
        isMuted: Boolean = this.isMuted,
        isLocked: Boolean = this.isLocked,
    ): Clip = Clip(
        id = id,
        name = name,
        start = start,
        length = length,
        peaks = peaks,
        sourceOffset = sourceOffset,
        fadeIn = fadeIn,
        fadeOut = fadeOut,
        gain = gain,
        tint = tint,
        isMuted = isMuted,
        isLocked = isLocked,
    )

    override fun equals(other: Any?): Boolean = other is Clip && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Clip(" +
        "id=$id, " +
        "name=$name, " +
        "start=$start, " +
        "length=$length, " +
        "peaks=$peaks, " +
        "sourceOffset=$sourceOffset, " +
        "fadeIn=$fadeIn, " +
        "fadeOut=$fadeOut, " +
        "gain=$gain, " +
        "tint=$tint, " +
        "isMuted=$isMuted, " +
        "isLocked=$isLocked)"

    private fun values(): List<Any?> = listOf(
        id,
        name,
        start,
        length,
        peaks,
        sourceOffset,
        fadeIn,
        fadeOut,
        gain,
        tint,
        isMuted,
        isLocked,
    )
}
