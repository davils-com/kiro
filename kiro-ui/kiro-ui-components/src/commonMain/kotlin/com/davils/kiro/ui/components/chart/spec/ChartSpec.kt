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

import com.davils.kiro.ui.components.chart.ChartLegendPosition
import com.davils.kiro.ui.components.chart.ChartScope
import com.davils.kiro.ui.components.chart.ChartTooltipIndicator

internal open class ChartSpec(private val numbers: (Double) -> String) : ChartScope {
    var tooltip: TooltipSpec? = null
        private set

    var legend: LegendSpec? = null
        private set

    override fun tooltip(
        indicator: ChartTooltipIndicator,
        isLabelHidden: Boolean,
        isIndicatorHidden: Boolean,
        format: ((Double) -> String)?,
    ) {
        tooltip = TooltipSpec(
            indicator = indicator,
            isLabelHidden = isLabelHidden,
            isIndicatorHidden = isIndicatorHidden,
            format = format ?: numbers,
        )
    }

    override fun legend(position: ChartLegendPosition, isIconHidden: Boolean) {
        legend = LegendSpec(position = position, isIconHidden = isIconHidden)
    }
}
