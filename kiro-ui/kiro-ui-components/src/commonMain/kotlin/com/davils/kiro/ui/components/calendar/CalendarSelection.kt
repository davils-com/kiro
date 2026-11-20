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
import kotlinx.datetime.LocalDate

/**
 * The days selected in a [Calendar]: a single day, several days or a range of days.
 *
 * @param S the kind of the selection, which [toggled] returns.
 * @since 1.0.0
 */
@Immutable
public sealed interface CalendarSelection<S : CalendarSelection<S>> {
    /**
     * Returns the selection after a click on a day.
     *
     * @param date the day that was clicked.
     * @return the new selection.
     * @since 1.0.0
     */
    public fun toggled(date: LocalDate): S

    /**
     * A selection of at most one day; a click selects a day and a click on the selected day clears the selection.
     *
     * @property date the selected day, or `null` if no day is selected.
     * @since 1.0.0
     */
    @Immutable
    public class Single(
        public val date: LocalDate?,
    ) : CalendarSelection<Single> {
        override fun toggled(date: LocalDate): Single = Single(date.takeUnless { it == this.date })

        /**
         * Returns a copy of this [Single] with the given values replaced and every other value kept.
         *
         * @param date the new value of [date].
         * @return the copy.
         * @since 1.0.0
         */
        public fun copy(date: LocalDate? = this.date): Single = Single(
            date = date,
        )

        override fun equals(other: Any?): Boolean = other is Single && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Single(" +
            "date=${date ?: "null"})"

        private fun values(): List<Any?> = listOf(
            date,
        )
    }

    /**
     * A selection of any number of days; a click adds a day to the selection or removes it.
     *
     * @property dates the selected days.
     * @since 1.0.0
     */
    @Immutable
    public class Multiple(
        public val dates: Set<LocalDate>,
    ) : CalendarSelection<Multiple> {
        override fun toggled(date: LocalDate): Multiple {
            if (date in dates) return Multiple(dates - date)
            return Multiple(dates + date)
        }

        /**
         * Returns a copy of this [Multiple] with the given values replaced and every other value kept.
         *
         * @param dates the new value of [dates].
         * @return the copy.
         * @since 1.0.0
         */
        public fun copy(dates: Set<LocalDate> = this.dates): Multiple = Multiple(
            dates = dates,
        )

        override fun equals(other: Any?): Boolean = other is Multiple && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Multiple(" +
            "dates=$dates)"

        private fun values(): List<Any?> = listOf(
            dates,
        )
    }

    /**
     * A selection of a range of days from a start to an end.
     *
     * The first click sets the start and the second the other end, so that the earlier day becomes the start. A
     * click on the start or the end of a range keeps only that day as the start, a click on a day that is both clears
     * the selection, and a click on another day moves an end of the range there: the start if the day lies before it,
     * the end otherwise.
     *
     * @property start the first day of the range, or `null` if no day is selected.
     * @property end the last day of the range, or `null` while only the start is selected.
     * @since 1.0.0
     */
    @Immutable
    public class Range(
        public val start: LocalDate?,
        public val end: LocalDate?,
    ) : CalendarSelection<Range> {
        override fun toggled(date: LocalDate): Range =
            start?.let { first -> fromStart(first = first, last = end, date = date) } ?: Range(start = date, end = null)

        /**
         * Returns a copy of this [Range] with the given values replaced and every other value kept.
         *
         * @param start the new value of [start].
         * @param end the new value of [end].
         * @return the copy.
         * @since 1.0.0
         */
        public fun copy(start: LocalDate? = this.start, end: LocalDate? = this.end): Range = Range(
            start = start,
            end = end,
        )

        override fun equals(other: Any?): Boolean = other is Range && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Range(" +
            "start=${start ?: "null"}, " +
            "end=${end ?: "null"})"

        private fun values(): List<Any?> = listOf(
            start,
            end,
        )
    }
}
