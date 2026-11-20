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

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val CAROL: String = "carol"
private const val ALICE: String = "alice"
private const val BOB: String = "bob"
private const val PAGE_SIZE: Int = 2

class DataTableStateTest : FunSpec({
    val name = DataTableColumn<String>(
        header = "Name",
        comparator = compareBy { value -> value },
        filterText = { value -> value },
    ) {}
    val fixed = DataTableColumn<String>(header = "Fixed", isHideable = false) {}
    val data = listOf(CAROL, ALICE, BOB)

    context("sorting") {
        test("cycles from ascending to descending to unsorted") {
            val state = DataTableState<String>(pageSize = data.size)

            state.sortBy(name)
            state.view(data, listOf(name)).rows shouldBe listOf(ALICE, BOB, CAROL)
            state.sortBy(name)
            state.view(data, listOf(name)).rows shouldBe listOf(CAROL, BOB, ALICE)
            state.sortBy(name)
            state.view(data, listOf(name)).rows shouldBe data
            state.sortBy(fixed)
            state.view(data, listOf(name)).rows shouldBe data
        }
    }

    context("filtering and paging") {
        test("filters without case and pages the result") {
            val state = DataTableState<String>(pageSize = PAGE_SIZE)

            state.view(data, listOf(name)).pageCount shouldBe 2
            state.showPage(1)
            state.view(data, listOf(name)).rows shouldBe listOf(BOB)
            state.view(data, listOf(name)).hasNextPage shouldBe false
            state.view(data, listOf(name)).hasPreviousPage shouldBe true
            state.filterBy("AL")
            state.page shouldBe 0
            state.view(data, listOf(name)).rows shouldBe listOf(ALICE)
            state.view(data, listOf(fixed)).rowCount shouldBe data.size
            state.filterBy(" ")
            state.view(data, listOf(name)).rowCount shouldBe data.size
            state.showPage(-1)
            state.page shouldBe 0
            state.resize(1)
            state.view(emptyList(), listOf(name)).pageCount shouldBe 1
        }

        test("needs a page size above zero") {
            shouldThrow<IllegalArgumentException> { DataTableState<String>(pageSize = 0) }
            shouldThrow<IllegalArgumentException> { DataTableState<String>().resize(0) }
        }
    }

    context("visibility and selection") {
        test("hides hideable columns and toggles rows") {
            val state = DataTableState<String>()

            state.toggleColumn(name)
            state.toggleColumn(fixed)
            state.view(data, listOf(name, fixed)).columns shouldBe listOf(fixed)
            state.toggleColumn(name)
            state.toggleRow(ALICE)
            state.selected shouldBe setOf(ALICE)
            state.toggleRows(data)
            state.selected shouldBe data.toSet()
            state.toggleRows(data)
            state.selected shouldBe emptySet()
            state.toggleRows(emptyList())
            state.toggleRow(ALICE)
            state.toggleRow(ALICE)
            state.selected shouldBe emptySet()
        }
    }
})
