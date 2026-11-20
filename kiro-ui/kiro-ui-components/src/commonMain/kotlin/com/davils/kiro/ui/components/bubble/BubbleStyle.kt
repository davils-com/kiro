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
     * @since 0.1.0
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
