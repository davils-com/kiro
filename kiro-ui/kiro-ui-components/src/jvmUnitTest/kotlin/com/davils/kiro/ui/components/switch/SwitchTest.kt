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

package com.davils.kiro.ui.components.switch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalTestApi::class)
class SwitchTest : FunSpec({
    val light = Theme.light()
    val dark = Theme.dark()

    context("styles") {
        test("are 32 by 18.4 with a size-4 thumb on the input color") {
            val style = SwitchStyles.Default.resolve(light, SwitchSize.Default)

            style.width shouldBe 32.dp
            style.height shouldBe 18.4.dp
            style.thumb.size shouldBe 16.dp
            style.thumb.color shouldBe light.colors.background
            style.colors.container.default shouldBe light.colors.input
            style.checkedColors.container.default shouldBe light.colors.primary
        }

        test("shrink for the small size and color the thumb in dark mode") {
            val small = SwitchStyles.Default.resolve(dark, SwitchSize.Sm)

            small.width shouldBe 24.dp
            small.thumb.size shouldBe 12.dp
            small.thumb.color shouldBe dark.colors.foreground
            small.thumb.checkedColor shouldBe dark.colors.primaryForeground
            small.colors.container.default shouldBe dark.colors.input.copy(alpha = 0.8f)
        }

        test("outline the thumb in a dark tone in light mode and in the background in dark mode") {
            val bright = SwitchStyles.Default.resolve(light, SwitchSize.Default)
            val night = SwitchStyles.Default.resolve(dark, SwitchSize.Default)

            bright.thumb.border.width shouldBe 1.dp
            bright.thumb.border.color shouldBe light.colors.foreground.copy(alpha = 0.25f)
            night.thumb.border.color shouldBe dark.colors.background
        }

        test("need a style of their own for custom sizes") {
            shouldThrow<IllegalArgumentException> { SwitchStyles.Default.resolve(light, Unknown) }
        }
    }

    context("switch") {
        test("toggles when it is clicked") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        var isChecked by remember { mutableStateOf(false) }
                        Switch(
                            checked = isChecked,
                            onCheckedChange = { isChecked = it },
                            modifier = Modifier.testTag(TAG),
                        )
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assertWidthIsEqualTo(32.dp)
                node.assertIsOff()
                node.performClick()
                node.assertIsOn()
            }
        }

        test("can be small, disabled and invalid") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Switch(
                            checked = true,
                            onCheckedChange = {},
                            modifier = Modifier.testTag(TAG),
                            size = SwitchSize.Sm,
                            enabled = false,
                            isInvalid = true,
                        )
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assertHeightIsEqualTo(14.dp)
                node.assertIsNotEnabled()
            }
        }
    }
})
