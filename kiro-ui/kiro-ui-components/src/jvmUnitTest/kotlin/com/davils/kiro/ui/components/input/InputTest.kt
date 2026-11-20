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

package com.davils.kiro.ui.components.input

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.mix
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private const val PLACEHOLDER: String = "Email"

@OptIn(ExperimentalTestApi::class)
class InputTest : FunSpec({
    val light = Theme.light()
    val dark = Theme.dark()

    context("styles") {
        test("are h-8 rounded-lg px-2.5 py-1 with text-sm and a muted placeholder") {
            val style = InputStyles.Input.resolve(light)

            style.minHeight shouldBe 32.dp
            style.maxHeight shouldBe 32.dp
            style.shape shouldBe RoundedCornerShape(10.dp)
            style.padding shouldBe PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            style.text.textStyle.fontSize shouldBe 14.sp
            style.text.placeholderColor shouldBe light.colors.mutedForeground
            style.colors.border.default shouldBe light.colors.input
            style.colors.border.focusVisible shouldBe light.colors.ring
        }

        test("fill with the input color in dark mode") {
            val style = InputStyles.Input.resolve(dark)

            style.colors.container.default shouldBe dark.colors.input.copy(alpha = 0.3f)
        }

        test("look like the outline button with gray text when disabled") {
            val darkColors = InputStyles.Input.resolve(dark).colors
            val lightColors = InputStyles.Input.resolve(light).colors

            darkColors.container.disabled shouldBe dark.colors.input
            darkColors.border.disabled shouldBe dark.colors.input.copy(alpha = 0.3f)
            darkColors.content.disabled shouldBe dark.colors.mutedForeground
            lightColors.container.disabled shouldBe light.colors.muted.mix(light.colors.foreground, amount = 0.04f)
            lightColors.border.disabled shouldBe light.colors.border
            lightColors.content.disabled shouldBe light.colors.mutedForeground
        }

        test("do not dim a disabled field as a whole") {
            InputStyles.Input.resolve(light).disabledAlpha shouldBe 1f
        }

        test("let the textarea grow from min-h-16 with py-2") {
            val style = InputStyles.Textarea.resolve(light)

            style.minHeight shouldBe 64.dp
            style.maxHeight shouldBe Dp.Unspecified
            style.padding shouldBe PaddingValues(horizontal = 10.dp, vertical = 8.dp)
        }
    }

    context("input") {
        test("is 32 high, shows its placeholder and takes typed text") {
            runComposeUiTest {
                setContent {
                    Themed {
                        var value by remember { mutableStateOf("") }
                        Input(
                            value = value,
                            onValueChange = { value = it },
                            modifier = Modifier.testTag(TAG),
                            placeholder = PLACEHOLDER,
                        )
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assertHeightIsEqualTo(32.dp)
                onNodeWithText(PLACEHOLDER).assertExists()
                node.performMouseInput { moveTo(center) }
                node.performClick()
                node.assertIsFocused()
                node.performTextInput("nils@example.com")
                onNodeWithText(PLACEHOLDER).assertDoesNotExist()
                onNodeWithText("nils@example.com").assertExists()
            }
        }

        test("hides secure text and marks invalid and disabled fields") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Input(
                            value = "secret",
                            onValueChange = {},
                            modifier = Modifier.testTag(TAG),
                            enabled = false,
                            isInvalid = true,
                            options = InputOptions(isSecure = true, isReadOnly = true),
                        )
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assert(SemanticsMatcher.keyIsDefined(SemanticsProperties.Password))
                node.assertIsNotEnabled()
            }
        }
    }

    context("textarea") {
        test("grows with its content beyond the minimum height") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Textarea(
                            value = List(size = 6) { "line $it" }.joinToString(separator = "\n"),
                            onValueChange = {},
                            modifier = Modifier.testTag(TAG),
                            placeholder = PLACEHOLDER,
                        )
                    }
                }

                onNodeWithTag(TAG).getUnclippedBoundsInRoot().height shouldBeGreaterThan 64.dp
                onNodeWithText(PLACEHOLDER).assertDoesNotExist()
            }
        }

        test("keeps its minimum height when it is empty") {
            runComposeUiTest {
                setContent {
                    Themed { Textarea(value = "", onValueChange = {}, modifier = Modifier.testTag(TAG)) }
                }

                onNodeWithTag(TAG).assertHeightIsEqualTo(64.dp)
            }
        }
    }
})
