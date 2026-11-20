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

package com.davils.kiro.ui.components.resizable.junction

import androidx.compose.ui.geometry.Offset
import kotlin.math.abs
import kotlin.math.roundToInt

internal fun junctionsOf(spots: List<HandleSpot>, tolerance: Float): List<Junction> {
    val columns = spots.filter { spot -> spot.isVerticalLine }
    val rows = spots.filterNot { spot -> spot.isVerticalLine }
    val touches = columns.flatMap { column ->
        rows.mapNotNull { row -> touchOf(column = column, row = row, tolerance = tolerance) }
    }
    val grouped = touches.groupBy { touch -> keyOf(point = touch.point, tolerance = tolerance) }
    return grouped.values.map { group ->
        Junction(
            point = group.first().point,
            columns = group.map { touch -> touch.column.actions }.distinct(),
            rows = group.map { touch -> touch.row.actions }.distinct(),
        )
    }
}

private fun touchOf(column: HandleSpot, row: HandleSpot, tolerance: Float): Touch? {
    val point = Offset(x = column.bounds.center.x, y = row.bounds.center.y)
    val isMeeting = isOnColumn(point = point, column = column, tolerance = tolerance) &&
        isOnRow(point = point, row = row, tolerance = tolerance)
    val isEnd = isEnd(point = point, row = row, tolerance = tolerance)
    val isJoined = isEnd || isCrossing(point = point, column = column, row = row)
    if (!(isMeeting && isJoined)) return null
    return Touch(point = point, column = column, row = row)
}

private fun isOnColumn(point: Offset, column: HandleSpot, tolerance: Float): Boolean =
    point.y >= column.bounds.top - tolerance && point.y <= column.bounds.bottom + tolerance

private fun isOnRow(point: Offset, row: HandleSpot, tolerance: Float): Boolean =
    point.x >= row.bounds.left - tolerance && point.x <= row.bounds.right + tolerance

private fun isEnd(point: Offset, row: HandleSpot, tolerance: Float): Boolean =
    abs(row.bounds.left - point.x) <= tolerance || abs(row.bounds.right - point.x) <= tolerance

private fun isCrossing(point: Offset, column: HandleSpot, row: HandleSpot): Boolean {
    val isInsideRow = point.x > row.bounds.left && point.x < row.bounds.right
    val isInsideColumn = point.y > column.bounds.top && point.y < column.bounds.bottom
    return isInsideRow && isInsideColumn
}

private fun keyOf(point: Offset, tolerance: Float): Pair<Int, Int> {
    val cell = maxOf(tolerance, 1f)
    return (point.x / cell).roundToInt() to (point.y / cell).roundToInt()
}
