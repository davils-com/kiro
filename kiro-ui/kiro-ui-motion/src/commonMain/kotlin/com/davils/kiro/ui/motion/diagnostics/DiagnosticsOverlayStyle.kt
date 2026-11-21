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

package com.davils.kiro.ui.motion.diagnostics

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * How a [DiagnosticsOverlay] looks.
 *
 * @property container the color behind the text.
 * @property content the color of the text.
 * @property textStyle the style of the text.
 * @property shape the shape of the container.
 * @property horizontalPadding the space left and right of the text.
 * @property verticalPadding the space above and below the text.
 * @since 1.0.0
 */
@Immutable
public class DiagnosticsOverlayStyle(
    public val container: Color,
    public val content: Color,
    public val textStyle: TextStyle,
    public val shape: Shape,
    public val horizontalPadding: Dp,
    public val verticalPadding: Dp,
) {
    /**
     * Returns a copy of this [DiagnosticsOverlayStyle] with the given values replaced and every other value kept.
     *
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param textStyle the new value of [textStyle].
     * @param shape the new value of [shape].
     * @param horizontalPadding the new value of [horizontalPadding].
     * @param verticalPadding the new value of [verticalPadding].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        container: Color = this.container,
        content: Color = this.content,
        textStyle: TextStyle = this.textStyle,
        shape: Shape = this.shape,
        horizontalPadding: Dp = this.horizontalPadding,
        verticalPadding: Dp = this.verticalPadding,
    ): DiagnosticsOverlayStyle = DiagnosticsOverlayStyle(
        container = container,
        content = content,
        textStyle = textStyle,
        shape = shape,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
    )

    override fun equals(other: Any?): Boolean = other is DiagnosticsOverlayStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "DiagnosticsOverlayStyle(" +
        "container=$container, " +
        "content=$content, " +
        "textStyle=$textStyle, " +
        "shape=$shape, " +
        "horizontalPadding=$horizontalPadding, " +
        "verticalPadding=$verticalPadding)"

    private fun values(): List<Any?> = listOf(
        container,
        content,
        textStyle,
        shape,
        horizontalPadding,
        verticalPadding,
    )
}
