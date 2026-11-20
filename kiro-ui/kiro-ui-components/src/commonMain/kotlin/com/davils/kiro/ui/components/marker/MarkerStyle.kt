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
     * @since 0.1.0
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
