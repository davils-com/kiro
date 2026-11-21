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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [TimeRuler] looks: major ticks with the time, minor ticks between them, and the head of the playhead.
 *
 * @property height the height of the ruler.
 * @property background the color behind the ruler.
 * @property tickColor the color of the ticks.
 * @property tickWidth the width of the ticks.
 * @property majorTick the length of a major tick.
 * @property minorTick the length of a minor tick.
 * @property minorCount the number of minor ticks between two major ticks.
 * @property labelStyle the text style of the times.
 * @property labelColor the color of the times.
 * @property labelPadding the space between a major tick and its time.
 * @property minimumSpacing the smallest distance between two major ticks.
 * @property intervals the distances between major ticks to choose from, from the shortest to the longest.
 * @property time the text of the time of a major tick, a timecode by default.
 * @property playheadColor the color of the head of the playhead.
 * @property playheadSize the width of the head of the playhead.
 * @property description what the ruler announces to accessibility services.
 * @since 1.0.0
 */
@Immutable
public class TimeRulerStyle(
    public val height: Dp,
    public val background: Color,
    public val tickColor: Color,
    public val tickWidth: Dp,
    public val majorTick: Dp,
    public val minorTick: Dp,
    public val minorCount: Int,
    public val labelStyle: TextStyle,
    public val labelColor: Color,
    public val labelPadding: Dp,
    public val minimumSpacing: Dp,
    public val intervals: List<Duration>,
    public val time: (Duration) -> String,
    public val playheadColor: Color,
    public val playheadSize: Dp,
    public val description: String,
) {
    /**
     * Returns a copy of this [TimeRulerStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param background the new value of [background].
     * @param tickColor the new value of [tickColor].
     * @param tickWidth the new value of [tickWidth].
     * @param majorTick the new value of [majorTick].
     * @param minorTick the new value of [minorTick].
     * @param minorCount the new value of [minorCount].
     * @param labelStyle the new value of [labelStyle].
     * @param labelColor the new value of [labelColor].
     * @param labelPadding the new value of [labelPadding].
     * @param minimumSpacing the new value of [minimumSpacing].
     * @param intervals the new value of [intervals].
     * @param time the new value of [time].
     * @param playheadColor the new value of [playheadColor].
     * @param playheadSize the new value of [playheadSize].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        background: Color = this.background,
        tickColor: Color = this.tickColor,
        tickWidth: Dp = this.tickWidth,
        majorTick: Dp = this.majorTick,
        minorTick: Dp = this.minorTick,
        minorCount: Int = this.minorCount,
        labelStyle: TextStyle = this.labelStyle,
        labelColor: Color = this.labelColor,
        labelPadding: Dp = this.labelPadding,
        minimumSpacing: Dp = this.minimumSpacing,
        intervals: List<Duration> = this.intervals,
        time: (Duration) -> String = this.time,
        playheadColor: Color = this.playheadColor,
        playheadSize: Dp = this.playheadSize,
        description: String = this.description,
    ): TimeRulerStyle = TimeRulerStyle(
        height = height,
        background = background,
        tickColor = tickColor,
        tickWidth = tickWidth,
        majorTick = majorTick,
        minorTick = minorTick,
        minorCount = minorCount,
        labelStyle = labelStyle,
        labelColor = labelColor,
        labelPadding = labelPadding,
        minimumSpacing = minimumSpacing,
        intervals = intervals,
        time = time,
        playheadColor = playheadColor,
        playheadSize = playheadSize,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is TimeRulerStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TimeRulerStyle(" +
        "height=$height, " +
        "background=$background, " +
        "tickColor=$tickColor, " +
        "tickWidth=$tickWidth, " +
        "majorTick=$majorTick, " +
        "minorTick=$minorTick, " +
        "minorCount=$minorCount, " +
        "labelStyle=$labelStyle, " +
        "labelColor=$labelColor, " +
        "labelPadding=$labelPadding, " +
        "minimumSpacing=$minimumSpacing, " +
        "intervals=$intervals, " +
        "time=$time, " +
        "playheadColor=$playheadColor, " +
        "playheadSize=$playheadSize, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        height,
        background,
        tickColor,
        tickWidth,
        majorTick,
        minorTick,
        minorCount,
        labelStyle,
        labelColor,
        labelPadding,
        minimumSpacing,
        intervals,
        time,
        playheadColor,
        playheadSize,
        description,
    )
}
