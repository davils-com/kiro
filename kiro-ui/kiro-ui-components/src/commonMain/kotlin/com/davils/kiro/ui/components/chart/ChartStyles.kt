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

package com.davils.kiro.ui.components.chart

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val HALF: Float = 0.5f
private const val BAND_PADDING: Float = 0.1f
private const val OUTER_RADIUS: Float = 0.8f
private const val ASPECT_VIDEO: Float = 16f / 9f
private const val ANIMATION_MILLIS: Int = 1500
private const val TABULAR: String = "tnum"

/**
 * Derives the style of the charts in a [ChartContainer] from the tokens of a theme.
 *
 * Register an implementation under [ChartStyleKey] in the components of a theme to change how every chart looks.
 *
 * @since 1.0.0
 */
public fun interface ChartStyles {
    /**
     * Returns the style of a chart for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the chart.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ChartStyle

    /**
     * Holds the default [ChartStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a chart, with the five chart colors of the theme as its palette.
         *
         * @since 1.0.0
         */
        public val Default: ChartStyles = ChartStyles(::defaultChartStyle)
    }
}

/**
 * Returns the style of a chart in the current theme, with the overrides of its components.
 *
 * @return the style of the chart.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun chartStyle(): ChartStyle = ChartStyleKey.current.resolve(theme)

internal fun defaultChartStyle(theme: Theme): ChartStyle {
    val colors = theme.colors
    val chart = colors.chart
    val xs = theme.textStyleOf(size = theme.typography.scale.xs)
    return ChartStyle(
        palette = listOf(chart.first, chart.second, chart.third, chart.fourth, chart.fifth),
        textStyle = xs,
        tickColor = colors.mutedForeground,
        labelColor = colors.foreground,
        gridColor = colors.border.copy(alpha = HALF),
        polarGridColor = colors.border,
        cursorFill = colors.muted,
        cursorLine = colors.border,
        trackColor = colors.muted,
        tickMargin = 8.dp,
        margin = 5.dp,
        barGap = 4.dp,
        bandPadding = BAND_PADDING,
        dotRadius = 3.dp,
        activeDotRadius = 4.dp,
        outerRadius = OUTER_RADIUS,
        labelOffset = 20.dp,
        pointInset = 12.dp,
        aspectRatio = ASPECT_VIDEO,
        animationMillis = ANIMATION_MILLIS,
        animationEasing = CubicBezierEasing(a = 0.25f, b = 0.1f, c = 0.25f, d = 1f),
        tooltip = defaultTooltipStyle(theme),
        legend = ChartLegendStyle(
            gap = 16.dp,
            itemGap = 6.dp,
            swatchSize = 8.dp,
            swatchShape = RoundedCornerShape(2.dp),
            spacing = 12.dp,
            iconSize = 12.dp,
            iconColor = colors.mutedForeground,
            textStyle = xs,
        ),
    )
}

private fun defaultTooltipStyle(theme: Theme): ChartTooltipStyle {
    val colors = theme.colors
    val xs = theme.textStyleOf(size = theme.typography.scale.xs)
    val mono = theme.textStyleOf(
        size = theme.typography.scale.xs,
        weight = FontWeight.Medium,
        lineHeight = theme.typography.leading.none,
        family = theme.typography.families.mono,
    )
    return ChartTooltipStyle(
        shape = RoundedCornerShape(theme.radii.lg),
        container = colors.background,
        border = colors.border.copy(alpha = HALF),
        shadow = theme.shadows.xl,
        padding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
        gap = 6.dp,
        minWidth = 128.dp,
        labelStyle = theme.textStyleOf(size = theme.typography.scale.xs, weight = FontWeight.Medium),
        nameStyle = xs.copy(lineHeight = theme.typography.leading.none),
        nameColor = colors.mutedForeground,
        valueStyle = mono.copy(fontFeatureSettings = TABULAR),
        valueColor = colors.foreground,
        indicatorSize = 10.dp,
        lineWidth = 4.dp,
        dashWidth = 1.5.dp,
        indicatorShape = RoundedCornerShape(2.dp),
        iconSize = 10.dp,
        offset = 10.dp,
    )
}

/**
 * The key of the styles of a chart in the components of a theme, with [ChartStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val ChartStyleKey: StyleKey<ChartStyles> = StyleKey(name = "chart") { _ -> ChartStyles.Default }
