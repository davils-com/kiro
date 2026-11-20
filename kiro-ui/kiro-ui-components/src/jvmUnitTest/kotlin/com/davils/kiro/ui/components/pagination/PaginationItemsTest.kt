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

package com.davils.kiro.ui.components.pagination

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val THREE: Int = 3
private const val FIVE: Int = 5
private const val PAGES: Int = 10

class PaginationItemsTest : FunSpec({
    context("items") {
        test("show every page when there are few") {
            paginationItems(
                page = 2,
                pageCount = THREE,
            ) shouldBe (1..THREE).map { number -> PaginationItem.Page(number) }
            paginationItems(page = 1, pageCount = 0) shouldBe emptyList()
        }

        test("put ellipses between the edges and the siblings") {
            paginationItems(page = 5, pageCount = PAGES) shouldBe listOf(
                PaginationItem.Page(number = 1),
                PaginationItem.Ellipsis(position = 2),
                PaginationItem.Page(number = 4),
                PaginationItem.Page(number = 5),
                PaginationItem.Page(number = 6),
                PaginationItem.Ellipsis(position = 7),
                PaginationItem.Page(number = PAGES),
            )
        }

        test("show a single hidden page instead of an ellipsis") {
            paginationItems(
                page = THREE,
                pageCount = FIVE,
                siblingCount = 0,
            ) shouldBe (1..FIVE).map { number -> PaginationItem.Page(number) }
        }

        test("reject negative counts") {
            shouldThrow<IllegalArgumentException> { paginationItems(page = 1, pageCount = -1) }
            shouldThrow<IllegalArgumentException> { paginationItems(page = 1, pageCount = 1, siblingCount = -1) }
        }
    }
})
