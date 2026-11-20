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

package com.davils.kiro.ui.components.toast

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Density

@Immutable
internal data class ToastStack(val offsets: Map<ToastId, Int>, val height: Int)

internal fun stackOf(
    toasts: List<Toast>,
    heights: Map<ToastId, Int>,
    isExpanded: Boolean,
    style: ToastStyle,
    density: Density,
): ToastStack {
    val expandedGap = with(density) { style.expandedGap.roundToPx() }
    val stackGap = with(density) { style.stackGap.roundToPx() }
    val sizes = toasts.map { toast -> heights[toast.id] ?: 0 }
    val expanded = sizes.runningFold(0) { offset, size -> offset + size + expandedGap }
    val pairs = toasts.mapIndexed { depth, toast ->
        toast.id to
            (expanded[depth].takeIf { isExpanded } ?: depth * stackGap)
    }
    val offsets = pairs.toMap()
    val front = sizes.firstOrNull() ?: 0
    val collapsedHeight = front + stackGap * maxOf(a = toasts.size - 1, b = 0)
    val expandedHeight = maxOf(a = expanded.last() - expandedGap, b = 0)
    return ToastStack(offsets = offsets, height = expandedHeight.takeIf { isExpanded } ?: collapsedHeight)
}
