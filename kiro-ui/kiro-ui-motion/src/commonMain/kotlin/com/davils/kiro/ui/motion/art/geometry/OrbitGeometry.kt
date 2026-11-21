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

import com.davils.kiro.ui.motion.noise.pickOf
import kotlin.math.pow
import kotlin.time.Duration

private const val KEPLER: Double = 1.5

internal fun keplerPeriod(period: Duration, radius: Float, firstRadius: Float): Duration {
    if (firstRadius <= 0f || radius <= firstRadius) return period
    return period * (radius / firstRadius).toDouble().pow(KEPLER)
}

internal fun planetCount(ring: Int, least: Int, most: Int, seed: Int): Int {
    val low = least.coerceAtLeast(0)
    val high = most.coerceAtLeast(low)
    return low + pickOf(size = high - low + 1, x = ring, seed = seed)
}

internal fun ringDirection(ring: Int, isAlternating: Boolean): Int {
    if (isAlternating && ring % 2 == 1) return -1
    return 1
}
