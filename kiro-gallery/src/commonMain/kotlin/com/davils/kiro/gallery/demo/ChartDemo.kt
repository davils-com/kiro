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

package com.davils.kiro.gallery.demo

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.chart.BarChart
import com.davils.kiro.ui.components.chart.ChartConfig
import com.davils.kiro.ui.components.chart.ChartContainer
import com.davils.kiro.ui.components.chart.ChartKey
import com.davils.kiro.ui.components.chart.ChartPoint
import com.davils.kiro.ui.components.chart.ChartSeries

private val desktop = ChartKey("desktop")
private val mobile = ChartKey("mobile")

private val config = ChartConfig(
    listOf(ChartSeries(key = desktop, label = "Desktop"), ChartSeries(key = mobile, label = "Mobile")),
)

private val visitors: List<ChartPoint> = listOf(
    ChartPoint(label = "January", values = mapOf(desktop to 186.0, mobile to 80.0)),
    ChartPoint(label = "February", values = mapOf(desktop to 305.0, mobile to 200.0)),
    ChartPoint(label = "March", values = mapOf(desktop to 237.0, mobile to 120.0)),
    ChartPoint(label = "April", values = mapOf(desktop to 73.0, mobile to 190.0)),
    ChartPoint(label = "May", values = mapOf(desktop to 209.0, mobile to 130.0)),
    ChartPoint(label = "June", values = mapOf(desktop to 214.0, mobile to 140.0)),
)

@Composable
internal fun ChartDemo() {
    ChartContainer(config = config, modifier = Modifier.width(560.dp)) {
        BarChart(data = visitors) {
            grid()
            categoryAxis(format = { label -> label.take(n = 3) })
            tooltip()
            legend()
            bar(desktop, radius = 4.dp)
            bar(mobile, radius = 4.dp)
        }
    }
}
