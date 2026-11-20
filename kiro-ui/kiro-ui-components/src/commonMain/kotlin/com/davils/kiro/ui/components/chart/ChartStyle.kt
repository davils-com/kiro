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

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * How the charts in a [ChartContainer] look: their colors, text style, margins, marks, animation, tooltip and legend.
 *
 * @property palette the colors of the series without a color of their own, taken in turn by their position.
 * @property textStyle the text style of the labels drawn in a chart, which the container also hands down to its
 * content.
 * @property tickColor the color of the tick labels of the axes and of the labels around a [RadarChart].
 * @property labelColor the color of the value labels of a [PieChart], which the container also hands down to its
 * content as the content color.
 * @property gridColor the color of the grid of a [BarChart], a [LineChart] and an [AreaChart].
 * @property polarGridColor the color of the grid of a [RadarChart].
 * @property cursorFill the color of the band behind the active category of a [BarChart].
 * @property cursorLine the color of the vertical line at the active category of a [LineChart] and an [AreaChart].
 * @property trackColor the color of the track behind the arcs of a [RadialBarChart].
 * @property tickMargin the distance between an axis and its tick labels, and between a [RadarChart] and the labels
 * around it.
 * @property margin the space kept free around the plot of a chart.
 * @property barGap the gap between two bars of the same category.
 * @property bandPadding the fraction of a category, or of a ring of a [RadialBarChart], left free on each side of
 * its bars.
 * @property dotRadius the radius of the dots on the points of a line or a radar.
 * @property activeDotRadius the radius of the dots on the points of the active category.
 * @property outerRadius the fraction of the available radius that a [PieChart], a [RadarChart] or a
 * [RadialBarChart] fills.
 * @property labelOffset the length of the line from a slice of a [PieChart] to its value label.
 * @property pointInset the space at the start and the end of the plot of a [LineChart] and an [AreaChart], so that
 * the first and the last point do not touch its edges.
 * @property aspectRatio the ratio of the width to the height of a [ChartContainer] by default.
 * @property animationMillis the duration in milliseconds of the animation that draws the marks in.
 * @property animationEasing the easing of the animation that draws the marks in.
 * @property tooltip the style of the tooltip.
 * @property legend the style of the legend.
 * @since 1.0.0
 */
@Immutable
public class ChartStyle(
    public val palette: List<Color>,
    public val textStyle: TextStyle,
    public val tickColor: Color,
    public val labelColor: Color,
    public val gridColor: Color,
    public val polarGridColor: Color,
    public val cursorFill: Color,
    public val cursorLine: Color,
    public val trackColor: Color,
    public val tickMargin: Dp,
    public val margin: Dp,
    public val barGap: Dp,
    public val bandPadding: Float,
    public val dotRadius: Dp,
    public val activeDotRadius: Dp,
    public val outerRadius: Float,
    public val labelOffset: Dp,
    public val pointInset: Dp,
    public val aspectRatio: Float,
    public val animationMillis: Int,
    public val animationEasing: Easing,
    public val tooltip: ChartTooltipStyle,
    public val legend: ChartLegendStyle,
) {
    /**
     * Returns a copy of this [ChartStyle] with the given values replaced and every other value kept.
     *
     * @param palette the new value of [palette].
     * @param textStyle the new value of [textStyle].
     * @param tickColor the new value of [tickColor].
     * @param labelColor the new value of [labelColor].
     * @param gridColor the new value of [gridColor].
     * @param polarGridColor the new value of [polarGridColor].
     * @param cursorFill the new value of [cursorFill].
     * @param cursorLine the new value of [cursorLine].
     * @param trackColor the new value of [trackColor].
     * @param tickMargin the new value of [tickMargin].
     * @param margin the new value of [margin].
     * @param barGap the new value of [barGap].
     * @param bandPadding the new value of [bandPadding].
     * @param dotRadius the new value of [dotRadius].
     * @param activeDotRadius the new value of [activeDotRadius].
     * @param outerRadius the new value of [outerRadius].
     * @param labelOffset the new value of [labelOffset].
     * @param pointInset the new value of [pointInset].
     * @param aspectRatio the new value of [aspectRatio].
     * @param animationMillis the new value of [animationMillis].
     * @param animationEasing the new value of [animationEasing].
     * @param tooltip the new value of [tooltip].
     * @param legend the new value of [legend].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        palette: List<Color> = this.palette,
        textStyle: TextStyle = this.textStyle,
        tickColor: Color = this.tickColor,
        labelColor: Color = this.labelColor,
        gridColor: Color = this.gridColor,
        polarGridColor: Color = this.polarGridColor,
        cursorFill: Color = this.cursorFill,
        cursorLine: Color = this.cursorLine,
        trackColor: Color = this.trackColor,
        tickMargin: Dp = this.tickMargin,
        margin: Dp = this.margin,
        barGap: Dp = this.barGap,
        bandPadding: Float = this.bandPadding,
        dotRadius: Dp = this.dotRadius,
        activeDotRadius: Dp = this.activeDotRadius,
        outerRadius: Float = this.outerRadius,
        labelOffset: Dp = this.labelOffset,
        pointInset: Dp = this.pointInset,
        aspectRatio: Float = this.aspectRatio,
        animationMillis: Int = this.animationMillis,
        animationEasing: Easing = this.animationEasing,
        tooltip: ChartTooltipStyle = this.tooltip,
        legend: ChartLegendStyle = this.legend,
    ): ChartStyle = ChartStyle(
        palette = palette,
        textStyle = textStyle,
        tickColor = tickColor,
        labelColor = labelColor,
        gridColor = gridColor,
        polarGridColor = polarGridColor,
        cursorFill = cursorFill,
        cursorLine = cursorLine,
        trackColor = trackColor,
        tickMargin = tickMargin,
        margin = margin,
        barGap = barGap,
        bandPadding = bandPadding,
        dotRadius = dotRadius,
        activeDotRadius = activeDotRadius,
        outerRadius = outerRadius,
        labelOffset = labelOffset,
        pointInset = pointInset,
        aspectRatio = aspectRatio,
        animationMillis = animationMillis,
        animationEasing = animationEasing,
        tooltip = tooltip,
        legend = legend,
    )

    override fun equals(other: Any?): Boolean = other is ChartStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ChartStyle(" +
        "palette=$palette, " +
        "textStyle=$textStyle, " +
        "tickColor=$tickColor, " +
        "labelColor=$labelColor, " +
        "gridColor=$gridColor, " +
        "polarGridColor=$polarGridColor, " +
        "cursorFill=$cursorFill, " +
        "cursorLine=$cursorLine, " +
        "trackColor=$trackColor, " +
        "tickMargin=$tickMargin, " +
        "margin=$margin, " +
        "barGap=$barGap, " +
        "bandPadding=$bandPadding, " +
        "dotRadius=$dotRadius, " +
        "activeDotRadius=$activeDotRadius, " +
        "outerRadius=$outerRadius, " +
        "labelOffset=$labelOffset, " +
        "pointInset=$pointInset, " +
        "aspectRatio=$aspectRatio, " +
        "animationMillis=$animationMillis, " +
        "animationEasing=$animationEasing, " +
        "tooltip=$tooltip, " +
        "legend=$legend)"

    private fun values(): List<Any?> = listOf(
        palette,
        textStyle,
        tickColor,
        labelColor,
        gridColor,
        polarGridColor,
        cursorFill,
        cursorLine,
        trackColor,
        tickMargin,
        margin,
        barGap,
        bandPadding,
        dotRadius,
        activeDotRadius,
        outerRadius,
        labelOffset,
        pointInset,
        aspectRatio,
        animationMillis,
        animationEasing,
        tooltip,
        legend,
    )
}
