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

package com.davils.kiro.ui.components.toast

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
public class ToastActionStyle(
    public val height: Dp,
    public val horizontalPadding: Dp,
    public val shape: Shape,
    public val textStyle: TextStyle,
    public val container: Color,
    public val content: Color,
) {
    /**
     * Returns a copy of this [ToastActionStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        height: Dp = this.height,
        horizontalPadding: Dp = this.horizontalPadding,
        shape: Shape = this.shape,
        textStyle: TextStyle = this.textStyle,
        container: Color = this.container,
        content: Color = this.content,
    ): ToastActionStyle = ToastActionStyle(
        height = height,
        horizontalPadding = horizontalPadding,
        shape = shape,
        textStyle = textStyle,
        container = container,
        content = content,
    )

    override fun equals(other: Any?): Boolean = other is ToastActionStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ToastActionStyle(" +
        "height=$height, " +
        "horizontalPadding=$horizontalPadding, " +
        "shape=$shape, " +
        "textStyle=$textStyle, " +
        "container=$container, " +
        "content=$content)"

    private fun values(): List<Any?> = listOf(
        height,
        horizontalPadding,
        shape,
        textStyle,
        container,
        content,
    )
}
