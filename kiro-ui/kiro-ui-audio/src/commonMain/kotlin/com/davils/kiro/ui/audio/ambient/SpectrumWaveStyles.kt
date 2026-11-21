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
private const val FALLOFF: Float = 0.2f
private const val FILL_ALPHA: Float = 0.32f
private const val LINE_ALPHA: Float = 0.9f
private const val REST: Float = 0.01f

/**
 * Derives the style of a [SpectrumWave] from the tokens of a theme.
 *
 * Register an implementation under [SpectrumWaveStyleKey] in the components of a theme to change how every spectrum
 * wave looks.
 *
 * @since 1.0.0
 */
public fun interface SpectrumWaveStyles {
    /**
     * Returns the style of a spectrum wave for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the spectrum wave.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SpectrumWaveStyle

    /**
     * Holds the default [SpectrumWaveStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a spectrum wave.
         *
         * @since 1.0.0
         */
        public val Default: SpectrumWaveStyles = SpectrumWaveStyles(::defaultSpectrumWaveStyle)
    }
}

/**
 * The key of the styles of a [SpectrumWave] in the components of a theme, with [SpectrumWaveStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val SpectrumWaveStyleKey: StyleKey<SpectrumWaveStyles> =
    StyleKey(name = "spectrum-wave") { _ -> SpectrumWaveStyles.Default }

/**
 * Returns the style of a [SpectrumWave] in the current theme, with the overrides of its components.
 *
 * @return the style of the spectrum wave.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun spectrumWaveStyle(): SpectrumWaveStyle = SpectrumWaveStyleKey.current.resolve(Theme.current)

internal fun defaultSpectrumWaveStyle(theme: Theme): SpectrumWaveStyle {
    val chart = theme.colors.chart
    return SpectrumWaveStyle(
        height = theme.spacing(steps = 40f),
        range = DecibelRange(floor = Decibels(FLOOR), ceiling = Decibels(CEILING)),
        colors = listOf(chart.first, chart.second, chart.third),
        falloff = FALLOFF,
        fillAlpha = FILL_ALPHA,
        lineWidth = theme.borders.thick,
        lineAlpha = LINE_ALPHA,
        baseline = Baseline.Bottom,
        rest = REST,
        description = Messages.SPECTRUM_WAVE,
    )
}
