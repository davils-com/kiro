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

package com.davils.kiro.ui.core.focus

import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.click
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val TAG: String = "target"

@Composable
private fun FocusedTarget(onFocusVisible: (Boolean) -> Unit) {
    val source = remember { MutableInteractionSource() }
    val requester = remember { FocusRequester() }
    val isFocusVisible by source.collectIsFocusVisibleAsState()
    onFocusVisible(isFocusVisible)
    Box(
        modifier = Modifier
            .size(10.dp)
            .testTag(TAG)
            .focusRequester(requester)
            .focusable(interactionSource = source),
    )
    LaunchedEffect(requester) { requester.requestFocus() }
}

@OptIn(ExperimentalTestApi::class)
class InputModalityTest : FunSpec({
    context("modality") {
        test("starts with the pointer and follows the last input") {
            val modality = InputModality()

            modality.isKeyboard shouldBe false
            modality.onKeyboard()
            modality.isKeyboard shouldBe true
            modality.onPointer()
            modality.isKeyboard shouldBe false
        }
    }

    context("focus visible") {
        test("shows focus once the keyboard is used and hides it after a pointer press") {
            var isFocusVisible = false

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = Theme.light()) { FocusedTarget { visible -> isFocusVisible = visible } }
                }
                waitForIdle()
                isFocusVisible shouldBe false

                onNodeWithTag(TAG).performKeyInput { pressKey(Key.A) }
                waitForIdle()
                isFocusVisible shouldBe true

                onNodeWithTag(TAG).performMouseInput { click() }
                waitForIdle()
                isFocusVisible shouldBe false
            }
        }

        test("follows the focus alone outside a theme") {
            var isFocusVisible = false

            runComposeUiTest {
                setContent { FocusedTarget { visible -> isFocusVisible = visible } }
                waitForIdle()

                isFocusVisible shouldBe true
            }
        }

        test("is tracked once for nested themes") {
            var outer: InputModality? = null
            var inner: InputModality? = null

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = Theme.light()) {
                        outer = LocalInputModality.current
                        ThemeProvider(theme = Theme.dark()) { inner = LocalInputModality.current }
                    }
                }
            }

            (inner === outer) shouldBe true
        }
    }
})
