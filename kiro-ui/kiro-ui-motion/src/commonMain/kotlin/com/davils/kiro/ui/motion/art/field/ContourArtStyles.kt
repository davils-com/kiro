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

package com.davils.kiro.ui.motion.art.field

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val LEVELS: Int = 12
private const val INDEX_EVERY: Int = 4
private const val LINE_ALPHA: Float = 0.55f
private const val INDEX_ALPHA: Float = 0.8f
private const val LINE_TINT: Float = 0.35f
private const val BAND_ALPHA: Float = 0.06f
private const val OCTAVES: Int = 3
private const val WARP: Float = 0.6f
private const val CONTRAST: Float = 1.6f
private const val DRIFT: Float = 0.15f
private const val PERIOD: Int = 160

/**
 * Derives the style of a [ContourArt] from the tokens of a theme.
 *
 * Register an implementation under [ContourArtStyleKey] in the components of a theme to change how every contour art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface ContourArtStyles {
    /**
     * Returns the style of a contour art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the contour art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ContourArtStyle

    /**
     * Holds the default [ContourArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a contour art.
         *
         * @since 1.0.0
         */
        public val Default: ContourArtStyles = ContourArtStyles(::defaultContourArtStyle)
    }
}

/**
 * The key of the styles of a [ContourArt] in the components of a theme, with [ContourArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val ContourArtStyleKey: StyleKey<ContourArtStyles> =
    StyleKey(name = "contour-art") { _ -> ContourArtStyles.Default }

/**
 * Returns the style of a [ContourArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the contour art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun contourArtStyle(): ContourArtStyle = ContourArtStyleKey.current.resolve(Theme.current)

internal fun defaultContourArtStyle(theme: Theme): ContourArtStyle {
    val spacing = theme.spacing
    return ContourArtStyle(
        colors = null,
        cell = spacing(steps = 1.5f),
        levels = LEVELS,
        indexEvery = INDEX_EVERY,
        lineWidth = spacing(steps = 0.25f),
        indexWidth = spacing(steps = 0.375f),
        lineAlpha = LINE_ALPHA,
        indexAlpha = INDEX_ALPHA,
        ink = null,
        lineTint = LINE_TINT,
        bandAlpha = BAND_ALPHA,
        scale = spacing(steps = 80f),
        octaves = OCTAVES,
        warp = WARP,
        contrast = CONTRAST,
        drift = DRIFT,
        period = theme.motion.durations.long * PERIOD,
    )
}
