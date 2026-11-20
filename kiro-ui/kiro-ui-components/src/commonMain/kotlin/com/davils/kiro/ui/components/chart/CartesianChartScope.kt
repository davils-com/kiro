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

/**
 * The scope of the charts that place categories along one axis and values along the other: the [BarChart], the
 * [LineChart] and the [AreaChart]. It adds a grid and the two axes to the tooltip and the legend of [ChartScope].
 *
 * @since 1.0.0
 */
public interface CartesianChartScope : ChartScope {
    /**
     * Shows a grid behind the marks of the chart, in the grid color of the [ChartStyle]. The lines across the value
     * axis stand at its ticks, the lines across the category axis at the centers of the categories. Without a call
     * the chart shows no grid.
     *
     * @param isHorizontal whether horizontal lines are drawn.
     * @param isVertical whether vertical lines are drawn.
     * @param isDashed whether the lines are dashed instead of solid.
     * @since 1.0.0
     */
    public fun grid(isHorizontal: Boolean = true, isVertical: Boolean = false, isDashed: Boolean = false)

    /**
     * Configures the axis of the categories, which shows the label of each [ChartPoint]. When the labels would
     * overlap, only every second, third or further label is shown. Without a call the axis is hidden.
     *
     * @param isShown whether the axis is shown.
     * @param format turns the label of a point into the text of its tick.
     * @since 1.0.0
     */
    public fun categoryAxis(isShown: Boolean = true, format: (String) -> String = { label -> label })

    /**
     * Configures the axis of the values, whose ticks are evenly spaced round numbers that include zero and every
     * value of the chart. Without a call the axis is hidden.
     *
     * @param isShown whether the axis is shown.
     * @param tickCount the number of ticks to aim for, at least 2; rounding the ticks may change it slightly.
     * @param format turns a value into the text of its tick, or `null` for numbers with at most two decimals.
     * @throws IllegalArgumentException if [tickCount] is less than 2.
     * @since 1.0.0
     */
    public fun valueAxis(isShown: Boolean = true, tickCount: Int = 5, format: ((Double) -> String)? = null)
}
