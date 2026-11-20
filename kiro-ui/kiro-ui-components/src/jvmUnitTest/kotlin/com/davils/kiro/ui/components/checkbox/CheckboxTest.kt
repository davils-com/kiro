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

package com.davils.kiro.ui.components.checkbox

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalTestApi::class)
class CheckboxTest : FunSpec({
    val light = Theme.light()

    context("styles") {
        test("are size-4 rounded-[4px] and fill with the primary color when checked") {
            val style = CheckboxStyles.Default.resolve(light)

            style.size shouldBe 16.dp
            style.shape shouldBe RoundedCornerShape(4.dp)
            style.indicator.size shouldBe 14.dp
            style.colors.border.default shouldBe light.colors.input
            style.checkedColors.container.default shouldBe light.colors.primary
            style.checkedColors.border.default shouldBe light.colors.primary
            style.checkedColors.content.default shouldBe light.colors.primaryForeground
        }

        test("fill the unchecked box with the input color in dark mode") {
            val dark = Theme.dark()

            val style = CheckboxStyles.Default.resolve(dark)

            style.colors.container.default shouldBe dark.colors.input.copy(alpha = 0.3f)
        }
    }

    context("checkbox") {
        test("toggles when it is clicked") {
            runComposeUiTest {
                setContent {
                    Themed {
                        var isChecked by remember { mutableStateOf(false) }
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { isChecked = it },
                            modifier = Modifier.testTag(TAG),
                        )
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assertHeightIsEqualTo(16.dp)
                node.assertIsOff()
                node.performClick()
                node.assertIsOn()
                node.performClick()
                node.assertIsOff()
            }
        }

        test("shows an indeterminate state and can be disabled and invalid") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Checkbox(
                            checked = false,
                            onCheckedChange = {},
                            modifier = Modifier.testTag(TAG),
                            enabled = false,
                            isInvalid = true,
                            isIndeterminate = true,
                        )
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assert(
                    SemanticsMatcher.expectValue(SemanticsProperties.ToggleableState, ToggleableState.Indeterminate),
                )
                node.assertIsNotEnabled()
            }
        }
    }
})
