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

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import com.davils.kiro.ui.components.chart.ChartStyle
import com.davils.kiro.ui.components.chart.curve.ChartCurves
import com.davils.kiro.ui.components.chart.spec.CartesianMark

private const val GRADIENT_TOP: Float = 0.8f
private const val GRADIENT_BOTTOM: Float = 0.1f

internal fun DrawScope.drawBars(
    geometry: CartesianGeometry,
    bars: List<IndexedValue<CartesianMark.Bar>>,
    colors: List<Color>,
    style: ChartStyle,
    progress: Float,
) {
    val band = geometry.categories.bandwidth
    val inner = band * (1 - 2 * style.bandPadding)
    val gap = style.barGap.toPx()
    val slots = 1.takeIf { geometry.frame.isStacked } ?: bars.size
    val thickness = maxOf((inner - gap * (slots - 1)) / maxOf(slots, 1), 1f)
    bars.forEachIndexed { slot, (mark, bar) ->
        val shift = 0.takeIf { geometry.frame.isStacked } ?: slot
        geometry.spans[mark].forEachIndexed { index, span ->
            val offset = geometry.categories.center(index) - inner / 2 + shift * (thickness + gap)
            val start = geometry.values.map(span.start)
            val end = geometry.values.map(span.at(progress))
            val low = minOf(start, end)
            val high = maxOf(start, end)
            val column = Rect(left = offset, top = low, right = offset + thickness, bottom = high)
            val row = Rect(left = low, top = offset, right = high, bottom = offset + thickness)
            val area = column.takeIf { geometry.frame.isVertical } ?: row
            val radius = minOf(bar.radius.toPx(), area.width / 2, area.height / 2)
            drawRoundRect(
                color = colors[mark],
                topLeft = area.topLeft,
                size = area.size,
                cornerRadius = CornerRadius(radius),
            )
        }
    }
}

internal fun DrawScope.drawLines(
    geometry: CartesianGeometry,
    lines: List<IndexedValue<CartesianMark.Line>>,
    colors: List<Color>,
    style: ChartStyle,
    progress: Float,
) {
    clipRect(right = geometry.plot.left + (size.width - geometry.plot.left) * progress) {
        lines.forEach { (mark, line) ->
            val points = geometry.spans[mark].mapIndexed { index, span -> geometry.pointOf(index, span.end) }
            val stroke = Stroke(width = line.strokeWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
            drawPath(path = ChartCurves.path(points, line.curve), color = colors[mark], style = stroke)
            if (line.isDotShown) {
                points.forEach { point ->
                    drawCircle(
                        color = colors[mark],
                        radius = style.dotRadius.toPx(),
                        center = point,
                    )
                }
            }
        }
    }
}

internal fun DrawScope.drawAreas(
    geometry: CartesianGeometry,
    areas: List<IndexedValue<CartesianMark.Area>>,
    colors: List<Color>,
    progress: Float,
) {
    val plot = geometry.plot
    clipRect(right = plot.left + (size.width - plot.left) * progress) {
        areas.forEach { (mark, area) ->
            val spans = geometry.spans[mark]
            val tops = spans.mapIndexed { index, span -> geometry.pointOf(index, span.end) }
            val bases = spans.mapIndexed { index, span -> geometry.pointOf(index, span.start) }.reversed()
            val outline = ChartCurves.path(tops, area.curve)
            val fill = ChartCurves.path(tops, area.curve)
            bases.firstOrNull()?.let { base -> fill.lineTo(base.x, base.y) }
            ChartCurves.extend(fill, bases, area.curve)
            fill.close()
            val color = colors[mark]
            drawPath(path = fill, brush = fillOf(color = color, area = area, top = plot.top, bottom = plot.bottom))
            drawPath(path = outline, color = color, style = Stroke(width = area.strokeWidth.toPx()))
        }
    }
}

internal fun DrawScope.drawActiveDots(
    geometry: CartesianGeometry,
    marks: List<Int>,
    colors: List<Color>,
    index: Int,
    style: ChartStyle,
) {
    marks.forEach { mark ->
        val point: Offset = geometry.pointOf(index, geometry.spans[mark][index].end)
        drawCircle(color = colors[mark], radius = style.activeDotRadius.toPx(), center = point)
    }
}

private fun fillOf(color: Color, area: CartesianMark.Area, top: Float, bottom: Float): Brush {
    if (!area.isGradient) return SolidColor(color.copy(alpha = area.fillAlpha))
    return Brush.verticalGradient(
        colors = listOf(color.copy(alpha = GRADIENT_TOP), color.copy(alpha = GRADIENT_BOTTOM)),
        startY = top,
        endY = bottom,
    )
}
