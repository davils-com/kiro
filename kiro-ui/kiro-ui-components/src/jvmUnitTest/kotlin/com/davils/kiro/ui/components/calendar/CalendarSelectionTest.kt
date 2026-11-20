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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate

class CalendarSelectionTest : FunSpec({
    val first = LocalDate(year = 2026, month = 11, day = 10)
    val second = LocalDate(year = 2026, month = 11, day = 14)
    val third = LocalDate(year = 2026, month = 11, day = 20)

    context("single") {
        test("selects a day and releases it on a second click") {
            val selected = CalendarSelection.Single(null).toggled(first)

            selected shouldBe CalendarSelection.Single(first)
            selected.toggled(first) shouldBe CalendarSelection.Single(null)
            selected.roleOf(first) shouldBe DayRole.Selected
            selected.roleOf(second) shouldBe DayRole.None
        }
    }

    context("multiple") {
        test("adds and removes days") {
            val selected = CalendarSelection.Multiple(emptySet()).toggled(first)

            selected.toggled(second).dates shouldBe setOf(first, second)
            selected.toggled(first).dates shouldBe emptySet()
            selected.roleOf(first) shouldBe DayRole.Selected
        }
    }

    context("range") {
        val empty = CalendarSelection.Range(start = null, end = null)
        val open = CalendarSelection.Range(start = second, end = null)
        val closed = CalendarSelection.Range(start = first, end = third)

        test("starts, extends and turns around an open range") {
            empty.toggled(second) shouldBe open
            open.toggled(third) shouldBe CalendarSelection.Range(start = second, end = third)
            open.toggled(first) shouldBe CalendarSelection.Range(start = first, end = second)
        }

        test("reshapes a closed range like react-day-picker") {
            CalendarSelection.Range(start = first, end = first).toggled(first) shouldBe empty
            closed.toggled(third) shouldBe CalendarSelection.Range(start = third, end = null)
            closed.toggled(first) shouldBe CalendarSelection.Range(start = first, end = null)
            closed.toggled(
                LocalDate(year = 2026, month = 11, day = 1),
            ) shouldBe CalendarSelection.Range(start = LocalDate(year = 2026, month = 11, day = 1), end = third)
            closed.toggled(second) shouldBe CalendarSelection.Range(start = first, end = second)
        }

        test("marks its start, middle and end") {
            closed.roleOf(first) shouldBe DayRole.RangeStart
            closed.roleOf(second) shouldBe DayRole.RangeMiddle
            closed.roleOf(third) shouldBe DayRole.RangeEnd
            closed.roleOf(LocalDate(year = 2026, month = 12, day = 1)) shouldBe DayRole.None
            open.roleOf(second) shouldBe DayRole.RangeEdges
            empty.roleOf(second) shouldBe DayRole.None
        }
    }
})
