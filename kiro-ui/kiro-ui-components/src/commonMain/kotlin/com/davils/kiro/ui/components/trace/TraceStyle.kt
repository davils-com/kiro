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

package com.davils.kiro.ui.components.trace

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.collapsible.CollapsibleStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconGraphic

@Immutable
public class TraceStyle(
    public val gap: Dp,
    public val lineGap: Dp,
    public val textStyle: TextStyle,
    public val color: Color,
    public val hoverColor: Color,
    public val errorColor: Color,
    public val pendingAlpha: Float,
    public val descriptionColor: Color,
    public val iconSize: Dp,
    public val shape: Shape,
    public val ring: Border,
    public val chevron: IconGraphic,
    public val contentStyle: TextStyle,
    public val contentBorder: Color,
    public val contentIndent: Dp,
    public val contentPadding: Dp,
    public val shimmerColor: Color,
    public val shimmerMillis: Int,
    public val motion: CollapsibleStyle,
) {
    /**
     * Returns a copy of this [TraceStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        gap: Dp = this.gap,
        lineGap: Dp = this.lineGap,
        textStyle: TextStyle = this.textStyle,
        color: Color = this.color,
        hoverColor: Color = this.hoverColor,
        errorColor: Color = this.errorColor,
        pendingAlpha: Float = this.pendingAlpha,
        descriptionColor: Color = this.descriptionColor,
        iconSize: Dp = this.iconSize,
        shape: Shape = this.shape,
        ring: Border = this.ring,
        chevron: IconGraphic = this.chevron,
        contentStyle: TextStyle = this.contentStyle,
        contentBorder: Color = this.contentBorder,
        contentIndent: Dp = this.contentIndent,
        contentPadding: Dp = this.contentPadding,
        shimmerColor: Color = this.shimmerColor,
        shimmerMillis: Int = this.shimmerMillis,
        motion: CollapsibleStyle = this.motion,
    ): TraceStyle = TraceStyle(
        gap = gap,
        lineGap = lineGap,
        textStyle = textStyle,
        color = color,
        hoverColor = hoverColor,
        errorColor = errorColor,
        pendingAlpha = pendingAlpha,
        descriptionColor = descriptionColor,
        iconSize = iconSize,
        shape = shape,
        ring = ring,
        chevron = chevron,
        contentStyle = contentStyle,
        contentBorder = contentBorder,
        contentIndent = contentIndent,
        contentPadding = contentPadding,
        shimmerColor = shimmerColor,
        shimmerMillis = shimmerMillis,
        motion = motion,
    )

    override fun equals(other: Any?): Boolean = other is TraceStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TraceStyle(" +
        "gap=$gap, " +
        "lineGap=$lineGap, " +
        "textStyle=$textStyle, " +
        "color=$color, " +
        "hoverColor=$hoverColor, " +
        "errorColor=$errorColor, " +
        "pendingAlpha=$pendingAlpha, " +
        "descriptionColor=$descriptionColor, " +
        "iconSize=$iconSize, " +
        "shape=$shape, " +
        "ring=$ring, " +
        "chevron=$chevron, " +
        "contentStyle=$contentStyle, " +
        "contentBorder=$contentBorder, " +
        "contentIndent=$contentIndent, " +
        "contentPadding=$contentPadding, " +
        "shimmerColor=$shimmerColor, " +
        "shimmerMillis=$shimmerMillis, " +
        "motion=$motion)"

    private fun values(): List<Any?> = listOf(
        gap,
        lineGap,
        textStyle,
        color,
        hoverColor,
        errorColor,
        pendingAlpha,
        descriptionColor,
        iconSize,
        shape,
        ring,
        chevron,
        contentStyle,
        contentBorder,
        contentIndent,
        contentPadding,
        shimmerColor,
        shimmerMillis,
        motion,
    )
}
