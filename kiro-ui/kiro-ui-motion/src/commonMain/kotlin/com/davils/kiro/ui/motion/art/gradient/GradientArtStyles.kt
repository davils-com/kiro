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

package com.davils.kiro.ui.motion.art.gradient

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware

private const val STRENGTH_LIGHT: Float = 0.2f
private const val STRENGTH_DARK: Float = 0.3f
private const val STEPS: Int = 24
private const val ROTATION: Int = 240
private const val ORBIT_PERIOD: Int = 160
private const val REACH: Float = 0.9f

/**
 * Derives the style of a [GradientArt] from the tokens of a theme.
 *
 * Register an implementation under [GradientArtStyleKey] in the components of a theme to change how every gradient art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface GradientArtStyles {
    /**
     * Returns the style of a gradient art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the gradient art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): GradientArtStyle

    /**
     * Holds the default [GradientArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a gradient art.
         *
         * @since 1.0.0
         */
        public val Default: GradientArtStyles = GradientArtStyles(::defaultGradientArtStyle)
    }
}

/**
 * The key of the styles of a [GradientArt] in the components of a theme, with [GradientArtStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val GradientArtStyleKey: StyleKey<GradientArtStyles> =
    StyleKey(name = "gradient-art") { _ -> GradientArtStyles.Default }

/**
 * Returns the style of a [GradientArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the gradient art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun gradientArtStyle(): GradientArtStyle = GradientArtStyleKey.current.resolve(Theme.current)

internal fun defaultGradientArtStyle(theme: Theme): GradientArtStyle {
    val long = theme.motion.durations.long
    return GradientArtStyle(
        kind = GradientKind.Linear,
        strength = theme.darkAware(light = STRENGTH_LIGHT, dark = STRENGTH_DARK),
        steps = STEPS,
        rotation = long * ROTATION,
        orbit = theme.spacing(steps = 30f),
        orbitPeriod = long * ORBIT_PERIOD,
        reach = REACH,
        colors = null,
        background = null,
    )
}
