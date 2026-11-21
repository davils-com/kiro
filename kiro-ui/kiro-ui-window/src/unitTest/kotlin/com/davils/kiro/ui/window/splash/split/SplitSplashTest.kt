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

package com.davils.kiro.ui.window.splash.split

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithContentDescription
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
import com.davils.kiro.ui.window.splash.EDITION
import com.davils.kiro.ui.window.splash.FAILURE
import com.davils.kiro.ui.window.splash.LOGO
import com.davils.kiro.ui.window.splash.MOUNTING
import com.davils.kiro.ui.window.splash.NAME
import com.davils.kiro.ui.window.splash.PLUGINS
import com.davils.kiro.ui.window.splash.RETRY
import com.davils.kiro.ui.window.splash.SUBTITLE
import com.davils.kiro.ui.window.splash.Splash
import com.davils.kiro.ui.window.splash.VENDOR
import com.davils.kiro.ui.window.splash.VERSION
import com.davils.kiro.ui.window.splash.WINDOWS
import com.davils.kiro.ui.window.splash.WORKSPACE
import com.davils.kiro.ui.window.splash.failed
import com.davils.kiro.ui.window.splash.runningAt
import com.davils.kiro.ui.window.splash.sample
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val VENDOR_LOGO: String = "vendor-logo"
private const val LATER: Long = 1_000

private val polite: SemanticsMatcher =
    SemanticsMatcher.expectValue(SemanticsProperties.LiveRegion, LiveRegionMode.Polite)

private val bounds: Modifier = Modifier.size(width = 880.dp, height = 520.dp)

@OptIn(ExperimentalTestApi::class)
class SplitSplashTest : FunSpec({
    context("content") {
        test("shows the name, the edition, the subtitle, the step, the version and the copyright in both themes") {
            listOf(reducedMotion, Theme.dark()).forEach { theme ->
                runComposeUiTest {
                    setContent { Themed(theme = theme) { SplitSplash(splash = sample, modifier = bounds) } }
                    mainClock.advanceTimeBy(LATER)

                    listOf(NAME, EDITION, SUBTITLE, CONFIGURING, VERSION, COPYRIGHT, VENDOR).forEach { text ->
                        onNodeWithText(text).assertIsDisplayed()
                    }
                    onNodeWithContentDescription("Running").assertIsDisplayed()
                    listOf(MOUNTING, PLUGINS, WINDOWS).forEach { label -> onNodeWithText(label).assertDoesNotExist() }
                    onRoot().captureToImage()
                }
            }
        }

        test("announces the current step politely") {
            runComposeUiTest {
                setContent { Themed { SplitSplash(splash = sample, modifier = bounds) } }

                onNodeWithText(CONFIGURING).assert(polite)
            }
        }

        test("says that it is starting and leaves out what the splash does not name") {
            runComposeUiTest {
                setContent { Themed { SplitSplash(splash = Splash(name = NAME), modifier = bounds) } }

                onNodeWithText("Starting…").assertIsDisplayed()
                listOf(EDITION, SUBTITLE, VERSION, COPYRIGHT, VENDOR).forEach { text ->
                    onNodeWithText(text).assertDoesNotExist()
                }
            }
        }

        test("stands the vendor upright with its logo below") {
            runComposeUiTest {
                setContent {
                    Themed {
                        SplitSplash(
                            splash = sample,
                            modifier = bounds,
                            vendorLogo = { slot -> Box(modifier = slot.testTag(VENDOR_LOGO)) },
                        )
                    }
                }
                onRoot().captureToImage()

                val name = onNodeWithText(VENDOR).fetchSemanticsNode().boundsInRoot
                val logo = onNodeWithTag(VENDOR_LOGO).fetchSemanticsNode().boundsInRoot
                (name.height > name.width) shouldBe true
                (logo.top >= name.bottom) shouldBe true
                (logo.left > onRoot().fetchSemanticsNode().size.width / 2) shouldBe true
            }
        }

        test("replaces the step when the next one runs, without motion") {
            var current by mutableStateOf(sample.copy(steps = runningAt(0)))

            runComposeUiTest {
                setContent { Themed(theme = reducedMotion) { SplitSplash(splash = current, modifier = bounds) } }
                onNodeWithText(MOUNTING).assertIsDisplayed()

                current = sample.copy(steps = runningAt(2))
                waitForIdle()

                onNodeWithText(CONFIGURING).assertIsDisplayed()
                onNodeWithText(MOUNTING).assertDoesNotExist()
            }
        }

        test("slides the step when the next one runs with motion") {
            var current by mutableStateOf(sample.copy(steps = runningAt(0)))

            runComposeUiTest {
                setContent { Themed(theme = Theme.light()) { SplitSplash(splash = current, modifier = bounds) } }
                current = sample.copy(steps = runningAt(2))
                mainClock.advanceTimeBy(LATER)

                onNodeWithText(CONFIGURING).assertIsDisplayed()
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
                        SplitSplash(splash = failed, modifier = bounds, onClose = { closed++ }, onRetry = { retried++ })
                    }
                }

                onNodeWithText(WORKSPACE).assertIsDisplayed()
                onNodeWithText(FAILURE).assertIsDisplayed()
                onNodeWithText(FAILURE).assert(polite)
                onNodeWithContentDescription("Failed").assertIsDisplayed()
                onNodeWithContentDescription("Running").assertDoesNotExist()
                onNodeWithText(CLOSE).performClick()
                onNodeWithText(RETRY).performClick()
            }

            closed shouldBe 1
            retried shouldBe 1
        }

        test("offers no retry without a handler") {
            runComposeUiTest {
                setContent { Themed { SplitSplash(splash = failed, modifier = bounds) } }

                onNodeWithText(CLOSE).assertIsDisplayed()
                onNodeWithText(RETRY).assertDoesNotExist()
            }
        }
    }

    context("style") {
        test("shows a changed logo size and changed labels") {
            runComposeUiTest {
                setContent {
                    Themed {
                        val style = splitSplashStyle()
                        SplitSplash(
                            splash = Splash(name = NAME, version = "2"),
                            modifier = bounds,
                            style = style.copy(
                                logoSize = 80.dp,
                                labels = style.labels.copy(starting = "Booting", version = { version -> "v$version" }),
                            ),
                            logo = { slot -> Box(modifier = slot.testTag(LOGO)) },
                        )
                    }
                }

                onNodeWithTag(LOGO).assertHeightIsEqualTo(80.dp)
                onNodeWithText("Booting").assertIsDisplayed()
                onNodeWithText("v2").assertIsDisplayed()
            }
        }
    }

    context("slots") {
        test("replaces the logo, the background and the logo of the vendor") {
            runComposeUiTest {
                setContent {
                    Themed {
                        SplitSplash(
                            splash = failed,
                            modifier = bounds,
                            logo = { slot -> Box(modifier = slot.testTag(LOGO)) },
                            background = { slot -> Box(modifier = slot.testTag(BACKGROUND)) },
                            vendorLogo = { slot -> Box(modifier = slot.testTag(VENDOR_LOGO)) },
                        )
                    }
                }

                listOf(LOGO, BACKGROUND, VENDOR_LOGO).forEach { tag -> onNodeWithTag(tag).assertExists() }
            }
        }
    }
})
