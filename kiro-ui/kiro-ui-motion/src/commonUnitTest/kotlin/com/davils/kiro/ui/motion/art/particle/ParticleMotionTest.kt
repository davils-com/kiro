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

package com.davils.kiro.ui.motion.art.particle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val TOLERANCE: Float = 0.0001f
private const val SPAN: Float = 10f
private const val QUARTER: Float = 0.25f
private const val HALF: Float = 0.5f
private const val NEAREST: Float = 0.2f
private const val DUTY: Float = 0.4f
private const val OVER: Double = 12.5
private const val REMAINDER: Float = 2.5f

class ParticleMotionTest : FunSpec({
    test("wraps a position into its span") {
        wrapOf(value = OVER, span = SPAN) shouldBe (REMAINDER plusOrMinus TOLERANCE)
        wrapOf(value = -OVER, span = SPAN) shouldBe (SPAN - REMAINDER plusOrMinus TOLERANCE)
        wrapOf(value = OVER, span = 0f) shouldBe 0f
    }

    test("keeps a twinkle between zero and one") {
        waveOf(phase = 0.0, rate = 1f, offset = QUARTER) shouldBe (1f plusOrMinus TOLERANCE)
        waveOf(phase = 0.0, rate = 1f, offset = -QUARTER) shouldBe (0f plusOrMinus TOLERANCE)
    }

    test("moves a star from the far end to the nearest depth") {
        val journey = journeyOf(phase = OVER, offset = HALF)

        journey shouldBe (0f plusOrMinus TOLERANCE)
        depthOf(journey = journey, nearest = NEAREST) shouldBe 1f
        depthOf(journey = 1f, nearest = NEAREST) shouldBe (NEAREST plusOrMinus TOLERANCE)
    }

    test("flies during its duty and rests after it") {
        flightOf(local = QUARTER, duty = DUTY) shouldBe (QUARTER / DUTY plusOrMinus TOLERANCE)
        flightOf(local = QUARTER, duty = 0f) shouldBe 1f
        envelopeOf(progress = HALF) shouldBe (1f plusOrMinus TOLERANCE)
        envelopeOf(progress = 2f) shouldBe (0f plusOrMinus TOLERANCE)
    }
})
