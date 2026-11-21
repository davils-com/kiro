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

import androidx.compose.animation.core.Easing
import androidx.compose.ui.graphics.Path
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

private const val CORNERS: Int = 6
private const val FIRST_CORNER: Double = PI / 6.0
private const val CORNER_STEP: Double = PI / 3.0
private const val ROOT_THREE: Float = 1.7320508f
private const val HALF: Float = 0.5f

internal fun hexFace(column: Int, row: Int, wave: Int, share: Float, seed: Int): Boolean =
    unitOf(x = column, y = row, z = wave, seed = seed) < share

internal fun flipProgress(from: Boolean, to: Boolean, local: Double, turn: Float, easing: Easing): Float {
    if (from == to) return 1f
    return turnProgress(phase = local, turn = turn, easing = easing)
}

internal fun flipSquash(progress: Float): Float = abs(cos(progress * PI)).toFloat()

internal fun flipLean(progress: Float): Float = sin(progress * PI).toFloat()

internal fun flipFace(from: Boolean, to: Boolean, progress: Float): Boolean {
    if (progress < HALF) return from
    return to
}

internal fun cardRadius(radius: Float, gap: Float): Float = (radius - gap / ROOT_THREE).coerceAtLeast(minimumValue = 0f)

internal fun shadeIndex(distance: Float, shades: Int): Int {
    val last = (shades - 1).coerceAtLeast(minimumValue = 0)
    return ((1f - distance.coerceIn(minimumValue = 0f, maximumValue = 1f)) * last).roundToInt()
}

internal fun Path.hexagon(radius: Float) {
    rewind()
    for (corner in 0 until CORNERS) {
        val angle = FIRST_CORNER + corner * CORNER_STEP
        val x = (radius * cos(angle)).toFloat()
        val y = (radius * sin(angle)).toFloat()
        if (corner == 0) moveTo(x = x, y = y)
        if (corner > 0) lineTo(x = x, y = y)
    }
    close()
}
