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
     * @since 0.1.0
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
