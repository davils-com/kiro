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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.icons.IconGraphic

@Immutable
public class NativeSelectIcon(
    public val icon: IconGraphic,
    public val size: Dp,
    public val color: Color,
    public val endPadding: Dp,
) {
    /**
     * Returns a copy of this [NativeSelectIcon] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        icon: IconGraphic = this.icon,
        size: Dp = this.size,
        color: Color = this.color,
        endPadding: Dp = this.endPadding,
    ): NativeSelectIcon = NativeSelectIcon(
        icon = icon,
        size = size,
        color = color,
        endPadding = endPadding,
    )

    override fun equals(other: Any?): Boolean = other is NativeSelectIcon && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "NativeSelectIcon(" +
        "icon=$icon, " +
        "size=$size, " +
        "color=$color, " +
        "endPadding=$endPadding)"

    private fun values(): List<Any?> = listOf(
        icon,
        size,
        color,
        endPadding,
    )
}
