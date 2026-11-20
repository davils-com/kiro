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

package com.davils.kiro.ui.components.nativeselect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
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
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.core.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeLessThan
import io.kotest.matchers.shouldBe
import kotlin.math.abs

private const val TODO: String = "Todo"
private const val DONE: String = "Done"
private const val BLOCKED: String = "Blocked"
private const val LONG: String = "A much longer option than the trigger is wide"

@OptIn(ExperimentalTestApi::class)
class NativeSelectTest : FunSpec({
    val light = Theme.light()
    val dark = Theme.dark()

    context("styles") {
        test("are h-8 rounded-lg with pl-2.5 pr-8 and a muted chevron") {
            val style = NativeSelectStyles.Default.resolve(light, NativeSelectSize.Default)

            style.height shouldBe 32.dp
            style.shape shouldBe RoundedCornerShape(10.dp)
            style.padding shouldBe PaddingValues(start = 10.dp, top = 4.dp, end = 32.dp, bottom = 4.dp)
            style.icon.color shouldBe light.colors.mutedForeground
            style.colors.container.hovered shouldBe null
            style.menu.container shouldBe light.colors.popover
        }

        test("shrink for sm and brighten on hover in dark mode") {
            val small = NativeSelectStyles.Default.resolve(dark, NativeSelectSize.Sm)

            small.height shouldBe 28.dp
            small.shape shouldBe RoundedCornerShape(8.dp)
            small.colors.container.hovered shouldBe dark.colors.input.copy(alpha = 0.5f)
        }

        test("need a style of their own for custom sizes") {
            shouldThrow<IllegalArgumentException> { NativeSelectStyles.Default.resolve(light, Unknown) }
        }
    }

    context("navigation") {
        test("skips disabled options and stops at the ends") {
            val options = listOf(
                NativeSelectEntry.Option(value = 1, label = "one", isEnabled = true),
                NativeSelectEntry.Option(value = 2, label = "two", isEnabled = false),
                NativeSelectEntry.Option(value = 3, label = "three", isEnabled = true),
            )

            stepFrom(options = options, index = 0, delta = 1) shouldBe 2
            stepFrom(options = options, index = 2, delta = 1) shouldBe null
            indexOf(options = options, value = 3) shouldBe 2
        }
    }

    context("select") {
        test("opens a menu and picks an option") {
            var selected by mutableStateOf(TODO)

            runComposeUiTest {
                setContent {
                    Themed {
                        NativeSelect(
                            selected = selected,
                            onSelectedChange = { selected = it },
                            modifier = Modifier.testTag(TAG),
                        ) {
                            option(value = TODO, label = TODO)
                            group(label = "Finished") {
                                option(value = DONE, label = DONE)
                                option(value = BLOCKED, label = BLOCKED, enabled = false)
                            }
                        }
                    }
                }

                val trigger = onNodeWithTag(TAG)
                trigger.assertHeightIsEqualTo(32.dp)
                trigger.performMouseInput { moveTo(center) }
                trigger.performClick()
                onNodeWithText("Finished").assertExists()
                val done = onNodeWithText(DONE)
                done.performMouseInput { moveTo(center) }
                done.performClick()
            }

            selected shouldBe DONE
        }

        test("centers its menu below the trigger") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            NativeSelect(
                                selected = TODO,
                                onSelectedChange = {},
                                modifier = Modifier.width(80.dp).testTag(TAG),
                            ) {
                                option(value = TODO, label = TODO)
                                option(value = DONE, label = LONG)
                            }
                        }
                    }
                }

                onNodeWithTag(TAG).performClick()
                waitForIdle()
                val trigger = onNodeWithTag(TAG).getBoundsInRoot()
                val row = onAllNodes(hasText(TODO) and hasClickAction())[1].getBoundsInRoot()
                val triggerCenter = (trigger.left + trigger.right) / 2
                val rowCenter = (row.left + row.right) / 2

                abs(triggerCenter.value - rowCenter.value) shouldBeLessThan 1f
            }
        }

        test("moves through the options with the arrow keys") {
            var selected by mutableStateOf(TODO)

            runComposeUiTest {
                setContent {
                    Themed {
                        NativeSelect(
                            selected = selected,
                            onSelectedChange = { selected = it },
                            modifier = Modifier.testTag(TAG),
                            size = NativeSelectSize.Sm,
                            isInvalid = true,
                        ) {
                            option(value = TODO, label = TODO)
                            option(value = DONE, label = DONE)
                        }
                    }
                }

                val trigger = onNodeWithTag(TAG)
                trigger.requestFocus()
                trigger.performKeyInput { pressKey(Key.DirectionDown) }
                trigger.performKeyInput { pressKey(Key.A) }
            }

            selected shouldBe DONE
        }

        test("chooses the highlighted option with the keyboard in the menu") {
            var selected by mutableStateOf(TODO)

            runComposeUiTest {
                setContent {
                    Themed {
                        NativeSelect(
                            selected = selected,
                            onSelectedChange = { selected = it },
                            modifier = Modifier.testTag(TAG),
                        ) {
                            option(value = TODO, label = TODO)
                            option(value = DONE, label = DONE)
                        }
                    }
                }

                onNodeWithTag(TAG).performClick()
                val menu = onNode(isFocused() and !hasClickAction())
                menu.performKeyInput { pressKey(Key.DirectionDown) }
                menu.performKeyInput { pressKey(Key.A) }
                menu.performKeyInput { pressKey(Key.Enter) }
            }

            selected shouldBe DONE
        }

        test("stays closed when it is disabled") {
            runComposeUiTest {
                setContent {
                    Themed {
                        NativeSelect(
                            selected = TODO,
                            onSelectedChange = {},
                            modifier = Modifier.testTag(TAG),
                            enabled = false,
                        ) {
                            option(value = TODO, label = TODO)
                        }
                    }
                }

                onNodeWithTag(TAG).performClick()
                onNodeWithText(TODO).assertExists()
            }
        }
    }
})
