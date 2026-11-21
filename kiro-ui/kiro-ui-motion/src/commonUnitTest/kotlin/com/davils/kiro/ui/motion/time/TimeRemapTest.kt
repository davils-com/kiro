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
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private const val QUARTER_BEFORE: Int = -250
private const val FIVE_QUARTERS_BEFORE: Int = -1250

class TimeRemapTest : FunSpec({
    test("leaves the time as it is by default") {
        TimeRemap.Identity.apply(ArtTime.of(3.seconds)) shouldBe ArtTime.of(3.seconds)
    }

    test("scales, shifts and freezes the time") {
        val remap = TimeRemap.Identity.copy(speed = 0.5f, offset = 1.seconds)

        remap.apply(ArtTime.of(4.seconds)) shouldBe ArtTime.of(3.seconds)
        remap.copy(isFrozen = true).apply(ArtTime.of(4.seconds)) shouldBe ArtTime.of(1.seconds)
    }

    test("loops and ping-pongs the time") {
        val loop = TimeRemap.Identity.copy(loop = 1.seconds)

        loop.apply(ArtTime.of(1250.milliseconds)) shouldBe ArtTime.of(250.milliseconds)
        loop.copy(isPingPong = true).apply(ArtTime.of(1250.milliseconds)) shouldBe ArtTime.of(750.milliseconds)
        loop.copy(isPingPong = true).apply(ArtTime.of(2250.milliseconds)) shouldBe ArtTime.of(250.milliseconds)
        loop.copy(loop = 0.seconds).apply(ArtTime.of(1250.milliseconds)) shouldBe ArtTime.of(1250.milliseconds)
    }

    test("loops times before the start") {
        val pingPong = TimeRemap.Identity.copy(loop = 1.seconds, isPingPong = true)

        pingPong.apply(ArtTime.of(QUARTER_BEFORE.milliseconds)) shouldBe ArtTime.of(250.milliseconds)
        pingPong.apply(ArtTime.of((-1).seconds)) shouldBe ArtTime.of(1.seconds)
        pingPong.apply(ArtTime.of(FIVE_QUARTERS_BEFORE.milliseconds)) shouldBe ArtTime.of(750.milliseconds)
    }

    test("is a value") {
        TimeRemap.Identity.copy() shouldBe TimeRemap.Identity
        TimeRemap.Identity.copy().hashCode() shouldBe TimeRemap.Identity.hashCode()
        TimeRemap.Identity.toString() shouldContain "loop=null"
        TimeRemap.Identity.copy(loop = 1.seconds).toString() shouldContain "loop=1s"
        TimeRemap.Identity.equals(ArtTime.Zero) shouldBe false
    }
})
