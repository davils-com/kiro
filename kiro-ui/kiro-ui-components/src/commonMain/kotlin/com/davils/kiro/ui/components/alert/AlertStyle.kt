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

package com.davils.kiro.ui.components.alert

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

/**
 * How an [Alert] looks: its shape, colors, paddings, gaps, the size and place of its icon, its text styles and the
 * place of its action.
 *
 * @property shape the shape of the alert.
 * @property container the color of the container.
 * @property content the color of the content, which the alert hands down to its icon and texts.
 * @property border the border of the alert.
 * @property horizontalPadding the padding at the start and the end of the content.
 * @property verticalPadding the padding at the top and the bottom of the content.
 * @property gap the vertical gap between the parts of the content, such as the title and the description.
 * @property iconGap the gap between the icon and the content.
 * @property iconSize the width and height of the icon.
 * @property iconOffset how far the icon is moved down to line up with the first line of text.
 * @property textStyle the text style the alert hands down to its content.
 * @property titleStyle the text style of an [AlertTitle].
 * @property descriptionStyle the text style of an [AlertDescription].
 * @property descriptionColor the color of an [AlertDescription].
 * @property actionInset the distance of the action from the top and the end of the alert.
 * @property actionReserve the space kept free for the action at the end of the content, padding included.
 * @since 1.0.0
 */
@Immutable
public class AlertStyle(
    public val shape: Shape,
    public val container: Color,
    public val content: Color,
    public val border: Border,
    public val horizontalPadding: Dp,
    public val verticalPadding: Dp,
    public val gap: Dp,
    public val iconGap: Dp,
    public val iconSize: Dp,
    public val iconOffset: Dp,
    public val textStyle: TextStyle,
    public val titleStyle: TextStyle,
    public val descriptionStyle: TextStyle,
    public val descriptionColor: Color,
    public val actionInset: Dp,
    public val actionReserve: Dp,
) {
    /**
     * Returns a copy of this [AlertStyle] with the given values replaced and every other value kept.
     *
     * @param shape the new value of [shape].
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param border the new value of [border].
     * @param horizontalPadding the new value of [horizontalPadding].
     * @param verticalPadding the new value of [verticalPadding].
     * @param gap the new value of [gap].
     * @param iconGap the new value of [iconGap].
     * @param iconSize the new value of [iconSize].
     * @param iconOffset the new value of [iconOffset].
     * @param textStyle the new value of [textStyle].
     * @param titleStyle the new value of [titleStyle].
     * @param descriptionStyle the new value of [descriptionStyle].
     * @param descriptionColor the new value of [descriptionColor].
     * @param actionInset the new value of [actionInset].
     * @param actionReserve the new value of [actionReserve].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        shape: Shape = this.shape,
        container: Color = this.container,
        content: Color = this.content,
        border: Border = this.border,
        horizontalPadding: Dp = this.horizontalPadding,
        verticalPadding: Dp = this.verticalPadding,
        gap: Dp = this.gap,
        iconGap: Dp = this.iconGap,
        iconSize: Dp = this.iconSize,
        iconOffset: Dp = this.iconOffset,
        textStyle: TextStyle = this.textStyle,
        titleStyle: TextStyle = this.titleStyle,
        descriptionStyle: TextStyle = this.descriptionStyle,
        descriptionColor: Color = this.descriptionColor,
        actionInset: Dp = this.actionInset,
        actionReserve: Dp = this.actionReserve,
    ): AlertStyle = AlertStyle(
        shape = shape,
        container = container,
        content = content,
        border = border,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
        gap = gap,
        iconGap = iconGap,
        iconSize = iconSize,
        iconOffset = iconOffset,
        textStyle = textStyle,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle,
        descriptionColor = descriptionColor,
        actionInset = actionInset,
        actionReserve = actionReserve,
    )

    override fun equals(other: Any?): Boolean = other is AlertStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AlertStyle(" +
        "shape=$shape, " +
        "container=$container, " +
        "content=$content, " +
        "border=$border, " +
        "horizontalPadding=$horizontalPadding, " +
        "verticalPadding=$verticalPadding, " +
        "gap=$gap, " +
        "iconGap=$iconGap, " +
        "iconSize=$iconSize, " +
        "iconOffset=$iconOffset, " +
        "textStyle=$textStyle, " +
        "titleStyle=$titleStyle, " +
        "descriptionStyle=$descriptionStyle, " +
        "descriptionColor=$descriptionColor, " +
        "actionInset=$actionInset, " +
        "actionReserve=$actionReserve)"

    private fun values(): List<Any?> = listOf(
        shape,
        container,
        content,
        border,
        horizontalPadding,
        verticalPadding,
        gap,
        iconGap,
        iconSize,
        iconOffset,
        textStyle,
        titleStyle,
        descriptionStyle,
        descriptionColor,
        actionInset,
        actionReserve,
    )
}
