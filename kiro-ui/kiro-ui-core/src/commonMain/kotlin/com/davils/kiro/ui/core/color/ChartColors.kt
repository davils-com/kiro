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

package com.davils.kiro.ui.core.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * The colors of the series of a chart, in the order in which a chart assigns them.
 *
 * @property first the color of the first series.
 * @property second the color of the second series.
 * @property third the color of the third series.
 * @property fourth the color of the fourth series.
 * @property fifth the color of the fifth series.
 * @since 1.0.0
 */
@Immutable
public class ChartColors(
    public val first: Color,
    public val second: Color,
    public val third: Color,
    public val fourth: Color,
    public val fifth: Color,
) {
    /**
     * Returns a copy of this [ChartColors] with the given values replaced and every other value kept.
     *
     * @param first the new value of [first].
     * @param second the new value of [second].
     * @param third the new value of [third].
     * @param fourth the new value of [fourth].
     * @param fifth the new value of [fifth].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        first: Color = this.first,
        second: Color = this.second,
        third: Color = this.third,
        fourth: Color = this.fourth,
        fifth: Color = this.fifth,
    ): ChartColors = ChartColors(
        first = first,
        second = second,
        third = third,
        fourth = fourth,
        fifth = fifth,
    )

    override fun equals(other: Any?): Boolean = other is ChartColors && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ChartColors(" +
        "first=$first, " +
        "second=$second, " +
        "third=$third, " +
        "fourth=$fourth, " +
        "fifth=$fifth)"

    private fun values(): List<Any?> = listOf(
        first,
        second,
        third,
        fourth,
        fifth,
    )
}
