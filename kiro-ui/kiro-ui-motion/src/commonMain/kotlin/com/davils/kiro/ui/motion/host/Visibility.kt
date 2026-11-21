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

package com.davils.kiro.ui.motion.host

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize

internal fun isShown(bounds: Rect, window: IntSize): Boolean {
    val area = windowAreaOf(bounds = bounds, window = window)
    val visible = bounds.intersect(area)
    return visible.width > 0f && visible.height > 0f
}

private fun windowAreaOf(bounds: Rect, window: IntSize): Rect {
    if (window.width <= 0 || window.height <= 0) return bounds
    return Rect(offset = Offset.Zero, size = window.toSize())
}
