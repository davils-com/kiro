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
 * The levels a meter or an analyzer shows, from a floor that maps to the bottom to a ceiling that maps to the top.
 * Levels outside the range are held at its edges.
 *
 * @property floor the level at the bottom of the scale.
 * @property ceiling the level at the top of the scale.
 * @throws IllegalArgumentException if [floor] is not below [ceiling].
 * @since 1.0.0
 */
@Immutable
public class DecibelRange(
    public val floor: Decibels,
    public val ceiling: Decibels,
) {
    init {
        require(floor.value < ceiling.value) { "The floor $floor must be below the ceiling $ceiling." }
    }

    /**
     * Returns where a level lies within this range.
     *
     * @param level the level to place.
     * @return `0` at or below [floor], `1` at or above [ceiling] and the share of the range in between.
     * @since 1.0.0
     */
    public fun fractionOf(level: Decibels): Float = fractionOf(level = level.value)

    internal fun fractionOf(level: Float): Float {
        val fraction = (level - floor.value) / (ceiling.value - floor.value)
        return fraction.takeUnless { it.isNaN() }?.coerceIn(minimumValue = 0f, maximumValue = 1f) ?: 0f
    }

    /**
     * Returns a copy of this [DecibelRange] with the given values replaced and every other value kept.
     *
     * @param floor the new value of [floor].
     * @param ceiling the new value of [ceiling].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(floor: Decibels = this.floor, ceiling: Decibels = this.ceiling): DecibelRange =
        DecibelRange(floor = floor, ceiling = ceiling)

    override fun equals(other: Any?): Boolean = other is DecibelRange && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "DecibelRange(" +
        "floor=$floor, " +
        "ceiling=$ceiling)"

    private fun values(): List<Any?> = listOf(floor, ceiling)
}
