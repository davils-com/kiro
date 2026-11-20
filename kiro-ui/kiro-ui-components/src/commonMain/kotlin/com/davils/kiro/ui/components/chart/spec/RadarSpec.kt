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
import com.davils.kiro.ui.components.chart.ChartKey
import com.davils.kiro.ui.components.chart.RadarChartScope

internal class RadarSpec(numbers: (Double) -> String) :
    ChartSpec(numbers),
    RadarChartScope {
    private val collected = mutableListOf<RadarMark>()

    val marks: List<RadarMark>
        get() = collected.toList()

    var grid: PolarGridSpec? = null
        private set

    var angleFormat: ((String) -> String)? = null
        private set

    override fun grid(isCircle: Boolean, isSpokeShown: Boolean) {
        grid = PolarGridSpec(isCircle = isCircle, isSpokeShown = isSpokeShown)
    }

    override fun angleAxis(format: (String) -> String) {
        angleFormat = format
    }

    override fun radar(key: ChartKey, fillAlpha: Float, strokeWidth: Dp, isDotShown: Boolean) {
        collected += RadarMark(key = key, fillAlpha = fillAlpha, strokeWidth = strokeWidth, isDotShown = isDotShown)
    }
}
