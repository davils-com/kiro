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

package com.davils.kiro.ui.audio.spectrum

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.audio.hertzOf
import com.davils.kiro.ui.audio.signal.DecibelRange
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.Colors
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import kotlin.math.abs

private const val FLOOR: Float = -84f
private const val CEILING: Float = 0f
private const val GRID_ALPHA: Float = 0.35f
private const val SMALL: Float = 0.8f

private val frequencies: List<Float> = listOf(50f, 100f, 200f, 500f, 1_000f, 2_000f, 5_000f, 10_000f)

/**
 * Derives the style of a [Spectrogram] from the tokens of a theme.
 *
 * Register an implementation under [SpectrogramStyleKey] in the components of a theme to change how every spectrogram
 * looks.
 *
 * @since 1.0.0
 */
public fun interface SpectrogramStyles {
    /**
     * Returns the style of a spectrogram for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the spectrogram.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SpectrogramStyle

    /**
     * Holds the default [SpectrogramStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a spectrogram.
         *
         * @since 1.0.0
         */
        public val Default: SpectrogramStyles = SpectrogramStyles(::defaultSpectrogramStyle)
    }
}

/**
 * The key of the styles of a [Spectrogram] in the components of a theme, with [SpectrogramStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val SpectrogramStyleKey: StyleKey<SpectrogramStyles> =
    StyleKey(name = "spectrogram") { _ -> SpectrogramStyles.Default }

/**
 * Returns the style of a [Spectrogram] in the current theme, with the overrides of its components.
 *
 * @return the style of the spectrogram.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun spectrogramStyle(): SpectrogramStyle = SpectrogramStyleKey.current.resolve(Theme.current)

internal fun defaultSpectrogramStyle(theme: Theme): SpectrogramStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    return SpectrogramStyle(
        height = spacing(steps = 48f),
        range = DecibelRange(floor = Decibels(FLOOR), ceiling = Decibels(CEILING)),
        ramp = rampOf(colors),
        frequencyMarks = frequencies,
        gridColor = colors.background.copy(alpha = GRID_ALPHA),
        gridWidth = theme.borders.thin,
        labelGap = spacing(steps = 1f),
        labelStyle = TextStyle(
            fontFamily = theme.typography.families.mono,
            fontSize = theme.typography.scale.xs.fontSize * SMALL,
        ),
        labelColor = colors.mutedForeground,
        frequency = ::hertzOf,
        radius = theme.radii.sm,
        description = Messages.SPECTROGRAM,
    )
}

private fun rampOf(colors: Colors): List<Color> {
    val chart = colors.chart
    val shades = listOf(chart.first, chart.second, chart.third, chart.fourth, chart.fifth)
    val background = colors.background.luminance()
    val ordered = shades.sortedBy { shade -> abs(shade.luminance() - background) }
    return listOf(colors.background) + ordered + colors.foreground
}
