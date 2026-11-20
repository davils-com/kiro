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

package com.davils.kiro.ui.components.scrollarea

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * How the scrollbars of a [ScrollArea] and a [LazyScrollArea] look: their size and the shape and colors of their
 * thumb.
 *
 * @property thickness the space a scrollbar takes across its direction, inset included.
 * @property inset the space between the thumb and the edges of its scrollbar.
 * @property thumbShape the shape of the thumb.
 * @property thumbColor the color of the thumb.
 * @property thumbHoverColor the color of the thumb while the pointer is on it.
 * @property minimalThumbLength the shortest length of the thumb, however long the content is.
 * @property hoverDurationMillis the duration in milliseconds of the change between the two colors of the thumb.
 * @since 1.0.0
 */
@Immutable
public class ScrollAreaStyle(
    public val thickness: Dp,
    public val inset: Dp,
    public val thumbShape: Shape,
    public val thumbColor: Color,
    public val thumbHoverColor: Color,
    public val minimalThumbLength: Dp,
    public val hoverDurationMillis: Int,
) {
    /**
     * Returns a copy of this [ScrollAreaStyle] with the given values replaced and every other value kept.
     *
     * @param thickness the new value of [thickness].
     * @param inset the new value of [inset].
     * @param thumbShape the new value of [thumbShape].
     * @param thumbColor the new value of [thumbColor].
     * @param thumbHoverColor the new value of [thumbHoverColor].
     * @param minimalThumbLength the new value of [minimalThumbLength].
     * @param hoverDurationMillis the new value of [hoverDurationMillis].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        thickness: Dp = this.thickness,
        inset: Dp = this.inset,
        thumbShape: Shape = this.thumbShape,
        thumbColor: Color = this.thumbColor,
        thumbHoverColor: Color = this.thumbHoverColor,
        minimalThumbLength: Dp = this.minimalThumbLength,
        hoverDurationMillis: Int = this.hoverDurationMillis,
    ): ScrollAreaStyle = ScrollAreaStyle(
        thickness = thickness,
        inset = inset,
        thumbShape = thumbShape,
        thumbColor = thumbColor,
        thumbHoverColor = thumbHoverColor,
        minimalThumbLength = minimalThumbLength,
        hoverDurationMillis = hoverDurationMillis,
    )

    override fun equals(other: Any?): Boolean = other is ScrollAreaStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ScrollAreaStyle(" +
        "thickness=$thickness, " +
        "inset=$inset, " +
        "thumbShape=$thumbShape, " +
        "thumbColor=$thumbColor, " +
        "thumbHoverColor=$thumbHoverColor, " +
        "minimalThumbLength=$minimalThumbLength, " +
        "hoverDurationMillis=$hoverDurationMillis)"

    private fun values(): List<Any?> = listOf(
        thickness,
        inset,
        thumbShape,
        thumbColor,
        thumbHoverColor,
        minimalThumbLength,
        hoverDurationMillis,
    )
}
