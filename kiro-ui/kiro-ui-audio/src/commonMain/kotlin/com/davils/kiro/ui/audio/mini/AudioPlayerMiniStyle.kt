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

package com.davils.kiro.ui.audio.mini

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

/**
 * How an [AudioPlayerMini] looks: a small pill with a round cover, the title, the visualizer and a play button
 * ringed by the progress.
 *
 * @property height the height of the pill.
 * @property maxWidth the largest width of the pill.
 * @property padding the horizontal padding of the pill.
 * @property gap the space between the parts of the pill.
 * @property shape the shape of the pill.
 * @property container the color of the pill before the tint of the cover.
 * @property content the color of the texts on the pill.
 * @property border the border of the pill.
 * @property tint how far the dominant color of the cover is mixed into [container].
 * @property coverShape the shape of the cover.
 * @property coverElevation the elevation of the cover while the track plays.
 * @property ringWidth the width of the progress ring around the play button.
 * @property ringGap the space between the progress ring and the play button.
 * @property ringAlpha the alpha of the accent for the unplayed part of the ring.
 * @property ringStart the angle in degrees where the ring starts, clockwise from the right.
 * @property playContainer the color of the play button.
 * @property playContent the color of the glyph of the play button.
 * @since 1.0.0
 */
@Immutable
public class AudioPlayerMiniStyle(
    public val height: Dp,
    public val maxWidth: Dp,
    public val padding: Dp,
    public val gap: Dp,
    public val shape: Shape,
    public val container: Color,
    public val content: Color,
    public val border: Border,
    public val tint: Float,
    public val coverShape: Shape,
    public val coverElevation: Dp,
    public val ringWidth: Dp,
    public val ringGap: Dp,
    public val ringAlpha: Float,
    public val ringStart: Float,
    public val playContainer: Color,
    public val playContent: Color,
) {
    /**
     * Returns a copy of this [AudioPlayerMiniStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param maxWidth the new value of [maxWidth].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param shape the new value of [shape].
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param border the new value of [border].
     * @param tint the new value of [tint].
     * @param coverShape the new value of [coverShape].
     * @param coverElevation the new value of [coverElevation].
     * @param ringWidth the new value of [ringWidth].
     * @param ringGap the new value of [ringGap].
     * @param ringAlpha the new value of [ringAlpha].
     * @param ringStart the new value of [ringStart].
     * @param playContainer the new value of [playContainer].
     * @param playContent the new value of [playContent].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        maxWidth: Dp = this.maxWidth,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        shape: Shape = this.shape,
        container: Color = this.container,
        content: Color = this.content,
        border: Border = this.border,
        tint: Float = this.tint,
        coverShape: Shape = this.coverShape,
        coverElevation: Dp = this.coverElevation,
        ringWidth: Dp = this.ringWidth,
        ringGap: Dp = this.ringGap,
        ringAlpha: Float = this.ringAlpha,
        ringStart: Float = this.ringStart,
        playContainer: Color = this.playContainer,
        playContent: Color = this.playContent,
    ): AudioPlayerMiniStyle = AudioPlayerMiniStyle(
        height = height,
        maxWidth = maxWidth,
        padding = padding,
        gap = gap,
        shape = shape,
        container = container,
        content = content,
        border = border,
        tint = tint,
        coverShape = coverShape,
        coverElevation = coverElevation,
        ringWidth = ringWidth,
        ringGap = ringGap,
        ringAlpha = ringAlpha,
        ringStart = ringStart,
        playContainer = playContainer,
        playContent = playContent,
    )

    override fun equals(other: Any?): Boolean = other is AudioPlayerMiniStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioPlayerMiniStyle(" +
        "height=$height, " +
        "maxWidth=$maxWidth, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "shape=$shape, " +
        "container=$container, " +
        "content=$content, " +
        "border=$border, " +
        "tint=$tint, " +
        "coverShape=$coverShape, " +
        "coverElevation=$coverElevation, " +
        "ringWidth=$ringWidth, " +
        "ringGap=$ringGap, " +
        "ringAlpha=$ringAlpha, " +
        "ringStart=$ringStart, " +
        "playContainer=$playContainer, " +
        "playContent=$playContent)"

    private fun values(): List<Any?> = listOf(
        height,
        maxWidth,
        padding,
        gap,
        shape,
        container,
        content,
        border,
        tint,
        coverShape,
        coverElevation,
        ringWidth,
        ringGap,
        ringAlpha,
        ringStart,
        playContainer,
        playContent,
    )
}
