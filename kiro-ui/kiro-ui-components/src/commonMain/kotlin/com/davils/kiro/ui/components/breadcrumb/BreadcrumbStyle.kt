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

package com.davils.kiro.ui.components.breadcrumb

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The look of a [Breadcrumb] and its parts.
 *
 * @property gap the space between the items and separators on a line of a breadcrumb.
 * @property itemGap the space between the parts of a [BreadcrumbItem].
 * @property textStyle the text style handed to the content of a breadcrumb.
 * @property color the color of the content of a breadcrumb and of a [BreadcrumbLink].
 * @property hoverColor the color of a [BreadcrumbLink] under the pointer.
 * @property pageColor the color of a [BreadcrumbPage].
 * @property separatorIcon the icon of a [BreadcrumbSeparator].
 * @property separatorSize the size of the icon of a [BreadcrumbSeparator].
 * @property ellipsisIcon the icon of a [BreadcrumbEllipsis].
 * @property ellipsisSize the size of the icon of a [BreadcrumbEllipsis].
 * @property ellipsisBox the width and height of the box around the icon of a [BreadcrumbEllipsis].
 * @property ellipsisShape the shape of the background of a [BreadcrumbEllipsis] that opens a menu.
 * @property ellipsisHover the background color of a [BreadcrumbEllipsis] that opens a menu, under the pointer.
 * @since 1.0.0
 */
@Immutable
public class BreadcrumbStyle(
    public val gap: Dp,
    public val itemGap: Dp,
    public val textStyle: TextStyle,
    public val color: Color,
    public val hoverColor: Color,
    public val pageColor: Color,
    public val separatorIcon: IconGraphic,
    public val separatorSize: Dp,
    public val ellipsisIcon: IconGraphic,
    public val ellipsisSize: Dp,
    public val ellipsisBox: Dp,
    public val ellipsisShape: Shape,
    public val ellipsisHover: Color,
) {
    /**
     * Returns a copy of this [BreadcrumbStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        gap: Dp = this.gap,
        itemGap: Dp = this.itemGap,
        textStyle: TextStyle = this.textStyle,
        color: Color = this.color,
        hoverColor: Color = this.hoverColor,
        pageColor: Color = this.pageColor,
        separatorIcon: IconGraphic = this.separatorIcon,
        separatorSize: Dp = this.separatorSize,
        ellipsisIcon: IconGraphic = this.ellipsisIcon,
        ellipsisSize: Dp = this.ellipsisSize,
        ellipsisBox: Dp = this.ellipsisBox,
        ellipsisShape: Shape = this.ellipsisShape,
        ellipsisHover: Color = this.ellipsisHover,
    ): BreadcrumbStyle = BreadcrumbStyle(
        gap = gap,
        itemGap = itemGap,
        textStyle = textStyle,
        color = color,
        hoverColor = hoverColor,
        pageColor = pageColor,
        separatorIcon = separatorIcon,
        separatorSize = separatorSize,
        ellipsisIcon = ellipsisIcon,
        ellipsisSize = ellipsisSize,
        ellipsisBox = ellipsisBox,
        ellipsisShape = ellipsisShape,
        ellipsisHover = ellipsisHover,
    )

    override fun equals(other: Any?): Boolean = other is BreadcrumbStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BreadcrumbStyle(" +
        "gap=$gap, " +
        "itemGap=$itemGap, " +
        "textStyle=$textStyle, " +
        "color=$color, " +
        "hoverColor=$hoverColor, " +
        "pageColor=$pageColor, " +
        "separatorIcon=$separatorIcon, " +
        "separatorSize=$separatorSize, " +
        "ellipsisIcon=$ellipsisIcon, " +
        "ellipsisSize=$ellipsisSize, " +
        "ellipsisBox=$ellipsisBox, " +
        "ellipsisShape=$ellipsisShape, " +
        "ellipsisHover=$ellipsisHover)"

    private fun values(): List<Any?> = listOf(
        gap,
        itemGap,
        textStyle,
        color,
        hoverColor,
        pageColor,
        separatorIcon,
        separatorSize,
        ellipsisIcon,
        ellipsisSize,
        ellipsisBox,
        ellipsisShape,
        ellipsisHover,
    )
}
