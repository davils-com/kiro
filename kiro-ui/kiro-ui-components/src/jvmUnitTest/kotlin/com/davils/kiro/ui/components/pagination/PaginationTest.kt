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

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalTestApi::class)
class PaginationTest : FunSpec({
    context("styles") {
        test("space links by 0.5 and tighten the edge links to pl-1.5") {
            val style = PaginationStyles.Default.resolve(Theme.light())

            style.gap shouldBe 2.dp
            style.edgePadding shouldBe 6.dp
            style.ellipsisBox shouldBe 32.dp
        }
    }

    context("pagination") {
        test("moves between pages") {
            var page = 2

            runComposeUiTest {
                setContent {
                    Themed {
                        Pagination {
                            PaginationPrevious(onClick = { page-- })
                            PaginationLink(onClick = { page = 1 }) { Text("1") }
                            PaginationLink(onClick = {}, isActive = true) { Text("2") }
                            PaginationEllipsis(contentDescription = "More pages")
                            PaginationNext(onClick = { page++ })
                        }
                    }
                }

                onNodeWithText("2").assertIsSelected()
                onNodeWithText("Next").performClick()
                onNodeWithText("Previous").performClick()
                onNodeWithText("Previous").performClick()
                onNodeWithContentDescription("More pages").assertExists()
            }

            page shouldBe 1
        }
    }
})
