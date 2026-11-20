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

package com.davils.kiro.ui.components.resizable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private const val HALF: Float = 50f
private const val STEP: Float = 10f
private const val FULL: Float = 100f
private const val MOST: Float = 70f
private const val LEAST: Float = 10f
private const val DRAG: Float = 100f
private const val QUARTER: Float = 25f
private const val LEFT: String = "left"
private const val RIGHT: String = "right"

@OptIn(ExperimentalTestApi::class)
class ResizableTest : FunSpec({
    context("math") {
        test("moves the border between two panels within their limits") {
            val limits = listOf(LEAST..FULL - LEAST, FULL - MOST..FULL)

            resized(
                sizes = listOf(HALF, HALF),
                handle = 0,
                delta = STEP,
                limits = limits,
            ) shouldBe listOf(HALF + STEP, HALF - STEP)
            resized(
                sizes = listOf(HALF, HALF),
                handle = 0,
                delta = HALF,
                limits = limits,
            ) shouldBe listOf(MOST, FULL - MOST)
            resized(
                sizes = listOf(HALF, HALF),
                handle = 0,
                delta = -HALF,
                limits = limits,
            ) shouldBe listOf(LEAST, FULL - LEAST)
        }
    }

    context("state") {
        test("needs sizes that add up to a hundred percent") {
            shouldThrow<IllegalArgumentException> { ResizableState(listOf(HALF)) }
            shouldThrow<IllegalArgumentException> { ResizableState(emptyList()) }
        }

        test("styles draw a 1 dp border-colored handle with a 4 dp hit area") {
            val light = Theme.light()
            val style = ResizableStyles.Default.resolve(light)

            style.thickness shouldBe 1.dp
            style.hitArea shouldBe 4.dp
            style.color shouldBe light.colors.border
        }
    }

    context("group") {
        test("splits its width by the sizes and resizes by dragging and with the keyboard") {
            val state = ResizableState(listOf(HALF, HALF))

            runComposeUiTest {
                setContent {
                    Themed {
                        ResizablePanelGroup(
                            state = state,
                            modifier = Modifier.size(width = 201.dp, height = 100.dp),
                            isGripShown = true,
                        ) {
                            panel(minSize = QUARTER) { Text(LEFT, modifier = Modifier.testTag(LEFT)) }
                            panel { Text(RIGHT, modifier = Modifier.testTag(RIGHT)) }
                        }
                    }
                }

                val handle = onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsProperties.Focused))[0]
                handle.performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = DRAG, y = 0f))
                    release()
                }
                handle.requestFocus()
                handle.performKeyInput { pressKey(Key.DirectionLeft) }
                handle.performKeyInput { pressKey(Key.A) }
            }

            state.sizes.first() shouldBeGreaterThan HALF - STEP
        }

        test("stacks its panels and mirrors dragging from right to left") {
            val vertical = ResizableState(listOf(HALF, HALF))
            val mirrored = ResizableState(listOf(HALF, HALF))

            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            ResizablePanelGroup(
                                state = vertical,
                                modifier = Modifier.size(width = 100.dp, height = 101.dp),
                                orientation = Orientation.Vertical,
                                isGripShown = true,
                            ) {
                                panel { Text("top") }
                                panel { Text("bottom") }
                            }
                            Direction(direction = LayoutDirection.Rtl) {
                                ResizablePanelGroup(
                                    state = mirrored,
                                    modifier = Modifier.size(width = 101.dp, height = 40.dp),
                                ) {
                                    panel { Text("start", modifier = Modifier.testTag(LEFT)) }
                                    panel { Text("end") }
                                }
                            }
                        }
                    }
                }

                val handles = onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsProperties.Focused))
                handles[0].requestFocus()
                handles[0].performKeyInput { pressKey(Key.DirectionDown) }
                handles[1].requestFocus()
                handles[1].performKeyInput { pressKey(Key.DirectionRight) }
            }

            vertical.sizes shouldBe listOf(HALF + STEP, HALF - STEP)
            mirrored.sizes shouldBe listOf(HALF - STEP, HALF + STEP)
        }

        test("needs one size per panel") {
            shouldThrow<IllegalArgumentException> {
                runComposeUiTest {
                    setContent {
                        Themed {
                            ResizablePanelGroup(state = ResizableState(listOf(FULL))) {
                                panel { Text(LEFT) }
                                panel { Text(RIGHT) }
                            }
                        }
                    }
                }
            }
        }
    }
})
