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

private const val CORNERS: Int = 4
private const val SADDLE: Int = 4
private const val QUARTER: Float = 0.25f

private val cornerX = intArrayOf(0, 1, 1, 0)
private val cornerY = intArrayOf(0, 0, 1, 1)

internal class ContourCell(private val field: FloatArray, private val columns: Int, private val level: Float) {
    private val values = FloatArray(CORNERS)
    private val above = BooleanArray(CORNERS)
    private var column = 0
    private var row = 0
    private var count = 0

    val isFull: Boolean
        get() = count == CORNERS

    fun load(column: Int, row: Int) {
        this.column = column
        this.row = row
        count = 0
        for (corner in 0 until CORNERS) {
            val value = field[(row + cornerY[corner]) * columns + column + cornerX[corner]]
            values[corner] = value
            above[corner] = value >= level
            if (above[corner]) count++
        }
    }

    fun lines(sink: ContourSink) {
        if (count == 0 || count == CORNERS) return
        val crossings = (0 until CORNERS).count { edge -> crosses(edge) }
        if (crossings == SADDLE) {
            val isCenterAbove = values.sum() * QUARTER >= level
            for (corner in 0 until CORNERS) {
                if (above[corner] != isCenterAbove) cut(corner = corner, sink = sink)
            }
            return
        }
        val first = (0 until CORNERS).first { edge -> crosses(edge) }
        val second = (first + 1 until CORNERS).first { edge -> crosses(edge) }
        sink.moveTo(x = crossingX(first), y = crossingY(first))
        sink.lineTo(x = crossingX(second), y = crossingY(second))
    }

    fun band(sink: ContourSink) {
        if (count == 0) return
        val isSaddle = (0 until CORNERS).count { edge -> crosses(edge) } == SADDLE
        if (isSaddle && values.sum() * QUARTER < level) {
            for (corner in 0 until CORNERS) {
                if (above[corner]) wedge(corner = corner, sink = sink)
            }
            return
        }
        var isFirst = true
        for (corner in 0 until CORNERS) {
            if (above[corner]) {
                val x = column + cornerX[corner].toFloat()
                point(x = x, y = row + cornerY[corner].toFloat(), isFirst = isFirst, sink = sink)
                isFirst = false
            }
            if (crosses(corner)) {
                point(x = crossingX(corner), y = crossingY(corner), isFirst = isFirst, sink = sink)
                isFirst = false
            }
        }
        sink.close()
    }

    private fun cut(corner: Int, sink: ContourSink) {
        val before = (corner + CORNERS - 1) % CORNERS
        sink.moveTo(x = crossingX(before), y = crossingY(before))
        sink.lineTo(x = crossingX(corner), y = crossingY(corner))
    }

    private fun wedge(corner: Int, sink: ContourSink) {
        val before = (corner + CORNERS - 1) % CORNERS
        sink.moveTo(x = crossingX(before), y = crossingY(before))
        sink.lineTo(x = column + cornerX[corner].toFloat(), y = row + cornerY[corner].toFloat())
        sink.lineTo(x = crossingX(corner), y = crossingY(corner))
        sink.close()
    }

    private fun point(x: Float, y: Float, isFirst: Boolean, sink: ContourSink) {
        if (isFirst) sink.moveTo(x = x, y = y)
        if (!isFirst) sink.lineTo(x = x, y = y)
    }

    private fun crosses(edge: Int): Boolean = above[edge] != above[(edge + 1) % CORNERS]

    private fun fraction(edge: Int): Float {
        val start = values[edge]
        val end = values[(edge + 1) % CORNERS]
        return ((level - start) / (end - start)).coerceIn(minimumValue = 0f, maximumValue = 1f)
    }

    private fun crossingX(edge: Int): Float {
        val next = (edge + 1) % CORNERS
        return column + cornerX[edge] + (cornerX[next] - cornerX[edge]) * fraction(edge)
    }

    private fun crossingY(edge: Int): Float {
        val next = (edge + 1) % CORNERS
        return row + cornerY[edge] + (cornerY[next] - cornerY[edge]) * fraction(edge)
    }
}
