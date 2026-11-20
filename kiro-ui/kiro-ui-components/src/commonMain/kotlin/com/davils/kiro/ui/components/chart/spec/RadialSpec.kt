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
import com.davils.kiro.ui.components.chart.RadialBarChartScope

internal class RadialSpec(numbers: (Double) -> String) :
    ChartSpec(numbers),
    RadialBarChartScope {
    private val collected = mutableListOf<RadialMark>()

    val marks: List<RadialMark>
        get() = collected.toList()

    override fun radialBar(key: ChartKey, isBackgroundShown: Boolean, cornerRadius: Dp) {
        collected += RadialMark(key = key, isBackgroundShown = isBackgroundShown, cornerRadius = cornerRadius)
    }
}
