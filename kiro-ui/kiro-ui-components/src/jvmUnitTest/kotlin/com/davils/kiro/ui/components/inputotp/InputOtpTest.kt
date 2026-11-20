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

package com.davils.kiro.ui.components.inputotp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.seconds

private val FIRST_HALF: IntRange = 0..2
private val SECOND_HALF: IntRange = 3..5

@OptIn(ExperimentalTestApi::class)
class InputOtpTest : FunSpec({
    context("styles") {
        test("swap the fill and the border of the slots in dark mode") {
            val dark = Theme.dark()
            val colors = InputOtpStyles.Default.resolve(dark).colors

            colors.container.default shouldBe dark.colors.input
            colors.border.default shouldBe dark.colors.input.copy(alpha = 0.3f)
        }

        test("are size-8 slots joined into rounded-lg groups with a blinking caret") {
            val light = Theme.light()
            val style = InputOtpStyles.Default.resolve(light)

            style.slotSize shouldBe 32.dp
            style.radius shouldBe 10.dp
            style.colors.border.default shouldBe light.colors.input
            style.invalidRing shouldBe light.colors.destructive.copy(alpha = 0.2f)
            style.caret.period shouldBe 1.seconds
            val still = InputOtpStyles.Default.resolve(reducedMotion)
            still.caret.period shouldBe null
        }
    }

    context("input otp") {
        test("fills its slots and stops at the length") {
            var code = ""

            runComposeUiTest {
                setContent {
                    Themed {
                        var value by remember { mutableStateOf("") }
                        InputOtp(
                            value = value,
                            onValueChange = { typed ->
                                value = typed
                                code = typed
                            },
                            length = 6,
                            modifier = Modifier.testTag(TAG),
                        ) {
                            group(FIRST_HALF)
                            separator()
                            group(SECOND_HALF)
                        }
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assertHeightIsEqualTo(32.dp)
                node.performClick()
                waitForIdle()
                node.performTextInput("1234567")
                onNodeWithText("4").assertExists()
            }

            code shouldBe "123456"
        }

        test("shows a steady caret without motion and can be disabled and invalid") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        InputOtp(
                            value = "1",
                            onValueChange = {},
                            length = 4,
                            modifier = Modifier.testTag(TAG),
                            isInvalid = true,
                        )
                        InputOtp(value = "", onValueChange = {}, length = 4, enabled = false)
                    }
                }

                onNodeWithTag(TAG).performClick()
                onNodeWithText("1").assertExists()
            }
        }
    }
})
