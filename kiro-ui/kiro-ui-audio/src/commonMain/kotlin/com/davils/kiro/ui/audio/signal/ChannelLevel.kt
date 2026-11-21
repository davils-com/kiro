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

private const val NONE: String = "none"

/**
 * The level of one channel at one moment, as a level meter shows it. Kiro only shows the values; the caller measures
 * them, holds the peak and decides when the channel clips.
 *
 * @property peak the highest sample level of the last block.
 * @property rms the root mean square level of the last block.
 * @property hold the peak held for a while, or `null` to show none.
 * @property isClipping whether the channel reached full scale since the clip indicator was reset.
 * @since 1.0.0
 */
@Immutable
public class ChannelLevel(
    public val peak: Decibels,
    public val rms: Decibels,
    public val hold: Decibels? = null,
    public val isClipping: Boolean = false,
) {
    /**
     * Returns a copy of this [ChannelLevel] with the given values replaced and every other value kept.
     *
     * @param peak the new value of [peak].
     * @param rms the new value of [rms].
     * @param hold the new value of [hold].
     * @param isClipping the new value of [isClipping].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        peak: Decibels = this.peak,
        rms: Decibels = this.rms,
        hold: Decibels? = this.hold,
        isClipping: Boolean = this.isClipping,
    ): ChannelLevel = ChannelLevel(peak = peak, rms = rms, hold = hold, isClipping = isClipping)

    override fun equals(other: Any?): Boolean = other is ChannelLevel && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ChannelLevel(" +
        "peak=$peak, " +
        "rms=$rms, " +
        "hold=${hold ?: NONE}, " +
        "isClipping=$isClipping)"

    private fun values(): List<Any?> = listOf(peak, rms, hold, isClipping)
}
