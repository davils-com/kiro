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
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware

private const val GLYPHS: String = "0123456789ABCDEF<>/=+*{}"
private const val COLUMN_SHARE: Float = 0.45f
private const val TRAIL: Int = 14
private const val PAUSE: Int = 10
private const val PERIOD: Int = 1
private const val SPEED_SPREAD: Float = 0.5f
private const val CHANGE_PERIOD: Int = 6
private const val TRAIL_ALPHA_LIGHT: Float = 0.5f
private const val TRAIL_ALPHA_DARK: Float = 0.7f
private const val HEAD_ALPHA: Float = 1f
private const val GLYPH_WIDTH: Float = 0.5f
private const val GLYPH_HEIGHT: Float = 0.62f

/**
 * Derives the style of a [MatrixArt] from the tokens of a theme.
 *
 * Register an implementation under [MatrixArtStyleKey] in the components of a theme to change how every
 * [MatrixArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface MatrixArtStyles {
    /**
     * Returns the style of a [MatrixArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): MatrixArtStyle

    /**
     * Holds the default [MatrixArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [MatrixArt].
         *
         * @since 1.0.0
         */
        public val Default: MatrixArtStyles = MatrixArtStyles(::defaultMatrixArtStyle)
    }
}

/**
 * The key of the styles of a [MatrixArt] in the components of a theme, with
 * [MatrixArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val MatrixArtStyleKey: StyleKey<MatrixArtStyles> =
    StyleKey(name = "matrix-art") { _ -> MatrixArtStyles.Default }

/**
 * Returns the style of a [MatrixArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun matrixArtStyle(): MatrixArtStyle = MatrixArtStyleKey.current.resolve(Theme.current)

internal fun defaultMatrixArtStyle(theme: Theme): MatrixArtStyle {
    val long = theme.motion.durations.long
    val typography = theme.typography
    return MatrixArtStyle(
        cell = theme.spacing(steps = 5f),
        textStyle = TextStyle(fontFamily = typography.families.mono, fontSize = typography.scale.xs.fontSize),
        glyphs = GLYPHS,
        columnShare = COLUMN_SHARE,
        trail = TRAIL,
        pause = PAUSE,
        period = long * PERIOD,
        speedSpread = SPEED_SPREAD,
        changePeriod = long * CHANGE_PERIOD,
        trailAlpha = theme.darkAware(light = TRAIL_ALPHA_LIGHT, dark = TRAIL_ALPHA_DARK),
        headAlpha = HEAD_ALPHA,
        glyphWidth = GLYPH_WIDTH,
        glyphHeight = GLYPH_HEIGHT,
        salt = 0,
        colors = null,
        head = null,
        background = null,
    )
}
