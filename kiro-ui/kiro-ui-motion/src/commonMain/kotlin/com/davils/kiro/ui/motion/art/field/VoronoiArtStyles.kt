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
import com.davils.kiro.ui.motion.color.darkAware

private const val JITTER: Float = 0.35f
private const val DRIFT: Float = 0.22f
private const val PERIOD: Int = 160
private const val LINE_ALPHA_LIGHT: Float = 0.45f
private const val LINE_ALPHA_DARK: Float = 0.5f
private const val TINT_ALPHA_LIGHT: Float = 0.16f
private const val TINT_ALPHA_DARK: Float = 0.22f
private const val TINT_VARIANCE: Float = 0.8f
private const val TONE_JITTER: Float = 0.3f
private const val SEED_ALPHA_LIGHT: Float = 0.5f
private const val SEED_ALPHA_DARK: Float = 0.6f
private const val POINTER_ALPHA_LIGHT: Float = 0.18f
private const val POINTER_ALPHA_DARK: Float = 0.26f

/**
 * Derives the style of a [VoronoiArt] from the tokens of a theme.
 *
 * Register an implementation under [VoronoiArtStyleKey] in the components of a theme to change how every voronoi art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface VoronoiArtStyles {
    /**
     * Returns the style of a voronoi art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the voronoi art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): VoronoiArtStyle

    /**
     * Holds the default [VoronoiArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a voronoi art.
         *
         * @since 1.0.0
         */
        public val Default: VoronoiArtStyles = VoronoiArtStyles(::defaultVoronoiArtStyle)
    }
}

/**
 * The key of the styles of a [VoronoiArt] in the components of a theme, with [VoronoiArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val VoronoiArtStyleKey: StyleKey<VoronoiArtStyles> =
    StyleKey(name = "voronoi-art") { _ -> VoronoiArtStyles.Default }

/**
 * Returns the style of a [VoronoiArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the voronoi art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun voronoiArtStyle(): VoronoiArtStyle = VoronoiArtStyleKey.current.resolve(Theme.current)

internal fun defaultVoronoiArtStyle(theme: Theme): VoronoiArtStyle {
    val spacing = theme.spacing
    return VoronoiArtStyle(
        cell = spacing(steps = 20f),
        jitter = JITTER,
        drift = DRIFT,
        period = theme.motion.durations.long * PERIOD,
        lineWidth = spacing(steps = 0.25f),
        lineAlpha = theme.darkAware(light = LINE_ALPHA_LIGHT, dark = LINE_ALPHA_DARK),
        tintAlpha = theme.darkAware(light = TINT_ALPHA_LIGHT, dark = TINT_ALPHA_DARK),
        tintVariance = TINT_VARIANCE,
        toneJitter = TONE_JITTER,
        seedRadius = spacing(steps = 0.375f),
        seedAlpha = theme.darkAware(light = SEED_ALPHA_LIGHT, dark = SEED_ALPHA_DARK),
        pointerAlpha = theme.darkAware(light = POINTER_ALPHA_LIGHT, dark = POINTER_ALPHA_DARK),
        colors = null,
        line = null,
    )
}
