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

private const val GRADIENTS: Int = 8
private const val DIAGONAL: Float = 0.70710677f
private const val QUINTIC_A: Float = 6f
private const val QUINTIC_B: Float = 15f
private const val QUINTIC_C: Float = 10f
private const val SCALE_2D: Float = 0.7071f

private val gradientX = floatArrayOf(1f, -1f, 0f, 0f, DIAGONAL, -DIAGONAL, DIAGONAL, -DIAGONAL)
private val gradientY = floatArrayOf(0f, 0f, 1f, -1f, DIAGONAL, DIAGONAL, -DIAGONAL, -DIAGONAL)

/**
 * Returns two-dimensional gradient noise (Perlin noise) between about `-1` and `1`, smoother and less blocky than
 * [valueNoise].
 *
 * @param x the horizontal position.
 * @param y the vertical position.
 * @param seed the seed.
 * @return the noise.
 * @since 1.0.0
 */
public fun perlinNoise(x: Float, y: Float, seed: Int): Float {
    val left = floor(x)
    val top = floor(y)
    val i = left.toInt()
    val j = top.toInt()
    val dx = x - left
    val dy = y - top
    val u = quintic(dx)
    val v = quintic(dy)
    val topLeft = corner(i = i, j = j, dx = dx, dy = dy, seed = seed)
    val topRight = corner(i = i + 1, j = j, dx = dx - 1f, dy = dy, seed = seed)
    val bottomLeft = corner(i = i, j = j + 1, dx = dx, dy = dy - 1f, seed = seed)
    val bottomRight = corner(i = i + 1, j = j + 1, dx = dx - 1f, dy = dy - 1f, seed = seed)
    val upper = lerp(start = topLeft, end = topRight, fraction = u)
    val lower = lerp(start = bottomLeft, end = bottomRight, fraction = u)
    return lerp(start = upper, end = lower, fraction = v) / SCALE_2D
}

/**
 * Returns three-dimensional gradient noise between about `-1` and `1`; the third axis is usually time, so a
 * two-dimensional field changes smoothly.
 *
 * @param x the horizontal position.
 * @param y the vertical position.
 * @param z the depth, such as the time.
 * @param seed the seed.
 * @return the noise.
 * @since 1.0.0
 */
public fun perlinNoise(x: Float, y: Float, z: Float, seed: Int): Float {
    val front = floor(z)
    val k = front.toInt()
    val w = quintic(z - front)
    val near = perlinNoise(x = x, y = y, seed = hashOf(x = k, seed = seed))
    val far = perlinNoise(x = x, y = y, seed = hashOf(x = k + 1, seed = seed))
    return lerp(start = near, end = far, fraction = w)
}

private fun corner(i: Int, j: Int, dx: Float, dy: Float, seed: Int): Float {
    val index = pickOf(size = GRADIENTS, x = i, y = j, seed = seed)
    return gradientX[index] * dx + gradientY[index] * dy
}

private fun quintic(t: Float): Float = t * t * t * (t * (t * QUINTIC_A - QUINTIC_B) + QUINTIC_C)
