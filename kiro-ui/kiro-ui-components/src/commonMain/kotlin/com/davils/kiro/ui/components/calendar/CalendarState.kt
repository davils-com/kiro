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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minusMonth
import kotlinx.datetime.plusMonth
import kotlinx.datetime.yearMonth

/**
 * The state of a [Calendar]: the month it shows.
 *
 * @param initialMonth the month shown first.
 * @since 1.0.0
 */
@Stable
public class CalendarState(initialMonth: YearMonth) {
    /**
     * The month the calendar shows.
     *
     * @since 1.0.0
     */
    public var month: YearMonth by mutableStateOf(initialMonth)
        private set

    /**
     * Shows a month.
     *
     * @param month the month to show.
     * @since 1.0.0
     */
    public fun show(month: YearMonth) {
        this.month = month
    }

    /**
     * Shows the month before the shown month.
     *
     * @since 1.0.0
     */
    public fun showPrevious() {
        month = month.minusMonth()
    }

    /**
     * Shows the month after the shown month.
     *
     * @since 1.0.0
     */
    public fun showNext() {
        month = month.plusMonth()
    }
}

/**
 * Creates a [CalendarState] and remembers it across recompositions.
 *
 * @param initialMonth the month shown first; later changes of it are ignored.
 * @return the remembered state.
 * @since 1.0.0
 */
@Composable
public fun rememberCalendarState(initialMonth: YearMonth = today().yearMonth): CalendarState =
    remember { CalendarState(initialMonth) }
