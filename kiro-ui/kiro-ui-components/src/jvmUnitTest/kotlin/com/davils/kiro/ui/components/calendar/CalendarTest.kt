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

package com.davils.kiro.ui.components.calendar

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.calendar.fixture.FixedClock
import com.davils.kiro.ui.components.calendar.fixture.fixedDay
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth

private const val NOVEMBER: String = "November 2026"
private const val DISABLED_DAY: Int = 25
private const val OUTSIDE_FLOOR: Float = 0.3f

@OptIn(ExperimentalTestApi::class)
class CalendarTest : FunSpec({
    context("styles") {
        test("use 28 dp cells with rounded-md corners and a primary selection") {
            val light = Theme.light()
            val style = CalendarStyles.Default.resolve(light)

            style.cellSize shouldBe 28.dp
            style.cellRadius shouldBe 8.dp
            style.padding shouldBe 8.dp
            style.day.selectedContainer shouldBe light.colors.primary
            style.day.todayContainer shouldBe light.colors.muted
            style.navButton.height shouldBe 28.dp
        }
    }

    context("outside days") {
        test("are grayed out more strongly in light mode than in dark mode") {
            val light = Theme.light()
            val dark = Theme.dark()

            val lightDay = CalendarStyles.Default.resolve(light).day
            val darkDay = CalendarStyles.Default.resolve(dark).day

            lightDay.outsideContent shouldBe light.colors.mutedForeground.copy(alpha = 0.5f)
            darkDay.outsideContent shouldBe dark.colors.mutedForeground
        }
    }

    context("formatter") {
        test("writes captions, weekdays and day labels in English unless the options bring a formatter") {
            val formatter = EnglishCalendarFormatter(previous = "Back", next = "On")

            formatter.caption(YearMonth(year = 2026, month = 11)) shouldBe "November 2026"
            formatter.weekday(DayOfWeek.MONDAY) shouldBe "Mo"
            formatter.day(fixedDay) shouldBe "Tuesday, November 10, 2026"
            formatter.date(fixedDay) shouldBe "November 10, 2026"
            listOf(formatter.previousMonth(), formatter.nextMonth()) shouldBe listOf("Back", "On")
        }
    }

    context("calendar") {
        test("shows the month of today and selects a clicked day") {
            var selection by mutableStateOf(CalendarSelection.Single(null))

            runComposeUiTest {
                setContent {
                    Themed {
                        CompositionLocalProvider(LocalClock provides FixedClock, LocalTimeZone provides TimeZone.UTC) {
                            Calendar(selection = selection, onSelectionChange = { selection = it })
                        }
                    }
                }

                onNodeWithText(NOVEMBER).assertExists()
                onNodeWithContentDescription("Friday, November 20, 2026").performClick()
                onNodeWithContentDescription("Friday, November 20, 2026").assertIsSelected()
                onNodeWithContentDescription("Go to the next month").performClick()
                onNodeWithText("December 2026").assertExists()
                onNodeWithContentDescription("Go to the previous month").performClick()
            }

            selection shouldBe CalendarSelection.Single(LocalDate(year = 2026, month = 11, day = 20))
        }

        test("shows the days of the previous month gray from the first frame of the next month") {
            runComposeUiTest {
                mainClock.autoAdvance = false
                setContent {
                    Themed {
                        CompositionLocalProvider(LocalClock provides FixedClock, LocalTimeZone provides TimeZone.UTC) {
                            Calendar(selection = CalendarSelection.Single(null), onSelectionChange = {})
                        }
                    }
                }
                onNodeWithContentDescription("Go to the next month").performClick()
                mainClock.advanceTimeByFrame()

                val image = onNodeWithContentDescription("Sunday, November 29, 2026").captureToImage()
                val pixels = image.toPixelMap()
                val darkest = (0 until pixels.width * pixels.height).minOf { index ->
                    pixels[index % pixels.width, index / pixels.width].red
                }

                darkest shouldBeGreaterThan OUTSIDE_FLOOR
            }
        }

        test("moves the focus with the arrow keys across months") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Calendar(
                            selection = CalendarSelection.Range(
                                start = LocalDate(year = 2026, month = 11, day = 2),
                                end = LocalDate(year = 2026, month = 11, day = 12),
                            ),
                            onSelectionChange = {},
                            options = CalendarOptions(
                                firstDayOfWeek = DayOfWeek.MONDAY,
                                isWeekNumberShown = true,
                                isDisabled = { date -> date.day == DISABLED_DAY },
                            ),
                        )
                    }
                }

                val start = onNodeWithContentDescription("Monday, November 2, 2026")
                start.requestFocus()
                start.performKeyInput { pressKey(Key.DirectionUp) }
                onNode(isFocused()).performKeyInput { pressKey(Key.A) }
                onNodeWithText("October 2026").assertExists()
                onNodeWithContentDescription("Monday, October 26, 2026").assertExists()
            }
        }

        test("hides outside days and mirrors the navigation from right to left") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Direction(direction = LayoutDirection.Rtl) {
                            Calendar(
                                selection = CalendarSelection.Multiple(setOf(fixedDay)),
                                onSelectionChange = {},
                                options = CalendarOptions(isOutsideDayShown = false),
                            )
                        }
                    }
                }

                onNodeWithText(NOVEMBER).assertExists()
                onNodeWithContentDescription("Sunday, November 1, 2026").assertExists()
                onNodeWithContentDescription("Saturday, October 31, 2026").assertDoesNotExist()
            }
        }
    }
})
