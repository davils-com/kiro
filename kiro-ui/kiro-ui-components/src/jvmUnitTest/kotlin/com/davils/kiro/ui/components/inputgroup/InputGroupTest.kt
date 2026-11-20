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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val ADDON: String = "addon"
private const val COPY: String = "copy"
private const val HEADER: String = "Header"
private const val FOOTER: String = "Footer"
private const val PULLED_LEFT: Float = 4.2f
private const val HALF_PIXEL: Float = 0.6f

@OptIn(ExperimentalTestApi::class)
class InputGroupTest : FunSpec({
    val light = Theme.light()

    fun button(size: InputGroupButtonSize) =
        InputGroupButtonStyles.Default.resolve(theme = light, variant = ButtonVariant.Ghost, size = size)

    context("styles") {
        test("frame the group like an input and pad addons by 2 and controls by 1.5 next to them") {
            val style = InputGroupStyles.Default.resolve(light)

            style.minHeight shouldBe 32.dp
            style.shape shouldBe RoundedCornerShape(10.dp)
            style.colors.border.default shouldBe light.colors.input
            style.addon.inlinePadding shouldBe 8.dp
            style.addon.color shouldBe light.colors.mutedForeground
            style.addon.textStyle.fontSize shouldBe 14.sp
            style.control.inlinePadding shouldBe 6.dp
            style.control.blockPadding shouldBe 12.dp
        }

        test("size the buttons like h-6, size-6 and size-8") {
            button(InputGroupButtonSize.Xs).height shouldBe 24.dp
            button(InputGroupButtonSize.Xs).shape shouldBe RoundedCornerShape(7.dp)
            button(InputGroupButtonSize.Xs).iconSize shouldBe 14.dp
            button(InputGroupButtonSize.Sm).height shouldBe 32.dp
            button(InputGroupButtonSize.Sm).gap shouldBe 8.dp
            button(InputGroupButtonSize.IconXs).minWidth shouldBe 24.dp
            button(InputGroupButtonSize.IconSm).minWidth shouldBe 32.dp
        }

        test("need a style of their own for custom button sizes") {
            shouldThrow<IllegalArgumentException> { button(Unknown) }
        }
    }

    context("scope") {
        test("needs exactly one control") {
            shouldThrow<IllegalArgumentException> { InputGroupScopeImpl().control }
            shouldThrow<IllegalStateException> {
                InputGroupScopeImpl().apply {
                    input(value = "", onValueChange = {})
                    textarea(value = "", onValueChange = {})
                }
            }
        }
    }

    context("group") {
        test("lays out inline addons around the input and focuses it from an addon") {
            var copies = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        var value by remember { mutableStateOf("") }
                        InputGroup(modifier = Modifier.testTag(TAG)) {
                            addon { InputGroupText(modifier = Modifier.testTag(ADDON)) { Text("https://") } }
                            input(value = value, onValueChange = { value = it }, placeholder = "example.com")
                            addon(align = InputGroupAlign.InlineEnd) {
                                InputGroupButton(onClick = { copies++ }, modifier = Modifier.testTag(COPY)) {
                                    Text("Copy")
                                }
                            }
                        }
                    }
                }

                onNodeWithTag(TAG).assertHeightIsEqualTo(32.dp)
                onNodeWithTag(ADDON).performClick()
                val field = onNode(hasSetTextAction())
                field.assertIsFocused()
                field.performTextInput("kiro.dev")
                onNodeWithText("kiro.dev").assertExists()
                onNodeWithTag(COPY).performClick()
            }

            copies shouldBe 1
        }

        test("pulls a button in an inline addon towards the edge") {
            runComposeUiTest {
                setContent {
                    Themed {
                        InputGroup(modifier = Modifier.testTag(TAG)) {
                            addon {
                                InputGroupButton(
                                    onClick = {},
                                    modifier = Modifier.testTag(COPY),
                                    size = InputGroupButtonSize.IconXs,
                                ) {
                                    Text("A")
                                }
                            }
                            input(value = "", onValueChange = {})
                        }
                    }
                }

                val group = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
                val button = onNodeWithTag(COPY).getUnclippedBoundsInRoot()
                (button.left - group.left).value shouldBe (PULLED_LEFT plusOrMinus HALF_PIXEL)
            }
        }

        test("stacks block addons above and below a textarea") {
            runComposeUiTest {
                setContent {
                    Themed {
                        InputGroup(modifier = Modifier.testTag(TAG), enabled = false, isInvalid = true) {
                            addon(align = InputGroupAlign.BlockStart) { Text(HEADER) }
                            textarea(value = "", onValueChange = {}, placeholder = "Message")
                            addon(align = InputGroupAlign.BlockEnd) { Text(FOOTER) }
                        }
                    }
                }

                val bounds = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
                bounds.bottom - bounds.top shouldBeGreaterThan 64.dp
                onNodeWithText(HEADER).assertExists()
                onNodeWithText("Message").assertExists()
            }
        }

        test("pads the input towards block addons") {
            runComposeUiTest {
                setContent {
                    Themed {
                        InputGroup(modifier = Modifier.testTag(TAG)) {
                            addon(align = InputGroupAlign.BlockStart) { Text(HEADER) }
                            input(value = "", onValueChange = {})
                            addon(align = InputGroupAlign.BlockEnd) { Text(FOOTER) }
                        }
                    }
                }

                onNodeWithText(FOOTER).assertExists()
            }
        }
    }
})
