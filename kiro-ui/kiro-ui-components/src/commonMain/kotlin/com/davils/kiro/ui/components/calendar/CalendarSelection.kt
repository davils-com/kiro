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

@Immutable
public sealed interface CalendarSelection<S : CalendarSelection<S>> {
    public fun toggled(date: LocalDate): S

    @Immutable
    public class Single(
        public val date: LocalDate?,
    ) : CalendarSelection<Single> {
        override fun toggled(date: LocalDate): Single = Single(date.takeUnless { it == this.date })

        /**
         * Returns a copy of this [Single] with the given values replaced and every other value kept.
         *
         * @since 0.1.0
         */
        public fun copy(date: LocalDate? = this.date): Single = Single(
            date = date,
        )

        override fun equals(other: Any?): Boolean = other is Single && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Single(" +
            "date=$date)"

        private fun values(): List<Any?> = listOf(
            date,
        )
    }

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
         * @since 0.1.0
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
         * @since 0.1.0
         */
        public fun copy(start: LocalDate? = this.start, end: LocalDate? = this.end): Range = Range(
            start = start,
            end = end,
        )

        override fun equals(other: Any?): Boolean = other is Range && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Range(" +
            "start=$start, " +
            "end=$end)"

        private fun values(): List<Any?> = listOf(
            start,
            end,
        )
    }
}
