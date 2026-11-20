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

package com.davils.kiro.ui.components.inputotp

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.icons.IconGraphic

@Immutable
public class InputOtpStyle(
    public val slotSize: Dp,
    public val radius: Dp,
    public val textStyle: TextStyle,
    public val colors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val invalidRing: Color,
    public val gap: Dp,
    public val separatorIcon: IconGraphic,
    public val separatorSize: Dp,
    public val caret: InputOtpCaret,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [InputOtpStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        slotSize: Dp = this.slotSize,
        radius: Dp = this.radius,
        textStyle: TextStyle = this.textStyle,
        colors: StateColors = this.colors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        invalidRing: Color = this.invalidRing,
        gap: Dp = this.gap,
        separatorIcon: IconGraphic = this.separatorIcon,
        separatorSize: Dp = this.separatorSize,
        caret: InputOtpCaret = this.caret,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): InputOtpStyle = InputOtpStyle(
        slotSize = slotSize,
        radius = radius,
        textStyle = textStyle,
        colors = colors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        invalidRing = invalidRing,
        gap = gap,
        separatorIcon = separatorIcon,
        separatorSize = separatorSize,
        caret = caret,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is InputOtpStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "InputOtpStyle(" +
        "slotSize=$slotSize, " +
        "radius=$radius, " +
        "textStyle=$textStyle, " +
        "colors=$colors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "invalidRing=$invalidRing, " +
        "gap=$gap, " +
        "separatorIcon=$separatorIcon, " +
        "separatorSize=$separatorSize, " +
        "caret=$caret, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        slotSize,
        radius,
        textStyle,
        colors,
        borderWidth,
        ringWidth,
        invalidRing,
        gap,
        separatorIcon,
        separatorSize,
        caret,
        disabledAlpha,
        colorAnimation,
    )
}
