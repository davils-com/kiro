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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.seconds

private const val RINGS: Int = 9
private const val FIRST_RADIUS: Float = 10f
private const val FOUR_TIMES: Float = 40f
private const val INSIDE: Float = 5f
private const val EIGHT_TIMES: Int = 8
private const val MOST: Int = 3
private const val SEED: Int = 4
private const val NEGATIVE_LEAST: Int = -2
private const val NEGATIVE_MOST: Int = -5

class OrbitGeometryTest : FunSpec({
    val period = 2.seconds

    test("slows the outer orbits as in Kepler's third law") {
        keplerPeriod(period = period, radius = FOUR_TIMES, firstRadius = FIRST_RADIUS) shouldBe period * EIGHT_TIMES
        keplerPeriod(period = period, radius = INSIDE, firstRadius = FIRST_RADIUS) shouldBe period
        keplerPeriod(period = period, radius = INSIDE, firstRadius = 0f) shouldBe period
    }

    test("counts the planets of an orbit within its range") {
        for (ring in 0 until RINGS) {
            planetCount(ring = ring, least = 1, most = MOST, seed = SEED) shouldBeInRange 1..MOST
        }
        planetCount(ring = 0, least = NEGATIVE_LEAST, most = NEGATIVE_MOST, seed = SEED) shouldBe 0
    }

    test("alternates the directions of the orbits") {
        ringDirection(ring = 0, isAlternating = true) shouldBe 1
        ringDirection(ring = 1, isAlternating = true) shouldBe -1
        ringDirection(ring = 1, isAlternating = false) shouldBe 1
    }
})
