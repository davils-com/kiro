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

package com.davils.kiro.ui.components.item

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors

/**
 * The style of an [Item] and of its parts.
 *
 * @property shape the shape of the item.
 * @property colors the container, content, border and ring colors of the item in each interaction state.
 * @property borderWidth the width of the border.
 * @property ringWidth the width of the focus ring.
 * @property gap the gap between the header, the row and the footer, and between the parts of the row.
 * @property horizontalPadding the padding at the start and the end of the item.
 * @property verticalPadding the padding at the top and the bottom of the item.
 * @property textStyle the text style of the content of the item.
 * @property mediaGap a gap for content placed next to the media; the item itself spaces its parts by [gap].
 * @property imageSize the width and height of the media of the [ItemMediaVariant.Image] variant.
 * @property imageShape the shape that the media of the [ItemMediaVariant.Image] variant is clipped to.
 * @property iconSize the size of icons in the media.
 * @property contentGap the gap between the title and the description.
 * @property titleStyle the text style of the title.
 * @property descriptionStyle the text style of the description.
 * @property descriptionColor the color of the description.
 * @property actionsGap the gap between the actions, and between the parts of the header and the footer.
 * @property colorAnimation the animation of the colors between interaction states.
 * @since 1.0.0
 */
@Immutable
public class ItemStyle(
    public val shape: Shape,
    public val colors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val gap: Dp,
    public val horizontalPadding: Dp,
    public val verticalPadding: Dp,
    public val textStyle: TextStyle,
    public val mediaGap: Dp,
    public val imageSize: Dp,
    public val imageShape: Shape,
    public val iconSize: Dp,
    public val contentGap: Dp,
    public val titleStyle: TextStyle,
    public val descriptionStyle: TextStyle,
    public val descriptionColor: Color,
    public val actionsGap: Dp,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [ItemStyle] with the given values replaced and every other value kept.
     *
     * @param shape the new value of [shape].
     * @param colors the new value of [colors].
     * @param borderWidth the new value of [borderWidth].
     * @param ringWidth the new value of [ringWidth].
     * @param gap the new value of [gap].
     * @param horizontalPadding the new value of [horizontalPadding].
     * @param verticalPadding the new value of [verticalPadding].
     * @param textStyle the new value of [textStyle].
     * @param mediaGap the new value of [mediaGap].
     * @param imageSize the new value of [imageSize].
     * @param imageShape the new value of [imageShape].
     * @param iconSize the new value of [iconSize].
     * @param contentGap the new value of [contentGap].
     * @param titleStyle the new value of [titleStyle].
     * @param descriptionStyle the new value of [descriptionStyle].
     * @param descriptionColor the new value of [descriptionColor].
     * @param actionsGap the new value of [actionsGap].
     * @param colorAnimation the new value of [colorAnimation].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        shape: Shape = this.shape,
        colors: StateColors = this.colors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        gap: Dp = this.gap,
        horizontalPadding: Dp = this.horizontalPadding,
        verticalPadding: Dp = this.verticalPadding,
        textStyle: TextStyle = this.textStyle,
        mediaGap: Dp = this.mediaGap,
        imageSize: Dp = this.imageSize,
        imageShape: Shape = this.imageShape,
        iconSize: Dp = this.iconSize,
        contentGap: Dp = this.contentGap,
        titleStyle: TextStyle = this.titleStyle,
        descriptionStyle: TextStyle = this.descriptionStyle,
        descriptionColor: Color = this.descriptionColor,
        actionsGap: Dp = this.actionsGap,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): ItemStyle = ItemStyle(
        shape = shape,
        colors = colors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        gap = gap,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
        textStyle = textStyle,
        mediaGap = mediaGap,
        imageSize = imageSize,
        imageShape = imageShape,
        iconSize = iconSize,
        contentGap = contentGap,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle,
        descriptionColor = descriptionColor,
        actionsGap = actionsGap,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is ItemStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ItemStyle(" +
        "shape=$shape, " +
        "colors=$colors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "gap=$gap, " +
        "horizontalPadding=$horizontalPadding, " +
        "verticalPadding=$verticalPadding, " +
        "textStyle=$textStyle, " +
        "mediaGap=$mediaGap, " +
        "imageSize=$imageSize, " +
        "imageShape=$imageShape, " +
        "iconSize=$iconSize, " +
        "contentGap=$contentGap, " +
        "titleStyle=$titleStyle, " +
        "descriptionStyle=$descriptionStyle, " +
        "descriptionColor=$descriptionColor, " +
        "actionsGap=$actionsGap, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        shape,
        colors,
        borderWidth,
        ringWidth,
        gap,
        horizontalPadding,
        verticalPadding,
        textStyle,
        mediaGap,
        imageSize,
        imageShape,
        iconSize,
        contentGap,
        titleStyle,
        descriptionStyle,
        descriptionColor,
        actionsGap,
        colorAnimation,
    )
}
