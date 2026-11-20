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

package com.davils.kiro.ui.components.select

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeLessThan
import io.kotest.matchers.shouldBe
import kotlin.math.abs

private const val APPLE: String = "Apple"
private const val BANANA: String = "Banana"
private const val PLACEHOLDER: String = "Select a fruit"
private const val LONG: String = "A much longer option than the trigger is wide"

@OptIn(ExperimentalTestApi::class)
class SelectTest : FunSpec({
    val light = Theme.light()

    context("styles") {
        test("are h-8 triggers with a muted chevron and shrink for sm") {
            SelectStyles.Default.resolve(light, SelectSize.Default).height shouldBe 32.dp
            val dark = Theme.dark()
            val small = SelectStyles.Default.resolve(light, SelectSize.Sm)
            val regular = SelectStyles.Default.resolve(light, SelectSize.Default)
            val night = SelectStyles.Default.resolve(dark, SelectSize.Default)

            small.height shouldBe 28.dp
            regular.iconColor shouldBe light.colors.mutedForeground
            night.colors.container.hovered shouldBe dark.colors.input.copy(alpha = 0.5f)
            shouldThrow<IllegalArgumentException> { SelectStyles.Default.resolve(light, Unknown) }
        }
    }

    context("select") {
        test("shows its placeholder and picks a clicked option") {
            var selected by mutableStateOf<String?>(null)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Select(
                            selected = selected,
                            onSelectedChange = { selected = it },
                            modifier = Modifier.testTag(TAG),
                        ) {
                            placeholder(PLACEHOLDER)
                            group(label = "Fruits") {
                                option(value = APPLE, label = APPLE)
                                option(value = BANANA, label = BANANA)
                            }
                            separator()
                            option(value = "Grapes", label = "Grapes", enabled = false)
                        }
                    }
                }

                onNodeWithText(PLACEHOLDER).assertExists()
                onNodeWithTag(TAG).performClick()
                waitForIdle()
                onNodeWithText(BANANA).performClick()
                waitForIdle()
                onNodeWithText(BANANA).assertExists()
            }

            selected shouldBe BANANA
        }

        test("fills the width it is given with its trigger") {
            val none: String? = null

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Select(selected = none, onSelectedChange = {}, modifier = Modifier.width(200.dp)) {
                            placeholder(PLACEHOLDER)
                            option(value = APPLE, label = APPLE)
                        }
                    }
                }

                val trigger = onNode(hasText(PLACEHOLDER) and hasClickAction()).getBoundsInRoot()

                trigger.right - trigger.left shouldBe 200.dp
            }
        }

        test("centers its menu below the trigger") {
            val none: String? = null

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Select(
                                selected = none,
                                onSelectedChange = {},
                                modifier = Modifier.width(80.dp).testTag(TAG),
                            ) {
                                placeholder(PLACEHOLDER)
                                option(value = APPLE, label = LONG)
                            }
                        }
                    }
                }

                onNodeWithTag(TAG).performClick()
                waitForIdle()
                val trigger = onNodeWithTag(TAG).getBoundsInRoot()
                val row = onNode(hasText(LONG) and isSelectable()).getBoundsInRoot()
                val triggerCenter = (trigger.left + trigger.right) / 2
                val rowCenter = (row.left + row.right) / 2

                abs(triggerCenter.value - rowCenter.value) shouldBeLessThan 1f
            }
        }

        test("opens with the arrow keys on the selected option") {
            var selected by mutableStateOf<String?>(APPLE)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Select(
                            selected = selected,
                            onSelectedChange = { selected = it },
                            modifier = Modifier.testTag(TAG),
                            size = SelectSize.Sm,
                            isInvalid = true,
                        ) {
                            option(value = APPLE, label = APPLE)
                            option(value = BANANA, label = BANANA)
                        }
                    }
                }

                val trigger = onNodeWithText(APPLE)
                trigger.requestFocus()
                trigger.performKeyInput { pressKey(Key.A) }
                trigger.performKeyInput { pressKey(Key.DirectionDown) }
                waitForIdle()
                val menu = onNode(isFocused() and hasAnyDescendant(hasText(BANANA)))
                menu.performKeyInput { pressKey(Key.DirectionDown) }
                menu.performKeyInput { pressKey(Key.Enter) }
                waitForIdle()
            }

            selected shouldBe BANANA
        }
    }
})
