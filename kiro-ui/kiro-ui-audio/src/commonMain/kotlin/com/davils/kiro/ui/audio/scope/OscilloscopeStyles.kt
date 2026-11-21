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

package com.davils.kiro.ui.audio.scope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val COLUMNS: Int = 8
private const val ROWS: Int = 4
private const val GLOW_ALPHA: Float = 0.25f
private const val GAIN: Float = 0.9f

/**
 * Derives the style of a [Oscilloscope] from the tokens of a theme.
 *
 * Register an implementation under [OscilloscopeStyleKey] in the components of a theme to change how every oscilloscope
 * looks.
 *
 * @since 1.0.0
 */
public fun interface OscilloscopeStyles {
    /**
     * Returns the style of a oscilloscope for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the oscilloscope.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): OscilloscopeStyle

    /**
     * Holds the default [OscilloscopeStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a oscilloscope.
         *
         * @since 1.0.0
         */
        public val Default: OscilloscopeStyles = OscilloscopeStyles(::defaultOscilloscopeStyle)
    }
}

/**
 * The key of the styles of a [Oscilloscope] in the components of a theme, with [OscilloscopeStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val OscilloscopeStyleKey: StyleKey<OscilloscopeStyles> =
    StyleKey(name = "oscilloscope") { _ -> OscilloscopeStyles.Default }

/**
 * Returns the style of a [Oscilloscope] in the current theme, with the overrides of its components.
 *
 * @return the style of the oscilloscope.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun oscilloscopeStyle(): OscilloscopeStyle = OscilloscopeStyleKey.current.resolve(Theme.current)

internal fun defaultOscilloscopeStyle(theme: Theme): OscilloscopeStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    return OscilloscopeStyle(
        height = spacing(steps = 32f),
        columns = COLUMNS,
        rows = ROWS,
        gridColor = colors.border,
        centerColor = colors.line,
        gridWidth = theme.borders.thin,
        layout = ScopeLayout.Overlaid,
        leftColor = colors.chart.second,
        rightColor = colors.status.info.foreground,
        traceWidth = theme.borders.thick,
        glowWidth = spacing(steps = 1.5f),
        glowAlpha = GLOW_ALPHA,
        gain = GAIN,
        description = Messages.OSCILLOSCOPE,
    )
}
