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

package com.davils.kiro.ui.components.table

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

/**
 * The look of a [Table]: the text of its rows, its paddings, the backgrounds of its rows, its lines and its caption.
 *
 * @property textStyle the text style of the body rows.
 * @property headTextStyle the text style of the header rows.
 * @property headColor the content color of the header rows.
 * @property headHeight the minimum height of a header row.
 * @property headPadding the padding at the start and the end of a cell of a header row.
 * @property cellPadding the padding around a cell of a body or footer row.
 * @property hoverColor the background of a hovered row.
 * @property selectedColor the background of a selected row.
 * @property footerColor the background of a footer row.
 * @property footerTextStyle the text style of the footer rows.
 * @property border the width and color of the lines between the rows.
 * @property captionStyle the text style of the caption.
 * @property captionColor the color of the caption.
 * @property captionSpacing the space between the table and its caption.
 * @property colorAnimation the animation of the background of a row when it changes.
 * @since 1.0.0
 */
@Immutable
public class TableStyle(
    public val textStyle: TextStyle,
    public val headTextStyle: TextStyle,
    public val headColor: Color,
    public val headHeight: Dp,
    public val headPadding: Dp,
    public val cellPadding: Dp,
    public val hoverColor: Color,
    public val selectedColor: Color,
    public val footerColor: Color,
    public val footerTextStyle: TextStyle,
    public val border: Border,
    public val captionStyle: TextStyle,
    public val captionColor: Color,
    public val captionSpacing: Dp,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [TableStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        textStyle: TextStyle = this.textStyle,
        headTextStyle: TextStyle = this.headTextStyle,
        headColor: Color = this.headColor,
        headHeight: Dp = this.headHeight,
        headPadding: Dp = this.headPadding,
        cellPadding: Dp = this.cellPadding,
        hoverColor: Color = this.hoverColor,
        selectedColor: Color = this.selectedColor,
        footerColor: Color = this.footerColor,
        footerTextStyle: TextStyle = this.footerTextStyle,
        border: Border = this.border,
        captionStyle: TextStyle = this.captionStyle,
        captionColor: Color = this.captionColor,
        captionSpacing: Dp = this.captionSpacing,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): TableStyle = TableStyle(
        textStyle = textStyle,
        headTextStyle = headTextStyle,
        headColor = headColor,
        headHeight = headHeight,
        headPadding = headPadding,
        cellPadding = cellPadding,
        hoverColor = hoverColor,
        selectedColor = selectedColor,
        footerColor = footerColor,
        footerTextStyle = footerTextStyle,
        border = border,
        captionStyle = captionStyle,
        captionColor = captionColor,
        captionSpacing = captionSpacing,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is TableStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TableStyle(" +
        "textStyle=$textStyle, " +
        "headTextStyle=$headTextStyle, " +
        "headColor=$headColor, " +
        "headHeight=$headHeight, " +
        "headPadding=$headPadding, " +
        "cellPadding=$cellPadding, " +
        "hoverColor=$hoverColor, " +
        "selectedColor=$selectedColor, " +
        "footerColor=$footerColor, " +
        "footerTextStyle=$footerTextStyle, " +
        "border=$border, " +
        "captionStyle=$captionStyle, " +
        "captionColor=$captionColor, " +
        "captionSpacing=$captionSpacing, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        textStyle,
        headTextStyle,
        headColor,
        headHeight,
        headPadding,
        cellPadding,
        hoverColor,
        selectedColor,
        footerColor,
        footerTextStyle,
        border,
        captionStyle,
        captionColor,
        captionSpacing,
        colorAnimation,
    )
}
