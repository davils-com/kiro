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

import androidx.compose.runtime.Immutable
import kotlin.time.Duration

/**
 * What a user asks an audio player to do. The players report actions and never change their state themselves, so
 * that an application can forward them to its playback engine; [AudioPlayerState.apply] handles them in memory.
 *
 * @since 1.0.0
 */
@Immutable
public sealed interface AudioAction {
    /**
     * Starts the playback of the current track.
     *
     * @since 1.0.0
     */
    public data object Play : AudioAction

    /**
     * Pauses the playback.
     *
     * @since 1.0.0
     */
    public data object Pause : AudioAction

    /**
     * Plays the next track of the queue.
     *
     * @since 1.0.0
     */
    public data object Next : AudioAction

    /**
     * Restarts the current track, or plays the previous one near its start.
     *
     * @since 1.0.0
     */
    public data object Previous : AudioAction

    /**
     * Mutes the player or turns the sound on again.
     *
     * @since 1.0.0
     */
    public data object Mute : AudioAction

    /**
     * Turns the shuffled order of the queue on or off.
     *
     * @since 1.0.0
     */
    public data object Shuffle : AudioAction

    /**
     * Moves on to the next [RepeatMode].
     *
     * @since 1.0.0
     */
    public data object Repeat : AudioAction

    /**
     * Moves the playback to a position in the current track.
     *
     * @property position the position to play from.
     * @since 1.0.0
     */
    public class Seek(public val position: Duration) : AudioAction {
        override fun equals(other: Any?): Boolean = other is Seek && position == other.position

        override fun hashCode(): Int = position.hashCode()

        override fun toString(): String = "Seek(position=$position)"
    }

    /**
     * Changes the volume.
     *
     * @property level the new volume between `0` and `1`.
     * @since 1.0.0
     */
    public class Volume(public val level: Float) : AudioAction {
        override fun equals(other: Any?): Boolean = other is Volume && level == other.level

        override fun hashCode(): Int = level.hashCode()

        override fun toString(): String = "Volume(level=$level)"
    }

    /**
     * Plays a track of the queue.
     *
     * @property track the track to play.
     * @since 1.0.0
     */
    public class Select(public val track: TrackId) : AudioAction {
        override fun equals(other: Any?): Boolean = other is Select && track == other.track

        override fun hashCode(): Int = track.hashCode()

        override fun toString(): String = "Select(track=$track)"
    }
}
