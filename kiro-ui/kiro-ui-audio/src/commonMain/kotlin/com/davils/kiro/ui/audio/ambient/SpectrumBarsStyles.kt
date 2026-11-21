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

package com.davils.kiro.ui.audio.ambient

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.audio.signal.DecibelRange
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val FLOOR: Float = -66f
private const val CEILING: Float = 0f
private const val GAP: Float = 0.28f
private const val REFLECTION: Float = 0.22f
private const val REFLECTION_ALPHA: Float = 0.3f
private const val REST: Float = 0.015f

/**
 * Derives the style of a [SpectrumBars] from the tokens of a theme.
 *
 * Register an implementation under [SpectrumBarsStyleKey] in the components of a theme to change how every spectrum
 * bars looks.
 *
 * @since 1.0.0
 */
public fun interface SpectrumBarsStyles {
    /**
     * Returns the style of a spectrum bars for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the spectrum bars.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SpectrumBarsStyle

    /**
     * Holds the default [SpectrumBarsStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a spectrum bars.
         *
         * @since 1.0.0
         */
        public val Default: SpectrumBarsStyles = SpectrumBarsStyles(::defaultSpectrumBarsStyle)
    }
}

/**
 * The key of the styles of a [SpectrumBars] in the components of a theme, with [SpectrumBarsStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val SpectrumBarsStyleKey: StyleKey<SpectrumBarsStyles> =
    StyleKey(name = "spectrum-bars") { _ -> SpectrumBarsStyles.Default }

/**
 * Returns the style of a [SpectrumBars] in the current theme, with the overrides of its components.
 *
 * @return the style of the spectrum bars.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun spectrumBarsStyle(): SpectrumBarsStyle = SpectrumBarsStyleKey.current.resolve(Theme.current)

internal fun defaultSpectrumBarsStyle(theme: Theme): SpectrumBarsStyle {
    val spacing = theme.spacing
    val chart = theme.colors.chart
    return SpectrumBarsStyle(
        height = spacing(steps = 48f),
        range = DecibelRange(floor = Decibels(FLOOR), ceiling = Decibels(CEILING)),
        colors = listOf(chart.first, chart.second, chart.third),
        gap = GAP,
        radius = spacing(steps = 0.5f),
        look = BarLook.Solid,
        segment = spacing(steps = 1f),
        segmentGap = spacing(steps = 0.5f),
        baseline = Baseline.Bottom,
        capColor = theme.colors.foreground,
        capHeight = theme.borders.thick,
        capGap = theme.borders.thick,
        reflection = REFLECTION,
        reflectionAlpha = REFLECTION_ALPHA,
        rest = REST,
        description = Messages.SPECTRUM_BARS,
    )
}
