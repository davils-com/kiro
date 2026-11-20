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

package com.davils.kiro.ui.components.navigationmenu

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val GETTING_STARTED: String = "Getting started"
private const val COMPONENTS: String = "Components"
private const val INTRODUCTION: String = "Introduction"
private const val ALERT: String = "Alert Dialog"
private const val WAIT_MILLIS: Long = 400
private const val PRODUCTS: String = "Products"
private const val SWITCHING: String = "Switching"
private const val CAMPUS: String = "Campus"
private const val ACCESS: String = "Access layer"

@OptIn(ExperimentalTestApi::class)
class NavigationMenuTest : FunSpec({
    context("styles") {
        test("are h-9 triggers with a size-3 chevron") {
            val light = Theme.light()
            val style = NavigationMenuStyles.Default.resolve(light)

            style.triggerHeight shouldBe 36.dp
            style.chevronSize shouldBe 12.dp
            style.hover shouldBe light.colors.muted
        }
    }

    context("navigation menu") {
        test("opens a panel on hover and follows the pointer to the next trigger") {
            var visited = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        NavigationMenu {
                            item(label = GETTING_STARTED) {
                                NavigationMenuLink(
                                    onClick = { visited = INTRODUCTION },
                                    isActive = true,
                                ) { Text(INTRODUCTION) }
                            }
                            item(label = COMPONENTS) {
                                NavigationMenuLink(onClick = { visited = ALERT }) { Text(ALERT) }
                            }
                            link(label = "Docs", onClick = { visited = "Docs" })
                        }
                    }
                }

                onNodeWithText(GETTING_STARTED).performMouseInput { moveTo(center) }
                mainClock.advanceTimeBy(WAIT_MILLIS)
                waitForIdle()
                onNodeWithText(INTRODUCTION).assertExists()
                onNodeWithText(COMPONENTS).performMouseInput { moveTo(center) }
                mainClock.advanceTimeBy(WAIT_MILLIS)
                waitForIdle()
                onNodeWithText(ALERT).performMouseInput { moveTo(center) }
                onNodeWithText(ALERT).performClick()
                onNodeWithText("Docs").performClick()
            }

            visited shouldBe "Docs"
        }

        test("toggles with clicks and moves with the arrow keys") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        NavigationMenu {
                            item(label = GETTING_STARTED) { Text(INTRODUCTION) }
                            item(label = COMPONENTS) { Text(ALERT) }
                        }
                    }
                }

                val first = onNodeWithText(GETTING_STARTED)
                first.performClick()
                waitForIdle()
                onNodeWithText(INTRODUCTION).assertExists()
                first.requestFocus()
                first.performKeyInput { pressKey(Key.DirectionRight) }
                onNodeWithText(COMPONENTS).performKeyInput { pressKey(Key.Escape) }
                waitForIdle()
                onNodeWithText(INTRODUCTION).assertDoesNotExist()
                onNodeWithText(COMPONENTS).performKeyInput { pressKey(Key.A) }
            }
        }

        test("opens a menu with nested sub menus three levels deep") {
            var picked = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        NavigationMenu {
                            menu(label = PRODUCTS) {
                                item(label = "Overview", onClick = { picked = "Overview" })
                                sub(label = SWITCHING) {
                                    sub(label = CAMPUS) {
                                        item(label = ACCESS, onClick = { picked = ACCESS })
                                    }
                                }
                            }
                        }
                    }
                }

                onNodeWithText(PRODUCTS).performMouseInput { moveTo(center) }
                mainClock.advanceTimeBy(WAIT_MILLIS)
                waitForIdle()
                onNodeWithText(SWITCHING).performMouseInput { moveTo(center) }
                mainClock.advanceTimeBy(WAIT_MILLIS)
                waitForIdle()
                onNodeWithText(CAMPUS).performMouseInput { moveTo(center) }
                mainClock.advanceTimeBy(WAIT_MILLIS)
                waitForIdle()
                onNodeWithText(ACCESS).performClick()
                waitForIdle()
                onNodeWithText(ACCESS).assertDoesNotExist()
            }

            picked shouldBe ACCESS
        }
    }
})
