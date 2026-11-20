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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.components.chart.ChartStyle
import com.davils.kiro.ui.components.chart.frame.drawLabel
import com.davils.kiro.ui.components.chart.spec.GridSpec
import kotlin.math.ceil

private const val DASH: Float = 3f
private const val LABEL_GAP: Float = 4f
private const val HALF: Float = 0.5f

internal fun DrawScope.drawGrid(geometry: CartesianGeometry, grid: GridSpec, color: Color) {
    val plot = geometry.plot
    val effect = PathEffect.dashPathEffect(floatArrayOf(DASH * density, DASH * density)).takeIf { grid.isDashed }
    val categories = (0 until geometry.count).map { index -> geometry.categories.center(index) }
    val values = geometry.ticks.map { tick -> geometry.values.map(tick) }
    val isVertical = geometry.frame.isVertical
    val rows = (values.takeIf { isVertical } ?: categories).takeIf { grid.isHorizontal }.orEmpty()
    val columns = (categories.takeIf { isVertical } ?: values).takeIf { grid.isVertical }.orEmpty()
    rows.forEach { y ->
        drawLine(
            color = color,
            start = Offset(plot.left, y),
            end = Offset(plot.right, y),
            pathEffect = effect,
        )
    }
    columns.forEach { x ->
        drawLine(
            color = color,
            start = Offset(x, plot.top),
            end = Offset(x, plot.bottom),
            pathEffect = effect,
        )
    }
}

internal fun DrawScope.drawAxes(geometry: CartesianGeometry, style: ChartStyle) {
    val plot = geometry.plot
    val margin = style.tickMargin.toPx()
    val isVertical = geometry.frame.isVertical
    val labels = geometry.categoryLabels
    intervalIndices(geometry = geometry, isVertical = isVertical).forEach { index ->
        val along = geometry.categories.center(index)
        val anchor = Offset(x = along, y = plot.bottom + margin).takeIf { isVertical }
            ?: Offset(x = plot.left - margin, y = along)
        drawLabel(
            label = labels[index],
            anchor = anchor,
            color = style.tickColor,
            alignX = HALF.takeIf { isVertical } ?: 1f,
            alignY = 0f.takeIf { isVertical } ?: HALF,
        )
    }
    geometry.valueLabels.forEachIndexed { index, label ->
        val across = geometry.values.map(geometry.ticks[index])
        val anchor = Offset(x = plot.left - margin, y = across).takeIf { isVertical }
            ?: Offset(x = across, y = plot.bottom + margin)
        drawLabel(
            label = label,
            anchor = anchor,
            color = style.tickColor,
            alignX = 1f.takeIf { isVertical } ?: HALF,
            alignY = HALF.takeIf { isVertical } ?: 0f,
        )
    }
}

internal fun DrawScope.drawCursor(geometry: CartesianGeometry, index: Int, style: ChartStyle) {
    val plot = geometry.plot
    val along = geometry.categories.center(index)
    val band = geometry.categories.bandwidth
    val isVertical = geometry.frame.isVertical
    if (geometry.frame.isBand) {
        val column = Rect(left = along - band / 2, top = plot.top, right = along + band / 2, bottom = plot.bottom)
        val row = Rect(left = plot.left, top = along - band / 2, right = plot.right, bottom = along + band / 2)
        val area = column.takeIf { isVertical } ?: row
        drawRect(color = style.cursorFill, topLeft = area.topLeft, size = area.size)
        return
    }
    drawLine(
        color = style.cursorLine,
        start = Offset(x = along, y = plot.top),
        end = Offset(x = along, y = plot.bottom),
        strokeWidth = 1f,
    )
}

private fun intervalIndices(geometry: CartesianGeometry, isVertical: Boolean): List<Int> {
    val labels = geometry.categoryLabels
    if (labels.isEmpty()) return emptyList()
    val extent =
        labels.maxOf { label -> (label.size.width.takeIf { isVertical } ?: label.size.height).toFloat() } + LABEL_GAP
    val step = geometry.categories.step.takeIf { value -> value > 0f } ?: extent
    val interval = maxOf(a = ceil(extent / step).toInt(), b = 1)
    return labels.indices.filter { index -> index % interval == 0 }
}
