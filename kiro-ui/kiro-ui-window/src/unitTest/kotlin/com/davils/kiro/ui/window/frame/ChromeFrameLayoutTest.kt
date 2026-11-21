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

@file:OptIn(DavilsInternal::class)

package com.davils.kiro.ui.window.frame

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.ChromeWindowState
import com.davils.kiro.ui.window.FramePreference
import com.davils.kiro.ui.window.chromeWindowStyle
import com.davils.kiro.ui.window.fixture.Themed
import com.davils.kiro.ui.window.testing.DetachedWindowScope
import com.davils.kiro.ui.window.testing.WindowFrameMock
import com.davils.kiro.ui.window.titlebar.WindowTitle
import com.davils.kiro.ui.window.titlebar.titleBarStyle
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val TITLE: String = "kiro"
private const val CONTENT: String = "Content"

private fun floating(): WindowState =
    WindowState(position = WindowPosition(x = 100.dp, y = 100.dp), size = DpSize(width = 800.dp, height = 600.dp))

@OptIn(ExperimentalTestApi::class)
private fun runChrome(
    state: WindowState,
    api: WindowFrameMock,
    platform: DesktopPlatform = DesktopPlatform.Linux,
    block: ComposeUiTest.() -> Unit,
) {
    runComposeUiTest {
        setContent {
            Themed {
                val setup = ChromeSetup(
                    state = ChromeWindowState(state),
                    strategy = frameStrategyOf(
                        platform = platform,
                        preference = FramePreference.Native,
                        isDecorationSupported = false,
                    ),
                    style = chromeWindowStyle(platform),
                    titleBarStyle = titleBarStyle(platform),
                    theme = Theme.light(),
                )
                with(DetachedWindowScope) {
                    ChromeContent(
                        api = api,
                        setup = setup,
                        onCloseRequest = {},
                        titleBar = { center { WindowTitle(TITLE) } },
                    ) { Text(CONTENT) }
                }
            }
        }
        block()
    }
}

@OptIn(ExperimentalTestApi::class)
class ChromeFrameLayoutTest : FunSpec({
    context("frame") {
        test("shows the title bar above the content and prepares the window") {
            val api = WindowFrameMock()

            runChrome(state = floating(), api = api) {
                onNodeWithText(TITLE).assertIsDisplayed()
                onNodeWithText(CONTENT).assertIsDisplayed()
                onNodeWithTag(RESIZE_TAG_PREFIX + ResizeEdge.SouthEast.name).assertIsDisplayed()
            }

            api.limited shouldBe DpSize(width = 480.dp, height = 320.dp)
        }

        test("hides the title bar in full screen") {
            val state = floating()
            state.placement = WindowPlacement.Fullscreen

            runChrome(state = state, api = WindowFrameMock()) {
                onNodeWithText(TITLE).assertDoesNotExist()
                onNodeWithText(CONTENT).assertIsDisplayed()
            }
        }

        test("leaves resizing to macOS") {
            runChrome(state = floating(), api = WindowFrameMock(), platform = DesktopPlatform.MacOs) {
                onNodeWithTag(RESIZE_TAG_PREFIX + ResizeEdge.East.name).assertDoesNotExist()
            }
        }
    }

    context("gestures") {
        test("moves the window by dragging the title bar") {
            val state = floating()
            val api = WindowFrameMock(
                pointer = DpOffset(x = 200.dp, y = 110.dp),
                step = DpOffset(x = 10.dp, y = 0.dp),
            )

            runChrome(state = state, api = api) {
                onNodeWithText(TITLE).performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = 40f, y = 0f))
                    moveBy(Offset(x = 40f, y = 0f))
                    release()
                }
                waitForIdle()
            }

            (state.position.x > 100.dp) shouldBe true
        }

        test("resizes the window by dragging an edge") {
            val state = floating()
            val api = WindowFrameMock(
                pointer = DpOffset(x = 900.dp, y = 400.dp),
                step = DpOffset(x = 10.dp, y = 0.dp),
            )

            runChrome(state = state, api = api) {
                onNodeWithTag(RESIZE_TAG_PREFIX + ResizeEdge.East.name).performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = 40f, y = 0f))
                    moveBy(Offset(x = 40f, y = 0f))
                    release()
                }
                waitForIdle()
            }

            (state.size.width > 800.dp) shouldBe true
        }

        test("stops after a cancelled drag") {
            val state = floating()

            runChrome(state = state, api = WindowFrameMock(pointer = null)) {
                onNodeWithTag(RESIZE_TAG_PREFIX + ResizeEdge.West.name).performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = 40f, y = 0f))
                    release()
                }
                onNodeWithText(TITLE).performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = 40f, y = 0f))
                    release()
                }
                waitForIdle()
            }

            state.size shouldBe DpSize(width = 800.dp, height = 600.dp)
        }
    }
})
