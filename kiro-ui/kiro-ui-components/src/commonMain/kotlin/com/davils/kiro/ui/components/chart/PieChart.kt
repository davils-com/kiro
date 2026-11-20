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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.davils.kiro.ui.components.chart.frame.ChartFrame
import com.davils.kiro.ui.components.chart.frame.chart
import com.davils.kiro.ui.components.chart.frame.drawLabel
import com.davils.kiro.ui.components.chart.polar.PieGeometry
import com.davils.kiro.ui.components.chart.polar.PieTarget
import com.davils.kiro.ui.components.chart.polar.PolarMath
import com.davils.kiro.ui.components.chart.scale.chartNumbers
import com.davils.kiro.ui.components.chart.spec.ChartSpec

private const val HALF: Float = 0.5f

@Composable
public fun PieChart(
    data: List<ChartPoint>,
    key: ChartKey,
    modifier: Modifier = Modifier,
    innerRadius: Dp = 0.dp,
    paddingAngle: Float = 0f,
    isLabelShown: Boolean = false,
    content: ChartScope.() -> Unit = {},
) {
    val context = chart()
    val style = context.style
    val numbers = chartNumbers()
    val spec = ChartSpec(numbers).apply(content)
    val density = LocalDensity.current
    val measurer = rememberTextMeasurer()
    var size by remember { mutableStateOf(IntSize.Zero) }
    val colors = data.mapIndexed { index, point -> context.palette.colorOf(ChartKey(point.label), index) }
    val labelSpace = style.labelOffset.takeIf { isLabelShown } ?: 0.dp
    val geometry = with(density) {
        PieGeometry.of(
            size = size.toSize(),
            margin = (style.margin + labelSpace).toPx(),
            fraction = style.outerRadius,
            inner = innerRadius.toPx(),
            padding = paddingAngle,
            values = data.map { point -> point.valueOf(key) },
        )
    }
    val target = PieTarget(
        data = data,
        key = key,
        colors = colors,
        palette = context.palette,
        indexAt = geometry::indexAt,
        anchorAt = geometry::anchorOf,
    )
    val labels = data.map { point -> numbers(point.valueOf(key)) }.takeIf { isLabelShown }
    ChartFrame(
        modifier = modifier,
        spec = spec,
        target = target,
        onSize = { measured -> size = measured },
    ) { _, progress ->
        drawSlices(geometry = geometry, colors = colors, progress = progress)
        labels?.let { values ->
            drawPieLabels(
                geometry = geometry,
                values = values,
                colors = colors,
                measurer = measurer,
                style = style,
            )
        }
    }
}

private fun DrawScope.drawSlices(geometry: PieGeometry, colors: List<Color>, progress: Float) {
    geometry.slices.forEachIndexed { index, slice ->
        val path = PolarMath.sector(
            center = geometry.center,
            inner = geometry.inner,
            outer = geometry.outer,
            start = slice.start * progress,
            sweep = slice.sweep * progress,
        )
        drawPath(path = path, color = colors[index])
    }
}

private fun DrawScope.drawPieLabels(
    geometry: PieGeometry,
    values: List<String>,
    colors: List<Color>,
    measurer: TextMeasurer,
    style: ChartStyle,
) {
    val reach = geometry.outer + style.labelOffset.toPx()
    geometry.slices.forEachIndexed { index, slice ->
        val start = PolarMath.pointOf(center = geometry.center, radius = geometry.outer, degrees = slice.middle)
        val end = PolarMath.pointOf(center = geometry.center, radius = reach, degrees = slice.middle)
        drawLine(color = colors[index], start = start, end = end)
        val label = measurer.measure(values[index], style.textStyle)
        val isRight = end.x >= geometry.center.x
        drawLabel(
            label = label,
            anchor = end,
            color = style.labelColor,
            alignX = 0f.takeIf { isRight } ?: 1f,
            alignY = HALF,
        )
    }
}
