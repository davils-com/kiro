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
import kotlin.time.Duration

/**
 * The outline of every channel of a recording and how long the recording is, as a waveform and the clips of a timeline
 * show it.
 *
 * @property channels the outline of each channel, all with the same number of buckets.
 * @property duration how long the recording is.
 * @throws IllegalArgumentException if the channels have different numbers of buckets or [duration] is negative.
 * @since 1.0.0
 */
@Immutable
public class WaveformPeaks(
    public val channels: List<WaveformChannel>,
    public val duration: Duration,
) {
    init {
        val first = channels.firstOrNull()?.size ?: 0
        require(channels.all { channel -> channel.size == first }) { "Every channel needs $first buckets." }
        require(!duration.isNegative()) { "The duration must not be negative, not $duration." }
    }

    /**
     * The number of buckets of each channel.
     *
     * @since 1.0.0
     */
    public val buckets: Int
        get() = channels.firstOrNull()?.size ?: 0

    /**
     * Returns a copy of this [WaveformPeaks] with the given values replaced and every other value kept.
     *
     * @param channels the new value of [channels].
     * @param duration the new value of [duration].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        channels: List<WaveformChannel> = this.channels,
        duration: Duration = this.duration,
    ): WaveformPeaks = WaveformPeaks(channels = channels, duration = duration)

    override fun equals(other: Any?): Boolean = other is WaveformPeaks && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "WaveformPeaks(" +
        "channels=$channels, " +
        "duration=$duration)"

    private fun values(): List<Any?> = listOf(channels, duration)
}
