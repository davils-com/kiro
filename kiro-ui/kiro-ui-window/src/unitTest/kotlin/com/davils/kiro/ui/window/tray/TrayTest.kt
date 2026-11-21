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

package com.davils.kiro.ui.window.tray

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.menubar.native.NativeMenuEntry
import com.davils.kiro.ui.window.fixture.RecordingApplication
import com.davils.kiro.ui.window.testing.TrayHostMock
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

private val icon = TrayIcon(
    light = ColorPainter(Color.White),
    dark = ColorPainter(Color.Black),
    template = ColorPainter(Color.Gray),
)

@OptIn(ExperimentalTestApi::class)
class TrayTest : FunSpec({
    context("tray") {
        test("shows the template image, the tooltip and the menu on macOS") {
            val host = TrayHostMock()
            val clicks = mutableListOf<String>()

            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalTrayHost provides host) {
                        with(RecordingApplication()) {
                            Tray(
                                icon = icon,
                                tooltip = "Kiro",
                                menu = {
                                    item(label = "Open", onClick = { clicks += "Open" })
                                    separator()
                                    item(label = "Quit", onClick = { clicks += "Quit" })
                                },
                                onAction = { clicks += "action" },
                                platform = DesktopPlatform.MacOs,
                            )
                        }
                    }
                }
                waitForIdle()
            }

            val spec = host.shown.single()
            spec.icon shouldBe icon.template
            spec.tooltip shouldBe "Kiro"
            spec.entries.map { entry -> entry::class.simpleName } shouldBe listOf("Item", "Separator", "Item")
            val open = spec.entries.first().shouldBeInstanceOf<NativeMenuEntry.Item>()
            open.onClick()
            spec.onAction()
            clicks shouldBe listOf("Open", "action")
        }

        test("follows the theme of the system off macOS") {
            val host = TrayHostMock()
            var expected: Painter? = null
            var ticks by mutableIntStateOf(0)

            runComposeUiTest {
                setContent {
                    expected = icon.painterFor(platform = DesktopPlatform.Linux, isDark = isSystemInDarkTheme())
                    CompositionLocalProvider(LocalTrayHost provides host) {
                        with(RecordingApplication()) {
                            if (ticks >= 0) {
                                Tray(icon = icon, tooltip = "Kiro", menu = {}, platform = DesktopPlatform.Linux)
                            }
                        }
                    }
                }
                waitForIdle()
                ticks += 1
                waitForIdle()
            }

            val spec = host.shown.single()
            spec.icon shouldBe expected
            spec.entries.shouldBeEmpty()
        }

        test("forwards the notifications of its state, also those sent before it showed") {
            val host = TrayHostMock()
            val state = TrayState()
            val early = TrayNotification(title = "Started", message = "Kiro runs in the tray.")
            val later = TrayNotification(title = "Synced", message = "Done.", type = TrayNotificationType.Info)
            state.sendNotification(early)

            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalTrayHost provides host) {
                        with(RecordingApplication()) {
                            Tray(icon = icon, tooltip = "Kiro", menu = {}, state = state)
                        }
                    }
                }
                waitForIdle()
                state.sendNotification(later)
                waitForIdle()
            }

            host.notifications shouldBe listOf(early, later)
        }

        test("records a changed spec once per change") {
            val host = TrayHostMock()
            var tooltip by mutableStateOf("One")
            var ticks by mutableIntStateOf(0)

            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalTrayHost provides host) {
                        key(ticks) { host.Show(scope = RecordingApplication(), spec = specOf(tooltip)) }
                    }
                }
                waitForIdle()
                ticks += 1
                waitForIdle()
                tooltip = "Two"
                waitForIdle()
            }

            host.shown.map(TraySpec::tooltip) shouldBe listOf("One", "Two")
        }

        test("calls onUnsupported once and shows nothing on a desktop without a tray") {
            val host = TrayHostMock(isSupported = false)
            var calls = 0
            var tooltip by mutableStateOf("One")

            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalTrayHost provides host) {
                        with(RecordingApplication()) {
                            Tray(icon = icon, tooltip = tooltip, menu = {}, onUnsupported = { calls += 1 })
                        }
                    }
                }
                waitForIdle()
                tooltip = "Two"
                waitForIdle()
            }

            calls shouldBe 1
            host.shown.shouldBeEmpty()
        }
    }
})

private val noAction: () -> Unit = {}

private val sharedState = TrayState()

private fun specOf(tooltip: String): TraySpec = TraySpec(
    icon = icon.light,
    tooltip = tooltip,
    entries = emptyList(),
    state = sharedState,
    onAction = noAction,
)
