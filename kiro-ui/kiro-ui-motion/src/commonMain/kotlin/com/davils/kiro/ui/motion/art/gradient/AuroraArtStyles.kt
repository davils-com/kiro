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

package com.davils.kiro.ui.motion.art.gradient

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.BlendMode
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware

private const val BANDS: Int = 3
private const val SEGMENTS: Int = 128
private const val TOP: Float = 0.26f
private const val GAP: Float = 0.13f
private const val LIFT: Float = 0.7f
private const val PERIOD: Int = 70
private const val RIPPLE: Float = 0.6f
private const val ALPHA_LIGHT: Float = 0.3f
private const val ALPHA_DARK: Float = 0.55f

/**
 * Derives the style of an [AuroraArt] from the tokens of a theme.
 *
 * Register an implementation under [AuroraArtStyleKey] in the components of a theme to change how every aurora art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface AuroraArtStyles {
    /**
     * Returns the style of an aurora art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the aurora art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): AuroraArtStyle

    /**
     * Holds the default [AuroraArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of an aurora art.
         *
         * @since 1.0.0
         */
        public val Default: AuroraArtStyles = AuroraArtStyles(::defaultAuroraArtStyle)
    }
}

/**
 * The key of the styles of an [AuroraArt] in the components of a theme, with [AuroraArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val AuroraArtStyleKey: StyleKey<AuroraArtStyles> =
    StyleKey(name = "aurora-art") { _ -> AuroraArtStyles.Default }

/**
 * Returns the style of an [AuroraArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the aurora art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun auroraArtStyle(): AuroraArtStyle = AuroraArtStyleKey.current.resolve(Theme.current)

internal fun defaultAuroraArtStyle(theme: Theme): AuroraArtStyle {
    val spacing = theme.spacing
    return AuroraArtStyle(
        bands = BANDS,
        segments = SEGMENTS,
        top = TOP,
        gap = GAP,
        thickness = spacing(steps = 22f),
        lift = LIFT,
        amplitude = spacing(steps = 8f),
        wavelength = spacing(steps = 110f),
        period = theme.motion.durations.long * PERIOD,
        ripple = RIPPLE,
        alpha = theme.darkAware(light = ALPHA_LIGHT, dark = ALPHA_DARK),
        blendMode = theme.darkAware(light = BlendMode.SrcOver, dark = BlendMode.Screen),
        colors = null,
    )
}
