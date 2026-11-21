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

import kotlin.math.abs

/**
 * Returns fractal Brownian motion: several octaves of [perlinNoise], each finer and fainter than the one before, which
 * gives natural detail like clouds or terrain. The result lies between about `-1` and `1`.
 *
 * @param x the horizontal position.
 * @param y the vertical position.
 * @param z the depth, such as the time.
 * @param seed the seed.
 * @param octaves the number of octaves, at least `1`.
 * @param lacunarity how much finer each octave is.
 * @param gain how much fainter each octave is.
 * @return the noise.
 * @since 1.0.0
 */
public fun fractalNoise(x: Float, y: Float, z: Float, seed: Int, octaves: Int, lacunarity: Float, gain: Float): Float {
    var sum = 0f
    var amplitude = 1f
    var frequency = 1f
    var total = 0f
    repeat(octaves.coerceAtLeast(minimumValue = 1)) { octave ->
        sum += amplitude * perlinNoise(x = x * frequency, y = y * frequency, z = z, seed = seed + octave)
        total += amplitude
        amplitude *= gain
        frequency *= lacunarity
    }
    return sum / total
}

/**
 * Returns ridged noise between `0` and `1`: [fractalNoise] folded at zero, which draws sharp ridges like mountain
 * crests or veins.
 *
 * @param x the horizontal position.
 * @param y the vertical position.
 * @param z the depth, such as the time.
 * @param seed the seed.
 * @param octaves the number of octaves, at least `1`.
 * @param lacunarity how much finer each octave is.
 * @param gain how much fainter each octave is.
 * @return the noise.
 * @since 1.0.0
 */
public fun ridgedNoise(x: Float, y: Float, z: Float, seed: Int, octaves: Int, lacunarity: Float, gain: Float): Float {
    val folded = abs(
        fractalNoise(x = x, y = y, z = z, seed = seed, octaves = octaves, lacunarity = lacunarity, gain = gain),
    )
    return (1f - folded).coerceIn(minimumValue = 0f, maximumValue = 1f)
}

/**
 * Returns [fractalNoise] at a position that is itself pushed around by noise, which swirls the field like smoke or
 * marble.
 *
 * @param x the horizontal position.
 * @param y the vertical position.
 * @param z the depth, such as the time.
 * @param seed the seed.
 * @param strength how far the position is pushed.
 * @param octaves the number of octaves, at least `1`.
 * @return the noise.
 * @since 1.0.0
 */
public fun warpedNoise(x: Float, y: Float, z: Float, seed: Int, strength: Float, octaves: Int): Float {
    val shiftX = perlinNoise(x = x, y = y, z = z, seed = seed + WARP_X)
    val shiftY = perlinNoise(x = x, y = y, z = z, seed = seed + WARP_Y)
    return fractalNoise(
        x = x + shiftX * strength,
        y = y + shiftY * strength,
        z = z,
        seed = seed,
        octaves = octaves,
        lacunarity = LACUNARITY,
        gain = GAIN,
    )
}

private const val WARP_X: Int = 101
private const val WARP_Y: Int = 211
private const val LACUNARITY: Float = 2f
private const val GAIN: Float = 0.5f
