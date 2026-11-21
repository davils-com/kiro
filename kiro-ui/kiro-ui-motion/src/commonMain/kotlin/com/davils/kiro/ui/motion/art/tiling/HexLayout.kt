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

import kotlin.math.atan2
import kotlin.math.ceil
import kotlin.math.sqrt

private const val ROOT_THREE: Float = 1.7320508f
private const val ROW_PITCH: Float = 1.5f
private const val DEGREES: Float = 57.29578f

internal class HexLayout(
    val columns: IntArray,
    val rows: IntArray,
    val xs: FloatArray,
    val ys: FloatArray,
    val distances: FloatArray,
    val angles: FloatArray,
) {
    val count: Int
        get() = xs.size

    companion object {
        val Empty: HexLayout = HexLayout(
            columns = IntArray(0),
            rows = IntArray(0),
            xs = FloatArray(0),
            ys = FloatArray(0),
            distances = FloatArray(0),
            angles = FloatArray(0),
        )

        fun of(width: Float, height: Float, radius: Float, originX: Float, originY: Float): HexLayout {
            if (radius <= 0f || width <= 0f || height <= 0f) return Empty
            val pitch = radius * ROOT_THREE
            val rise = radius * ROW_PITCH
            val halfColumns = ceil(width / 2f / pitch).toInt() + 1
            val halfRows = ceil(height / 2f / rise).toInt() + 1
            val capacity = (halfColumns * 2 + 1) * (halfRows * 2 + 1)
            val columns = IntArray(capacity)
            val rows = IntArray(capacity)
            val xs = FloatArray(capacity)
            val ys = FloatArray(capacity)
            var count = 0
            for (row in -halfRows..halfRows) {
                for (column in -halfColumns..halfColumns) {
                    val x = width / 2f + (column + row.mod(2) / 2f) * pitch
                    val y = height / 2f + row * rise
                    if (!isInside(x = x, y = y, width = width, height = height, radius = radius)) continue
                    columns[count] = column
                    rows[count] = row
                    xs[count] = x
                    ys[count] = y
                    count++
                }
            }
            val farthest = farthestCorner(width = width, height = height, x = originX, y = originY)
            return HexLayout(
                columns = columns.copyOf(count),
                rows = rows.copyOf(count),
                xs = xs.copyOf(count),
                ys = ys.copyOf(count),
                distances = FloatArray(count) { index ->
                    distanceOf(dx = xs[index] - originX, dy = ys[index] - originY) / farthest
                },
                angles = FloatArray(count) { index ->
                    atan2(y = ys[index] - originY, x = xs[index] - originX) * DEGREES
                },
            )
        }
    }
}

private fun isInside(x: Float, y: Float, width: Float, height: Float, radius: Float): Boolean =
    x + radius >= 0f && x - radius <= width && y + radius >= 0f && y - radius <= height

private fun farthestCorner(width: Float, height: Float, x: Float, y: Float): Float {
    val dx = maxOf(a = x, b = width - x)
    val dy = maxOf(a = y, b = height - y)
    return distanceOf(dx = dx, dy = dy).coerceAtLeast(minimumValue = 1f)
}

internal fun distanceOf(dx: Float, dy: Float): Float = sqrt(dx * dx + dy * dy)
