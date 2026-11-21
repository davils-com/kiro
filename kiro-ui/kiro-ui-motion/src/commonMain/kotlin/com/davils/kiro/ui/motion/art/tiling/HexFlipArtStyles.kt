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

private const val CENTER: Float = 0.5f
private const val OUTLINE_ALPHA_LIGHT: Float = 0.2f
private const val OUTLINE_ALPHA_DARK: Float = 0.2f
private const val FRONT_ALPHA_LIGHT: Float = 0.03f
private const val FRONT_ALPHA_DARK: Float = 0.05f
private const val BACK_ALPHA_LIGHT: Float = 0.24f
private const val BACK_ALPHA_DARK: Float = 0.3f
private const val EDGE_ALPHA_LIGHT: Float = 0.45f
private const val EDGE_ALPHA_DARK: Float = 0.6f
private const val DETAIL_ALPHA_LIGHT: Float = 0.3f
private const val DETAIL_ALPHA_DARK: Float = 0.45f
private const val SHADING_LIGHT: Float = 0.05f
private const val SHADING_DARK: Float = 0.25f
private const val SHARE: Float = 0.2f
private const val DETAIL: Float = 0.5f
private const val SPREAD: Float = 0.45f
private const val TURN: Float = 0.12f
private const val PERIOD: Int = 40
private const val SHADES: Int = 16

/**
 * Derives the style of a [HexFlipArt] from the tokens of a theme.
 *
 * Register an implementation under [HexFlipArtStyleKey] in the components of a theme to change how every
 * [HexFlipArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface HexFlipArtStyles {
    /**
     * Returns the style of a [HexFlipArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): HexFlipArtStyle

    /**
     * Holds the default [HexFlipArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [HexFlipArt].
         *
         * @since 1.0.0
         */
        public val Default: HexFlipArtStyles = HexFlipArtStyles(::defaultHexFlipArtStyle)
    }
}

/**
 * The key of the styles of a [HexFlipArt] in the components of a theme, with
 * [HexFlipArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val HexFlipArtStyleKey: StyleKey<HexFlipArtStyles> =
    StyleKey(name = "hex-flip-art") { _ -> HexFlipArtStyles.Default }

/**
 * Returns the style of a [HexFlipArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun hexFlipArtStyle(): HexFlipArtStyle = HexFlipArtStyleKey.current.resolve(Theme.current)

internal fun defaultHexFlipArtStyle(theme: Theme): HexFlipArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return HexFlipArtStyle(
        radius = spacing(steps = 6f),
        gap = spacing(steps = 0.75f),
        outlineWidth = theme.borders.thin,
        outlineAlpha = theme.darkAware(light = OUTLINE_ALPHA_LIGHT, dark = OUTLINE_ALPHA_DARK),
        frontAlpha = theme.darkAware(light = FRONT_ALPHA_LIGHT, dark = FRONT_ALPHA_DARK),
        backAlpha = theme.darkAware(light = BACK_ALPHA_LIGHT, dark = BACK_ALPHA_DARK),
        edgeAlpha = theme.darkAware(light = EDGE_ALPHA_LIGHT, dark = EDGE_ALPHA_DARK),
        detail = DETAIL,
        detailAlpha = theme.darkAware(light = DETAIL_ALPHA_LIGHT, dark = DETAIL_ALPHA_DARK),
        detailQuality = QualityLevel.Balanced,
        shading = theme.darkAware(light = SHADING_LIGHT, dark = SHADING_DARK),
        share = SHARE,
        shades = SHADES,
        centerX = CENTER,
        centerY = CENTER,
        spread = SPREAD,
        turn = TURN,
        period = long * PERIOD,
        easing = theme.motion.easings.standard,
        salt = 0,
        colors = null,
        line = null,
        shade = null,
        background = null,
    )
}
