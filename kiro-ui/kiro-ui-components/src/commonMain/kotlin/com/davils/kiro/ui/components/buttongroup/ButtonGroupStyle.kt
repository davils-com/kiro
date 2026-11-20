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

package com.davils.kiro.ui.components.buttongroup

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

@Immutable
public class ButtonGroupStyle(
    public val radius: Dp,
    public val overlap: Dp,
    public val separatorColor: Color,
    public val separatorThickness: Dp,
    public val separatorInset: Dp,
    public val textContainer: Color,
    public val textBorder: Border,
    public val textStyle: TextStyle,
    public val textPadding: Dp,
    public val textGap: Dp,
    public val iconSize: Dp,
) {
    /**
     * Returns a copy of this [ButtonGroupStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        radius: Dp = this.radius,
        overlap: Dp = this.overlap,
        separatorColor: Color = this.separatorColor,
        separatorThickness: Dp = this.separatorThickness,
        separatorInset: Dp = this.separatorInset,
        textContainer: Color = this.textContainer,
        textBorder: Border = this.textBorder,
        textStyle: TextStyle = this.textStyle,
        textPadding: Dp = this.textPadding,
        textGap: Dp = this.textGap,
        iconSize: Dp = this.iconSize,
    ): ButtonGroupStyle = ButtonGroupStyle(
        radius = radius,
        overlap = overlap,
        separatorColor = separatorColor,
        separatorThickness = separatorThickness,
        separatorInset = separatorInset,
        textContainer = textContainer,
        textBorder = textBorder,
        textStyle = textStyle,
        textPadding = textPadding,
        textGap = textGap,
        iconSize = iconSize,
    )

    override fun equals(other: Any?): Boolean = other is ButtonGroupStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ButtonGroupStyle(" +
        "radius=$radius, " +
        "overlap=$overlap, " +
        "separatorColor=$separatorColor, " +
        "separatorThickness=$separatorThickness, " +
        "separatorInset=$separatorInset, " +
        "textContainer=$textContainer, " +
        "textBorder=$textBorder, " +
        "textStyle=$textStyle, " +
        "textPadding=$textPadding, " +
        "textGap=$textGap, " +
        "iconSize=$iconSize)"

    private fun values(): List<Any?> = listOf(
        radius,
        overlap,
        separatorColor,
        separatorThickness,
        separatorInset,
        textContainer,
        textBorder,
        textStyle,
        textPadding,
        textGap,
        iconSize,
    )
}
