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

package com.davils.kiro.ui.components.primitive.surface

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.style.Border

public fun Modifier.surface(shape: Shape, color: Color, border: Border? = null, ring: Border? = null): Modifier =
    drawBehind {
        ring?.let { outer -> drawRing(shape = shape, ring = outer) }
        val edge = border?.takeIf { visible -> visible.width > 0.dp }
        val borderWidth = edge?.run { width.toPx() } ?: 0f
        drawInset(shape = shape, inset = borderWidth, color = color, stroke = null)
        edge?.let { visible ->
            drawInset(
                shape = shape,
                inset = borderWidth / 2,
                color = visible.color,
                stroke = Stroke(borderWidth),
            )
        }
    }

private fun DrawScope.drawRing(shape: Shape, ring: Border) {
    val width = ring.width.toPx()
    val outerSize = Size(size.width + width * 2, size.height + width * 2)
    val outer = Path()
    outer.addOutline(expanded(shape, width).createOutline(outerSize, layoutDirection, this))
    outer.translate(Offset(-width, -width))
    val inner = Path()
    inner.addOutline(shape.createOutline(size, layoutDirection, this))
    val band = Path()
    band.op(outer, inner, PathOperation.Difference)
    drawPath(path = band, color = ring.color)
}

private fun DrawScope.expanded(shape: Shape, width: Float): Shape {
    if (shape !is RoundedCornerShape) return shape
    return RoundedCornerShape(
        topStart = CornerSize(shape.topStart.toPx(size, this) + width),
        topEnd = CornerSize(shape.topEnd.toPx(size, this) + width),
        bottomEnd = CornerSize(shape.bottomEnd.toPx(size, this) + width),
        bottomStart = CornerSize(shape.bottomStart.toPx(size, this) + width),
    )
}

private fun DrawScope.drawInset(shape: Shape, inset: Float, color: Color, stroke: Stroke?) {
    val inner = Size(size.width - inset * 2, size.height - inset * 2)
    val outline = shape.createOutline(inner, layoutDirection, this)
    translate(left = inset, top = inset) {
        stroke?.let { style -> drawOutline(outline = outline, color = color, style = style) }
            ?: drawOutline(outline = outline, color = color)
    }
}
