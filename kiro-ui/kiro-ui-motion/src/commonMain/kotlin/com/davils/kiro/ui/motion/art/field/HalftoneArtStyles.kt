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

private const val MAX_DOT: Float = 0.42f
private const val MIN_DOT: Float = 0.06f
private const val ALPHA_LIGHT: Float = 0.5f
private const val ALPHA_DARK: Float = 0.85f
private const val ANGLE: Float = 15f
private const val GRADIENT_ANGLE: Float = 30f
private const val WAVES: Int = 2
private const val FIRST_SPEED: Float = 0.5f
private const val SECOND_SPEED: Float = -0.35f
private const val ORBIT: Float = 0.3f
private const val PERIOD: Int = 40
private const val WANDER_PERIOD: Int = 240
private const val POINTER_STRENGTH: Float = 0.5f

/**
 * Derives the style of a [HalftoneArt] from the tokens of a theme.
 *
 * Register an implementation under [HalftoneArtStyleKey] in the components of a theme to change how every halftone art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface HalftoneArtStyles {
    /**
     * Returns the style of a halftone art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the halftone art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): HalftoneArtStyle

    /**
     * Holds the default [HalftoneArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a halftone art.
         *
         * @since 1.0.0
         */
        public val Default: HalftoneArtStyles = HalftoneArtStyles(::defaultHalftoneArtStyle)
    }
}

/**
 * The key of the styles of a [HalftoneArt] in the components of a theme, with [HalftoneArtStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val HalftoneArtStyleKey: StyleKey<HalftoneArtStyles> =
    StyleKey(name = "halftone-art") { _ -> HalftoneArtStyles.Default }

/**
 * Returns the style of a [HalftoneArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the halftone art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun halftoneArtStyle(): HalftoneArtStyle = HalftoneArtStyleKey.current.resolve(Theme.current)

internal fun defaultHalftoneArtStyle(theme: Theme): HalftoneArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return HalftoneArtStyle(
        colors = null,
        pitch = spacing(steps = 4f),
        maxDot = MAX_DOT,
        minDot = MIN_DOT,
        alpha = theme.darkAware(light = ALPHA_LIGHT, dark = ALPHA_DARK),
        pattern = HalftonePattern.Hex,
        shape = HalftoneShape.Circle,
        angle = ANGLE,
        gradientAngle = GRADIENT_ANGLE,
        waves = WAVES,
        wavelengths = listOf(spacing(steps = 64f), spacing(steps = 90f)),
        speeds = listOf(FIRST_SPEED, SECOND_SPEED),
        orbit = ORBIT,
        period = long * PERIOD,
        wanderPeriod = long * WANDER_PERIOD,
        pointerStrength = POINTER_STRENGTH,
        pointerRadius = spacing(steps = 30f),
        pointerWavelength = spacing(steps = 12f),
    )
}
