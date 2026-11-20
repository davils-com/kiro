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

package com.davils.kiro.ui.core.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * The colors of one status, such as an error.
 *
 * @property foreground the color of text and icons that show the status.
 * @property surface the color of a surface that shows the status.
 * @property border the color of the border of such a surface.
 * @since 1.0.0
 */
@Immutable
public class StatusColor(
    public val foreground: Color,
    public val surface: Color,
    public val border: Color,
) {
    /**
     * Returns a copy of this [StatusColor] with the given values replaced and every other value kept.
     *
     * @param foreground the new value of [foreground].
     * @param surface the new value of [surface].
     * @param border the new value of [border].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        foreground: Color = this.foreground,
        surface: Color = this.surface,
        border: Color = this.border,
    ): StatusColor = StatusColor(
        foreground = foreground,
        surface = surface,
        border = border,
    )

    override fun equals(other: Any?): Boolean = other is StatusColor && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "StatusColor(" +
        "foreground=$foreground, " +
        "surface=$surface, " +
        "border=$border)"

    private fun values(): List<Any?> = listOf(
        foreground,
        surface,
        border,
    )
}
