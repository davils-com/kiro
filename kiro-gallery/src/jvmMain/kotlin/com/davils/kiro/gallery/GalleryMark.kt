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

package com.davils.kiro.gallery

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter

private const val MARK_SIZE: Float = 32f
private const val RING: Float = 0.12f
private const val CORE: Float = 0.18f

internal class GalleryMark(private val isDark: Boolean) : Painter() {
    override val intrinsicSize: Size = Size(width = MARK_SIZE, height = MARK_SIZE)

    override fun DrawScope.onDraw() {
        val color = colorOf(isDark)
        val stroke = size.minDimension * RING
        val radius = (size.minDimension - stroke) / 2
        drawCircle(color = color, radius = radius, style = Stroke(width = stroke))
        drawCircle(
            color = color,
            radius = size.minDimension * CORE,
            center = Offset(x = size.width / 2, y = size.height / 2),
        )
    }
}

private fun colorOf(isDark: Boolean): Color {
    if (isDark) return Color.White
    return Color.Black
}
