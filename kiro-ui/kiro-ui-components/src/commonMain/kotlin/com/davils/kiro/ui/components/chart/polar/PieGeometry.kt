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

package com.davils.kiro.ui.components.chart.polar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

private const val FULL: Float = 360f

internal class PieGeometry(val center: Offset, val inner: Float, val outer: Float, val slices: List<PieSlice>) {
    fun indexAt(position: Offset): Int? {
        val distance = (position - center).getDistance()
        if (distance < inner || distance > outer) return null
        val degrees = PolarMath.degreesOf(center, position)
        val index = slices.indexOfFirst { slice ->
            PolarMath.contains(
                start = slice.start,
                sweep = slice.sweep,
                degrees = degrees,
            )
        }
        return index.takeIf { found -> found >= 0 }
    }

    fun anchorOf(index: Int): Offset = PolarMath.pointOf(center, (inner + outer) / 2, slices[index].middle)

    companion object {
        fun of(
            size: Size,
            margin: Float,
            fraction: Float,
            inner: Float,
            padding: Float,
            values: List<Double>,
        ): PieGeometry {
            val outer = maxOf((minOf(size.width, size.height) / 2 - margin) * fraction, 0f)
            val total = values.filter { value -> value > 0 }.sum()
            val filled = values.count { value -> value > 0 }
            val gaps = padding.takeIf { filled > 1 } ?: 0f
            val available = FULL - filled * gaps
            val divisor = total.takeIf { sum -> sum > 0 } ?: 1.0
            val sweeps = values.map { value -> (maxOf(value, 0.0) / divisor).toFloat() * available }
            val starts = sweeps.runningFold(0f) { start, sweep -> start + sweep + gaps.takeIf { sweep > 0f }.orZero() }
            val slices = sweeps.mapIndexed { index, sweep -> PieSlice(start = starts[index], sweep = sweep) }
            return PieGeometry(
                center = Offset(size.width / 2, size.height / 2),
                inner = minOf(inner, outer),
                outer = outer,
                slices = slices,
            )
        }

        private fun Float?.orZero(): Float = this ?: 0f
    }
}
