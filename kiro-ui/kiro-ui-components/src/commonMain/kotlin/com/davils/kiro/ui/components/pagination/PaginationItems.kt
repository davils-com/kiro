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

private const val EDGE_PAGES: Int = 1

/**
 * Returns the pages and gaps that a pagination shows: the first and the last page, the current page with
 * [siblingCount] pages on each side and an ellipsis for each gap of more than one page. A gap of exactly one page shows
 * that page instead.
 *
 * @param page the current page, counted from 1; it is clamped to the pages that exist.
 * @param pageCount the number of pages.
 * @param siblingCount the number of pages shown on each side of the current page.
 * @return the items in the order to show them.
 * @throws IllegalArgumentException if [pageCount] or [siblingCount] is negative.
 * @since 1.0.0
 */
public fun paginationItems(page: Int, pageCount: Int, siblingCount: Int = 1): List<PaginationItem> {
    require(pageCount >= 0) { "A pagination needs a page count of zero or more, not $pageCount." }
    require(siblingCount >= 0) { "A pagination needs a sibling count of zero or more, not $siblingCount." }
    val current = page.coerceIn(minimumValue = 1, maximumValue = maxOf(a = pageCount, b = 1))
    val first = maxOf(a = current - siblingCount, b = 1)
    val last = minOf(a = current + siblingCount, b = pageCount)
    val leading = 1..minOf(a = EDGE_PAGES, b = pageCount)
    val trailing = maxOf(a = pageCount - EDGE_PAGES + 1, b = 1)..pageCount
    val shown = leading + (first..last) + trailing
    val pages = shown.distinct().sorted()
    return pages.flatMapIndexed { index, number ->
        gapBefore(
            pages = pages,
            index = index,
        ) +
            PaginationItem.Page(number)
    }
}

private fun gapBefore(pages: List<Int>, index: Int): List<PaginationItem> {
    val previous = pages.getOrNull(index - 1) ?: return emptyList()
    return fillerOf(previous = previous, skipped = pages[index] - previous - 1)
}

private fun fillerOf(previous: Int, skipped: Int): List<PaginationItem> {
    if (skipped == 1) return listOf(PaginationItem.Page(previous + 1))
    val ellipsis = listOf<PaginationItem>(PaginationItem.Ellipsis(previous + 1))
    return ellipsis.takeIf { skipped > 1 }.orEmpty()
}
