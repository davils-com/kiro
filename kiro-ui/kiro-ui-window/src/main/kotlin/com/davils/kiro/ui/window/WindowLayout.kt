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

package com.davils.kiro.ui.window

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.size
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.davils.kiro.ui.window.screen.Screen
import com.davils.kiro.ui.window.screen.visibleBoundsOf

/**
 * Where a window was and how it was placed, for an application to remember between two runs.
 *
 * Read it from [ChromeWindowState.layout] and turn it back into a state with [chromeWindowStateOf].
 *
 * @property bounds the bounds of the window while it is neither maximized nor in full screen.
 * @property placement whether the window was floating, maximized or in full screen.
 * @since 1.0.0
 */
@Immutable
public class WindowLayout(
    public val bounds: DpRect,
    public val placement: WindowPlacement,
) {
    /**
     * Returns a copy of this [WindowLayout] with the given values replaced and every other value kept.
     *
     * @param bounds the new value of [bounds].
     * @param placement the new value of [placement].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(bounds: DpRect = this.bounds, placement: WindowPlacement = this.placement): WindowLayout =
        WindowLayout(
            bounds = bounds,
            placement = placement,
        )

    override fun equals(other: Any?): Boolean = other is WindowLayout && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "WindowLayout(" +
        "bounds=$bounds, " +
        "placement=$placement)"

    private fun values(): List<Any?> = listOf(
        bounds,
        placement,
    )
}

/**
 * Returns the state of a window that opens with a remembered [layout], moved onto the primary screen if the screen it
 * was on is gone or it would only show a sliver.
 *
 * A window that was maximized opens maximized; where Kiro maximizes windows on its own, it does so again once the
 * window is open and keeps [WindowLayout.bounds] to restore.
 *
 * @param layout the remembered layout.
 * @param screens the screens attached now, such as the ones of [com.davils.kiro.ui.window.screen.ScreenApi.System].
 * @param minimumVisible the size of the remembered bounds that must lie on a screen for the window to stay there.
 * @return the state of the window.
 * @since 1.0.0
 */
public fun chromeWindowStateOf(layout: WindowLayout, screens: List<Screen>, minimumVisible: DpSize): ChromeWindowState {
    val bounds = visibleBoundsOf(
        saved = layout.bounds,
        screens = screens,
        fallback = layout.bounds.size,
        minimumVisible = minimumVisible,
    )
    val window = WindowState(
        placement = layout.placement,
        position = WindowPosition(x = bounds.left, y = bounds.top),
        size = bounds.size,
    )
    return ChromeWindowState(window = window)
}
