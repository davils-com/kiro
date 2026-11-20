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

package com.davils.kiro.ui.components.chart.frame

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import com.davils.kiro.ui.components.chart.ChartConfig
import com.davils.kiro.ui.components.chart.ChartKey
import com.davils.kiro.ui.components.chart.ChartSeries
import com.davils.kiro.ui.icons.IconGraphic

internal class ChartPalette(private val config: ChartConfig, private val colors: List<Color>) {
    init {
        require(colors.isNotEmpty()) { "A chart palette needs at least one color." }
    }

    fun colorOf(key: ChartKey, fallback: Int): Color {
        val series = seriesOf(key)
        val index = config.series.indexOf(series).takeIf { position -> position >= 0 } ?: fallback
        val color = series?.color ?: Color.Unspecified
        return color.takeIf { configured -> configured.isSpecified } ?: colors[index % colors.size]
    }

    fun labelOf(key: ChartKey): String = seriesOf(key)?.label ?: key.value

    fun iconOf(key: ChartKey): IconGraphic? = seriesOf(key)?.icon

    private fun seriesOf(key: ChartKey): ChartSeries? = config.series.firstOrNull { series ->
        series.key == key
    }
}
