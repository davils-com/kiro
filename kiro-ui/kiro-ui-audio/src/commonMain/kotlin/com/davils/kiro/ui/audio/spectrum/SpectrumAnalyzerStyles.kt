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
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.audio.hertzOf
import com.davils.kiro.ui.audio.signal.DecibelRange
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.audio.tenthsOf
import com.davils.kiro.ui.audio.wholeDecibelsOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val FLOOR: Float = -84f
private const val CEILING: Float = 0f
private const val FILL_TOP: Float = 0.35f
private const val FILL_BOTTOM: Float = 0.02f
private const val SMALL: Float = 0.8f
private const val TABULAR: String = "tnum"

private val frequencies: List<Float> = listOf(20f, 50f, 100f, 200f, 500f, 1_000f, 2_000f, 5_000f, 10_000f, 20_000f)
private val levels: List<Float> = listOf(0f, -12f, -24f, -36f, -48f, -60f, -72f)

/**
 * Derives the style of a [SpectrumAnalyzer] from the tokens of a theme.
 *
 * Register an implementation under [SpectrumAnalyzerStyleKey] in the components of a theme to change how every spectrum
 * analyzer looks.
 *
 * @since 1.0.0
 */
public fun interface SpectrumAnalyzerStyles {
    /**
     * Returns the style of a spectrum analyzer for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the spectrum analyzer.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SpectrumAnalyzerStyle

    /**
     * Holds the default [SpectrumAnalyzerStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a spectrum analyzer.
         *
         * @since 1.0.0
         */
        public val Default: SpectrumAnalyzerStyles = SpectrumAnalyzerStyles(::defaultSpectrumAnalyzerStyle)
    }
}

/**
 * The key of the styles of a [SpectrumAnalyzer] in the components of a theme, with [SpectrumAnalyzerStyles.Default] as
 * its default.
 *
 * @since 1.0.0
 */
public val SpectrumAnalyzerStyleKey: StyleKey<SpectrumAnalyzerStyles> =
    StyleKey(name = "spectrum-analyzer") { _ -> SpectrumAnalyzerStyles.Default }

/**
 * Returns the style of a [SpectrumAnalyzer] in the current theme, with the overrides of its components.
 *
 * @return the style of the spectrum analyzer.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun spectrumAnalyzerStyle(): SpectrumAnalyzerStyle = SpectrumAnalyzerStyleKey.current.resolve(Theme.current)

internal fun defaultSpectrumAnalyzerStyle(theme: Theme): SpectrumAnalyzerStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    val mono = theme.typography.families.mono
    val small = TextStyle(fontFamily = mono, fontSize = theme.typography.scale.xs.fontSize * SMALL)
    return SpectrumAnalyzerStyle(
        height = spacing(steps = 56f),
        range = DecibelRange(floor = Decibels(FLOOR), ceiling = Decibels(CEILING)),
        frequencyMarks = frequencies,
        decibelMarks = levels.map(::Decibels),
        gridColor = colors.border,
        gridWidth = theme.borders.thin,
        labelGap = spacing(steps = 1f),
        labelStyle = small,
        labelColor = colors.mutedForeground,
        frequency = ::hertzOf,
        decibels = { level -> wholeDecibelsOf(level.value) },
        curve = SpectrumCurve.Smooth,
        lineColor = colors.chart.first,
        lineWidth = theme.borders.thick,
        fillTopAlpha = FILL_TOP,
        fillBottomAlpha = FILL_BOTTOM,
        holdColor = colors.foreground,
        holdWidth = theme.borders.thin,
        crosshairColor = colors.mutedForeground,
        readoutStyle = TextStyle(
            fontFamily = mono,
            fontSize = theme.typography.scale.xs.fontSize,
            fontFeatureSettings = TABULAR,
        ),
        readoutColor = colors.popoverForeground,
        readoutContainer = colors.popover,
        readoutPadding = spacing(steps = 1.5f),
        readoutRadius = theme.radii.sm,
        readout = { frequency, level -> "${hertzOf(frequency)}Hz  ${tenthsOf(level.value)} dB" },
        description = Messages.SPECTRUM_ANALYZER,
    )
}
