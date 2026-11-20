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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

private const val DEFAULT_PAGE_SIZE: Int = 10

/**
 * The state of a [DataTable]: its sorting, its filter, the page shown and its size, the hidden columns and the keys
 * of the selected rows.
 *
 * @param T the type of a row.
 * @param pageSize the number of rows on a page at first; it must be above zero.
 * @since 1.0.0
 */
@Stable
public class DataTableState<T>(pageSize: Int = DEFAULT_PAGE_SIZE) {
    init {
        require(pageSize > 0) { "A data table needs a page size above zero, not $pageSize." }
    }

    /**
     * The column that sorts the rows and its direction, or `null` while the rows keep their order.
     *
     * @since 1.0.0
     */
    public var sort: DataTableSort<T>? by mutableStateOf(null)
        private set

    /**
     * The text the rows are filtered by; a row is shown when one of its filter texts contains it, ignoring case.
     *
     * @since 1.0.0
     */
    public var filter: String by mutableStateOf("")
        private set

    /**
     * The index of the page shown, from zero; the view clamps it to the last page.
     *
     * @since 1.0.0
     */
    public var page: Int by mutableIntStateOf(0)
        private set

    /**
     * The number of rows on a page.
     *
     * @since 1.0.0
     */
    public var pageSize: Int by mutableIntStateOf(pageSize)
        private set

    /**
     * The columns that are hidden.
     *
     * @since 1.0.0
     */
    public var hidden: Set<DataTableColumn<T>> by mutableStateOf(emptySet())
        private set

    /**
     * The keys of the selected rows.
     *
     * @since 1.0.0
     */
    public var selected: Set<Any> by mutableStateOf(emptySet())
        private set

    /**
     * Sorts by a column: a column that does not sort yet sorts ascending, then descending, then not at all.
     *
     * @param column the column whose header was clicked.
     * @since 1.0.0
     */
    public fun sortBy(column: DataTableColumn<T>) {
        sort = nextSort(current = sort, column = column)
    }

    /**
     * Filters the rows by a text and returns to the first page.
     *
     * @param text the new filter.
     * @since 1.0.0
     */
    public fun filterBy(text: String) {
        filter = text
        page = 0
    }

    /**
     * Shows a page; a negative index shows the first page and an index past the last page shows the last one.
     *
     * @param page the index of the page, from zero.
     * @since 1.0.0
     */
    public fun showPage(page: Int) {
        this.page = maxOf(a = page, b = 0)
    }

    /**
     * Changes the number of rows on a page and returns to the first page.
     *
     * @param pageSize the new number of rows on a page.
     * @throws IllegalArgumentException if [pageSize] is not above zero.
     * @since 1.0.0
     */
    public fun resize(pageSize: Int) {
        require(pageSize > 0) { "A data table needs a page size above zero, not $pageSize." }
        this.pageSize = pageSize
        page = 0
    }

    /**
     * Hides a shown column or shows a hidden one; a column that is not hideable is left as it is.
     *
     * @param column the column to hide or show.
     * @since 1.0.0
     */
    public fun toggleColumn(column: DataTableColumn<T>) {
        if (!column.isHideable) return
        hidden = toggledIn(set = hidden, value = column)
    }

    /**
     * Selects a row or clears its selection.
     *
     * @param key the key of the row.
     * @since 1.0.0
     */
    public fun toggleRow(key: Any) {
        selected = toggledIn(set = selected, value = key)
    }

    /**
     * Clears the selection of the rows if all of them are selected, and selects all of them otherwise.
     *
     * @param keys the keys of the rows.
     * @since 1.0.0
     */
    public fun toggleRows(keys: Collection<Any>) {
        val isAll = keys.isNotEmpty() && selected.containsAll(keys)
        selected = (selected - keys.toSet()).takeIf { isAll } ?: selected + keys
    }
}

/**
 * Creates and remembers a [DataTableState] without sorting, filter, hidden columns or selection, on the first page.
 *
 * @param T the type of a row.
 * @param pageSize the number of rows on a page at first.
 * @return the remembered state.
 * @throws IllegalArgumentException if [pageSize] is not above zero.
 * @since 1.0.0
 */
@Composable
public fun <T> rememberDataTableState(pageSize: Int = DEFAULT_PAGE_SIZE): DataTableState<T> =
    remember { DataTableState(pageSize) }

private fun <T> nextSort(current: DataTableSort<T>?, column: DataTableColumn<T>): DataTableSort<T>? {
    if (current?.column != column) return DataTableSort(column = column, isDescending = false)
    return DataTableSort(column = column, isDescending = true).takeUnless { current.isDescending }
}

private fun <V> toggledIn(set: Set<V>, value: V): Set<V> {
    if (value in set) return set - value
    return set + value
}
