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

package com.davils.kiro.ui.motion.export

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class FrameTimesTest : FunSpec({
    test("spaces the frames evenly and ends before the end") {
        frameTimes(from = 1.seconds, to = 2.seconds, framesPerSecond = 4).toList() shouldBe
            listOf(1000.milliseconds, 1250.milliseconds, 1500.milliseconds, 1750.milliseconds)
    }

    test("has no frames between equal times") {
        frameTimes(from = 1.seconds, to = 1.seconds, framesPerSecond = 30).toList() shouldBe emptyList()
    }

    test("rejects a frame rate that is not positive and an end before the start") {
        shouldThrow<IllegalArgumentException> { frameTimes(from = 1.seconds, to = 2.seconds, framesPerSecond = 0) }
        shouldThrow<IllegalArgumentException> { frameTimes(from = 2.seconds, to = 1.seconds, framesPerSecond = 30) }
    }
})
