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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment

/**
 * A column of a [DataTable]: its header, how its cells are aligned and drawn, and whether it can be sorted, filtered
 * and hidden.
 *
 * @param T the type of a row.
 * @property header the text of the header.
 * @property alignment the horizontal alignment of the header and the cells.
 * @property isHideable whether [DataTableState.toggleColumn] can hide the column.
 * @property comparator the order of the rows when the column sorts them, or `null` for a column that cannot sort.
 * @property filterText returns the text of a row that the filter of the table is matched against, or `null` for a
 * column the filter ignores.
 * @property cell draws the cell of a row.
 * @since 1.0.0
 */
@Immutable
public class DataTableColumn<T>(
    public val header: String,
    public val alignment: Alignment.Horizontal = Alignment.Start,
    public val isHideable: Boolean = true,
    public val comparator: Comparator<T>? = null,
    public val filterText: ((T) -> String)? = null,
    public val cell: @Composable (T) -> Unit,
) {
    /**
     * Returns a copy of this [DataTableColumn] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        header: String = this.header,
        alignment: Alignment.Horizontal = this.alignment,
        isHideable: Boolean = this.isHideable,
        comparator: Comparator<T>? = this.comparator,
        filterText: ((T) -> String)? = this.filterText,
        cell: @Composable (T) -> Unit = this.cell,
    ): DataTableColumn<T> = DataTableColumn(
        header = header,
        alignment = alignment,
        isHideable = isHideable,
        comparator = comparator,
        filterText = filterText,
        cell = cell,
    )

    override fun equals(other: Any?): Boolean = other is DataTableColumn<*> && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "DataTableColumn(" +
        "header=$header, " +
        "alignment=$alignment, " +
        "isHideable=$isHideable, " +
        "comparator=${comparator ?: "null"}, " +
        "filterText=${filterText ?: "null"}, " +
        "cell=$cell)"

    private fun values(): List<Any?> = listOf(
        header,
        alignment,
        isHideable,
        comparator,
        filterText,
        cell,
    )
}
