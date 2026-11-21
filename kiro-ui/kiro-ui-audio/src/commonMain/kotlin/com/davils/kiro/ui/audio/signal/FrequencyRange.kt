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
import kotlin.math.exp
import kotlin.math.ln

/**
 * The frequencies the bands of an [AudioSpectrum] cover, from the center of the first band to the center of the last,
 * and how the bands are spaced in between.
 *
 * @property lowest the frequency of the first band in hertz.
 * @property highest the frequency of the last band in hertz.
 * @property spacing how the bands are spaced.
 * @throws IllegalArgumentException if [lowest] is not positive or not below [highest].
 * @since 1.0.0
 */
@Immutable
public class FrequencyRange(
    public val lowest: Float,
    public val highest: Float,
    public val spacing: FrequencySpacing = FrequencySpacing.Logarithmic,
) {
    init {
        require(lowest > 0f) { "The lowest frequency must be positive, not $lowest." }
        require(lowest < highest) { "The lowest frequency $lowest must be below the highest frequency $highest." }
    }

    /**
     * Returns where a frequency lies on the axis of this range.
     *
     * @param frequency the frequency in hertz.
     * @return `0` at [lowest], `1` at [highest] and the position in between by [spacing]; frequencies outside the
     * range lie outside of `0` to `1`.
     * @since 1.0.0
     */
    public fun fractionOf(frequency: Float): Float = when (spacing) {
        FrequencySpacing.Logarithmic -> ln(frequency / lowest) / ln(highest / lowest)
        FrequencySpacing.Linear -> (frequency - lowest) / (highest - lowest)
    }

    /**
     * Returns the frequency at a position on the axis of this range.
     *
     * @param fraction the position, `0` at [lowest] and `1` at [highest].
     * @return the frequency in hertz.
     * @since 1.0.0
     */
    public fun frequencyAt(fraction: Float): Float = when (spacing) {
        FrequencySpacing.Logarithmic -> lowest * exp(ln(highest / lowest) * fraction)
        FrequencySpacing.Linear -> lowest + (highest - lowest) * fraction
    }

    /**
     * Returns a copy of this [FrequencyRange] with the given values replaced and every other value kept.
     *
     * @param lowest the new value of [lowest].
     * @param highest the new value of [highest].
     * @param spacing the new value of [spacing].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        lowest: Float = this.lowest,
        highest: Float = this.highest,
        spacing: FrequencySpacing = this.spacing,
    ): FrequencyRange = FrequencyRange(lowest = lowest, highest = highest, spacing = spacing)

    override fun equals(other: Any?): Boolean = other is FrequencyRange && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "FrequencyRange(" +
        "lowest=$lowest, " +
        "highest=$highest, " +
        "spacing=$spacing)"

    private fun values(): List<Any?> = listOf(lowest, highest, spacing)
}
