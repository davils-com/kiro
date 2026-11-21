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

package com.davils.kiro.ui.motion.art.geometry

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

private const val MIN_STEPS: Int = 8

internal class Trochoid(
    val fixed: Int,
    val rolling: Int,
    val pen: Float,
    val isOutside: Boolean,
) {
    val rounds: Int
        get() = rolling / greatestDivisor(first = fixed, second = rolling)

    val extent: Float
        get() {
            if (isOutside) return fixed + rolling + pen
            return abs(fixed - rolling) + pen
        }

    fun xAt(angle: Double): Double {
        if (isOutside) {
            val sum = (fixed + rolling).toDouble()
            return sum * cos(angle) - pen * cos(sum / rolling * angle)
        }
        val difference = (fixed - rolling).toDouble()
        return difference * cos(angle) + pen * cos(difference / rolling * angle)
    }

    fun yAt(angle: Double): Double {
        if (isOutside) {
            val sum = (fixed + rolling).toDouble()
            return sum * sin(angle) - pen * sin(sum / rolling * angle)
        }
        val difference = (fixed - rolling).toDouble()
        return difference * sin(angle) - pen * sin(difference / rolling * angle)
    }

    fun points(steps: Int, scale: Float): FloatArray {
        val count = rounds * steps.coerceAtLeast(MIN_STEPS) + 1
        val end = 2.0 * PI * rounds
        val points = FloatArray(count * 2)
        for (index in 0 until count) {
            val angle = end * index / (count - 1)
            points[index * 2] = (xAt(angle) * scale).toFloat()
            points[index * 2 + 1] = (yAt(angle) * scale).toFloat()
        }
        return points
    }
}
