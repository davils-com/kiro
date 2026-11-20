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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.chart.cartesian.CartesianChart
import com.davils.kiro.ui.components.chart.cartesian.CartesianFrame
import com.davils.kiro.ui.components.chart.scale.chartNumbers
import com.davils.kiro.ui.components.chart.spec.CartesianSpec

/**
 * Draws a line chart: one line per series declared in [content], over the categories of [data] from left to right,
 * with the values growing upwards.
 *
 * The chart takes its colors, labels and style from the [ChartContainer] around it.
 *
 * @param data the points of the chart, one per category, in order.
 * @param modifier the modifier of the chart.
 * @param content declares the lines, the grid, the axes, the tooltip and the legend of the chart.
 * @throws IllegalArgumentException if the chart is not placed in a [ChartContainer].
 * @since 1.0.0
 */
@Composable
public fun LineChart(data: List<ChartPoint>, modifier: Modifier = Modifier, content: LineChartScope.() -> Unit) {
    val spec = CartesianSpec(chartNumbers()).apply(content)
    CartesianChart(
        data = data,
        spec = spec,
        frame = CartesianFrame(isVertical = true, isBand = false, isStacked = false),
        modifier = modifier,
    )
}
