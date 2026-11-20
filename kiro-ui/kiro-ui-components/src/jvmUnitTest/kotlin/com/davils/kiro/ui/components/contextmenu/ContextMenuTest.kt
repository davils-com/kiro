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

package com.davils.kiro.ui.components.contextmenu

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.rightClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeLessThan
import io.kotest.matchers.shouldBe
import kotlin.math.abs

private const val BACK: String = "Back"
private const val AREA: String = "Right click here"
private const val MAIN: String = "Main"
private const val MIDDLE: String = "Middle"
private const val DEEPER: String = "Deeper"
private const val LEAF: String = "Leaf"
private const val POLL_MILLIS: Long = 8L
private const val MAX_POLLS: Int = 60
private const val FAR: Float = 200f
private const val SETTLE_MILLIS: Long = 500L
private const val MAX_DRIFT: Float = 12f

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.advanceUntilGone(text: String) {
    repeat(MAX_POLLS) {
        val remaining = onAllNodesWithText(text).fetchSemanticsNodes()
        if (remaining.isEmpty()) return
        mainClock.advanceTimeBy(POLL_MILLIS)
    }
}

@OptIn(ExperimentalTestApi::class)
class ContextMenuTest : FunSpec({
    context("context menu") {
        test("opens where the area was right-clicked") {
            var clicked = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        ContextMenu(
                            menu = { item(label = BACK, onClick = { clicked = BACK }) },
                            modifier = Modifier.size(200.dp).testTag(TAG),
                        ) {
                            Text(AREA)
                        }
                    }
                }

                onNodeWithText(BACK).assertDoesNotExist()
                onNodeWithTag(TAG).performMouseInput { rightClick(center) }
                waitForIdle()
                onNodeWithText(BACK).performClick()
                waitForIdle()
                onNodeWithText(BACK).assertDoesNotExist()
            }

            clicked shouldBe BACK
        }

        test("stays where it was opened while it fades out") {
            runComposeUiTest {
                mainClock.autoAdvance = false
                setContent {
                    Themed {
                        ContextMenu(
                            menu = { item(label = BACK, onClick = {}) },
                            modifier = Modifier.size(300.dp).testTag(TAG),
                        ) {
                            Text(AREA)
                        }
                    }
                }

                onNodeWithTag(TAG).performMouseInput { rightClick(Offset(x = FAR, y = FAR)) }
                mainClock.advanceTimeBy(SETTLE_MILLIS)
                val opened = onNodeWithText(BACK).getBoundsInRoot()
                onNodeWithText(BACK).performClick()
                mainClock.advanceTimeByFrame()
                val closing = onNodeWithText(BACK).getBoundsInRoot()

                abs(closing.left.value - opened.left.value) shouldBeLessThan MAX_DRIFT
                abs(closing.top.value - opened.top.value) shouldBeLessThan MAX_DRIFT
            }
        }

        test("closes the deepest sub menu first and the main menu last") {
            runComposeUiTest {
                mainClock.autoAdvance = false
                setContent {
                    Themed {
                        ContextMenu(
                            menu = {
                                item(label = MAIN, onClick = {})
                                sub(label = MIDDLE) {
                                    sub(label = DEEPER) { item(label = LEAF, onClick = {}) }
                                }
                            },
                            modifier = Modifier.size(300.dp).testTag(TAG),
                        ) {
                            Text(AREA)
                        }
                    }
                }

                onNodeWithTag(TAG).performMouseInput { rightClick(Offset(x = FAR, y = FAR)) }
                mainClock.advanceTimeBy(SETTLE_MILLIS)
                onNodeWithText(MIDDLE).performClick()
                mainClock.advanceTimeBy(SETTLE_MILLIS)
                onNodeWithText(DEEPER).performClick()
                mainClock.advanceTimeBy(SETTLE_MILLIS)
                onNodeWithText(LEAF).performClick()

                advanceUntilGone(LEAF)
                onNodeWithText(DEEPER).assertExists()
                onNodeWithText(MAIN).assertExists()

                advanceUntilGone(DEEPER)
                onNodeWithText(MAIN).assertExists()

                advanceUntilGone(MAIN)
            }
        }
    }
})
