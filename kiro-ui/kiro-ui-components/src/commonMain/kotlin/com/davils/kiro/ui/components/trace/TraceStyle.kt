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

/**
 * The style of a [Trace] and of its lines and collapsibles.
 *
 * @property gap the gap between the lines of a trace; it also spaces the details of a collapsible from its trigger.
 * @property lineGap the gap between the parts of a line.
 * @property textStyle the text style of the lines.
 * @property color the content color of the lines and the details.
 * @property hoverColor the content color of a clickable line while hovered.
 * @property errorColor the content color of a line with [TraceStatus.Error].
 * @property pendingAlpha the alpha of a line with [TraceStatus.Pending].
 * @property descriptionColor the color of a [TraceDescription].
 * @property iconSize the size of a [TraceIcon] and of the icons in a trace.
 * @property shape the shape of a line, which the focus ring follows.
 * @property ring the focus ring of a clickable line.
 * @property chevron the icon at the end of a [TraceCollapsibleTrigger].
 * @property contentStyle the text style of the details of a collapsible.
 * @property contentBorder the color of the line along the start of the details.
 * @property contentIndent the indent of the details before their line.
 * @property contentPadding the padding between the line and the details.
 * @property shimmerColor the highlight color of the shimmer of a running title.
 * @property shimmerMillis the duration of one run of the shimmer, in milliseconds.
 * @property motion the style of the collapsible that expands and collapses the details.
 * @since 1.0.0
 */
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
     * @param gap the new value of [gap].
     * @param lineGap the new value of [lineGap].
     * @param textStyle the new value of [textStyle].
     * @param color the new value of [color].
     * @param hoverColor the new value of [hoverColor].
     * @param errorColor the new value of [errorColor].
     * @param pendingAlpha the new value of [pendingAlpha].
     * @param descriptionColor the new value of [descriptionColor].
     * @param iconSize the new value of [iconSize].
     * @param shape the new value of [shape].
     * @param ring the new value of [ring].
     * @param chevron the new value of [chevron].
     * @param contentStyle the new value of [contentStyle].
     * @param contentBorder the new value of [contentBorder].
     * @param contentIndent the new value of [contentIndent].
     * @param contentPadding the new value of [contentPadding].
     * @param shimmerColor the new value of [shimmerColor].
     * @param shimmerMillis the new value of [shimmerMillis].
     * @param motion the new value of [motion].
     * @return the copy.
     * @since 1.0.0
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
