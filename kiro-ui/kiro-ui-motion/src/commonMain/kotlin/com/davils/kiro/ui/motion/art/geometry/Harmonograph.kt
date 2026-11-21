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
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin

private const val FULL_TURN: Double = PI * 2.0

internal class Harmonograph(
    val frequencyX: Int,
    val frequencyY: Int,
    val detune: Float,
    val twist: Float,
    val damping: Float,
    val turns: Float,
) {
    fun fill(shift: Double, steps: Int, into: FloatArray) {
        val last = (steps - 1).coerceAtLeast(minimumValue = 1)
        val span = turns * FULL_TURN
        for (step in 0 until steps) {
            val share = step.toDouble() / last
            val time = share * span
            val envelope = exp(-damping * share)
            val x = envelope * sin(frequencyX * time + shift)
            val y = envelope * sin((frequencyY + detune) * time)
            val angle = twist * share * FULL_TURN
            into[step * 2] = (x * cos(angle) - y * sin(angle)).toFloat()
            into[step * 2 + 1] = (x * sin(angle) + y * cos(angle)).toFloat()
        }
    }
}
