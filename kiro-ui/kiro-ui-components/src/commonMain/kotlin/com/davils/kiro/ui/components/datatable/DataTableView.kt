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

@Immutable
public class DataTableView<T>(
    public val rows: List<T>,
    public val columns: List<DataTableColumn<T>>,
    public val rowCount: Int,
    public val page: Int,
    public val pageCount: Int,
) {
    public val hasPreviousPage: Boolean get() = page > 0

    public val hasNextPage: Boolean get() = page < pageCount - 1

    /**
     * Returns a copy of this [DataTableView] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        rows: List<T> = this.rows,
        columns: List<DataTableColumn<T>> = this.columns,
        rowCount: Int = this.rowCount,
        page: Int = this.page,
        pageCount: Int = this.pageCount,
    ): DataTableView<T> = DataTableView(
        rows = rows,
        columns = columns,
        rowCount = rowCount,
        page = page,
        pageCount = pageCount,
    )

    override fun equals(other: Any?): Boolean = other is DataTableView<*> && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "DataTableView(" +
        "rows=$rows, " +
        "columns=$columns, " +
        "rowCount=$rowCount, " +
        "page=$page, " +
        "pageCount=$pageCount)"

    private fun values(): List<Any?> = listOf(
        rows,
        columns,
        rowCount,
        page,
        pageCount,
    )
}
