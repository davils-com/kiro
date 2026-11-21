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

package com.davils.kiro.ui.motion.art.grid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware

private const val HORIZON: Float = 0.42f
private const val DEPTH: Float = 0.5f
private const val LINE_ALPHA_LIGHT: Float = 0.55f
private const val LINE_ALPHA_DARK: Float = 0.7f
private const val FADE: Float = 0.6f
private const val MAX_ROWS: Int = 48
private const val PERIOD: Int = 10
private const val GLOW_HEIGHT: Float = 0.16f
private const val GLOW_ALPHA_LIGHT: Float = 0.1f
private const val GLOW_ALPHA_DARK: Float = 0.16f
private const val PULSES: Int = 4
private const val PULSE_LENGTH: Float = 0.22f
private const val PULSE_ALPHA_LIGHT: Float = 0.75f
private const val PULSE_ALPHA_DARK: Float = 0.9f
private const val PULSE_PERIOD: Int = 18

/**
 * Derives the style of a [RetroGridArt] from the tokens of a theme.
 *
 * Register an implementation under [RetroGridArtStyleKey] in the components of a theme to change how every
 * [RetroGridArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface RetroGridArtStyles {
    /**
     * Returns the style of a [RetroGridArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): RetroGridArtStyle

    /**
     * Holds the default [RetroGridArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [RetroGridArt].
         *
         * @since 1.0.0
         */
        public val Default: RetroGridArtStyles = RetroGridArtStyles(::defaultRetroGridArtStyle)
    }
}

/**
 * The key of the styles of a [RetroGridArt] in the components of a theme, with
 * [RetroGridArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val RetroGridArtStyleKey: StyleKey<RetroGridArtStyles> =
    StyleKey(name = "retro-grid-art") { _ -> RetroGridArtStyles.Default }

/**
 * Returns the style of a [RetroGridArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun retroGridArtStyle(): RetroGridArtStyle = RetroGridArtStyleKey.current.resolve(Theme.current)

internal fun defaultRetroGridArtStyle(theme: Theme): RetroGridArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return RetroGridArtStyle(
        horizon = HORIZON,
        cell = spacing(steps = 20f),
        depth = DEPTH,
        lineWidth = theme.borders.thin,
        lineAlpha = theme.darkAware(light = LINE_ALPHA_LIGHT, dark = LINE_ALPHA_DARK),
        fade = FADE,
        maxRows = MAX_ROWS,
        period = long * PERIOD,
        glowHeight = GLOW_HEIGHT,
        glowAlpha = theme.darkAware(light = GLOW_ALPHA_LIGHT, dark = GLOW_ALPHA_DARK),
        pulses = PULSES,
        pulseLength = PULSE_LENGTH,
        pulseWidth = theme.borders.thick,
        pulseAlpha = theme.darkAware(light = PULSE_ALPHA_LIGHT, dark = PULSE_ALPHA_DARK),
        pulsePeriod = long * PULSE_PERIOD,
        salt = 0,
        line = null,
        colors = null,
        glow = null,
        background = null,
    )
}
