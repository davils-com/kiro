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

package com.davils.kiro.ui.components.resizable.junction

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.resizable.ResizablePanelGroup
import com.davils.kiro.ui.components.resizable.ResizableState
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.floats.shouldBeLessThan

private const val HALF: Float = 50f
private const val DRAG: Float = 80f
private const val JUNCTION: String = "Resize in both directions"

@OptIn(ExperimentalTestApi::class)
class JunctionGripTest : FunSpec({
    context("junction") {
        test("resizes the outer and both inner groups at once and moves with the arrow keys") {
            val columns = ResizableState(listOf(HALF, HALF))
            val left = ResizableState(listOf(HALF, HALF))
            val right = ResizableState(listOf(HALF, HALF))

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        ResizablePanelGroup(state = columns, modifier = Modifier.size(400.dp), isGripShown = true) {
                            panel {
                                ResizablePanelGroup(state = left, orientation = Orientation.Vertical) {
                                    panel { Text("Tree") }
                                    panel { Text("Outline") }
                                }
                            }
                            panel {
                                ResizablePanelGroup(state = right, orientation = Orientation.Vertical) {
                                    panel { Text("Editor") }
                                    panel { Text("Terminal") }
                                }
                            }
                        }
                    }
                }

                val grip = onNodeWithContentDescription(JUNCTION)
                grip.performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = DRAG, y = DRAG))
                    release()
                }
                waitForIdle()
                columns.sizes.first() shouldBeGreaterThan HALF
                left.sizes.first() shouldBeGreaterThan HALF
                right.sizes.first() shouldBeGreaterThan HALF
                val moved = columns.sizes.first()
                grip.requestFocus()
                grip.performKeyInput { pressKey(Key.DirectionLeft) }
                grip.performKeyInput { pressKey(Key.DirectionUp) }
                waitForIdle()
                columns.sizes.first() shouldBeLessThan moved
            }
        }
    }
})
