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

/**
 * The texts the audio players show and announce, in English by default.
 *
 * @property play the spoken label of the button that starts the playback.
 * @property pause the spoken label of the button that pauses the playback.
 * @property previous the spoken label of the button that plays the previous track.
 * @property next the spoken label of the button that plays the next track.
 * @property shuffle the spoken label of the button that shuffles the queue.
 * @property repeat the spoken label of the button that repeats the queue or the track.
 * @property mute the spoken label of the button that mutes the player.
 * @property queue the title of the queue and the spoken label of the button that shows it.
 * @property seek the spoken label of the bar that shows and changes the position in a track.
 * @property nothingPlaying the text in place of a title while no track is loaded.
 * @since 1.0.0
 */
@Immutable
public class AudioLabels(
    public val play: String,
    public val pause: String,
    public val previous: String,
    public val next: String,
    public val shuffle: String,
    public val repeat: String,
    public val mute: String,
    public val queue: String,
    public val seek: String,
    public val nothingPlaying: String,
) {
    /**
     * Returns a copy of this [AudioLabels] with the given values replaced and every other value kept.
     *
     * @param play the new value of [play].
     * @param pause the new value of [pause].
     * @param previous the new value of [previous].
     * @param next the new value of [next].
     * @param shuffle the new value of [shuffle].
     * @param repeat the new value of [repeat].
     * @param mute the new value of [mute].
     * @param queue the new value of [queue].
     * @param seek the new value of [seek].
     * @param nothingPlaying the new value of [nothingPlaying].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        play: String = this.play,
        pause: String = this.pause,
        previous: String = this.previous,
        next: String = this.next,
        shuffle: String = this.shuffle,
        repeat: String = this.repeat,
        mute: String = this.mute,
        queue: String = this.queue,
        seek: String = this.seek,
        nothingPlaying: String = this.nothingPlaying,
    ): AudioLabels = AudioLabels(
        play = play,
        pause = pause,
        previous = previous,
        next = next,
        shuffle = shuffle,
        repeat = repeat,
        mute = mute,
        queue = queue,
        seek = seek,
        nothingPlaying = nothingPlaying,
    )

    override fun equals(other: Any?): Boolean = other is AudioLabels && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioLabels(" +
        "play=$play, " +
        "pause=$pause, " +
        "previous=$previous, " +
        "next=$next, " +
        "shuffle=$shuffle, " +
        "repeat=$repeat, " +
        "mute=$mute, " +
        "queue=$queue, " +
        "seek=$seek, " +
        "nothingPlaying=$nothingPlaying)"

    private fun values(): List<Any?> = listOf(
        play,
        pause,
        previous,
        next,
        shuffle,
        repeat,
        mute,
        queue,
        seek,
        nothingPlaying,
    )
}
