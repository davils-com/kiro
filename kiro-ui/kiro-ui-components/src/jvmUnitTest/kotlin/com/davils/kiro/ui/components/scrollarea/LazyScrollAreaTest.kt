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

package com.davils.kiro.ui.components.scrollarea

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec

private const val ROWS: Int = 1000
private const val LAST: Int = ROWS - 1
private val VIEWPORT = 160.dp

@OptIn(ExperimentalTestApi::class)
class LazyScrollAreaTest : FunSpec({
    context("vertical") {
        test("composes only the visible rows of a long list") {
            runComposeUiTest {
                setContent {
                    Themed {
                        LazyScrollArea(modifier = Modifier.size(VIEWPORT)) {
                            items(count = ROWS) { row -> Text("Row $row") }
                        }
                    }
                }

                onNodeWithText("Row 0").assertExists()
                onNodeWithText("Row $LAST").assertDoesNotExist()
            }
        }

        test("scrolls to the last row") {
            val state = LazyListState()
            runComposeUiTest {
                setContent {
                    Themed {
                        LazyScrollArea(modifier = Modifier.size(VIEWPORT), state = state) {
                            items(count = ROWS) { row -> Text("Row $row") }
                        }
                        LaunchedEffect(state) { state.scrollToItem(LAST) }
                    }
                }

                onNodeWithText("Row $LAST").assertExists()
            }
        }
    }

    context("horizontal") {
        test("lays the items out in a row and scrolls sideways") {
            val state = LazyListState()
            runComposeUiTest {
                setContent {
                    Themed {
                        LazyScrollArea(
                            modifier = Modifier.size(VIEWPORT),
                            state = state,
                            orientation = Orientation.Horizontal,
                        ) {
                            items(
                                count = ROWS,
                            ) { column -> Text("Column $column", modifier = Modifier.width(VIEWPORT)) }
                        }
                        LaunchedEffect(state) { state.scrollToItem(LAST) }
                    }
                }

                onNodeWithText("Column $LAST").assertExists()
            }
        }
    }
})
