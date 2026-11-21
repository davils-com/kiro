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

package com.davils.kiro.ui.audio.waveform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.audio.clockOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import kotlin.time.Duration.Companion.seconds

private const val PEAK_ALPHA: Float = 0.45f
private const val SEEK_SECONDS: Int = 5
private const val TABULAR: String = "tnum"

/**
 * Derives the style of a [Waveform] from the tokens of a theme.
 *
 * Register an implementation under [WaveformStyleKey] in the components of a theme to change how every waveform looks.
 *
 * @since 1.0.0
 */
public fun interface WaveformStyles {
    /**
     * Returns the style of a waveform for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the waveform.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): WaveformStyle

    /**
     * Holds the default [WaveformStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a waveform.
         *
         * @since 1.0.0
         */
        public val Default: WaveformStyles = WaveformStyles(::defaultWaveformStyle)
    }
}

/**
 * The key of the styles of a [Waveform] in the components of a theme, with [WaveformStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val WaveformStyleKey: StyleKey<WaveformStyles> =
    StyleKey(name = "waveform") { _ -> WaveformStyles.Default }

/**
 * Returns the style of a [Waveform] in the current theme, with the overrides of its components.
 *
 * @return the style of the waveform.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun waveformStyle(): WaveformStyle = WaveformStyleKey.current.resolve(Theme.current)

internal fun defaultWaveformStyle(theme: Theme): WaveformStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    return WaveformStyle(
        height = spacing(steps = 24f),
        layout = WaveformLayout.Combined,
        look = WaveformLook.Continuous,
        playedColor = colors.chart.first,
        unplayedColor = colors.mutedForeground,
        peakAlpha = PEAK_ALPHA,
        centerColor = colors.border,
        centerWidth = theme.borders.thin,
        channelGap = spacing(steps = 1f),
        barWidth = spacing(steps = 0.75f),
        barGap = spacing(steps = 0.5f),
        playheadColor = colors.foreground,
        playheadWidth = theme.borders.thick,
        hoverColor = colors.mutedForeground,
        timeStyle = TextStyle(
            fontFamily = theme.typography.families.mono,
            fontSize = theme.typography.scale.xs.fontSize,
            fontFeatureSettings = TABULAR,
        ),
        timeColor = colors.foreground,
        time = ::clockOf,
        seekStep = SEEK_SECONDS.seconds,
        description = Messages.WAVEFORM,
    )
}
