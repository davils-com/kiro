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

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.ChromeWindow
import com.davils.kiro.ui.window.ChromeWindowState
import com.davils.kiro.ui.window.fixture.Themed
import com.davils.kiro.ui.window.host.LocalWindowHost
import com.davils.kiro.ui.window.testing.DetachedWindowHost
import com.davils.kiro.ui.window.testing.WindowFrameMock
import com.davils.kiro.ui.window.titlebar.WindowTitle
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

private const val TITLE: String = "kiro"

private fun maximized(): WindowState = WindowState(
    placement = WindowPlacement.Maximized,
    position = WindowPosition(x = 100.dp, y = 100.dp),
    size = DpSize(width = 800.dp, height = 600.dp),
)

@OptIn(ExperimentalTestApi::class)
class DecoratedFrameTest : FunSpec({
    context("decorations of the runtime") {
        test("keeps the frame of the system and hands it the title bar") {
            val api = WindowFrameMock()
            val host = DetachedWindowHost(api = api, isDecorationSupported = true)

            runComposeUiTest {
                setContent {
                    Themed(theme = Theme.dark()) {
                        CompositionLocalProvider(LocalWindowHost provides host) {
                            ChromeWindow(onCloseRequest = {}, title = TITLE, platform = DesktopPlatform.Windows) {
                                Text("Content")
                            }
                        }
                    }
                }
                onNodeWithText(TITLE).assertIsDisplayed()
                onNodeWithContentDescription("Close").assertDoesNotExist()
            }

            val spec = host.opened.single()
            spec.isUndecorated shouldBe false
            spec.isTransparent shouldBe false
            api.decoratedHeight shouldBe 36.dp
            api.isDecoratedDark shouldBe true
        }

        test("tells the system which pointer events a control of the bar handles") {
            val api = WindowFrameMock()
            val host = DetachedWindowHost(api = api, isDecorationSupported = true)
            var clicks = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        CompositionLocalProvider(LocalWindowHost provides host) {
                            ChromeWindow(
                                onCloseRequest = {},
                                title = TITLE,
                                platform = DesktopPlatform.Windows,
                                titleBar = {
                                    start { Button(onClick = { clicks += 1 }) { Text("Menu") } }
                                    center { WindowTitle(TITLE) }
                                },
                            ) {}
                        }
                    }
                }
                onNodeWithText("Menu").performClick()
                waitForIdle()
            }

            clicks shouldBe 1
            api.hitTests shouldContain true
        }

        test("leaves moving and maximizing to the system") {
            val api = WindowFrameMock(isSystemMoving = true)
            val frame = ChromeFrame(
                chrome = ChromeWindowState(maximized()),
                api = api,
                strategy = FrameStrategy.Decorated,
                minimumSize = DpSize(width = 400.dp, height = 300.dp),
                onClose = {},
            )

            frame.prepare(titleBarHeight = 36.dp, isDark = false)
            frame.hitTest(isClient = false)
            frame.toggleMaximize()

            frame.isResizable shouldBe false
            frame.titleBarChrome.isSystemControlled shouldBe true
            frame.captionInsets.right shouldBe 138.dp
            api.hitTests shouldBe listOf(false)
        }
    }

    context("windows maximized by Kiro") {
        test("maximize on the work area again when they open maximized") {
            val state = maximized()
            val chrome = ChromeWindowState(state)
            val frame = ChromeFrame(
                chrome = chrome,
                api = WindowFrameMock(),
                strategy = FrameStrategy.Emulated,
                minimumSize = DpSize(width = 400.dp, height = 300.dp),
                onClose = {},
            )

            frame.prepare(titleBarHeight = 40.dp, isDark = false)

            state.placement shouldBe WindowPlacement.Floating
            chrome.isMaximized shouldBe true
            state.size shouldBe DpSize(width = 1920.dp, height = 1000.dp)
            chrome.floatingBounds.left shouldBe 100.dp
        }

        test("stay maximized by the system where it moves windows itself") {
            val state = maximized()
            val frame = ChromeFrame(
                chrome = ChromeWindowState(state),
                api = WindowFrameMock(isSystemMoving = true),
                strategy = FrameStrategy.Emulated,
                minimumSize = DpSize(width = 400.dp, height = 300.dp),
                onClose = {},
            )

            frame.prepare(titleBarHeight = 40.dp, isDark = false)

            state.placement shouldBe WindowPlacement.Maximized
        }
    }
})
