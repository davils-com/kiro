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

package com.davils.kiro.ui.components.bubble

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * The look of a [Bubble] and of its [BubbleContent] parts and [BubbleReactions].
 *
 * @property shape the shape of a part.
 * @property container the container color of a part.
 * @property content the content color of the bubble.
 * @property hover the container color of a clickable part while the pointer hovers over it.
 * @property border the color of the border of a part.
 * @property focusBorder the color of the border of a clickable part while its focus is visible.
 * @property ring the color of the ring around a clickable part while its focus is visible.
 * @property ringWidth the width of that ring.
 * @property padding the padding of the content of a part.
 * @property textStyle the text style of the bubble.
 * @property maxWidthFraction the largest width of a part, as a fraction of the width available to the bubble.
 * @property gap the space between the parts of a bubble.
 * @property groupGap the space between the bubbles of a [BubbleGroup].
 * @property reactions the style of the reactions of the bubble.
 * @since 1.0.0
 */
@Immutable
public class BubbleStyle(
    public val shape: Shape,
    public val container: Color,
    public val content: Color,
    public val hover: Color,
    public val border: Color,
    public val focusBorder: Color,
    public val ring: Color,
    public val ringWidth: Dp,
    public val padding: PaddingValues,
    public val textStyle: TextStyle,
    public val maxWidthFraction: Float,
    public val gap: Dp,
    public val groupGap: Dp,
    public val reactions: BubbleReactionsStyle,
) {
    /**
     * Returns a copy of this [BubbleStyle] with the given values replaced and every other value kept.
     *
     * @param shape the new value of [shape].
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param hover the new value of [hover].
     * @param border the new value of [border].
     * @param focusBorder the new value of [focusBorder].
     * @param ring the new value of [ring].
     * @param ringWidth the new value of [ringWidth].
     * @param padding the new value of [padding].
     * @param textStyle the new value of [textStyle].
     * @param maxWidthFraction the new value of [maxWidthFraction].
     * @param gap the new value of [gap].
     * @param groupGap the new value of [groupGap].
     * @param reactions the new value of [reactions].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        shape: Shape = this.shape,
        container: Color = this.container,
        content: Color = this.content,
        hover: Color = this.hover,
        border: Color = this.border,
        focusBorder: Color = this.focusBorder,
        ring: Color = this.ring,
        ringWidth: Dp = this.ringWidth,
        padding: PaddingValues = this.padding,
        textStyle: TextStyle = this.textStyle,
        maxWidthFraction: Float = this.maxWidthFraction,
        gap: Dp = this.gap,
        groupGap: Dp = this.groupGap,
        reactions: BubbleReactionsStyle = this.reactions,
    ): BubbleStyle = BubbleStyle(
        shape = shape,
        container = container,
        content = content,
        hover = hover,
        border = border,
        focusBorder = focusBorder,
        ring = ring,
        ringWidth = ringWidth,
        padding = padding,
        textStyle = textStyle,
        maxWidthFraction = maxWidthFraction,
        gap = gap,
        groupGap = groupGap,
        reactions = reactions,
    )

    override fun equals(other: Any?): Boolean = other is BubbleStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BubbleStyle(" +
        "shape=$shape, " +
        "container=$container, " +
        "content=$content, " +
        "hover=$hover, " +
        "border=$border, " +
        "focusBorder=$focusBorder, " +
        "ring=$ring, " +
        "ringWidth=$ringWidth, " +
        "padding=$padding, " +
        "textStyle=$textStyle, " +
        "maxWidthFraction=$maxWidthFraction, " +
        "gap=$gap, " +
        "groupGap=$groupGap, " +
        "reactions=$reactions)"

    private fun values(): List<Any?> = listOf(
        shape,
        container,
        content,
        hover,
        border,
        focusBorder,
        ring,
        ringWidth,
        padding,
        textStyle,
        maxWidthFraction,
        gap,
        groupGap,
        reactions,
    )
}
