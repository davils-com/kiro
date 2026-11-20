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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

private const val HALF_TURN_DEGREES: Double = 180.0

private const val FULL: Float = 360f

internal object PolarMath {
    fun degreesOf(center: Offset, position: Offset): Float {
        val delta = position - center
        val degrees = (atan2(-delta.y, delta.x).toDouble() * HALF_TURN_DEGREES / PI).toFloat()
        return (degrees + FULL) % FULL
    }

    fun pointOf(center: Offset, radius: Float, degrees: Float): Offset {
        val radians = (degrees.toDouble() * PI / HALF_TURN_DEGREES)
        return Offset(center.x + radius * cos(radians).toFloat(), center.y - radius * sin(radians).toFloat())
    }

    fun sector(center: Offset, inner: Float, outer: Float, start: Float, sweep: Float): Path {
        val path = Path()
        path.arcTo(
            rect = square(center, outer),
            startAngleDegrees = -start,
            sweepAngleDegrees = -sweep,
            forceMoveTo = true,
        )
        if (inner <= 0f) {
            path.lineTo(center.x, center.y)
            path.close()
            return path
        }
        path.arcTo(
            rect = square(center, inner),
            startAngleDegrees = -(start + sweep),
            sweepAngleDegrees = sweep,
            forceMoveTo = false,
        )
        path.close()
        return path
    }

    fun square(center: Offset, radius: Float): Rect = Rect(
        center - Offset(radius, radius),
        Size(radius * 2, radius * 2),
    )

    fun contains(start: Float, sweep: Float, degrees: Float): Boolean {
        val relative = ((degrees - start) % FULL + FULL) % FULL
        return relative <= sweep
    }
}
