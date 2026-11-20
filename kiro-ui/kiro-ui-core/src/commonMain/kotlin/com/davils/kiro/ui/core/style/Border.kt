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

package com.davils.kiro.ui.core.style

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * A border of a component.
 *
 * @property width the width of the border.
 * @property color the color of the border.
 * @since 1.0.0
 */
@Immutable
public class Border(
    public val width: Dp,
    public val color: Color,
) {
    /**
     * Returns a copy of this [Border] with the given values replaced and every other value kept.
     *
     * @param width the new value of [width].
     * @param color the new value of [color].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(width: Dp = this.width, color: Color = this.color): Border = Border(
        width = width,
        color = color,
    )

    override fun equals(other: Any?): Boolean = other is Border && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Border(" +
        "width=$width, " +
        "color=$color)"

    private fun values(): List<Any?> = listOf(
        width,
        color,
    )
}
