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

package com.davils.kiro.ui.components.chart.cartesian

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize
import com.davils.kiro.ui.components.chart.ChartPoint
import com.davils.kiro.ui.components.chart.ChartStyle
import com.davils.kiro.ui.components.chart.frame.ChartFrame
import com.davils.kiro.ui.components.chart.frame.chart
import com.davils.kiro.ui.components.chart.spec.CartesianMark
import com.davils.kiro.ui.components.chart.spec.CartesianSpec

@Composable
internal fun CartesianChart(data: List<ChartPoint>, spec: CartesianSpec, frame: CartesianFrame, modifier: Modifier) {
    val context = chart()
    val style = context.style
    val measurer = rememberTextMeasurer()
    val density = LocalDensity.current
    var size by remember { mutableStateOf(IntSize.Zero) }
    val keys = spec.marks.map { mark -> mark.key }
    val colors = keys.mapIndexed { index, key -> context.palette.colorOf(key, index) }
    val layout = CartesianLayout(measurer = measurer, style = style, density = density)
    val geometry = size.takeIf { measured -> measured != IntSize.Zero }?.let { measured ->
        layout.of(size = measured.toSize(), data = data, spec = spec, frame = frame)
    }
    val target = CartesianTarget(
        geometry = geometry,
        data = data,
        keys = keys,
        colors = colors,
        palette = context.palette,
    )
    ChartFrame(
        modifier = modifier,
        spec = spec,
        target = target,
        onSize = { measured -> size = measured },
    ) { active, progress ->
        geometry?.let { shown ->
            drawCartesian(
                geometry = shown,
                spec = spec,
                colors = colors,
                style = style,
                active = active,
                progress = progress,
            )
        }
    }
}

private fun DrawScope.drawCartesian(
    geometry: CartesianGeometry,
    spec: CartesianSpec,
    colors: List<Color>,
    style: ChartStyle,
    active: Int?,
    progress: Float,
) {
    spec.grid?.let { grid -> drawGrid(geometry = geometry, grid = grid, color = style.gridColor) }
    drawAxes(geometry = geometry, style = style)
    active?.let { index -> drawCursor(geometry = geometry, index = index, style = style) }
    val marks = spec.marks.withIndex()
    drawBars(geometry = geometry, bars = marks.only(), colors = colors, style = style, progress = progress)
    drawAreas(geometry = geometry, areas = marks.only(), colors = colors, progress = progress)
    drawLines(geometry = geometry, lines = marks.only(), colors = colors, style = style, progress = progress)
    val pointed = spec.marks.indices.filter { index -> spec.marks[index] !is CartesianMark.Bar }
    active?.let { index ->
        drawActiveDots(
            geometry = geometry,
            marks = pointed,
            colors = colors,
            index = index,
            style = style,
        )
    }
}

private inline fun <reified T : CartesianMark> Iterable<IndexedValue<CartesianMark>>.only(): List<IndexedValue<T>> =
    mapNotNull { (index, mark) -> (mark as? T)?.let { typed -> IndexedValue(index, typed) } }
