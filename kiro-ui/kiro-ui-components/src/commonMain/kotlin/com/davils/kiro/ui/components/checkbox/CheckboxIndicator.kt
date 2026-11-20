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

package com.davils.kiro.ui.components.checkbox

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.icons.IconGraphic

@Immutable
public class CheckboxIndicator(
    public val size: Dp,
    public val checkedIcon: IconGraphic,
    public val indeterminateIcon: IconGraphic,
) {
    /**
     * Returns a copy of this [CheckboxIndicator] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        size: Dp = this.size,
        checkedIcon: IconGraphic = this.checkedIcon,
        indeterminateIcon: IconGraphic = this.indeterminateIcon,
    ): CheckboxIndicator = CheckboxIndicator(
        size = size,
        checkedIcon = checkedIcon,
        indeterminateIcon = indeterminateIcon,
    )

    override fun equals(other: Any?): Boolean = other is CheckboxIndicator && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CheckboxIndicator(" +
        "size=$size, " +
        "checkedIcon=$checkedIcon, " +
        "indeterminateIcon=$indeterminateIcon)"

    private fun values(): List<Any?> = listOf(
        size,
        checkedIcon,
        indeterminateIcon,
    )
}
