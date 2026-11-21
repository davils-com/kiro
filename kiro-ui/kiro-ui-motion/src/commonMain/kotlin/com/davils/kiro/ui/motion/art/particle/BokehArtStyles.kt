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

private const val COUNT: Int = 9
private const val PERIOD: Int = 120
private const val BREATHE: Float = 0.08f
private const val BREATHE_PERIOD: Int = 24
private const val ALPHA_LIGHT: Float = 0.14f
private const val ALPHA_DARK: Float = 0.22f
private const val ALPHA_FLOOR: Float = 0.35f
private const val SOFTNESS: Float = 0.5f
private const val RIM_ALPHA: Float = 0.6f

/**
 * Derives the style of a [BokehArt] from the tokens of a theme.
 *
 * Register an implementation under [BokehArtStyleKey] in the components of a theme to change how every bokeh art looks.
 *
 * @since 1.0.0
 */
public fun interface BokehArtStyles {
    /**
     * Returns the style of a bokeh art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the bokeh art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): BokehArtStyle

    /**
     * Holds the default [BokehArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a bokeh art.
         *
         * @since 1.0.0
         */
        public val Default: BokehArtStyles = BokehArtStyles(::defaultBokehArtStyle)
    }
}

/**
 * The key of the styles of a [BokehArt] in the components of a theme, with [BokehArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val BokehArtStyleKey: StyleKey<BokehArtStyles> =
    StyleKey(name = "bokeh-art") { _ -> BokehArtStyles.Default }

/**
 * Returns the style of a [BokehArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the bokeh art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun bokehArtStyle(): BokehArtStyle = BokehArtStyleKey.current.resolve(Theme.current)

internal fun defaultBokehArtStyle(theme: Theme): BokehArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return BokehArtStyle(
        count = COUNT,
        minRadius = spacing(steps = 10f),
        maxRadius = spacing(steps = 30f),
        wander = spacing(steps = 14f),
        period = long * PERIOD,
        breathe = BREATHE,
        breathePeriod = long * BREATHE_PERIOD,
        alpha = theme.darkAware(light = ALPHA_LIGHT, dark = ALPHA_DARK),
        alphaFloor = ALPHA_FLOOR,
        softness = SOFTNESS,
        rimWidth = spacing(steps = 0.25f),
        rimAlpha = RIM_ALPHA,
        colors = null,
    )
}
