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

/**
 * The look of a [Select]: its field, its icon and its menu.
 *
 * @property height the height of the field.
 * @property shape the shape of the field.
 * @property startPadding the padding before the text of the field.
 * @property endPadding the padding after the icon of the field.
 * @property gap the smallest space between the text and the icon of the field.
 * @property textStyle the text style of the field and of the menu.
 * @property placeholderColor the color of the placeholder.
 * @property colors the container, content, border and ring colors of the field by interaction state.
 * @property borderWidth the width of the border of the field.
 * @property ringWidth the width of the focus ring of the field.
 * @property icon the icon at the end of the field.
 * @property iconSize the size of the icon.
 * @property iconColor the color of the icon.
 * @property menu the style of the menu.
 * @property minWidth the smallest width of the menu.
 * @property disabledAlpha the opacity of a disabled field.
 * @property colorAnimation the animation of the colors of the field.
 * @since 1.0.0
 */
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
     * @param height the new value of [height].
     * @param shape the new value of [shape].
     * @param startPadding the new value of [startPadding].
     * @param endPadding the new value of [endPadding].
     * @param gap the new value of [gap].
     * @param textStyle the new value of [textStyle].
     * @param placeholderColor the new value of [placeholderColor].
     * @param colors the new value of [colors].
     * @param borderWidth the new value of [borderWidth].
     * @param ringWidth the new value of [ringWidth].
     * @param icon the new value of [icon].
     * @param iconSize the new value of [iconSize].
     * @param iconColor the new value of [iconColor].
     * @param menu the new value of [menu].
     * @param minWidth the new value of [minWidth].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @param colorAnimation the new value of [colorAnimation].
     * @return the copy.
     * @since 1.0.0
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
