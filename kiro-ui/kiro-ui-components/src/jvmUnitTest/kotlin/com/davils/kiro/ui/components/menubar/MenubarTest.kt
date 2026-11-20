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

package com.davils.kiro.ui.components.menubar

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
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val FILE: String = "File"
private const val EDIT: String = "Edit"
private const val NEW_TAB: String = "New Tab"
private const val UNDO: String = "Undo"

@OptIn(ExperimentalTestApi::class)
class MenubarTest : FunSpec({
    context("styles") {
        test("are h-8 bordered bars with rounded-sm triggers") {
            val light = Theme.light()
            val style = MenubarStyles.Default.resolve(light)

            style.height shouldBe 32.dp
            style.border.color shouldBe light.colors.border
            style.triggerActive shouldBe light.colors.muted
            style.sideOffset shouldBe 8.dp
        }
    }

    context("menubar") {
        test("opens a menu by click, switches by hover and runs an item") {
            var clicked = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Menubar {
                            menu(label = FILE) { item(label = NEW_TAB, onClick = { clicked = NEW_TAB }) }
                            menu(label = EDIT) { item(label = UNDO, onClick = { clicked = UNDO }) }
                            menu(label = "Help", enabled = false) { item(label = "About", onClick = {}) }
                        }
                    }
                }

                onNodeWithText(FILE).performClick()
                waitForIdle()
                onNodeWithText(NEW_TAB).assertExists()
                onNodeWithText(EDIT).performMouseInput { moveTo(center) }
                waitForIdle()
                onNodeWithText(UNDO).performClick()
                waitForIdle()
            }

            clicked shouldBe UNDO
        }

        test("moves between menus and items with the keyboard") {
            var clicked = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Menubar {
                            menu(label = FILE) { item(label = NEW_TAB, onClick = { clicked = NEW_TAB }) }
                            menu(label = EDIT) { item(label = UNDO, onClick = { clicked = UNDO }) }
                        }
                    }
                }

                val file = onNodeWithText(FILE)
                file.requestFocus()
                file.performKeyInput { pressKey(Key.DirectionRight) }
                waitForIdle()
                val edit = onNodeWithText(EDIT)
                edit.performKeyInput { pressKey(Key.DirectionDown) }
                waitForIdle()
                edit.performKeyInput { pressKey(Key.DirectionLeft) }
                waitForIdle()
                onNodeWithText(NEW_TAB).assertExists()
                onNodeWithText(FILE).performKeyInput { pressKey(Key.Escape) }
                waitForIdle()
                onNodeWithText(NEW_TAB).assertDoesNotExist()
                onNodeWithText(FILE).performKeyInput { pressKey(Key.Enter) }
                waitForIdle()
                onNodeWithText(FILE).performKeyInput { pressKey(Key.Enter) }
                waitForIdle()
            }

            clicked shouldBe NEW_TAB
        }
    }
})
