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

package com.davils.kiro.ui.components.marker

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * The look of a [Marker].
 *
 * @property textStyle the text style of the content.
 * @property color the content color of the content.
 * @property gap the space between the parts of the row.
 * @property iconSize the size of the icons.
 * @property lineColor the color of the lines of [MarkerVariant.Separator] and [MarkerVariant.Border].
 * @property lineGap the extra space between the content and the lines of [MarkerVariant.Separator].
 * @property borderPadding the space between the content and the line of [MarkerVariant.Border].
 * @property minHeight the smallest height of the row.
 * @since 1.0.0
 */
@Immutable
public class MarkerStyle(
    public val textStyle: TextStyle,
    public val color: Color,
    public val gap: Dp,
    public val iconSize: Dp,
    public val lineColor: Color,
    public val lineGap: Dp,
    public val borderPadding: Dp,
    public val minHeight: Dp,
) {
    /**
     * Returns a copy of this [MarkerStyle] with the given values replaced and every other value kept.
     *
     * @param textStyle the new value of [textStyle].
     * @param color the new value of [color].
     * @param gap the new value of [gap].
     * @param iconSize the new value of [iconSize].
     * @param lineColor the new value of [lineColor].
     * @param lineGap the new value of [lineGap].
     * @param borderPadding the new value of [borderPadding].
     * @param minHeight the new value of [minHeight].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        textStyle: TextStyle = this.textStyle,
        color: Color = this.color,
        gap: Dp = this.gap,
        iconSize: Dp = this.iconSize,
        lineColor: Color = this.lineColor,
        lineGap: Dp = this.lineGap,
        borderPadding: Dp = this.borderPadding,
        minHeight: Dp = this.minHeight,
    ): MarkerStyle = MarkerStyle(
        textStyle = textStyle,
        color = color,
        gap = gap,
        iconSize = iconSize,
        lineColor = lineColor,
        lineGap = lineGap,
        borderPadding = borderPadding,
        minHeight = minHeight,
    )

    override fun equals(other: Any?): Boolean = other is MarkerStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MarkerStyle(" +
        "textStyle=$textStyle, " +
        "color=$color, " +
        "gap=$gap, " +
        "iconSize=$iconSize, " +
        "lineColor=$lineColor, " +
        "lineGap=$lineGap, " +
        "borderPadding=$borderPadding, " +
        "minHeight=$minHeight)"

    private fun values(): List<Any?> = listOf(
        textStyle,
        color,
        gap,
        iconSize,
        lineColor,
        lineGap,
        borderPadding,
        minHeight,
    )
}
