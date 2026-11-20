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

package com.davils.kiro.ui.components.tabs

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * The line that marks the selected tab of [Tabs], drawn along the bottom edge of a tab in a horizontal list and along
 * its end edge in a vertical list.
 *
 * @property color the color of the line.
 * @property thickness the thickness of the line.
 * @property offset how far the line is moved down, below the bottom edge of a tab in a horizontal list.
 * @property verticalOffset how far the line is moved outward, past the end edge of a tab in a vertical list.
 * @since 1.0.0
 */
@Immutable
public class TabsIndicator(
    public val color: Color,
    public val thickness: Dp,
    public val offset: Dp,
    public val verticalOffset: Dp,
) {
    /**
     * Returns a copy of this [TabsIndicator] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        color: Color = this.color,
        thickness: Dp = this.thickness,
        offset: Dp = this.offset,
        verticalOffset: Dp = this.verticalOffset,
    ): TabsIndicator = TabsIndicator(
        color = color,
        thickness = thickness,
        offset = offset,
        verticalOffset = verticalOffset,
    )

    override fun equals(other: Any?): Boolean = other is TabsIndicator && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TabsIndicator(" +
        "color=$color, " +
        "thickness=$thickness, " +
        "offset=$offset, " +
        "verticalOffset=$verticalOffset)"

    private fun values(): List<Any?> = listOf(
        color,
        thickness,
        offset,
        verticalOffset,
    )
}
