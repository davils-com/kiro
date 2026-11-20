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

package com.davils.kiro.ui.components.sheet

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.primitive.overlay.Physical
import com.davils.kiro.ui.core.style.Border
import kotlin.math.roundToInt

internal fun alignmentOf(physical: Physical): Alignment = when (physical) {
    Physical.Top -> Alignment.TopCenter
    Physical.Bottom -> Alignment.BottomCenter
    Physical.Left -> Alignment.CenterStart
    Physical.Right -> Alignment.CenterEnd
}

internal fun Modifier.edgeSized(physical: Physical, fraction: Float, minSize: Dp): Modifier = layout {
        measurable,
        constraints,
    ->
    val isAcross = physical == Physical.Top || physical == Physical.Bottom
    val extent = constraints.maxHeight.takeIf { isAcross } ?: constraints.maxWidth
    val size = maxOf((extent * fraction).roundToInt(), minSize.roundToPx()).coerceAtMost(extent)
    val fixed = Constraints.fixed(width = constraints.maxWidth, height = size).takeIf { isAcross }
        ?: Constraints.fixed(width = size, height = constraints.maxHeight)
    val placeable = measurable.measure(fixed)
    layout(placeable.width, placeable.height) { placeable.place(x = 0, y = 0) }
}

internal fun Modifier.innerEdge(physical: Physical, border: Border): Modifier = drawBehind {
    val stroke = border.width.toPx()
    val half = stroke / 2
    val (start, end) = when (physical) {
        Physical.Top -> Offset(0f, size.height - half) to Offset(size.width, size.height - half)
        Physical.Bottom -> Offset(0f, half) to Offset(size.width, half)
        Physical.Left -> Offset(size.width - half, 0f) to Offset(size.width - half, size.height)
        Physical.Right -> Offset(half, 0f) to Offset(half, size.height)
    }
    drawLine(color = border.color, start = start, end = end, strokeWidth = stroke)
}
