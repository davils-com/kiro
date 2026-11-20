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

package com.davils.kiro.ui.components.chart.spec

import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.chart.AreaChartScope
import com.davils.kiro.ui.components.chart.BarChartScope
import com.davils.kiro.ui.components.chart.ChartCurve
import com.davils.kiro.ui.components.chart.ChartKey
import com.davils.kiro.ui.components.chart.LineChartScope

internal class CartesianSpec(private val numbers: (Double) -> String) :
    ChartSpec(numbers),
    BarChartScope,
    LineChartScope,
    AreaChartScope {
    private val collected = mutableListOf<CartesianMark>()

    val marks: List<CartesianMark>
        get() = collected.toList()

    var grid: GridSpec? = null
        private set

    var axes: AxisSpec = AxisSpec()
        private set

    override fun grid(isHorizontal: Boolean, isVertical: Boolean, isDashed: Boolean) {
        grid = GridSpec(isHorizontal = isHorizontal, isVertical = isVertical, isDashed = isDashed)
    }

    override fun categoryAxis(isShown: Boolean, format: (String) -> String) {
        axes = axes.copy(isCategoryShown = isShown, category = format)
    }

    override fun valueAxis(isShown: Boolean, tickCount: Int, format: ((Double) -> String)?) {
        require(tickCount >= 2) { "A value axis needs at least two ticks." }
        axes = axes.copy(isValueShown = isShown, tickCount = tickCount, value = format ?: numbers)
    }

    override fun bar(key: ChartKey, radius: Dp) {
        collected += CartesianMark.Bar(key = key, radius = radius)
    }

    override fun line(key: ChartKey, curve: ChartCurve, strokeWidth: Dp, isDotShown: Boolean) {
        collected += CartesianMark.Line(key = key, curve = curve, strokeWidth = strokeWidth, isDotShown = isDotShown)
    }

    override fun area(key: ChartKey, curve: ChartCurve, fillAlpha: Float, strokeWidth: Dp, isGradient: Boolean) {
        collected += CartesianMark.Area(
            key = key,
            curve = curve,
            fillAlpha = fillAlpha,
            strokeWidth = strokeWidth,
            isGradient = isGradient,
        )
    }
}
