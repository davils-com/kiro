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

package com.davils.kiro.ui.components.toast

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val SWIPED: String = "Swipe me"
private const val CLOSED: String = "Close me"
private const val RICH: String = "Deployed"
private const val UPLOAD: String = "Uploading"
private const val SHORT: Float = 20f
private const val FAR: Float = 300f
private const val SETTLE: Long = 1_000L
private const val HALF: Float = 0.5f

@OptIn(ExperimentalTestApi::class)
class ToastInteractionTest : FunSpec({
    context("dismissing") {
        test("springs back after a short swipe and leaves after a long one") {
            val toaster = ToasterState()

            runComposeUiTest {
                setContent { Themed(theme = reducedMotion) { Toaster(toaster = toaster) } }

                toaster.show(title = SWIPED, duration = null)
                waitForIdle()
                val toast = onNodeWithText(SWIPED)
                toast.performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = SHORT, y = 0f))
                    release()
                }
                waitForIdle()
                toaster.toasts.size shouldBe 1
                toast.performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = FAR, y = 0f))
                    release()
                }
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
                toaster.toasts.size shouldBe 0
            }
        }

        test("resists a swipe away from the edge") {
            val toaster = ToasterState()

            runComposeUiTest {
                setContent {
                    Themed(
                        theme = reducedMotion,
                    ) { Toaster(toaster = toaster, position = ToastPosition.TopStart) }
                }

                toaster.show(title = SWIPED, duration = null)
                waitForIdle()
                onNodeWithText(SWIPED).performMouseInput {
                    moveTo(center)
                    press()
                    moveBy(Offset(x = FAR, y = 0f))
                    release()
                }
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
                toaster.toasts.size shouldBe 1
            }
        }

        test("closes with the close button and runs the exit before it removes the toast") {
            val toaster = ToasterState()
            var isClosed = false

            runComposeUiTest {
                setContent { Themed(theme = reducedMotion) { Toaster(toaster = toaster, isCloseButtonShown = true) } }

                toaster.show(title = CLOSED, duration = null, onDismiss = { isClosed = true })
                waitForIdle()
                onNodeWithContentDescription("Close").performClick()
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
                onNodeWithText(CLOSED).assertDoesNotExist()
            }

            isClosed shouldBe true
        }
    }

    context("variants") {
        test("tints rich toasts, shows progress and a timer") {
            val toaster = ToasterState()

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Toaster(
                            toaster = toaster,
                            position = ToastPosition.BottomCenter,
                            isRich = true,
                            isTimerShown = true,
                        )
                    }
                }

                toaster.show(title = RICH, type = ToastType.Success)
                toaster.show(title = "Warning", type = ToastType.Warning)
                val upload = toaster.show(title = UPLOAD, progress = ToastProgress.Indeterminate)
                waitForIdle()
                toaster.update(upload) { copy(progress = ToastProgress.Determinate(HALF)) }
                waitForIdle()
                onNodeWithText(RICH).assertExists()
                onNodeWithText(UPLOAD).performMouseInput { moveTo(center) }
                waitForIdle()
                onNodeWithText(UPLOAD).assertExists()
            }
        }
    }
})
