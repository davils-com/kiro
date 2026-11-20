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

package com.davils.kiro.ui.components.chart

import androidx.compose.runtime.Immutable

@Immutable
public class ChartPoint(
    public val label: String,
    public val values: Map<ChartKey, Double>,
) {
    /**
     * Returns a copy of this [ChartPoint] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(label: String = this.label, values: Map<ChartKey, Double> = this.values): ChartPoint = ChartPoint(
        label = label,
        values = values,
    )

    override fun equals(other: Any?): Boolean = other is ChartPoint && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ChartPoint(" +
        "label=$label, " +
        "values=$values)"

    private fun values(): List<Any?> = listOf(
        label,
        values,
    )
}

internal fun ChartPoint.valueOf(key: ChartKey): Double = values[key] ?: 0.0
