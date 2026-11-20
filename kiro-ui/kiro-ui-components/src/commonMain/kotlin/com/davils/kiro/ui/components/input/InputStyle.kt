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

package com.davils.kiro.ui.components.input

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors

@Immutable
public class InputStyle(
    public val minHeight: Dp,
    public val maxHeight: Dp,
    public val shape: Shape,
    public val padding: PaddingValues,
    public val text: InputTextStyle,
    public val colors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [InputStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        minHeight: Dp = this.minHeight,
        maxHeight: Dp = this.maxHeight,
        shape: Shape = this.shape,
        padding: PaddingValues = this.padding,
        text: InputTextStyle = this.text,
        colors: StateColors = this.colors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): InputStyle = InputStyle(
        minHeight = minHeight,
        maxHeight = maxHeight,
        shape = shape,
        padding = padding,
        text = text,
        colors = colors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is InputStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "InputStyle(" +
        "minHeight=$minHeight, " +
        "maxHeight=$maxHeight, " +
        "shape=$shape, " +
        "padding=$padding, " +
        "text=$text, " +
        "colors=$colors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        minHeight,
        maxHeight,
        shape,
        padding,
        text,
        colors,
        borderWidth,
        ringWidth,
        disabledAlpha,
        colorAnimation,
    )
}
