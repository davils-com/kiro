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

import kotlinx.datetime.LocalDate

internal fun CalendarSelection<*>.roleOf(date: LocalDate): DayRole = when (this) {
    is CalendarSelection.Single -> selectedOf(date == this.date)
    is CalendarSelection.Multiple -> selectedOf(date in dates)
    is CalendarSelection.Range -> rangeRoleOf(start = start, end = end ?: start, date = date)
}

private fun selectedOf(isSelected: Boolean): DayRole {
    if (isSelected) return DayRole.Selected
    return DayRole.None
}

private fun rangeRoleOf(start: LocalDate?, end: LocalDate?, date: LocalDate): DayRole {
    val first = start ?: return DayRole.None
    return end?.let { last -> boundedRoleOf(first = first, last = last, date = date) } ?: DayRole.None
}

private fun boundedRoleOf(first: LocalDate, last: LocalDate, date: LocalDate): DayRole {
    if (date == first) return startRoleOf(isAlsoEnd = date == last)
    return endOrMiddleOf(first = first, last = last, date = date)
}

private fun startRoleOf(isAlsoEnd: Boolean): DayRole {
    if (isAlsoEnd) return DayRole.RangeEdges
    return DayRole.RangeStart
}

private fun endOrMiddleOf(first: LocalDate, last: LocalDate, date: LocalDate): DayRole {
    if (date == last) return DayRole.RangeEnd
    val isInside = date > first && date < last
    return DayRole.RangeMiddle.takeIf { isInside } ?: DayRole.None
}
