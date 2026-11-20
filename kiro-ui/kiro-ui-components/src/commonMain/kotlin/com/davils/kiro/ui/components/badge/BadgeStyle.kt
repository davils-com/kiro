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

package com.davils.kiro.ui.components.badge

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors

@Immutable
public class BadgeStyle(
    public val height: Dp,
    public val horizontalPadding: Dp,
    public val iconEdgePadding: Dp,
    public val gap: Dp,
    public val shape: Shape,
    public val textStyle: TextStyle,
    public val iconSize: Dp,
    public val colors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val isUnderlinedOnHover: Boolean,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [BadgeStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        height: Dp = this.height,
        horizontalPadding: Dp = this.horizontalPadding,
        iconEdgePadding: Dp = this.iconEdgePadding,
        gap: Dp = this.gap,
        shape: Shape = this.shape,
        textStyle: TextStyle = this.textStyle,
        iconSize: Dp = this.iconSize,
        colors: StateColors = this.colors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        isUnderlinedOnHover: Boolean = this.isUnderlinedOnHover,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): BadgeStyle = BadgeStyle(
        height = height,
        horizontalPadding = horizontalPadding,
        iconEdgePadding = iconEdgePadding,
        gap = gap,
        shape = shape,
        textStyle = textStyle,
        iconSize = iconSize,
        colors = colors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        isUnderlinedOnHover = isUnderlinedOnHover,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is BadgeStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BadgeStyle(" +
        "height=$height, " +
        "horizontalPadding=$horizontalPadding, " +
        "iconEdgePadding=$iconEdgePadding, " +
        "gap=$gap, " +
        "shape=$shape, " +
        "textStyle=$textStyle, " +
        "iconSize=$iconSize, " +
        "colors=$colors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "isUnderlinedOnHover=$isUnderlinedOnHover, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        height,
        horizontalPadding,
        iconEdgePadding,
        gap,
        shape,
        textStyle,
        iconSize,
        colors,
        borderWidth,
        ringWidth,
        isUnderlinedOnHover,
        colorAnimation,
    )
}
