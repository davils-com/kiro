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

/**
 * The settings of a [Calendar] apart from its look: the first day of the week, the days it shows and disables and the
 * formatter of its texts.
 *
 * @property firstDayOfWeek the day each week starts with, or `null` for Sunday.
 * @property isOutsideDayShown whether the days of the previous and next month that fill the first and last week are
 * shown.
 * @property isWeekNumberShown whether the ISO number of each week is shown before it.
 * @property isDisabled decides for a day whether it is disabled, so that it can neither be selected nor focused.
 * @property formatter the formatter of the texts, or `null` for English texts.
 * @since 1.0.0
 */
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
     * @param firstDayOfWeek the new value of [firstDayOfWeek].
     * @param isOutsideDayShown the new value of [isOutsideDayShown].
     * @param isWeekNumberShown the new value of [isWeekNumberShown].
     * @param isDisabled the new value of [isDisabled].
     * @param formatter the new value of [formatter].
     * @return the copy.
     * @since 1.0.0
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
        "firstDayOfWeek=${firstDayOfWeek ?: "null"}, " +
        "isOutsideDayShown=$isOutsideDayShown, " +
        "isWeekNumberShown=$isWeekNumberShown, " +
        "isDisabled=$isDisabled, " +
        "formatter=${formatter ?: "null"})"

    private fun values(): List<Any?> = listOf(
        firstDayOfWeek,
        isOutsideDayShown,
        isWeekNumberShown,
        isDisabled,
        formatter,
    )
}
