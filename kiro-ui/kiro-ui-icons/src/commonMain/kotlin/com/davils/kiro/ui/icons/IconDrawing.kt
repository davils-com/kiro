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

package com.davils.kiro.ui.icons

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp

internal fun DrawScope.drawIcon(geometry: IconGeometry, style: IconStyle) {
    val factor = size.minDimension / geometry.viewportSize
    val stroke = Stroke(width = strokeWidthOf(style, factor), cap = StrokeCap.Round, join = StrokeJoin.Round)
    scale(scale = factor, pivot = Offset.Zero) {
        geometry.shapes.forEach { shape ->
            if (shape.isFilled) drawPath(path = shape.path, color = style.color, style = Fill)
            drawPath(path = shape.path, color = style.color, style = stroke)
        }
    }
}

private fun DrawScope.strokeWidthOf(style: IconStyle, factor: Float): Float {
    if (style.isNonScalingStroke != true) return style.strokeWidth
    return style.strokeWidth.dp.toPx() / factor
}
