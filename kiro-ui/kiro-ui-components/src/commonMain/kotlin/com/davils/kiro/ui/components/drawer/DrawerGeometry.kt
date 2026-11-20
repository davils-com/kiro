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

package com.davils.kiro.ui.components.drawer

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.overlay.Physical

internal val LocalDrawerPhysical = staticCompositionLocalOf { Physical.Bottom }

internal fun shapeOf(physical: Physical, radius: Dp): Shape {
    val round = CornerSize(radius)
    val square = CornerSize(0.dp)
    return when (physical) {
        Physical.Bottom -> RoundedCornerShape(
            topStart = round,
            topEnd = round,
            bottomEnd = square,
            bottomStart = square,
        )

        Physical.Top -> RoundedCornerShape(topStart = square, topEnd = square, bottomEnd = round, bottomStart = round)

        Physical.Left -> RoundedCornerShape(topStart = square, topEnd = round, bottomEnd = round, bottomStart = square)

        Physical.Right -> RoundedCornerShape(topStart = round, topEnd = square, bottomEnd = square, bottomStart = round)
    }
}

internal fun orientationOf(physical: Physical): Orientation = when (physical) {
    Physical.Top -> Orientation.Vertical
    Physical.Bottom -> Orientation.Vertical
    Physical.Left -> Orientation.Horizontal
    Physical.Right -> Orientation.Horizontal
}

internal fun dismissSignOf(physical: Physical): Float = when (physical) {
    Physical.Top -> -1f
    Physical.Bottom -> 1f
    Physical.Left -> -1f
    Physical.Right -> 1f
}

internal fun isVertical(physical: Physical): Boolean = orientationOf(physical) == Orientation.Vertical

internal fun isHandleFirst(physical: Physical): Boolean = physical == Physical.Bottom || physical == Physical.Right
