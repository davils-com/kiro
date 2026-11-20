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

package com.davils.kiro.ui.components.primitive.overlay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe

private const val ANCHOR: String = "anchor"
private const val PANEL: String = "panel"
private const val SETTLE: Long = 1_000L
private const val FRAME: Long = 16L
private const val FRAMES: Int = 20

@OptIn(ExperimentalTestApi::class)
class OverlayPopupTest : FunSpec({
    context("opening") {
        test("starts its content afresh when it reopens while it is still closing") {
            var isOpen by mutableStateOf(true)
            var created = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        Box {
                            OverlayPopup(
                                isOpen = isOpen,
                                placement = Placement(),
                                isFocusable = false,
                                onDismiss = {},
                            ) {
                                remember { created += 1 }
                                Box(modifier = Modifier.size(10.dp))
                            }
                        }
                    }
                }

                waitForIdle()
                mainClock.autoAdvance = false
                isOpen = false
                mainClock.advanceTimeBy(FRAME)
                isOpen = true
                mainClock.autoAdvance = true
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
            }

            created shouldBe 2
        }

        test("never slides across its anchor while it enters") {
            var isOpen by mutableStateOf(false)

            runComposeUiTest {
                setContent {
                    Themed {
                        Box(modifier = Modifier.size(80.dp).testTag(ANCHOR)) {
                            OverlayPopup(
                                isOpen = isOpen,
                                placement = Placement(),
                                isFocusable = false,
                                onDismiss = {},
                            ) {
                                Box(modifier = Modifier.size(40.dp).testTag(PANEL))
                            }
                        }
                    }
                }

                waitForIdle()
                val anchor = onNodeWithTag(ANCHOR).fetchSemanticsNode().boundsInWindow
                mainClock.autoAdvance = false
                isOpen = true
                repeat(FRAMES) {
                    mainClock.advanceTimeByFrame()
                    val panel = onNodeWithTag(PANEL).fetchSemanticsNode().boundsInWindow

                    panel.top shouldBeGreaterThanOrEqual anchor.bottom
                }
            }
        }
    }
})
