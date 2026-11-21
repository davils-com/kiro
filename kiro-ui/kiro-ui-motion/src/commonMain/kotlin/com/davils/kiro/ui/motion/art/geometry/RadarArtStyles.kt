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

private const val CENTER: Float = 0.5f
private const val SIZE: Float = 0.42f
private const val RINGS: Int = 4
private const val RING_ALPHA_LIGHT: Float = 0.3f
private const val RING_ALPHA_DARK: Float = 0.3f
private const val CROSS_ALPHA_LIGHT: Float = 0.16f
private const val CROSS_ALPHA_DARK: Float = 0.16f
private const val CROSS_REACH: Float = 1.12f
private const val TICKS: Int = 72
private const val MAJOR_EVERY: Int = 6
private const val TICK_ALPHA_LIGHT: Float = 0.4f
private const val TICK_ALPHA_DARK: Float = 0.42f
private const val WEDGE: Float = 60f
private const val WEDGE_ALPHA_LIGHT: Float = 0.14f
private const val WEDGE_ALPHA_DARK: Float = 0.22f
private const val BEAM_ALPHA_LIGHT: Float = 0.55f
private const val BEAM_ALPHA_DARK: Float = 0.7f
private const val BLIPS: Int = 8
private const val BLIP_ALPHA: Float = 0.9f
private const val FADE: Float = 0.75f
private const val HALO_ALPHA_LIGHT: Float = 0.3f
private const val HALO_ALPHA_DARK: Float = 0.4f
private const val INNER: Float = 0.2f
private const val PERIOD: Int = 24

/**
 * Derives the style of a [RadarArt] from the tokens of a theme.
 *
 * Register an implementation under [RadarArtStyleKey] in the components of a theme to change how every
 * [RadarArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface RadarArtStyles {
    /**
     * Returns the style of a [RadarArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): RadarArtStyle

    /**
     * Holds the default [RadarArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [RadarArt].
         *
         * @since 1.0.0
         */
        public val Default: RadarArtStyles = RadarArtStyles(::defaultRadarArtStyle)
    }
}

/**
 * The key of the styles of a [RadarArt] in the components of a theme, with
 * [RadarArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val RadarArtStyleKey: StyleKey<RadarArtStyles> =
    StyleKey(name = "radar-art") { _ -> RadarArtStyles.Default }

/**
 * Returns the style of a [RadarArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun radarArtStyle(): RadarArtStyle = RadarArtStyleKey.current.resolve(Theme.current)

internal fun defaultRadarArtStyle(theme: Theme): RadarArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return RadarArtStyle(
        centerX = CENTER,
        centerY = CENTER,
        size = SIZE,
        rings = RINGS,
        lineWidth = theme.borders.thin,
        ringAlpha = theme.darkAware(light = RING_ALPHA_LIGHT, dark = RING_ALPHA_DARK),
        crossAlpha = theme.darkAware(light = CROSS_ALPHA_LIGHT, dark = CROSS_ALPHA_DARK),
        crossReach = CROSS_REACH,
        ticks = TICKS,
        majorEvery = MAJOR_EVERY,
        tick = spacing(steps = 1f),
        majorTick = spacing(steps = 2f),
        tickAlpha = theme.darkAware(light = TICK_ALPHA_LIGHT, dark = TICK_ALPHA_DARK),
        wedge = WEDGE,
        wedgeAlpha = theme.darkAware(light = WEDGE_ALPHA_LIGHT, dark = WEDGE_ALPHA_DARK),
        beamAlpha = theme.darkAware(light = BEAM_ALPHA_LIGHT, dark = BEAM_ALPHA_DARK),
        blips = BLIPS,
        inner = INNER,
        blip = spacing(steps = 1.25f),
        blipAlpha = BLIP_ALPHA,
        fade = FADE,
        halo = spacing(steps = 4f),
        haloAlpha = theme.darkAware(light = HALO_ALPHA_LIGHT, dark = HALO_ALPHA_DARK),
        haloQuality = QualityLevel.Medium,
        period = long * PERIOD,
        salt = 0,
        colors = null,
        line = null,
        accent = null,
        background = null,
    )
}
