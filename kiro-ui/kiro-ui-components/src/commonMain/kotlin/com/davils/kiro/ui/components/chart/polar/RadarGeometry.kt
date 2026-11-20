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
import kotlin.math.roundToInt

private const val FULL: Float = 360f
private const val TOP: Float = 90f

internal class RadarGeometry(val center: Offset, val radius: Float, val count: Int, val ticks: List<Double>) {
    private val maximum: Double
        get() = ticks.last().takeIf { value -> value > 0.0 } ?: 1.0

    fun degreesOf(index: Int): Float = TOP - FULL * index / maxOf(count, 1)

    fun pointOf(index: Int, value: Double): Offset = PolarMath.pointOf(
        center,
        radius * (value / maximum).toFloat(),
        degreesOf(index),
    )

    fun indexAt(position: Offset): Int? {
        if (count == 0) return null
        val degrees = PolarMath.degreesOf(center, position)
        val steps = ((TOP - degrees) / (FULL / count)).roundToInt()
        return steps.mod(count)
    }

    fun anchorOf(index: Int): Offset = PolarMath.pointOf(center, radius, degreesOf(index))

    companion object {
        fun of(size: Size, margin: Float, fraction: Float, count: Int, ticks: List<Double>): RadarGeometry =
            RadarGeometry(
                center = Offset(size.width / 2, size.height / 2),
                radius = maxOf((minOf(size.width, size.height) / 2 - margin) * fraction, 0f),
                count = count,
                ticks = ticks,
            )
    }
}
