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

package com.davils.kiro.ui.window.controls

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

internal fun DrawScope.drawGlyph(button: WindowButton, isMaximized: Boolean, color: Color, width: Float, shift: Float) {
    when (button) {
        WindowButton.Minimize -> drawMinimize(color = color, width = width)
        WindowButton.Maximize -> drawMaximize(isMaximized = isMaximized, color = color, width = width, fraction = shift)
        WindowButton.Close -> drawClose(color = color, width = width)
    }
}

private fun DrawScope.drawMinimize(color: Color, width: Float) {
    val middle = size.height / 2
    drawLine(
        color = color,
        start = Offset(x = 0f, y = middle),
        end = Offset(x = size.width, y = middle),
        strokeWidth = width,
    )
}

private fun DrawScope.drawMaximize(isMaximized: Boolean, color: Color, width: Float, fraction: Float) {
    val stroke = Stroke(width = width)
    if (!isMaximized) {
        drawRect(color = color, style = stroke)
        return
    }
    val shift = size.width * fraction
    val inner = size.copy(width = size.width - shift, height = size.height - shift)
    drawRect(color = color, topLeft = Offset(x = 0f, y = shift), size = inner, style = stroke)
    drawLine(color = color, start = Offset(x = shift, y = shift), end = Offset(x = shift, y = 0f), strokeWidth = width)
    drawLine(
        color = color,
        start = Offset(x = shift, y = 0f),
        end = Offset(x = size.width, y = 0f),
        strokeWidth = width,
    )
    drawLine(
        color = color,
        start = Offset(x = size.width, y = 0f),
        end = Offset(x = size.width, y = size.height - shift),
        strokeWidth = width,
    )
    drawLine(
        color = color,
        start = Offset(x = size.width, y = size.height - shift),
        end = Offset(x = size.width - shift, y = size.height - shift),
        strokeWidth = width,
    )
}

private fun DrawScope.drawClose(color: Color, width: Float) {
    drawLine(color = color, start = Offset.Zero, end = Offset(x = size.width, y = size.height), strokeWidth = width)
    drawLine(
        color = color,
        start = Offset(x = size.width, y = 0f),
        end = Offset(x = 0f, y = size.height),
        strokeWidth = width,
    )
}
