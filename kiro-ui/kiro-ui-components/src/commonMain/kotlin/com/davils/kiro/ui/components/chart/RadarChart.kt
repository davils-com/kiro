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

package com.davils.kiro.ui.components.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize
import com.davils.kiro.ui.components.chart.frame.ChartFrame
import com.davils.kiro.ui.components.chart.frame.chart
import com.davils.kiro.ui.components.chart.frame.drawLabel
import com.davils.kiro.ui.components.chart.polar.PolarMath
import com.davils.kiro.ui.components.chart.polar.RadarGeometry
import com.davils.kiro.ui.components.chart.polar.SeriesTarget
import com.davils.kiro.ui.components.chart.scale.ChartTicks
import com.davils.kiro.ui.components.chart.scale.chartNumbers
import com.davils.kiro.ui.components.chart.spec.PolarGridSpec
import com.davils.kiro.ui.components.chart.spec.RadarMark
import com.davils.kiro.ui.components.chart.spec.RadarSpec
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val HALF_TURN_DEGREES: Double = 180.0

private const val TICKS: Int = 5
private const val HALF: Float = 0.5f
private const val EDGE: Float = 0.01f

/**
 * Draws a radar chart: one spoke per point of [data], clockwise from the top, and one filled polygon per series
 * declared in [content] whose corners lie on the spokes at the values of the series.
 *
 * The chart takes its colors, labels and style from the [ChartContainer] around it.
 *
 * @param data the points of the chart, one per spoke, in order.
 * @param modifier the modifier of the chart.
 * @param content declares the polygons, the grid, the labels of the spokes, the tooltip and the legend of the chart.
 * @throws IllegalArgumentException if the chart is not placed in a [ChartContainer].
 * @since 1.0.0
 */
@Composable
public fun RadarChart(data: List<ChartPoint>, modifier: Modifier = Modifier, content: RadarChartScope.() -> Unit) {
    val context = chart()
    val style = context.style
    val spec = RadarSpec(chartNumbers()).apply(content)
    val density = LocalDensity.current
    val measurer = rememberTextMeasurer()
    var size by remember { mutableStateOf(IntSize.Zero) }
    val keys = spec.marks.map { mark -> mark.key }
    val colors = keys.mapIndexed { index, key -> context.palette.colorOf(key, index) }
    val highest = data.flatMap { point -> keys.map { key -> point.valueOf(key) } }.maxOrNull() ?: 0.0
    val geometry = with(density) {
        RadarGeometry.of(
            size = size.toSize(),
            margin = style.margin.toPx(),
            fraction = style.outerRadius,
            count = data.size,
            ticks = ChartTicks.nice(min = 0.0, max = highest, count = TICKS),
        )
    }
    val target = SeriesTarget(
        data = data,
        keys = keys,
        colors = colors,
        palette = context.palette,
        indexAt = geometry::indexAt,
        anchorAt = geometry::anchorOf,
    )
    val labels = spec.angleFormat?.let { format -> data.map { point -> format(point.label) } }
    ChartFrame(
        modifier = modifier,
        spec = spec,
        target = target,
        onSize = { measured -> size = measured },
    ) { active, progress ->
        spec.grid?.let { grid -> drawRadarGrid(geometry = geometry, grid = grid, color = style.polarGridColor) }
        labels?.let { texts ->
            drawAngleLabels(
                geometry = geometry,
                labels = texts,
                measurer = measurer,
                style = style,
            )
        }
        spec.marks.forEachIndexed { mark, radar ->
            val values = data.map { point -> point.valueOf(radar.key) * progress }
            drawRadar(
                geometry = geometry,
                radar = radar,
                values = values,
                color = colors[mark],
                style = style,
                active = active,
            )
        }
    }
}

private fun DrawScope.drawRadar(
    geometry: RadarGeometry,
    radar: RadarMark,
    values: List<Double>,
    color: Color,
    style: ChartStyle,
    active: Int?,
) {
    val points = values.mapIndexed { index, value -> geometry.pointOf(index, value) }
    val path = polygonOf(points)
    drawPath(path = path, color = color.copy(alpha = radar.fillAlpha))
    if (radar.strokeWidth.value > 0f) {
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = radar.strokeWidth.toPx()),
        )
    }
    if (radar.isDotShown) {
        points.forEach { point ->
            drawCircle(
                color = color,
                radius = style.dotRadius.toPx(),
                center = point,
            )
        }
    }
    active?.let { index -> drawCircle(color = color, radius = style.activeDotRadius.toPx(), center = points[index]) }
}

private fun DrawScope.drawRadarGrid(geometry: RadarGeometry, grid: PolarGridSpec, color: Color) {
    val spokes = List(geometry.count) { index -> index }
    geometry.ticks.filter { tick -> tick > 0.0 }.forEach { tick ->
        val ring = spokes.map { index -> geometry.pointOf(index, tick) }
        val radius = (geometry.pointOf(0, tick) - geometry.center).getDistance()
        val outline = Stroke(width = 1f)
        if (grid.isCircle) drawCircle(color = color, radius = radius, center = geometry.center, style = outline)
        if (!grid.isCircle) drawPath(path = polygonOf(ring), color = color, style = outline)
    }
    if (!grid.isSpokeShown) return
    spokes.forEach { index -> drawLine(color = color, start = geometry.center, end = geometry.anchorOf(index)) }
}

private fun DrawScope.drawAngleLabels(
    geometry: RadarGeometry,
    labels: List<String>,
    measurer: TextMeasurer,
    style: ChartStyle,
) {
    val reach = geometry.radius + style.tickMargin.toPx()
    labels.forEachIndexed { index, text ->
        val degrees = geometry.degreesOf(index)
        val radians = degrees.toDouble() * PI / HALF_TURN_DEGREES
        drawLabel(
            label = measurer.measure(text, style.textStyle),
            anchor = PolarMath.pointOf(center = geometry.center, radius = reach, degrees = degrees),
            color = style.tickColor,
            alignX = alignOf(cos(radians).toFloat()),
            alignY = 1f - alignOf(sin(radians).toFloat()),
        )
    }
}

private fun alignOf(direction: Float): Float {
    if (direction > EDGE) return 0f
    return 1f.takeIf { direction < -EDGE } ?: HALF
}

private fun polygonOf(points: List<Offset>): Path {
    val path = Path()
    points.firstOrNull()?.let { first -> path.moveTo(x = first.x, y = first.y) }
    points.drop(1).forEach { point -> path.lineTo(x = point.x, y = point.y) }
    path.close()
    return path
}
