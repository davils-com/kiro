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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
public class ChartLegendStyle(
    public val gap: Dp,
    public val itemGap: Dp,
    public val swatchSize: Dp,
    public val swatchShape: Shape,
    public val spacing: Dp,
    public val iconSize: Dp,
    public val iconColor: Color,
    public val textStyle: TextStyle,
) {
    /**
     * Returns a copy of this [ChartLegendStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        gap: Dp = this.gap,
        itemGap: Dp = this.itemGap,
        swatchSize: Dp = this.swatchSize,
        swatchShape: Shape = this.swatchShape,
        spacing: Dp = this.spacing,
        iconSize: Dp = this.iconSize,
        iconColor: Color = this.iconColor,
        textStyle: TextStyle = this.textStyle,
    ): ChartLegendStyle = ChartLegendStyle(
        gap = gap,
        itemGap = itemGap,
        swatchSize = swatchSize,
        swatchShape = swatchShape,
        spacing = spacing,
        iconSize = iconSize,
        iconColor = iconColor,
        textStyle = textStyle,
    )

    override fun equals(other: Any?): Boolean = other is ChartLegendStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ChartLegendStyle(" +
        "gap=$gap, " +
        "itemGap=$itemGap, " +
        "swatchSize=$swatchSize, " +
        "swatchShape=$swatchShape, " +
        "spacing=$spacing, " +
        "iconSize=$iconSize, " +
        "iconColor=$iconColor, " +
        "textStyle=$textStyle)"

    private fun values(): List<Any?> = listOf(
        gap,
        itemGap,
        swatchSize,
        swatchShape,
        spacing,
        iconSize,
        iconColor,
        textStyle,
    )
}
