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

package com.davils.kiro.ui.motion.art.grid

import com.davils.kiro.ui.motion.noise.smoothstep
import kotlin.math.ceil

private const val FAINTEST: Float = 0.2f
private const val MAX_LANES: Int = 256

internal fun retroDepthOf(row: Int, phase: Double, depth: Float): Float = ((row + 1 - phase) * depth).toFloat()

internal fun retroShareOf(depth: Float): Float {
    if (depth <= 0f) return Float.POSITIVE_INFINITY
    return 1f / depth
}

internal fun retroFadeOf(share: Float, fade: Float): Float {
    if (fade <= 0f) return 1f
    val rise = smoothstep(share / fade)
    return rise * rise
}

internal fun retroLanesOf(halfWidth: Float, cell: Float, fade: Float): Int {
    if (cell <= 0f) return 0
    val reach = (fade * FAINTEST).coerceAtLeast(minimumValue = FAINTEST * FAINTEST)
    val lanes = ceil(halfWidth / (cell * reach)).toInt()
    return lanes.coerceIn(minimumValue = 0, maximumValue = MAX_LANES)
}

internal fun retroPulseOf(progress: Float, length: Float): ClosedFloatingPointRange<Float> {
    val head = progress * progress * (1f + length)
    val tail = head * (1f - length.coerceIn(minimumValue = 0f, maximumValue = 1f))
    return tail..head
}
