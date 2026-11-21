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

package com.davils.kiro.ui.motion.art.geometry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware
import com.davils.kiro.ui.motion.quality.QualityLevel

private const val INSET: Float = 0.2f
private const val VACANCY: Float = 0.3f
private const val JITTER: Float = 0.2f
private const val FREQUENCY: Float = 0.09f
private const val PERIOD: Int = 40
private const val ORIGIN_X: Float = 0.5f
private const val ORIGIN_Y: Float = 0.45f
private const val TINT_LIGHT: Float = 0.45f
private const val TINT_DARK: Float = 0.6f
private const val COLOR_BIAS: Float = 2f
private const val LEFT_SHADE_LIGHT: Float = 0.03f
private const val LEFT_SHADE_DARK: Float = 0.15f
private const val RIGHT_SHADE_LIGHT: Float = 0.07f
private const val RIGHT_SHADE_DARK: Float = 0.3f
private const val OUTLINE_ALPHA_LIGHT: Float = 0.18f
private const val OUTLINE_ALPHA_DARK: Float = 0.35f
private const val WINDOW_ALPHA_LIGHT: Float = 0.06f
private const val WINDOW_ALPHA_DARK: Float = 0.1f

/**
 * Derives the style of an [IsometricCityArt] from the tokens of a theme.
 *
 * Register an implementation under [IsometricCityArtStyleKey] in the components of a theme to change how every
 * [IsometricCityArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface IsometricCityArtStyles {
    /**
     * Returns the style of an [IsometricCityArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): IsometricCityArtStyle

    /**
     * Holds the default [IsometricCityArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of an [IsometricCityArt].
         *
         * @since 1.0.0
         */
        public val Default: IsometricCityArtStyles = IsometricCityArtStyles(::defaultIsometricCityArtStyle)
    }
}

/**
 * The key of the styles of an [IsometricCityArt] in the components of a theme, with
 * [IsometricCityArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val IsometricCityArtStyleKey: StyleKey<IsometricCityArtStyles> =
    StyleKey(name = "isometric-city-art") { _ -> IsometricCityArtStyles.Default }

/**
 * Returns the style of an [IsometricCityArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun isometricCityArtStyle(): IsometricCityArtStyle = IsometricCityArtStyleKey.current.resolve(Theme.current)

internal fun defaultIsometricCityArtStyle(theme: Theme): IsometricCityArtStyle {
    val spacing = theme.spacing
    return IsometricCityArtStyle(
        tile = spacing(steps = 14f),
        inset = INSET,
        vacancy = VACANCY,
        minHeight = spacing(steps = 1f),
        maxHeight = spacing(steps = 10f),
        jitter = JITTER,
        frequency = FREQUENCY,
        period = theme.motion.durations.long * PERIOD,
        originX = ORIGIN_X,
        originY = ORIGIN_Y,
        tint = theme.darkAware(light = TINT_LIGHT, dark = TINT_DARK),
        colorBias = COLOR_BIAS,
        leftShade = theme.darkAware(light = LEFT_SHADE_LIGHT, dark = LEFT_SHADE_DARK),
        rightShade = theme.darkAware(light = RIGHT_SHADE_LIGHT, dark = RIGHT_SHADE_DARK),
        outlineWidth = theme.borders.thin,
        outlineAlpha = theme.darkAware(light = OUTLINE_ALPHA_LIGHT, dark = OUTLINE_ALPHA_DARK),
        floor = spacing(steps = 2f),
        window = spacing(steps = 1.5f),
        windowHeight = spacing(steps = 0.5f),
        windowGap = spacing(steps = 1.5f),
        windowAlpha = theme.darkAware(light = WINDOW_ALPHA_LIGHT, dark = WINDOW_ALPHA_DARK),
        detailQuality = QualityLevel.Medium,
        salt = 0,
        colors = null,
        shade = null,
        base = null,
        detail = null,
        background = null,
    )
}
