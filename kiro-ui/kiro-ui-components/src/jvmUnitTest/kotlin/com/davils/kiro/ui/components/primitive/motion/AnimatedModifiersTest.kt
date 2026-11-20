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

package com.davils.kiro.ui.components.primitive.motion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val WIDTH = 64.dp
private val HEIGHT = 32.dp
private const val HALF: Float = 0.5f
private const val QUARTER_TURN: Float = 90f

@OptIn(ExperimentalTestApi::class)
class AnimatedModifiersTest : FunSpec({
    context("size") {
        test("follows the width and the height without composing again") {
            val width = mutableStateOf(40.dp)
            val height = mutableStateOf(20.dp)
            var compositions = 0
            runComposeUiTest {
                setContent {
                    SideEffect { compositions++ }
                    Box(modifier = Modifier.testTag(TAG).animatedWidth(width).animatedHeight(height)) {
                        Box(modifier = Modifier.size(100.dp))
                    }
                }
                width.value = WIDTH
                height.value = HEIGHT
                waitForIdle()

                val node = onNodeWithTag(TAG)
                node.assertWidthIsEqualTo(WIDTH)
                node.assertHeightIsEqualTo(HEIGHT)
                compositions shouldBe 1
            }
        }
    }

    context("layer") {
        test("fades and turns without composing again") {
            val alpha = mutableFloatStateOf(1f)
            val turn = mutableFloatStateOf(0f)
            var compositions = 0
            runComposeUiTest {
                setContent {
                    SideEffect { compositions++ }
                    Box(
                        modifier = Modifier
                            .testTag(TAG)
                            .size(10.dp)
                            .fadeBy(alpha)
                            .turnBy(turn),
                    )
                }
                alpha.floatValue = HALF
                turn.floatValue = QUARTER_TURN
                waitForIdle()

                compositions shouldBe 1
            }
        }
    }
})
