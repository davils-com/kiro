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

package com.davils.kiro.ui.audio.meter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.audio.tenthsOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val SMALL: Float = 0.8f
private const val TABULAR: String = "tnum"

/**
 * Derives the style of a [CorrelationMeter] from the tokens of a theme.
 *
 * Register an implementation under [CorrelationMeterStyleKey] in the components of a theme to change how every
 * correlation meter looks.
 *
 * @since 1.0.0
 */
public fun interface CorrelationMeterStyles {
    /**
     * Returns the style of a correlation meter for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the correlation meter.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): CorrelationMeterStyle

    /**
     * Holds the default [CorrelationMeterStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a correlation meter.
         *
         * @since 1.0.0
         */
        public val Default: CorrelationMeterStyles = CorrelationMeterStyles(::defaultCorrelationMeterStyle)
    }
}

/**
 * The key of the styles of a [CorrelationMeter] in the components of a theme, with [CorrelationMeterStyles.Default] as
 * its default.
 *
 * @since 1.0.0
 */
public val CorrelationMeterStyleKey: StyleKey<CorrelationMeterStyles> =
    StyleKey(name = "correlation-meter") { _ -> CorrelationMeterStyles.Default }

/**
 * Returns the style of a [CorrelationMeter] in the current theme, with the overrides of its components.
 *
 * @return the style of the correlation meter.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun correlationMeterStyle(): CorrelationMeterStyle = CorrelationMeterStyleKey.current.resolve(Theme.current)

internal fun defaultCorrelationMeterStyle(theme: Theme): CorrelationMeterStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    return CorrelationMeterStyle(
        length = spacing(steps = 56f),
        thickness = spacing(steps = 2f),
        radius = spacing(steps = 0.5f),
        track = colors.muted,
        positiveColor = colors.status.success.foreground,
        negativeColor = colors.status.error.foreground,
        centerColor = colors.mutedForeground,
        centerWidth = theme.borders.thin,
        labelGap = spacing(steps = 1f),
        labelStyle = TextStyle(
            fontFamily = theme.typography.families.mono,
            fontSize = theme.typography.scale.xs.fontSize * SMALL,
            fontFeatureSettings = TABULAR,
        ),
        labelColor = colors.mutedForeground,
        negative = Messages.NEGATIVE,
        center = Messages.ZERO,
        positive = Messages.POSITIVE,
        response = theme.motion.durations.short,
        motion = theme.motion,
        readout = { value -> tenthsOf(level = value, signed = true) },
        description = Messages.CORRELATION,
    )
}
