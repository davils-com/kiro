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

package com.davils.kiro.ui.core.typography

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.TextUnit

/**
 * A step of the type scale: a font size with its line height.
 *
 * @property fontSize the font size.
 * @property lineHeight the line height.
 * @since 1.0.0
 */
@Immutable
public class TextSize(
    public val fontSize: TextUnit,
    public val lineHeight: TextUnit,
) {
    /**
     * Returns a copy of this [TextSize] with the given values replaced and every other value kept.
     *
     * @param fontSize the new value of [fontSize].
     * @param lineHeight the new value of [lineHeight].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(fontSize: TextUnit = this.fontSize, lineHeight: TextUnit = this.lineHeight): TextSize = TextSize(
        fontSize = fontSize,
        lineHeight = lineHeight,
    )

    override fun equals(other: Any?): Boolean = other is TextSize && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TextSize(" +
        "fontSize=$fontSize, " +
        "lineHeight=$lineHeight)"

    private fun values(): List<Any?> = listOf(
        fontSize,
        lineHeight,
    )
}
