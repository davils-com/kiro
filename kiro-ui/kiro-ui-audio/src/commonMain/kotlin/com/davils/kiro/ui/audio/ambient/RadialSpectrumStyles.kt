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
private const val INNER: Float = 0.46f
private const val REACH: Float = 0.5f
private const val BAR_WIDTH: Float = 0.55f
private const val START: Float = -90f
private const val PULSE: Float = 0.06f
private const val REST: Float = 0.03f

/**
 * Derives the style of a [RadialSpectrum] from the tokens of a theme.
 *
 * Register an implementation under [RadialSpectrumStyleKey] in the components of a theme to change how every radial
 * spectrum looks.
 *
 * @since 1.0.0
 */
public fun interface RadialSpectrumStyles {
    /**
     * Returns the style of a radial spectrum for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the radial spectrum.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): RadialSpectrumStyle

    /**
     * Holds the default [RadialSpectrumStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a radial spectrum.
         *
         * @since 1.0.0
         */
        public val Default: RadialSpectrumStyles = RadialSpectrumStyles(::defaultRadialSpectrumStyle)
    }
}

/**
 * The key of the styles of a [RadialSpectrum] in the components of a theme, with [RadialSpectrumStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val RadialSpectrumStyleKey: StyleKey<RadialSpectrumStyles> =
    StyleKey(name = "radial-spectrum") { _ -> RadialSpectrumStyles.Default }

/**
 * Returns the style of a [RadialSpectrum] in the current theme, with the overrides of its components.
 *
 * @return the style of the radial spectrum.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun radialSpectrumStyle(): RadialSpectrumStyle = RadialSpectrumStyleKey.current.resolve(Theme.current)

internal fun defaultRadialSpectrumStyle(theme: Theme): RadialSpectrumStyle {
    val spacing = theme.spacing
    val chart = theme.colors.chart
    return RadialSpectrumStyle(
        size = spacing(steps = 72f),
        range = DecibelRange(floor = Decibels(FLOOR), ceiling = Decibels(CEILING)),
        inner = INNER,
        reach = REACH,
        barWidth = BAR_WIDTH,
        colors = listOf(chart.first, chart.second, chart.third, chart.fourth, chart.fifth),
        symmetry = RadialSymmetry.Mirrored,
        startAngle = START,
        ringColor = theme.colors.border,
        ringWidth = theme.borders.thick,
        pulse = PULSE,
        capColor = theme.colors.foreground,
        capSize = spacing(steps = 0.75f),
        rest = REST,
        description = Messages.RADIAL_SPECTRUM,
    )
}
