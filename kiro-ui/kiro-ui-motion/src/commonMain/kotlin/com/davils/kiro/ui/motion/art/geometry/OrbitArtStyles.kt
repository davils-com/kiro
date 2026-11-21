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

private const val CENTER: Float = 0.5f
private const val RINGS: Int = 6
private const val RING_ALPHA_LIGHT: Float = 0.22f
private const val RING_ALPHA_DARK: Float = 0.45f
private const val DRIFT: Int = 600
private const val MAX_PLANETS: Int = 2
private const val SHINE: Float = 0.35f
private const val SHINE_SIZE: Float = 0.36f
private const val TRAIL: Float = 60f
private const val TRAIL_SEGMENTS: Int = 20
private const val TRAIL_ALPHA_LIGHT: Float = 0.18f
private const val TRAIL_ALPHA_DARK: Float = 0.3f
private const val MOON_CHANCE: Float = 0.25f
private const val MOON_PERIOD: Int = 24
private const val GLOW_ALPHA_LIGHT: Float = 0.16f
private const val GLOW_ALPHA_DARK: Float = 0.22f
private const val PERIOD: Int = 80

/**
 * Derives the style of an [OrbitArt] from the tokens of a theme.
 *
 * Register an implementation under [OrbitArtStyleKey] in the components of a theme to change how every
 * [OrbitArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface OrbitArtStyles {
    /**
     * Returns the style of an [OrbitArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): OrbitArtStyle

    /**
     * Holds the default [OrbitArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of an [OrbitArt].
         *
         * @since 1.0.0
         */
        public val Default: OrbitArtStyles = OrbitArtStyles(::defaultOrbitArtStyle)
    }
}

/**
 * The key of the styles of an [OrbitArt] in the components of a theme, with
 * [OrbitArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val OrbitArtStyleKey: StyleKey<OrbitArtStyles> =
    StyleKey(name = "orbit-art") { _ -> OrbitArtStyles.Default }

/**
 * Returns the style of an [OrbitArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun orbitArtStyle(): OrbitArtStyle = OrbitArtStyleKey.current.resolve(Theme.current)

internal fun defaultOrbitArtStyle(theme: Theme): OrbitArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return OrbitArtStyle(
        centerX = CENTER,
        centerY = CENTER,
        firstRadius = spacing(steps = 16f),
        ringSpacing = spacing(steps = 10f),
        rings = RINGS,
        ringWidth = theme.borders.thin,
        ringAlpha = theme.darkAware(light = RING_ALPHA_LIGHT, dark = RING_ALPHA_DARK),
        dash = spacing(steps = 0.5f),
        dashGap = spacing(steps = 1f),
        drift = long * DRIFT,
        minPlanets = 1,
        maxPlanets = MAX_PLANETS,
        minPlanet = spacing(steps = 1.25f),
        maxPlanet = spacing(steps = 2.5f),
        shine = SHINE,
        shineSize = SHINE_SIZE,
        trail = TRAIL,
        trailSegments = TRAIL_SEGMENTS,
        trailAlpha = theme.darkAware(light = TRAIL_ALPHA_LIGHT, dark = TRAIL_ALPHA_DARK),
        moonChance = MOON_CHANCE,
        moon = spacing(steps = 0.75f),
        moonDistance = spacing(steps = 2f),
        moonPeriod = long * MOON_PERIOD,
        glowRadius = spacing(steps = 28f),
        glowAlpha = theme.darkAware(light = GLOW_ALPHA_LIGHT, dark = GLOW_ALPHA_DARK),
        period = long * PERIOD,
        isAlternating = true,
        salt = 0,
        colors = null,
        glow = null,
        ring = null,
        highlight = null,
        background = null,
    )
}
