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

package com.davils.kiro.ui.components.nativeselect

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.StateColors

@Immutable
public class NativeSelectStyle(
    public val height: Dp,
    public val shape: Shape,
    public val padding: PaddingValues,
    public val textStyle: TextStyle,
    public val colors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val icon: NativeSelectIcon,
    public val menu: NativeSelectMenuStyle,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [NativeSelectStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        height: Dp = this.height,
        shape: Shape = this.shape,
        padding: PaddingValues = this.padding,
        textStyle: TextStyle = this.textStyle,
        colors: StateColors = this.colors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        icon: NativeSelectIcon = this.icon,
        menu: NativeSelectMenuStyle = this.menu,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): NativeSelectStyle = NativeSelectStyle(
        height = height,
        shape = shape,
        padding = padding,
        textStyle = textStyle,
        colors = colors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        icon = icon,
        menu = menu,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is NativeSelectStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "NativeSelectStyle(" +
        "height=$height, " +
        "shape=$shape, " +
        "padding=$padding, " +
        "textStyle=$textStyle, " +
        "colors=$colors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "icon=$icon, " +
        "menu=$menu, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        height,
        shape,
        padding,
        textStyle,
        colors,
        borderWidth,
        ringWidth,
        icon,
        menu,
        disabledAlpha,
        colorAnimation,
    )
}
