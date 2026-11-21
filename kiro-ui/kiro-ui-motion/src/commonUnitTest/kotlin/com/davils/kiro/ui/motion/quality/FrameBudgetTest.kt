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

package com.davils.kiro.ui.motion.quality

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

private const val MILLISECOND: Long = 1_000_000L
private const val FAST: Long = 2L
private const val FIRST: Long = 10L
private const val WITHIN: Long = 12L
private const val SECOND: Long = 20L
private const val AVERAGE: Long = 15L
private const val SLOW: Long = 40L
private const val PATIENCE: Int = 3
private const val MANY: Int = 9
private const val STEADY: Int = 20

private val policy: QualityPolicy.Adaptive = QualityPolicy.Adaptive(
    budget = 16.milliseconds,
    initial = QualityLevel.High,
    minimum = QualityLevel.Medium,
    maximum = QualityLevel.High,
    headroom = 0.5f,
    smoothing = 0.5f,
    patience = PATIENCE,
)

private fun FrameBudget.recordAll(millis: Long, count: Int): QualityLevel {
    repeat(count) { record(frameNanos = millis * MILLISECOND) }
    return level
}

class FrameBudgetTest : FunSpec({
    test("averages the frame times exponentially") {
        val budget = FrameBudget(policy = policy)

        budget.record(frameNanos = FIRST * MILLISECOND)
        budget.averageNanos shouldBe ((FIRST * MILLISECOND).toDouble() plusOrMinus 1.0)
        budget.record(frameNanos = SECOND * MILLISECOND)
        budget.averageNanos shouldBe ((AVERAGE * MILLISECOND).toDouble() plusOrMinus 1.0)
    }

    test("steps down after slow frames, but not below the minimum") {
        val budget = FrameBudget(policy = policy)

        budget.recordAll(millis = SLOW, count = PATIENCE - 1) shouldBe QualityLevel.High
        budget.recordAll(millis = SLOW, count = 1) shouldBe QualityLevel.Balanced
        budget.recordAll(millis = SLOW, count = PATIENCE) shouldBe QualityLevel.Medium
        budget.recordAll(millis = SLOW, count = MANY) shouldBe QualityLevel.Medium
    }

    test("steps up after fast frames, but not above the maximum") {
        val budget = FrameBudget(policy = policy.copy(initial = QualityLevel.Medium))

        budget.recordAll(millis = FAST, count = PATIENCE) shouldBe QualityLevel.Balanced
        budget.recordAll(millis = FAST, count = MANY) shouldBe QualityLevel.High
    }

    test("keeps the level while frames stay between the headroom and the budget") {
        val budget = FrameBudget(policy = policy.copy(initial = QualityLevel.Balanced))

        budget.recordAll(millis = WITHIN, count = STEADY) shouldBe QualityLevel.Balanced
    }

    test("starts within the bounds, even when they are swapped") {
        val swapped = policy.copy(minimum = QualityLevel.High, maximum = QualityLevel.Low)

        FrameBudget(policy = policy.copy(initial = QualityLevel.Low)).level shouldBe QualityLevel.Medium
        FrameBudget(policy = swapped).level shouldBe QualityLevel.High
    }

    test("never takes a budget below its floor") {
        val budget = FrameBudget(policy = policy, floor = 100.milliseconds)

        budget.recordAll(millis = SLOW, count = MANY) shouldBe QualityLevel.High
    }

    test("keeps a fixed level whatever the frames take") {
        val fixed = frameBudgetOf(policy = QualityPolicy.Fixed(level = QualityLevel.Balanced), floor = Duration.ZERO)
        val adaptive = frameBudgetOf(policy = policy, floor = Duration.ZERO)

        fixed.recordAll(millis = SLOW, count = MANY) shouldBe QualityLevel.Balanced
        fixed.recordAll(millis = FAST, count = MANY) shouldBe QualityLevel.Balanced
        adaptive.recordAll(millis = SLOW, count = MANY) shouldBe QualityLevel.Medium
    }

    test("treats a patience below one as one") {
        val budget = FrameBudget(policy = policy.copy(patience = 0))

        budget.recordAll(millis = SLOW, count = 1) shouldBe QualityLevel.Balanced
    }
})
