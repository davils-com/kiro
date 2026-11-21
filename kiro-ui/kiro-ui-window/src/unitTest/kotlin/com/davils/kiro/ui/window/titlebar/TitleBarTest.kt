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

package com.davils.kiro.ui.window.titlebar

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.doubleClick
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.controls.ControlPlacement
import com.davils.kiro.ui.window.controls.LinuxControls
import com.davils.kiro.ui.window.controls.WindowButton
import com.davils.kiro.ui.window.fixture.RecordingActions
import com.davils.kiro.ui.window.fixture.TAG
import com.davils.kiro.ui.window.fixture.Themed
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

private const val START: String = "Start"
private const val CENTER: String = "Center"
private const val END: String = "End"
private const val CLOSE: String = "Close"
private const val MINIMIZE: String = "Minimize"

@OptIn(ExperimentalTestApi::class)
class TitleBarTest : FunSpec({
    context("slots") {
        test("lays out start, center and end from left to right") {
            runComposeUiTest {
                setContent {
                    Themed {
                        TitleBar(actions = RecordingActions(), modifier = Modifier.width(800.dp)) {
                            end { Text(END) }
                            center { Text(CENTER) }
                            start { Text(START) }
                        }
                    }
                }

                val start = onNodeWithText(START).getBoundsInRoot()
                val center = onNodeWithText(CENTER).getBoundsInRoot()
                val end = onNodeWithText(END).getBoundsInRoot()

                (start.right < center.left) shouldBe true
                (center.right < end.left) shouldBe true
            }
        }

        test("lays out its slots from right to left in a language that is written that way") {
            runComposeUiTest {
                setContent {
                    Themed {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            TitleBar(actions = RecordingActions(), modifier = Modifier.width(800.dp)) {
                                start { Text(START) }
                                center { Text(CENTER) }
                                end { Text(END) }
                            }
                        }
                    }
                }

                val start = onNodeWithText(START).getBoundsInRoot()
                val center = onNodeWithText(CENTER).getBoundsInRoot()
                val end = onNodeWithText(END).getBoundsInRoot()

                (end.right < center.left) shouldBe true
                (center.right < start.left) shouldBe true
            }
        }

        test("keeps the window buttons on the side of the platform in every direction") {
            val sides = listOf(LayoutDirection.Ltr, LayoutDirection.Rtl).map { direction ->
                var close = 0.dp
                runComposeUiTest {
                    setContent {
                        Themed {
                            CompositionLocalProvider(LocalLayoutDirection provides direction) {
                                TitleBar(
                                    actions = RecordingActions(),
                                    modifier = Modifier.width(800.dp),
                                    style = titleBarStyle(DesktopPlatform.Windows),
                                ) { start { Text(START) } }
                            }
                        }
                    }
                    close = onNodeWithContentDescription(CLOSE).getBoundsInRoot().right
                }
                close
            }

            sides shouldBe listOf(800.dp, 800.dp)
        }

        test("shows the window title") {
            runComposeUiTest {
                setContent {
                    Themed { TitleBar(actions = RecordingActions()) { center { WindowTitle("kiro") } } }
                }

                onNodeWithText("kiro").assertIsDisplayed()
            }
        }
    }

    context("controls") {
        DesktopPlatform.entries.filter { platform -> platform != DesktopPlatform.MacOs }.forEach { platform ->
            test("runs every window action from the buttons on $platform") {
                val actions = RecordingActions()

                runComposeUiTest {
                    setContent {
                        Themed { TitleBar(actions = actions, style = titleBarStyle(platform)) {} }
                    }

                    onNodeWithContentDescription(MINIMIZE).performClick()
                    onNodeWithContentDescription("Maximize").performClick()
                    onNodeWithContentDescription(CLOSE).performClick()
                }

                actions.calls shouldContainExactly listOf("minimize", "toggleMaximize", "close")
            }
        }

        test("offers to restore a maximized window") {
            runComposeUiTest {
                setContent {
                    Themed {
                        TitleBar(
                            actions = RecordingActions(isMaximized = true),
                            style = titleBarStyle(DesktopPlatform.Windows),
                        ) {}
                    }
                }

                onNodeWithContentDescription("Restore").assertIsDisplayed()
            }
        }

        test("leaves the buttons to macOS and reserves their room on the left") {
            runComposeUiTest {
                setContent {
                    Themed {
                        TitleBar(actions = RecordingActions(), style = titleBarStyle(DesktopPlatform.MacOs)) {
                            start { Text(START) }
                        }
                    }
                }

                onNodeWithContentDescription(CLOSE).assertDoesNotExist()
                (onNodeWithText(START).getBoundsInRoot().left >= 76.dp) shouldBe true
            }
        }

        test("places the buttons on the left when the desktop asks for it") {
            runComposeUiTest {
                setContent {
                    Themed {
                        val linux = titleBarStyle(DesktopPlatform.Linux)
                        val controls = linux.controls as LinuxControls
                        val style = linux.copy(
                            controls = controls.copy(
                                buttons = listOf(WindowButton.Close),
                                placement = ControlPlacement.Start,
                            ),
                        )
                        TitleBar(actions = RecordingActions(), style = style) { start { Text(START) } }
                    }
                }

                val close = onNodeWithContentDescription(CLOSE).getBoundsInRoot()
                val start = onNodeWithText(START).getBoundsInRoot()

                (close.right < start.left) shouldBe true
                onNodeWithContentDescription(MINIMIZE).assertDoesNotExist()
            }
        }

        test("draws the controls in the dark theme") {
            runComposeUiTest {
                setContent {
                    Themed(theme = Theme.dark()) {
                        TitleBar(actions = RecordingActions(), style = titleBarStyle(DesktopPlatform.Windows)) {}
                    }
                }

                onNodeWithContentDescription(CLOSE).assertIsDisplayed()
            }
        }
    }

    context("divider") {
        test("draws a bar without a divider") {
            runComposeUiTest {
                setContent {
                    Themed {
                        val style = titleBarStyle(DesktopPlatform.Linux).copy(divider = null)
                        TitleBar(actions = RecordingActions(), style = style) { center { WindowTitle("Plain") } }
                    }
                }

                onNodeWithText("Plain").assertIsDisplayed()
            }
        }
    }

    context("double click") {
        test("toggles the maximized state") {
            val actions = RecordingActions()

            runComposeUiTest {
                setContent {
                    Themed {
                        TitleBar(
                            actions = actions,
                            modifier = Modifier.testTag(TAG),
                            style = titleBarStyle(DesktopPlatform.Linux),
                        ) {}
                    }
                }

                onNodeWithTag(TAG).performMouseInput { doubleClick(center.copy(x = 20f)) }
                waitForIdle()
            }

            actions.calls shouldContainExactly listOf("toggleMaximize")
        }
    }
})
