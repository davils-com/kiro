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

package com.davils.kiro.ui.components.chart

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.elevation.BoxShadow

@Immutable
public class ChartTooltipStyle(
    public val shape: Shape,
    public val container: Color,
    public val border: Color,
    public val shadow: BoxShadow,
    public val padding: PaddingValues,
    public val gap: Dp,
    public val minWidth: Dp,
    public val labelStyle: TextStyle,
    public val nameStyle: TextStyle,
    public val nameColor: Color,
    public val valueStyle: TextStyle,
    public val valueColor: Color,
    public val indicatorSize: Dp,
    public val lineWidth: Dp,
    public val dashWidth: Dp,
    public val indicatorShape: Shape,
    public val iconSize: Dp,
    public val offset: Dp,
) {
    /**
     * Returns a copy of this [ChartTooltipStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        shape: Shape = this.shape,
        container: Color = this.container,
        border: Color = this.border,
        shadow: BoxShadow = this.shadow,
        padding: PaddingValues = this.padding,
        gap: Dp = this.gap,
        minWidth: Dp = this.minWidth,
        labelStyle: TextStyle = this.labelStyle,
        nameStyle: TextStyle = this.nameStyle,
        nameColor: Color = this.nameColor,
        valueStyle: TextStyle = this.valueStyle,
        valueColor: Color = this.valueColor,
        indicatorSize: Dp = this.indicatorSize,
        lineWidth: Dp = this.lineWidth,
        dashWidth: Dp = this.dashWidth,
        indicatorShape: Shape = this.indicatorShape,
        iconSize: Dp = this.iconSize,
        offset: Dp = this.offset,
    ): ChartTooltipStyle = ChartTooltipStyle(
        shape = shape,
        container = container,
        border = border,
        shadow = shadow,
        padding = padding,
        gap = gap,
        minWidth = minWidth,
        labelStyle = labelStyle,
        nameStyle = nameStyle,
        nameColor = nameColor,
        valueStyle = valueStyle,
        valueColor = valueColor,
        indicatorSize = indicatorSize,
        lineWidth = lineWidth,
        dashWidth = dashWidth,
        indicatorShape = indicatorShape,
        iconSize = iconSize,
        offset = offset,
    )

    override fun equals(other: Any?): Boolean = other is ChartTooltipStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ChartTooltipStyle(" +
        "shape=$shape, " +
        "container=$container, " +
        "border=$border, " +
        "shadow=$shadow, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "minWidth=$minWidth, " +
        "labelStyle=$labelStyle, " +
        "nameStyle=$nameStyle, " +
        "nameColor=$nameColor, " +
        "valueStyle=$valueStyle, " +
        "valueColor=$valueColor, " +
        "indicatorSize=$indicatorSize, " +
        "lineWidth=$lineWidth, " +
        "dashWidth=$dashWidth, " +
        "indicatorShape=$indicatorShape, " +
        "iconSize=$iconSize, " +
        "offset=$offset)"

    private fun values(): List<Any?> = listOf(
        shape,
        container,
        border,
        shadow,
        padding,
        gap,
        minWidth,
        labelStyle,
        nameStyle,
        nameColor,
        valueStyle,
        valueColor,
        indicatorSize,
        lineWidth,
        dashWidth,
        indicatorShape,
        iconSize,
        offset,
    )
}
