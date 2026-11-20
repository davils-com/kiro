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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.checkbox.Checkbox
import com.davils.kiro.ui.components.table.Table
import com.davils.kiro.ui.components.table.TableRowScope
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.IconGraphic

/**
 * Shows a page of rows in a [com.davils.kiro.ui.components.table.Table], filtered, sorted and paged by its
 * [DataTableState].
 *
 * A column with a comparator has a header button that cycles through ascending, descending and no sorting. The
 * hidden columns of the state are left out. Paging, filtering and hiding columns are controlled through [state],
 * for example by a search field and buttons around the table.
 *
 * @param T the type of a row.
 * @param data every row, before filtering, sorting and paging.
 * @param columns the columns of the table, in order.
 * @param key returns the stable key of a row, which the selection of [state] holds.
 * @param modifier the modifier of the table.
 * @param state the sorting, filter, page, hidden columns and selection of the table.
 * @param isSelectable whether the table shows a column of checkboxes to select rows, with one in the header that
 * selects or clears every row of the page.
 * @param emptyText the text shown when no row is left on the page.
 * @param style the style of the data table.
 * @since 1.0.0
 */
@Composable
public fun <T> DataTable(
    data: List<T>,
    columns: List<DataTableColumn<T>>,
    key: (T) -> Any,
    modifier: Modifier = Modifier,
    state: DataTableState<T> = rememberDataTableState(),
    isSelectable: Boolean = false,
    emptyText: String = Messages.DataTable.EMPTY,
    style: DataTableStyle = dataTableStyle(),
) {
    val view = state.view(data = data, columns = columns)
    val keys = view.rows.map(key)
    val span = view.columns.size + selectionSpanOf(isSelectable)
    Table(modifier = modifier, style = style.table) {
        header {
            if (isSelectable) selectAll(keys = keys, state = state)
            view.columns.forEach { column -> head(column = column, state = state, style = style) }
        }
        view.rows.forEachIndexed { index, row ->
            val rowKey = keys[index]
            row(isSelected = rowKey in state.selected) {
                if (isSelectable) {
                    cell { Checkbox(checked = rowKey in state.selected, onCheckedChange = { state.toggleRow(rowKey) }) }
                }
                view.columns.forEach { column -> cell(alignment = column.alignment) { column.cell(row) } }
            }
        }
        if (view.rows.isEmpty()) {
            row {
                cell(span = maxOf(a = span, b = 1), alignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(style.emptyHeight),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(emptyText)
                    }
                }
            }
        }
    }
}

private fun <T> TableRowScope.selectAll(keys: List<Any>, state: DataTableState<T>) {
    val count = keys.count { key -> key in state.selected }
    cell {
        Checkbox(
            checked = keys.isNotEmpty() && count == keys.size,
            onCheckedChange = { state.toggleRows(keys) },
            isIndeterminate = count in 1 until keys.size,
        )
    }
}

private fun <T> TableRowScope.head(column: DataTableColumn<T>, state: DataTableState<T>, style: DataTableStyle) {
    val comparator = column.comparator
    if (comparator == null) {
        cell(alignment = column.alignment) { Text(column.header) }
        return
    }
    cell(alignment = column.alignment) {
        Button(onClick = { state.sortBy(column) }, variant = ButtonVariant.Ghost, size = ButtonSize.Sm) {
            Text(column.header)
            Icon(
                icon = sortIconOf(column = column, sort = state.sort, style = style),
                placement = IconPlacement.Trailing,
            )
        }
    }
}

private fun <T> sortIconOf(column: DataTableColumn<T>, sort: DataTableSort<T>?, style: DataTableStyle): IconGraphic {
    val active = sort?.takeIf { current -> current.column == column }
    return active?.let { current -> style.descendingIcon.takeIf { current.isDescending } ?: style.ascendingIcon }
        ?: style.unsortedIcon
}

private fun selectionSpanOf(isSelectable: Boolean): Int {
    if (isSelectable) return 1
    return 0
}
