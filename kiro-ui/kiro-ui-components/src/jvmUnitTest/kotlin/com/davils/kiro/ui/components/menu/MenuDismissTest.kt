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

package com.davils.kiro.ui.components.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.dropdownmenu.DropdownMenu
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.menubar.Menubar
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val BACKGROUND: String = "background"
private const val FILE: String = "File"
private const val NEW_TAB: String = "New tab"
private const val SHARE: String = "Share"
private const val EXPORT: String = "Export"
private const val PDF: String = "PDF"
private const val SETTLE: Long = 1_000L
private val outside = Offset(x = 700f, y = 500f)

private fun MenuScopeImpl.fileMenu(onNewTab: () -> Unit = {}) {
    item(label = NEW_TAB, onClick = onNewTab)
    sub(label = SHARE) {
        sub(label = EXPORT) { item(label = PDF, onClick = {}) }
    }
}

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.hover(text: String) {
    onNodeWithText(text).performMouseInput { moveTo(center) }
    mainClock.advanceTimeBy(SETTLE)
    waitForIdle()
}

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.clickOutside() {
    onNodeWithTag(BACKGROUND).performMouseInput {
        moveTo(outside)
        press()
        release()
    }
    mainClock.advanceTimeBy(SETTLE)
    waitForIdle()
}

@OptIn(ExperimentalTestApi::class)
class MenuDismissTest : FunSpec({
    context("menubar") {
        test("closes every level with one click outside and closes again from its trigger") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Box(modifier = Modifier.fillMaxSize().testTag(BACKGROUND)) {
                            Menubar { menu(label = FILE) { fileMenu() } }
                        }
                    }
                }

                onNodeWithText(FILE).performClick()
                waitForIdle()
                hover(SHARE)
                hover(EXPORT)
                onNodeWithText(PDF).assertExists()
                clickOutside()
                onNodeWithText(EXPORT).assertDoesNotExist()
                onNodeWithText(NEW_TAB).assertDoesNotExist()
                onNodeWithText(FILE).performClick()
                waitForIdle()
                onNodeWithText(NEW_TAB).assertExists()
                onNodeWithText(FILE).performClick()
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
                onNodeWithText(NEW_TAB).assertDoesNotExist()
            }
        }
    }

    context("dropdown menu") {
        test("closes every level with one click outside") {
            var isOpen by mutableStateOf(true)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Box(modifier = Modifier.fillMaxSize().testTag(BACKGROUND)) {
                            DropdownMenu(
                                expanded = isOpen,
                                onExpandedChange = { isOpen = it },
                                trigger = { Text("Open") },
                            ) {
                                fileMenu()
                            }
                        }
                    }
                }

                hover(SHARE)
                hover(EXPORT)
                onNodeWithText(PDF).assertExists()
                clickOutside()
                onNodeWithText(EXPORT).assertDoesNotExist()
            }

            isOpen shouldBe false
        }

        test("keeps the menu when a click lands on a parent level") {
            var isOpen by mutableStateOf(true)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        DropdownMenu(
                            expanded = isOpen,
                            onExpandedChange = { isOpen = it },
                            trigger = { Text("Open") },
                        ) {
                            fileMenu()
                        }
                    }
                }

                hover(SHARE)
                onNodeWithText(EXPORT).assertExists()
                hover(NEW_TAB)
                onNodeWithText(NEW_TAB).performClick()
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
            }

            isOpen shouldBe false
        }
    }
})
