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

package com.davils.kiro.ui.components.togglegroup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.toggle.ToggleSize
import com.davils.kiro.ui.components.toggle.ToggleVariant
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val BOLD: String = "bold"
private const val ITALIC: String = "italic"

@OptIn(ExperimentalTestApi::class)
class ToggleGroupTest : FunSpec({
    val light = Theme.light()

    context("styles") {
        test("space items by 2 and tighten joined items to px-2") {
            val style = ToggleGroupStyles.Default.resolve(light, ToggleVariant.Outline, ToggleSize.Sm)

            style.spacing shouldBe 8.dp
            style.joinedPadding shouldBe 8.dp
            style.item.height shouldBe 28.dp
            style.radius shouldBe 10.dp
        }
    }

    context("group") {
        test("presses the clicked item and releases the others") {
            runComposeUiTest {
                setContent {
                    Themed {
                        var selected by remember { mutableStateOf(emptySet<String>()) }
                        ToggleGroup(selected = selected, onSelectedChange = { selected = it }) {
                            item(value = BOLD) { Text("B", modifier = Modifier.testTag(BOLD)) }
                            item(value = ITALIC) { Text("I", modifier = Modifier.testTag(ITALIC)) }
                        }
                    }
                }

                onNodeWithText("B").performClick()
                onNodeWithText("I").performClick()
                onNodeWithTag(ITALIC, useUnmergedTree = true).assertExists()
                onNodeWithText("I").assertIsOn()
                onNodeWithText("B").assertIsOff()
            }
        }

        test("joins vertical outline items without spacing") {
            runComposeUiTest {
                setContent {
                    Themed {
                        val base = toggleGroupStyle(variant = ToggleVariant.Outline)
                        ToggleGroup(
                            selected = setOf(BOLD),
                            onSelectedChange = {},
                            isMultiple = true,
                            orientation = Orientation.Vertical,
                            style = base.copy(spacing = 0.dp),
                        ) {
                            item(value = BOLD) { Text("Bold text") }
                            item(value = ITALIC, enabled = false) { Text("I") }
                        }
                    }
                }

                val first = onNodeWithText("Bold text").getUnclippedBoundsInRoot()
                val second = onNodeWithText("I").getUnclippedBoundsInRoot()
                second.top shouldBe first.bottom - 1.dp
                onNodeWithText("I").assertIsNotEnabled()
            }
        }

        test("moves the focus with the arrow keys") {
            runComposeUiTest {
                setContent {
                    Themed {
                        ToggleGroup(selected = emptySet<String>(), onSelectedChange = {}) {
                            item(value = BOLD) { Text("B") }
                            item(value = ITALIC) { Text("I") }
                        }
                    }
                }

                onNodeWithText("B").requestFocus()
                onNodeWithText("B").performKeyInput { pressKey(Key.DirectionRight) }
                onNodeWithText("I").assertIsFocused()
                onNodeWithText("I").performKeyInput { pressKey(Key.A) }
            }
        }

        test("spaces vertical items apart") {
            runComposeUiTest {
                setContent {
                    Themed {
                        ToggleGroup(
                            selected = emptySet<String>(),
                            onSelectedChange = {},
                            orientation = Orientation.Vertical,
                        ) {
                            item(value = BOLD) { Text("B") }
                        }
                    }
                }

                onNodeWithText("B").assertIsOff()
            }
        }
    }
})
