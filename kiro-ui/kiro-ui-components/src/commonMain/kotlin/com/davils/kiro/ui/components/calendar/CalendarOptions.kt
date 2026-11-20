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

import androidx.compose.runtime.Immutable
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@Immutable
public class CalendarOptions(
    public val firstDayOfWeek: DayOfWeek? = null,
    public val isOutsideDayShown: Boolean = true,
    public val isWeekNumberShown: Boolean = false,
    public val isDisabled: (LocalDate) -> Boolean = { false },
    public val formatter: CalendarFormatter? = null,
) {
    /**
     * Returns a copy of this [CalendarOptions] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        firstDayOfWeek: DayOfWeek? = this.firstDayOfWeek,
        isOutsideDayShown: Boolean = this.isOutsideDayShown,
        isWeekNumberShown: Boolean = this.isWeekNumberShown,
        isDisabled: (LocalDate) -> Boolean = this.isDisabled,
        formatter: CalendarFormatter? = this.formatter,
    ): CalendarOptions = CalendarOptions(
        firstDayOfWeek = firstDayOfWeek,
        isOutsideDayShown = isOutsideDayShown,
        isWeekNumberShown = isWeekNumberShown,
        isDisabled = isDisabled,
        formatter = formatter,
    )

    override fun equals(other: Any?): Boolean = other is CalendarOptions && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CalendarOptions(" +
        "firstDayOfWeek=$firstDayOfWeek, " +
        "isOutsideDayShown=$isOutsideDayShown, " +
        "isWeekNumberShown=$isWeekNumberShown, " +
        "isDisabled=$isDisabled, " +
        "formatter=$formatter)"

    private fun values(): List<Any?> = listOf(
        firstDayOfWeek,
        isOutsideDayShown,
        isWeekNumberShown,
        isDisabled,
        formatter,
    )
}
