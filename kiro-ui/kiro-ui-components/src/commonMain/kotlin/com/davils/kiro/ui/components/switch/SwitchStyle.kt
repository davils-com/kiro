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

package com.davils.kiro.ui.components.switch

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors

@Immutable
public class SwitchStyle(
    public val width: Dp,
    public val height: Dp,
    public val thumb: SwitchThumb,
    public val colors: StateColors,
    public val checkedColors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
    public val thumbAnimation: AnimationSpec<Dp>,
) {
    /**
     * Returns a copy of this [SwitchStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        width: Dp = this.width,
        height: Dp = this.height,
        thumb: SwitchThumb = this.thumb,
        colors: StateColors = this.colors,
        checkedColors: StateColors = this.checkedColors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
        thumbAnimation: AnimationSpec<Dp> = this.thumbAnimation,
    ): SwitchStyle = SwitchStyle(
        width = width,
        height = height,
        thumb = thumb,
        colors = colors,
        checkedColors = checkedColors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
        thumbAnimation = thumbAnimation,
    )

    override fun equals(other: Any?): Boolean = other is SwitchStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SwitchStyle(" +
        "width=$width, " +
        "height=$height, " +
        "thumb=$thumb, " +
        "colors=$colors, " +
        "checkedColors=$checkedColors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation, " +
        "thumbAnimation=$thumbAnimation)"

    private fun values(): List<Any?> = listOf(
        width,
        height,
        thumb,
        colors,
        checkedColors,
        borderWidth,
        ringWidth,
        disabledAlpha,
        colorAnimation,
        thumbAnimation,
    )
}
