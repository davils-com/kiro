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

package com.davils.kiro.ui.components.radiogroup

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
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val COMFORTABLE: String = "comfortable"
private const val COMPACT: String = "compact"

@OptIn(ExperimentalTestApi::class)
class RadioGroupTest : FunSpec({
    val light = Theme.light()

    context("styles") {
        test("are size-4 circles with a size-2 dot and a gap-2 group") {
            val style = RadioStyles.Default.resolve(light)

            style.size shouldBe 16.dp
            style.dotSize shouldBe 8.dp
            style.groupGap shouldBe 8.dp
            style.checkedColors.container.default shouldBe light.colors.primary
        }
    }

    context("radio group") {
        test("selects the clicked item") {
            runComposeUiTest {
                setContent {
                    Themed {
                        var selected by remember { mutableStateOf<String?>(null) }
                        RadioGroup(selected = selected, onSelectedChange = { selected = it }) {
                            RadioGroupItem(value = COMFORTABLE, modifier = Modifier.testTag(COMFORTABLE))
                            RadioGroupItem(value = COMPACT, modifier = Modifier.testTag(COMPACT))
                        }
                    }
                }

                onNodeWithTag(COMPACT).performClick()
                onNodeWithTag(COMPACT).assertIsSelected()
                onNodeWithTag(COMFORTABLE).assertIsNotSelected()
                onNodeWithTag(COMFORTABLE).performClick()
                onNodeWithTag(COMFORTABLE).assertIsSelected()
            }
        }

        test("selects the next enabled item with the arrow keys") {
            var selected by mutableStateOf<String?>(null)

            runComposeUiTest {
                setContent {
                    Themed {
                        RadioGroup(selected = selected, onSelectedChange = { selected = it }) {
                            RadioGroupItem(value = COMFORTABLE, modifier = Modifier.testTag(COMFORTABLE))
                            RadioGroupItem(value = "disabled", enabled = false)
                            RadioGroupItem(value = COMPACT, modifier = Modifier.testTag(COMPACT))
                        }
                    }
                }

                onNodeWithTag(COMFORTABLE).requestFocus()
                onNodeWithTag(COMFORTABLE).performKeyInput { pressKey(Key.DirectionDown) }
                waitForIdle()
                onNodeWithTag(COMPACT).assertIsFocused()
                onNodeWithTag(COMPACT).performKeyInput { pressKey(Key.A) }
            }

            selected shouldBe COMPACT
        }

        test("lays out a disabled and invalid group in a row") {
            runComposeUiTest {
                setContent {
                    Themed {
                        RadioGroup(
                            selected = COMPACT,
                            onSelectedChange = {},
                            enabled = false,
                            isInvalid = true,
                            orientation = Orientation.Horizontal,
                        ) {
                            RadioGroupItem(value = COMPACT, modifier = Modifier.testTag(COMPACT))
                        }
                    }
                }

                onNodeWithTag(COMPACT).assertIsNotEnabled()
            }
        }
    }
})
