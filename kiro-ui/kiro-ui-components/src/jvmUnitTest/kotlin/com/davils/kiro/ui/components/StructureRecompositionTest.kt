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

package com.davils.kiro.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.accordion.Accordion
import com.davils.kiro.ui.components.accordion.accordionStyle
import com.davils.kiro.ui.components.breadcrumb.Breadcrumb
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbEllipsis
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbItem
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbLink
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbPage
import com.davils.kiro.ui.components.breadcrumb.BreadcrumbSeparator
import com.davils.kiro.ui.components.breadcrumb.breadcrumbStyle
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.carousel.Carousel
import com.davils.kiro.ui.components.carousel.carouselStyle
import com.davils.kiro.ui.components.collapsible.Collapsible
import com.davils.kiro.ui.components.collapsible.CollapsibleContent
import com.davils.kiro.ui.components.collapsible.CollapsibleTrigger
import com.davils.kiro.ui.components.collapsible.collapsibleStyle
import com.davils.kiro.ui.components.datatable.DataTable
import com.davils.kiro.ui.components.datatable.DataTableColumn
import com.davils.kiro.ui.components.datatable.dataTableStyle
import com.davils.kiro.ui.components.datatable.rememberDataTableState
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.pagination.Pagination
import com.davils.kiro.ui.components.pagination.PaginationEllipsis
import com.davils.kiro.ui.components.pagination.PaginationLink
import com.davils.kiro.ui.components.pagination.PaginationNext
import com.davils.kiro.ui.components.pagination.PaginationPrevious
import com.davils.kiro.ui.components.pagination.paginationStyle
import com.davils.kiro.ui.components.resizable.ResizablePanelGroup
import com.davils.kiro.ui.components.resizable.rememberResizableState
import com.davils.kiro.ui.components.resizable.resizableStyle
import com.davils.kiro.ui.components.scrollarea.ScrollArea
import com.davils.kiro.ui.components.scrollarea.ScrollDirection
import com.davils.kiro.ui.components.scrollarea.scrollAreaStyle
import com.davils.kiro.ui.components.table.Table
import com.davils.kiro.ui.components.table.tableStyle
import com.davils.kiro.ui.components.tabs.Tabs
import com.davils.kiro.ui.components.tabs.TabsVariant
import com.davils.kiro.ui.components.tabs.tabsStyle
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val ROUNDS: Int = 3
private const val FIXED: String = "fixed"
private const val HALF: Float = 50f

@Composable
private fun Disclosures(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    Collapsible(
        expanded = isOdd,
        onExpandedChange = {},
        modifier = Modifier,
        enabled = !isOdd,
        style = collapsibleStyle(),
    ) {
        CollapsibleTrigger(modifier = Modifier) { Text(label) }
        CollapsibleContent(modifier = Modifier) { Text(label) }
    }
    Accordion(
        expanded = setOf(label),
        onExpandedChange = {},
        modifier = Modifier,
        isMultiple = isOdd,
        style = accordionStyle(),
    ) {
        item(value = label, enabled = !isOdd, trigger = { Text(label) }) { Text(label) }
    }
    Tabs(
        selected = label,
        onSelectedChange = {},
        modifier = Modifier,
        listModifier = Modifier,
        orientation = Orientation.Vertical,
        variant = TabsVariant.Line,
        style = tabsStyle(TabsVariant.Line),
    ) {
        tab(value = label, enabled = !isOdd, content = { Text(label) }) { Text(label) }
    }
}

@Composable
private fun Navigation(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    Breadcrumb(modifier = Modifier, style = breadcrumbStyle()) {
        BreadcrumbItem(modifier = Modifier, style = breadcrumbStyle()) {
            BreadcrumbLink(text = label, onClick = {}, modifier = Modifier, style = breadcrumbStyle())
        }
        BreadcrumbSeparator(modifier = Modifier, style = breadcrumbStyle())
        BreadcrumbEllipsis(contentDescription = label, modifier = Modifier, style = breadcrumbStyle())
        BreadcrumbPage(text = label, modifier = Modifier, style = breadcrumbStyle())
    }
    Pagination(modifier = Modifier, style = paginationStyle()) {
        PaginationPrevious(onClick = {}, modifier = Modifier, text = label, enabled = !isOdd, style = paginationStyle())
        PaginationLink(
            onClick = {},
            modifier = Modifier,
            isActive = isOdd,
            size = ButtonSize.Sm,
            enabled = !isOdd,
        ) { Text(label) }
        PaginationEllipsis(contentDescription = label, modifier = Modifier, style = paginationStyle())
        PaginationNext(onClick = {}, modifier = Modifier, text = label, enabled = !isOdd, style = paginationStyle())
    }
    ScrollArea(
        modifier = Modifier.size(40.dp),
        direction = ScrollDirection.Both,
        verticalState = rememberScrollState(),
        horizontalState = rememberScrollState(),
        style = scrollAreaStyle(),
    ) {
        Text(label)
    }
}

@Composable
private fun Layouts(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    ResizablePanelGroup(
        state = rememberResizableState(HALF, HALF),
        modifier = Modifier.size(80.dp),
        orientation = Orientation.Vertical,
        isGripShown = isOdd,
        style = resizableStyle(),
    ) {
        panel(minSize = 0f, maxSize = 100f) { Text(label) }
        panel { Text(label) }
    }
    Table(modifier = Modifier, style = tableStyle()) {
        header { cell(span = 1, alignment = Alignment.End) { Text(label) } }
        row(isSelected = isOdd, onClick = {}) { cell { Text(label) } }
    }
    DataTable(
        data = listOf(label),
        columns = listOf(DataTableColumn(header = label) { value -> Text(value) }),
        key = { value -> value },
        modifier = Modifier,
        state = rememberDataTableState(),
        isSelectable = isOdd,
        emptyText = label,
        style = dataTableStyle(),
    )
    Carousel(
        count = 2,
        modifier = Modifier.size(80.dp),
        state = rememberPagerState(pageCount = { 2 }),
        orientation = Orientation.Horizontal,
        itemsPerView = 1,
        isNavigationShown = !isOdd,
        style = carouselStyle(),
    ) { page -> Text("$label $page") }
}

@OptIn(ExperimentalTestApi::class)
class StructureRecompositionTest : FunSpec({
    context("structure components") {
        test("take every argument explicitly and recompose with changed and unchanged arguments") {
            var round by mutableIntStateOf(0)
            var rendered = -1

            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            val isOdd = round % 2 == 1
                            val label = "Round $round"
                            Disclosures(label = label, isOdd = isOdd, tick = round)
                            Disclosures(label = FIXED, isOdd = false, tick = round)
                            Navigation(label = label, isOdd = isOdd, tick = round)
                            Navigation(label = FIXED, isOdd = false, tick = round)
                            Layouts(label = label, isOdd = isOdd, tick = round)
                            Layouts(label = FIXED, isOdd = false, tick = round)
                            val stable = remember { FIXED.uppercase() }
                            val isNever = round < 0
                            Disclosures(label = stable, isOdd = isNever, tick = round)
                            Navigation(label = stable, isOdd = isNever, tick = round)
                            Layouts(label = stable, isOdd = isNever, tick = round)
                            rendered = round
                        }
                    }
                }
                repeat(ROUNDS) {
                    round++
                    waitForIdle()
                }
            }

            rendered shouldBe ROUNDS
        }
    }
})
