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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

/**
 * The thumb of a [Switch], the circle that slides along its track.
 *
 * @property size the width and height of the thumb.
 * @property color the color of the thumb while the switch is off.
 * @property checkedColor the color of the thumb while the switch is on.
 * @property border the border of the thumb.
 * @since 1.0.0
 */
@Immutable
public class SwitchThumb(
    public val size: Dp,
    public val color: Color,
    public val checkedColor: Color,
    public val border: Border,
) {
    /**
     * Returns a copy of this [SwitchThumb] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param color the new value of [color].
     * @param checkedColor the new value of [checkedColor].
     * @param border the new value of [border].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        size: Dp = this.size,
        color: Color = this.color,
        checkedColor: Color = this.checkedColor,
        border: Border = this.border,
    ): SwitchThumb = SwitchThumb(
        size = size,
        color = color,
        checkedColor = checkedColor,
        border = border,
    )

    override fun equals(other: Any?): Boolean = other is SwitchThumb && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SwitchThumb(" +
        "size=$size, " +
        "color=$color, " +
        "checkedColor=$checkedColor, " +
        "border=$border)"

    private fun values(): List<Any?> = listOf(
        size,
        color,
        checkedColor,
        border,
    )
}
