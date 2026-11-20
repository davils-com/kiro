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

package com.davils.kiro.ui.components.datatable

import androidx.compose.runtime.Immutable

/**
 * The column that sorts the rows of a [DataTable] and the direction of the order.
 *
 * @param T the type of a row.
 * @property column the column whose comparator orders the rows.
 * @property isDescending whether the order of the comparator is reversed.
 * @since 1.0.0
 */
@Immutable
public class DataTableSort<T>(
    public val column: DataTableColumn<T>,
    public val isDescending: Boolean,
) {
    /**
     * Returns a copy of this [DataTableSort] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        column: DataTableColumn<T> = this.column,
        isDescending: Boolean = this.isDescending,
    ): DataTableSort<T> = DataTableSort(
        column = column,
        isDescending = isDescending,
    )

    override fun equals(other: Any?): Boolean = other is DataTableSort<*> && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "DataTableSort(" +
        "column=$column, " +
        "isDescending=$isDescending)"

    private fun values(): List<Any?> = listOf(
        column,
        isDescending,
    )
}
