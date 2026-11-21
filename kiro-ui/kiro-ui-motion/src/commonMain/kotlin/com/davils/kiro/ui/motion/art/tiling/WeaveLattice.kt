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

package com.davils.kiro.ui.motion.art.tiling

import kotlin.math.ceil

private const val ROOT_THREE_HALF: Float = 0.8660254f
private const val THIRD: Float = 1f / 3f
private const val ROOT_TWO: Float = 1.4142135f
private const val ARCS: Int = 4
private const val CORNERS: Int = 3

internal class WeaveLattice(val segments: FloatArray, val frames: Int, val panels: List<FloatArray>) {
    val count: Int
        get() = segments.size / 4

    companion object {
        val Empty: WeaveLattice = WeaveLattice(segments = FloatArray(0), frames = 0, panels = emptyList())

        fun of(pattern: WeavePattern, width: Float, height: Float, cell: Float, arcSteps: Int): WeaveLattice {
            if (cell <= 0f || width <= 0f || height <= 0f) return Empty
            val builder = LatticeBuilder()
            when (pattern) {
                WeavePattern.Asanoha -> builder.asanoha(width = width, height = height, cell = cell)
                WeavePattern.Kagome -> builder.kagome(width = width, height = height, cell = cell)
                WeavePattern.Shippo -> builder.shippo(width = width, height = height, cell = cell, steps = arcSteps)
            }
            return builder.build()
        }
    }
}

private fun LatticeBuilder.asanoha(width: Float, height: Float, cell: Float) {
    val rise = cell * ROOT_THREE_HALF
    forEachVertex(width = width, height = height, cell = cell) { column, row ->
        val x = vertexX(column = column, row = row, cell = cell)
        val y = row * rise
        val below = column + row.mod(2)
        frame(x1 = x, y1 = y, x2 = vertexX(column = column + 1, row = row, cell = cell), y2 = y)
        frame(x1 = x, y1 = y, x2 = vertexX(column = below - 1, row = row + 1, cell = cell), y2 = y + rise)
        frame(x1 = x, y1 = y, x2 = vertexX(column = below, row = row + 1, cell = cell), y2 = y + rise)
    }
    forEachTriangle(width = width, height = height, cell = cell) { triangle ->
        val cx = (triangle[0] + triangle[2] + triangle[4]) * THIRD
        val cy = (triangle[1] + triangle[3] + triangle[5]) * THIRD
        for (corner in 0 until CORNERS) line(x1 = triangle[corner * 2], y1 = triangle[corner * 2 + 1], x2 = cx, y2 = cy)
        panel(triangle.copyOf())
    }
}

private fun LatticeBuilder.kagome(width: Float, height: Float, cell: Float) {
    val middles = FloatArray(size = 6)
    forEachTriangle(width = width, height = height, cell = cell) { triangle ->
        for (corner in 0 until CORNERS) {
            val next = (corner + 1) % CORNERS
            middles[corner * 2] = (triangle[corner * 2] + triangle[next * 2]) / 2f
            middles[corner * 2 + 1] = (triangle[corner * 2 + 1] + triangle[next * 2 + 1]) / 2f
        }
        val cx = (middles[0] + middles[2] + middles[4]) * THIRD
        val cy = (middles[1] + middles[3] + middles[5]) * THIRD
        for (corner in 0 until CORNERS) {
            val next = (corner + 1) % CORNERS
            val x = middles[corner * 2]
            val y = middles[corner * 2 + 1]
            frame(x1 = x, y1 = y, x2 = middles[next * 2], y2 = middles[next * 2 + 1])
            line(x1 = x, y1 = y, x2 = cx, y2 = cy)
        }
        panel(middles.copyOf())
    }
}

private fun LatticeBuilder.shippo(width: Float, height: Float, cell: Float, steps: Int) {
    val pieces = steps.coerceAtLeast(minimumValue = 1)
    val columns = ceil(width / cell).toInt() + 1
    val rows = ceil(height / cell).toInt() + 1
    val radius = cell / ROOT_TWO
    for (row in -1..rows) {
        for (column in -1..columns) {
            val x = column * cell
            val y = row * cell
            val count = pieces * ARCS
            val circle = circleOf(x = x, y = y, radius = radius, pieces = count)
            for (piece in 0 until count) {
                val next = (piece + 1) % count * 2
                frame(x1 = circle[piece * 2], y1 = circle[piece * 2 + 1], x2 = circle[next], y2 = circle[next + 1])
            }
            line(x1 = x, y1 = y, x2 = x + cell, y2 = y)
            line(x1 = x, y1 = y, x2 = x, y2 = y + cell)
            panel(circle)
        }
    }
}

private inline fun forEachVertex(width: Float, height: Float, cell: Float, action: (Int, Int) -> Unit) {
    val columns = ceil(width / cell).toInt() + 1
    val rows = ceil(height / (cell * ROOT_THREE_HALF)).toInt() + 1
    for (row in -1..rows) {
        for (column in -1..columns) action(column, row)
    }
}

private inline fun forEachTriangle(width: Float, height: Float, cell: Float, action: (FloatArray) -> Unit) {
    val rise = cell * ROOT_THREE_HALF
    val triangle = FloatArray(size = 6)
    forEachVertex(width = width, height = height, cell = cell) { column, row ->
        val below = column + row.mod(2)
        val top = row * rise
        triangle.corners(
            first = vertexX(column = column, row = row, cell = cell),
            second = vertexX(column = column + 1, row = row, cell = cell),
            third = vertexX(column = below, row = row + 1, cell = cell),
            top = top,
            rise = rise,
            isUp = false,
        )
        action(triangle)
        triangle.corners(
            first = vertexX(column = below, row = row + 1, cell = cell),
            second = vertexX(column = below + 1, row = row + 1, cell = cell),
            third = vertexX(column = column + 1, row = row, cell = cell),
            top = top,
            rise = rise,
            isUp = true,
        )
        action(triangle)
    }
}

private fun FloatArray.corners(first: Float, second: Float, third: Float, top: Float, rise: Float, isUp: Boolean) {
    val shift = rise * isUp.toBit()
    set(index = 0, value = first)
    set(index = 1, value = top + shift)
    set(index = 2, value = second)
    set(index = 3, value = top + shift)
    set(index = 4, value = third)
    set(index = 5, value = top + rise - shift)
}

internal fun vertexX(column: Int, row: Int, cell: Float): Float = (column + row.mod(2) / 2f) * cell
