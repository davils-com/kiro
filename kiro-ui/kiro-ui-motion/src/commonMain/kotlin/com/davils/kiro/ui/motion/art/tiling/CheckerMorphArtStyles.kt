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

package com.davils.kiro.ui.motion.art.tiling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware
import com.davils.kiro.ui.motion.quality.QualityLevel

private const val INSET: Float = 0.16f
private const val CIRCLE: Float = 0.78f
private const val FILL_ALPHA_LIGHT: Float = 0.08f
private const val FILL_ALPHA_DARK: Float = 0.09f
private const val ACCENT_ALPHA_LIGHT: Float = 0.5f
private const val ACCENT_ALPHA_DARK: Float = 0.65f
private const val GRID_ALPHA_LIGHT: Float = 0.1f
private const val GRID_ALPHA_DARK: Float = 0.12f
private const val OUTLINE_ALPHA_LIGHT: Float = 0.35f
private const val OUTLINE_ALPHA_DARK: Float = 0.5f
private const val LAG: Float = 0.08f
private const val MORPH: Float = 0.6f
private const val JITTER: Float = 0.02f
private const val BAND: Int = 5
private const val PERIOD: Int = 40

/**
 * Derives the style of a [CheckerMorphArt] from the tokens of a theme.
 *
 * Register an implementation under [CheckerMorphArtStyleKey] in the components of a theme to change how every
 * [CheckerMorphArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface CheckerMorphArtStyles {
    /**
     * Returns the style of a [CheckerMorphArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): CheckerMorphArtStyle

    /**
     * Holds the default [CheckerMorphArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [CheckerMorphArt].
         *
         * @since 1.0.0
         */
        public val Default: CheckerMorphArtStyles = CheckerMorphArtStyles(::defaultCheckerMorphArtStyle)
    }
}

/**
 * The key of the styles of a [CheckerMorphArt] in the components of a theme, with
 * [CheckerMorphArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val CheckerMorphArtStyleKey: StyleKey<CheckerMorphArtStyles> =
    StyleKey(name = "checker-morph-art") { _ -> CheckerMorphArtStyles.Default }

/**
 * Returns the style of a [CheckerMorphArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun checkerMorphArtStyle(): CheckerMorphArtStyle = CheckerMorphArtStyleKey.current.resolve(Theme.current)

internal fun defaultCheckerMorphArtStyle(theme: Theme): CheckerMorphArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return CheckerMorphArtStyle(
        cell = spacing(steps = 8f),
        inset = INSET,
        circle = CIRCLE,
        fillAlpha = theme.darkAware(light = FILL_ALPHA_LIGHT, dark = FILL_ALPHA_DARK),
        accentAlpha = theme.darkAware(light = ACCENT_ALPHA_LIGHT, dark = ACCENT_ALPHA_DARK),
        gridWidth = theme.borders.thin,
        gridAlpha = theme.darkAware(light = GRID_ALPHA_LIGHT, dark = GRID_ALPHA_DARK),
        outlineAlpha = theme.darkAware(light = OUTLINE_ALPHA_LIGHT, dark = OUTLINE_ALPHA_DARK),
        outlineQuality = QualityLevel.Balanced,
        band = BAND,
        lag = LAG,
        jitter = JITTER,
        morph = MORPH,
        period = long * PERIOD,
        easing = theme.motion.easings.standard,
        salt = 0,
        colors = null,
        line = null,
        background = null,
    )
}
