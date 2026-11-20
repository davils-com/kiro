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

internal fun fromStart(first: LocalDate, last: LocalDate?, date: LocalDate): CalendarSelection.Range =
    last?.let { end -> reshaped(first = first, last = end, date = date) } ?: extended(first = first, date = date)

private fun extended(first: LocalDate, date: LocalDate): CalendarSelection.Range {
    if (date < first) return CalendarSelection.Range(start = date, end = first)
    return CalendarSelection.Range(start = first, end = date)
}

private fun reshaped(first: LocalDate, last: LocalDate, date: LocalDate): CalendarSelection.Range {
    val isWhole = first == date && last == date
    if (isWhole) return CalendarSelection.Range(start = null, end = null)
    return trimmed(first = first, last = last, date = date) ?: stretched(first = first, last = last, date = date)
}

private fun trimmed(first: LocalDate, last: LocalDate, date: LocalDate): CalendarSelection.Range? {
    if (last == date) return CalendarSelection.Range(start = last, end = null)
    return CalendarSelection.Range(start = first, end = null).takeIf { first == date }
}

private fun stretched(first: LocalDate, last: LocalDate, date: LocalDate): CalendarSelection.Range {
    if (date < first) return CalendarSelection.Range(start = date, end = last)
    return CalendarSelection.Range(start = first, end = date)
}
