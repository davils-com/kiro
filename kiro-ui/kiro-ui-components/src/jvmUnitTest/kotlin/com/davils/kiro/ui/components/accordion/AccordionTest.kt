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

package com.davils.kiro.ui.components.accordion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val FIRST: String = "Is it accessible?"
private const val SECOND: String = "Is it styled?"
private const val THIRD: String = "Is it animated?"

@OptIn(ExperimentalTestApi::class)
class AccordionTest : FunSpec({
    context("styles") {
        test("are py-2.5 text-sm font-medium triggers divided by the border color") {
            val light = Theme.light()
            val style = AccordionStyles.Default.resolve(light)

            style.triggerPadding shouldBe 10.dp
            style.divider.color shouldBe light.colors.border
            style.iconColor shouldBe light.colors.mutedForeground
            style.contentPadding shouldBe 10.dp
        }
    }

    context("accordion") {
        test("opens one item at a time") {
            var expanded by mutableStateOf(emptySet<Int>())

            runComposeUiTest {
                setContent {
                    Themed {
                        Accordion(expanded = expanded, onExpandedChange = { expanded = it }) {
                            item(value = 1, trigger = { Text(FIRST) }) { Text("Yes.") }
                            item(value = 2, trigger = { Text(SECOND) }) { Text("Yes, too.") }
                            item(value = 3, enabled = false, trigger = { Text(THIRD) }) { Text("No.") }
                        }
                    }
                }

                onNodeWithText(FIRST).performMouseInput { moveTo(center) }
                onNodeWithText(FIRST).performClick()
                waitForIdle()
                onNodeWithText("Yes.").assertExists()
                onNodeWithText(SECOND).performClick()
                waitForIdle()
                onNodeWithText("Yes.").assertDoesNotExist()
            }

            expanded shouldBe setOf(2)
        }

        test("keeps several items open and moves the focus with the arrow keys") {
            var expanded by mutableStateOf(setOf(1))

            runComposeUiTest {
                setContent {
                    Themed {
                        Accordion(expanded = expanded, onExpandedChange = { expanded = it }, isMultiple = true) {
                            item(value = 1, trigger = { Text(FIRST) }) { Text("Yes.") }
                            item(value = 2, trigger = { Text(SECOND) }) { Text("Yes, too.") }
                        }
                    }
                }

                onNodeWithText(FIRST).requestFocus()
                onNodeWithText(FIRST).performKeyInput { pressKey(Key.DirectionDown) }
                val focused = onNode(isFocused())
                focused.performKeyInput { pressKey(Key.A) }
                focused.performClick()
            }

            expanded shouldBe setOf(1, 2)
        }
    }
})
