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

package com.davils.kiro.ui.audio.spectrum

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.audio.signal.DecibelRange
import com.davils.kiro.ui.audio.signal.Decibels

/**
 * How a [SpectrumAnalyzer] looks: a grid of frequencies and levels with their names, the curve of the bands over a
 * gradient, the curve of the held peaks and a crosshair with a readout under the pointer.
 *
 * @property height the height of the analyzer, unless its modifier sets another.
 * @property range the levels the analyzer shows from bottom to top.
 * @property frequencyMarks the frequencies in hertz the grid marks and names.
 * @property decibelMarks the levels the grid marks and names.
 * @property gridColor the color of the grid.
 * @property gridWidth the width of the lines of the grid.
 * @property labelGap the space between the grid and the names of its lines.
 * @property labelStyle the text style of the names of the grid.
 * @property labelColor the color of the names of the grid.
 * @property frequency the name of a frequency of the grid, such as `1k`.
 * @property decibels the name of a level of the grid, such as `-24`.
 * @property curve how the levels of the bands are connected.
 * @property lineColor the color of the curve of the bands.
 * @property lineWidth the width of the curve of the bands.
 * @property fillTopAlpha the alpha of [lineColor] at the top of the area under the curve.
 * @property fillBottomAlpha the alpha of [lineColor] at the bottom of the area under the curve.
 * @property holdColor the color of the curve of the held peaks.
 * @property holdWidth the width of the curve of the held peaks.
 * @property crosshairColor the color of the crosshair under the pointer.
 * @property readoutStyle the text style of the readout under the pointer.
 * @property readoutColor the color of the text of the readout.
 * @property readoutContainer the color behind the readout.
 * @property readoutPadding the padding around the text of the readout.
 * @property readoutRadius the corner radius of the readout.
 * @property readout the text of the readout of a frequency and the level of its band.
 * @property description what the analyzer announces to accessibility services.
 * @since 1.0.0
 */
@Immutable
public class SpectrumAnalyzerStyle(
    public val height: Dp,
    public val range: DecibelRange,
    public val frequencyMarks: List<Float>,
    public val decibelMarks: List<Decibels>,
    public val gridColor: Color,
    public val gridWidth: Dp,
    public val labelGap: Dp,
    public val labelStyle: TextStyle,
    public val labelColor: Color,
    public val frequency: (Float) -> String,
    public val decibels: (Decibels) -> String,
    public val curve: SpectrumCurve,
    public val lineColor: Color,
    public val lineWidth: Dp,
    public val fillTopAlpha: Float,
    public val fillBottomAlpha: Float,
    public val holdColor: Color,
    public val holdWidth: Dp,
    public val crosshairColor: Color,
    public val readoutStyle: TextStyle,
    public val readoutColor: Color,
    public val readoutContainer: Color,
    public val readoutPadding: Dp,
    public val readoutRadius: Dp,
    public val readout: (frequency: Float, level: Decibels) -> String,
    public val description: String,
) {
    /**
     * Returns a copy of this [SpectrumAnalyzerStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param range the new value of [range].
     * @param frequencyMarks the new value of [frequencyMarks].
     * @param decibelMarks the new value of [decibelMarks].
     * @param gridColor the new value of [gridColor].
     * @param gridWidth the new value of [gridWidth].
     * @param labelGap the new value of [labelGap].
     * @param labelStyle the new value of [labelStyle].
     * @param labelColor the new value of [labelColor].
     * @param frequency the new value of [frequency].
     * @param decibels the new value of [decibels].
     * @param curve the new value of [curve].
     * @param lineColor the new value of [lineColor].
     * @param lineWidth the new value of [lineWidth].
     * @param fillTopAlpha the new value of [fillTopAlpha].
     * @param fillBottomAlpha the new value of [fillBottomAlpha].
     * @param holdColor the new value of [holdColor].
     * @param holdWidth the new value of [holdWidth].
     * @param crosshairColor the new value of [crosshairColor].
     * @param readoutStyle the new value of [readoutStyle].
     * @param readoutColor the new value of [readoutColor].
     * @param readoutContainer the new value of [readoutContainer].
     * @param readoutPadding the new value of [readoutPadding].
     * @param readoutRadius the new value of [readoutRadius].
     * @param readout the new value of [readout].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        range: DecibelRange = this.range,
        frequencyMarks: List<Float> = this.frequencyMarks,
        decibelMarks: List<Decibels> = this.decibelMarks,
        gridColor: Color = this.gridColor,
        gridWidth: Dp = this.gridWidth,
        labelGap: Dp = this.labelGap,
        labelStyle: TextStyle = this.labelStyle,
        labelColor: Color = this.labelColor,
        frequency: (Float) -> String = this.frequency,
        decibels: (Decibels) -> String = this.decibels,
        curve: SpectrumCurve = this.curve,
        lineColor: Color = this.lineColor,
        lineWidth: Dp = this.lineWidth,
        fillTopAlpha: Float = this.fillTopAlpha,
        fillBottomAlpha: Float = this.fillBottomAlpha,
        holdColor: Color = this.holdColor,
        holdWidth: Dp = this.holdWidth,
        crosshairColor: Color = this.crosshairColor,
        readoutStyle: TextStyle = this.readoutStyle,
        readoutColor: Color = this.readoutColor,
        readoutContainer: Color = this.readoutContainer,
        readoutPadding: Dp = this.readoutPadding,
        readoutRadius: Dp = this.readoutRadius,
        readout: (frequency: Float, level: Decibels) -> String = this.readout,
        description: String = this.description,
    ): SpectrumAnalyzerStyle = SpectrumAnalyzerStyle(
        height = height,
        range = range,
        frequencyMarks = frequencyMarks,
        decibelMarks = decibelMarks,
        gridColor = gridColor,
        gridWidth = gridWidth,
        labelGap = labelGap,
        labelStyle = labelStyle,
        labelColor = labelColor,
        frequency = frequency,
        decibels = decibels,
        curve = curve,
        lineColor = lineColor,
        lineWidth = lineWidth,
        fillTopAlpha = fillTopAlpha,
        fillBottomAlpha = fillBottomAlpha,
        holdColor = holdColor,
        holdWidth = holdWidth,
        crosshairColor = crosshairColor,
        readoutStyle = readoutStyle,
        readoutColor = readoutColor,
        readoutContainer = readoutContainer,
        readoutPadding = readoutPadding,
        readoutRadius = readoutRadius,
        readout = readout,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is SpectrumAnalyzerStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SpectrumAnalyzerStyle(" +
        "height=$height, " +
        "range=$range, " +
        "frequencyMarks=$frequencyMarks, " +
        "decibelMarks=$decibelMarks, " +
        "gridColor=$gridColor, " +
        "gridWidth=$gridWidth, " +
        "labelGap=$labelGap, " +
        "labelStyle=$labelStyle, " +
        "labelColor=$labelColor, " +
        "frequency=$frequency, " +
        "decibels=$decibels, " +
        "curve=$curve, " +
        "lineColor=$lineColor, " +
        "lineWidth=$lineWidth, " +
        "fillTopAlpha=$fillTopAlpha, " +
        "fillBottomAlpha=$fillBottomAlpha, " +
        "holdColor=$holdColor, " +
        "holdWidth=$holdWidth, " +
        "crosshairColor=$crosshairColor, " +
        "readoutStyle=$readoutStyle, " +
        "readoutColor=$readoutColor, " +
        "readoutContainer=$readoutContainer, " +
        "readoutPadding=$readoutPadding, " +
        "readoutRadius=$readoutRadius, " +
        "readout=$readout, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        height,
        range,
        frequencyMarks,
        decibelMarks,
        gridColor,
        gridWidth,
        labelGap,
        labelStyle,
        labelColor,
        frequency,
        decibels,
        curve,
        lineColor,
        lineWidth,
        fillTopAlpha,
        fillBottomAlpha,
        holdColor,
        holdWidth,
        crosshairColor,
        readoutStyle,
        readoutColor,
        readoutContainer,
        readoutPadding,
        readoutRadius,
        readout,
        description,
    )
}
