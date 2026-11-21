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
import com.davils.kiro.ui.audio.signal.DecibelRange
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.icons.IconGraphic

/**
 * How an [AudioClip] looks: a tinted bar with a header that names the clip, the waveform of the part of the recording
 * it plays, the curves and handles of its fades, the line of its gain, and the outlines of hover and selection.
 *
 * @property radius the corner radius of the clip.
 * @property headerHeight the height of the header.
 * @property tints the colors a clip picks from by its tint, at least one.
 * @property headerAlpha the alpha of the tint for the header.
 * @property bodyAlpha the alpha of the tint for the body below the header.
 * @property peakAlpha the alpha of the tint for the outline of the peaks of the waveform.
 * @property nameStyle the text style of the name.
 * @property lightName the color of the name and of the icons on a dark tint.
 * @property darkName the color of the name and of the icons on a light tint.
 * @property padding the horizontal padding of the header.
 * @property iconSize the size of the icons of a muted or locked clip.
 * @property border the width of the outline of the clip.
 * @property borderAlpha the alpha of the tint for the outline.
 * @property selectedColor the color of the outline of a selected clip.
 * @property selectedWidth the width of the outline of a selected clip.
 * @property hoverAlpha the alpha of [selectedColor] laid over a hovered clip.
 * @property mutedAlpha the alpha of a muted clip.
 * @property fadeColor the color of the shade over the faded part of the clip.
 * @property fadeAlpha the alpha of [fadeColor].
 * @property fadeLineColor the color of the curves of the fades.
 * @property lineWidth the width of the curves of the fades and of the line of the gain.
 * @property handleSize the size of a handle of a fade.
 * @property handleColor the color of the handles of the fades.
 * @property gainColor the color of the line of the gain.
 * @property gainRange the gains from the bottom to the top of the body.
 * @property edgeWidth how close to an edge, a handle or the line of the gain the pointer grabs it.
 * @property muted the icon of a muted clip.
 * @property locked the icon of a locked clip.
 * @property gainLabel the text of a gain, shown while it is dragged.
 * @property selectedLabel what a selected clip announces to accessibility services.
 * @property mutedLabel what the icon of a muted clip announces.
 * @property lockedLabel what the icon of a locked clip announces.
 * @throws IllegalArgumentException if [tints] is empty.
 * @since 1.0.0
 */
@Immutable
public class AudioClipStyle(
    public val radius: Dp,
    public val headerHeight: Dp,
    public val tints: List<Color>,
    public val headerAlpha: Float,
    public val bodyAlpha: Float,
    public val peakAlpha: Float,
    public val nameStyle: TextStyle,
    public val lightName: Color,
    public val darkName: Color,
    public val padding: Dp,
    public val iconSize: Dp,
    public val border: Dp,
    public val borderAlpha: Float,
    public val selectedColor: Color,
    public val selectedWidth: Dp,
    public val hoverAlpha: Float,
    public val mutedAlpha: Float,
    public val fadeColor: Color,
    public val fadeAlpha: Float,
    public val fadeLineColor: Color,
    public val lineWidth: Dp,
    public val handleSize: Dp,
    public val handleColor: Color,
    public val gainColor: Color,
    public val gainRange: DecibelRange,
    public val edgeWidth: Dp,
    public val muted: IconGraphic,
    public val locked: IconGraphic,
    public val gainLabel: (Decibels) -> String,
    public val selectedLabel: String,
    public val mutedLabel: String,
    public val lockedLabel: String,
) {
    init {
        require(tints.isNotEmpty()) { "A clip needs at least one tint." }
    }

    /**
     * Returns a copy of this [AudioClipStyle] with the given values replaced and every other value kept.
     *
     * @param radius the new value of [radius].
     * @param headerHeight the new value of [headerHeight].
     * @param tints the new value of [tints].
     * @param headerAlpha the new value of [headerAlpha].
     * @param bodyAlpha the new value of [bodyAlpha].
     * @param peakAlpha the new value of [peakAlpha].
     * @param nameStyle the new value of [nameStyle].
     * @param lightName the new value of [lightName].
     * @param darkName the new value of [darkName].
     * @param padding the new value of [padding].
     * @param iconSize the new value of [iconSize].
     * @param border the new value of [border].
     * @param borderAlpha the new value of [borderAlpha].
     * @param selectedColor the new value of [selectedColor].
     * @param selectedWidth the new value of [selectedWidth].
     * @param hoverAlpha the new value of [hoverAlpha].
     * @param mutedAlpha the new value of [mutedAlpha].
     * @param fadeColor the new value of [fadeColor].
     * @param fadeAlpha the new value of [fadeAlpha].
     * @param fadeLineColor the new value of [fadeLineColor].
     * @param lineWidth the new value of [lineWidth].
     * @param handleSize the new value of [handleSize].
     * @param handleColor the new value of [handleColor].
     * @param gainColor the new value of [gainColor].
     * @param gainRange the new value of [gainRange].
     * @param edgeWidth the new value of [edgeWidth].
     * @param muted the new value of [muted].
     * @param locked the new value of [locked].
     * @param gainLabel the new value of [gainLabel].
     * @param selectedLabel the new value of [selectedLabel].
     * @param mutedLabel the new value of [mutedLabel].
     * @param lockedLabel the new value of [lockedLabel].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        radius: Dp = this.radius,
        headerHeight: Dp = this.headerHeight,
        tints: List<Color> = this.tints,
        headerAlpha: Float = this.headerAlpha,
        bodyAlpha: Float = this.bodyAlpha,
        peakAlpha: Float = this.peakAlpha,
        nameStyle: TextStyle = this.nameStyle,
        lightName: Color = this.lightName,
        darkName: Color = this.darkName,
        padding: Dp = this.padding,
        iconSize: Dp = this.iconSize,
        border: Dp = this.border,
        borderAlpha: Float = this.borderAlpha,
        selectedColor: Color = this.selectedColor,
        selectedWidth: Dp = this.selectedWidth,
        hoverAlpha: Float = this.hoverAlpha,
        mutedAlpha: Float = this.mutedAlpha,
        fadeColor: Color = this.fadeColor,
        fadeAlpha: Float = this.fadeAlpha,
        fadeLineColor: Color = this.fadeLineColor,
        lineWidth: Dp = this.lineWidth,
        handleSize: Dp = this.handleSize,
        handleColor: Color = this.handleColor,
        gainColor: Color = this.gainColor,
        gainRange: DecibelRange = this.gainRange,
        edgeWidth: Dp = this.edgeWidth,
        muted: IconGraphic = this.muted,
        locked: IconGraphic = this.locked,
        gainLabel: (Decibels) -> String = this.gainLabel,
        selectedLabel: String = this.selectedLabel,
        mutedLabel: String = this.mutedLabel,
        lockedLabel: String = this.lockedLabel,
    ): AudioClipStyle = AudioClipStyle(
        radius = radius,
        headerHeight = headerHeight,
        tints = tints,
        headerAlpha = headerAlpha,
        bodyAlpha = bodyAlpha,
        peakAlpha = peakAlpha,
        nameStyle = nameStyle,
        lightName = lightName,
        darkName = darkName,
        padding = padding,
        iconSize = iconSize,
        border = border,
        borderAlpha = borderAlpha,
        selectedColor = selectedColor,
        selectedWidth = selectedWidth,
        hoverAlpha = hoverAlpha,
        mutedAlpha = mutedAlpha,
        fadeColor = fadeColor,
        fadeAlpha = fadeAlpha,
        fadeLineColor = fadeLineColor,
        lineWidth = lineWidth,
        handleSize = handleSize,
        handleColor = handleColor,
        gainColor = gainColor,
        gainRange = gainRange,
        edgeWidth = edgeWidth,
        muted = muted,
        locked = locked,
        gainLabel = gainLabel,
        selectedLabel = selectedLabel,
        mutedLabel = mutedLabel,
        lockedLabel = lockedLabel,
    )

    override fun equals(other: Any?): Boolean = other is AudioClipStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioClipStyle(" +
        "radius=$radius, " +
        "headerHeight=$headerHeight, " +
        "tints=$tints, " +
        "headerAlpha=$headerAlpha, " +
        "bodyAlpha=$bodyAlpha, " +
        "peakAlpha=$peakAlpha, " +
        "nameStyle=$nameStyle, " +
        "lightName=$lightName, " +
        "darkName=$darkName, " +
        "padding=$padding, " +
        "iconSize=$iconSize, " +
        "border=$border, " +
        "borderAlpha=$borderAlpha, " +
        "selectedColor=$selectedColor, " +
        "selectedWidth=$selectedWidth, " +
        "hoverAlpha=$hoverAlpha, " +
        "mutedAlpha=$mutedAlpha, " +
        "fadeColor=$fadeColor, " +
        "fadeAlpha=$fadeAlpha, " +
        "fadeLineColor=$fadeLineColor, " +
        "lineWidth=$lineWidth, " +
        "handleSize=$handleSize, " +
        "handleColor=$handleColor, " +
        "gainColor=$gainColor, " +
        "gainRange=$gainRange, " +
        "edgeWidth=$edgeWidth, " +
        "muted=$muted, " +
        "locked=$locked, " +
        "gainLabel=$gainLabel, " +
        "selectedLabel=$selectedLabel, " +
        "mutedLabel=$mutedLabel, " +
        "lockedLabel=$lockedLabel)"

    private fun values(): List<Any?> = listOf(
        radius,
        headerHeight,
        tints,
        headerAlpha,
        bodyAlpha,
        peakAlpha,
        nameStyle,
        lightName,
        darkName,
        padding,
        iconSize,
        border,
        borderAlpha,
        selectedColor,
        selectedWidth,
        hoverAlpha,
        mutedAlpha,
        fadeColor,
        fadeAlpha,
        fadeLineColor,
        lineWidth,
        handleSize,
        handleColor,
        gainColor,
        gainRange,
        edgeWidth,
        muted,
        locked,
        gainLabel,
        selectedLabel,
        mutedLabel,
        lockedLabel,
    )
}
