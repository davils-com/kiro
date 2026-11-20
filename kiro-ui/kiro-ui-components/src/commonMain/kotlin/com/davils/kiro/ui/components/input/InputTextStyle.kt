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

package com.davils.kiro.ui.components.input

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Immutable
public class InputTextStyle(
    public val textStyle: TextStyle,
    public val placeholderColor: Color,
    public val cursorColor: Color,
    public val selectionColors: TextSelectionColors,
) {
    /**
     * Returns a copy of this [InputTextStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        textStyle: TextStyle = this.textStyle,
        placeholderColor: Color = this.placeholderColor,
        cursorColor: Color = this.cursorColor,
        selectionColors: TextSelectionColors = this.selectionColors,
    ): InputTextStyle = InputTextStyle(
        textStyle = textStyle,
        placeholderColor = placeholderColor,
        cursorColor = cursorColor,
        selectionColors = selectionColors,
    )

    override fun equals(other: Any?): Boolean = other is InputTextStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "InputTextStyle(" +
        "textStyle=$textStyle, " +
        "placeholderColor=$placeholderColor, " +
        "cursorColor=$cursorColor, " +
        "selectionColors=$selectionColors)"

    private fun values(): List<Any?> = listOf(
        textStyle,
        placeholderColor,
        cursorColor,
        selectionColors,
    )
}
