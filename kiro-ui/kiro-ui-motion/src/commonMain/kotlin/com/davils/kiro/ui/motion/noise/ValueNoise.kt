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

package com.davils.kiro.ui.motion.noise

import kotlin.math.floor

private const val SMOOTH_RISE: Float = 3f
private const val SMOOTH_FALL: Float = 2f

/**
 * Returns smooth one-dimensional noise between `0` and `1`: random values at whole numbers, blended smoothly between.
 *
 * @param x the position.
 * @param seed the seed; [row] tells several independent curves of one seed apart.
 * @param row the curve.
 * @return the noise.
 * @since 1.0.0
 */
public fun valueNoise(x: Float, seed: Int, row: Int = 0): Float {
    val cell = floor(x)
    val index = cell.toInt()
    val start = unitOf(x = index, y = row, seed = seed)
    val end = unitOf(x = index + 1, y = row, seed = seed)
    return start + (end - start) * smoothstep(x - cell)
}

/**
 * Returns smooth two-dimensional noise between `0` and `1`.
 *
 * @param x the horizontal position.
 * @param y the vertical position.
 * @param seed the seed.
 * @return the noise.
 * @since 1.0.0
 */
public fun valueNoise(x: Float, y: Float, seed: Int): Float {
    val left = floor(x)
    val top = floor(y)
    val i = left.toInt()
    val j = top.toInt()
    val u = smoothstep(x - left)
    val v = smoothstep(y - top)
    val upper = lerp(
        start = unitOf(x = i, y = j, seed = seed),
        end = unitOf(x = i + 1, y = j, seed = seed),
        fraction = u,
    )
    val lower = lerp(
        start = unitOf(x = i, y = j + 1, seed = seed),
        end = unitOf(x = i + 1, y = j + 1, seed = seed),
        fraction = u,
    )
    return lerp(start = upper, end = lower, fraction = v)
}

/**
 * Returns `value` eased with the smoothstep curve after clamping it between `0` and `1`.
 *
 * @param value the value.
 * @return the eased value.
 * @since 1.0.0
 */
public fun smoothstep(value: Float): Float {
    val clamped = value.coerceIn(minimumValue = 0f, maximumValue = 1f)
    return clamped * clamped * (SMOOTH_RISE - SMOOTH_FALL * clamped)
}

/**
 * Returns the value a fraction of the way from [start] to [end].
 *
 * @param start the value at `0`.
 * @param end the value at `1`.
 * @param fraction the fraction, which is not clamped.
 * @return the value.
 * @since 1.0.0
 */
public fun lerp(start: Float, end: Float, fraction: Float): Float = start + (end - start) * fraction
