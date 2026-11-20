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

package com.davils.kiro.ui.components.popover

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val OPEN: String = "Open"
private const val TITLE: String = "Dimensions"

@OptIn(ExperimentalTestApi::class)
class PopoverTest : FunSpec({
    context("styles") {
        test("are w-72 p-2.5 popover panels with a gap of 2.5") {
            val light = Theme.light()
            val style = PopoverStyles.Default.resolve(light)

            style.width shouldBe 288.dp
            style.gap shouldBe 10.dp
            style.panel.container shouldBe light.colors.popover
            style.panel.padding shouldBe 10.dp
            style.panel.shadow shouldBe light.shadows.md
        }
    }

    context("popover") {
        test("opens from its trigger and closes with escape") {
            var isOpen by mutableStateOf(false)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Popover(
                            expanded = isOpen,
                            onExpandedChange = { isOpen = it },
                            trigger = { Button(onClick = { isOpen = true }) { Text(OPEN) } },
                            placement = Placement(side = Side.End),
                        ) {
                            PopoverHeader {
                                PopoverTitle(text = TITLE)
                                PopoverDescription(text = "Set the dimensions.")
                            }
                        }
                    }
                }

                onNodeWithText(OPEN).performClick()
                waitForIdle()
                onNodeWithText(TITLE).assertExists()
                onNode(isFocused() and !hasClickAction()).performKeyInput { pressKey(Key.Escape) }
                waitForIdle()
                onNodeWithText(TITLE).assertDoesNotExist()
            }

            isOpen shouldBe false
        }

        test("animates its content in and out") {
            runComposeUiTest {
                setContent {
                    Themed {
                        var isShown by remember { mutableStateOf(true) }
                        Popover(expanded = isShown, onExpandedChange = { isShown = it }, trigger = { Text(OPEN) }) {
                            Button(onClick = { isShown = false }) { Text(TITLE) }
                        }
                    }
                }

                onNodeWithText(TITLE).performClick()
                waitForIdle()
                onNodeWithText(TITLE).assertDoesNotExist()
            }
        }
    }
})
