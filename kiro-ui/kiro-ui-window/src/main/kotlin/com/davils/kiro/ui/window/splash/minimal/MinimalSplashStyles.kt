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

package com.davils.kiro.ui.window.splash.minimal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.scene.ScenePresets
import com.davils.kiro.ui.window.splash.SplashMessages
import com.davils.kiro.ui.window.splash.defaultSplashFailureStyle
import com.davils.kiro.ui.window.splash.spinnerOf
import com.davils.kiro.ui.window.textStyleOf

/**
 * Derives the style of a [MinimalSplash] from the tokens of a theme.
 *
 * Register an implementation under [MinimalSplashStyleKey] in the components of a theme to change how every
 * minimal splash looks.
 *
 * @since 1.0.0
 */
public fun interface MinimalSplashStyles {
    /**
     * Returns the style of a minimal splash for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the minimal splash.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): MinimalSplashStyle

    /**
     * Holds the default [MinimalSplashStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a minimal splash.
         *
         * @since 1.0.0
         */
        public val Default: MinimalSplashStyles = MinimalSplashStyles(::defaultMinimalSplashStyle)
    }
}

/**
 * The key of the styles of a [MinimalSplash] in the components of a theme, with [MinimalSplashStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val MinimalSplashStyleKey: StyleKey<MinimalSplashStyles> =
    StyleKey(name = "minimal-splash") { _ -> MinimalSplashStyles.Default }

/**
 * Returns the style of a [MinimalSplash] in the current theme, with the overrides of its components.
 *
 * @return the style of the minimal splash.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun minimalSplashStyle(): MinimalSplashStyle = MinimalSplashStyleKey.current.resolve(Theme.current)

internal fun defaultMinimalSplashStyle(theme: Theme): MinimalSplashStyle {
    val spacing = theme.spacing
    val typography = theme.typography
    val colors = theme.colors
    val failure = defaultSplashFailureStyle(theme)
    return MinimalSplashStyle(
        size = DpSize(width = spacing(steps = 80f), height = spacing(steps = 80f)),
        scene = ScenePresets.Topography,
        card = colors.card,
        border = Border(width = theme.borders.thin, color = colors.border),
        cornerRadius = theme.radii.xl,
        shadow = theme.shadows.lg,
        cardPadding = spacing(steps = 6f),
        content = colors.cardForeground,
        padding = spacing(steps = 5f),
        gap = spacing(steps = 4f),
        logoSize = spacing(steps = 12f),
        nameStyle = theme.textStyleOf(
            size = typography.scale.base,
            weight = FontWeight.SemiBold,
            family = typography.families.heading,
        ),
        isNameShown = true,
        spinner = theme.spinnerOf(steps = 5f),
        failure = failure.copy(textStyle = failure.textStyle.copy(textAlign = TextAlign.Center)),
        labels = SplashMessages.labels,
    )
}
