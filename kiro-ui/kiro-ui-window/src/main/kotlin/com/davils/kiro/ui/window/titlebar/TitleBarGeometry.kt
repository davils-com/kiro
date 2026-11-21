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

package com.davils.kiro.ui.window.titlebar

internal fun titleOffset(placement: TitlePlacement, width: Int, title: Int, room: IntRange, isRtl: Boolean): Int {
    val left = room.first
    val right = maxOf(a = room.first, b = room.last)
    val last = maxOf(a = left, b = right - title)
    return when (placement) {
        TitlePlacement.WindowCenter -> ((width - title) / 2).coerceIn(minimumValue = left, maximumValue = last)
        TitlePlacement.SlotCenter -> left + (right - left - title) / 2
        TitlePlacement.Start -> startOf(left = left, last = last, isRtl = isRtl)
    }
}

internal fun gapAfter(width: Int, gap: Int): Int {
    if (width == 0) return 0
    return gap
}

private fun startOf(left: Int, last: Int, isRtl: Boolean): Int {
    if (isRtl) return last
    return left
}
