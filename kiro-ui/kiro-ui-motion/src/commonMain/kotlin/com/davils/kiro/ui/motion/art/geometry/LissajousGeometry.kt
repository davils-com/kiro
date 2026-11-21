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
import com.davils.kiro.ui.motion.noise.pickOf
import kotlin.math.floor

private const val FIRST_SALT: Int = 0x7f4a7c15
private const val SECOND_SALT: Int = 0x1f83d9ab

internal fun lissajousFrequency(index: Int, least: Int, most: Int, seed: Int): Int {
    val low = least.coerceAtLeast(minimumValue = 1)
    val high = most.coerceAtLeast(low)
    return low + pickOf(size = high - low + 1, x = index, seed = seed + FIRST_SALT)
}

internal fun lissajousPartner(first: Int, index: Int, seed: Int): Int =
    first + pickOf(size = 2, x = index, seed = seed + SECOND_SALT)

internal fun Path.polyline(points: FloatArray, count: Int) {
    rewind()
    if (count <= 0) return
    moveTo(x = points[0], y = points[1])
    for (index in 1 until count) lineTo(x = points[index * 2], y = points[index * 2 + 1])
}

internal fun Path.partial(points: FloatArray, count: Int, progress: Float): Offset {
    rewind()
    val last = (count - 1).coerceAtLeast(minimumValue = 0)
    val position = progress.coerceIn(minimumValue = 0f, maximumValue = 1f) * last
    val lower = floor(position).toInt()
    val whole = lower.coerceAtMost(maximumValue = last)
    moveTo(x = points[0], y = points[1])
    for (index in 1..whole) lineTo(x = points[index * 2], y = points[index * 2 + 1])
    val next = (whole + 1).coerceAtMost(maximumValue = last)
    val fraction = position - whole
    val x = points[whole * 2] + (points[next * 2] - points[whole * 2]) * fraction
    val y = points[whole * 2 + 1] + (points[next * 2 + 1] - points[whole * 2 + 1]) * fraction
    lineTo(x = x, y = y)
    return Offset(x = x, y = y)
}
