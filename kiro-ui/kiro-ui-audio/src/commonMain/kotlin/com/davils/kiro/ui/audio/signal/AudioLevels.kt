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

import androidx.compose.runtime.Immutable

/**
 * The levels of every channel of a signal at one moment, such as left and right of a stereo signal.
 *
 * @property channels the level of each channel, in the order of the channels.
 * @since 1.0.0
 */
@Immutable
public class AudioLevels(
    public val channels: List<ChannelLevel>,
) {
    /**
     * Returns a copy of this [AudioLevels] with the given values replaced and every other value kept.
     *
     * @param channels the new value of [channels].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(channels: List<ChannelLevel> = this.channels): AudioLevels = AudioLevels(channels = channels)

    override fun equals(other: Any?): Boolean = other is AudioLevels && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioLevels(" +
        "channels=$channels)"

    private fun values(): List<Any?> = listOf(channels)
}
