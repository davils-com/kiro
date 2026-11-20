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

package com.davils.kiro.ui.components.select

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.menu.MenuStyle
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.icons.IconGraphic

@Immutable
public class SelectStyle(
    public val height: Dp,
    public val shape: Shape,
    public val startPadding: Dp,
    public val endPadding: Dp,
    public val gap: Dp,
    public val textStyle: TextStyle,
    public val placeholderColor: Color,
    public val colors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val icon: IconGraphic,
    public val iconSize: Dp,
    public val iconColor: Color,
    public val menu: MenuStyle,
    public val minWidth: Dp,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [SelectStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        height: Dp = this.height,
        shape: Shape = this.shape,
        startPadding: Dp = this.startPadding,
        endPadding: Dp = this.endPadding,
        gap: Dp = this.gap,
        textStyle: TextStyle = this.textStyle,
        placeholderColor: Color = this.placeholderColor,
        colors: StateColors = this.colors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        icon: IconGraphic = this.icon,
        iconSize: Dp = this.iconSize,
        iconColor: Color = this.iconColor,
        menu: MenuStyle = this.menu,
        minWidth: Dp = this.minWidth,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): SelectStyle = SelectStyle(
        height = height,
        shape = shape,
        startPadding = startPadding,
        endPadding = endPadding,
        gap = gap,
        textStyle = textStyle,
        placeholderColor = placeholderColor,
        colors = colors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        icon = icon,
        iconSize = iconSize,
        iconColor = iconColor,
        menu = menu,
        minWidth = minWidth,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is SelectStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SelectStyle(" +
        "height=$height, " +
        "shape=$shape, " +
        "startPadding=$startPadding, " +
        "endPadding=$endPadding, " +
        "gap=$gap, " +
        "textStyle=$textStyle, " +
        "placeholderColor=$placeholderColor, " +
        "colors=$colors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "icon=$icon, " +
        "iconSize=$iconSize, " +
        "iconColor=$iconColor, " +
        "menu=$menu, " +
        "minWidth=$minWidth, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        height,
        shape,
        startPadding,
        endPadding,
        gap,
        textStyle,
        placeholderColor,
        colors,
        borderWidth,
        ringWidth,
        icon,
        iconSize,
        iconColor,
        menu,
        minWidth,
        disabledAlpha,
        colorAnimation,
    )
}
