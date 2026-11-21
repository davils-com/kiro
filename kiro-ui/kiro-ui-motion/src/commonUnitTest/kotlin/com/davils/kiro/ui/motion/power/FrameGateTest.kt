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

package com.davils.kiro.ui.motion.power

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val MILLISECOND: Long = 1_000_000L
private const val FRAME: Long = 16L
private const val FRAMES: Int = 7
private const val CAP: Int = 30
private const val LATER: Long = 10L
private const val EARLIER: Long = 5L

private fun FrameGate.admitted(frames: Int): List<Long> {
    val deltas = (1..frames).map { frame -> admit(nanos = frame * FRAME * MILLISECOND) }
    return deltas.filter { delta -> delta != SKIPPED_FRAME }
}

class FrameGateTest : FunSpec({
    test("admits every frame without a cap, starting at no time") {
        val gate = FrameGate(maxFrameRate = null)
        val frame = FRAME * MILLISECOND

        gate.admitted(frames = FRAMES) shouldBe listOf(0L) + List(FRAMES - 1) { frame }
    }

    test("skips frames until the interval of the cap has passed") {
        val gate = FrameGate(maxFrameRate = CAP)
        val twoFrames = 2 * FRAME * MILLISECOND

        gate.admitted(frames = FRAMES) shouldBe listOf(0L, twoFrames, twoFrames, twoFrames)
    }

    test("measures the interval of a cap") {
        frameIntervalOf(maxFrameRate = CAP) shouldBe 1.seconds / CAP
        frameIntervalOf(maxFrameRate = null) shouldBe Duration.ZERO
        frameIntervalOf(maxFrameRate = -1) shouldBe Duration.ZERO
    }

    test("ignores caps that are not positive and skips time that runs backwards") {
        val gate = FrameGate(maxFrameRate = 0)

        gate.admit(nanos = LATER * MILLISECOND) shouldBe 0L
        gate.admit(nanos = EARLIER * MILLISECOND) shouldBe SKIPPED_FRAME
    }
})
