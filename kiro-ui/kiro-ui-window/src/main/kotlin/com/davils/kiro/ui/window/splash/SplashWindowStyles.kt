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

package com.davils.kiro.ui.window.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the style of a [SplashWindow] from the tokens of a theme.
 *
 * Register an implementation under [SplashWindowStyleKey] in the components of a theme to change how every splash
 * window looks.
 *
 * @since 1.0.0
 */
public fun interface SplashWindowStyles {
    /**
     * Returns the style of a splash window for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the splash window.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SplashWindowStyle

    /**
     * Holds the default [SplashWindowStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a splash window: rounded, with a thin border.
         *
         * @since 1.0.0
         */
        public val Default: SplashWindowStyles = SplashWindowStyles(::defaultSplashWindowStyle)
    }
}

/**
 * The key of the styles of a [SplashWindow] in the components of a theme, with [SplashWindowStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val SplashWindowStyleKey: StyleKey<SplashWindowStyles> =
    StyleKey(name = "splash-window") { _ -> SplashWindowStyles.Default }

/**
 * Returns the style of a [SplashWindow] in the current theme, with the overrides of its components.
 *
 * @return the style of the splash window.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun splashWindowStyle(): SplashWindowStyle = SplashWindowStyleKey.current.resolve(Theme.current)

internal fun defaultSplashWindowStyle(theme: Theme): SplashWindowStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    return SplashWindowStyle(
        background = colors.background,
        border = Border(width = theme.borders.thin, color = colors.border),
        cornerRadius = theme.radii.xl,
        size = DpSize(width = spacing(steps = 220f), height = spacing(steps = 130f)),
    )
}
