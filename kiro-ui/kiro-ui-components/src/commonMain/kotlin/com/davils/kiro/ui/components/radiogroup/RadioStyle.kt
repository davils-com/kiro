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

package com.davils.kiro.ui.components.radiogroup

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors

/**
 * How the items of a radio group look: their size, their dot, their colors and their spacing.
 *
 * @property size the width and height of the circle of an item.
 * @property dotSize the width and height of the dot of the selected item.
 * @property groupGap the space between the items of a group.
 * @property colors the colors of an item that is not selected, in each interaction state.
 * @property checkedColors the colors of the selected item, in each interaction state; the dot takes the content color.
 * @property borderWidth the width of the border of the circle.
 * @property ringWidth the width of the focus ring around the circle.
 * @property disabledAlpha the alpha of a disabled item.
 * @property colorAnimation the animation of the colors when the selection or the interaction state changes.
 * @since 1.0.0
 */
@Immutable
public class RadioStyle(
    public val size: Dp,
    public val dotSize: Dp,
    public val groupGap: Dp,
    public val colors: StateColors,
    public val checkedColors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [RadioStyle] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param dotSize the new value of [dotSize].
     * @param groupGap the new value of [groupGap].
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
        size: Dp = this.size,
        dotSize: Dp = this.dotSize,
        groupGap: Dp = this.groupGap,
        colors: StateColors = this.colors,
        checkedColors: StateColors = this.checkedColors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): RadioStyle = RadioStyle(
        size = size,
        dotSize = dotSize,
        groupGap = groupGap,
        colors = colors,
        checkedColors = checkedColors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is RadioStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "RadioStyle(" +
        "size=$size, " +
        "dotSize=$dotSize, " +
        "groupGap=$groupGap, " +
        "colors=$colors, " +
        "checkedColors=$checkedColors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        size,
        dotSize,
        groupGap,
        colors,
        checkedColors,
        borderWidth,
        ringWidth,
        disabledAlpha,
        colorAnimation,
    )
}
