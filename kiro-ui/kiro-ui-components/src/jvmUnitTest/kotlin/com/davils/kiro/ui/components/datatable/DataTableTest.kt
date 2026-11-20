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

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val EMAIL: String = "Email"

@OptIn(ExperimentalTestApi::class)
class DataTableTest : FunSpec({
    val email = DataTableColumn<String>(
        header = EMAIL,
        comparator = compareBy { value -> value },
    ) { value -> Text(value) }
    val status = DataTableColumn<String>(header = "Status") { Text("ok") }

    context("styles") {
        test("show an h-24 empty row") {
            DataTableStyles.Default.resolve(Theme.light()).emptyHeight shouldBe 96.dp
        }
    }

    context("data table") {
        test("sorts by a header and selects rows") {
            val state = DataTableState<String>()

            runComposeUiTest {
                setContent {
                    Themed {
                        DataTable(
                            data = listOf("b@kiro.dev", "a@kiro.dev"),
                            columns = listOf(email, status),
                            key = { value -> value },
                            state = state,
                            isSelectable = true,
                        )
                    }
                }

                onNodeWithText(EMAIL).performClick()
                onNodeWithText(EMAIL).performClick()
                onAllNodesWithText("ok")[0].assertExists()
            }

            state.sort?.isDescending shouldBe true
        }

        test("shows a message without rows") {
            runComposeUiTest {
                setContent {
                    Themed {
                        DataTable(
                            data = emptyList(),
                            columns = listOf(email),
                            key = { value -> value },
                            emptyText = "Nothing",
                        )
                    }
                }

                onNodeWithText("Nothing").assertExists()
            }
        }
    }
})
