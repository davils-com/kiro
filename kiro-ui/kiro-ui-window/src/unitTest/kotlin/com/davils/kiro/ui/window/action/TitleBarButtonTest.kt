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

package com.davils.kiro.ui.window.action

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Search
import com.davils.kiro.ui.window.fixture.Themed
import com.davils.kiro.ui.window.titlebar.titleBarStyle
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val MARK: String = "mark"
private const val SEARCH: String = "Search"

@OptIn(ExperimentalTestApi::class)
class TitleBarButtonTest : FunSpec({
    test("runs its action") {
        var clicks = 0

        runComposeUiTest {
            setContent {
                Themed {
                    TitleBarButton(
                        icon = Lucide.Search,
                        contentDescription = SEARCH,
                        onClick = { clicks += 1 },
                    )
                }
            }
            onNodeWithContentDescription(SEARCH).performClick()
        }

        clicks shouldBe 1
    }

    test("shows a changed style on its badge") {
        runComposeUiTest {
            setContent {
                Themed {
                    val style = titleBarStyle().copy(badgeDotSize = 12.dp)
                    TitleBarButton(
                        icon = Lucide.Search,
                        contentDescription = SEARCH,
                        onClick = {},
                        badge = TitleBarBadge.Dot,
                        style = style,
                    )
                }
            }
            onNodeWithTag(BADGE_TAG).assertHeightIsEqualTo(12.dp)
        }
    }

    test("shows the limit of a changed style") {
        runComposeUiTest {
            setContent {
                Themed {
                    TitleBarButton(
                        icon = Lucide.Search,
                        contentDescription = SEARCH,
                        onClick = {},
                        badge = TitleBarBadge.Count(value = 12),
                        style = titleBarStyle().copy(badgeLimit = 9),
                    )
                }
            }
            onNodeWithText("9+").assertExists()
        }
    }

    test("replaces the badge through its slot") {
        runComposeUiTest {
            setContent {
                Themed {
                    TitleBarButton(
                        icon = Lucide.Search,
                        contentDescription = SEARCH,
                        onClick = {},
                        badge = TitleBarBadge.Dot,
                        mark = { _, modifier -> Box(modifier.testTag(MARK)) },
                    )
                }
            }
            onNodeWithTag(MARK).assertExists()
            onNodeWithTag(BADGE_TAG).assertDoesNotExist()
        }
    }
})
