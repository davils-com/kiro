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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.ui.geometry.Offset
import com.davils.kiro.ui.motion.noise.fractalNoise
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.hypot
import kotlin.math.sin

private const val TAU: Float = (PI * 2).toFloat()
private const val HALF_TURN: Float = PI.toFloat()
private const val QUARTER_TURN: Float = (PI / 2).toFloat()
private const val CURL_STEP: Float = 0.01f
private const val LACUNARITY: Float = 2f
private const val GAIN: Float = 0.5f

internal class FlowField(private val kind: FlowFieldKind, private val octaves: Int, private val turns: Float) {
    var scale: Float = 1f
    var depth: Float = 0f
    var seed: Int = 0
    private var touch: Offset = Offset.Zero
    private var strength = 0f
    private var radius = 1f

    fun touch(pointer: Offset?, strength: Float, radius: Float) {
        touch = pointer ?: Offset.Zero
        this.strength = strength.takeIf { pointer != null } ?: 0f
        this.radius = radius.coerceAtLeast(minimumValue = 1f)
    }

    fun angleAt(x: Float, y: Float): Float {
        val base = when (kind) {
            FlowFieldKind.Angle -> noiseAt(x = x, y = y) * turns * TAU
            FlowFieldKind.Curl -> curlAt(x = x, y = y)
        }
        if (strength == 0f) return base
        return swirl(angle = base, x = x, y = y)
    }

    fun trace(start: Offset, steps: Int, segment: Float, points: FloatArray, angles: FloatArray) {
        var x = start.x
        var y = start.y
        points[0] = x
        points[1] = y
        for (step in 0 until steps) {
            val angle = angleAt(x = x, y = y)
            angles[step] = angle
            x += cos(angle) * segment
            y += sin(angle) * segment
            val index = (step + 1) * 2
            points[index] = x
            points[index + 1] = y
        }
    }

    private fun noiseAt(x: Float, y: Float): Float = fractalNoise(
        x = x / scale,
        y = y / scale,
        z = depth,
        seed = seed,
        octaves = octaves,
        lacunarity = LACUNARITY,
        gain = GAIN,
    )

    private fun curlAt(x: Float, y: Float): Float {
        val step = CURL_STEP * scale
        val alongX = noiseAt(x = x, y = y + step) - noiseAt(x = x, y = y - step)
        val alongY = noiseAt(x = x + step, y = y) - noiseAt(x = x - step, y = y)
        return atan2(y = -alongY, x = alongX)
    }

    private fun swirl(angle: Float, x: Float, y: Float): Float {
        val dx = x - touch.x
        val dy = y - touch.y
        val distance = hypot(x = dx, y = dy) / radius
        val pull = (strength * exp(-distance * distance)).coerceIn(minimumValue = 0f, maximumValue = 1f)
        val tangent = atan2(y = dy, x = dx) + QUARTER_TURN
        val turn = (tangent - angle + HALF_TURN).mod(TAU) - HALF_TURN
        return angle + turn * pull
    }
}
