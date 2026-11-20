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

package com.davils.kiro.ui.components.button

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.click
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.width
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Plus
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val LABEL: String = "Save"

@OptIn(ExperimentalTestApi::class)
class ButtonTest : FunSpec({
    context("clicks") {
        test("calls onClick and describes itself as a button") {
            var clicks = 0

            runComposeUiTest {
                setContent {
                    Themed { Button(onClick = { clicks++ }, modifier = Modifier.testTag(TAG)) { Text(LABEL) } }
                }

                val node = onNodeWithTag(TAG).performClick()
                node.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))
            }

            clicks shouldBe 1
        }

        test("ignores clicks while disabled") {
            var clicks = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        Button(
                            onClick = { clicks++ },
                            modifier = Modifier.testTag(TAG),
                            enabled = false,
                        ) { Text(LABEL) }
                    }
                }

                val node = onNodeWithTag(TAG).assertIsNotEnabled()
                node.performClick()
            }

            clicks shouldBe 0
        }
    }

    context("layout") {
        test("is 32 dp high by default") {
            runComposeUiTest {
                setContent { Themed { Button(onClick = {}, modifier = Modifier.testTag(TAG)) { Text(LABEL) } } }

                onNodeWithTag(TAG).assertHeightIsEqualTo(32.dp)
            }
        }

        test("pulls a leading or trailing icon two dp into the padding like data-icon of the website") {
            val widths = mutableMapOf<IconPlacement, Float>()

            runComposeUiTest {
                setContent {
                    Themed {
                        IconPlacement.entries.forEach { placement ->
                            Button(onClick = {}, modifier = Modifier.testTag(placement.name)) {
                                Icon(icon = Lucide.Plus, placement = placement)
                                Text(LABEL)
                            }
                        }
                    }
                }

                IconPlacement.entries.forEach { placement ->
                    val bounds = onNodeWithTag(placement.name).getUnclippedBoundsInRoot()
                    widths[placement] = bounds.width.value
                }
            }

            val inline = widths.getValue(IconPlacement.Inline)
            inline - widths.getValue(IconPlacement.Leading) shouldBe 2f
            inline - widths.getValue(IconPlacement.Trailing) shouldBe 2f
        }
    }

    context("content") {
        test("colors its content with the content color of the variant") {
            var color = Color.Unspecified

            runComposeUiTest {
                setContent { Themed { Button(onClick = {}) { color = LocalContentColor.current } } }
            }

            color shouldBe Theme.light().colors.primaryForeground
        }
    }

    context("interaction") {
        test("reacts to hover, press and keyboard focus") {
            var clicks = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        Button(
                            onClick = { clicks++ },
                            modifier = Modifier.testTag(TAG),
                            variant = ButtonVariant.Link,
                        ) {
                            Text(LABEL)
                        }
                    }
                }

                val node = onNodeWithTag(TAG)
                node.performMouseInput { moveTo(center) }
                node.performMouseInput { click() }
                node.performKeyInput { pressKey(Key.Enter) }
                waitForIdle()
            }

            clicks shouldBe 2
        }
    }
})
