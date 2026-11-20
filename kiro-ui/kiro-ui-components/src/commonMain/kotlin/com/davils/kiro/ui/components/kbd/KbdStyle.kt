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

package com.davils.kiro.ui.components.kbd

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * The style of a [Kbd] and of a [KbdGroup].
 *
 * @property height the height of a key.
 * @property minWidth the smallest width of a key.
 * @property horizontalPadding the horizontal padding inside a key.
 * @property gap the space between the parts of a key.
 * @property groupGap the space between the keys of a [KbdGroup].
 * @property shape the shape of a key.
 * @property container the color of the container of a key.
 * @property content the color of the text and icons of a key.
 * @property textStyle the text style of a key.
 * @property iconSize the size of the icons of a key.
 * @since 1.0.0
 */
@Immutable
public class KbdStyle(
    public val height: Dp,
    public val minWidth: Dp,
    public val horizontalPadding: Dp,
    public val gap: Dp,
    public val groupGap: Dp,
    public val shape: Shape,
    public val container: Color,
    public val content: Color,
    public val textStyle: TextStyle,
    public val iconSize: Dp,
) {
    /**
     * Returns a copy of this [KbdStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        minWidth: Dp = this.minWidth,
        horizontalPadding: Dp = this.horizontalPadding,
        gap: Dp = this.gap,
        groupGap: Dp = this.groupGap,
        shape: Shape = this.shape,
        container: Color = this.container,
        content: Color = this.content,
        textStyle: TextStyle = this.textStyle,
        iconSize: Dp = this.iconSize,
    ): KbdStyle = KbdStyle(
        height = height,
        minWidth = minWidth,
        horizontalPadding = horizontalPadding,
        gap = gap,
        groupGap = groupGap,
        shape = shape,
        container = container,
        content = content,
        textStyle = textStyle,
        iconSize = iconSize,
    )

    override fun equals(other: Any?): Boolean = other is KbdStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "KbdStyle(" +
        "height=$height, " +
        "minWidth=$minWidth, " +
        "horizontalPadding=$horizontalPadding, " +
        "gap=$gap, " +
        "groupGap=$groupGap, " +
        "shape=$shape, " +
        "container=$container, " +
        "content=$content, " +
        "textStyle=$textStyle, " +
        "iconSize=$iconSize)"

    private fun values(): List<Any?> = listOf(
        height,
        minWidth,
        horizontalPadding,
        gap,
        groupGap,
        shape,
        container,
        content,
        textStyle,
        iconSize,
    )
}
