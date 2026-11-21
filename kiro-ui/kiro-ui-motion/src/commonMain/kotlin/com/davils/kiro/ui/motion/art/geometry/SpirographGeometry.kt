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

import com.davils.kiro.ui.motion.noise.smoothstep
import kotlin.math.abs
import kotlin.math.roundToInt

private const val MIN_RATIO: Float = 0.05f

internal fun ratioNear(target: Float, loops: Int, isOutside: Boolean): IntArray {
    val clamped = target.coerceIn(minimumValue = MIN_RATIO, maximumValue = 1f - MIN_RATIO)
    var best = intArrayOf(1, 3)
    var bestError = Float.MAX_VALUE
    for (rolling in 1..loops.coerceAtLeast(1)) {
        val fixed = (rolling / clamped).roundToInt().coerceAtLeast(rolling + 1)
        val error = abs(rolling.toFloat() / fixed - clamped)
        val isEllipse = !isOutside && fixed == rolling * 2
        val isCandidate = greatestDivisor(first = fixed, second = rolling) == 1 && !isEllipse
        if (isCandidate && error < bestError) {
            bestError = error
            best = intArrayOf(rolling, fixed)
        }
    }
    return best
}

internal fun greatestDivisor(first: Int, second: Int): Int {
    var a = abs(first)
    var b = abs(second)
    while (b != 0) {
        val rest = a % b
        a = b
        b = rest
    }
    return a.coerceAtLeast(1)
}

internal fun drawingOf(cycle: Double, share: Float): Float {
    if (share <= 0f) return 1f
    return (cycle / share).toFloat().coerceIn(minimumValue = 0f, maximumValue = 1f)
}

internal fun fadeOf(cycle: Double, share: Float): Float {
    if (share >= 1f || cycle <= share) return 1f
    return 1f - smoothstep(((cycle - share) / (1f - share)).toFloat())
}
