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

package com.davils.kiro.ui.motion.art.line

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val WIDTH: Float = 400f
private const val HEIGHT: Float = 200f
private const val HALF: Float = 0.5f
private const val QUARTER: Float = 0.25f
private const val TOLERANCE: Float = 0.0001f
private const val COUNT: Int = 4

class LineGeometryTest : FunSpec({
    val size = Size(width = WIDTH, height = HEIGHT)

    test("sweeps a curve from below the bottom to above the top, further right for later positions") {
        val first = beamCurveOf(position = 0f, spread = HALF, bend = QUARTER, size = size)
        val last = beamCurveOf(position = 1f, spread = HALF, bend = QUARTER, size = size)

        (first.start.y > HEIGHT) shouldBe true
        (first.end.y < 0f) shouldBe true
        (last.start.x > first.start.x) shouldBe true
        (last.end.x - last.start.x > first.end.x - first.start.x) shouldBe true
    }

    test("keeps the window of a beam on its curve") {
        beamWindowOf(progress = 0f, segment = QUARTER) shouldBe 0f..0f
        beamWindowOf(progress = 1f, segment = QUARTER) shouldBe 1f..1f
        val middle = beamWindowOf(progress = HALF, segment = HALF)
        middle.endInclusive shouldBe (HALF * (1f + HALF) plusOrMinus TOLERANCE)
        middle.start shouldBe (QUARTER plusOrMinus TOLERANCE)
        beamWindowOf(progress = HALF, segment = 2f).start shouldBe 0f
    }

    test("spreads the rings evenly over a period") {
        rippleFractionOf(phase = 0.0, index = 1, count = COUNT, offset = 0f) shouldBe QUARTER
        rippleFractionOf(phase = 0.0, index = COUNT, count = COUNT, offset = 0f) shouldBe 0f
        rippleFractionOf(phase = 0.0, index = 1, count = 0, offset = HALF) shouldBe HALF
    }

    test("moves the origin towards the pointer") {
        val base = Offset(x = WIDTH, y = HEIGHT)

        rippleOriginOf(base = base, pointer = null, follow = 1f) shouldBe base
        val halfway = Offset(x = WIDTH * HALF, y = HEIGHT * HALF)

        rippleOriginOf(base = base, pointer = Offset.Zero, follow = HALF) shouldBe halfway
        rippleOriginOf(base = base, pointer = Offset.Zero, follow = 2f) shouldBe Offset.Zero
    }
})
