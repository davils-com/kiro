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

package com.davils.kiro.ui.audio.waveform

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [Waveform] looks: the outline of the peaks with the root mean square inside it, played and unplayed parts in
 * their own colors, a playhead, and a line with the time under the pointer.
 *
 * @property height the height of the waveform, unless the modifier sets another.
 * @property layout how the channels are placed.
 * @property look how the outline is drawn.
 * @property playedColor the color of the part before the playhead.
 * @property unplayedColor the color of the part after the playhead.
 * @property peakAlpha the alpha of the colors for the outline of the peaks around the root mean square.
 * @property centerColor the color of the center line of a channel.
 * @property centerWidth the width of the center line of a channel; zero hides it.
 * @property channelGap the space between two channels when [layout] is [WaveformLayout.Stacked].
 * @property barWidth the width of a bar when [look] is [WaveformLook.Bars].
 * @property barGap the space between two bars when [look] is [WaveformLook.Bars].
 * @property playheadColor the color of the playhead.
 * @property playheadWidth the width of the playhead; zero hides it.
 * @property hoverColor the color of the line under the pointer.
 * @property timeStyle the text style of the time under the pointer.
 * @property timeColor the color of the time under the pointer.
 * @property time the text of the time under the pointer.
 * @property seekStep how far an arrow key moves the playhead.
 * @property description what the waveform announces to accessibility services.
 * @since 1.0.0
 */
@Immutable
public class WaveformStyle(
    public val height: Dp,
    public val layout: WaveformLayout,
    public val look: WaveformLook,
    public val playedColor: Color,
    public val unplayedColor: Color,
    public val peakAlpha: Float,
    public val centerColor: Color,
    public val centerWidth: Dp,
    public val channelGap: Dp,
    public val barWidth: Dp,
    public val barGap: Dp,
    public val playheadColor: Color,
    public val playheadWidth: Dp,
    public val hoverColor: Color,
    public val timeStyle: TextStyle,
    public val timeColor: Color,
    public val time: (Duration) -> String,
    public val seekStep: Duration,
    public val description: String,
) {
    /**
     * Returns a copy of this [WaveformStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param layout the new value of [layout].
     * @param look the new value of [look].
     * @param playedColor the new value of [playedColor].
     * @param unplayedColor the new value of [unplayedColor].
     * @param peakAlpha the new value of [peakAlpha].
     * @param centerColor the new value of [centerColor].
     * @param centerWidth the new value of [centerWidth].
     * @param channelGap the new value of [channelGap].
     * @param barWidth the new value of [barWidth].
     * @param barGap the new value of [barGap].
     * @param playheadColor the new value of [playheadColor].
     * @param playheadWidth the new value of [playheadWidth].
     * @param hoverColor the new value of [hoverColor].
     * @param timeStyle the new value of [timeStyle].
     * @param timeColor the new value of [timeColor].
     * @param time the new value of [time].
     * @param seekStep the new value of [seekStep].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        layout: WaveformLayout = this.layout,
        look: WaveformLook = this.look,
        playedColor: Color = this.playedColor,
        unplayedColor: Color = this.unplayedColor,
        peakAlpha: Float = this.peakAlpha,
        centerColor: Color = this.centerColor,
        centerWidth: Dp = this.centerWidth,
        channelGap: Dp = this.channelGap,
        barWidth: Dp = this.barWidth,
        barGap: Dp = this.barGap,
        playheadColor: Color = this.playheadColor,
        playheadWidth: Dp = this.playheadWidth,
        hoverColor: Color = this.hoverColor,
        timeStyle: TextStyle = this.timeStyle,
        timeColor: Color = this.timeColor,
        time: (Duration) -> String = this.time,
        seekStep: Duration = this.seekStep,
        description: String = this.description,
    ): WaveformStyle = WaveformStyle(
        height = height,
        layout = layout,
        look = look,
        playedColor = playedColor,
        unplayedColor = unplayedColor,
        peakAlpha = peakAlpha,
        centerColor = centerColor,
        centerWidth = centerWidth,
        channelGap = channelGap,
        barWidth = barWidth,
        barGap = barGap,
        playheadColor = playheadColor,
        playheadWidth = playheadWidth,
        hoverColor = hoverColor,
        timeStyle = timeStyle,
        timeColor = timeColor,
        time = time,
        seekStep = seekStep,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is WaveformStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "WaveformStyle(" +
        "height=$height, " +
        "layout=$layout, " +
        "look=$look, " +
        "playedColor=$playedColor, " +
        "unplayedColor=$unplayedColor, " +
        "peakAlpha=$peakAlpha, " +
        "centerColor=$centerColor, " +
        "centerWidth=$centerWidth, " +
        "channelGap=$channelGap, " +
        "barWidth=$barWidth, " +
        "barGap=$barGap, " +
        "playheadColor=$playheadColor, " +
        "playheadWidth=$playheadWidth, " +
        "hoverColor=$hoverColor, " +
        "timeStyle=$timeStyle, " +
        "timeColor=$timeColor, " +
        "time=$time, " +
        "seekStep=$seekStep, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        height,
        layout,
        look,
        playedColor,
        unplayedColor,
        peakAlpha,
        centerColor,
        centerWidth,
        channelGap,
        barWidth,
        barGap,
        playheadColor,
        playheadWidth,
        hoverColor,
        timeStyle,
        timeColor,
        time,
        seekStep,
        description,
    )
}
