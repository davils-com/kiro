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

private const val CURVES: Int = 2
private const val CENTER: Float = 0.5f
private const val SIZE: Float = 0.4f
private const val STRETCH: Float = 1.45f
private const val MIN_FREQUENCY: Int = 2
private const val MAX_FREQUENCY: Int = 3
private const val DAMPING: Float = 1.6f
private const val TURNS: Float = 5f
private const val STEPS: Int = 720
private const val SPREAD: Float = 0.04f
private const val DETUNE: Float = 0.015f
private const val TWIST: Float = 0f
private const val LINE_ALPHA_LIGHT: Float = 0.75f
private const val LINE_ALPHA_DARK: Float = 0.85f
private const val FAINT_ALPHA_LIGHT: Float = 0.12f
private const val FAINT_ALPHA_DARK: Float = 0.16f
private const val GLOW_ALPHA_LIGHT: Float = 0.14f
private const val GLOW_ALPHA_DARK: Float = 0.2f
private const val GUIDE_ALPHA_LIGHT: Float = 0.06f
private const val GUIDE_ALPHA_DARK: Float = 0.1f
private const val DRAW_SHARE: Float = 0.7f
private const val DRAW_PERIOD: Int = 56
private const val DRIFT_PERIOD: Int = 400

/**
 * Derives the style of a [LissajousArt] from the tokens of a theme.
 *
 * Register an implementation under [LissajousArtStyleKey] in the components of a theme to change how every
 * [LissajousArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface LissajousArtStyles {
    /**
     * Returns the style of a [LissajousArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): LissajousArtStyle

    /**
     * Holds the default [LissajousArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [LissajousArt].
         *
         * @since 1.0.0
         */
        public val Default: LissajousArtStyles = LissajousArtStyles(::defaultLissajousArtStyle)
    }
}

/**
 * The key of the styles of a [LissajousArt] in the components of a theme, with
 * [LissajousArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val LissajousArtStyleKey: StyleKey<LissajousArtStyles> =
    StyleKey(name = "lissajous-art") { _ -> LissajousArtStyles.Default }

/**
 * Returns the style of a [LissajousArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun lissajousArtStyle(): LissajousArtStyle = LissajousArtStyleKey.current.resolve(Theme.current)

internal fun defaultLissajousArtStyle(theme: Theme): LissajousArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return LissajousArtStyle(
        curves = CURVES,
        centerX = CENTER,
        centerY = CENTER,
        size = SIZE,
        stretch = STRETCH,
        minFrequency = MIN_FREQUENCY,
        maxFrequency = MAX_FREQUENCY,
        detune = DETUNE,
        twist = TWIST,
        damping = DAMPING,
        turns = TURNS,
        spread = SPREAD,
        steps = STEPS,
        lineWidth = theme.borders.thin,
        lineAlpha = theme.darkAware(light = LINE_ALPHA_LIGHT, dark = LINE_ALPHA_DARK),
        faintAlpha = theme.darkAware(light = FAINT_ALPHA_LIGHT, dark = FAINT_ALPHA_DARK),
        head = spacing(steps = 1.25f),
        headGlow = spacing(steps = 4f),
        glowAlpha = theme.darkAware(light = GLOW_ALPHA_LIGHT, dark = GLOW_ALPHA_DARK),
        guideAlpha = theme.darkAware(light = GUIDE_ALPHA_LIGHT, dark = GUIDE_ALPHA_DARK),
        drawPeriod = long * DRAW_PERIOD,
        drawShare = DRAW_SHARE,
        driftPeriod = long * DRIFT_PERIOD,
        salt = 0,
        colors = null,
        line = null,
        pen = null,
        background = null,
    )
}
