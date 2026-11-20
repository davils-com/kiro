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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.IconGraphic
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import kotlinx.datetime.yearMonth

/**
 * A month calendar: a caption with buttons to the previous and next month, the names of the weekdays and a grid of the
 * days of the shown month, whose days can be selected.
 *
 * A click on a day calls [onSelectionChange] with the selection toggled at that day, so the kind of [selection] decides
 * whether one day, several days or a range is selected. The arrow keys move the focus by a day or a week, Page Up and
 * Page Down by a month, and Home and End to the start and end of the week; moving to another month shows it.
 *
 * @param S the kind of the selection.
 * @param selection the selected days.
 * @param onSelectionChange called with the new selection when a day is clicked.
 * @param modifier the modifier of the calendar.
 * @param state the state that holds the shown month; by default it starts at the month of the first selected day, or
 * of today.
 * @param options the first day of the week, the days that are shown or disabled and the formatter of the texts.
 * @param style the style of the calendar.
 * @since 1.0.0
 */
@Composable
public fun <S : CalendarSelection<S>> Calendar(
    selection: S,
    onSelectionChange: (S) -> Unit,
    modifier: Modifier = Modifier,
    state: CalendarState = rememberCalendarState(
        initialMonth = firstSelectedOf(selection)?.yearMonth ?: today().yearMonth,
    ),
    options: CalendarOptions = CalendarOptions(),
    style: CalendarStyle = calendarStyle(),
) {
    val month = state.month
    val settings = options.settled()
    val today = today()
    val isReversed = LocalLayoutDirection.current == LayoutDirection.Rtl
    var focused by remember { mutableStateOf<LocalDate?>(null) }
    var requested by remember { mutableStateOf<LocalDate?>(null) }
    val tabbable = focusTargetOf(month = month, candidates = listOf(focused, firstSelectedOf(selection), today))
    val move = { date: LocalDate, key: Key ->
        val next = movedBy(key = key, date = date, firstDayOfWeek = settings.firstDayOfWeek, isReversed = isReversed)
        next?.let { target ->
            state.show(target.yearMonth)
            focused = target
            requested = target
        }
        next != null
    }
    val frame = Modifier.width(IntrinsicSize.Max).background(style.container).padding(style.padding)
    Column(
        modifier = modifier.then(frame).semantics { isTraversalGroup = true },
        verticalArrangement = Arrangement.spacedBy(style.sectionGap),
    ) {
        CalendarHeader(month = month, state = state, options = settings, style = style, isReversed = isReversed)
        Column {
            Weekdays(options = settings, style = style)
            key(month) {
                weeksOf(month = month, firstDayOfWeek = settings.firstDayOfWeek).forEach { week ->
                    Row(modifier = Modifier.padding(top = style.weekGap)) {
                        if (settings.isWeekNumberShown) WeekNumber(number = isoWeekOf(week.first()), style = style)
                        val view = MonthView(month = month, today = today, tabbable = tabbable, requested = requested)
                        cellsOf(
                            week = week,
                            view = view,
                            selection = selection,
                            isDisabled = settings.isDisabled,
                        ).forEach { cell ->
                            DayOrBlank(
                                cell = cell,
                                options = settings,
                                style = style,
                                onSelect = {
                                    focused = cell.date
                                    onSelectionChange(selection.toggled(cell.date))
                                },
                                onFocused = {
                                    focused = cell.date
                                    requested = null
                                },
                                onKey = { pressed -> move(cell.date, pressed) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DayOrBlank(
    cell: DayCell,
    options: CalendarSettings,
    style: CalendarStyle,
    onSelect: () -> Unit,
    onFocused: () -> Unit,
    onKey: (Key) -> Boolean,
) {
    val isHidden = cell.isOutside && !options.isOutsideDayShown
    if (isHidden) {
        Box(modifier = Modifier.size(style.cellSize))
        return
    }
    CalendarDay(
        cell = cell,
        label = options.formatter.day(cell.date),
        onSelect = onSelect,
        onFocused = onFocused,
        onKey = onKey,
        style = style,
    )
}

@Composable
private fun CalendarHeader(
    month: YearMonth,
    state: CalendarState,
    options: CalendarSettings,
    style: CalendarStyle,
    isReversed: Boolean,
) {
    Box(modifier = Modifier.fillMaxWidth().height(style.cellSize), contentAlignment = Alignment.Center) {
        Text(text = options.formatter.caption(month), style = style.captionStyle)
        NavButton(
            icon = iconOf(isReversed = isReversed, forward = style.previousIcon, backward = style.nextIcon),
            label = options.formatter.previousMonth(),
            modifier = Modifier.align(Alignment.CenterStart),
            style = style,
            onClick = state::showPrevious,
        )
        NavButton(
            icon = iconOf(isReversed = isReversed, forward = style.nextIcon, backward = style.previousIcon),
            label = options.formatter.nextMonth(),
            modifier = Modifier.align(Alignment.CenterEnd),
            style = style,
            onClick = state::showNext,
        )
    }
}

@Composable
private fun NavButton(
    icon: IconGraphic,
    label: String,
    modifier: Modifier,
    style: CalendarStyle,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier.semantics { contentDescription = label },
        variant = ButtonVariant.Ghost,
        style = style.navButton,
    ) {
        Icon(icon = icon, placement = IconPlacement.Inline)
    }
}

@Composable
private fun Weekdays(options: CalendarSettings, style: CalendarStyle) {
    val first = weekStartOf(date = today(), firstDayOfWeek = options.firstDayOfWeek)
    val week = weeksOf(month = first.yearMonth, firstDayOfWeek = options.firstDayOfWeek).first()
    Row {
        if (options.isWeekNumberShown) Box(modifier = Modifier.size(style.cellSize))
        week.forEach { date ->
            Box(modifier = Modifier.width(style.cellSize), contentAlignment = Alignment.Center) {
                Text(
                    text = options.formatter.weekday(date.dayOfWeek),
                    style = style.weekdayStyle,
                    color = style.weekdayColor,
                )
            }
        }
    }
}

@Composable
private fun WeekNumber(number: Int, style: CalendarStyle) {
    Box(modifier = Modifier.size(style.cellSize), contentAlignment = Alignment.Center) {
        Text(text = number.toString(), style = style.weekdayStyle, color = style.weekdayColor)
    }
}

private fun iconOf(isReversed: Boolean, forward: IconGraphic, backward: IconGraphic): IconGraphic {
    if (isReversed) return backward
    return forward
}

private fun firstSelectedOf(selection: CalendarSelection<*>): LocalDate? = when (selection) {
    is CalendarSelection.Single -> selection.date
    is CalendarSelection.Multiple -> selection.dates.minOrNull()
    is CalendarSelection.Range -> selection.start
}
