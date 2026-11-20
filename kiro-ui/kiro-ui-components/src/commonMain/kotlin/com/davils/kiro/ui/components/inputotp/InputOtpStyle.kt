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

/**
 * How an [InputOtp] looks: the size, shape, colors and borders of its slots, its text style, its groups, separators
 * and caret, its disabled alpha and the animation of its colors.
 *
 * @property slotSize the width and height of a slot.
 * @property radius the radius of the outer corners of a group of slots.
 * @property textStyle the text style of the characters.
 * @property colors the colors of a slot by state: its container, its border and its focus ring.
 * @property borderWidth the width of the border of a slot.
 * @property ringWidth the width of the focus ring of the active slot and of the invalid ring of a group.
 * @property invalidRing the color of the ring around each group while the value is invalid.
 * @property gap the gap between groups and separators.
 * @property separatorIcon the icon of a separator.
 * @property separatorSize the width and height of the icon of a separator.
 * @property caret the caret in the active slot.
 * @property disabledAlpha the alpha of the whole field while it is disabled.
 * @property colorAnimation the animation of the border and ring colors when the state of a slot changes.
 * @since 1.0.0
 */
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
     * @param slotSize the new value of [slotSize].
     * @param radius the new value of [radius].
     * @param textStyle the new value of [textStyle].
     * @param colors the new value of [colors].
     * @param borderWidth the new value of [borderWidth].
     * @param ringWidth the new value of [ringWidth].
     * @param invalidRing the new value of [invalidRing].
     * @param gap the new value of [gap].
     * @param separatorIcon the new value of [separatorIcon].
     * @param separatorSize the new value of [separatorSize].
     * @param caret the new value of [caret].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @param colorAnimation the new value of [colorAnimation].
     * @return the copy.
     * @since 1.0.0
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
