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

/**
 * The style of a [Switch]: the size of its track, its thumb, its colors and its animations.
 *
 * @property width the width of the track.
 * @property height the height of the track.
 * @property thumb the size, the colors and the border of the thumb.
 * @property colors the colors of the track, its border and its focus ring in each interaction state while the switch
 * is off.
 * @property checkedColors the colors of the track, its border and its focus ring in each interaction state while the
 * switch is on.
 * @property borderWidth the width of the border of the track, which also insets the thumb.
 * @property ringWidth the width of the focus ring.
 * @property disabledAlpha the opacity of the whole switch while it is disabled.
 * @property colorAnimation the animation of the colors of the track and the thumb.
 * @property thumbAnimation the animation of the thumb as it slides between its ends.
 * @since 1.0.0
 */
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
     * @param width the new value of [width].
     * @param height the new value of [height].
     * @param thumb the new value of [thumb].
     * @param colors the new value of [colors].
     * @param checkedColors the new value of [checkedColors].
     * @param borderWidth the new value of [borderWidth].
     * @param ringWidth the new value of [ringWidth].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @param colorAnimation the new value of [colorAnimation].
     * @param thumbAnimation the new value of [thumbAnimation].
     * @return the copy.
     * @since 1.0.0
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
