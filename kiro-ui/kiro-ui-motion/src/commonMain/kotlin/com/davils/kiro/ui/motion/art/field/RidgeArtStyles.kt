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

private const val LINES: Int = 18
private const val LINE_ALPHA: Float = 0.85f
private const val SPREAD: Float = 0.16f
private const val WANDER: Float = 0.06f
private const val FLAT: Float = 0.02f
private const val FREQUENCY: Float = 5f
private const val OCTAVES: Int = 2
private const val SHARPNESS: Float = 1.6f
private const val TOP: Float = 0.5f
private const val BOTTOM: Float = 0.94f
private const val PERIOD: Int = 60
private const val EVOLUTION: Int = 200

/**
 * Derives the style of a [RidgeArt] from the tokens of a theme.
 *
 * Register an implementation under [RidgeArtStyleKey] in the components of a theme to change how every ridge art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface RidgeArtStyles {
    /**
     * Returns the style of a ridge art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the ridge art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): RidgeArtStyle

    /**
     * Holds the default [RidgeArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a ridge art.
         *
         * @since 1.0.0
         */
        public val Default: RidgeArtStyles = RidgeArtStyles(::defaultRidgeArtStyle)
    }
}

/**
 * The key of the styles of a [RidgeArt] in the components of a theme, with [RidgeArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val RidgeArtStyleKey: StyleKey<RidgeArtStyles> =
    StyleKey(name = "ridge-art") { _ -> RidgeArtStyles.Default }

/**
 * Returns the style of a [RidgeArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the ridge art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun ridgeArtStyle(): RidgeArtStyle = RidgeArtStyleKey.current.resolve(Theme.current)

internal fun defaultRidgeArtStyle(theme: Theme): RidgeArtStyle {
    val spacing = theme.spacing
    val durations = theme.motion.durations
    return RidgeArtStyle(
        colors = null,
        background = null,
        lines = LINES,
        lineWidth = spacing(steps = 0.25f),
        lineAlpha = LINE_ALPHA,
        step = spacing(steps = 1f),
        amplitude = spacing(steps = 12f),
        spread = SPREAD,
        wander = WANDER,
        flat = FLAT,
        frequency = FREQUENCY,
        octaves = OCTAVES,
        sharpness = SHARPNESS,
        top = TOP,
        bottom = BOTTOM,
        period = durations.long * PERIOD,
        evolution = durations.long * EVOLUTION,
        gradient = RidgeGradient.BackToFront,
        hasFill = true,
    )
}
