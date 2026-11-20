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

package com.davils.kiro.ui.components.tooltip

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * The style of a [Tooltip].
 *
 * @property container the background color of the tooltip and its arrow.
 * @property content the content color of the tooltip.
 * @property shape the shape of the tooltip.
 * @property horizontalPadding the padding at the start and the end of the tooltip.
 * @property verticalPadding the padding at the top and the bottom of the tooltip.
 * @property textStyle the text style of the content.
 * @property maxWidth the largest width of the tooltip.
 * @property gap the gap between the parts of the content.
 * @property arrowSize the width and height of the square that is turned by 45 degrees to draw the arrow.
 * @property arrowRadius the corner radius of the arrow.
 * @property openDelay how long the pointer or the focus must stay before the tooltip opens.
 * @property closeDelay how long the tooltip stays open after the pointer or the focus has left.
 * @since 1.0.0
 */
@Immutable
public class TooltipStyle(
    public val container: Color,
    public val content: Color,
    public val shape: Shape,
    public val horizontalPadding: Dp,
    public val verticalPadding: Dp,
    public val textStyle: TextStyle,
    public val maxWidth: Dp,
    public val gap: Dp,
    public val arrowSize: Dp,
    public val arrowRadius: Dp,
    public val openDelay: Duration,
    public val closeDelay: Duration,
) {
    /**
     * Returns a copy of this [TooltipStyle] with the given values replaced and every other value kept.
     *
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param shape the new value of [shape].
     * @param horizontalPadding the new value of [horizontalPadding].
     * @param verticalPadding the new value of [verticalPadding].
     * @param textStyle the new value of [textStyle].
     * @param maxWidth the new value of [maxWidth].
     * @param gap the new value of [gap].
     * @param arrowSize the new value of [arrowSize].
     * @param arrowRadius the new value of [arrowRadius].
     * @param openDelay the new value of [openDelay].
     * @param closeDelay the new value of [closeDelay].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        container: Color = this.container,
        content: Color = this.content,
        shape: Shape = this.shape,
        horizontalPadding: Dp = this.horizontalPadding,
        verticalPadding: Dp = this.verticalPadding,
        textStyle: TextStyle = this.textStyle,
        maxWidth: Dp = this.maxWidth,
        gap: Dp = this.gap,
        arrowSize: Dp = this.arrowSize,
        arrowRadius: Dp = this.arrowRadius,
        openDelay: Duration = this.openDelay,
        closeDelay: Duration = this.closeDelay,
    ): TooltipStyle = TooltipStyle(
        container = container,
        content = content,
        shape = shape,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
        textStyle = textStyle,
        maxWidth = maxWidth,
        gap = gap,
        arrowSize = arrowSize,
        arrowRadius = arrowRadius,
        openDelay = openDelay,
        closeDelay = closeDelay,
    )

    override fun equals(other: Any?): Boolean = other is TooltipStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TooltipStyle(" +
        "container=$container, " +
        "content=$content, " +
        "shape=$shape, " +
        "horizontalPadding=$horizontalPadding, " +
        "verticalPadding=$verticalPadding, " +
        "textStyle=$textStyle, " +
        "maxWidth=$maxWidth, " +
        "gap=$gap, " +
        "arrowSize=$arrowSize, " +
        "arrowRadius=$arrowRadius, " +
        "openDelay=$openDelay, " +
        "closeDelay=$closeDelay)"

    private fun values(): List<Any?> = listOf(
        container,
        content,
        shape,
        horizontalPadding,
        verticalPadding,
        textStyle,
        maxWidth,
        gap,
        arrowSize,
        arrowRadius,
        openDelay,
        closeDelay,
    )
}
