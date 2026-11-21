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

/**
 * How a [ClipTrack] looks: a header with the name of the track and its switches for mute, solo and record, and the lane
 * with its clips.
 *
 * @property height the height of the track.
 * @property headerWidth the width of the header.
 * @property headerPadding the padding of the header.
 * @property headerGap the space between the switches of the header.
 * @property headerColor the color of the header.
 * @property laneColor the color of the lane behind the clips.
 * @property separatorColor the color of the line below the track.
 * @property separatorWidth the width of the line below the track.
 * @property clipInset the space above and below the clips in the lane.
 * @property nameStyle the text style of the name of the track.
 * @property nameColor the color of the name of the track.
 * @property toggleSize the size of a switch.
 * @property toggleRadius the corner radius of a switch.
 * @property toggleStyle the text style of the letter of a switch.
 * @property toggleIdle the color of a switch that is off.
 * @property toggleIdleContent the color of the letter of a switch that is off.
 * @property toggleActiveContent the color of the letter of a switch that is on.
 * @property muteColor the color of the mute switch when it is on.
 * @property soloColor the color of the solo switch when it is on.
 * @property armColor the color of the record switch when it is on.
 * @property mute the letter of the mute switch.
 * @property solo the letter of the solo switch.
 * @property arm the letter of the record switch.
 * @property muteLabel what the mute switch announces to accessibility services.
 * @property soloLabel what the solo switch announces to accessibility services.
 * @property armLabel what the record switch announces to accessibility services.
 * @since 1.0.0
 */
@Immutable
public class ClipTrackStyle(
    public val height: Dp,
    public val headerWidth: Dp,
    public val headerPadding: Dp,
    public val headerGap: Dp,
    public val headerColor: Color,
    public val laneColor: Color,
    public val separatorColor: Color,
    public val separatorWidth: Dp,
    public val clipInset: Dp,
    public val nameStyle: TextStyle,
    public val nameColor: Color,
    public val toggleSize: Dp,
    public val toggleRadius: Dp,
    public val toggleStyle: TextStyle,
    public val toggleIdle: Color,
    public val toggleIdleContent: Color,
    public val toggleActiveContent: Color,
    public val muteColor: Color,
    public val soloColor: Color,
    public val armColor: Color,
    public val mute: String,
    public val solo: String,
    public val arm: String,
    public val muteLabel: String,
    public val soloLabel: String,
    public val armLabel: String,
) {
    /**
     * Returns a copy of this [ClipTrackStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param headerWidth the new value of [headerWidth].
     * @param headerPadding the new value of [headerPadding].
     * @param headerGap the new value of [headerGap].
     * @param headerColor the new value of [headerColor].
     * @param laneColor the new value of [laneColor].
     * @param separatorColor the new value of [separatorColor].
     * @param separatorWidth the new value of [separatorWidth].
     * @param clipInset the new value of [clipInset].
     * @param nameStyle the new value of [nameStyle].
     * @param nameColor the new value of [nameColor].
     * @param toggleSize the new value of [toggleSize].
     * @param toggleRadius the new value of [toggleRadius].
     * @param toggleStyle the new value of [toggleStyle].
     * @param toggleIdle the new value of [toggleIdle].
     * @param toggleIdleContent the new value of [toggleIdleContent].
     * @param toggleActiveContent the new value of [toggleActiveContent].
     * @param muteColor the new value of [muteColor].
     * @param soloColor the new value of [soloColor].
     * @param armColor the new value of [armColor].
     * @param mute the new value of [mute].
     * @param solo the new value of [solo].
     * @param arm the new value of [arm].
     * @param muteLabel the new value of [muteLabel].
     * @param soloLabel the new value of [soloLabel].
     * @param armLabel the new value of [armLabel].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        headerWidth: Dp = this.headerWidth,
        headerPadding: Dp = this.headerPadding,
        headerGap: Dp = this.headerGap,
        headerColor: Color = this.headerColor,
        laneColor: Color = this.laneColor,
        separatorColor: Color = this.separatorColor,
        separatorWidth: Dp = this.separatorWidth,
        clipInset: Dp = this.clipInset,
        nameStyle: TextStyle = this.nameStyle,
        nameColor: Color = this.nameColor,
        toggleSize: Dp = this.toggleSize,
        toggleRadius: Dp = this.toggleRadius,
        toggleStyle: TextStyle = this.toggleStyle,
        toggleIdle: Color = this.toggleIdle,
        toggleIdleContent: Color = this.toggleIdleContent,
        toggleActiveContent: Color = this.toggleActiveContent,
        muteColor: Color = this.muteColor,
        soloColor: Color = this.soloColor,
        armColor: Color = this.armColor,
        mute: String = this.mute,
        solo: String = this.solo,
        arm: String = this.arm,
        muteLabel: String = this.muteLabel,
        soloLabel: String = this.soloLabel,
        armLabel: String = this.armLabel,
    ): ClipTrackStyle = ClipTrackStyle(
        height = height,
        headerWidth = headerWidth,
        headerPadding = headerPadding,
        headerGap = headerGap,
        headerColor = headerColor,
        laneColor = laneColor,
        separatorColor = separatorColor,
        separatorWidth = separatorWidth,
        clipInset = clipInset,
        nameStyle = nameStyle,
        nameColor = nameColor,
        toggleSize = toggleSize,
        toggleRadius = toggleRadius,
        toggleStyle = toggleStyle,
        toggleIdle = toggleIdle,
        toggleIdleContent = toggleIdleContent,
        toggleActiveContent = toggleActiveContent,
        muteColor = muteColor,
        soloColor = soloColor,
        armColor = armColor,
        mute = mute,
        solo = solo,
        arm = arm,
        muteLabel = muteLabel,
        soloLabel = soloLabel,
        armLabel = armLabel,
    )

    override fun equals(other: Any?): Boolean = other is ClipTrackStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ClipTrackStyle(" +
        "height=$height, " +
        "headerWidth=$headerWidth, " +
        "headerPadding=$headerPadding, " +
        "headerGap=$headerGap, " +
        "headerColor=$headerColor, " +
        "laneColor=$laneColor, " +
        "separatorColor=$separatorColor, " +
        "separatorWidth=$separatorWidth, " +
        "clipInset=$clipInset, " +
        "nameStyle=$nameStyle, " +
        "nameColor=$nameColor, " +
        "toggleSize=$toggleSize, " +
        "toggleRadius=$toggleRadius, " +
        "toggleStyle=$toggleStyle, " +
        "toggleIdle=$toggleIdle, " +
        "toggleIdleContent=$toggleIdleContent, " +
        "toggleActiveContent=$toggleActiveContent, " +
        "muteColor=$muteColor, " +
        "soloColor=$soloColor, " +
        "armColor=$armColor, " +
        "mute=$mute, " +
        "solo=$solo, " +
        "arm=$arm, " +
        "muteLabel=$muteLabel, " +
        "soloLabel=$soloLabel, " +
        "armLabel=$armLabel)"

    private fun values(): List<Any?> = listOf(
        height,
        headerWidth,
        headerPadding,
        headerGap,
        headerColor,
        laneColor,
        separatorColor,
        separatorWidth,
        clipInset,
        nameStyle,
        nameColor,
        toggleSize,
        toggleRadius,
        toggleStyle,
        toggleIdle,
        toggleIdleContent,
        toggleActiveContent,
        muteColor,
        soloColor,
        armColor,
        mute,
        solo,
        arm,
        muteLabel,
        soloLabel,
        armLabel,
    )
}
