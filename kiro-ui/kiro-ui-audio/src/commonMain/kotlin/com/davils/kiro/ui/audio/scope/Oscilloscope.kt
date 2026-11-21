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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.audio.signal.AudioScope
import com.davils.kiro.ui.audio.signal.Samples

/**
 * An oscilloscope as editing programs show it: the latest samples of each channel of a stereo signal as a glowing
 * trace over a grid, either over each other or the left channel above the right.
 *
 * @param scope the latest samples of the two channels.
 * @param modifier the modifier of the oscilloscope.
 * @param style the style of the oscilloscope.
 * @since 1.0.0
 */
@Composable
public fun Oscilloscope(
    scope: AudioScope,
    modifier: Modifier = Modifier,
    style: OscilloscopeStyle = oscilloscopeStyle(),
) {
    Canvas(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(style.height)
                .semantics { contentDescription = style.description },
        ),
    ) {
        drawGrid(style)
        when (style.layout) {
            ScopeLayout.Overlaid -> {
                val middle = size.height / 2f
                drawCenter(middle = middle, style = style)
                drawChannel(
                    samples = scope.right,
                    middle = middle,
                    half = middle,
                    color = style.rightColor,
                    style = style,
                )
                drawChannel(
                    samples = scope.left,
                    middle = middle,
                    half = middle,
                    color = style.leftColor,
                    style = style,
                )
            }

            ScopeLayout.Split -> {
                val half = size.height / 4f
                drawCenter(middle = half, style = style)
                drawCenter(middle = half * 3f, style = style)
                drawChannel(samples = scope.left, middle = half, half = half, color = style.leftColor, style = style)
                drawChannel(
                    samples = scope.right,
                    middle = half * 3f,
                    half = half,
                    color = style.rightColor,
                    style = style,
                )
            }
        }
    }
}

private fun DrawScope.drawGrid(style: OscilloscopeStyle) {
    val width = style.gridWidth.toPx()
    for (column in 0..style.columns) {
        val x = size.width * column / style.columns.coerceAtLeast(minimumValue = 1)
        drawLine(
            color = style.gridColor,
            start = Offset(x = x, y = 0f),
            end = Offset(x = x, y = size.height),
            strokeWidth = width,
        )
    }
    for (row in 0..style.rows) {
        val y = size.height * row / style.rows.coerceAtLeast(minimumValue = 1)
        drawLine(
            color = style.gridColor,
            start = Offset(x = 0f, y = y),
            end = Offset(x = size.width, y = y),
            strokeWidth = width,
        )
    }
}

private fun DrawScope.drawCenter(middle: Float, style: OscilloscopeStyle) {
    drawLine(
        color = style.centerColor,
        start = Offset(x = 0f, y = middle),
        end = Offset(x = size.width, y = middle),
        strokeWidth = style.gridWidth.toPx(),
    )
}

private fun DrawScope.drawChannel(
    samples: Samples,
    middle: Float,
    half: Float,
    color: Color,
    style: OscilloscopeStyle,
) {
    if (samples.size < 2) return
    val trace = Path()
    val step = size.width / (samples.size - 1)
    val reach = half * style.gain
    for (index in 0 until samples.size) {
        val y = middle - samples[index].coerceIn(minimumValue = -1f, maximumValue = 1f) * reach
        if (index == 0) trace.moveTo(x = 0f, y = y)
        if (index > 0) trace.lineTo(x = index * step, y = y)
    }
    val glow = Stroke(width = style.glowWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    drawPath(path = trace, color = color.copy(alpha = style.glowAlpha), style = glow)
    drawPath(
        path = trace,
        color = color,
        style = Stroke(width = style.traceWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round),
    )
}
