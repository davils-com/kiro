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

/**
 * How a [Slider] and a [RangeSlider] look: their track, the filled range on it, their thumbs, their focus ring, their
 * minimum length, their disabled alpha and the animation of the ring.
 *
 * @property trackThickness the thickness of the track.
 * @property trackColor the color of the track.
 * @property rangeColor the color of the part of the track up to the thumb, or between the thumbs of a range.
 * @property thumbSize the width and height of a thumb.
 * @property thumbColor the color of a thumb.
 * @property thumbBorder the border of a thumb.
 * @property ring the ring around a thumb while it is hovered, focused by the keyboard or dragged.
 * @property minLength the length of the slider when its parent does not limit it.
 * @property disabledAlpha the alpha of the whole slider while it is disabled.
 * @property colorAnimation the animation of the color of the ring when it appears or disappears.
 * @since 1.0.0
 */
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
     * @param trackThickness the new value of [trackThickness].
     * @param trackColor the new value of [trackColor].
     * @param rangeColor the new value of [rangeColor].
     * @param thumbSize the new value of [thumbSize].
     * @param thumbColor the new value of [thumbColor].
     * @param thumbBorder the new value of [thumbBorder].
     * @param ring the new value of [ring].
     * @param minLength the new value of [minLength].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @param colorAnimation the new value of [colorAnimation].
     * @return the copy.
     * @since 1.0.0
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
