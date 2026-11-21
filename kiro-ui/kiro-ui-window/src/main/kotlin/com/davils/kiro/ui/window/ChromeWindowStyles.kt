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
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val MINIMUM_WIDTH_STEPS: Float = 120f
private const val MINIMUM_HEIGHT_STEPS: Float = 80f
private const val ROUNDED_CORNER_STEPS: Float = 3f

/**
 * Derives the style of a [ChromeWindow] from the tokens of a theme for an operating system.
 *
 * Register an implementation under [ChromeWindowStyleKey] in the components of a theme to change how every window
 * looks.
 *
 * @since 1.0.0
 */
public fun interface ChromeWindowStyles {
    /**
     * Returns the style of a window for a theme and an operating system.
     *
     * @param theme the theme whose tokens the style follows.
     * @param platform the operating system whose windows the style resembles.
     * @return the style of the window.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, platform: DesktopPlatform): ChromeWindowStyle

    /**
     * Holds the default [ChromeWindowStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a window, which keeps the frame of the operating system where it can.
         *
         * @since 1.0.0
         */
        public val Default: ChromeWindowStyles = ChromeWindowStyles(::defaultChromeWindowStyle)
    }
}

/**
 * The key of the styles of a [ChromeWindow] in the components of a theme, with [ChromeWindowStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val ChromeWindowStyleKey: StyleKey<ChromeWindowStyles> =
    StyleKey(name = "chrome-window") { _ -> ChromeWindowStyles.Default }

/**
 * Returns the style of a [ChromeWindow] in the current theme for an operating system, with the overrides of its
 * components.
 *
 * @param platform the operating system whose windows the style resembles.
 * @return the style of the window.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun chromeWindowStyle(platform: DesktopPlatform = DesktopPlatform.current()): ChromeWindowStyle =
    ChromeWindowStyleKey.current.resolve(Theme.current, platform)

internal fun defaultChromeWindowStyle(theme: Theme, platform: DesktopPlatform): ChromeWindowStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    return ChromeWindowStyle(
        background = colors.background,
        border = Border(width = theme.borders.thin, color = colors.border),
        cornerRadius = spacing(steps = cornerSteps(platform)),
        resizeThickness = spacing(steps = 1.5f),
        minimumSize = DpSize(
            width = spacing(steps = MINIMUM_WIDTH_STEPS),
            height = spacing(steps = MINIMUM_HEIGHT_STEPS),
        ),
        frame = FramePreference.Native,
    )
}

private fun cornerSteps(platform: DesktopPlatform): Float = when (platform) {
    DesktopPlatform.MacOs -> 0f
    DesktopPlatform.Windows -> 2f
    DesktopPlatform.Linux -> ROUNDED_CORNER_STEPS
    DesktopPlatform.Other -> ROUNDED_CORNER_STEPS
}
