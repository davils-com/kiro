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

package com.davils.kiro.ui.motion.time

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.floats.shouldBeLessThan
import io.kotest.matchers.shouldBe

private const val TOLERANCE: Float = 0.001f
private const val EARLY: Float = 0.1f
private const val LATE: Float = 0.8f
private const val ALMOST: Float = 0.9f
private const val BOUNCES: Float = 2.75f
private const val SECOND_BOUNCE: Float = 0.6f
private const val THIRD_BOUNCE: Float = 0.85f
private const val FOURTH_BOUNCE: Float = 0.95f
private const val STEPS: Int = 4
private const val STEP_FRACTION: Float = 0.3f
private const val ONE_STEP: Float = 0.25f
private const val TWO_STEPS: Float = 0.5f
private const val PERIOD: Float = 0.5f
private const val X1: Float = 0.4f
private const val X2: Float = 0.2f

private fun Easing.shouldRunFromZeroToOne() {
    transform(fraction = 0f) shouldBe (0f plusOrMinus TOLERANCE)
    transform(fraction = 1f) shouldBe (1f plusOrMinus TOLERANCE)
}

class EasingsTest : FunSpec({
    test("every curve starts at zero and ends at one") {
        listOf(Easings.backOut, Easings.backInOut, Easings.elasticOut, Easings.bounceOut).forEach { easing ->
            easing.shouldRunFromZeroToOne()
        }
    }

    test("back curves overshoot and pull back") {
        Easings.backOut.transform(fraction = LATE) shouldBeGreaterThan 1f
        Easings.backInOut.transform(fraction = EARLY) shouldBeLessThan 0f
        Easings.backInOut.transform(fraction = ALMOST) shouldBeGreaterThan 1f
        Easings.backOut(overshoot = 0f).transform(fraction = LATE) shouldBeLessThan 1f
    }

    test("the elastic curve swings past the end") {
        val elastic = Easings.elasticOut(period = PERIOD)

        Easings.elasticOut.transform(fraction = EARLY) shouldBeGreaterThan 1f
        elastic.transform(fraction = -1f) shouldBe 0f
        elastic.transform(fraction = 2f) shouldBe 1f
    }

    test("the bounce curve touches the end on every bounce") {
        val bounce = Easings.bounceOut

        bounce.transform(fraction = 1f / BOUNCES) shouldBe (1f plusOrMinus TOLERANCE)
        bounce.transform(fraction = 2f / BOUNCES) shouldBe (1f plusOrMinus TOLERANCE)
        bounce.transform(fraction = SECOND_BOUNCE) shouldBeLessThan 1f
        bounce.transform(fraction = THIRD_BOUNCE) shouldBeLessThan 1f
        bounce.transform(fraction = FOURTH_BOUNCE) shouldBeLessThan 1f
    }

    test("steps jump at the end or at the start of a step") {
        Easings.steps(count = STEPS).transform(fraction = STEP_FRACTION) shouldBe ONE_STEP
        Easings.steps(count = STEPS, jumpsAtStart = true).transform(fraction = STEP_FRACTION) shouldBe TWO_STEPS
        Easings.steps(count = STEPS).transform(fraction = 2f) shouldBe 1f
        shouldThrow<IllegalArgumentException> { Easings.steps(count = 0) }
    }

    test("cubic Bézier curves follow CSS") {
        val expected = CubicBezierEasing(a = X1, b = 0f, c = X2, d = 1f)

        Easings.cubicBezier(x1 = X1, y1 = 0f, x2 = X2, y2 = 1f) shouldBe expected
    }
})
