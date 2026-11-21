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

package com.davils.kiro.ui.window.frame

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.takeOrElse
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState

internal fun boundsOf(state: WindowState): DpRect {
    val position = state.position
    val origin = DpOffset(x = position.x.takeOrElse { 0.dp }, y = position.y.takeOrElse { 0.dp })
    return DpRect(origin = origin, size = state.size)
}

internal fun movedBounds(start: DpRect, delta: DpOffset): DpRect = DpRect(
    left = start.left + delta.x,
    top = start.top + delta.y,
    right = start.right + delta.x,
    bottom = start.bottom + delta.y,
)

internal fun resizedBounds(edge: ResizeEdge, start: DpRect, delta: DpOffset, minimum: DpSize): DpRect = DpRect(
    left = moved(edge.isLeft, start.left) { minOf(a = start.left + delta.x, b = start.right - minimum.width) },
    top = moved(edge.isTop, start.top) { minOf(a = start.top + delta.y, b = start.bottom - minimum.height) },
    right = moved(edge.isRight, start.right) { maxOf(a = start.right + delta.x, b = start.left + minimum.width) },
    bottom = moved(edge.isBottom, start.bottom) { maxOf(a = start.bottom + delta.y, b = start.top + minimum.height) },
)

internal fun restoredBounds(pointer: DpOffset, maximized: DpRect, restored: DpSize): DpRect {
    val ratio = (pointer.x - maximized.left) / (maximized.right - maximized.left)
    val origin = DpOffset(x = pointer.x - restored.width * ratio, y = maximized.top)
    return DpRect(origin = origin, size = restored)
}

internal fun toggledPlacement(placement: WindowPlacement): WindowPlacement {
    if (placement == WindowPlacement.Maximized) return WindowPlacement.Floating
    return WindowPlacement.Maximized
}

private inline fun moved(isMoving: Boolean, value: Dp, target: () -> Dp): Dp {
    if (!isMoving) return value
    return target()
}
