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
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.yearMonth

private const val DAYS_IN_WEEK: Int = 7
private const val THURSDAY_OFFSET: Int = 3

private val dayMoves: Map<Key, (LocalDate, DayOfWeek) -> LocalDate> = mapOf(
    Key.DirectionLeft to { date, _ -> date.minus(DatePeriod(days = 1)) },
    Key.DirectionRight to { date, _ -> date.plus(DatePeriod(days = 1)) },
    Key.DirectionUp to { date, _ -> date.minus(DatePeriod(days = DAYS_IN_WEEK)) },
    Key.DirectionDown to { date, _ -> date.plus(DatePeriod(days = DAYS_IN_WEEK)) },
    Key.PageUp to { date, _ -> date.minus(DatePeriod(months = 1)) },
    Key.PageDown to { date, _ -> date.plus(DatePeriod(months = 1)) },
    Key.MoveHome to { date, first -> weekStartOf(date = date, firstDayOfWeek = first) },
    Key.MoveEnd to { date, first ->
        weekStartOf(
            date = date,
            firstDayOfWeek = first,
        ).plus(DatePeriod(days = DAYS_IN_WEEK - 1))
    },
)

private val mirroredKeys: Map<Key, Key> = mapOf(
    Key.DirectionLeft to Key.DirectionRight,
    Key.DirectionRight to Key.DirectionLeft,
)

internal fun weekStartOf(date: LocalDate, firstDayOfWeek: DayOfWeek): LocalDate {
    val offset = (date.dayOfWeek.isoDayNumber - firstDayOfWeek.isoDayNumber + DAYS_IN_WEEK) % DAYS_IN_WEEK
    return date.minus(DatePeriod(days = offset))
}

internal fun weeksOf(month: YearMonth, firstDayOfWeek: DayOfWeek): List<List<LocalDate>> {
    val start = weekStartOf(date = month.firstDay, firstDayOfWeek = firstDayOfWeek)
    val weeks = generateSequence(start) { week -> week.plus(DatePeriod(days = DAYS_IN_WEEK)) }
    val shown = weeks.takeWhile { week -> week <= month.lastDay }
    return shown.map { week -> List(DAYS_IN_WEEK) { day -> week.plus(DatePeriod(days = day)) } }.toList()
}

internal fun isoWeekOf(date: LocalDate): Int {
    val thursday = date.plus(DatePeriod(days = THURSDAY_OFFSET - (date.dayOfWeek.isoDayNumber - 1)))
    return (thursday.dayOfYear - 1) / DAYS_IN_WEEK + 1
}

internal fun movedBy(key: Key, date: LocalDate, firstDayOfWeek: DayOfWeek, isReversed: Boolean): LocalDate? {
    val effective = mirroredKeys[key]?.takeIf { isReversed } ?: key
    return dayMoves[effective]?.invoke(date, firstDayOfWeek)
}

internal fun focusTargetOf(month: YearMonth, candidates: List<LocalDate?>): LocalDate =
    candidates.firstOrNull { candidate -> candidate?.yearMonth == month } ?: month.firstDay
