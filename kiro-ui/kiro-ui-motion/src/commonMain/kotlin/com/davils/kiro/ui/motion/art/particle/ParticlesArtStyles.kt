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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware
import com.davils.kiro.ui.motion.quality.QualityLevel

private const val MAX_COUNT: Int = 360
private const val MIN_ALPHA_LIGHT: Float = 0.3f
private const val MIN_ALPHA_DARK: Float = 0.45f
private const val MAX_ALPHA_LIGHT: Float = 0.85f
private const val MAX_ALPHA_DARK: Float = 1f
private const val PERIOD: Int = 30
private const val TWINKLE: Float = 0.6f
private const val TWINKLE_PERIOD: Int = 12
private const val LINK_ALPHA_LIGHT: Float = 0.4f
private const val LINK_ALPHA_DARK: Float = 0.5f

/**
 * Derives the style of a [ParticlesArt] from the tokens of a theme.
 *
 * Register an implementation under [ParticlesArtStyleKey] in the components of a theme to change how every particles
 * art looks.
 *
 * @since 1.0.0
 */
public fun interface ParticlesArtStyles {
    /**
     * Returns the style of a particles art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the particles art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ParticlesArtStyle

    /**
     * Holds the default [ParticlesArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a particles art.
         *
         * @since 1.0.0
         */
        public val Default: ParticlesArtStyles = ParticlesArtStyles(::defaultParticlesArtStyle)
    }
}

/**
 * The key of the styles of a [ParticlesArt] in the components of a theme, with [ParticlesArtStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val ParticlesArtStyleKey: StyleKey<ParticlesArtStyles> =
    StyleKey(name = "particles-art") { _ -> ParticlesArtStyles.Default }

/**
 * Returns the style of a [ParticlesArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the particles art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun particlesArtStyle(): ParticlesArtStyle = ParticlesArtStyleKey.current.resolve(Theme.current)

internal fun defaultParticlesArtStyle(theme: Theme): ParticlesArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return ParticlesArtStyle(
        distance = spacing(steps = 14f),
        maxCount = MAX_COUNT,
        minRadius = spacing(steps = 0.25f),
        maxRadius = spacing(steps = 0.625f),
        minAlpha = theme.darkAware(light = MIN_ALPHA_LIGHT, dark = MIN_ALPHA_DARK),
        maxAlpha = theme.darkAware(light = MAX_ALPHA_LIGHT, dark = MAX_ALPHA_DARK),
        drift = spacing(steps = 12f),
        period = long * PERIOD,
        twinkle = TWINKLE,
        twinklePeriod = long * TWINKLE_PERIOD,
        parallax = spacing(steps = 4f),
        linkDistance = spacing(steps = 24f),
        linkWidth = spacing(steps = 0.25f),
        linkAlpha = theme.darkAware(light = LINK_ALPHA_LIGHT, dark = LINK_ALPHA_DARK),
        linkQuality = QualityLevel.Medium,
        colors = null,
        link = null,
    )
}
