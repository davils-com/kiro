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

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Search
import com.davils.kiro.ui.window.fixture.Themed
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalTestApi::class)
class NotificationBellTest : FunSpec({
    context("notification bell") {
        test("shows the number of unread notifications and reacts to a click") {
            var clicks = 0

            runComposeUiTest {
                setContent { Themed { NotificationBell(count = 3, onClick = { clicks++ }) } }

                onNodeWithText("3").assertIsDisplayed()
                onNodeWithContentDescription("Notifications").performClick()
            }

            clicks shouldBe 1
        }

        test("caps the badge at ninety-nine") {
            runComposeUiTest {
                setContent { Themed { NotificationBell(count = 120, onClick = {}) } }

                onNodeWithText("99+").assertIsDisplayed()
            }
        }

        test("hides the badge without notifications") {
            runComposeUiTest {
                setContent { Themed { NotificationBell(count = 0, onClick = {}) } }

                onNodeWithText("0").assertDoesNotExist()
            }
        }
    }

    context("title bar button") {
        test("marks pending work with a dot") {
            runComposeUiTest {
                setContent {
                    Themed {
                        TitleBarButton(
                            icon = Lucide.Search,
                            contentDescription = "Search",
                            onClick = {},
                            badge = TitleBarBadge.Dot,
                        )
                    }
                }

                onNodeWithContentDescription("Search").assertIsDisplayed()
            }
        }
    }
})
