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

package com.davils.kiro.ui.components.menu.intent

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

internal fun isInSafeArea(apex: Offset, target: Rect, point: Offset): Boolean {
    val near = target.left.takeIf { target.center.x >= apex.x } ?: target.right
    val top = Offset(x = near, y = target.top)
    val bottom = Offset(x = near, y = target.bottom)
    val first = sideOf(point = point, from = apex, to = top)
    val second = sideOf(point = point, from = top, to = bottom)
    val third = sideOf(point = point, from = bottom, to = apex)
    val hasNegative = first < 0f || second < 0f || third < 0f
    val hasPositive = first > 0f || second > 0f || third > 0f
    return !(hasNegative && hasPositive)
}

private fun sideOf(point: Offset, from: Offset, to: Offset): Float =
    (point.x - to.x) * (from.y - to.y) - (from.x - to.x) * (point.y - to.y)
