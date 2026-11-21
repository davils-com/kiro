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

package com.davils.kiro.ui.motion.shader.mesh

import androidx.compose.ui.geometry.Offset
import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin

internal const val MAX_ANCHORS: Int = 6

private const val GOLDEN_ANGLE: Float = 2.3999631f
private const val TURN: Double = 2.0 * PI
private const val CENTER: Float = 0.5f
private const val RING: Float = 0.55f
private const val SALT_REACH: Int = 17
private const val SCALE_MIN: Float = 0.8f
private const val SCALE_RANGE: Float = 0.4f
private const val PULL_REACH: Float = 0.12f
private const val SALT_ANGLE: Int = 3
private const val SALT_X: Int = 5
private const val SALT_Y: Int = 7
private const val SALT_FREQUENCY: Int = 11
private const val SALT_SCALE: Int = 13

internal class MeshAnchors {
    val x: FloatArray = FloatArray(MAX_ANCHORS)
    val y: FloatArray = FloatArray(MAX_ANCHORS)
    val scale: FloatArray = FloatArray(MAX_ANCHORS)

    fun place(count: Int, phase: Double, seed: Int, style: MeshGradientArtStyle, pointer: Offset?) {
        val start = unitOf(x = 0, seed = seed + SALT_ANGLE) * TURN.toFloat()
        for (index in 0 until count) {
            val angle = start + index * GOLDEN_ANGLE
            val reach = style.spread * (RING + (1f - RING) * unitOf(x = index, seed = seed + SALT_REACH))
            val turnX = TURN * (1 + pickOf(size = 2, x = index, seed = seed + SALT_FREQUENCY)) * phase
            val turnY = TURN * (1 + pickOf(size = 2, x = index, y = 1, seed = seed + SALT_FREQUENCY)) * phase
            val shiftX = unitOf(x = index, seed = seed + SALT_X) * TURN
            val shiftY = unitOf(x = index, seed = seed + SALT_Y) * TURN
            x[index] = CENTER + reach * cos(angle) + style.drift * sin(turnX + shiftX).toFloat()
            y[index] = CENTER + reach * sin(angle) + style.drift * cos(turnY + shiftY).toFloat()
            scale[index] = SCALE_MIN + SCALE_RANGE * unitOf(x = index, seed = seed + SALT_SCALE)
            if (pointer != null) lean(index = index, pointer = pointer, pull = style.pointerPull)
        }
    }

    private fun lean(index: Int, pointer: Offset, pull: Float) {
        val dx = pointer.x - x[index]
        val dy = pointer.y - y[index]
        val weight = pull * exp(-(dx * dx + dy * dy) / PULL_REACH)
        x[index] += dx * weight
        y[index] += dy * weight
    }
}

internal fun anchorCountOf(style: MeshGradientArtStyle): Int =
    style.anchors.coerceIn(minimumValue = 1, maximumValue = MAX_ANCHORS)
