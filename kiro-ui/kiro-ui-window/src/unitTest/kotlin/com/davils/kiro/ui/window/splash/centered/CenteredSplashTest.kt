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

package com.davils.kiro.ui.window.splash.centered

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
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
import io.kotest.matchers.shouldNotBe
import kotlin.math.abs

private const val LATER: Long = 1_000
private const val MOMENT: Long = 200
private const val HALF: Float = 0.5f
private const val PIXEL: Float = 1f

private val polite: SemanticsMatcher =
    SemanticsMatcher.expectValue(SemanticsProperties.LiveRegion, LiveRegionMode.Polite)

private val indeterminate: SemanticsMatcher =
    SemanticsMatcher.expectValue(SemanticsProperties.ProgressBarRangeInfo, ProgressBarRangeInfo.Indeterminate)

private val bounds: Modifier = Modifier.size(width = 520.dp, height = 440.dp)

private fun determinate(fraction: Float): SemanticsMatcher = SemanticsMatcher.expectValue(
    SemanticsProperties.ProgressBarRangeInfo,
    ProgressBarRangeInfo(current = fraction, range = 0f..1f),
)

private val ring: SemanticsMatcher = SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo)

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.ringFrames(splash: Splash, theme: Theme): Pair<List<Int>, List<Int>> {
    mainClock.autoAdvance = false
    setContent {
        Themed(theme = theme) {
            CenteredSplash(
                splash = splash,
                modifier = bounds,
                logo = { slot -> Box(modifier = slot) },
                backdrop = { slot -> Box(modifier = slot) },
            )
        }
    }
    mainClock.advanceTimeBy(MOMENT)
    val first = onNode(ring).pixels()
    mainClock.advanceTimeBy(MOMENT)
    return first to onNode(ring).pixels()
}

private fun SemanticsNodeInteraction.pixels(): List<Int> {
    val map = captureToImage().toPixelMap()
    return List(map.width * map.height) { index -> map[index % map.width, index / map.width].hashCode() }
}

@OptIn(ExperimentalTestApi::class)
class CenteredSplashTest : FunSpec({
    context("content") {
        test("shows the name, the subtitle, the ring, the step, the version and the copyright in both themes") {
            listOf(reducedMotion, Theme.dark()).forEach { theme ->
                runComposeUiTest {
                    setContent { Themed(theme = theme) { CenteredSplash(splash = sample, modifier = bounds) } }
                    mainClock.advanceTimeBy(LATER)

                    listOf(NAME, SUBTITLE, CONFIGURING, VERSION, COPYRIGHT).forEach { text ->
                        onNodeWithText(text).assertIsDisplayed()
                    }
                    onNode(determinate(HALF)).assertIsDisplayed()
                    onNodeWithText(MOUNTING).assertDoesNotExist()
                    onRoot().captureToImage()
                }
            }
        }

        test("announces the current step politely") {
            runComposeUiTest {
                setContent { Themed { CenteredSplash(splash = sample, modifier = bounds) } }

                onNodeWithText(CONFIGURING).assert(polite)
            }
        }

        test("says that it is starting without steps and leaves out what the splash does not name") {
            runComposeUiTest {
                setContent { Themed { CenteredSplash(splash = Splash(name = NAME), modifier = bounds) } }

                onNodeWithText("Starting…").assertIsDisplayed()
                listOf(SUBTITLE, VERSION, COPYRIGHT).forEach { text -> onNodeWithText(text).assertDoesNotExist() }
            }
        }

        test("replaces the step when the next one runs, without motion") {
            var current by mutableStateOf(sample.copy(steps = runningAt(0)))

            runComposeUiTest {
                setContent { Themed(theme = reducedMotion) { CenteredSplash(splash = current, modifier = bounds) } }
                onNodeWithText(MOUNTING).assertIsDisplayed()

                current = sample.copy(steps = runningAt(2))
                waitForIdle()

                onNodeWithText(CONFIGURING).assertIsDisplayed()
                onNodeWithText(MOUNTING).assertDoesNotExist()
            }
        }

        test("keeps the ring in the center of the splash") {
            runComposeUiTest {
                setContent { Themed { CenteredSplash(splash = sample, modifier = bounds) } }

                val center = onNode(ring).fetchSemanticsNode().boundsInRoot.center
                val root = onRoot().fetchSemanticsNode().boundsInRoot.center
                (abs(center.y - root.y) < PIXEL) shouldBe true
                (abs(center.x - root.x) < PIXEL) shouldBe true
            }
        }
    }

    context("ring") {
        test("follows the progress and jumps to it under reduced motion") {
            var current by mutableStateOf(Splash(name = NAME, progress = 0f))

            runComposeUiTest {
                setContent { Themed(theme = reducedMotion) { CenteredSplash(splash = current, modifier = bounds) } }
                onNode(determinate(0f)).assertExists()

                current = current.copy(progress = 1f)
                waitForIdle()

                onNode(determinate(1f)).assertExists()
            }
        }

        test("eases to a new progress with motion") {
            var current by mutableStateOf(Splash(name = NAME, progress = 0f))

            runComposeUiTest {
                mainClock.autoAdvance = false
                setContent {
                    Themed(theme = Theme.light()) {
                        CenteredSplash(splash = current, modifier = bounds, backdrop = { slot -> Box(modifier = slot) })
                    }
                }
                mainClock.advanceTimeBy(MOMENT)
                val before = onNode(ring).pixels()

                current = current.copy(progress = 1f)
                mainClock.advanceTimeBy(LATER)

                onNode(determinate(1f)).assertExists()
                onNode(ring).pixels() shouldNotBe before
            }
        }

        test("turns while the progress is unknown") {
            runComposeUiTest {
                val (first, second) = ringFrames(splash = Splash(name = NAME), theme = Theme.light())

                onNode(indeterminate).assertExists()
                second shouldNotBe first
            }
        }

        test("stands still while the progress is unknown under reduced motion") {
            runComposeUiTest {
                val (first, second) = ringFrames(splash = Splash(name = NAME), theme = reducedMotion)

                onNode(indeterminate).assertExists()
                second shouldBe first
            }
        }

        test("stops turning once the start failed") {
            runComposeUiTest {
                val (first, second) = ringFrames(splash = Splash(name = NAME, failure = FAILURE), theme = Theme.light())

                second shouldBe first
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
                        CenteredSplash(
                            splash = failed,
                            modifier = bounds,
                            onClose = { closed++ },
                            onRetry = { retried++ },
                        )
                    }
                }

                onNodeWithText(WORKSPACE).assertIsDisplayed()
                onNodeWithText(FAILURE).assertIsDisplayed()
                onNodeWithText(FAILURE).assert(polite)
                onNodeWithText(CLOSE).performClick()
                onNodeWithText(RETRY).performClick()
            }

            closed shouldBe 1
            retried shouldBe 1
        }

        test("raises the ring as far as the failure needs room in a small splash") {
            runComposeUiTest {
                setContent {
                    Themed(theme = Theme.dark()) {
                        CenteredSplash(splash = failed, modifier = Modifier.size(width = 440.dp, height = 320.dp))
                    }
                }

                val center = onNode(ring).fetchSemanticsNode().boundsInRoot.center
                val root = onRoot().fetchSemanticsNode().boundsInRoot
                (center.y < root.center.y) shouldBe true
                (onNodeWithText(CLOSE).fetchSemanticsNode().boundsInRoot.bottom <= root.bottom) shouldBe true
            }
        }

        test("offers no retry without a handler") {
            runComposeUiTest {
                setContent { Themed { CenteredSplash(splash = failed, modifier = bounds) } }

                onNodeWithText(CLOSE).assertIsDisplayed()
                onNodeWithText(RETRY).assertDoesNotExist()
            }
        }
    }

    context("style") {
        test("shows a changed logo size, a changed ring and a changed label") {
            runComposeUiTest {
                setContent {
                    Themed {
                        val style = centeredSplashStyle()
                        CenteredSplash(
                            splash = Splash(name = NAME),
                            modifier = bounds,
                            style = style.copy(
                                logoSize = 72.dp,
                                ringGap = 10.dp,
                                ringWidth = 4.dp,
                                labels = style.labels.copy(starting = "Booting"),
                            ),
                            logo = { slot -> Box(modifier = slot.testTag(LOGO)) },
                        )
                    }
                }

                onNodeWithTag(LOGO).assertHeightIsEqualTo(72.dp)
                onNode(ring).assertHeightIsEqualTo(100.dp)
                onNodeWithText("Booting").assertIsDisplayed()
            }
        }
    }

    context("slots") {
        test("replaces the logo and the backdrop") {
            runComposeUiTest {
                setContent {
                    Themed {
                        CenteredSplash(
                            splash = failed,
                            modifier = bounds,
                            logo = { slot -> Box(modifier = slot.testTag(LOGO)) },
                            backdrop = { slot -> Box(modifier = slot.testTag(BACKGROUND)) },
                        )
                    }
                }

                onNodeWithTag(LOGO).assertExists()
                onNodeWithTag(BACKGROUND).assertWidthIsEqualTo(520.dp)
            }
        }
    }
})
