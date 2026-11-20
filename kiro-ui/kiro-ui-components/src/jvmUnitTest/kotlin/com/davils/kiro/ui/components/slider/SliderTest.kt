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

package com.davils.kiro.ui.components.slider

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.click
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val OTHER: String = "other"
private const val STEPPED: Float = 15f
private const val BETWEEN_STEPS: Float = 42f
private const val SNAPPED: Float = 40f
private const val FULL: Float = 100f
private const val HALF: Float = 50f
private const val LOWER: Float = 20f
private const val UPPER: Float = 80f

private val hasProgress = SemanticsMatcher.keyIsDefined(SemanticsActions.SetProgress)

@OptIn(ExperimentalTestApi::class)
class SliderTest : FunSpec({
    context("styles") {
        test("are an h-1 muted track with a size-3 white thumb ringed in the ring color") {
            val light = Theme.light()
            val style = SliderStyles.Default.resolve(light)

            style.trackThickness shouldBe 4.dp
            style.trackColor shouldBe light.colors.muted
            style.rangeColor shouldBe light.colors.primary
            style.thumbSize shouldBe 12.dp
            style.thumbBorder.color shouldBe light.colors.ring
            style.minLength shouldBe 160.dp
        }
    }

    context("slider") {
        test("jumps to the pointer and follows a drag") {
            var value by mutableFloatStateOf(0f)

            runComposeUiTest {
                setContent {
                    Themed {
                        Slider(
                            value = value,
                            onValueChange = { value = it },
                            modifier = Modifier.width(112.dp).testTag(TAG),
                        )
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assertHeightIsEqualTo(12.dp)
                node.performMouseInput { click(Offset(x = width / 2f, y = centerY)) }
                value shouldBe HALF
                node.performMouseInput {
                    moveTo(Offset(x = width / 2f, y = centerY))
                    press()
                    moveTo(Offset(x = width.toFloat(), y = centerY))
                    release()
                }
                value shouldBe FULL
            }
        }

        test("steps with the keyboard and through the progress action") {
            var value by mutableFloatStateOf(10f)

            runComposeUiTest {
                setContent {
                    Themed { Slider(value = value, onValueChange = { value = it }, step = 5f) }
                }

                val thumb = onAllNodes(hasProgress)[0]
                thumb.requestFocus()
                thumb.performKeyInput { pressKey(Key.DirectionRight) }
                value shouldBe STEPPED
                thumb.performKeyInput { pressKey(Key.A) }
                thumb.performSemanticsAction(SemanticsActions.SetProgress) { action -> action(BETWEEN_STEPS) }
                value shouldBe SNAPPED
            }
        }

        test("runs bottom to top and right to left") {
            var value by mutableFloatStateOf(0f)

            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            Slider(
                                value = value,
                                onValueChange = { value = it },
                                modifier = Modifier.height(200.dp).testTag(TAG),
                                orientation = Orientation.Vertical,
                            )
                            Direction(direction = LayoutDirection.Rtl) {
                                Slider(value = 30f, onValueChange = {}, modifier = Modifier.width(100.dp))
                            }
                        }
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assertHeightIsEqualTo(200.dp)
                node.assertWidthIsEqualTo(12.dp)
                node.performMouseInput { click(Offset(x = centerX, y = 6f)) }
                value shouldBe FULL
            }
        }

        test("falls back to min-h-40 without a bounded length") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                                Slider(value = 0f, onValueChange = {}, modifier = Modifier.testTag(TAG))
                            }
                            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                Slider(
                                    value = 0f,
                                    onValueChange = {},
                                    modifier = Modifier.testTag(OTHER),
                                    orientation = Orientation.Vertical,
                                )
                            }
                        }
                    }
                }

                onNodeWithTag(TAG).assertWidthIsEqualTo(160.dp)
                onNodeWithTag(OTHER).assertHeightIsEqualTo(160.dp)
            }
        }

        test("ignores the pointer when it is disabled") {
            var value by mutableFloatStateOf(0f)

            runComposeUiTest {
                setContent {
                    Themed {
                        Slider(
                            value = value,
                            onValueChange = { value = it },
                            modifier = Modifier.testTag(TAG),
                            enabled = false,
                        )
                    }
                }

                onNodeWithTag(TAG).performMouseInput { click(center) }
                value shouldBe 0f
            }
        }
    }

    context("range slider") {
        test("moves the thumb nearest to the pointer") {
            var range by mutableStateOf(LOWER..UPPER)

            runComposeUiTest {
                setContent {
                    Themed {
                        RangeSlider(
                            value = range,
                            onValueChange = { range = it },
                            modifier = Modifier.width(112.dp).testTag(TAG),
                        )
                    }
                }

                onNodeWithTag(TAG).performMouseInput { click(Offset(x = 106f, y = centerY)) }
                range shouldBe LOWER..FULL
            }
        }
    }
})
