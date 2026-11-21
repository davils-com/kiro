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

private const val PRIME_X: Int = 73_856_093
private const val PRIME_Y: Int = 19_349_663
private const val PRIME_Z: Int = 83_492_791
private const val PRIME_SEED: Int = 25_165_843
private const val MIX: Int = 0x45d9f3b
private const val SHIFT: Int = 16
private const val MASK: Int = 0xFFFFFF
private const val RANGE: Float = 16_777_216f

/**
 * Returns a well mixed number for integer coordinates and a seed; the same input always gives the same number.
 *
 * @param x the first coordinate.
 * @param y the second coordinate.
 * @param z the third coordinate.
 * @param seed the seed.
 * @return the hash.
 * @since 1.0.0
 */
public fun hashOf(x: Int, y: Int = 0, z: Int = 0, seed: Int = 0): Int {
    var value = x * PRIME_X xor y * PRIME_Y xor z * PRIME_Z xor seed * PRIME_SEED
    value = (value ushr SHIFT xor value) * MIX
    value = (value ushr SHIFT xor value) * MIX
    return value ushr SHIFT xor value
}

/**
 * Returns a number between `0` (inclusive) and `1` (exclusive) for integer coordinates and a seed.
 *
 * @param x the first coordinate.
 * @param y the second coordinate.
 * @param z the third coordinate.
 * @param seed the seed.
 * @return the number.
 * @since 1.0.0
 */
public fun unitOf(x: Int, y: Int = 0, z: Int = 0, seed: Int = 0): Float =
    (hashOf(x = x, y = y, z = z, seed = seed) and MASK) / RANGE

/**
 * Returns an index below [size] for integer coordinates and a seed.
 *
 * @param size the number of choices, at least `1`.
 * @param x the first coordinate.
 * @param y the second coordinate.
 * @param z the third coordinate.
 * @param seed the seed.
 * @return the index.
 * @since 1.0.0
 */
public fun pickOf(size: Int, x: Int, y: Int = 0, z: Int = 0, seed: Int = 0): Int =
    hashOf(x = x, y = y, z = z, seed = seed).mod(size)

/**
 * Returns a number between [from] and [to] for integer coordinates and a seed.
 *
 * @param from the smallest number.
 * @param to the largest number.
 * @param x the first coordinate.
 * @param y the second coordinate.
 * @param seed the seed.
 * @return the number.
 * @since 1.0.0
 */
public fun rangeOf(from: Float, to: Float, x: Int, y: Int = 0, seed: Int = 0): Float =
    from + (to - from) * unitOf(x = x, y = y, seed = seed)
