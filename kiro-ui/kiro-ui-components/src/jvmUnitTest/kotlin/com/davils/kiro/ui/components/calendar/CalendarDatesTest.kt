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

import androidx.compose.ui.input.key.Key
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

private const val FIVE_WEEKS: Int = 5
private const val SIX_WEEKS: Int = 6
private const val WEEK_OF_DATE: Int = 46
private const val LAST_WEEK_OF_2026: Int = 53

class CalendarDatesTest : FunSpec({
    val date = LocalDate(year = 2026, month = 11, day = 10)

    context("weeks") {
        test("cover the month from the first day of the week") {
            val weeks = weeksOf(month = YearMonth(year = 2026, month = 11), firstDayOfWeek = DayOfWeek.SUNDAY)

            weeks.size shouldBe FIVE_WEEKS
            weeks.first().first() shouldBe LocalDate(year = 2026, month = 11, day = 1)
            weeks.last().last() shouldBe LocalDate(year = 2026, month = 12, day = 5)
        }

        test("start on monday when asked") {
            val weeks = weeksOf(month = YearMonth(year = 2026, month = 11), firstDayOfWeek = DayOfWeek.MONDAY)

            weeks.first().first() shouldBe LocalDate(year = 2026, month = 10, day = 26)
            weeks.size shouldBe SIX_WEEKS
        }

        test("number weeks after ISO 8601") {
            isoWeekOf(date) shouldBe WEEK_OF_DATE
            isoWeekOf(LocalDate(year = 2027, month = 1, day = 1)) shouldBe LAST_WEEK_OF_2026
        }
    }

    context("keys") {
        fun move(key: Key, isReversed: Boolean = false) =
            movedBy(key = key, date = date, firstDayOfWeek = DayOfWeek.SUNDAY, isReversed = isReversed)

        fun november(day: Int) = LocalDate(year = 2026, month = 11, day = day)

        test("move by day, week, month and to the ends of the week") {
            move(Key.DirectionRight) shouldBe november(day = 11)
            move(Key.DirectionRight, isReversed = true) shouldBe november(day = 9)
            move(Key.DirectionLeft) shouldBe november(day = 9)
            move(Key.DirectionUp) shouldBe november(day = 3)
            move(Key.DirectionDown) shouldBe november(day = 17)
            move(Key.PageUp) shouldBe LocalDate(year = 2026, month = 10, day = 10)
            move(Key.PageDown) shouldBe LocalDate(year = 2026, month = 12, day = 10)
            move(Key.MoveHome) shouldBe november(day = 8)
            move(Key.MoveEnd) shouldBe november(day = 14)
            move(Key.A).shouldBeNull()
        }
    }

    context("focus") {
        test("prefers the first candidate inside the month") {
            val month = YearMonth(year = 2026, month = 11)

            focusTargetOf(
                month = month,
                candidates = listOf(null, LocalDate(year = 2026, month = 10, day = 1), date),
            ) shouldBe date
            focusTargetOf(month = month, candidates = listOf(null)) shouldBe month.firstDay
        }
    }
})
