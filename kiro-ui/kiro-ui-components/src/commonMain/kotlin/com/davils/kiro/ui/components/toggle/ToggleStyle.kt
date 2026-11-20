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

package com.davils.kiro.ui.components.toggle

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors

/**
 * How a toggle looks: its size, its padding, its shape, its text and icons, and its colors while it is pressed or not.
 *
 * @property height the height of the toggle.
 * @property minWidth the smallest width of the toggle.
 * @property horizontalPadding the padding at the start and the end of the toggle.
 * @property iconEdgePadding the padding between a leading or trailing icon and the edge of the toggle, which replaces
 * [horizontalPadding] on that side.
 * @property gap the space between the children of the label.
 * @property shape the shape of the toggle outside of a joined group.
 * @property textStyle the text style of the label.
 * @property iconSize the size of the icons of the label.
 * @property colors the colors of the toggle while it is not pressed, in each interaction state.
 * @property checkedColors the colors of the toggle while it is pressed, in each interaction state.
 * @property borderWidth the width of the border; in a joined group, neighbouring toggles overlap by this width.
 * @property ringWidth the width of the focus ring.
 * @property disabledAlpha the alpha of a disabled toggle.
 * @property colorAnimation the animation of the colors when the pressed state or the interaction state changes.
 * @since 1.0.0
 */
@Immutable
public class ToggleStyle(
    public val height: Dp,
    public val minWidth: Dp,
    public val horizontalPadding: Dp,
    public val iconEdgePadding: Dp,
    public val gap: Dp,
    public val shape: Shape,
    public val textStyle: TextStyle,
    public val iconSize: Dp,
    public val colors: StateColors,
    public val checkedColors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [ToggleStyle] with the given values replaced and every other value kept.
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
     * @param checkedColors the new value of [checkedColors].
     * @param borderWidth the new value of [borderWidth].
     * @param ringWidth the new value of [ringWidth].
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
        checkedColors: StateColors = this.checkedColors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): ToggleStyle = ToggleStyle(
        height = height,
        minWidth = minWidth,
        horizontalPadding = horizontalPadding,
        iconEdgePadding = iconEdgePadding,
        gap = gap,
        shape = shape,
        textStyle = textStyle,
        iconSize = iconSize,
        colors = colors,
        checkedColors = checkedColors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is ToggleStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ToggleStyle(" +
        "height=$height, " +
        "minWidth=$minWidth, " +
        "horizontalPadding=$horizontalPadding, " +
        "iconEdgePadding=$iconEdgePadding, " +
        "gap=$gap, " +
        "shape=$shape, " +
        "textStyle=$textStyle, " +
        "iconSize=$iconSize, " +
        "colors=$colors, " +
        "checkedColors=$checkedColors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
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
        checkedColors,
        borderWidth,
        ringWidth,
        disabledAlpha,
        colorAnimation,
    )
}
