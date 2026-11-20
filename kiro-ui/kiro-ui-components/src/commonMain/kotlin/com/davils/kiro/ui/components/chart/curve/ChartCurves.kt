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

package com.davils.kiro.ui.components.chart.curve

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import com.davils.kiro.ui.components.chart.ChartCurve

private const val THIRD: Float = 1f / 3f
private const val INNER_DIAGONAL: Float = 4f
private const val LAST_DIAGONAL: Float = 7f
private const val LAST_WEIGHT: Float = 8f

internal object ChartCurves {
    fun path(points: List<Offset>, curve: ChartCurve): Path {
        val path = Path()
        if (points.isEmpty()) return path
        path.moveTo(x = points.first().x, y = points.first().y)
        extend(path = path, points = points, curve = curve)
        return path
    }

    fun extend(path: Path, points: List<Offset>, curve: ChartCurve) {
        if (points.size < 2) return
        when (curve) {
            ChartCurve.Linear -> points.drop(1).forEach { point -> path.lineTo(x = point.x, y = point.y) }
            ChartCurve.Step -> step(path = path, points = points)
            ChartCurve.Monotone -> bezier(path = path, points = points, tangents = monotoneTangents(points))
            ChartCurve.Natural -> natural(path = path, points = points)
        }
    }

    private fun step(path: Path, points: List<Offset>) {
        points.zipWithNext { from, to ->
            val middle = (from.x + to.x) / 2
            path.lineTo(x = middle, y = from.y)
            path.lineTo(x = middle, y = to.y)
            path.lineTo(x = to.x, y = to.y)
        }
    }

    private fun bezier(path: Path, points: List<Offset>, tangents: List<Float>) {
        points.indices.drop(1).forEach { index ->
            val from = points[index - 1]
            val to = points[index]
            val third = (to.x - from.x) * THIRD
            path.cubicTo(
                x1 = from.x + third,
                y1 = from.y + tangents[index - 1] * third,
                x2 = to.x - third,
                y2 = to.y - tangents[index] * third,
                x3 = to.x,
                y3 = to.y,
            )
        }
    }

    private fun monotoneTangents(points: List<Offset>): List<Float> {
        val slopes = points.zipWithNext { from, to -> slopeOf(from = from, to = to) }
        val inner = slopes.zipWithNext { left, right -> harmonic(left = left, right = right) }
        return listOf(slopes.first()) + inner + listOf(slopes.last())
    }

    private fun slopeOf(from: Offset, to: Offset): Float {
        val width = to.x - from.x
        if (width == 0f) return 0f
        return (to.y - from.y) / width
    }

    private fun harmonic(left: Float, right: Float): Float {
        if (left * right <= 0f) return 0f
        return 2f / (1f / left + 1f / right)
    }

    private fun natural(path: Path, points: List<Offset>) {
        if (points.size == 2) return path.lineTo(x = points.last().x, y = points.last().y)
        val xs = controls(points.map { point -> point.x })
        val ys = controls(points.map { point -> point.y })
        points.indices.drop(1).forEach { index ->
            val segment = index - 1
            path.cubicTo(
                x1 = xs.first[segment],
                y1 = ys.first[segment],
                x2 = xs.second[segment],
                y2 = ys.second[segment],
                x3 = points[index].x,
                y3 = points[index].y,
            )
        }
    }

    private fun controls(values: List<Float>): Pair<List<Float>, List<Float>> {
        val segments = values.size - 1
        val a = FloatArray(segments)
        val b = FloatArray(segments)
        val r = FloatArray(segments)
        a[0] = 0f
        b[0] = 2f
        r[0] = values[0] + 2 * values[1]
        for (index in 1 until segments - 1) {
            a[index] = 1f
            b[index] = INNER_DIAGONAL
            r[index] = INNER_DIAGONAL * values[index] + 2 * values[index + 1]
        }
        a[segments - 1] = 2f
        b[segments - 1] = LAST_DIAGONAL
        r[segments - 1] = LAST_WEIGHT * values[segments - 1] + values[segments]
        for (index in 1 until segments) {
            val m = a[index] / b[index - 1]
            b[index] -= m
            r[index] -= m * r[index - 1]
        }
        val first = FloatArray(segments)
        first[segments - 1] = r[segments - 1] / b[segments - 1]
        for (index in segments - 2 downTo 0) {
            first[index] = (r[index] - first[index + 1]) / b[index]
        }
        val second = FloatArray(segments) { index -> 2 * values[index + 1] - first.getOrElse(index + 1) { 0f } }
        second[segments - 1] = (values[segments] + first[segments - 1]) / 2
        return first.toList() to second.toList()
    }
}
