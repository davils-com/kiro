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

package com.davils.kiro.ui.audio.bar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

/**
 * How an [AudioPlayerBar] looks: a full-width bar with the track on the start, the playback in the center and the
 * volume on the end, tinted by the cover.
 *
 * @property height the height of the bar.
 * @property padding the horizontal padding of the bar.
 * @property gap the space between the three parts of the bar and their items.
 * @property shape the shape of the bar.
 * @property container the color of the bar before the tint of the cover.
 * @property content the color of the texts and icons on the bar.
 * @property border the border of the bar.
 * @property tint how far the dominant color of the cover is mixed into [container].
 * @property centerWeight the width of the center part relative to each side part.
 * @property coverSize the size of the cover.
 * @property coverRadius the corner radius of the cover.
 * @property coverElevation the elevation of the cover while the track plays.
 * @since 1.0.0
 */
@Immutable
public class AudioPlayerBarStyle(
    public val height: Dp,
    public val padding: Dp,
    public val gap: Dp,
    public val shape: Shape,
    public val container: Color,
    public val content: Color,
    public val border: Border,
    public val tint: Float,
    public val centerWeight: Float,
    public val coverSize: Dp,
    public val coverRadius: Dp,
    public val coverElevation: Dp,
) {
    /**
     * Returns a copy of this [AudioPlayerBarStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param shape the new value of [shape].
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param border the new value of [border].
     * @param tint the new value of [tint].
     * @param centerWeight the new value of [centerWeight].
     * @param coverSize the new value of [coverSize].
     * @param coverRadius the new value of [coverRadius].
     * @param coverElevation the new value of [coverElevation].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        shape: Shape = this.shape,
        container: Color = this.container,
        content: Color = this.content,
        border: Border = this.border,
        tint: Float = this.tint,
        centerWeight: Float = this.centerWeight,
        coverSize: Dp = this.coverSize,
        coverRadius: Dp = this.coverRadius,
        coverElevation: Dp = this.coverElevation,
    ): AudioPlayerBarStyle = AudioPlayerBarStyle(
        height = height,
        padding = padding,
        gap = gap,
        shape = shape,
        container = container,
        content = content,
        border = border,
        tint = tint,
        centerWeight = centerWeight,
        coverSize = coverSize,
        coverRadius = coverRadius,
        coverElevation = coverElevation,
    )

    override fun equals(other: Any?): Boolean = other is AudioPlayerBarStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioPlayerBarStyle(" +
        "height=$height, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "shape=$shape, " +
        "container=$container, " +
        "content=$content, " +
        "border=$border, " +
        "tint=$tint, " +
        "centerWeight=$centerWeight, " +
        "coverSize=$coverSize, " +
        "coverRadius=$coverRadius, " +
        "coverElevation=$coverElevation)"

    private fun values(): List<Any?> = listOf(
        height,
        padding,
        gap,
        shape,
        container,
        content,
        border,
        tint,
        centerWeight,
        coverSize,
        coverRadius,
        coverElevation,
    )
}
