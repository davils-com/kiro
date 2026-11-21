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

package com.davils.kiro.ui.window.splash.checklist

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
import androidx.compose.ui.test.assertWidthIsEqualTo
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
import com.davils.kiro.ui.window.splash.FAILURE
import com.davils.kiro.ui.window.splash.LOGO
import com.davils.kiro.ui.window.splash.MOUNTING
import com.davils.kiro.ui.window.splash.NAME
import com.davils.kiro.ui.window.splash.PLUGINS
import com.davils.kiro.ui.window.splash.RETRY
import com.davils.kiro.ui.window.splash.Splash
import com.davils.kiro.ui.window.splash.SplashStepStatus
import com.davils.kiro.ui.window.splash.VERSION
import com.davils.kiro.ui.window.splash.WINDOWS
import com.davils.kiro.ui.window.splash.WORKSPACE
import com.davils.kiro.ui.window.splash.failed
import com.davils.kiro.ui.window.splash.runningAt
import com.davils.kiro.ui.window.splash.sample
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val bounds: Modifier = Modifier.size(width = 720.dp, height = 440.dp)

@OptIn(ExperimentalTestApi::class)
class ChecklistSplashTest : FunSpec({
    context("content") {
        test("lists every step with its status, the name and the version in both themes") {
            listOf(reducedMotion, Theme.dark()).forEach { theme ->
                runComposeUiTest {
                    setContent { Themed(theme = theme) { ChecklistSplash(splash = sample, modifier = bounds) } }

                    listOf(NAME, VERSION, MOUNTING, PLUGINS, CONFIGURING, WINDOWS).forEach { text ->
                        onNodeWithText(text).assertIsDisplayed()
                    }
                    listOf("Done", "Skipped", "Running", "Pending").forEach { status ->
                        onNodeWithContentDescription(status).assertIsDisplayed()
                    }
                    onRoot().captureToImage()
                }
            }
        }

        test("leaves out the version the splash does not name") {
            runComposeUiTest {
                setContent { Themed { ChecklistSplash(splash = Splash(name = NAME), modifier = bounds) } }

                onNodeWithText(NAME).assertIsDisplayed()
                onNodeWithText(VERSION).assertDoesNotExist()
            }
        }

        test("updates the status of a step when it finishes") {
            var current by mutableStateOf(sample.copy(steps = runningAt(0)))

            runComposeUiTest {
                setContent { Themed { ChecklistSplash(splash = current, modifier = bounds) } }
                onNodeWithContentDescription("Done").assertDoesNotExist()

                current = sample.copy(steps = runningAt(1))
                waitForIdle()

                onNodeWithContentDescription("Done").assertIsDisplayed()
            }
        }
    }

    context("failure") {
        test("marks the failed step, shows the message and closes or retries on request") {
            var closed = 0
            var retried = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        ChecklistSplash(
                            splash = failed,
                            modifier = bounds,
                            onClose = { closed++ },
                            onRetry = { retried++ },
                        )
                    }
                }

                onNodeWithText(WORKSPACE).assertIsDisplayed()
                onNodeWithContentDescription("Failed").assertIsDisplayed()
                onNodeWithText(FAILURE).assertIsDisplayed()
                onNodeWithText(CLOSE).performClick()
                onNodeWithText(RETRY).performClick()
            }

            closed shouldBe 1
            retried shouldBe 1
        }
    }

    context("style") {
        test("shows a changed logo size and a changed description of a status") {
            runComposeUiTest {
                setContent {
                    Themed {
                        val style = checklistSplashStyle()
                        val labels = style.labels.copy(status = { status -> "is ${status.name}" })
                        ChecklistSplash(
                            splash = sample,
                            modifier = bounds,
                            style = style.copy(logoSize = 64.dp, bandFraction = 0.5f, labels = labels),
                            logo = { slot -> Box(modifier = slot.testTag(LOGO)) },
                            backdrop = { slot -> Box(modifier = slot.testTag(BACKGROUND)) },
                        )
                    }
                }

                onNodeWithTag(LOGO).assertHeightIsEqualTo(64.dp)
                onNodeWithTag(BACKGROUND).assertWidthIsEqualTo(360.dp)
                onNodeWithContentDescription("is ${SplashStepStatus.Done.name}").assertIsDisplayed()
            }
        }
    }

    context("slots") {
        test("replaces the logo, the icons of the steps and the backdrop") {
            runComposeUiTest {
                setContent {
                    Themed {
                        ChecklistSplash(
                            splash = failed,
                            modifier = bounds,
                            logo = { slot -> Box(modifier = slot.testTag(LOGO)) },
                            stepIcon = { step, slot -> Box(modifier = slot.testTag(step.id)) },
                            backdrop = { slot -> Box(modifier = slot.testTag(BACKGROUND)) },
                        )
                    }
                }

                onNodeWithTag(LOGO).assertExists()
                onNodeWithTag(BACKGROUND).assertHeightIsEqualTo(440.dp)
                failed.steps.forEach { step -> onNodeWithTag(step.id, useUnmergedTree = true).assertExists() }
                onNodeWithContentDescription("Running").assertDoesNotExist()
            }
        }
    }
})
