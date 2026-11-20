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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec

private const val LINES: Int = 40
private const val CONTENT: String = "content"
private val VIEWPORT = 120.dp
private val GUTTER = 10.dp

@OptIn(ExperimentalTestApi::class)
class ScrollGutterTest : FunSpec({
    context("gutter") {
        test("keeps the scrollbar beside overflowing content") {
            runComposeUiTest {
                setContent {
                    Themed {
                        ScrollArea(modifier = Modifier.size(VIEWPORT)) {
                            Column(modifier = Modifier.fillMaxWidth().testTag(CONTENT)) {
                                repeat(LINES) { line -> Text("Line $line") }
                            }
                        }
                    }
                }

                onNodeWithTag(CONTENT).assertWidthIsEqualTo(VIEWPORT - GUTTER)
            }
        }

        test("gives content that fits the whole width") {
            runComposeUiTest {
                setContent {
                    Themed {
                        ScrollArea(modifier = Modifier.size(VIEWPORT)) {
                            Box(modifier = Modifier.fillMaxWidth().testTag(CONTENT)) { Text("Short") }
                        }
                    }
                }

                onNodeWithTag(CONTENT).assertWidthIsEqualTo(VIEWPORT)
            }
        }
    }

    context("horizontal") {
        test("keeps the scrollbar below overflowing rows") {
            runComposeUiTest {
                setContent {
                    Themed {
                        ScrollArea(modifier = Modifier.size(VIEWPORT), direction = ScrollDirection.Horizontal) {
                            Row(modifier = Modifier.fillMaxHeight().testTag(CONTENT)) {
                                repeat(LINES) { line -> Text("Column $line", modifier = Modifier.width(VIEWPORT)) }
                            }
                        }
                    }
                }

                onNodeWithTag(CONTENT).assertHeightIsEqualTo(VIEWPORT - GUTTER)
            }
        }

        test("reserves both gutters when the content overflows both ways") {
            runComposeUiTest {
                setContent {
                    Themed {
                        ScrollArea(
                            modifier = Modifier.size(VIEWPORT).testTag(TAG),
                            direction = ScrollDirection.Both,
                        ) {
                            Column {
                                repeat(LINES) { line -> Text("Row $line", modifier = Modifier.width(VIEWPORT * 2)) }
                            }
                        }
                    }
                }

                onNodeWithTag(TAG).assertWidthIsEqualTo(VIEWPORT)
            }
        }
    }
})
