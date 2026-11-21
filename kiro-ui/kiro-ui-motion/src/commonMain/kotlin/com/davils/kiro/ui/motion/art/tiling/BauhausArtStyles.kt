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

private const val QUARTER_WEIGHT: Float = 3f
private const val HALF_WEIGHT: Float = 2.5f
private const val CIRCLE_WEIGHT: Float = 1.5f
private const val RING_WEIGHT: Float = 1.5f
private const val TRIANGLE_WEIGHT: Float = 1f
private const val STRIPES_WEIGHT: Float = 0.6f
private const val DOUBLE_WEIGHT: Float = 1.5f
private const val DOT_WEIGHT: Float = 1f
private const val CIRCLE: Float = 0.3f
private const val RING: Float = 0.28f
private const val RING_WIDTH: Float = 0.04f
private const val DOT: Float = 0.08f
private const val STRIPES: Int = 3
private const val STRIPE_WIDTH: Float = 0.05f
private const val TILE_TINT: Float = 0.04f
private const val SHAPE_TINT_LIGHT: Float = 0.16f
private const val SHAPE_TINT_DARK: Float = 0.22f
private const val ACCENT_SHARE: Float = 0.18f
private const val OUTLINE_ALPHA_LIGHT: Float = 0.1f
private const val OUTLINE_ALPHA_DARK: Float = 0.16f
private const val PERIOD: Int = 40
private const val TURN: Float = 0.1f
private const val POP: Float = 0.04f

/**
 * Derives the style of a [BauhausArt] from the tokens of a theme.
 *
 * Register an implementation under [BauhausArtStyleKey] in the components of a theme to change how every
 * [BauhausArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface BauhausArtStyles {
    /**
     * Returns the style of a [BauhausArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): BauhausArtStyle

    /**
     * Holds the default [BauhausArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [BauhausArt].
         *
         * @since 1.0.0
         */
        public val Default: BauhausArtStyles = BauhausArtStyles(::defaultBauhausArtStyle)
    }
}

/**
 * The key of the styles of a [BauhausArt] in the components of a theme, with
 * [BauhausArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val BauhausArtStyleKey: StyleKey<BauhausArtStyles> =
    StyleKey(name = "bauhaus-art") { _ -> BauhausArtStyles.Default }

/**
 * Returns the style of a [BauhausArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun bauhausArtStyle(): BauhausArtStyle = BauhausArtStyleKey.current.resolve(Theme.current)

internal fun defaultBauhausArtStyle(theme: Theme): BauhausArtStyle {
    val spacing = theme.spacing
    val motion = theme.motion
    return BauhausArtStyle(
        cell = spacing(steps = 16f),
        gap = spacing(steps = 3f),
        radius = theme.radii.lg,
        weights = mapOf(
            BauhausShape.QuarterCircle to QUARTER_WEIGHT,
            BauhausShape.HalfCircle to HALF_WEIGHT,
            BauhausShape.Circle to CIRCLE_WEIGHT,
            BauhausShape.Ring to RING_WEIGHT,
            BauhausShape.Triangle to TRIANGLE_WEIGHT,
            BauhausShape.Stripes to STRIPES_WEIGHT,
            BauhausShape.DoubleQuarter to DOUBLE_WEIGHT,
            BauhausShape.Dot to DOT_WEIGHT,
        ),
        circle = CIRCLE,
        ring = RING,
        ringWidth = RING_WIDTH,
        dot = DOT,
        stripes = STRIPES,
        stripeWidth = STRIPE_WIDTH,
        tileTint = TILE_TINT,
        shapeTint = theme.darkAware(light = SHAPE_TINT_LIGHT, dark = SHAPE_TINT_DARK),
        accentShare = ACCENT_SHARE,
        outlineWidth = theme.borders.thin,
        outlineAlpha = theme.darkAware(light = OUTLINE_ALPHA_LIGHT, dark = OUTLINE_ALPHA_DARK),
        period = motion.durations.long * PERIOD,
        stagger = 1f,
        turn = TURN,
        pop = POP,
        easing = motion.easings.standard,
        salt = 0,
        colors = null,
        outline = null,
        background = null,
    )
}
