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

package com.davils.kiro.ui.components.chart

import androidx.compose.runtime.Immutable

/**
 * Names and colors the series of the charts in a [ChartContainer].
 *
 * A chart looks up the [ChartSeries] of a key here for its color, its label in the tooltip and the legend and its
 * icon. A key without a series gets a color of the palette by its position and its own value as label.
 *
 * @property series the series of the charts.
 * @since 1.0.0
 */
@Immutable
public class ChartConfig(
    public val series: List<ChartSeries>,
) {
    /**
     * Returns a copy of this [ChartConfig] with the given values replaced and every other value kept.
     *
     * @param series the new value of [series].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(series: List<ChartSeries> = this.series): ChartConfig = ChartConfig(
        series = series,
    )

    override fun equals(other: Any?): Boolean = other is ChartConfig && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ChartConfig(" +
        "series=$series)"

    private fun values(): List<Any?> = listOf(
        series,
    )
}
