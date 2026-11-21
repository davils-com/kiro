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

package com.davils.kiro.ui.window.screen

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width

/**
 * Returns where a window opens again: at [saved] if enough of it is still visible on the work area of a screen, or
 * centered on the primary screen in the size [fallback] if its screen is gone or it would only show a sliver.
 *
 * Use it to restore the bounds of a window that an application remembered, so that a window never opens on a screen
 * that is no longer attached.
 *
 * @param saved the bounds the window had when it was remembered.
 * @param screens the screens attached now, such as the ones of [ScreenApi.System].
 * @param fallback the size of the window when it has to move to the primary screen.
 * @param minimumVisible the size of [saved] that must lie on a work area for the window to stay where it was.
 * @return the bounds the window opens in.
 * @since 1.0.0
 */
public fun visibleBoundsOf(saved: DpRect, screens: List<Screen>, fallback: DpSize, minimumVisible: DpSize): DpRect {
    val primary = screens.firstOrNull { screen -> screen.isPrimary }
    val isVisible = screens.any { screen ->
        isVisibleOn(
            bounds = saved,
            area = screen.workArea,
            minimum = minimumVisible,
        )
    }
    if (primary == null || isVisible) return saved
    return centeredOn(area = primary.workArea, size = fallback)
}

internal fun workAreaOf(screen: DpRect, insets: ScreenInsets): DpRect = DpRect(
    left = screen.left + insets.left,
    top = screen.top + insets.top,
    right = screen.right - insets.right,
    bottom = screen.bottom - insets.bottom,
)

internal fun centeredOn(area: DpRect, size: DpSize): DpRect {
    val width = minOf(a = size.width, b = area.width)
    val height = minOf(a = size.height, b = area.height)
    val origin = DpOffset(x = area.left + (area.width - width) / 2, y = area.top + (area.height - height) / 2)
    return DpRect(origin = origin, size = DpSize(width = width, height = height))
}

private fun isVisibleOn(bounds: DpRect, area: DpRect, minimum: DpSize): Boolean {
    val width = minOf(a = bounds.right, b = area.right) - maxOf(a = bounds.left, b = area.left)
    val height = minOf(a = bounds.bottom, b = area.bottom) - maxOf(a = bounds.top, b = area.top)
    val isWide = width >= minOf(a = minimum.width, b = bounds.width)
    val isHigh = height >= minOf(a = minimum.height, b = bounds.height)
    return isWide && isHigh
}
