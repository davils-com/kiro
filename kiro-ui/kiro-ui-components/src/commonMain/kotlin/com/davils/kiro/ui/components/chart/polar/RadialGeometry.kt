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
import kotlin.math.floor

private const val HALF: Float = 0.5f

internal class RadialGeometry(
    val center: Offset,
    val inner: Float,
    val outer: Float,
    val count: Int,
    val padding: Float,
    val start: Float,
    val end: Float,
) {
    val band: Float
        get() = (outer - inner) / maxOf(a = count, b = 1)

    val thickness: Float
        get() = band * (1 - 2 * padding)

    fun radiusOf(index: Int): Float = inner + band * (index + HALF)

    fun degreesOf(fraction: Float): Float = start + (end - start) * fraction

    fun indexAt(position: Offset): Int? {
        val distance = (position - center).getDistance()
        val index = floor((distance - inner) / band.takeIf { value -> value > 0f }.orOne()).toInt()
        return index.takeIf { found -> found in 0 until count }
    }

    fun anchorOf(index: Int): Offset = PolarMath.pointOf(center = center, radius = radiusOf(index), degrees = start)

    private fun Float?.orOne(): Float = this ?: 1f

    companion object {
        fun of(
            size: Size,
            margin: Float,
            fraction: Float,
            inner: Float,
            outer: Float?,
            count: Int,
            padding: Float,
            angles: Pair<Float, Float>,
        ): RadialGeometry {
            val available = maxOf(a = (minOf(a = size.width, b = size.height) / 2 - margin) * fraction, b = 0f)
            val reach = outer?.let { value -> minOf(a = value, b = minOf(a = size.width, b = size.height) / 2) }
                ?: available
            return RadialGeometry(
                center = Offset(x = size.width / 2, y = size.height / 2),
                inner = minOf(a = inner, b = reach),
                outer = reach,
                count = count,
                padding = padding,
                start = angles.first,
                end = angles.second,
            )
        }
    }
}
