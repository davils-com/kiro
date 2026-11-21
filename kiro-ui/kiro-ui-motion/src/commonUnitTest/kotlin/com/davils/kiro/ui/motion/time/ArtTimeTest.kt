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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private const val TOLERANCE: Double = 1e-9
private const val ONE_AND_A_HALF: Double = 1.5
private const val TWO_AND_A_HALF: Double = 2.5
private const val QUARTER: Double = 0.25
private const val LATE_TOLERANCE: Double = 1e-6
private const val NANOS_PER_SECOND: Long = 1_000_000_000L

class ArtTimeTest : FunSpec({
    test("counts seconds and keeps its duration") {
        val time = ArtTime.of(duration = 1500.milliseconds)

        time.seconds shouldBe (ONE_AND_A_HALF plusOrMinus TOLERANCE)
        time.duration shouldBe 1500.milliseconds
        ArtTime.Zero.seconds shouldBe 0.0
    }

    test("counts the periods that have passed") {
        ArtTime.of(duration = 5.seconds).phase(period = 2.seconds) shouldBe (TWO_AND_A_HALF plusOrMinus TOLERANCE)
    }

    test("keeps the fraction of a phase precise after days") {
        val late = ArtTime.of(duration = 40.days + 250.milliseconds).phase(period = 1.seconds)

        late % 1.0 shouldBe (QUARTER plusOrMinus LATE_TOLERANCE)
    }

    test("keeps phases of very long periods") {
        val period = Long.MAX_VALUE.div(other = 2L)
        val time = ArtTime(nanos = period)

        time.phase(period = Duration.parse("PT${period / NANOS_PER_SECOND}S")) shouldNotBe 0.0
    }

    test("stands still for a period that is not positive") {
        ArtTime.of(duration = 5.seconds).phase(period = Duration.ZERO) shouldBe 0.0
        ArtTime.of(duration = 5.seconds).phase(period = -Duration.INFINITE) shouldBe 0.0
    }

    test("is a value") {
        ArtTime(nanos = 3L) shouldBe ArtTime(nanos = 3L)
        ArtTime(nanos = 3L).hashCode() shouldBe ArtTime(nanos = 3L).hashCode()
        ArtTime(nanos = 3L).toString() shouldBe "ArtTime(nanos=3)"
        ArtTime(nanos = 3L) shouldNotBe Any()
    }
})
