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
import androidx.compose.ui.graphics.StrokeCap
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware
import com.davils.kiro.ui.motion.quality.QualityLevel

private const val FILL_ALPHA_LIGHT: Float = 0.05f
private const val FILL_ALPHA_DARK: Float = 0.07f
private const val BAND: Int = 3
private const val ACCENT_EVERY: Int = 4
private const val LINE_ALPHA_LIGHT: Float = 0.2f
private const val LINE_ALPHA_DARK: Float = 0.32f
private const val ACCENT_ALPHA_LIGHT: Float = 0.7f
private const val ACCENT_ALPHA_DARK: Float = 0.85f
private const val LINE_SHARE: Float = 0.25f
private const val FLIP: Float = 0.3f
private const val PERIOD: Int = 40
private const val LAG: Float = 0.025f
private const val TURN: Float = 0.3f
private const val POINTER_LEAD: Float = 0.5f

/**
 * Derives the style of a [TruchetArt] from the tokens of a theme.
 *
 * Register an implementation under [TruchetArtStyleKey] in the components of a theme to change how every
 * [TruchetArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface TruchetArtStyles {
    /**
     * Returns the style of a [TruchetArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): TruchetArtStyle

    /**
     * Holds the default [TruchetArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [TruchetArt].
         *
         * @since 1.0.0
         */
        public val Default: TruchetArtStyles = TruchetArtStyles(::defaultTruchetArtStyle)
    }
}

/**
 * The key of the styles of a [TruchetArt] in the components of a theme, with
 * [TruchetArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val TruchetArtStyleKey: StyleKey<TruchetArtStyles> =
    StyleKey(name = "truchet-art") { _ -> TruchetArtStyles.Default }

/**
 * Returns the style of a [TruchetArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun truchetArtStyle(): TruchetArtStyle = TruchetArtStyleKey.current.resolve(Theme.current)

internal fun defaultTruchetArtStyle(theme: Theme): TruchetArtStyle {
    val spacing = theme.spacing
    val motion = theme.motion
    return TruchetArtStyle(
        cell = spacing(steps = 12f),
        strokeWidth = spacing(steps = 0.375f),
        cap = StrokeCap.Round,
        fillAlpha = theme.darkAware(light = FILL_ALPHA_LIGHT, dark = FILL_ALPHA_DARK),
        fillQuality = QualityLevel.Medium,
        band = BAND,
        accentEvery = ACCENT_EVERY,
        lineAlpha = theme.darkAware(light = LINE_ALPHA_LIGHT, dark = LINE_ALPHA_DARK),
        accentAlpha = theme.darkAware(light = ACCENT_ALPHA_LIGHT, dark = ACCENT_ALPHA_DARK),
        variant = TruchetVariant.Arcs,
        lineShare = LINE_SHARE,
        flip = FLIP,
        period = motion.durations.long * PERIOD,
        lag = LAG,
        turn = TURN,
        easing = motion.easings.standard,
        pointerRadius = spacing(steps = 32f),
        pointerLead = POINTER_LEAD,
        salt = 0,
        colors = null,
        line = null,
        background = null,
    )
}
