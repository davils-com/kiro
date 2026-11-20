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

package com.davils.kiro.ui.components.drawer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val TITLE: String = "Move goal"
private const val FAR: Float = 600f
private const val NEAR: Float = 5f
private const val QUARTER: Float = 0.25f
private const val HALF: Float = 0.5f
private const val UP: Float = 250f
private const val FIT: Float = 2f
private const val PANEL: String = "panel"
private const val SUBMIT: String = "Submit"

@OptIn(ExperimentalTestApi::class)
class DrawerTest : FunSpec({
    context("styles") {
        test("are rounded-xl panels with a w-24 handle that dismiss after a quarter") {
            val light = Theme.light()
            val style = DrawerStyles.Default.resolve(light)

            style.handleLength shouldBe 96.dp
            style.handleColor shouldBe light.colors.muted
            style.dismissFraction shouldBe QUARTER
            style.snapPoints shouldBe listOf(HALF, 1f)
        }
    }

    context("drawer") {
        test("closes when it is dragged far enough") {
            var isOpen by mutableStateOf(true)

            runComposeUiTest {
                setContent {
                    Themed {
                        Drawer(open = isOpen, onOpenChange = { isOpen = it }) {
                            DrawerHeader {
                                DrawerTitle(text = TITLE)
                                DrawerDescription(text = "Set your daily activity goal.")
                            }
                            Text("Body")
                            DrawerFooter { Text(SUBMIT) }
                        }
                    }
                }

                val title = onNodeWithText(TITLE)
                title.performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = 0f, y = NEAR))
                    release()
                }
                waitForIdle()
                title.assertExists()
                title.performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = 0f, y = FAR))
                    release()
                }
                waitForIdle()
            }

            isOpen shouldBe false
        }

        test("opens from the other sides") {
            listOf(Side.Top, Side.Start, Side.End).forEach { side ->
                runComposeUiTest {
                    setContent {
                        Themed {
                            Drawer(open = true, onOpenChange = {}, side = side) {
                                DrawerHeader { DrawerTitle(text = "$TITLE $side") }
                                DrawerFooter { Text(SUBMIT) }
                            }
                        }
                    }

                    onNodeWithText("$TITLE $side").assertExists()
                }
            }
        }

        test("opens at the first snap point and snaps up to the full height") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Drawer(open = true, onOpenChange = {}, modifier = Modifier.testTag(PANEL)) {
                            DrawerHeader { DrawerTitle(text = TITLE) }
                            DrawerFooter { Text(SUBMIT) }
                        }
                    }
                }

                val half = onNodeWithTag(PANEL).getBoundsInRoot()
                val halfHeight = half.bottom - half.top
                onNodeWithText(TITLE).performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = 0f, y = -UP))
                    release()
                }
                waitForIdle()
                val full = onNodeWithTag(PANEL).getBoundsInRoot()
                (full.bottom - full.top).value shouldBe ((halfHeight * 2).value plusOrMinus FIT)
            }
        }
    }
})
