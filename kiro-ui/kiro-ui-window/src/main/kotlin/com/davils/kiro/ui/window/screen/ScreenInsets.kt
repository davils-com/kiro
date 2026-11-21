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

package com.davils.kiro.ui.window.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

/**
 * The parts of the edges of a screen that the operating system reserves, such as a task bar, a dock or a menu bar.
 *
 * @property left the width reserved at the left edge.
 * @property top the height reserved at the top edge.
 * @property right the width reserved at the right edge.
 * @property bottom the height reserved at the bottom edge.
 * @since 1.0.0
 */
@Immutable
public class ScreenInsets(
    public val left: Dp,
    public val top: Dp,
    public val right: Dp,
    public val bottom: Dp,
) {
    /**
     * Returns a copy of this [ScreenInsets] with the given values replaced and every other value kept.
     *
     * @param left the new value of [left].
     * @param top the new value of [top].
     * @param right the new value of [right].
     * @param bottom the new value of [bottom].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        left: Dp = this.left,
        top: Dp = this.top,
        right: Dp = this.right,
        bottom: Dp = this.bottom,
    ): ScreenInsets = ScreenInsets(
        left = left,
        top = top,
        right = right,
        bottom = bottom,
    )

    override fun equals(other: Any?): Boolean = other is ScreenInsets && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ScreenInsets(" +
        "left=$left, " +
        "top=$top, " +
        "right=$right, " +
        "bottom=$bottom)"

    private fun values(): List<Any?> = listOf(
        left,
        top,
        right,
        bottom,
    )
}
