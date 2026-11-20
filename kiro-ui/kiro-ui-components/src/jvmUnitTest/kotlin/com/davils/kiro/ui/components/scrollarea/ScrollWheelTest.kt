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

package com.davils.kiro.ui.components.scrollarea

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.ScrollWheel
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private const val NOTCHES: Float = 3f
private const val ROWS: Int = 100
private val VIEWPORT = 120.dp
private val LONG = 1000.dp
private val SHORT = 100.dp

@OptIn(ExperimentalTestApi::class)
private fun offsetsAfterWheel(direction: ScrollDirection, width: Dp, height: Dp): Pair<Int, Int> {
    val vertical = ScrollState(0)
    val horizontal = ScrollState(0)
    runComposeUiTest {
        setContent {
            Themed {
                ScrollArea(
                    modifier = Modifier.size(VIEWPORT).testTag(TAG),
                    direction = direction,
                    verticalState = vertical,
                    horizontalState = horizontal,
                ) { Box(modifier = Modifier.size(width = width, height = height)) }
            }
        }
        onNodeWithTag(TAG).performMouseInput {
            moveTo(center)
            scroll(delta = NOTCHES, scrollWheel = ScrollWheel.Vertical)
        }
        waitForIdle()
    }
    return vertical.value to horizontal.value
}

@OptIn(ExperimentalTestApi::class)
class ScrollWheelTest : FunSpec({
    context("scroll area") {
        test("scrolls horizontally with the wheel when horizontal is the only direction") {
            val offsets = offsetsAfterWheel(ScrollDirection.Horizontal, width = LONG, height = SHORT)

            offsets.first shouldBe 0
            offsets.second shouldBeGreaterThan 0
        }

        test("scrolls vertically with the wheel when both directions can scroll") {
            val offsets = offsetsAfterWheel(ScrollDirection.Both, width = LONG, height = LONG)

            offsets.first shouldBeGreaterThan 0
            offsets.second shouldBe 0
        }

        test("scrolls horizontally with the wheel when only the horizontal direction overflows") {
            val offsets = offsetsAfterWheel(ScrollDirection.Both, width = LONG, height = SHORT)

            offsets.first shouldBe 0
            offsets.second shouldBeGreaterThan 0
        }

        test("scrolls vertically with the wheel when vertical is the only direction") {
            val offsets = offsetsAfterWheel(ScrollDirection.Vertical, width = SHORT, height = LONG)

            offsets.first shouldBeGreaterThan 0
            offsets.second shouldBe 0
        }
    }

    context("lazy scroll area") {
        test("scrolls a horizontal list with the wheel") {
            val state = LazyListState()

            runComposeUiTest {
                setContent {
                    Themed {
                        LazyScrollArea(
                            modifier = Modifier.size(VIEWPORT).testTag(TAG),
                            state = state,
                            orientation = Orientation.Horizontal,
                        ) {
                            items(count = ROWS) { row -> Text("Item $row") }
                        }
                    }
                }
                onNodeWithTag(TAG).performMouseInput {
                    moveTo(Offset(x = 10f, y = 10f))
                    scroll(delta = NOTCHES, scrollWheel = ScrollWheel.Vertical)
                }
                waitForIdle()
            }

            state.firstVisibleItemIndex + state.firstVisibleItemScrollOffset shouldBeGreaterThan 0
        }
    }
})
