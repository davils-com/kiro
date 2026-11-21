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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val SAMPLES: Int = 400
private const val SEED: Int = 11
private const val STEP: Float = 0.137f
private const val PICKS: Int = 5
private const val LOW: Float = 2f
private const val HIGH: Float = 3f
private const val WHOLE: Int = 3
private const val HALF: Float = 0.5f
private const val QUARTER: Float = 0.25f
private const val LERP_END: Float = 4f
private const val LERP_MIDDLE: Float = 2.5f
private const val LATTICE_Y: Float = 5f
private const val LATTICE_TOLERANCE: Float = 1e-5f
private const val SLOPE_Y: Float = 0.3f
private const val SLOPE_Z: Float = 0.1f
private const val RANGE: Float = 1.5f
private const val OCTAVES: Int = 4
private const val RIDGED_OCTAVES: Int = 3
private const val LACUNARITY: Float = 2f
private const val SAMPLE_X: Float = 0.3f
private const val SAMPLE_Y: Float = 0.7f
private const val NEXT_Z: Float = 1.001f
private const val SMOOTH_TOLERANCE: Float = 0.01f
private const val OCTAVE_X: Float = 0.4f
private const val OCTAVE_Y: Float = 0.6f

private fun samples(): List<Float> = (0 until SAMPLES).map { index -> index * STEP }

class NoiseTest : FunSpec({
    context("hashing") {
        test("is deterministic and spread") {
            hashOf(x = 1, y = 2, z = WHOLE, seed = SEED) shouldBe hashOf(x = 1, y = 2, z = WHOLE, seed = SEED)
            (0 until SAMPLES).map { x -> hashOf(x = x, seed = SEED) }.toSet() shouldHaveAtLeastSize SAMPLES - 1
        }

        test("stays in its ranges") {
            for (x in 0 until SAMPLES) {
                (unitOf(x = x, seed = SEED) in 0f..1f) shouldBe true
                (pickOf(size = PICKS, x = x, seed = SEED) in 0 until PICKS) shouldBe true
                (rangeOf(from = LOW, to = HIGH, x = x, seed = SEED) in LOW..HIGH) shouldBe true
            }
        }
    }

    context("value noise") {
        test("hits the hashed values at whole numbers and stays between zero and one") {
            valueNoise(x = WHOLE.toFloat(), seed = SEED) shouldBe unitOf(x = WHOLE, seed = SEED)
            samples().forEach { x ->
                (valueNoise(x = x, seed = SEED) in 0f..1f) shouldBe true
                (valueNoise(x = x, y = x * HALF, seed = SEED) in 0f..1f) shouldBe true
            }
        }

        test("eases and interpolates") {
            smoothstep(-1f) shouldBe 0f
            smoothstep(2f) shouldBe 1f
            smoothstep(HALF) shouldBe HALF
            lerp(start = LOW, end = LERP_END, fraction = QUARTER) shouldBe LERP_MIDDLE
        }
    }

    context("gradient and fractal noise") {
        test("is zero at the lattice and stays near minus one to one") {
            perlinNoise(x = LOW, y = LATTICE_Y, seed = SEED) shouldBe (0f plusOrMinus LATTICE_TOLERANCE)
            samples().forEach { x ->
                (perlinNoise(x = x, y = x * SLOPE_Y, z = x * SLOPE_Z, seed = SEED) in -RANGE..RANGE) shouldBe true
                val fractal = fractalNoise(
                    x = x,
                    y = x,
                    z = HALF,
                    seed = SEED,
                    octaves = OCTAVES,
                    lacunarity = LACUNARITY,
                    gain = HALF,
                )
                (fractal in -RANGE..RANGE) shouldBe true
                val ridged = ridgedNoise(
                    x = x,
                    y = x,
                    z = HALF,
                    seed = SEED,
                    octaves = RIDGED_OCTAVES,
                    lacunarity = LACUNARITY,
                    gain = HALF,
                )
                (ridged in 0f..1f) shouldBe true
                val warped = warpedNoise(x = x, y = x, z = HALF, seed = SEED, strength = 1f, octaves = 2)
                (warped in -RANGE..RANGE) shouldBe true
            }
        }

        test("changes smoothly along time") {
            val near = perlinNoise(x = SAMPLE_X, y = SAMPLE_Y, z = 1f, seed = SEED)
            val next = perlinNoise(x = SAMPLE_X, y = SAMPLE_Y, z = NEXT_Z, seed = SEED)

            next shouldBe (near plusOrMinus SMOOTH_TOLERANCE)
        }

        test("takes at least one octave") {
            val none = fractalNoise(
                x = OCTAVE_X,
                y = OCTAVE_Y,
                z = 0f,
                seed = SEED,
                octaves = 0,
                lacunarity = LACUNARITY,
                gain = HALF,
            )
            none shouldBe fractalNoise(
                x = OCTAVE_X,
                y = OCTAVE_Y,
                z = 0f,
                seed = SEED,
                octaves = 1,
                lacunarity = LACUNARITY,
                gain = HALF,
            )
        }
    }
})
