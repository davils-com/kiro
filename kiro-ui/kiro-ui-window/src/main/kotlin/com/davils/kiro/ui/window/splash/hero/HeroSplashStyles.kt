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

package com.davils.kiro.ui.window.splash.hero

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.scene.ScenePresets
import com.davils.kiro.ui.window.splash.SplashMessages
import com.davils.kiro.ui.window.splash.defaultSplashFailureStyle
import com.davils.kiro.ui.window.splash.progressStyle
import com.davils.kiro.ui.window.textStyleOf

private const val SCRIM_START: Float = 0.15f
private const val SCRIM_END: Float = 0.6f

/**
 * Derives the style of a [HeroSplash] from the tokens of a theme.
 *
 * Register an implementation under [HeroSplashStyleKey] in the components of a theme to change how every hero splash
 * looks.
 *
 * @since 1.0.0
 */
public fun interface HeroSplashStyles {
    /**
     * Returns the style of a hero splash for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the hero splash.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): HeroSplashStyle

    /**
     * Holds the default [HeroSplashStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a hero splash.
         *
         * @since 1.0.0
         */
        public val Default: HeroSplashStyles = HeroSplashStyles(::defaultHeroSplashStyle)
    }
}

/**
 * The key of the styles of a [HeroSplash] in the components of a theme, with [HeroSplashStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val HeroSplashStyleKey: StyleKey<HeroSplashStyles> =
    StyleKey(name = "hero-splash") { _ -> HeroSplashStyles.Default }

/**
 * Returns the style of a [HeroSplash] in the current theme, with the overrides of its components.
 *
 * @return the style of the hero splash.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun heroSplashStyle(): HeroSplashStyle = HeroSplashStyleKey.current.resolve(Theme.current)

internal fun defaultHeroSplashStyle(theme: Theme): HeroSplashStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    val typography = theme.typography
    val scale = typography.scale
    val heading = theme.textStyleOf(size = scale.xl4, weight = FontWeight.Bold, family = typography.families.heading)
    return HeroSplashStyle(
        size = DpSize(width = spacing(steps = 200f), height = spacing(steps = 120f)),
        scene = ScenePresets.Composition,
        scrim = colors.background,
        scrimStart = SCRIM_START,
        scrimEnd = SCRIM_END,
        content = colors.foreground,
        muted = colors.mutedForeground,
        padding = spacing(steps = 10f),
        gap = spacing(steps = 4f),
        titleGap = spacing(steps = 1.5f),
        logoSize = spacing(steps = 12f),
        nameStyle = heading.copy(letterSpacing = typography.tracking.tight),
        subtitleStyle = theme.textStyleOf(size = scale.base),
        stepStyle = theme.textStyleOf(size = scale.sm, weight = FontWeight.Medium),
        footerStyle = theme.textStyleOf(size = scale.xs),
        progress = theme.progressStyle(),
        failure = defaultSplashFailureStyle(theme),
        labels = SplashMessages.labels,
    )
}
