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

package com.davils.kiro.ui.motion.art.field

internal fun contourLines(field: FloatArray, columns: Int, rows: Int, level: Float, sink: ContourSink) {
    val cell = ContourCell(field = field, columns = columns, level = level)
    for (row in 0 until rows - 1) {
        for (column in 0 until columns - 1) {
            cell.load(column = column, row = row)
            cell.lines(sink = sink)
        }
    }
}

internal fun contourBands(field: FloatArray, columns: Int, rows: Int, level: Float, sink: ContourSink) {
    val cell = ContourCell(field = field, columns = columns, level = level)
    for (row in 0 until rows - 1) {
        var run = -1
        for (column in 0 until columns - 1) {
            cell.load(column = column, row = row)
            val isFull = cell.isFull
            if (isFull && run < 0) run = column
            if (isFull) continue
            if (run >= 0) rectangle(sink = sink, left = run, right = column, row = row)
            run = -1
            cell.band(sink = sink)
        }
        if (run >= 0) rectangle(sink = sink, left = run, right = columns - 1, row = row)
    }
}

private fun rectangle(sink: ContourSink, left: Int, right: Int, row: Int) {
    sink.moveTo(x = left.toFloat(), y = row.toFloat())
    sink.lineTo(x = right.toFloat(), y = row.toFloat())
    sink.lineTo(x = right.toFloat(), y = row + 1f)
    sink.lineTo(x = left.toFloat(), y = row + 1f)
    sink.close()
}
