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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.motion.Motion
import kotlin.time.Duration

/**
 * How a [CorrelationMeter] looks and moves: a track from `-1` to `+1` with a mark in the middle, a bar from the middle
 * to the correlation and the names of the ends and of the middle below.
 *
 * @property length the length of the meter, unless its modifier sets another.
 * @property thickness the thickness of the track.
 * @property radius the corner radius of the track.
 * @property track the color of the track.
 * @property positiveColor the color of the bar of a correlation above `0`, a signal safe for mono.
 * @property negativeColor the color of the bar of a correlation below `0`, a signal with phase problems.
 * @property centerColor the color of the mark in the middle.
 * @property centerWidth the width of the mark in the middle.
 * @property labelGap the space between the track and the names.
 * @property labelStyle the text style of the names.
 * @property labelColor the color of the names.
 * @property negative the name of the left end.
 * @property center the name of the middle.
 * @property positive the name of the right end.
 * @property response how long the bar takes to follow a new correlation.
 * @property motion the motion tokens; when they are reduced the bar follows at once.
 * @property readout the text a correlation is announced with, such as `+0.8`.
 * @property description what the meter announces to accessibility services.
 * @since 1.0.0
 */
@Immutable
public class CorrelationMeterStyle(
    public val length: Dp,
    public val thickness: Dp,
    public val radius: Dp,
    public val track: Color,
    public val positiveColor: Color,
    public val negativeColor: Color,
    public val centerColor: Color,
    public val centerWidth: Dp,
    public val labelGap: Dp,
    public val labelStyle: TextStyle,
    public val labelColor: Color,
    public val negative: String,
    public val center: String,
    public val positive: String,
    public val response: Duration,
    public val motion: Motion,
    public val readout: (Float) -> String,
    public val description: String,
) {
    /**
     * Returns a copy of this [CorrelationMeterStyle] with the given values replaced and every other value kept.
     *
     * @param length the new value of [length].
     * @param thickness the new value of [thickness].
     * @param radius the new value of [radius].
     * @param track the new value of [track].
     * @param positiveColor the new value of [positiveColor].
     * @param negativeColor the new value of [negativeColor].
     * @param centerColor the new value of [centerColor].
     * @param centerWidth the new value of [centerWidth].
     * @param labelGap the new value of [labelGap].
     * @param labelStyle the new value of [labelStyle].
     * @param labelColor the new value of [labelColor].
     * @param negative the new value of [negative].
     * @param center the new value of [center].
     * @param positive the new value of [positive].
     * @param response the new value of [response].
     * @param motion the new value of [motion].
     * @param readout the new value of [readout].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        length: Dp = this.length,
        thickness: Dp = this.thickness,
        radius: Dp = this.radius,
        track: Color = this.track,
        positiveColor: Color = this.positiveColor,
        negativeColor: Color = this.negativeColor,
        centerColor: Color = this.centerColor,
        centerWidth: Dp = this.centerWidth,
        labelGap: Dp = this.labelGap,
        labelStyle: TextStyle = this.labelStyle,
        labelColor: Color = this.labelColor,
        negative: String = this.negative,
        center: String = this.center,
        positive: String = this.positive,
        response: Duration = this.response,
        motion: Motion = this.motion,
        readout: (Float) -> String = this.readout,
        description: String = this.description,
    ): CorrelationMeterStyle = CorrelationMeterStyle(
        length = length,
        thickness = thickness,
        radius = radius,
        track = track,
        positiveColor = positiveColor,
        negativeColor = negativeColor,
        centerColor = centerColor,
        centerWidth = centerWidth,
        labelGap = labelGap,
        labelStyle = labelStyle,
        labelColor = labelColor,
        negative = negative,
        center = center,
        positive = positive,
        response = response,
        motion = motion,
        readout = readout,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is CorrelationMeterStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CorrelationMeterStyle(" +
        "length=$length, " +
        "thickness=$thickness, " +
        "radius=$radius, " +
        "track=$track, " +
        "positiveColor=$positiveColor, " +
        "negativeColor=$negativeColor, " +
        "centerColor=$centerColor, " +
        "centerWidth=$centerWidth, " +
        "labelGap=$labelGap, " +
        "labelStyle=$labelStyle, " +
        "labelColor=$labelColor, " +
        "negative=$negative, " +
        "center=$center, " +
        "positive=$positive, " +
        "response=$response, " +
        "motion=$motion, " +
        "readout=$readout, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        length,
        thickness,
        radius,
        track,
        positiveColor,
        negativeColor,
        centerColor,
        centerWidth,
        labelGap,
        labelStyle,
        labelColor,
        negative,
        center,
        positive,
        response,
        motion,
        readout,
        description,
    )
}
