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

package com.davils.kiro.ui.window.application

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.menubar.Menubar
import com.davils.kiro.ui.window.fixture.Themed
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private const val ABOUT: String = "About"

private const val SETTINGS: String = "Settings…"

@OptIn(ExperimentalTestApi::class)
class ApplicationMenuEntriesTest : FunSpec({
    context("application menu in the menubar") {
        test("adds About, Settings and Quit on Windows and Linux") {
            val calls = mutableListOf<String>()

            runComposeUiTest {
                setContent {
                    Themed {
                        Menubar {
                            applicationMenu(
                                label = "Kiro",
                                onQuit = { calls += "quit" },
                                onAbout = { calls += "about" },
                                onSettings = { calls += "settings" },
                                platform = DesktopPlatform.Windows,
                            )
                        }
                    }
                }

                onNodeWithText("Kiro").performClick()
                waitForIdle()
                onNodeWithText(ABOUT).assertIsDisplayed()
                onNodeWithText(SETTINGS).assertIsDisplayed()
                onNodeWithText("Quit").performClick()
                waitForIdle()
            }

            calls shouldBe listOf("quit")
        }

        test("shows the given labels and only Quit without other actions") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Menubar {
                            applicationMenu(
                                label = "Datei",
                                onQuit = {},
                                platform = DesktopPlatform.Linux,
                                labels = ApplicationMenuLabels(quit = "Beenden"),
                            )
                        }
                    }
                }

                onNodeWithText("Datei").performClick()
                waitForIdle()
                onNodeWithText("Beenden").assertIsDisplayed()
                onNodeWithText(ABOUT).assertDoesNotExist()
                onNodeWithText(SETTINGS).assertDoesNotExist()
            }
        }

        test("decides by the platform the application runs on by default") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Menubar {
                            applicationMenu(label = "Kiro", onQuit = {})
                            menu(label = "Edit") { item(label = "Copy", onClick = {}) }
                        }
                    }
                }

                onNodeWithText("Edit").assertIsDisplayed()
            }
        }

        test("adds nothing on macOS") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Menubar {
                            applicationMenu(label = "Kiro", onQuit = {}, onAbout = {}, platform = DesktopPlatform.MacOs)
                            menu(label = "Edit") { item(label = "Copy", onClick = {}) }
                        }
                    }
                }

                onNodeWithText("Edit").assertIsDisplayed()
                onNodeWithText("Kiro").assertDoesNotExist()
            }
        }
    }

    context("labels") {
        test("are English by default and compare by their values") {
            val labels = ApplicationMenuLabels()

            labels.about shouldBe ABOUT
            labels.settings shouldBe SETTINGS
            labels.quit shouldBe "Quit"
            labels.copy() shouldBe labels
            labels.copy().hashCode() shouldBe labels.hashCode()
            labels.copy(quit = "Exit") shouldNotBe labels
            labels.toString() shouldStartWith "ApplicationMenuLabels(about=About"
        }
    }
})
