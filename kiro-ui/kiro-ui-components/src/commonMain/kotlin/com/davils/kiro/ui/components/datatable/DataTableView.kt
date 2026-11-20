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
 * What a [DataTable] shows of its data: the rows of the current page after filtering and sorting, and the shown
 * columns.
 *
 * @param T the type of a row.
 * @property rows the rows of the page, filtered and sorted.
 * @property columns the columns that are not hidden, in order.
 * @property rowCount the number of rows left after filtering, on all pages.
 * @property page the index of the page shown, from zero.
 * @property pageCount the number of pages, at least one.
 * @since 1.0.0
 */
@Immutable
public class DataTableView<T>(
    public val rows: List<T>,
    public val columns: List<DataTableColumn<T>>,
    public val rowCount: Int,
    public val page: Int,
    public val pageCount: Int,
) {
    /**
     * Whether a page comes before the page shown.
     *
     * @since 1.0.0
     */
    public val hasPreviousPage: Boolean get() = page > 0

    /**
     * Whether a page comes after the page shown.
     *
     * @since 1.0.0
     */
    public val hasNextPage: Boolean get() = page < pageCount - 1

    /**
     * Returns a copy of this [DataTableView] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
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
