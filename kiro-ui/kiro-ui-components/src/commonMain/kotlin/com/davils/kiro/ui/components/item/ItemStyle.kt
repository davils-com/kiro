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
     * @since 0.1.0
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
