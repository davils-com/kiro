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

package com.davils.kiro.ui.window.splash.hero

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.fixture.Themed
import com.davils.kiro.ui.window.fixture.reducedMotion
import com.davils.kiro.ui.window.splash.BACKGROUND
import com.davils.kiro.ui.window.splash.CLOSE
import com.davils.kiro.ui.window.splash.CONFIGURING
import com.davils.kiro.ui.window.splash.COPYRIGHT
import com.davils.kiro.ui.window.splash.FAILURE
import com.davils.kiro.ui.window.splash.LOGO
import com.davils.kiro.ui.window.splash.MOUNTING
import com.davils.kiro.ui.window.splash.NAME
import com.davils.kiro.ui.window.splash.RETRY
import com.davils.kiro.ui.window.splash.SUBTITLE
import com.davils.kiro.ui.window.splash.Splash
import com.davils.kiro.ui.window.splash.VERSION
import com.davils.kiro.ui.window.splash.WORKSPACE
import com.davils.kiro.ui.window.splash.failed
import com.davils.kiro.ui.window.splash.runningAt
import com.davils.kiro.ui.window.splash.sample
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val LATER: Long = 1_000

private val bounds: Modifier = Modifier.size(width = 800.dp, height = 480.dp)

@OptIn(ExperimentalTestApi::class)
class HeroSplashTest : FunSpec({
    context("content") {
        test("shows the name, the subtitle, the step, the version and the copyright in both themes") {
            listOf(reducedMotion, Theme.dark()).forEach { theme ->
                runComposeUiTest {
                    setContent { Themed(theme = theme) { HeroSplash(splash = sample, modifier = bounds) } }
                    mainClock.advanceTimeBy(LATER)

                    listOf(NAME, SUBTITLE, CONFIGURING, VERSION, COPYRIGHT).forEach { text ->
                        onNodeWithText(text).assertIsDisplayed()
                    }
                    onNodeWithText(MOUNTING).assertDoesNotExist()
                    onRoot().captureToImage()
                }
            }
        }

        test("says that it is starting and leaves out what the splash does not name") {
            runComposeUiTest {
                setContent { Themed { HeroSplash(splash = Splash(name = NAME), modifier = bounds) } }

                onNodeWithText("Starting…").assertIsDisplayed()
                listOf(SUBTITLE, VERSION, COPYRIGHT).forEach { text -> onNodeWithText(text).assertDoesNotExist() }
            }
        }

        test("replaces the step when the next one runs, without motion") {
            var current by mutableStateOf(sample.copy(steps = runningAt(0)))

            runComposeUiTest {
                setContent { Themed(theme = reducedMotion) { HeroSplash(splash = current, modifier = bounds) } }
                onNodeWithText(MOUNTING).assertIsDisplayed()

                current = sample.copy(steps = runningAt(2))
                waitForIdle()

                onNodeWithText(CONFIGURING).assertIsDisplayed()
                onNodeWithText(MOUNTING).assertDoesNotExist()
            }
        }
    }

    context("failure") {
        test("names the failed step, shows the message and closes or retries on request") {
            var closed = 0
            var retried = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        HeroSplash(splash = failed, modifier = bounds, onClose = { closed++ }, onRetry = { retried++ })
                    }
                }

                onNodeWithText(WORKSPACE).assertIsDisplayed()
                onNodeWithText(FAILURE).assertIsDisplayed()
                onNodeWithText(CLOSE).performClick()
                onNodeWithText(RETRY).performClick()
            }

            closed shouldBe 1
            retried shouldBe 1
        }
    }

    context("style") {
        test("shows a changed logo size and a changed label") {
            runComposeUiTest {
                setContent {
                    Themed {
                        val style = heroSplashStyle()
                        HeroSplash(
                            splash = Splash(name = NAME),
                            modifier = bounds,
                            style = style.copy(logoSize = 88.dp, labels = style.labels.copy(starting = "Booting")),
                            logo = { slot -> Box(modifier = slot.testTag(LOGO)) },
                        )
                    }
                }

                onNodeWithTag(LOGO).assertHeightIsEqualTo(88.dp)
                onNodeWithText("Booting").assertIsDisplayed()
            }
        }
    }

    context("slots") {
        test("replaces the background and the logo") {
            runComposeUiTest {
                setContent {
                    Themed {
                        HeroSplash(
                            splash = failed,
                            modifier = bounds,
                            background = { slot -> Box(modifier = slot.testTag(BACKGROUND)) },
                            logo = { slot -> Box(modifier = slot.testTag(LOGO)) },
                        )
                    }
                }

                onNodeWithTag(BACKGROUND).assertExists()
                onNodeWithTag(LOGO).assertExists()
            }
        }
    }
})
