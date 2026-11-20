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

/**
 * Returns what a [DataTable] with this state shows of its data: the rows filtered by [DataTableState.filter], sorted
 * by [DataTableState.sort] and cut to the page shown, and the columns that are not hidden.
 *
 * @param T the type of a row.
 * @param data every row, before filtering, sorting and paging.
 * @param columns the columns of the table, in order.
 * @return the view of the data.
 * @since 1.0.0
 */
public fun <T> DataTableState<T>.view(data: List<T>, columns: List<DataTableColumn<T>>): DataTableView<T> {
    val filtered = filtered(data = data, columns = columns, text = filter)
    val sorted = sorted(rows = filtered, sort = sort)
    val pageCount = maxOf(a = (sorted.size + pageSize - 1) / pageSize, b = 1)
    val current = page.coerceIn(minimumValue = 0, maximumValue = pageCount - 1)
    val start = current * pageSize
    return DataTableView(
        rows = sorted.subList(fromIndex = start, toIndex = minOf(a = start + pageSize, b = sorted.size)),
        columns = columns.filterNot { column -> column in hidden },
        rowCount = sorted.size,
        page = current,
        pageCount = pageCount,
    )
}

private fun <T> filtered(data: List<T>, columns: List<DataTableColumn<T>>, text: String): List<T> {
    val texts = columns.mapNotNull { column -> column.filterText }
    val query = text.trim()
    if (query.isEmpty() || texts.isEmpty()) return data
    return data.filter { row -> texts.any { textOf -> textOf(row).contains(query, ignoreCase = true) } }
}

private fun <T> sorted(rows: List<T>, sort: DataTableSort<T>?): List<T> {
    val comparator = sort?.let { active -> comparatorOf(active) } ?: return rows
    return rows.sortedWith(comparator)
}

private fun <T> comparatorOf(sort: DataTableSort<T>): Comparator<T>? {
    val comparator = sort.column.comparator ?: return null
    return comparator.reversed().takeIf { sort.isDescending } ?: comparator
}
