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

import androidx.compose.runtime.remember
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.seconds

private const val SAVED: String = "Event has been created"
private const val WAIT_MILLIS: Long = 4500
private const val OFFSET: Int = 24
private const val WIDE: Int = 1000
private const val HIGH: Int = 800
private const val TOAST_WIDTH: Int = 356
private const val TOAST_HEIGHT: Int = 60
private const val GAP: Int = 14
private const val VISIBLE: Int = 3
private const val MISSING: Long = 99L

@OptIn(ExperimentalTestApi::class)
class ToasterTest : FunSpec({
    context("toaster") {
        test("shows, updates and dismisses toasts") {
            val toaster = ToasterState()

            var dismissed = 0
            val first = toaster.show(title = "First", onDismiss = { dismissed++ })
            val loading = toaster.show(title = "Loading", type = ToastType.Loading)
            toaster.update(loading) { copy(title = "Done", type = ToastType.Success, duration = 4.seconds) }
            toaster.dismiss(first)
            toaster.dismiss(ToastId(MISSING))

            toaster.isLeaving(first) shouldBe true
            toaster.remove(first)
            toaster.remove(first)
            toaster.toasts.map { toast -> toast.title } shouldBe listOf("Done")
            toaster.toasts.single().duration shouldBe 4.seconds
            dismissed shouldBe 1
            toaster.dismissAll()
            toaster.isLeaving(loading) shouldBe true
        }

        test("styles are 356 wide popover cards with a 14 gap") {
            val style = ToastStyles.Default.resolve(Theme.light())

            style.width shouldBe 356.dp
            style.expandedGap shouldBe 14.dp
            style.visibleCount shouldBe VISIBLE
        }
    }

    context("stack") {
        test("peeks behind collapsed and spreads out expanded") {
            val style = ToastStyles.Default.resolve(Theme.light())
            val toasts = listOf(ToastId(2), ToastId(1)).map { id ->
                Toast(
                    id = id,
                    title = "t",
                    description = null,
                    type = ToastType.Default,
                    action = null,
                    duration = null,
                )
            }
            val heights = toasts.associate { toast -> toast.id to TOAST_HEIGHT }

            val collapsed = stackOf(
                toasts = toasts,
                heights = heights,
                isExpanded = false,
                style = style,
                density = Density(1f),
            )
            val expanded = stackOf(
                toasts = toasts,
                heights = heights,
                isExpanded = true,
                style = style,
                density = Density(1f),
            )

            collapsed.offsets[ToastId(1)] shouldBe GAP
            expanded.offsets[ToastId(1)] shouldBe TOAST_HEIGHT + GAP
            expanded.height shouldBe TOAST_HEIGHT * 2 + GAP
        }

        test("sits in every corner of the window") {
            val window = IntSize(width = WIDE, height = HIGH)
            val toast = IntSize(width = TOAST_WIDTH, height = TOAST_HEIGHT)
            val anchor = IntRect(left = 0, top = 0, right = 0, bottom = 0)

            fun place(position: ToastPosition, direction: LayoutDirection = LayoutDirection.Ltr) =
                CornerPositionProvider(position = position, offset = OFFSET)
                    .calculatePosition(
                        anchorBounds = anchor,
                        windowSize = window,
                        layoutDirection = direction,
                        popupContentSize = toast,
                    )

            place(
                ToastPosition.BottomEnd,
            ) shouldBe IntOffset(x = WIDE - TOAST_WIDTH - OFFSET, y = HIGH - TOAST_HEIGHT - OFFSET)
            place(ToastPosition.TopStart) shouldBe IntOffset(x = OFFSET, y = OFFSET)
            place(
                ToastPosition.TopStart,
                LayoutDirection.Rtl,
            ) shouldBe IntOffset(x = WIDE - TOAST_WIDTH - OFFSET, y = OFFSET)
            place(ToastPosition.BottomEnd, LayoutDirection.Rtl).x shouldBe OFFSET
            place(ToastPosition.TopCenter) shouldBe IntOffset(x = (WIDE - TOAST_WIDTH) / 2, y = OFFSET)
            place(ToastPosition.BottomStart).x shouldBe OFFSET
            place(ToastPosition.BottomCenter).y shouldBe HIGH - TOAST_HEIGHT - OFFSET
            place(ToastPosition.TopEnd).y shouldBe OFFSET
        }
    }

    context("host") {
        test("shows toasts until they time out and runs their action") {
            val toaster = ToasterState()
            var isUndone = false

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) { Toaster(toaster = toaster) }
                }

                toaster.show(
                    title = SAVED,
                    description = "Sunday, December 03",
                    action = ToastAction(label = "Undo") { isUndone = true },
                )
                toaster.show(title = "Saving", type = ToastType.Loading)
                waitForIdle()
                onNodeWithText(SAVED).assertExists()
                onNodeWithText(SAVED).performMouseInput { moveTo(center) }
                waitForIdle()
                onNodeWithText("Undo").performClick()
                waitForIdle()
                onNodeWithText(SAVED).assertDoesNotExist()
                toaster.show(title = "Timed", type = ToastType.Warning)
                waitForIdle()
                mainClock.advanceTimeBy(WAIT_MILLIS)
                waitForIdle()
                onNodeWithText("Timed").assertDoesNotExist()
            }

            isUndone shouldBe true
        }

        test("spins the loading icon with motion") {
            runComposeUiTest {
                setContent {
                    Themed {
                        val toaster = remember {
                            ToasterState().apply {
                                show(
                                    title = "Spin",
                                    type = ToastType.Loading,
                                )
                            }
                        }
                        Toaster(toaster = toaster, position = ToastPosition.TopCenter)
                    }
                }

                onNodeWithText("Spin").assertExists()
            }
        }
    }
})
