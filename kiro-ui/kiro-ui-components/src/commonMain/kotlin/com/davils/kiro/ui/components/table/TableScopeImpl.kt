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

package com.davils.kiro.ui.components.table

internal class TableScopeImpl : TableScope {
    private val collected = mutableListOf<TableRowSpec>()
    private val captions = mutableListOf<String>()

    val rows: List<TableRowSpec> get() = bordered(collected.toList())

    val caption: String? get() = captions.lastOrNull()

    override fun header(content: TableRowScope.() -> Unit) {
        add(kind = TableRowKind.Head, isSelected = false, onClick = null, content = content)
    }

    override fun row(isSelected: Boolean, onClick: (() -> Unit)?, content: TableRowScope.() -> Unit) {
        add(kind = TableRowKind.Body, isSelected = isSelected, onClick = onClick, content = content)
    }

    override fun footer(content: TableRowScope.() -> Unit) {
        add(kind = TableRowKind.Foot, isSelected = false, onClick = null, content = content)
    }

    override fun caption(text: String) {
        captions += text
    }

    private fun add(
        kind: TableRowKind,
        isSelected: Boolean,
        onClick: (() -> Unit)?,
        content: TableRowScope.() -> Unit,
    ) {
        val cells = TableRowScopeImpl().apply(content).cells
        collected += TableRowSpec(kind = kind, isSelected = isSelected, onClick = onClick, cells = cells)
    }
}

private fun bordered(rows: List<TableRowSpec>): List<TableRowSpec> {
    val lastBody = rows.indexOfLast { row -> row.kind == TableRowKind.Body }
    val firstFoot = rows.indexOfFirst { row -> row.kind == TableRowKind.Foot }
    val lastFoot = rows.indexOfLast { row -> row.kind == TableRowKind.Foot }
    return rows.mapIndexed { index, row ->
        row.copy(hasTopBorder = index == firstFoot, hasBottomBorder = index != lastBody && index != lastFoot)
    }
}
