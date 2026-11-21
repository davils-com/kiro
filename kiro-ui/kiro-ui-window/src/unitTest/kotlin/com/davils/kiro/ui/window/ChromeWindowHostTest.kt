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

package com.davils.kiro.ui.window

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.ApplicationThemeProvider
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.fixture.Themed
import com.davils.kiro.ui.window.host.LocalWindowHost
import com.davils.kiro.ui.window.testing.DetachedWindowHost
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val TITLE: String = "kiro"

@OptIn(ExperimentalTestApi::class)
class ChromeWindowHostTest : FunSpec({
    context("chrome window") {
        test("opens a framed window with the title in its bar and closes on request") {
            val host = DetachedWindowHost()
            var isClosed = false

            runComposeUiTest {
                setContent {
                    Themed {
                        CompositionLocalProvider(LocalWindowHost provides host) {
                            ChromeWindow(
                                onCloseRequest = { isClosed = true },
                                title = TITLE,
                                platform = DesktopPlatform.Windows,
                            ) { Text("Content") }
                        }
                    }
                }

                onNodeWithText(TITLE).assertIsDisplayed()
                onNodeWithText("Content").assertIsDisplayed()
                onNodeWithContentDescription("Close").performClick()
            }

            host.opened.single().title shouldBe TITLE
            isClosed shouldBe true
        }
    }

    context("theme") {
        test("carries the theme of the application into the window") {
            var current: Theme? = null

            runComposeUiTest {
                setContent {
                    ApplicationThemeProvider(theme = Theme.dark()) {
                        CompositionLocalProvider(LocalWindowHost provides DetachedWindowHost()) {
                            ChromeWindow(onCloseRequest = {}, title = TITLE) { current = Theme.current }
                        }
                    }
                }
                waitForIdle()
            }

            current?.isDark shouldBe true
            current?.colors shouldBe Theme.dark().colors
        }
    }

    context("options") {
        test("hands the key handlers and the visibility to the window and draws nothing of a hidden one") {
            val host = DetachedWindowHost()
            val options = WindowOptions(isVisible = false, onPreviewKeyEvent = { true })

            runComposeUiTest {
                setContent {
                    Themed {
                        CompositionLocalProvider(LocalWindowHost provides host) {
                            ChromeWindow(onCloseRequest = {}, title = TITLE, options = options) { Text("Hidden") }
                        }
                    }
                }
                waitForIdle()

                onNodeWithText("Hidden").assertDoesNotExist()
            }

            host.opened.single().options shouldBe options
        }
    }
})
