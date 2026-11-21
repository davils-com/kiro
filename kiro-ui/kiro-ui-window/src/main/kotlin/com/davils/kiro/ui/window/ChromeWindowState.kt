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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.davils.kiro.ui.window.frame.boundsOf

/**
 * The state of a [ChromeWindow]: the [WindowState] of the operating system and the bounds a window returns to after
 * Kiro maximized it on its own.
 *
 * Kiro maximizes a window on its own where the operating system would cover its task bar, and keeps the bounds of
 * the window from before in [restoreBounds].
 *
 * @param window the position, size and placement of the window.
 * @param restoreBounds the bounds a window maximized by Kiro returns to, or `null` if Kiro did not maximize it.
 * @since 1.0.0
 */
@Stable
public class ChromeWindowState(public val window: WindowState = WindowState(), restoreBounds: DpRect? = null) {
    /**
     * The bounds a window maximized by Kiro returns to, or `null` if Kiro did not maximize it.
     *
     * @since 1.0.0
     */
    public var restoreBounds: DpRect? by mutableStateOf(restoreBounds)
        internal set

    /**
     * Whether Kiro maximized the window on its own.
     *
     * @since 1.0.0
     */
    public val isMaximized: Boolean
        get() = restoreBounds != null

    /**
     * The placement of the window, [WindowPlacement.Maximized] also while Kiro maximized it on its own.
     *
     * @since 1.0.0
     */
    public val placement: WindowPlacement
        get() {
            if (isMaximized) return WindowPlacement.Maximized
            return window.placement
        }

    /**
     * The bounds the window has while it is neither maximized nor in full screen.
     *
     * @since 1.0.0
     */
    public val floatingBounds: DpRect
        get() = restoreBounds ?: boundsOf(window)

    /**
     * The layout of the window to remember, such as when the application quits.
     *
     * @since 1.0.0
     */
    public val layout: WindowLayout
        get() = WindowLayout(bounds = floatingBounds, placement = placement)
}

/**
 * Remembers the state of a [ChromeWindow].
 *
 * @param window the position, size and placement of the window.
 * @param restoreBounds the bounds a window maximized by Kiro returns to, or `null` if Kiro did not maximize it.
 * @return the remembered state.
 * @since 1.0.0
 */
@Composable
public fun rememberChromeWindowState(
    window: WindowState = rememberWindowState(),
    restoreBounds: DpRect? = null,
): ChromeWindowState = remember(window) { ChromeWindowState(window = window, restoreBounds = restoreBounds) }
