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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors

/**
 * The look of an [InputGroup]: its field, its control and its addons.
 *
 * @property minHeight the height of a group with a single-line input and no addon above or below it, and the minimum
 * height of every other group.
 * @property shape the shape of the field.
 * @property colors the colors of the field, the text, the border and the focus ring in each state.
 * @property borderWidth the width of the border.
 * @property ringWidth the width of the focus ring.
 * @property disabledAlpha the opacity of a disabled group, from 0 to 1.
 * @property colorAnimation the animation of the colors when the state changes.
 * @property control the style of the input or textarea.
 * @property addon the style of the addons.
 * @since 1.0.0
 */
@Immutable
public class InputGroupStyle(
    public val minHeight: Dp,
    public val shape: Shape,
    public val colors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
    public val control: InputGroupControlStyle,
    public val addon: InputGroupAddonStyle,
) {
    /**
     * Returns a copy of this [InputGroupStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        minHeight: Dp = this.minHeight,
        shape: Shape = this.shape,
        colors: StateColors = this.colors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
        control: InputGroupControlStyle = this.control,
        addon: InputGroupAddonStyle = this.addon,
    ): InputGroupStyle = InputGroupStyle(
        minHeight = minHeight,
        shape = shape,
        colors = colors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
        control = control,
        addon = addon,
    )

    override fun equals(other: Any?): Boolean = other is InputGroupStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "InputGroupStyle(" +
        "minHeight=$minHeight, " +
        "shape=$shape, " +
        "colors=$colors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation, " +
        "control=$control, " +
        "addon=$addon)"

    private fun values(): List<Any?> = listOf(
        minHeight,
        shape,
        colors,
        borderWidth,
        ringWidth,
        disabledAlpha,
        colorAnimation,
        control,
        addon,
    )
}
