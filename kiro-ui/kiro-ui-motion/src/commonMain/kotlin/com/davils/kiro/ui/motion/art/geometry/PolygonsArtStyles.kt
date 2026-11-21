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

private const val RINGS: Int = 6
private const val CENTER: Float = 0.5f
private const val MIN_SIDES: Int = 3
private const val MAX_SIDES: Int = 8
private const val LINE_ALPHA_LIGHT: Float = 0.32f
private const val LINE_ALPHA_DARK: Float = 0.3f
private const val DOT_ALPHA_LIGHT: Float = 0.55f
private const val DOT_ALPHA_DARK: Float = 0.6f
private const val GUIDE_ALPHA_LIGHT: Float = 0.09f
private const val GUIDE_ALPHA_DARK: Float = 0.1f
private const val TRACE_ALPHA_LIGHT: Float = 0.85f
private const val TRACE_ALPHA_DARK: Float = 0.95f
private const val SLOWDOWN: Float = 0.35f
private const val DRAW_SHARE: Float = 0.6f
private const val PERIOD: Int = 160
private const val TRACE_PERIOD: Int = 24

/**
 * Derives the style of a [PolygonsArt] from the tokens of a theme.
 *
 * Register an implementation under [PolygonsArtStyleKey] in the components of a theme to change how every
 * [PolygonsArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface PolygonsArtStyles {
    /**
     * Returns the style of a [PolygonsArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): PolygonsArtStyle

    /**
     * Holds the default [PolygonsArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [PolygonsArt].
         *
         * @since 1.0.0
         */
        public val Default: PolygonsArtStyles = PolygonsArtStyles(::defaultPolygonsArtStyle)
    }
}

/**
 * The key of the styles of a [PolygonsArt] in the components of a theme, with
 * [PolygonsArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val PolygonsArtStyleKey: StyleKey<PolygonsArtStyles> =
    StyleKey(name = "polygons-art") { _ -> PolygonsArtStyles.Default }

/**
 * Returns the style of a [PolygonsArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun polygonsArtStyle(): PolygonsArtStyle = PolygonsArtStyleKey.current.resolve(Theme.current)

internal fun defaultPolygonsArtStyle(theme: Theme): PolygonsArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return PolygonsArtStyle(
        rings = RINGS,
        centerX = CENTER,
        centerY = CENTER,
        firstRadius = spacing(steps = 6f),
        ringSpacing = spacing(steps = 5f),
        minSides = MIN_SIDES,
        maxSides = MAX_SIDES,
        lineWidth = theme.borders.thin,
        lineAlpha = theme.darkAware(light = LINE_ALPHA_LIGHT, dark = LINE_ALPHA_DARK),
        dot = spacing(steps = 0.75f),
        dotAlpha = theme.darkAware(light = DOT_ALPHA_LIGHT, dark = DOT_ALPHA_DARK),
        dotQuality = QualityLevel.Medium,
        guideAlpha = theme.darkAware(light = GUIDE_ALPHA_LIGHT, dark = GUIDE_ALPHA_DARK),
        period = long * PERIOD,
        slowdown = SLOWDOWN,
        isAlternating = true,
        tracePeriod = long * TRACE_PERIOD,
        drawShare = DRAW_SHARE,
        traceWidth = theme.borders.thick,
        traceAlpha = theme.darkAware(light = TRACE_ALPHA_LIGHT, dark = TRACE_ALPHA_DARK),
        head = spacing(steps = 1.25f),
        salt = 0,
        line = null,
        accent = null,
        background = null,
    )
}
