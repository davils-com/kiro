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
import androidx.compose.ui.unit.Dp

/**
 * How an [Oscilloscope] looks: a grid, a center line and a glowing trace per channel.
 *
 * @property height the height of the oscilloscope, unless its modifier sets another.
 * @property columns the number of columns of the grid.
 * @property rows the number of rows of the grid.
 * @property gridColor the color of the grid.
 * @property centerColor the color of the center line of a channel.
 * @property gridWidth the width of the lines of the grid.
 * @property layout how the channels are placed.
 * @property leftColor the color of the trace of the left channel.
 * @property rightColor the color of the trace of the right channel.
 * @property traceWidth the width of a trace.
 * @property glowWidth the width of the glow around a trace.
 * @property glowAlpha the alpha of the color of a trace for its glow.
 * @property gain how far a sample at full scale reaches, as a share of the half height of a channel.
 * @property description what the oscilloscope announces to accessibility services.
 * @since 1.0.0
 */
@Immutable
public class OscilloscopeStyle(
    public val height: Dp,
    public val columns: Int,
    public val rows: Int,
    public val gridColor: Color,
    public val centerColor: Color,
    public val gridWidth: Dp,
    public val layout: ScopeLayout,
    public val leftColor: Color,
    public val rightColor: Color,
    public val traceWidth: Dp,
    public val glowWidth: Dp,
    public val glowAlpha: Float,
    public val gain: Float,
    public val description: String,
) {
    /**
     * Returns a copy of this [OscilloscopeStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param columns the new value of [columns].
     * @param rows the new value of [rows].
     * @param gridColor the new value of [gridColor].
     * @param centerColor the new value of [centerColor].
     * @param gridWidth the new value of [gridWidth].
     * @param layout the new value of [layout].
     * @param leftColor the new value of [leftColor].
     * @param rightColor the new value of [rightColor].
     * @param traceWidth the new value of [traceWidth].
     * @param glowWidth the new value of [glowWidth].
     * @param glowAlpha the new value of [glowAlpha].
     * @param gain the new value of [gain].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        columns: Int = this.columns,
        rows: Int = this.rows,
        gridColor: Color = this.gridColor,
        centerColor: Color = this.centerColor,
        gridWidth: Dp = this.gridWidth,
        layout: ScopeLayout = this.layout,
        leftColor: Color = this.leftColor,
        rightColor: Color = this.rightColor,
        traceWidth: Dp = this.traceWidth,
        glowWidth: Dp = this.glowWidth,
        glowAlpha: Float = this.glowAlpha,
        gain: Float = this.gain,
        description: String = this.description,
    ): OscilloscopeStyle = OscilloscopeStyle(
        height = height,
        columns = columns,
        rows = rows,
        gridColor = gridColor,
        centerColor = centerColor,
        gridWidth = gridWidth,
        layout = layout,
        leftColor = leftColor,
        rightColor = rightColor,
        traceWidth = traceWidth,
        glowWidth = glowWidth,
        glowAlpha = glowAlpha,
        gain = gain,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is OscilloscopeStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "OscilloscopeStyle(" +
        "height=$height, " +
        "columns=$columns, " +
        "rows=$rows, " +
        "gridColor=$gridColor, " +
        "centerColor=$centerColor, " +
        "gridWidth=$gridWidth, " +
        "layout=$layout, " +
        "leftColor=$leftColor, " +
        "rightColor=$rightColor, " +
        "traceWidth=$traceWidth, " +
        "glowWidth=$glowWidth, " +
        "glowAlpha=$glowAlpha, " +
        "gain=$gain, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        height,
        columns,
        rows,
        gridColor,
        centerColor,
        gridWidth,
        layout,
        leftColor,
        rightColor,
        traceWidth,
        glowWidth,
        glowAlpha,
        gain,
        description,
    )
}
