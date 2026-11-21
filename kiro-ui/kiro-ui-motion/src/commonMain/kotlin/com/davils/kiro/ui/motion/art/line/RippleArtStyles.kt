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

package com.davils.kiro.ui.motion.art.line

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware

private const val CENTER: Float = 0.5f
private const val RINGS: Int = 5
private const val REACH: Float = 0.9f
private const val RING_ALPHA_LIGHT: Float = 0.5f
private const val RING_ALPHA_DARK: Float = 0.7f
private const val FILL_ALPHA_LIGHT: Float = 0.05f
private const val FILL_ALPHA_DARK: Float = 0.08f
private const val PERIOD: Int = 36
private const val GUIDES: Int = 8
private const val GUIDE_ALPHA_LIGHT: Float = 0.25f
private const val GUIDE_ALPHA_DARK: Float = 0.4f
private const val POINTER_FOLLOW: Float = 1f

/**
 * Derives the style of a [RippleArt] from the tokens of a theme.
 *
 * Register an implementation under [RippleArtStyleKey] in the components of a theme to change how every
 * [RippleArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface RippleArtStyles {
    /**
     * Returns the style of a [RippleArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): RippleArtStyle

    /**
     * Holds the default [RippleArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [RippleArt].
         *
         * @since 1.0.0
         */
        public val Default: RippleArtStyles = RippleArtStyles(::defaultRippleArtStyle)
    }
}

/**
 * The key of the styles of a [RippleArt] in the components of a theme, with
 * [RippleArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val RippleArtStyleKey: StyleKey<RippleArtStyles> =
    StyleKey(name = "ripple-art") { _ -> RippleArtStyles.Default }

/**
 * Returns the style of a [RippleArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun rippleArtStyle(): RippleArtStyle = RippleArtStyleKey.current.resolve(Theme.current)

internal fun defaultRippleArtStyle(theme: Theme): RippleArtStyle = RippleArtStyle(
    originX = CENTER,
    originY = CENTER,
    rings = RINGS,
    reach = REACH,
    lineWidth = theme.borders.thin,
    ringAlpha = theme.darkAware(light = RING_ALPHA_LIGHT, dark = RING_ALPHA_DARK),
    fillAlpha = theme.darkAware(light = FILL_ALPHA_LIGHT, dark = FILL_ALPHA_DARK),
    period = theme.motion.durations.long * PERIOD,
    easing = theme.motion.easings.ease,
    guides = GUIDES,
    guideSpacing = theme.spacing(steps = 16f),
    guideAlpha = theme.darkAware(light = GUIDE_ALPHA_LIGHT, dark = GUIDE_ALPHA_DARK),
    pointerFollow = POINTER_FOLLOW,
    salt = 0,
    colors = null,
    guide = null,
    background = null,
)
