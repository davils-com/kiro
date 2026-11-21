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
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val TRACE_ALPHA: Float = 0.55f
private const val GAIN: Float = 1.6f
private const val SMALL: Float = 0.8f

/**
 * Derives the style of a [Goniometer] from the tokens of a theme.
 *
 * Register an implementation under [GoniometerStyleKey] in the components of a theme to change how every goniometer
 * looks.
 *
 * @since 1.0.0
 */
public fun interface GoniometerStyles {
    /**
     * Returns the style of a goniometer for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the goniometer.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): GoniometerStyle

    /**
     * Holds the default [GoniometerStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a goniometer.
         *
         * @since 1.0.0
         */
        public val Default: GoniometerStyles = GoniometerStyles(::defaultGoniometerStyle)
    }
}

/**
 * The key of the styles of a [Goniometer] in the components of a theme, with [GoniometerStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val GoniometerStyleKey: StyleKey<GoniometerStyles> =
    StyleKey(name = "goniometer") { _ -> GoniometerStyles.Default }

/**
 * Returns the style of a [Goniometer] in the current theme, with the overrides of its components.
 *
 * @return the style of the goniometer.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun goniometerStyle(): GoniometerStyle = GoniometerStyleKey.current.resolve(Theme.current)

internal fun defaultGoniometerStyle(theme: Theme): GoniometerStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    return GoniometerStyle(
        size = spacing(steps = 48f),
        grid = ScopeGrid.Round,
        gridColor = colors.border,
        axisColor = colors.line,
        gridWidth = theme.borders.thin,
        labelGap = spacing(steps = 1f),
        labelStyle = TextStyle(
            fontFamily = theme.typography.families.mono,
            fontSize = theme.typography.scale.xs.fontSize * SMALL,
        ),
        labelColor = colors.mutedForeground,
        left = Messages.LEFT,
        right = Messages.RIGHT,
        mid = Messages.MID,
        side = Messages.SIDE,
        trace = ScopeTrace.Dots,
        traceColor = colors.chart.first,
        traceAlpha = TRACE_ALPHA,
        dotSize = spacing(steps = 0.5f),
        lineWidth = theme.borders.thin,
        gain = GAIN,
        description = Messages.GONIOMETER,
    )
}
