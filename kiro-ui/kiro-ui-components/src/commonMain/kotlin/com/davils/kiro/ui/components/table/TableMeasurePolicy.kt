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

package com.davils.kiro.ui.components.table

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints

internal class TableMeasurePolicy(private val minRowHeights: List<Int>) : MeasurePolicy {
    override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
        val cells = measurables.mapNotNull { measurable ->
            (measurable.layoutId as? TableSlot.Cell)?.let { slot ->
                slot to
                    measurable
            }
        }
        val backgrounds = measurables.mapNotNull { measurable ->
            (measurable.layoutId as? TableSlot.Background)?.let { slot -> slot to measurable }
        }
        val widths = columnWidthsOf(cells = cells, minWidth = constraints.minWidth)
        val starts = widths.runningFold(0) { start, width -> start + width }
        val tableWidth = starts.last()
        val placedCells = cells.map { (slot, measurable) ->
            val width = starts[slot.column + slot.span] - starts[slot.column]
            slot to measurable.measure(Constraints(minWidth = width, maxWidth = width))
        }
        val heights = minRowHeights.mapIndexed { row, minimum ->
            maxOf(
                minimum,
                placedCells.filter { (slot, _) -> slot.row == row }.maxOfOrNull { (_, placeable) -> placeable.height }
                    ?: 0,
            )
        }
        val tops = heights.runningFold(0) { top, height -> top + height }
        val placedBackgrounds = backgrounds.map { (slot, measurable) ->
            slot to measurable.measure(Constraints.fixed(width = tableWidth, height = heights[slot.row]))
        }
        return layout(tableWidth, tops.last()) {
            placedBackgrounds.forEach { (slot, placeable) -> placeable.placeRelative(x = 0, y = tops[slot.row]) }
            placedCells.forEach { (slot, placeable) ->
                placeable.placeRelative(
                    x = starts[slot.column],
                    y = tops[slot.row] + centered(placeable, heights[slot.row]),
                )
            }
        }
    }

    private fun centered(placeable: Placeable, height: Int): Int = (height - placeable.height) / 2
}

internal fun columnWidthsOf(cells: List<Pair<TableSlot.Cell, Measurable>>, minWidth: Int): List<Int> {
    val slots = cells.map { (slot, _) -> slot }
    val count = slots.maxOfOrNull { slot -> slot.column + slot.span } ?: 0
    val intrinsic = cells.map { (_, measurable) -> measurable.maxIntrinsicWidth(Constraints.Infinity) }
    val single = MutableList(count) { 0 }
    slots.forEachIndexed { index, slot ->
        if (slot.span == 1) single[slot.column] = maxOf(single[slot.column], intrinsic[index])
    }
    slots.forEachIndexed { index, slot ->
        val covered = (slot.column until slot.column + slot.span).sumOf { column -> single[column] }
        val last = slot.column + slot.span - 1
        single[last] += maxOf(intrinsic[index] - covered, 0)
    }
    return stretched(widths = single.toList(), minWidth = minWidth)
}

internal fun stretched(widths: List<Int>, minWidth: Int): List<Int> {
    val total = widths.sum()
    val extra = minWidth - total
    if (extra <= 0 || widths.isEmpty()) return widths
    val shares = widths.map { width -> width.toLong() * extra / maxOf(total, 1) }
    val evenShares = List(widths.size) { (extra / widths.size).toLong() }
    val used = shares.takeIf { total > 0 } ?: evenShares
    val rest = extra - used.sum().toInt()
    return widths.mapIndexed { index, width ->
        width +
            used[index].toInt() +
            rest.takeIf { index == widths.lastIndex }.orZero()
    }
}

private fun Int?.orZero(): Int = this ?: 0
