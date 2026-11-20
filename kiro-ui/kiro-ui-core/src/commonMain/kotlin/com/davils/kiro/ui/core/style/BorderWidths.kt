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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The widths of the borders of a theme.
 *
 * @property thin the width of ordinary borders and separators.
 * @property thick the width of emphasized borders, such as the one of a selected item.
 * @since 0.1.0
 */
@Immutable
public class BorderWidths(
    public val thin: Dp = 1.dp,
    public val thick: Dp = 2.dp,
) {
    /**
     * Returns a copy of this [BorderWidths] with the given values replaced and every other value kept.
     *
     * @param thin the new value of [thin].
     * @param thick the new value of [thick].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(thin: Dp = this.thin, thick: Dp = this.thick): BorderWidths = BorderWidths(
        thin = thin,
        thick = thick,
    )

    override fun equals(other: Any?): Boolean = other is BorderWidths && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BorderWidths(" +
        "thin=$thin, " +
        "thick=$thick)"

    private fun values(): List<Any?> = listOf(
        thin,
        thick,
    )
}
