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

private const val CURVES: Int = 3
private const val CENTER: Float = 0.5f
private const val SIZE: Float = 0.92f
private const val MIN_SIZE: Float = 0.5f
private const val MIN_ROLLER: Float = 0.25f
private const val MAX_ROLLER: Float = 0.7f
private const val MIN_PEN: Float = 0.55f
private const val MAX_PEN: Float = 0.85f
private const val LOOPS: Int = 11
private const val EPICYCLES: Float = 0.25f
private const val STEPS: Int = 180
private const val LINE_ALPHA: Float = 0.75f
private const val DRAW_PERIOD: Int = 70
private const val DRAW_SHARE: Float = 0.75f
private const val ROTATION_PERIOD: Int = 600
private const val GLOW_ALPHA: Float = 0.14f
private const val TRAIL_ALPHA: Float = 0.08f
private const val ECHOES: Int = 2
private const val ECHO_ANGLE: Float = 3f

/**
 * Derives the style of a [SpirographArt] from the tokens of a theme.
 *
 * Register an implementation under [SpirographArtStyleKey] in the components of a theme to change how every
 * [SpirographArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface SpirographArtStyles {
    /**
     * Returns the style of a [SpirographArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SpirographArtStyle

    /**
     * Holds the default [SpirographArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [SpirographArt].
         *
         * @since 1.0.0
         */
        public val Default: SpirographArtStyles = SpirographArtStyles(::defaultSpirographArtStyle)
    }
}

/**
 * The key of the styles of a [SpirographArt] in the components of a theme, with
 * [SpirographArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val SpirographArtStyleKey: StyleKey<SpirographArtStyles> =
    StyleKey(name = "spirograph-art") { _ -> SpirographArtStyles.Default }

/**
 * Returns the style of a [SpirographArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun spirographArtStyle(): SpirographArtStyle = SpirographArtStyleKey.current.resolve(Theme.current)

internal fun defaultSpirographArtStyle(theme: Theme): SpirographArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return SpirographArtStyle(
        curves = CURVES,
        centerX = CENTER,
        centerY = CENTER,
        size = SIZE,
        minSize = MIN_SIZE,
        minRoller = MIN_ROLLER,
        maxRoller = MAX_ROLLER,
        minPen = MIN_PEN,
        maxPen = MAX_PEN,
        loops = LOOPS,
        epicycles = EPICYCLES,
        steps = STEPS,
        lineWidth = theme.borders.thin,
        lineAlpha = LINE_ALPHA,
        drawPeriod = long * DRAW_PERIOD,
        drawShare = DRAW_SHARE,
        rotationPeriod = long * ROTATION_PERIOD,
        headSize = spacing(steps = 1.25f),
        headGlow = spacing(steps = 4f),
        glowAlpha = GLOW_ALPHA,
        trailAlpha = TRAIL_ALPHA,
        echoes = ECHOES,
        echoAngle = ECHO_ANGLE,
        salt = 0,
        colors = null,
        head = null,
        background = null,
    )
}
