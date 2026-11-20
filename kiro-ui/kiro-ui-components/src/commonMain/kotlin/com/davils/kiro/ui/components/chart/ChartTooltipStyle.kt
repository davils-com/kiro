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

/**
 * How the tooltip of a chart looks: its panel, its text styles and colors, its indicators and its distance from the
 * pointer.
 *
 * @property shape the shape of the panel.
 * @property container the color of the panel.
 * @property border the color of the border of the panel.
 * @property shadow the shadow of the panel.
 * @property padding the padding inside the panel.
 * @property gap the vertical gap between the label and the rows, and between the lines of a row.
 * @property minWidth the minimum width of the panel.
 * @property labelStyle the text style of the label of the category.
 * @property nameStyle the text style of the name of a series.
 * @property nameColor the color of the name of a series and of its icon.
 * @property valueStyle the text style of a value.
 * @property valueColor the color of a value.
 * @property indicatorSize the width and height of a [ChartTooltipIndicator.Dot].
 * @property lineWidth the width of a [ChartTooltipIndicator.Line].
 * @property dashWidth the width of a [ChartTooltipIndicator.Dashed].
 * @property indicatorShape the shape of a [ChartTooltipIndicator.Dot] and a [ChartTooltipIndicator.Line].
 * @property iconSize the width and height of the icon of a series.
 * @property offset the distance between the pointer and the panel.
 * @since 1.0.0
 */
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
     * @param shape the new value of [shape].
     * @param container the new value of [container].
     * @param border the new value of [border].
     * @param shadow the new value of [shadow].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param minWidth the new value of [minWidth].
     * @param labelStyle the new value of [labelStyle].
     * @param nameStyle the new value of [nameStyle].
     * @param nameColor the new value of [nameColor].
     * @param valueStyle the new value of [valueStyle].
     * @param valueColor the new value of [valueColor].
     * @param indicatorSize the new value of [indicatorSize].
     * @param lineWidth the new value of [lineWidth].
     * @param dashWidth the new value of [dashWidth].
     * @param indicatorShape the new value of [indicatorShape].
     * @param iconSize the new value of [iconSize].
     * @param offset the new value of [offset].
     * @return the copy.
     * @since 1.0.0
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
