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

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StateColors

private val spans: Set<DayRole> = setOf(DayRole.RangeStart, DayRole.RangeMiddle, DayRole.RangeEnd)

internal fun dayColorsOf(cell: DayCell, style: CalendarDayStyle): StateColors = StateColors(
    container = containerOf(cell = cell, style = style),
    content = StateValue(default = contentOf(cell = cell, style = style)),
    border = StateValue(default = Color.Transparent),
    ring = StateValue(default = Color.Transparent, focusVisible = style.focusRing),
)

internal fun spanShapeOf(cell: DayCell, radius: Dp): Shape? {
    if (cell.role !in spans) return null
    return roundedOf(
        isStartRound = cell.role == DayRole.RangeStart || cell.isWeekStart,
        isEndRound = cell.role == DayRole.RangeEnd || cell.isWeekEnd,
        radius = radius,
    )
}

internal fun dayShapeOf(cell: DayCell, radius: Dp): Shape {
    if (cell.role != DayRole.RangeMiddle) return RoundedCornerShape(radius)
    return roundedOf(isStartRound = cell.isWeekStart, isEndRound = cell.isWeekEnd, radius = radius)
}

private fun roundedOf(isStartRound: Boolean, isEndRound: Boolean, radius: Dp): Shape {
    val start = CornerSize(radius.takeIf { isStartRound } ?: 0.dp)
    val end = CornerSize(radius.takeIf { isEndRound } ?: 0.dp)
    return RoundedCornerShape(topStart = start, topEnd = end, bottomEnd = end, bottomStart = start)
}

private fun containerOf(cell: DayCell, style: CalendarDayStyle): StateValue<Color> = when (cell.role) {
    DayRole.None -> idleOf(isToday = cell.isToday, style = style)
    DayRole.RangeMiddle -> StateValue(default = style.rangeContainer)
    DayRole.Selected -> StateValue(default = style.selectedContainer)
    DayRole.RangeStart -> StateValue(default = style.selectedContainer)
    DayRole.RangeEnd -> StateValue(default = style.selectedContainer)
    DayRole.RangeEdges -> StateValue(default = style.selectedContainer)
}

private fun idleOf(isToday: Boolean, style: CalendarDayStyle): StateValue<Color> {
    if (isToday) return style.container.copy(default = style.todayContainer)
    return style.container
}

private fun contentOf(cell: DayCell, style: CalendarDayStyle): Color {
    if (cell.isOutside) return style.outsideContent
    return roleContentOf(role = cell.role, style = style)
}

private fun roleContentOf(role: DayRole, style: CalendarDayStyle): Color = when (role) {
    DayRole.None -> style.content
    DayRole.RangeMiddle -> style.rangeContent
    DayRole.Selected -> style.selectedContent
    DayRole.RangeStart -> style.selectedContent
    DayRole.RangeEnd -> style.selectedContent
    DayRole.RangeEdges -> style.selectedContent
}
