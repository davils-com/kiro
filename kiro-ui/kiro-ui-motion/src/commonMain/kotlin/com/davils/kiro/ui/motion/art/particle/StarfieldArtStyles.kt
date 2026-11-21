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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware

private const val COUNT: Int = 220
private const val CENTER: Float = 0.5f
private const val SPREAD: Float = 0.35f
private const val NEAREST: Float = 0.06f
private const val PERIOD: Int = 60
private const val STREAK: Float = 0.08f
private const val ALPHA_LIGHT: Float = 0.8f
private const val ALPHA_DARK: Float = 0.9f
private const val FADE_IN: Float = 0.35f

/**
 * Derives the style of a [StarfieldArt] from the tokens of a theme.
 *
 * Register an implementation under [StarfieldArtStyleKey] in the components of a theme to change how every starfield
 * art looks.
 *
 * @since 1.0.0
 */
public fun interface StarfieldArtStyles {
    /**
     * Returns the style of a starfield art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the starfield art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): StarfieldArtStyle

    /**
     * Holds the default [StarfieldArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a starfield art.
         *
         * @since 1.0.0
         */
        public val Default: StarfieldArtStyles = StarfieldArtStyles(::defaultStarfieldArtStyle)
    }
}

/**
 * The key of the styles of a [StarfieldArt] in the components of a theme, with [StarfieldArtStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val StarfieldArtStyleKey: StyleKey<StarfieldArtStyles> =
    StyleKey(name = "starfield-art") { _ -> StarfieldArtStyles.Default }

/**
 * Returns the style of a [StarfieldArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the starfield art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun starfieldArtStyle(): StarfieldArtStyle = StarfieldArtStyleKey.current.resolve(Theme.current)

internal fun defaultStarfieldArtStyle(theme: Theme): StarfieldArtStyle {
    val spacing = theme.spacing
    return StarfieldArtStyle(
        count = COUNT,
        centerX = CENTER,
        centerY = CENTER,
        spread = SPREAD,
        nearest = NEAREST,
        period = theme.motion.durations.long * PERIOD,
        streak = STREAK,
        minWidth = spacing(steps = 0.25f),
        maxWidth = spacing(steps = 0.75f),
        alpha = theme.darkAware(light = ALPHA_LIGHT, dark = ALPHA_DARK),
        fadeIn = FADE_IN,
        colors = null,
    )
}
