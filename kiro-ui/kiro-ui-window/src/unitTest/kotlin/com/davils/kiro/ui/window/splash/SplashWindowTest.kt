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

package com.davils.kiro.ui.window.splash

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.fixture.Themed
import com.davils.kiro.ui.window.host.LocalWindowHost
import com.davils.kiro.ui.window.host.WindowSpec
import com.davils.kiro.ui.window.testing.DetachedWindowHost
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val TITLE: String = "kiro"
private const val LOADING: String = "Loading"

private val silent: NativeSplash = NativeSplash {}

@OptIn(ExperimentalTestApi::class)
class SplashWindowTest : FunSpec({
    context("spec") {
        test("opens an undecorated, fixed, transparent and centered window with the title and the icon") {
            val host = DetachedWindowHost()
            val icon = ColorPainter(Color.Black)

            runComposeUiTest {
                setContent {
                    Themed {
                        CompositionLocalProvider(LocalWindowHost provides host, LocalNativeSplash provides silent) {
                            SplashWindow(onCloseRequest = {}, title = TITLE, icon = icon) { Text(LOADING) }
                        }
                    }
                }

                onNodeWithText(LOADING).assertIsDisplayed()
                onRoot().captureToImage()
            }

            val spec: WindowSpec = host.opened.single()
            spec.title shouldBe TITLE
            spec.isUndecorated shouldBe true
            spec.isResizable shouldBe false
            spec.isTransparent shouldBe true
            spec.options.icon shouldBe icon
            spec.state.position shouldBe WindowPosition(Alignment.Center)
            spec.state.size shouldBe DpSize(width = 880.dp, height = 520.dp)
        }

        test("stays opaque without rounded corners and opens with a changed size") {
            val host = DetachedWindowHost()
            val size = DpSize(width = 320.dp, height = 240.dp)

            runComposeUiTest {
                setContent {
                    Themed(theme = Theme.dark()) {
                        CompositionLocalProvider(LocalWindowHost provides host, LocalNativeSplash provides silent) {
                            val style = splashWindowStyle().copy(cornerRadius = 0.dp)
                            SplashWindow(onCloseRequest = {}, title = TITLE, style = style, size = size) {
                                Text(LOADING)
                            }
                        }
                    }
                }
                waitForIdle()
            }

            val spec = host.opened.single()
            spec.isTransparent shouldBe false
            spec.state.size shouldBe size
        }

        test("hands the close request to the window") {
            val host = DetachedWindowHost()
            var closed = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        CompositionLocalProvider(LocalWindowHost provides host, LocalNativeSplash provides silent) {
                            SplashWindow(onCloseRequest = { closed++ }, title = TITLE) { Text(LOADING) }
                        }
                    }
                }
                waitForIdle()
            }

            host.opened.single().onCloseRequest()
            closed shouldBe 1
        }
    }

    context("native splash") {
        test("closes the splash image of the runtime once after the first frame") {
            var closes = 0
            var count by mutableIntStateOf(0)

            runComposeUiTest {
                setContent {
                    Themed {
                        CompositionLocalProvider(
                            LocalWindowHost provides DetachedWindowHost(),
                            LocalNativeSplash provides NativeSplash { closes++ },
                        ) {
                            SplashWindow(onCloseRequest = {}, title = TITLE) { Text("$LOADING $count") }
                        }
                    }
                }
                waitForIdle()
                closes shouldBe 1

                count = 1
                waitForIdle()
                count = 2
                waitForIdle()

                onNodeWithText("$LOADING 2").assertIsDisplayed()
            }

            closes shouldBe 1
        }
    }
})
