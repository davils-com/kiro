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
 * The outline of one channel of a recording, reduced to buckets of equal length, as a waveform shows it.
 *
 * @property minimums the lowest sample of each bucket between `-1` and `0`.
 * @property maximums the highest sample of each bucket between `0` and `1`.
 * @property rms the root mean square of each bucket between `0` and `1`.
 * @throws IllegalArgumentException if the three outlines have different numbers of buckets.
 * @since 1.0.0
 */
@Immutable
public class WaveformChannel(
    public val minimums: Samples,
    public val maximums: Samples,
    public val rms: Samples,
) {
    init {
        require(minimums.size == maximums.size && maximums.size == rms.size) {
            "The outlines have ${minimums.size}, ${maximums.size} and ${rms.size} buckets."
        }
    }

    /**
     * The number of buckets.
     *
     * @since 1.0.0
     */
    public val size: Int
        get() = minimums.size

    /**
     * Returns a copy of this [WaveformChannel] with the given values replaced and every other value kept.
     *
     * @param minimums the new value of [minimums].
     * @param maximums the new value of [maximums].
     * @param rms the new value of [rms].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        minimums: Samples = this.minimums,
        maximums: Samples = this.maximums,
        rms: Samples = this.rms,
    ): WaveformChannel = WaveformChannel(minimums = minimums, maximums = maximums, rms = rms)

    override fun equals(other: Any?): Boolean = other is WaveformChannel && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "WaveformChannel(" +
        "minimums=$minimums, " +
        "maximums=$maximums, " +
        "rms=$rms)"

    private fun values(): List<Any?> = listOf(minimums, maximums, rms)
}
