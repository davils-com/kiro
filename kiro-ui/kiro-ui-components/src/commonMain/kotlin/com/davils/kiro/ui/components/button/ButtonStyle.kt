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

package com.davils.kiro.ui.components.button

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors

/**
 * The style of a [Button]: its metrics, its shape, its text, its colors and how it reacts to presses.
 *
 * @property height the height of the button.
 * @property minWidth the smallest width of the button, which makes the icon sizes square.
 * @property horizontalPadding the space between the edges of the button and its content.
 * @property iconEdgePadding the space between an edge of the button and an icon placed at that edge, which replaces
 * [horizontalPadding] on that side.
 * @property gap the space between the items of the content.
 * @property shape the shape of the container, its border and its focus ring.
 * @property textStyle the style of the label.
 * @property iconSize the size of the icons of the content.
 * @property colors the colors of the container, the content, the border and the focus ring in each interaction state.
 * @property borderWidth the width of the border.
 * @property ringWidth the width of the focus ring.
 * @property isUnderlinedOnHover whether the label is underlined while the button is hovered.
 * @property pressOffset how far the button moves down while it is pressed.
 * @property disabledAlpha the opacity of the whole button while it is disabled.
 * @property colorAnimation the animation of the colors when the interaction state changes.
 * @since 1.0.0
 */
@Immutable
public class ButtonStyle(
    public val height: Dp,
    public val minWidth: Dp,
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
    public val pressOffset: Dp,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [ButtonStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param minWidth the new value of [minWidth].
     * @param horizontalPadding the new value of [horizontalPadding].
     * @param iconEdgePadding the new value of [iconEdgePadding].
     * @param gap the new value of [gap].
     * @param shape the new value of [shape].
     * @param textStyle the new value of [textStyle].
     * @param iconSize the new value of [iconSize].
     * @param colors the new value of [colors].
     * @param borderWidth the new value of [borderWidth].
     * @param ringWidth the new value of [ringWidth].
     * @param isUnderlinedOnHover the new value of [isUnderlinedOnHover].
     * @param pressOffset the new value of [pressOffset].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @param colorAnimation the new value of [colorAnimation].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        minWidth: Dp = this.minWidth,
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
        pressOffset: Dp = this.pressOffset,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): ButtonStyle = ButtonStyle(
        height = height,
        minWidth = minWidth,
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
        pressOffset = pressOffset,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is ButtonStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ButtonStyle(" +
        "height=$height, " +
        "minWidth=$minWidth, " +
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
        "pressOffset=$pressOffset, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        height,
        minWidth,
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
        pressOffset,
        disabledAlpha,
        colorAnimation,
    )
}
