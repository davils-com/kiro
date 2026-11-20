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

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.YearMonth
import kotlinx.datetime.isoDayNumber

private val monthNames: List<String> = listOf(
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
)

private val dayNames: List<String> = listOf(
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
    "Sunday",
)

private const val SHORT_WEEKDAY: Int = 2

internal class EnglishCalendarFormatter(private val previous: String, private val next: String) : CalendarFormatter {
    override fun caption(month: YearMonth): String = "${nameOf(month.month)} ${month.year}"

    override fun weekday(day: DayOfWeek): String = nameOf(day).take(SHORT_WEEKDAY)

    override fun day(date: LocalDate): String = "${nameOf(date.dayOfWeek)}, ${date(date)}"

    override fun date(date: LocalDate): String = "${nameOf(date.month)} ${date.day}, ${date.year}"

    override fun previousMonth(): String = previous

    override fun nextMonth(): String = next

    private fun nameOf(month: Month): String = monthNames[month.ordinal]

    private fun nameOf(day: DayOfWeek): String = dayNames[day.isoDayNumber - 1]
}
