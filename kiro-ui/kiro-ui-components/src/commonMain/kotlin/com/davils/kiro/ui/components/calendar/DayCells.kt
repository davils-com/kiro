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
import kotlinx.datetime.yearMonth

internal fun cellsOf(
    week: List<LocalDate>,
    view: MonthView,
    selection: CalendarSelection<*>,
    isDisabled: (LocalDate) -> Boolean,
): List<DayCell> = week.mapIndexed { index, date ->
    DayCell(
        date = date,
        role = selection.roleOf(date),
        isToday = date == view.today,
        isOutside = date.yearMonth != view.month,
        isDisabled = isDisabled(date),
        isWeekStart = index == 0,
        isWeekEnd = index == week.lastIndex,
        isTabbable = date == view.tabbable,
        isFocusRequested = date == view.requested,
    )
}
