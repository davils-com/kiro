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

package com.davils.kiro.ui.components.dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val TITLE: String = "Edit profile"
private const val SAVE: String = "Save changes"
private const val FAR: Float = 300f
private const val NEAR: Float = 200f

@OptIn(ExperimentalTestApi::class)
class DialogTest : FunSpec({
    context("styles") {
        test("are rounded-xl p-4 popover panels up to max-w-sm over a black/10 scrim") {
            val light = Theme.light()
            val style = DialogStyles.Default.resolve(light)

            style.panel.padding shouldBe 16.dp
            style.maxWidth shouldBe 384.dp
            style.radius shouldBe light.radii.xl
            style.footerContainer shouldBe light.colors.muted.copy(alpha = 0.5f)
        }
    }

    context("dialog") {
        test("shows its parts and closes with its button, escape and the scrim") {
            var isOpen by mutableStateOf(true)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Dialog(open = isOpen, onOpenChange = { isOpen = it }) {
                            DialogHeader {
                                DialogTitle(text = TITLE)
                                DialogDescription(text = "Make changes to your profile here.")
                            }
                            DialogFooter { Button(onClick = { isOpen = false }) { Text(SAVE) } }
                        }
                    }
                }

                onNodeWithText(TITLE).assertExists()
                onNodeWithText(TITLE).performClick()
                onNodeWithContentDescription("Close").performClick()
                waitForIdle()
                onNodeWithText(TITLE).assertDoesNotExist()
                isOpen = true
                waitForIdle()
                onNode(isFocused() and hasAnyDescendant(hasText(TITLE))).performKeyInput { pressKey(Key.Escape) }
                waitForIdle()
                isOpen = true
                waitForIdle()
                onNodeWithText(TITLE).performMouseInput { click(Offset(x = -FAR, y = -NEAR)) }
                waitForIdle()
            }

            isOpen shouldBe false
        }

        test("animates in and out") {
            var isOpen by mutableStateOf(true)

            runComposeUiTest {
                setContent {
                    Themed {
                        Dialog(
                            open = isOpen,
                            onOpenChange = { isOpen = it },
                            isCloseButtonShown = false,
                        ) { Text(TITLE) }
                    }
                }

                onNodeWithText(TITLE).assertExists()
                isOpen = false
                waitForIdle()
                onNodeWithText(TITLE).assertDoesNotExist()
            }
        }
    }
})
