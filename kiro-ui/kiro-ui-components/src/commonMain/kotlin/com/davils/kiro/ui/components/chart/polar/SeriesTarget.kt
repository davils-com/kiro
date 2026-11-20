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

package com.davils.kiro.ui.components.chart.polar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.components.chart.ChartKey
import com.davils.kiro.ui.components.chart.ChartPoint
import com.davils.kiro.ui.components.chart.frame.ChartPalette
import com.davils.kiro.ui.components.chart.frame.ChartTarget
import com.davils.kiro.ui.components.chart.frame.LegendItem
import com.davils.kiro.ui.components.chart.frame.TooltipModel
import com.davils.kiro.ui.components.chart.frame.TooltipRow
import com.davils.kiro.ui.components.chart.valueOf

internal class SeriesTarget(
    private val data: List<ChartPoint>,
    private val keys: List<ChartKey>,
    private val colors: List<Color>,
    private val palette: ChartPalette,
    private val indexAt: (Offset) -> Int?,
    private val anchorAt: (Int) -> Offset,
) : ChartTarget {
    override val count: Int
        get() = data.size

    override fun indexAt(position: Offset): Int? = indexAt.invoke(position)

    override fun anchorOf(index: Int): Offset = anchorAt(index)

    override fun tooltipOf(index: Int): TooltipModel {
        val point = data[index]
        val rows = keys.mapIndexed { mark, key ->
            TooltipRow(
                color = colors[mark],
                name = palette.labelOf(key),
                value = point.valueOf(key),
                icon = palette.iconOf(key),
            )
        }
        return TooltipModel(label = palette.labelOf(ChartKey(point.label)), rows = rows)
    }

    override fun legend(): List<LegendItem> = keys.mapIndexed { mark, key ->
        LegendItem(color = colors[mark], label = palette.labelOf(key), icon = palette.iconOf(key))
    }
}
