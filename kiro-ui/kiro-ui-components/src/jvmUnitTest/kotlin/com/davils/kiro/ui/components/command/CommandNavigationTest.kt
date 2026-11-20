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

package com.davils.kiro.ui.components.command

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.test.withKeyDown
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val ALL: String = "All"
private const val FILES: String = "Files"
private const val ACTIONS: String = "Actions"
private const val README: String = "README.md"
private const val RELOAD: String = "Reload"
private const val DEVICES: String = "Devices"
private const val CORE: String = "core-01"
private const val PORTS: String = "Ports"
private const val UPLINK: String = "Gi1/0/48"

private fun CommandScope.workspace(onRun: (String) -> Unit) {
    allTab(label = ALL)
    tab(label = FILES, placeholder = "Search files...") {
        group(heading = FILES) { item(label = README, onSelect = { onRun(README) }, detail = "docs") }
    }
    tab(label = ACTIONS) {
        group(heading = ACTIONS) {
            item(label = RELOAD, onSelect = { onRun(RELOAD) }, shortcut = "Ctrl+R")
            page(label = DEVICES) {
                group(heading = DEVICES) {
                    page(label = CORE) {
                        group(heading = PORTS) { item(label = UPLINK, onSelect = { onRun(UPLINK) }) }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTestApi::class)
class CommandNavigationTest : FunSpec({
    context("tabs") {
        test("searches every tab in the all tab and switches tabs with tab and shift tab") {
            val state = CommandState()

            runComposeUiTest {
                setContent { Themed(theme = reducedMotion) { Command(state = state) { workspace {} } } }

                onNodeWithText(README).assertExists()
                onNodeWithText(RELOAD).assertExists()
                val field = onNode(hasSetTextAction())
                field.performClick()
                field.performKeyInput { pressKey(Key.Tab) }
                waitForIdle()
                state.tab shouldBe 1
                onNodeWithText(RELOAD).assertDoesNotExist()
                field.performKeyInput { withKeyDown(Key.ShiftLeft) { pressKey(Key.Tab) } }
                field.performKeyInput { withKeyDown(Key.ShiftLeft) { pressKey(Key.Tab) } }
                waitForIdle()
                state.tab shouldBe 2
                onNodeWithText(ALL).performClick()
                waitForIdle()
                state.tab shouldBe 0
            }
        }
    }

    context("pages") {
        test("opens nested pages, runs an item and walks back with backspace and escape") {
            val state = CommandState()
            var ran = ""

            runComposeUiTest {
                setContent { Themed(theme = reducedMotion) { Command(state = state) { workspace { ran = it } } } }

                onNodeWithText(DEVICES).performClick()
                waitForIdle()
                state.path shouldBe listOf(DEVICES)
                val field = onNode(hasSetTextAction())
                field.performClick()
                field.performKeyInput { pressKey(Key.Enter) }
                waitForIdle()
                state.path shouldBe listOf(DEVICES, CORE)
                onNodeWithText("$DEVICES / $CORE /").assertExists()
                field.performKeyInput { pressKey(Key.Backspace) }
                waitForIdle()
                state.path shouldBe listOf(DEVICES)
                field.performKeyInput { pressKey(Key.DirectionRight) }
                waitForIdle()
                field.performTextInput("48")
                waitForIdle()
                field.performKeyInput { pressKey(Key.Enter) }
                field.performKeyInput { pressKey(Key.Escape) }
                waitForIdle()
                state.path shouldBe listOf(DEVICES)
            }

            ran shouldBe UPLINK
        }
    }

    context("dialog") {
        test("focuses the search, closes after a pick and forgets the search") {
            var isOpen by mutableStateOf(true)
            val state = CommandState()
            var ran = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        CommandDialog(
                            open = isOpen,
                            onOpenChange = { isOpen = it },
                            state = state,
                        ) { workspace { ran = it } }
                    }
                }

                val field = onNode(hasSetTextAction() and isFocused())
                field.performTextInput("reload")
                waitForIdle()
                field.performKeyInput { pressKey(Key.Enter) }
                waitForIdle()
                onNode(hasSetTextAction()).assertDoesNotExist()
            }

            ran shouldBe RELOAD
            isOpen shouldBe false
            state.query shouldBe ""
        }
    }

    context("state") {
        test("ignores back without a page") {
            CommandState().back() shouldBe false
        }
    }
})
