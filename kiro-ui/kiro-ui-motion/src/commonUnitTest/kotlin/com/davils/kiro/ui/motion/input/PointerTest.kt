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

package com.davils.kiro.ui.motion.input

import androidx.compose.ui.geometry.Offset
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val FRAME: Long = 1_000_000_000L / 60L
private const val TENTH_SECOND: Long = 100_000_000L
private const val TOLERANCE: Float = 0.001f
private const val QUARTER: Float = 0.25f
private const val TWO_QUARTERS: Float = 0.4375f
private const val HALF: Float = 0.5f
private const val TOO_STRONG: Float = 3f
private const val START: Float = 10f
private const val STEP: Float = 10f
private const val TARGET: Float = 30f
private const val FAR: Float = 100f
private const val CLOSE: Float = 99.9f
private const val SPEED: Float = 600f
private const val SETTLING_FRAMES: Int = 20

private val start: Offset = Offset(x = START, y = START)
private val target: Offset = Offset(x = TARGET, y = START)

class PointerTest : FunSpec({
    test("covers the share of the smoothing in a sixtieth of a second") {
        smoothingShare(smoothing = QUARTER, deltaNanos = FRAME) shouldBe (QUARTER plusOrMinus TOLERANCE)
        smoothingShare(smoothing = QUARTER, deltaNanos = FRAME * 2) shouldBe (TWO_QUARTERS plusOrMinus TOLERANCE)
        smoothingShare(smoothing = 1f, deltaNanos = FRAME) shouldBe 1f
        smoothingShare(smoothing = HALF, deltaNanos = 0L) shouldBe 0f
        smoothingShare(smoothing = TOO_STRONG, deltaNanos = -FRAME) shouldBe 0f
    }

    test("approaches the target and snaps to it when close") {
        val far = Offset(x = FAR, y = 0f)

        approach(current = Offset.Zero, target = far, share = QUARTER) shouldBe Offset(x = FAR * QUARTER, y = 0f)
        approach(current = Offset(x = CLOSE, y = 0f), target = far, share = HALF) shouldBe far
    }

    test("measures the velocity in pixels per second") {
        val moved = Offset(x = STEP, y = 0f)

        velocityOf(from = Offset.Zero, to = moved, deltaNanos = TENTH_SECOND) shouldBe Offset(x = STEP * STEP, y = 0f)
        velocityOf(from = Offset.Zero, to = moved, deltaNanos = 0L) shouldBe Offset.Zero
    }

    test("tracks a pointer: jumps in, smooths moves, settles and exits") {
        val tracker = PointerTracker()

        tracker.isSettling shouldBe false
        tracker.step(deltaNanos = FRAME, smoothing = HALF)
        tracker.pointer shouldBe null

        tracker.move(position = start, isPressed = true)
        tracker.isSettling shouldBe true
        tracker.step(deltaNanos = FRAME, smoothing = HALF)
        tracker.pointer shouldBe start
        tracker.isPressed shouldBe true
        tracker.isSettling shouldBe false

        tracker.move(position = target, isPressed = false)
        tracker.step(deltaNanos = FRAME, smoothing = HALF)
        tracker.pointer?.x shouldBe (START + STEP plusOrMinus TOLERANCE)
        tracker.velocity.x shouldBe (SPEED plusOrMinus QUARTER)
        tracker.isSettling shouldBe true
        repeat(SETTLING_FRAMES) { tracker.step(deltaNanos = FRAME, smoothing = HALF) }
        tracker.pointer shouldBe target
        tracker.velocity shouldBe Offset.Zero
        tracker.isSettling shouldBe false

        tracker.exit()
        tracker.pointer shouldBe null
        tracker.isPressed shouldBe false
        tracker.isSettling shouldBe false
    }

    test("adds the smoothed pointer to the inputs of the application") {
        val tracker = PointerTracker()
        val external = ArtInputs.None.copy(progress = HALF)

        tracker.inputsOf(external) shouldBe external
        tracker.move(position = start, isPressed = true)
        tracker.step(deltaNanos = FRAME, smoothing = HALF)
        tracker.inputsOf(external) shouldBe external.copy(pointer = start, isPressed = true)
    }
})
