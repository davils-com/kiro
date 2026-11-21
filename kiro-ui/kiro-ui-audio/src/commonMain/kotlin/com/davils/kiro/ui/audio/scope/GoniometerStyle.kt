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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * How a [Goniometer] looks: a grid with the axes of left, right, mid and side, their names and the trace of the
 * samples.
 *
 * @property size the size of the goniometer, unless its modifier sets another.
 * @property grid the outline of the grid.
 * @property gridColor the color of the outline of the grid.
 * @property axisColor the color of the axes.
 * @property gridWidth the width of the lines of the grid and of the axes.
 * @property labelGap the space between the grid and the names of the axes.
 * @property labelStyle the text style of the names of the axes.
 * @property labelColor the color of the names of the axes.
 * @property left the name of the axis of the left channel.
 * @property right the name of the axis of the right channel.
 * @property mid the name of the axis of the sum of the channels.
 * @property side the name of the axis of the difference of the channels.
 * @property trace how the samples are drawn.
 * @property traceColor the color of the samples.
 * @property traceAlpha the alpha of [traceColor], so that dense regions glow.
 * @property dotSize the size of a sample when [trace] is [ScopeTrace.Dots].
 * @property lineWidth the width of the line when [trace] is [ScopeTrace.Lines].
 * @property gain how far a sample at full scale reaches, as a share of the radius of the grid; samples beyond the grid
 * stay on its edge.
 * @property description what the goniometer announces to accessibility services.
 * @since 1.0.0
 */
@Immutable
public class GoniometerStyle(
    public val size: Dp,
    public val grid: ScopeGrid,
    public val gridColor: Color,
    public val axisColor: Color,
    public val gridWidth: Dp,
    public val labelGap: Dp,
    public val labelStyle: TextStyle,
    public val labelColor: Color,
    public val left: String,
    public val right: String,
    public val mid: String,
    public val side: String,
    public val trace: ScopeTrace,
    public val traceColor: Color,
    public val traceAlpha: Float,
    public val dotSize: Dp,
    public val lineWidth: Dp,
    public val gain: Float,
    public val description: String,
) {
    /**
     * Returns a copy of this [GoniometerStyle] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param grid the new value of [grid].
     * @param gridColor the new value of [gridColor].
     * @param axisColor the new value of [axisColor].
     * @param gridWidth the new value of [gridWidth].
     * @param labelGap the new value of [labelGap].
     * @param labelStyle the new value of [labelStyle].
     * @param labelColor the new value of [labelColor].
     * @param left the new value of [left].
     * @param right the new value of [right].
     * @param mid the new value of [mid].
     * @param side the new value of [side].
     * @param trace the new value of [trace].
     * @param traceColor the new value of [traceColor].
     * @param traceAlpha the new value of [traceAlpha].
     * @param dotSize the new value of [dotSize].
     * @param lineWidth the new value of [lineWidth].
     * @param gain the new value of [gain].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        size: Dp = this.size,
        grid: ScopeGrid = this.grid,
        gridColor: Color = this.gridColor,
        axisColor: Color = this.axisColor,
        gridWidth: Dp = this.gridWidth,
        labelGap: Dp = this.labelGap,
        labelStyle: TextStyle = this.labelStyle,
        labelColor: Color = this.labelColor,
        left: String = this.left,
        right: String = this.right,
        mid: String = this.mid,
        side: String = this.side,
        trace: ScopeTrace = this.trace,
        traceColor: Color = this.traceColor,
        traceAlpha: Float = this.traceAlpha,
        dotSize: Dp = this.dotSize,
        lineWidth: Dp = this.lineWidth,
        gain: Float = this.gain,
        description: String = this.description,
    ): GoniometerStyle = GoniometerStyle(
        size = size,
        grid = grid,
        gridColor = gridColor,
        axisColor = axisColor,
        gridWidth = gridWidth,
        labelGap = labelGap,
        labelStyle = labelStyle,
        labelColor = labelColor,
        left = left,
        right = right,
        mid = mid,
        side = side,
        trace = trace,
        traceColor = traceColor,
        traceAlpha = traceAlpha,
        dotSize = dotSize,
        lineWidth = lineWidth,
        gain = gain,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is GoniometerStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "GoniometerStyle(" +
        "size=$size, " +
        "grid=$grid, " +
        "gridColor=$gridColor, " +
        "axisColor=$axisColor, " +
        "gridWidth=$gridWidth, " +
        "labelGap=$labelGap, " +
        "labelStyle=$labelStyle, " +
        "labelColor=$labelColor, " +
        "left=$left, " +
        "right=$right, " +
        "mid=$mid, " +
        "side=$side, " +
        "trace=$trace, " +
        "traceColor=$traceColor, " +
        "traceAlpha=$traceAlpha, " +
        "dotSize=$dotSize, " +
        "lineWidth=$lineWidth, " +
        "gain=$gain, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        size,
        grid,
        gridColor,
        axisColor,
        gridWidth,
        labelGap,
        labelStyle,
        labelColor,
        left,
        right,
        mid,
        side,
        trace,
        traceColor,
        traceAlpha,
        dotSize,
        lineWidth,
        gain,
        description,
    )
}
