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
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The icons of the audio players.
 *
 * @property previous the icon of the button that plays the previous track.
 * @property next the icon of the button that plays the next track.
 * @property shuffle the icon of the button that shuffles the queue.
 * @property repeat the icon of the button that repeats the queue or, while it is off, nothing.
 * @property repeatOne the icon of the repeat button while one track repeats.
 * @property muted the icon of the volume while the sound is muted or at zero.
 * @property quiet the icon of a quiet volume.
 * @property medium the icon of a medium volume.
 * @property loud the icon of a loud volume.
 * @property queue the icon of the button that shows the queue.
 * @property placeholder the icon in place of a missing cover.
 * @since 1.0.0
 */
@Immutable
public class AudioIcons(
    public val previous: IconGraphic,
    public val next: IconGraphic,
    public val shuffle: IconGraphic,
    public val repeat: IconGraphic,
    public val repeatOne: IconGraphic,
    public val muted: IconGraphic,
    public val quiet: IconGraphic,
    public val medium: IconGraphic,
    public val loud: IconGraphic,
    public val queue: IconGraphic,
    public val placeholder: IconGraphic,
) {
    /**
     * Returns a copy of this [AudioIcons] with the given values replaced and every other value kept.
     *
     * @param previous the new value of [previous].
     * @param next the new value of [next].
     * @param shuffle the new value of [shuffle].
     * @param repeat the new value of [repeat].
     * @param repeatOne the new value of [repeatOne].
     * @param muted the new value of [muted].
     * @param quiet the new value of [quiet].
     * @param medium the new value of [medium].
     * @param loud the new value of [loud].
     * @param queue the new value of [queue].
     * @param placeholder the new value of [placeholder].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        previous: IconGraphic = this.previous,
        next: IconGraphic = this.next,
        shuffle: IconGraphic = this.shuffle,
        repeat: IconGraphic = this.repeat,
        repeatOne: IconGraphic = this.repeatOne,
        muted: IconGraphic = this.muted,
        quiet: IconGraphic = this.quiet,
        medium: IconGraphic = this.medium,
        loud: IconGraphic = this.loud,
        queue: IconGraphic = this.queue,
        placeholder: IconGraphic = this.placeholder,
    ): AudioIcons = AudioIcons(
        previous = previous,
        next = next,
        shuffle = shuffle,
        repeat = repeat,
        repeatOne = repeatOne,
        muted = muted,
        quiet = quiet,
        medium = medium,
        loud = loud,
        queue = queue,
        placeholder = placeholder,
    )

    override fun equals(other: Any?): Boolean = other is AudioIcons && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioIcons(" +
        "previous=$previous, " +
        "next=$next, " +
        "shuffle=$shuffle, " +
        "repeat=$repeat, " +
        "repeatOne=$repeatOne, " +
        "muted=$muted, " +
        "quiet=$quiet, " +
        "medium=$medium, " +
        "loud=$loud, " +
        "queue=$queue, " +
        "placeholder=$placeholder)"

    private fun values(): List<Any?> = listOf(
        previous,
        next,
        shuffle,
        repeat,
        repeatOne,
        muted,
        quiet,
        medium,
        loud,
        queue,
        placeholder,
    )
}
