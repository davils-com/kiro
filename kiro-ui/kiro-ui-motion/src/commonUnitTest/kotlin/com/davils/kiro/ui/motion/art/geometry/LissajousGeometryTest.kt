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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.floats.shouldBeLessThan
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldBe
import kotlin.math.abs

private const val LEAST: Int = 2
private const val MOST: Int = 3
private const val SEEDS: Int = 16
private const val STEPS: Int = 50
private const val DAMPING: Float = 1.5f
private const val TURNS: Float = 3f
private const val DETUNE: Float = 0.02f
private const val TWIST: Float = 0.25f
private const val SHIFT: Double = 0.3
private const val TOLERANCE: Float = 0.001f
private const val LENGTH: Float = 10f
private const val HALF: Float = 0.5f
private const val QUARTER: Float = 0.25f

class LissajousGeometryTest : FunSpec({
    test("picks frequencies within their range and partners at the same or the next") {
        for (seed in 0 until SEEDS) {
            val first = lissajousFrequency(index = 0, least = LEAST, most = MOST, seed = seed)
            first shouldBeInRange LEAST..MOST
            lissajousPartner(first = first, index = 0, seed = seed) shouldBeInRange first..first + 1
        }
        lissajousFrequency(index = 0, least = 0, most = -1, seed = 0) shouldBe 1
    }

    test("swings within the unit square and dies down") {
        val points = FloatArray(STEPS * 2)
        val curve = Harmonograph(
            frequencyX = LEAST,
            frequencyY = MOST,
            detune = DETUNE,
            twist = TWIST,
            damping = DAMPING,
            turns = TURNS,
        )
        curve.fill(shift = SHIFT, steps = STEPS, into = points)

        for (value in points) abs(value) shouldBeLessThan 1f + TOLERANCE
        abs(points[STEPS * 2 - 1]) shouldBeLessThan HALF
        curve.frequencyX shouldBe LEAST
    }

    test("draws a polyline and a part of it") {
        val points = floatArrayOf(0f, 0f, LENGTH, 0f, LENGTH, LENGTH)
        val path = Path()

        path.partial(points = points, count = 3, progress = QUARTER).x shouldBe (LENGTH * HALF plusOrMinus TOLERANCE)
        path.partial(points = points, count = 3, progress = 1f) shouldBe Offset(x = LENGTH, y = LENGTH)
        path.polyline(points = points, count = 3)
        path.getBounds().height shouldBe LENGTH
        path.polyline(points = points, count = 0)
        path.isEmpty shouldBe true
    }
})
