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

package com.davils.kiro.ui.core.spacing

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The spacing of a theme. Every distance and size of a component is a number of steps of [unit], like the spacing scale
 * of Tailwind CSS, so changing the unit scales every component at once.
 *
 * @property unit the length of one step.
 * @since 0.1.0
 */
@Immutable
public class Spacing(
    public val unit: Dp = 4.dp,
) {
    /**
     * Returns a copy of this [Spacing] with the given values replaced and every other value kept.
     *
     * @param unit the new value of [unit].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(unit: Dp = this.unit): Spacing = Spacing(
        unit = unit,
    )

    override fun equals(other: Any?): Boolean = other is Spacing && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Spacing(" +
        "unit=$unit)"

    private fun values(): List<Any?> = listOf(
        unit,
    )
}
