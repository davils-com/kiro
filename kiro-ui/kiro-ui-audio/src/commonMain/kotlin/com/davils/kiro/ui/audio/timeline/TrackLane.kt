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

/**
 * A track of a timeline with its clips and its switches for mute, solo and record.
 *
 * @property id the identifier of the track.
 * @property name the name shown in the header of the track.
 * @property clips the clips on the track.
 * @property isMuted whether the track is muted.
 * @property isSoloed whether the track plays solo.
 * @property isArmed whether the track is armed for recording.
 * @since 1.0.0
 */
@Immutable
public class TrackLane(
    public val id: TrackLaneId,
    public val name: String,
    public val clips: List<Clip>,
    public val isMuted: Boolean = false,
    public val isSoloed: Boolean = false,
    public val isArmed: Boolean = false,
) {
    /**
     * Returns a copy of this [TrackLane] with the given values replaced and every other value kept.
     *
     * @param id the new value of [id].
     * @param name the new value of [name].
     * @param clips the new value of [clips].
     * @param isMuted the new value of [isMuted].
     * @param isSoloed the new value of [isSoloed].
     * @param isArmed the new value of [isArmed].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        id: TrackLaneId = this.id,
        name: String = this.name,
        clips: List<Clip> = this.clips,
        isMuted: Boolean = this.isMuted,
        isSoloed: Boolean = this.isSoloed,
        isArmed: Boolean = this.isArmed,
    ): TrackLane = TrackLane(
        id = id,
        name = name,
        clips = clips,
        isMuted = isMuted,
        isSoloed = isSoloed,
        isArmed = isArmed,
    )

    override fun equals(other: Any?): Boolean = other is TrackLane && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TrackLane(" +
        "id=$id, " +
        "name=$name, " +
        "clips=$clips, " +
        "isMuted=$isMuted, " +
        "isSoloed=$isSoloed, " +
        "isArmed=$isArmed)"

    private fun values(): List<Any?> = listOf(id, name, clips, isMuted, isSoloed, isArmed)
}
