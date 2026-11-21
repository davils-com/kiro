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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.window.fixture.RecordingApplication
import com.davils.kiro.ui.window.testing.DesktopMock
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.io.File
import java.net.URI

@OptIn(ExperimentalTestApi::class)
class ApplicationMenuTest : FunSpec({
    context("application menu") {
        test("registers every handler, routes the calls of the system and resets them when it leaves") {
            val desktop = DesktopMock()
            val calls = mutableListOf<String>()
            var isShown by mutableStateOf(true)

            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalDesktopHandlers provides desktop) {
                        if (isShown) {
                            with(RecordingApplication()) {
                                ApplicationMenu(
                                    onQuitRequest = { request -> request.confirm() },
                                    onAbout = { calls += "about" },
                                    onSettings = { calls += "settings" },
                                    onOpenFiles = { files -> calls += files.map(File::getName).joinToString() },
                                    onOpenUri = { uri -> calls += uri.toString() },
                                    onReopen = { calls += "reopen" },
                                )
                            }
                        }
                    }
                }
                waitForIdle()

                desktop.showAbout()
                desktop.showSettings()
                desktop.open(listOf(File("a.txt"), File("b.txt")))
                desktop.open(URI("kiro://open"))
                desktop.reopen()
                desktop.requestQuit()
                isShown = false
                waitForIdle()
            }

            calls shouldBe listOf("about", "settings", "a.txt, b.txt", "kiro://open", "reopen")
            desktop.quitAnswers shouldBe listOf(true)
            desktop.hasQuitHandler shouldBe false
            desktop.hasAboutHandler shouldBe false
            desktop.hasSettingsHandler shouldBe false
            desktop.hasOpenFilesHandler shouldBe false
            desktop.hasOpenUriHandler shouldBe false
            desktop.hasReopenHandler shouldBe false
        }

        test("registers only the quit handler when nothing else is given") {
            val desktop = DesktopMock()

            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalDesktopHandlers provides desktop) {
                        with(RecordingApplication()) { ApplicationMenu(onQuitRequest = QuitRequest::cancel) }
                    }
                }
                waitForIdle()
                desktop.showAbout()
                desktop.showSettings()
                desktop.open(emptyList())
                desktop.open(URI("kiro://none"))
                desktop.reopen()
                desktop.requestQuit()

                desktop.hasQuitHandler shouldBe true
                desktop.hasAboutHandler shouldBe false
                desktop.hasSettingsHandler shouldBe false
                desktop.hasOpenFilesHandler shouldBe false
                desktop.hasOpenUriHandler shouldBe false
                desktop.hasReopenHandler shouldBe false
            }

            desktop.quitAnswers shouldBe listOf(false)
            desktop.hasQuitHandler shouldBe false
        }

        test("calls the latest callbacks without registering again") {
            val desktop = DesktopMock()
            val calls = mutableListOf<String>()
            var version by mutableStateOf("first")

            runComposeUiTest {
                setContent {
                    val current = version
                    CompositionLocalProvider(LocalDesktopHandlers provides desktop) {
                        with(RecordingApplication()) {
                            ApplicationMenu(
                                onQuitRequest = { request ->
                                    calls += "quit $current"
                                    request.cancel()
                                },
                                onAbout = { calls += "about $current" },
                            )
                        }
                    }
                }
                waitForIdle()
                version = "second"
                waitForIdle()
                desktop.showAbout()
                desktop.requestQuit()
            }

            calls shouldBe listOf("about second", "quit second")
        }

        test("adds a handler that appears later and removes one that disappears") {
            val desktop = DesktopMock()
            var onAbout by mutableStateOf<(() -> Unit)?>(null)

            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalDesktopHandlers provides desktop) {
                        with(RecordingApplication()) { ApplicationMenu(onQuitRequest = {}, onAbout = onAbout) }
                    }
                }
                waitForIdle()
                desktop.hasAboutHandler shouldBe false
                onAbout = {}
                waitForIdle()
                desktop.hasAboutHandler shouldBe true
                onAbout = null
                waitForIdle()
                desktop.hasAboutHandler shouldBe false
            }
        }
    }
})
