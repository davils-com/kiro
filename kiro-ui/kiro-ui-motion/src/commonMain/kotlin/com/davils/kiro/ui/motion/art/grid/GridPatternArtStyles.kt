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

private const val LINE_ALPHA_LIGHT: Float = 0.35f
private const val LINE_ALPHA_DARK: Float = 0.5f
private const val MAJOR_EVERY: Int = 4
private const val MAJOR_ALPHA_LIGHT: Float = 0.6f
private const val MAJOR_ALPHA_DARK: Float = 0.8f
private const val HIGHLIGHTS: Int = 7
private const val HIGHLIGHT_ALPHA_LIGHT: Float = 0.2f
private const val HIGHLIGHT_ALPHA_DARK: Float = 0.3f
private const val PERIOD: Int = 24
private const val POINTER_ALPHA_LIGHT: Float = 0.12f
private const val POINTER_ALPHA_DARK: Float = 0.22f
private const val POINTER_OUTLINE_ALPHA_LIGHT: Float = 0.45f
private const val POINTER_OUTLINE_ALPHA_DARK: Float = 0.6f

/**
 * Derives the style of a [GridPatternArt] from the tokens of a theme.
 *
 * Register an implementation under [GridPatternArtStyleKey] in the components of a theme to change how every
 * [GridPatternArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface GridPatternArtStyles {
    /**
     * Returns the style of a [GridPatternArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): GridPatternArtStyle

    /**
     * Holds the default [GridPatternArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [GridPatternArt].
         *
         * @since 1.0.0
         */
        public val Default: GridPatternArtStyles = GridPatternArtStyles(::defaultGridPatternArtStyle)
    }
}

/**
 * The key of the styles of a [GridPatternArt] in the components of a theme, with
 * [GridPatternArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val GridPatternArtStyleKey: StyleKey<GridPatternArtStyles> =
    StyleKey(name = "grid-pattern-art") { _ -> GridPatternArtStyles.Default }

/**
 * Returns the style of a [GridPatternArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun gridPatternArtStyle(): GridPatternArtStyle = GridPatternArtStyleKey.current.resolve(Theme.current)

internal fun defaultGridPatternArtStyle(theme: Theme): GridPatternArtStyle {
    val spacing = theme.spacing
    return GridPatternArtStyle(
        kind = GridPatternKind.Lines,
        cell = spacing(steps = 12f),
        lineWidth = theme.borders.thin,
        mark = spacing(steps = 0.5f),
        lineAlpha = theme.darkAware(light = LINE_ALPHA_LIGHT, dark = LINE_ALPHA_DARK),
        majorEvery = MAJOR_EVERY,
        majorAlpha = theme.darkAware(light = MAJOR_ALPHA_LIGHT, dark = MAJOR_ALPHA_DARK),
        highlights = HIGHLIGHTS,
        highlightAlpha = theme.darkAware(light = HIGHLIGHT_ALPHA_LIGHT, dark = HIGHLIGHT_ALPHA_DARK),
        radius = theme.radii.sm,
        inset = spacing(steps = 0.5f),
        period = theme.motion.durations.long * PERIOD,
        easing = theme.motion.easings.ease,
        pointerAlpha = theme.darkAware(light = POINTER_ALPHA_LIGHT, dark = POINTER_ALPHA_DARK),
        pointerOutlineAlpha = theme.darkAware(light = POINTER_OUTLINE_ALPHA_LIGHT, dark = POINTER_OUTLINE_ALPHA_DARK),
        salt = 0,
        colors = null,
        line = null,
        pointer = null,
        background = null,
    )
}
