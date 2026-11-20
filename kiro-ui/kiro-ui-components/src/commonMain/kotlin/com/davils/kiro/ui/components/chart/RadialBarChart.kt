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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.davils.kiro.ui.components.chart.frame.ChartFrame
import com.davils.kiro.ui.components.chart.frame.ChartPalette
import com.davils.kiro.ui.components.chart.frame.ChartTarget
import com.davils.kiro.ui.components.chart.frame.chart
import com.davils.kiro.ui.components.chart.polar.PieTarget
import com.davils.kiro.ui.components.chart.polar.PolarMath
import com.davils.kiro.ui.components.chart.polar.RadialGeometry
import com.davils.kiro.ui.components.chart.polar.SeriesTarget
import com.davils.kiro.ui.components.chart.scale.chartNumbers
import com.davils.kiro.ui.components.chart.spec.RadialMark
import com.davils.kiro.ui.components.chart.spec.RadialSpec

@Composable
public fun RadialBarChart(
    data: List<ChartPoint>,
    modifier: Modifier = Modifier,
    startAngle: Float = 0f,
    endAngle: Float = 360f,
    innerRadius: Dp = 30.dp,
    outerRadius: Dp? = null,
    content: RadialBarChartScope.() -> Unit,
) {
    val context = chart()
    val style = context.style
    val spec = RadialSpec(chartNumbers()).apply(content)
    val density = LocalDensity.current
    var size by remember { mutableStateOf(IntSize.Zero) }
    val keys = spec.marks.map { mark -> mark.key }
    val colors = colorsOf(data = data, keys = keys, palette = context.palette)
    val geometry = with(density) {
        RadialGeometry.of(
            size = size.toSize(),
            margin = style.margin.toPx(),
            fraction = style.outerRadius,
            inner = innerRadius.toPx(),
            outer = outerRadius?.toPx(),
            count = data.size,
            padding = style.bandPadding,
            angles = startAngle to endAngle,
        )
    }
    val target = targetOf(data = data, keys = keys, colors = colors, palette = context.palette, geometry = geometry)
    val totals = data.map { point -> keys.sumOf { key -> maxOf(point.valueOf(key), 0.0) } }
    val highest = totals.maxOrNull()?.takeIf { value -> value > 0.0 } ?: 1.0
    ChartFrame(
        modifier = modifier,
        spec = spec,
        target = target,
        onSize = { measured -> size = measured },
    ) { _, progress ->
        data.forEachIndexed { index, point ->
            val values = spec.marks.map { mark -> maxOf(point.valueOf(mark.key), 0.0) * progress / highest }
            val shades = colors.takeIf { keys.size > 1 } ?: List(keys.size) { colors[index] }
            drawRow(
                geometry = geometry,
                index = index,
                marks = spec.marks,
                fractions = values,
                colors = shades,
                track = style.trackColor,
            )
        }
    }
}

private fun DrawScope.drawRow(
    geometry: RadialGeometry,
    index: Int,
    marks: List<RadialMark>,
    fractions: List<Double>,
    colors: List<Color>,
    track: Color,
) {
    val radius = geometry.radiusOf(index)
    val background = marks.firstOrNull { mark -> mark.isBackgroundShown }
    background?.let { mark ->
        drawRing(
            geometry = geometry,
            radius = radius,
            span = 0f to 1f,
            color = track,
            mark = mark,
        )
    }
    val starts = fractions.runningFold(0.0) { start, fraction -> start + fraction }
    marks.forEachIndexed { position, mark ->
        val span = starts[position].toFloat() to starts[position + 1].toFloat()
        drawRing(geometry = geometry, radius = radius, span = span, color = colors[position], mark = mark)
    }
}

private fun DrawScope.drawRing(
    geometry: RadialGeometry,
    radius: Float,
    span: Pair<Float, Float>,
    color: Color,
    mark: RadialMark,
) {
    val start = geometry.degreesOf(span.first)
    val sweep = geometry.degreesOf(span.second) - start
    if (sweep == 0f) return
    val cap = StrokeCap.Round.takeIf { mark.cornerRadius.value > 0f } ?: StrokeCap.Butt
    val bounds = PolarMath.square(geometry.center, radius)
    drawArc(
        color = color,
        startAngle = -start,
        sweepAngle = -sweep,
        useCenter = false,
        topLeft = bounds.topLeft,
        size = bounds.size,
        style = Stroke(width = geometry.thickness, cap = cap),
    )
}

private fun targetOf(
    data: List<ChartPoint>,
    keys: List<ChartKey>,
    colors: List<Color>,
    palette: ChartPalette,
    geometry: RadialGeometry,
): ChartTarget {
    val indexAt = geometry::indexAt
    val anchorAt = geometry::anchorOf
    val single = keys.singleOrNull()
        ?: return SeriesTarget(
            data = data,
            keys = keys,
            colors = colors,
            palette = palette,
            indexAt = indexAt,
            anchorAt = anchorAt,
        )
    return PieTarget(
        data = data,
        key = single,
        colors = colors,
        palette = palette,
        indexAt = indexAt,
        anchorAt = anchorAt,
    )
}

private fun colorsOf(data: List<ChartPoint>, keys: List<ChartKey>, palette: ChartPalette): List<Color> {
    if (keys.size == 1) return data.mapIndexed { index, point -> palette.colorOf(ChartKey(point.label), index) }
    return keys.mapIndexed { index, key -> palette.colorOf(key, index) }
}
