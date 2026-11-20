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

package com.davils.kiro.ui.components.slider

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

@Immutable
public class SliderStyle(
    public val trackThickness: Dp,
    public val trackColor: Color,
    public val rangeColor: Color,
    public val thumbSize: Dp,
    public val thumbColor: Color,
    public val thumbBorder: Border,
    public val ring: Border,
    public val minLength: Dp,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [SliderStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        trackThickness: Dp = this.trackThickness,
        trackColor: Color = this.trackColor,
        rangeColor: Color = this.rangeColor,
        thumbSize: Dp = this.thumbSize,
        thumbColor: Color = this.thumbColor,
        thumbBorder: Border = this.thumbBorder,
        ring: Border = this.ring,
        minLength: Dp = this.minLength,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): SliderStyle = SliderStyle(
        trackThickness = trackThickness,
        trackColor = trackColor,
        rangeColor = rangeColor,
        thumbSize = thumbSize,
        thumbColor = thumbColor,
        thumbBorder = thumbBorder,
        ring = ring,
        minLength = minLength,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is SliderStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SliderStyle(" +
        "trackThickness=$trackThickness, " +
        "trackColor=$trackColor, " +
        "rangeColor=$rangeColor, " +
        "thumbSize=$thumbSize, " +
        "thumbColor=$thumbColor, " +
        "thumbBorder=$thumbBorder, " +
        "ring=$ring, " +
        "minLength=$minLength, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        trackThickness,
        trackColor,
        rangeColor,
        thumbSize,
        thumbColor,
        thumbBorder,
        ring,
        minLength,
        disabledAlpha,
        colorAnimation,
    )
}
