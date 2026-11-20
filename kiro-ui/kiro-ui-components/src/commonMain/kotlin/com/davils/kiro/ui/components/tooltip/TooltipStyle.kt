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
     * @since 0.1.0
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
