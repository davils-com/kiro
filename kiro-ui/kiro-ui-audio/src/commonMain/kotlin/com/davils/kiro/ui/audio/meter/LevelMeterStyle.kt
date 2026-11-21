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
import com.davils.kiro.ui.audio.signal.DecibelRange
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.core.motion.Motion
import kotlin.time.Duration

/**
 * How a [LevelMeter] looks and moves: a bar per channel colored by zones, the root mean square level inside the peak
 * level, a hold line, a clip indicator above each bar, a scale beside the bars, the name of each channel and a readout
 * of its peak.
 *
 * @property thickness the thickness of the bar of a channel.
 * @property length the length of the meter along its bars, unless its modifier sets another.
 * @property channelGap the space between the bars of two channels.
 * @property radius the corner radius of a bar and of a clip indicator.
 * @property range the levels the bars show.
 * @property marks the levels the scale names.
 * @property warning the level from which a bar takes [warningColor].
 * @property danger the level from which a bar takes [dangerColor].
 * @property safeColor the color of a bar below [warning].
 * @property warningColor the color of a bar from [warning] to [danger].
 * @property dangerColor the color of a bar from [danger] up.
 * @property track the color of the empty part of a bar.
 * @property peakAlpha the alpha of the zone colors for the part of a bar above the root mean square level up to the
 * peak.
 * @property holdThickness the thickness of the line of a held peak.
 * @property mode how the bars are filled.
 * @property segment the length of a segment when [mode] is [MeterMode.Segmented].
 * @property segmentGap the space between two segments when [mode] is [MeterMode.Segmented].
 * @property clipSize the length of a clip indicator along the bars.
 * @property clipGap the space between a clip indicator, a readout or a name and the bar.
 * @property clipColor the color of a clip indicator of a channel that clipped.
 * @property clipIdle the color of a clip indicator of a channel that did not clip.
 * @property scaleGap the space between the bars and the scale.
 * @property tickLength the length of a tick of the scale.
 * @property tickColor the color of the ticks of the scale.
 * @property scaleStyle the text style of the levels of the scale.
 * @property labelStyle the text style of the names of the channels and of the readouts.
 * @property labelColor the color of the texts of the meter.
 * @property response how long a bar takes to follow a new level, for levels that arrive less often than the screen is
 * drawn.
 * @property motion the motion tokens; when they are reduced the bars follow at once.
 * @property scaleLabel the text of a level of the scale, such as `-12`.
 * @property readout the text of the readout of a peak, such as `-12.3`.
 * @property channel the name of a channel by its position and the number of channels, such as `L` and `R`.
 * @property description what the meter announces to accessibility services.
 * @property resetClip the label of the action that resets the clip indicators.
 * @since 1.0.0
 */
@Immutable
public class LevelMeterStyle(
    public val thickness: Dp,
    public val length: Dp,
    public val channelGap: Dp,
    public val radius: Dp,
    public val range: DecibelRange,
    public val marks: List<Decibels>,
    public val warning: Decibels,
    public val danger: Decibels,
    public val safeColor: Color,
    public val warningColor: Color,
    public val dangerColor: Color,
    public val track: Color,
    public val peakAlpha: Float,
    public val holdThickness: Dp,
    public val mode: MeterMode,
    public val segment: Dp,
    public val segmentGap: Dp,
    public val clipSize: Dp,
    public val clipGap: Dp,
    public val clipColor: Color,
    public val clipIdle: Color,
    public val scaleGap: Dp,
    public val tickLength: Dp,
    public val tickColor: Color,
    public val scaleStyle: TextStyle,
    public val labelStyle: TextStyle,
    public val labelColor: Color,
    public val response: Duration,
    public val motion: Motion,
    public val scaleLabel: (Decibels) -> String,
    public val readout: (Decibels) -> String,
    public val channel: (index: Int, count: Int) -> String,
    public val description: String,
    public val resetClip: String,
) {
    /**
     * Returns a copy of this [LevelMeterStyle] with the given values replaced and every other value kept.
     *
     * @param thickness the new value of [thickness].
     * @param length the new value of [length].
     * @param channelGap the new value of [channelGap].
     * @param radius the new value of [radius].
     * @param range the new value of [range].
     * @param marks the new value of [marks].
     * @param warning the new value of [warning].
     * @param danger the new value of [danger].
     * @param safeColor the new value of [safeColor].
     * @param warningColor the new value of [warningColor].
     * @param dangerColor the new value of [dangerColor].
     * @param track the new value of [track].
     * @param peakAlpha the new value of [peakAlpha].
     * @param holdThickness the new value of [holdThickness].
     * @param mode the new value of [mode].
     * @param segment the new value of [segment].
     * @param segmentGap the new value of [segmentGap].
     * @param clipSize the new value of [clipSize].
     * @param clipGap the new value of [clipGap].
     * @param clipColor the new value of [clipColor].
     * @param clipIdle the new value of [clipIdle].
     * @param scaleGap the new value of [scaleGap].
     * @param tickLength the new value of [tickLength].
     * @param tickColor the new value of [tickColor].
     * @param scaleStyle the new value of [scaleStyle].
     * @param labelStyle the new value of [labelStyle].
     * @param labelColor the new value of [labelColor].
     * @param response the new value of [response].
     * @param motion the new value of [motion].
     * @param scaleLabel the new value of [scaleLabel].
     * @param readout the new value of [readout].
     * @param channel the new value of [channel].
     * @param description the new value of [description].
     * @param resetClip the new value of [resetClip].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        thickness: Dp = this.thickness,
        length: Dp = this.length,
        channelGap: Dp = this.channelGap,
        radius: Dp = this.radius,
        range: DecibelRange = this.range,
        marks: List<Decibels> = this.marks,
        warning: Decibels = this.warning,
        danger: Decibels = this.danger,
        safeColor: Color = this.safeColor,
        warningColor: Color = this.warningColor,
        dangerColor: Color = this.dangerColor,
        track: Color = this.track,
        peakAlpha: Float = this.peakAlpha,
        holdThickness: Dp = this.holdThickness,
        mode: MeterMode = this.mode,
        segment: Dp = this.segment,
        segmentGap: Dp = this.segmentGap,
        clipSize: Dp = this.clipSize,
        clipGap: Dp = this.clipGap,
        clipColor: Color = this.clipColor,
        clipIdle: Color = this.clipIdle,
        scaleGap: Dp = this.scaleGap,
        tickLength: Dp = this.tickLength,
        tickColor: Color = this.tickColor,
        scaleStyle: TextStyle = this.scaleStyle,
        labelStyle: TextStyle = this.labelStyle,
        labelColor: Color = this.labelColor,
        response: Duration = this.response,
        motion: Motion = this.motion,
        scaleLabel: (Decibels) -> String = this.scaleLabel,
        readout: (Decibels) -> String = this.readout,
        channel: (index: Int, count: Int) -> String = this.channel,
        description: String = this.description,
        resetClip: String = this.resetClip,
    ): LevelMeterStyle = LevelMeterStyle(
        thickness = thickness,
        length = length,
        channelGap = channelGap,
        radius = radius,
        range = range,
        marks = marks,
        warning = warning,
        danger = danger,
        safeColor = safeColor,
        warningColor = warningColor,
        dangerColor = dangerColor,
        track = track,
        peakAlpha = peakAlpha,
        holdThickness = holdThickness,
        mode = mode,
        segment = segment,
        segmentGap = segmentGap,
        clipSize = clipSize,
        clipGap = clipGap,
        clipColor = clipColor,
        clipIdle = clipIdle,
        scaleGap = scaleGap,
        tickLength = tickLength,
        tickColor = tickColor,
        scaleStyle = scaleStyle,
        labelStyle = labelStyle,
        labelColor = labelColor,
        response = response,
        motion = motion,
        scaleLabel = scaleLabel,
        readout = readout,
        channel = channel,
        description = description,
        resetClip = resetClip,
    )

    override fun equals(other: Any?): Boolean = other is LevelMeterStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "LevelMeterStyle(" +
        "thickness=$thickness, " +
        "length=$length, " +
        "channelGap=$channelGap, " +
        "radius=$radius, " +
        "range=$range, " +
        "marks=$marks, " +
        "warning=$warning, " +
        "danger=$danger, " +
        "safeColor=$safeColor, " +
        "warningColor=$warningColor, " +
        "dangerColor=$dangerColor, " +
        "track=$track, " +
        "peakAlpha=$peakAlpha, " +
        "holdThickness=$holdThickness, " +
        "mode=$mode, " +
        "segment=$segment, " +
        "segmentGap=$segmentGap, " +
        "clipSize=$clipSize, " +
        "clipGap=$clipGap, " +
        "clipColor=$clipColor, " +
        "clipIdle=$clipIdle, " +
        "scaleGap=$scaleGap, " +
        "tickLength=$tickLength, " +
        "tickColor=$tickColor, " +
        "scaleStyle=$scaleStyle, " +
        "labelStyle=$labelStyle, " +
        "labelColor=$labelColor, " +
        "response=$response, " +
        "motion=$motion, " +
        "scaleLabel=$scaleLabel, " +
        "readout=$readout, " +
        "channel=$channel, " +
        "description=$description, " +
        "resetClip=$resetClip)"

    private fun values(): List<Any?> = listOf(
        thickness,
        length,
        channelGap,
        radius,
        range,
        marks,
        warning,
        danger,
        safeColor,
        warningColor,
        dangerColor,
        track,
        peakAlpha,
        holdThickness,
        mode,
        segment,
        segmentGap,
        clipSize,
        clipGap,
        clipColor,
        clipIdle,
        scaleGap,
        tickLength,
        tickColor,
        scaleStyle,
        labelStyle,
        labelColor,
        response,
        motion,
        scaleLabel,
        readout,
        channel,
        description,
        resetClip,
    )
}
