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

package com.davils.kiro.ui.audio

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class FormatsTest : FunSpec({
    test("writes levels to a tenth with an optional plus sign") {
        tenthsOf(level = -12.34f) shouldBe "-12.3"
        tenthsOf(level = -0.04f) shouldBe "0.0"
        tenthsOf(level = 3f, signed = true) shouldBe "+3.0"
        tenthsOf(level = 3f) shouldBe "3.0"
        tenthsOf(level = Float.NEGATIVE_INFINITY) shouldBe "-∞"
        tenthsOf(level = Float.NaN) shouldBe "-∞"
    }

    test("writes whole levels") {
        wholeDecibelsOf(level = -11.6f) shouldBe "-12"
        wholeDecibelsOf(level = Float.NEGATIVE_INFINITY) shouldBe "-∞"
        wholeDecibelsOf(level = Float.NaN) shouldBe "-∞"
    }

    test("writes frequencies in hertz and kilohertz") {
        hertzOf(frequency = 50f) shouldBe "50"
        hertzOf(frequency = 1_000f) shouldBe "1k"
        hertzOf(frequency = 2_500f) shouldBe "2.5k"
        hertzOf(frequency = 20_000f) shouldBe "20k"
    }

    test("writes a timecode with frames") {
        timecodeOf(time = 1.minutes + 5.seconds + 500.milliseconds, framesPerSecond = 30) shouldBe "00:01:05:15"
        timecodeOf(time = (-1).seconds, framesPerSecond = 25) shouldBe "00:00:00:00"
        timecodeOf(time = 3_725.seconds, framesPerSecond = 24) shouldBe "01:02:05:00"
    }
})
