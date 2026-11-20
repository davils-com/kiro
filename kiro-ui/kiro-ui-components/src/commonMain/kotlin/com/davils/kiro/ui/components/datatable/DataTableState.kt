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

@Stable
public class DataTableState<T>(pageSize: Int = DEFAULT_PAGE_SIZE) {
    init {
        require(pageSize > 0) { "A data table needs a page size above zero, not $pageSize." }
    }

    public var sort: DataTableSort<T>? by mutableStateOf(null)
        private set

    public var filter: String by mutableStateOf("")
        private set

    public var page: Int by mutableIntStateOf(0)
        private set

    public var pageSize: Int by mutableIntStateOf(pageSize)
        private set

    public var hidden: Set<DataTableColumn<T>> by mutableStateOf(emptySet())
        private set

    public var selected: Set<Any> by mutableStateOf(emptySet())
        private set

    public fun sortBy(column: DataTableColumn<T>) {
        sort = nextSort(current = sort, column = column)
    }

    public fun filterBy(text: String) {
        filter = text
        page = 0
    }

    public fun showPage(page: Int) {
        this.page = maxOf(page, 0)
    }

    public fun resize(pageSize: Int) {
        require(pageSize > 0) { "A data table needs a page size above zero, not $pageSize." }
        this.pageSize = pageSize
        page = 0
    }

    public fun toggleColumn(column: DataTableColumn<T>) {
        if (!column.isHideable) return
        hidden = toggledIn(set = hidden, value = column)
    }

    public fun toggleRow(key: Any) {
        selected = toggledIn(set = selected, value = key)
    }

    public fun toggleRows(keys: Collection<Any>) {
        val isAll = keys.isNotEmpty() && selected.containsAll(keys)
        selected = (selected - keys.toSet()).takeIf { isAll } ?: selected + keys
    }
}

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
