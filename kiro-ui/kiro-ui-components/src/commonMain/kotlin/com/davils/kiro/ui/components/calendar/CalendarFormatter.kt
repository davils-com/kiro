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
import kotlinx.datetime.YearMonth

/**
 * Formats the texts that a [Calendar] shows and announces, so that they can follow any language.
 *
 * @since 1.0.0
 */
public interface CalendarFormatter {
    /**
     * Returns the caption of a month, shown above its days.
     *
     * @param month the month.
     * @return the caption, such as `January 2026`.
     * @since 1.0.0
     */
    public fun caption(month: YearMonth): String

    /**
     * Returns the short name of a day of the week, shown above its column.
     *
     * @param day the day of the week.
     * @return the short name, such as `Mo`.
     * @since 1.0.0
     */
    public fun weekday(day: DayOfWeek): String

    /**
     * Returns the description of a day that the calendar announces for its cell.
     *
     * @param date the day.
     * @return the description, such as `Monday, January 5, 2026`.
     * @since 1.0.0
     */
    public fun day(date: LocalDate): String

    /**
     * Returns a date as text.
     *
     * @param date the date.
     * @return the text, such as `January 5, 2026`.
     * @since 1.0.0
     */
    public fun date(date: LocalDate): String

    /**
     * Returns the label that the calendar announces for the button to the previous month.
     *
     * @return the label.
     * @since 1.0.0
     */
    public fun previousMonth(): String

    /**
     * Returns the label that the calendar announces for the button to the next month.
     *
     * @return the label.
     * @since 1.0.0
     */
    public fun nextMonth(): String
}
