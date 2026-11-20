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

package com.davils.kiro.ui.components.checkbox

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors

@Immutable
public class CheckboxStyle(
    public val size: Dp,
    public val shape: Shape,
    public val colors: StateColors,
    public val checkedColors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val indicator: CheckboxIndicator,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [CheckboxStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        size: Dp = this.size,
        shape: Shape = this.shape,
        colors: StateColors = this.colors,
        checkedColors: StateColors = this.checkedColors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        indicator: CheckboxIndicator = this.indicator,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): CheckboxStyle = CheckboxStyle(
        size = size,
        shape = shape,
        colors = colors,
        checkedColors = checkedColors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        indicator = indicator,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is CheckboxStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CheckboxStyle(" +
        "size=$size, " +
        "shape=$shape, " +
        "colors=$colors, " +
        "checkedColors=$checkedColors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "indicator=$indicator, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        size,
        shape,
        colors,
        checkedColors,
        borderWidth,
        ringWidth,
        indicator,
        disabledAlpha,
        colorAnimation,
    )
}
