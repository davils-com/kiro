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

package com.davils.kiro.ui.window.frame

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

/**
 * The widths at the physical left and right of a title bar that the operating system draws into, such as the
 * window buttons of Windows or the traffic lights of macOS when the system draws the frame of a window.
 *
 * @property left the width the system uses at the left of the title bar.
 * @property right the width the system uses at the right of the title bar.
 * @since 1.0.0
 */
@Immutable
public class CaptionInsets(
    public val left: Dp,
    public val right: Dp,
) {
    /**
     * Returns a copy of this [CaptionInsets] with the given values replaced and every other value kept.
     *
     * @param left the new value of [left].
     * @param right the new value of [right].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(left: Dp = this.left, right: Dp = this.right): CaptionInsets = CaptionInsets(
        left = left,
        right = right,
    )

    override fun equals(other: Any?): Boolean = other is CaptionInsets && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CaptionInsets(" +
        "left=$left, " +
        "right=$right)"

    private fun values(): List<Any?> = listOf(
        left,
        right,
    )

    /**
     * Holds the insets of a title bar that the system does not draw into.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The insets of a title bar that Kiro draws on its own.
         *
         * @since 1.0.0
         */
        public val None: CaptionInsets = CaptionInsets(left = Dp(0f), right = Dp(0f))
    }
}
