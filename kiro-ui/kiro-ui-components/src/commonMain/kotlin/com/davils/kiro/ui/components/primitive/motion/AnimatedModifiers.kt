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

package com.davils.kiro.ui.components.primitive.motion

import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp

internal fun Modifier.animatedWidth(width: State<Dp>): Modifier = layout { measurable, constraints ->
    val pixels = width.value.roundToPx().coerceIn(constraints.minWidth, constraints.maxWidth)
    val placeable = measurable.measure(
        Constraints.fixedWidth(pixels).copy(minHeight = constraints.minHeight, maxHeight = constraints.maxHeight),
    )
    layout(placeable.width, placeable.height) { placeable.place(x = 0, y = 0) }
}

internal fun Modifier.animatedHeight(height: State<Dp>): Modifier = layout { measurable, constraints ->
    val pixels = height.value.roundToPx().coerceIn(constraints.minHeight, constraints.maxHeight)
    val placeable = measurable.measure(
        Constraints.fixedHeight(pixels).copy(minWidth = constraints.minWidth, maxWidth = constraints.maxWidth),
    )
    layout(placeable.width, placeable.height) { placeable.place(x = 0, y = 0) }
}

internal fun Modifier.fadeBy(alpha: State<Float>): Modifier = graphicsLayer { this.alpha = alpha.value }

internal fun Modifier.turnBy(degrees: State<Float>): Modifier = graphicsLayer { rotationZ = degrees.value }
