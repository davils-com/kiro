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

private const val LEVELS: Int = 6
private const val MIN_ALPHA_LIGHT: Float = 0.08f
private const val MIN_ALPHA_DARK: Float = 0.06f
private const val MAX_ALPHA_LIGHT: Float = 0.7f
private const val MAX_ALPHA_DARK: Float = 0.55f
private const val PERIOD: Int = 8
private const val STRUCTURE: Float = 0.6f
private const val DRIFT: Int = 40
private const val ACCENT_SHARE: Float = 0.08f
private const val MAX_CELLS: Int = 10_000

/**
 * Derives the style of a [FlickeringGridArt] from the tokens of a theme.
 *
 * Register an implementation under [FlickeringGridArtStyleKey] in the components of a theme to change how every
 * [FlickeringGridArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface FlickeringGridArtStyles {
    /**
     * Returns the style of a [FlickeringGridArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): FlickeringGridArtStyle

    /**
     * Holds the default [FlickeringGridArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [FlickeringGridArt].
         *
         * @since 1.0.0
         */
        public val Default: FlickeringGridArtStyles = FlickeringGridArtStyles(::defaultFlickeringGridArtStyle)
    }
}

/**
 * The key of the styles of a [FlickeringGridArt] in the components of a theme, with
 * [FlickeringGridArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val FlickeringGridArtStyleKey: StyleKey<FlickeringGridArtStyles> =
    StyleKey(name = "flickering-grid-art") { _ -> FlickeringGridArtStyles.Default }

/**
 * Returns the style of a [FlickeringGridArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun flickeringGridArtStyle(): FlickeringGridArtStyle = FlickeringGridArtStyleKey.current.resolve(Theme.current)

internal fun defaultFlickeringGridArtStyle(theme: Theme): FlickeringGridArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return FlickeringGridArtStyle(
        square = spacing(steps = 1f),
        gap = spacing(steps = 1.5f),
        levels = LEVELS,
        minAlpha = theme.darkAware(light = MIN_ALPHA_LIGHT, dark = MIN_ALPHA_DARK),
        maxAlpha = theme.darkAware(light = MAX_ALPHA_LIGHT, dark = MAX_ALPHA_DARK),
        period = long * PERIOD,
        structure = STRUCTURE,
        fieldSize = spacing(steps = 48f),
        drift = long * DRIFT,
        accentShare = ACCENT_SHARE,
        maxCells = MAX_CELLS,
        salt = 0,
        color = null,
        accent = null,
        background = null,
    )
}
