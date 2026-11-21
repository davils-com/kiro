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

/**
 * How a [Spectrogram] looks: the history of the bands as a heat map that scrolls from right to left, colored along a
 * ramp, with the frequencies named on the left.
 *
 * @property height the height of the spectrogram, unless its modifier sets another.
 * @property range the levels the ramp spans.
 * @property ramp the colors from the quietest to the loudest level, at least one.
 * @property frequencyMarks the frequencies in hertz named on the left.
 * @property gridColor the color of the lines at the named frequencies.
 * @property gridWidth the width of the lines at the named frequencies.
 * @property labelGap the space between the heat map and the names.
 * @property labelStyle the text style of the names.
 * @property labelColor the color of the names.
 * @property frequency the name of a frequency, such as `1k`.
 * @property radius the corner radius of the heat map.
 * @property description what the spectrogram announces to accessibility services.
 * @throws IllegalArgumentException if [ramp] is empty.
 * @since 1.0.0
 */
@Immutable
public class SpectrogramStyle(
    public val height: Dp,
    public val range: DecibelRange,
    public val ramp: List<Color>,
    public val frequencyMarks: List<Float>,
    public val gridColor: Color,
    public val gridWidth: Dp,
    public val labelGap: Dp,
    public val labelStyle: TextStyle,
    public val labelColor: Color,
    public val frequency: (Float) -> String,
    public val radius: Dp,
    public val description: String,
) {
    init {
        require(ramp.isNotEmpty()) { "A spectrogram needs at least one color in its ramp." }
    }

    /**
     * Returns a copy of this [SpectrogramStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param range the new value of [range].
     * @param ramp the new value of [ramp].
     * @param frequencyMarks the new value of [frequencyMarks].
     * @param gridColor the new value of [gridColor].
     * @param gridWidth the new value of [gridWidth].
     * @param labelGap the new value of [labelGap].
     * @param labelStyle the new value of [labelStyle].
     * @param labelColor the new value of [labelColor].
     * @param frequency the new value of [frequency].
     * @param radius the new value of [radius].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        range: DecibelRange = this.range,
        ramp: List<Color> = this.ramp,
        frequencyMarks: List<Float> = this.frequencyMarks,
        gridColor: Color = this.gridColor,
        gridWidth: Dp = this.gridWidth,
        labelGap: Dp = this.labelGap,
        labelStyle: TextStyle = this.labelStyle,
        labelColor: Color = this.labelColor,
        frequency: (Float) -> String = this.frequency,
        radius: Dp = this.radius,
        description: String = this.description,
    ): SpectrogramStyle = SpectrogramStyle(
        height = height,
        range = range,
        ramp = ramp,
        frequencyMarks = frequencyMarks,
        gridColor = gridColor,
        gridWidth = gridWidth,
        labelGap = labelGap,
        labelStyle = labelStyle,
        labelColor = labelColor,
        frequency = frequency,
        radius = radius,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is SpectrogramStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SpectrogramStyle(" +
        "height=$height, " +
        "range=$range, " +
        "ramp=$ramp, " +
        "frequencyMarks=$frequencyMarks, " +
        "gridColor=$gridColor, " +
        "gridWidth=$gridWidth, " +
        "labelGap=$labelGap, " +
        "labelStyle=$labelStyle, " +
        "labelColor=$labelColor, " +
        "frequency=$frequency, " +
        "radius=$radius, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        height,
        range,
        ramp,
        frequencyMarks,
        gridColor,
        gridWidth,
        labelGap,
        labelStyle,
        labelColor,
        frequency,
        radius,
        description,
    )
}
