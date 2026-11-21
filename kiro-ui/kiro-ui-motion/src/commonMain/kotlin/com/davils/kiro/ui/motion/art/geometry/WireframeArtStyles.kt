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

private const val SIZE: Float = 0.34f
private const val CENTER: Float = 0.5f
private const val PERSPECTIVE: Float = 3.5f
private const val TILT: Float = 22f
private const val WOBBLE: Float = 10f
private const val FAR_DOT: Float = 0.45f
private const val NEAR_ALPHA_LIGHT: Float = 0.75f
private const val NEAR_ALPHA_DARK: Float = 0.9f
private const val FAR_ALPHA_LIGHT: Float = 0.2f
private const val FAR_ALPHA_DARK: Float = 0.22f
private const val DOT_ALPHA_LIGHT: Float = 0.7f
private const val DOT_ALPHA_DARK: Float = 0.85f
private const val GUIDE_ALPHA_LIGHT: Float = 0.07f
private const val GUIDE_ALPHA_DARK: Float = 0.12f
private const val PERIOD: Int = 90
private const val WOBBLE_PERIOD: Int = 150

/**
 * Derives the style of a [WireframeArt] from the tokens of a theme.
 *
 * Register an implementation under [WireframeArtStyleKey] in the components of a theme to change how every
 * [WireframeArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface WireframeArtStyles {
    /**
     * Returns the style of a [WireframeArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): WireframeArtStyle

    /**
     * Holds the default [WireframeArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [WireframeArt].
         *
         * @since 1.0.0
         */
        public val Default: WireframeArtStyles = WireframeArtStyles(::defaultWireframeArtStyle)
    }
}

/**
 * The key of the styles of a [WireframeArt] in the components of a theme, with
 * [WireframeArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val WireframeArtStyleKey: StyleKey<WireframeArtStyles> =
    StyleKey(name = "wireframe-art") { _ -> WireframeArtStyles.Default }

/**
 * Returns the style of a [WireframeArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun wireframeArtStyle(): WireframeArtStyle = WireframeArtStyleKey.current.resolve(Theme.current)

internal fun defaultWireframeArtStyle(theme: Theme): WireframeArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return WireframeArtStyle(
        solid = WireframeSolid.Icosahedron,
        size = SIZE,
        centerX = CENTER,
        centerY = CENTER,
        perspective = PERSPECTIVE,
        tilt = TILT,
        wobble = WOBBLE,
        lineWidth = theme.borders.thin,
        nearAlpha = theme.darkAware(light = NEAR_ALPHA_LIGHT, dark = NEAR_ALPHA_DARK),
        farAlpha = theme.darkAware(light = FAR_ALPHA_LIGHT, dark = FAR_ALPHA_DARK),
        dot = spacing(steps = 1f),
        farDot = FAR_DOT,
        dotAlpha = theme.darkAware(light = DOT_ALPHA_LIGHT, dark = DOT_ALPHA_DARK),
        dotQuality = QualityLevel.Medium,
        guideAlpha = theme.darkAware(light = GUIDE_ALPHA_LIGHT, dark = GUIDE_ALPHA_DARK),
        period = long * PERIOD,
        wobblePeriod = long * WOBBLE_PERIOD,
        salt = 0,
        line = null,
        accent = null,
        background = null,
    )
}
