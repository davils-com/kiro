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

package com.davils.kiro.ui.components.sheet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val TITLE: String = "Edit profile"
private const val QUARTER: Float = 0.25f
private const val PANEL: String = "panel"
private const val FIT: Float = 1f

@OptIn(ExperimentalTestApi::class)
class SheetTest : FunSpec({
    context("styles") {
        test("take a quarter of the window, at least 280, with a large shadow") {
            val light = Theme.light()
            val style = SheetStyles.Default.resolve(light)

            style.fraction shouldBe QUARTER
            style.minSize shouldBe 280.dp
            style.shadow shouldBe light.shadows.lg
        }
    }

    context("sheet") {
        test("slides in from every side and closes with its button") {
            Side.entries.forEach { side ->
                var isOpen by mutableStateOf(true)

                runComposeUiTest {
                    setContent {
                        Themed {
                            Direction(direction = LayoutDirection.Rtl) {
                                Sheet(open = isOpen, onOpenChange = { isOpen = it }, side = side) {
                                    SheetHeader {
                                        SheetTitle(text = TITLE)
                                        SheetDescription(text = "Make changes here.")
                                    }
                                    Text("Body")
                                    SheetFooter { Text("Footer") }
                                }
                            }
                        }
                    }

                    onNodeWithText(TITLE).assertExists()
                    onNodeWithContentDescription("Close").performClick()
                    waitForIdle()
                    onNodeWithText(TITLE).assertDoesNotExist()
                }

                isOpen shouldBe false
            }
        }

        test("covers a quarter of the window from the side and from the bottom") {
            listOf(Side.End, Side.Bottom).forEach { side ->
                runComposeUiTest {
                    setContent {
                        Themed {
                            Sheet(open = true, onOpenChange = {}, modifier = Modifier.testTag(PANEL), side = side) {
                                SheetTitle(text = TITLE)
                            }
                        }
                    }

                    val roots = onAllNodes(isRoot())
                    val window = roots.onFirst().getBoundsInRoot()
                    val panel = onNodeWithTag(PANEL).getBoundsInRoot()
                    val isAcross = side == Side.Bottom
                    val windowExtent = (window.bottom - window.top).takeIf { isAcross } ?: window.right - window.left
                    val panelExtent = (panel.bottom - panel.top).takeIf { isAcross } ?: panel.right - panel.left
                    val expected = maxOf(windowExtent * QUARTER, 280.dp)
                    panelExtent.value shouldBe (expected.value plusOrMinus FIT)
                }
            }
        }
    }
})
