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

private const val JITTER: Float = 0.38f
private const val DRIFT: Float = 0.06f
private const val DRIFT_PERIOD: Int = 90
private const val RELIEF: Float = 0.9f
private const val TINT_LIGHT: Float = 0.16f
private const val TINT_DARK: Float = 0.3f
private const val CONTRAST: Float = 1.2f
private const val ELEVATION: Float = 35f
private const val LIGHT_PERIOD: Int = 200
private const val EDGE_ALPHA_LIGHT: Float = 0.5f
private const val EDGE_ALPHA_DARK: Float = 0.35f

/**
 * Derives the style of a [LowPolyArt] from the tokens of a theme.
 *
 * Register an implementation under [LowPolyArtStyleKey] in the components of a theme to change how every low poly art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface LowPolyArtStyles {
    /**
     * Returns the style of a low poly art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the low poly art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): LowPolyArtStyle

    /**
     * Holds the default [LowPolyArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a low poly art.
         *
         * @since 1.0.0
         */
        public val Default: LowPolyArtStyles = LowPolyArtStyles(::defaultLowPolyArtStyle)
    }
}

/**
 * The key of the styles of a [LowPolyArt] in the components of a theme, with [LowPolyArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val LowPolyArtStyleKey: StyleKey<LowPolyArtStyles> =
    StyleKey(name = "low-poly-art") { _ -> LowPolyArtStyles.Default }

/**
 * Returns the style of a [LowPolyArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the low poly art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun lowPolyArtStyle(): LowPolyArtStyle = LowPolyArtStyleKey.current.resolve(Theme.current)

internal fun defaultLowPolyArtStyle(theme: Theme): LowPolyArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return LowPolyArtStyle(
        cell = spacing(steps = 16f),
        jitter = JITTER,
        drift = DRIFT,
        driftPeriod = long * DRIFT_PERIOD,
        relief = RELIEF,
        reliefScale = spacing(steps = 70f),
        tint = theme.darkAware(light = TINT_LIGHT, dark = TINT_DARK),
        contrast = CONTRAST,
        elevation = ELEVATION,
        lightPeriod = long * LIGHT_PERIOD,
        edgeWidth = spacing(steps = 0.25f),
        edgeAlpha = theme.darkAware(light = EDGE_ALPHA_LIGHT, dark = EDGE_ALPHA_DARK),
        colors = null,
        background = null,
        edge = null,
    )
}
