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

package com.davils.kiro.ui.window.splash.minimal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
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
import com.davils.kiro.ui.window.splash.FAILURE
import com.davils.kiro.ui.window.splash.LOGO
import com.davils.kiro.ui.window.splash.NAME
import com.davils.kiro.ui.window.splash.RETRY
import com.davils.kiro.ui.window.splash.SUBTITLE
import com.davils.kiro.ui.window.splash.VERSION
import com.davils.kiro.ui.window.splash.WORKSPACE
import com.davils.kiro.ui.window.splash.failed
import com.davils.kiro.ui.window.splash.sample
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val bounds: Modifier = Modifier.size(width = 320.dp, height = 320.dp)

@OptIn(ExperimentalTestApi::class)
class MinimalSplashTest : FunSpec({
    context("content") {
        test("shows the logo, the name and a spinner but no other text in both themes") {
            listOf(reducedMotion, Theme.dark()).forEach { theme ->
                runComposeUiTest {
                    setContent { Themed(theme = theme) { MinimalSplash(splash = sample, modifier = bounds) } }

                    onNodeWithText(NAME).assertIsDisplayed()
                    val images = onAllNodes(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))
                    images.assertCountEquals(2)
                    onNodeWithContentDescription("Running").assertIsDisplayed()
                    listOf(SUBTITLE, CONFIGURING, VERSION, COPYRIGHT).forEach { text ->
                        onNodeWithText(text).assertDoesNotExist()
                    }
                    onRoot().captureToImage()
                }
            }
        }
    }

    context("failure") {
        test("shows an icon in place of the spinner and the message and closes or retries on request") {
            var closed = 0
            var retried = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        MinimalSplash(
                            splash = failed,
                            modifier = bounds,
                            onClose = { closed++ },
                            onRetry = { retried++ },
                        )
                    }
                }

                onNodeWithContentDescription("Failed").assertIsDisplayed()
                onNodeWithContentDescription("Running").assertDoesNotExist()
                onNodeWithText(FAILURE).assertIsDisplayed()
                onNodeWithText(WORKSPACE).assertDoesNotExist()
                onNodeWithText(CLOSE).performClick()
                onNodeWithText(RETRY).performClick()
            }

            closed shouldBe 1
            retried shouldBe 1
        }
    }

    context("style") {
        test("hides the name and shows a changed logo size") {
            runComposeUiTest {
                setContent {
                    Themed {
                        MinimalSplash(
                            splash = sample,
                            modifier = bounds,
                            style = minimalSplashStyle().copy(
                                logoSize = 96.dp,
                                cardPadding = 12.dp,
                                isNameShown = false,
                            ),
                            logo = { slot -> Box(modifier = slot.testTag(LOGO)) },
                        )
                    }
                }

                onNodeWithTag(LOGO).assertHeightIsEqualTo(96.dp)
                onNodeWithText(NAME).assertDoesNotExist()
            }
        }
    }

    context("slots") {
        test("replaces the logo and the backdrop") {
            runComposeUiTest {
                setContent {
                    Themed {
                        MinimalSplash(
                            splash = failed,
                            modifier = bounds,
                            logo = { slot -> Box(modifier = slot.testTag(LOGO)) },
                            backdrop = { slot -> Box(modifier = slot.testTag(BACKGROUND)) },
                        )
                    }
                }

                onNodeWithTag(LOGO).assertExists()
                onNodeWithTag(BACKGROUND).assertExists()
            }
        }
    }
})
