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

import com.davils.kiro.ui.motion.time.ArtTime
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeGreaterThanOrEqual
import io.kotest.matchers.floats.shouldBeLessThan
import io.kotest.matchers.shouldBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val BLIPS: Int = 32
private const val SEED: Int = 5
private const val REVOLUTION: Int = 3
private const val PHASE: Double = 0.4
private const val INNER: Float = 0.2f
private const val TICK: Float = 4f
private const val MAJOR_TICK: Float = 8f
private const val EVERY: Int = 6
private const val SEVEN_SECONDS: Int = 7
private const val TWICE: Int = 2

class RadarGeometryTest : FunSpec({
    test("counts the turns of the sweep") {
        revolutionOf(time = ArtTime.of(SEVEN_SECONDS.seconds), period = 3.seconds) shouldBe TWICE
        revolutionOf(time = ArtTime.of(SEVEN_SECONDS.seconds), period = Duration.ZERO) shouldBe 0
    }

    test("ages a blip from the moment the beam found it, at most two turns ago") {
        for (index in 0 until BLIPS) {
            val age = blipAge(index = index, revolution = REVOLUTION, phase = PHASE, seed = SEED)
            age shouldBeGreaterThanOrEqual 0f
            age shouldBeLessThan 2f
            val found = blipRevolution(index = index, revolution = REVOLUTION, phase = PHASE, seed = SEED)
            (REVOLUTION - found in 0..1) shouldBe true
            val distance = blipDistance(index = index, revolution = found, seed = SEED, inner = INNER)
            distance shouldBeGreaterThanOrEqual INNER
            distance shouldBeLessThan 1f + Float.MIN_VALUE
        }
    }

    test("lengthens every major tick") {
        tickLength(index = 0, majorEvery = EVERY, tick = TICK, majorTick = MAJOR_TICK) shouldBe MAJOR_TICK
        tickLength(index = 1, majorEvery = EVERY, tick = TICK, majorTick = MAJOR_TICK) shouldBe TICK
        tickLength(index = 0, majorEvery = 0, tick = TICK, majorTick = MAJOR_TICK) shouldBe TICK
    }
})
