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
 * The look of the [BubbleReactions] of a [Bubble].
 *
 * @property shape the shape of the row of reactions.
 * @property container the container color of the row.
 * @property content the content color of the reactions.
 * @property ring the color of the ring that separates the row from the bubble.
 * @property ringWidth the width of that ring.
 * @property padding the padding of the row.
 * @property gap the space between the reactions.
 * @property inset the distance of the row from the start or end of the edge of the bubble.
 * @property overlap the fraction of the height of the row that lies outside the bubble.
 * @property textStyle the text style of the reactions.
 * @since 1.0.0
 */
@Immutable
public class BubbleReactionsStyle(
    public val shape: Shape,
    public val container: Color,
    public val content: Color,
    public val ring: Color,
    public val ringWidth: Dp,
    public val padding: PaddingValues,
    public val gap: Dp,
    public val inset: Dp,
    public val overlap: Float,
    public val textStyle: TextStyle,
) {
    /**
     * Returns a copy of this [BubbleReactionsStyle] with the given values replaced and every other value kept.
     *
     * @param shape the new value of [shape].
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param ring the new value of [ring].
     * @param ringWidth the new value of [ringWidth].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param inset the new value of [inset].
     * @param overlap the new value of [overlap].
     * @param textStyle the new value of [textStyle].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        shape: Shape = this.shape,
        container: Color = this.container,
        content: Color = this.content,
        ring: Color = this.ring,
        ringWidth: Dp = this.ringWidth,
        padding: PaddingValues = this.padding,
        gap: Dp = this.gap,
        inset: Dp = this.inset,
        overlap: Float = this.overlap,
        textStyle: TextStyle = this.textStyle,
    ): BubbleReactionsStyle = BubbleReactionsStyle(
        shape = shape,
        container = container,
        content = content,
        ring = ring,
        ringWidth = ringWidth,
        padding = padding,
        gap = gap,
        inset = inset,
        overlap = overlap,
        textStyle = textStyle,
    )

    override fun equals(other: Any?): Boolean = other is BubbleReactionsStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BubbleReactionsStyle(" +
        "shape=$shape, " +
        "container=$container, " +
        "content=$content, " +
        "ring=$ring, " +
        "ringWidth=$ringWidth, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "inset=$inset, " +
        "overlap=$overlap, " +
        "textStyle=$textStyle)"

    private fun values(): List<Any?> = listOf(
        shape,
        container,
        content,
        ring,
        ringWidth,
        padding,
        gap,
        inset,
        overlap,
        textStyle,
    )
}
