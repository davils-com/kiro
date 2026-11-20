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

package com.davils.kiro.ui.components.command

import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.User
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private const val CALENDAR: String = "Calendar"
private const val PROFILE: String = "Profile"
private const val CORNER_FLOOR: Float = 0.99f

@OptIn(ExperimentalTestApi::class)
class CommandTest : FunSpec({
    context("styles") {
        test("are rounded-xl p-1 panels with muted highlights") {
            val light = Theme.light()
            val style = CommandStyles.Default.resolve(light)

            style.panel.padding shouldBe 4.dp
            style.item.highlight shouldBe light.colors.muted
            style.listMaxHeight shouldBe 288.dp
        }
    }

    context("item highlight") {
        test("keeps the hover feedback inside the rounded corners of the row") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Command {
                            group(heading = "Files") {
                                item(label = CALENDAR, onSelect = {})
                                item(label = PROFILE, onSelect = {})
                            }
                        }
                    }
                }

                val row = onNodeWithText(PROFILE)
                row.performMouseInput { moveTo(center) }
                waitForIdle()
                val pixels = row.captureToImage().toPixelMap()

                pixels[0, 0].red shouldBeGreaterThan CORNER_FLOOR
                pixels[pixels.width - 1, 0].red shouldBeGreaterThan CORNER_FLOOR
            }
        }
    }

    context("command") {
        test("filters by label and keyword and runs the highlighted item") {
            var ran = ""

            runComposeUiTest {
                setContent {
                    Themed {
                        Command {
                            group(heading = "Suggestions") {
                                item(label = CALENDAR, onSelect = { ran = CALENDAR }, keywords = listOf("schedule"))
                                item(label = "Calculator", onSelect = {}, enabled = false)
                            }
                            separator()
                            group(heading = "Settings") {
                                item(
                                    label = PROFILE,
                                    onSelect = { ran = PROFILE },
                                    icon = Lucide.User,
                                    shortcut = "⌘P",
                                )
                            }
                        }
                    }
                }

                onNodeWithText(PROFILE).performMouseInput { moveTo(center) }
                onNodeWithText(PROFILE).performClick()
                val field = onNode(hasSetTextAction())
                field.performClick()
                field.performTextInput("sched")
                waitForIdle()
                onNodeWithText(PROFILE).assertDoesNotExist()
                field.performKeyInput { pressKey(Key.Enter) }
                field.performTextInput("xyz")
                waitForIdle()
                onNodeWithText("No results found.").assertExists()
            }

            ran shouldBe CALENDAR
        }
    }
})
