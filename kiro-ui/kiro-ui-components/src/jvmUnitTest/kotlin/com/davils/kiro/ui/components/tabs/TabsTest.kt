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

package com.davils.kiro.ui.components.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val ACCOUNT: String = "Account"
private const val PASSWORD: String = "Password"

@OptIn(ExperimentalTestApi::class)
class TabsTest : FunSpec({
    val light = Theme.light()
    val dark = Theme.dark()

    context("styles") {
        test("draw a muted h-8 list with a raised active trigger") {
            val style = TabsStyles.Default.resolve(light, TabsVariant.Default)

            style.listContainer shouldBe light.colors.muted
            style.listHeight shouldBe 32.dp
            style.listPadding shouldBe 3.dp
            style.colors.container.selected shouldBe light.colors.background
            style.colors.content.default shouldBe light.colors.foreground.copy(alpha = 0.6f)
            style.activeShadow shouldBe light.shadows.sm
            style.indicator shouldBe null
        }

        test("underline the active trigger of the line variant and raise it with the input color in dark mode") {
            val line = TabsStyles.Default.resolve(dark, TabsVariant.Line)
            val raised = TabsStyles.Default.resolve(dark, TabsVariant.Default)

            line.indicator?.color shouldBe dark.colors.foreground
            line.listGap shouldBe 4.dp
            raised.colors.container.selected shouldBe dark.colors.input.copy(alpha = 0.3f)
            raised.colors.border.selected shouldBe dark.colors.input
            raised.colors.content.default shouldBe dark.colors.mutedForeground
        }

        test("need a style of their own for custom variants") {
            shouldThrow<IllegalArgumentException> { TabsStyles.Default.resolve(light, Unknown) }
        }
    }

    context("tabs") {
        test("shows the content of the selected tab") {
            var selected by mutableStateOf(ACCOUNT)

            runComposeUiTest {
                setContent {
                    Themed {
                        Tabs(
                            selected = selected,
                            onSelectedChange = { selected = it },
                            listModifier = Modifier.testTag(TAG),
                        ) {
                            tab(value = ACCOUNT, content = { Text("Account settings") }) {
                                Icon(icon = Lucide.User, placement = IconPlacement.Leading)
                                Text(ACCOUNT)
                            }
                            tab(value = PASSWORD, content = { Text("Password settings") }) { Text(PASSWORD) }
                        }
                    }
                }

                onNodeWithTag(TAG).assertHeightIsEqualTo(32.dp)
                onNodeWithText("Account settings").assertExists()
                onNodeWithText(PASSWORD).performMouseInput { moveTo(center) }
                onNodeWithText(PASSWORD).performClick()
                onNodeWithText("Password settings").assertExists()
                onNodeWithText(PASSWORD).assertIsSelected()
            }

            selected shouldBe PASSWORD
        }

        test("selects the next enabled tab with the arrow keys") {
            var selected by mutableStateOf(ACCOUNT)

            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            Tabs(
                                selected = selected,
                                onSelectedChange = { selected = it },
                                orientation = Orientation.Vertical,
                                variant = TabsVariant.Line,
                            ) {
                                tab(value = ACCOUNT) { Text(ACCOUNT) }
                                tab(value = "Billing", enabled = false) { Text("Billing") }
                                tab(value = PASSWORD) { Text(PASSWORD) }
                            }
                            Tabs(selected = "none", onSelectedChange = {}, variant = TabsVariant.Line) {
                                tab(value = ACCOUNT) { Text("Other") }
                            }
                        }
                    }
                }

                onNodeWithText(ACCOUNT).requestFocus()
                onNodeWithText(ACCOUNT).performKeyInput { pressKey(Key.DirectionDown) }
                waitForIdle()
                onNode(isFocused()).performKeyInput { pressKey(Key.A) }
            }

            selected shouldBe PASSWORD
        }
    }
})
