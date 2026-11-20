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

package com.davils.kiro.ui.components.separator

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * The style of a [Separator].
 *
 * @property thickness the thickness of the line.
 * @property color the color of the line.
 * @since 1.0.0
 */
@Immutable
public class SeparatorStyle(
    public val thickness: Dp,
    public val color: Color,
) {
    /**
     * Returns a copy of this [SeparatorStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(thickness: Dp = this.thickness, color: Color = this.color): SeparatorStyle = SeparatorStyle(
        thickness = thickness,
        color = color,
    )

    override fun equals(other: Any?): Boolean = other is SeparatorStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SeparatorStyle(" +
        "thickness=$thickness, " +
        "color=$color)"

    private fun values(): List<Any?> = listOf(
        thickness,
        color,
    )
}
