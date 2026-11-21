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

package com.davils.kiro.ui.audio.scope

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import com.davils.kiro.ui.audio.signal.AudioScope
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sqrt

private val diagonal: Float = 1f / sqrt(2f)

/**
 * A goniometer, the vectorscope of audio, as editing programs show it beside the meters: every pair of samples of a
 * stereo signal becomes a point, mid up and side across, so that a mono signal draws a vertical line, a wide signal a
 * round cloud and a signal with phase problems a horizontal smear. Axes mark left, right, mid and side.
 *
 * @param scope the latest samples of the two channels.
 * @param modifier the modifier of the goniometer.
 * @param style the style of the goniometer.
 * @since 1.0.0
 */
@Composable
public fun Goniometer(scope: AudioScope, modifier: Modifier = Modifier, style: GoniometerStyle = goniometerStyle()) {
    val measurer = rememberTextMeasurer()
    val labels = remember(style, measurer) {
        val look = style.labelStyle.copy(color = style.labelColor)
        listOf(
            style.mid,
            style.side,
            style.left,
            style.right,
        ).map { text -> measurer.measure(text = text, style = look) }
    }
    Canvas(
        modifier = modifier.then(
            Modifier
                .size(style.size)
                .semantics { contentDescription = style.description },
        ),
    ) {
        val margin = labels.maxOf { label ->
            maxOf(
                a = label.size.width,
                b = label.size.height,
            )
        } +
            style.labelGap.toPx()
        val radius = (min(a = size.width, b = size.height) / 2f - margin).coerceAtLeast(minimumValue = 0f)
        drawGrid(radius = radius, style = style)
        drawLabels(labels = labels, radius = radius + style.labelGap.toPx())
        drawTrace(scope = scope, radius = radius, style = style)
    }
}

private fun DrawScope.drawGrid(radius: Float, style: GoniometerStyle) {
    val width = style.gridWidth.toPx()
    when (style.grid) {
        ScopeGrid.Round -> {
            drawCircle(color = style.gridColor, radius = radius, center = center, style = Stroke(width))
        }

        ScopeGrid.Square -> {
            val diamond = Path()
            diamond.moveTo(x = center.x, y = center.y - radius)
            diamond.lineTo(x = center.x + radius, y = center.y)
            diamond.lineTo(x = center.x, y = center.y + radius)
            diamond.lineTo(x = center.x - radius, y = center.y)
            diamond.close()
            drawPath(path = diamond, color = style.gridColor, style = Stroke(width))
        }
    }
    val reach = radius * diagonal
    val axes = listOf(
        Offset(x = 0f, y = radius),
        Offset(x = radius, y = 0f),
        Offset(x = reach, y = reach),
        Offset(x = -reach, y = reach),
    )
    axes.forEach { axis ->
        drawLine(
            color = style.axisColor,
            start = center - axis,
            end = center + axis,
            strokeWidth = width,
        )
    }
}

private fun DrawScope.drawLabels(labels: List<TextLayoutResult>, radius: Float) {
    val mid = labels[0]
    val side = labels[1]
    val left = labels[2]
    val right = labels[3]
    val reach = radius * diagonal
    drawText(mid, topLeft = center + Offset(x = -mid.size.width / 2f, y = -radius - mid.size.height))
    drawText(side, topLeft = center + Offset(x = radius, y = -side.size.height / 2f))
    drawText(left, topLeft = center + Offset(x = -reach - left.size.width, y = -reach - left.size.height))
    drawText(right, topLeft = center + Offset(x = reach, y = -reach - right.size.height))
}

private fun DrawScope.drawTrace(scope: AudioScope, radius: Float, style: GoniometerStyle) {
    val reach = radius * style.gain
    val points = (0 until scope.left.size).map { index ->
        val left = scope.left[index]
        val right = scope.right[index]
        val side = (right - left) * diagonal
        val mid = (left + right) * diagonal
        val point = Offset(x = side * reach, y = -mid * reach)
        val length = point.getDistance().takeIf { style.grid == ScopeGrid.Round } ?: abs(point.x) + abs(point.y)
        val scale = (radius / length).takeIf { length > radius } ?: 1f
        center + point * scale
    }
    val color = style.traceColor.copy(alpha = style.traceAlpha)
    when (style.trace) {
        ScopeTrace.Dots -> drawPoints(
            points = points,
            pointMode = PointMode.Points,
            color = color,
            strokeWidth = style.dotSize.toPx(),
            cap = StrokeCap.Round,
        )

        ScopeTrace.Lines -> drawPoints(
            points = points,
            pointMode = PointMode.Polygon,
            color = color,
            strokeWidth = style.lineWidth.toPx(),
            cap = StrokeCap.Round,
        )
    }
}
