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
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.math.abs

private const val LOOPS: Int = 9
private const val STEPS: Int = 16
private const val TOLERANCE: Double = 0.0001
private const val HALF: Float = 0.5f
private const val QUARTER_CYCLE: Double = 0.25
private const val THREE_QUARTER_CYCLE: Double = 0.75
private const val TWELVE: Int = 12
private const val EIGHTEEN: Int = 18
private const val SIX: Int = 6
private const val MINUS_FOUR: Int = -4
private const val FIXED: Int = 5
private const val ROLLING: Int = 3
private const val PEN: Float = 2f
private const val INSIDE_EXTENT: Float = 4f
private const val OUTSIDE_EXTENT: Float = 10f
private const val THIRD: Int = 3

class SpirographGeometryTest : FunSpec({
    test("finds a ratio of whole teeth that closes within the loops") {
        for (index in 1..LOOPS) {
            val ratio = ratioNear(target = index / (LOOPS + 1f), loops = LOOPS, isOutside = false)
            greatestDivisor(first = ratio[0], second = ratio[1]) shouldBe 1
            ratio[0] shouldBeLessThanOrEqual LOOPS
            ratio[1] shouldNotBe ratio[0] * 2
        }
        ratioNear(target = HALF, loops = 1, isOutside = true).toList() shouldBe listOf(1, 2)
        ratioNear(target = HALF, loops = 1, isOutside = false).toList() shouldBe listOf(1, THIRD)
    }

    test("finds the greatest common divisor") {
        greatestDivisor(first = TWELVE, second = EIGHTEEN) shouldBe SIX
        greatestDivisor(first = 0, second = 0) shouldBe 1
        greatestDivisor(first = MINUS_FOUR, second = SIX) shouldBe 2
    }

    test("traces a closed hypotrochoid and epitrochoid within their extent") {
        for (isOutside in listOf(false, true)) {
            val trochoid = Trochoid(fixed = FIXED, rolling = ROLLING, pen = PEN, isOutside = isOutside)
            val points = trochoid.points(steps = STEPS, scale = 1f)

            trochoid.rounds shouldBe ROLLING
            points.size shouldBe (ROLLING * STEPS + 1) * 2
            points[0].toDouble() shouldBe (points[points.size - 2].toDouble() plusOrMinus TOLERANCE)
            points[1].toDouble() shouldBe (points[points.size - 1].toDouble() plusOrMinus TOLERANCE)
            for (value in points) (abs(value) <= trochoid.extent + TOLERANCE) shouldBe true
        }
        Trochoid(fixed = FIXED, rolling = ROLLING, pen = PEN, isOutside = false).extent shouldBe INSIDE_EXTENT
        Trochoid(fixed = FIXED, rolling = ROLLING, pen = PEN, isOutside = true).extent shouldBe OUTSIDE_EXTENT
    }

    test("draws during its share and fades after it") {
        drawingOf(cycle = QUARTER_CYCLE, share = HALF) shouldBe HALF
        drawingOf(cycle = THREE_QUARTER_CYCLE, share = HALF) shouldBe 1f
        drawingOf(cycle = QUARTER_CYCLE, share = 0f) shouldBe 1f
        fadeOf(cycle = QUARTER_CYCLE, share = HALF) shouldBe 1f
        fadeOf(cycle = THREE_QUARTER_CYCLE, share = HALF) shouldBe (HALF plusOrMinus TOLERANCE.toFloat())
        fadeOf(cycle = 1.0, share = HALF) shouldBe 0f
        fadeOf(cycle = THREE_QUARTER_CYCLE, share = 1f) shouldBe 1f
    }
})
