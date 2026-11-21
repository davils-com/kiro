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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

private const val LEAST_SIDES: Int = 3
private const val FULL_TURN: Double = PI * 2.0

internal fun polygonSides(ring: Int, least: Int, most: Int): Int {
    val low = least.coerceAtLeast(LEAST_SIDES)
    return low + ring.mod(mostSides(least = least, most = most) - low + 1)
}

internal fun mostSides(least: Int, most: Int): Int = most.coerceAtLeast(least.coerceAtLeast(LEAST_SIDES))

internal fun polygonCorners(center: Offset, radius: Float, sides: Int, turn: Double, into: FloatArray) {
    for (corner in 0 until sides) {
        val angle = (turn + corner.toDouble() / sides) * FULL_TURN
        into[corner * 2] = center.x + (radius * cos(angle)).toFloat()
        into[corner * 2 + 1] = center.y + (radius * sin(angle)).toFloat()
    }
}

internal fun Path.polygon(corners: FloatArray, sides: Int) {
    rewind()
    moveTo(x = corners[0], y = corners[1])
    for (corner in 1 until sides) lineTo(x = corners[corner * 2], y = corners[corner * 2 + 1])
    close()
}

internal fun Path.polygonTrace(corners: FloatArray, sides: Int, progress: Float): Offset {
    rewind()
    val position = progress.coerceIn(minimumValue = 0f, maximumValue = 1f) * sides
    val lower = floor(position).toInt()
    val whole = lower.coerceAtMost(maximumValue = sides)
    moveTo(x = corners[0], y = corners[1])
    for (edge in 1..whole) lineTo(x = corners[edge.mod(sides) * 2], y = corners[edge.mod(sides) * 2 + 1])
    val from = whole.mod(sides)
    val to = (whole + 1).mod(sides)
    val fraction = position - whole
    val x = corners[from * 2] + (corners[to * 2] - corners[from * 2]) * fraction
    val y = corners[from * 2 + 1] + (corners[to * 2 + 1] - corners[from * 2 + 1]) * fraction
    lineTo(x = x, y = y)
    return Offset(x = x, y = y)
}

internal fun traceRing(index: Int, rings: Int, offset: Int): Int = (index + offset).mod(rings.coerceAtLeast(1))
