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

package com.davils.kiro.ui.window.application

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.menubar.native.NativeMenuEntry
import com.davils.kiro.ui.window.fixture.RecordingApplication
import com.davils.kiro.ui.window.testing.DesktopMock
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

private const val ICON_SIZE: Int = 4

private const val NEW_WINDOW: String = "New window"

private const val PROGRESS: Float = 0.42f

private const val PERCENT: Int = 42

private const val FULL: Int = 100

@OptIn(ExperimentalTestApi::class)
class DockTest : FunSpec({
    context("dock") {
        test("shows the badge, the progress, the icon and the menu and clears them when it leaves") {
            val desktop = DesktopMock()
            val clicks = mutableListOf<String>()
            val icon = BitmapPainter(ImageBitmap(width = ICON_SIZE, height = ICON_SIZE))
            var badge by mutableStateOf<String?>("3")
            var isShown by mutableStateOf(true)

            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalDockApi provides desktop) {
                        if (isShown) {
                            with(RecordingApplication()) {
                                Dock(
                                    badge = badge,
                                    progress = PROGRESS,
                                    icon = icon,
                                    menu = {
                                        item(label = NEW_WINDOW, onClick = { clicks += NEW_WINDOW })
                                        separator()
                                        checkbox(label = "Do not disturb", checked = true, onCheckedChange = {})
                                    },
                                )
                            }
                        }
                    }
                }
                waitForIdle()

                desktop.badge shouldBe "3"
                desktop.progress shouldBe PERCENT
                desktop.icon.shouldNotBeNull()
                val menu = desktop.menu.shouldNotBeNull()
                menu.map { entry -> entry::class.simpleName } shouldBe listOf("Item", "Separator", "Checkbox")
                val first = menu.first().shouldBeInstanceOf<NativeMenuEntry.Item>()
                first.onClick()

                badge = "4"
                waitForIdle()
                desktop.badge shouldBe "4"

                isShown = false
                waitForIdle()
            }

            clicks shouldBe listOf(NEW_WINDOW)
            desktop.badge.shouldBeNull()
            desktop.progress.shouldBeNull()
            desktop.icon.shouldBeNull()
            desktop.menu.shouldBeNull()
        }

        test("leaves out what is not given") {
            val desktop = DesktopMock()
            var ticks by mutableIntStateOf(0)

            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalDockApi provides desktop) {
                        with(RecordingApplication()) { if (ticks >= 0) Dock() }
                    }
                }
                waitForIdle()
                ticks += 1
                waitForIdle()
            }

            desktop.badge.shouldBeNull()
            desktop.progress.shouldBeNull()
            desktop.icon.shouldBeNull()
            desktop.menu.shouldBeNull()
        }

        test("requests attention only while it shows its state") {
            val desktop = DesktopMock()
            val state = DockState()
            var isShown by mutableStateOf(true)

            state.requestAttention()
            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalDockApi provides desktop) {
                        if (isShown) with(RecordingApplication()) { Dock(state = state) }
                    }
                }
                waitForIdle()
                state.requestAttention()
                state.requestAttention(isCritical = true)
                isShown = false
                waitForIdle()
                state.requestAttention()
            }

            desktop.attentionRequests shouldBe listOf(false, true)
        }

        test("keeps a state that another dock took over") {
            val first = DesktopMock()
            val second = DesktopMock()
            val state = DockState()

            state.attach(first)
            state.attach(second)
            state.detach(first)
            state.requestAttention()

            first.attentionRequests shouldBe emptyList()
            second.attentionRequests shouldBe listOf(false)
        }
    }

    context("progress") {
        test("turns a fraction into a percentage within its bounds") {
            percentOf(PROGRESS) shouldBe PERCENT
            percentOf(-PROGRESS) shouldBe 0
            percentOf(PROGRESS + 1f) shouldBe FULL
        }
    }
})
