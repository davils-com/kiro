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

package com.davils.kiro.ui.motion.art.grid

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe

private const val WIDTH: Float = 100f
private const val HEIGHT: Float = 60f
private const val PITCH: Float = 20f
private const val MIDDLE_X: Float = 50f
private const val MIDDLE_Y: Float = 30f
private const val CAP: Int = 10
private const val SQUARE: Float = 4f
private const val LEVELS: Int = 4
private const val FAINT: Float = 0.1f
private const val STRONG: Float = 0.5f
private const val TOLERANCE: Float = 0.0001f
private const val EVERY: Int = 4
private const val HALF: Float = 0.5f
private const val QUARTER: Double = 0.25
private const val SPAN: Int = 10
private const val LANES: Int = 25
private const val MAX_LANES: Int = 256

class GridGeometryTest : FunSpec({
    test("centers the cells on the middle of the area") {
        val cells = GridCells.of(width = WIDTH, height = HEIGHT, pitch = PITCH)
        val middle = cells.lineAt(position = MIDDLE_X, start = cells.left)

        cells.xOf(middle) shouldBe (MIDDLE_X plusOrMinus TOLERANCE)
        cells.yOf(cells.lineAt(position = MIDDLE_Y, start = cells.top)) shouldBe (MIDDLE_Y plusOrMinus TOLERANCE)
        cells.xOf(cells.columns) shouldBe (cells.xOf(0) + cells.columns * PITCH plusOrMinus TOLERANCE)
        cells.columnAt(MIDDLE_X + 1f) shouldBe middle
        cells.rowAt(MIDDLE_Y - 1f) shouldBe cells.lineAt(position = MIDDLE_Y, start = cells.top) - 1
    }

    test("has no cells in an empty area or without a pitch") {
        GridCells.of(width = 0f, height = HEIGHT, pitch = PITCH).columns shouldBe 0
        GridCells.of(width = WIDTH, height = 0f, pitch = PITCH).columns shouldBe 0
        GridCells.of(width = WIDTH, height = HEIGHT, pitch = 0f).columns shouldBe 0
    }

    test("marks every few lines from the middle as major") {
        isMajor(index = EVERY + 1, middle = 1, every = EVERY) shouldBe true
        isMajor(index = 1 - EVERY, middle = 1, every = EVERY) shouldBe true
        isMajor(index = 2, middle = 1, every = EVERY) shouldBe false
        isMajor(index = 1, middle = 1, every = 0) shouldBe false
    }

    test("makes the squares coarser beyond the cap") {
        val fine = flickerLayout(width = WIDTH, height = HEIGHT, square = SQUARE, pitch = SQUARE, cap = Int.MAX_VALUE)
        val coarse = flickerLayout(width = WIDTH, height = HEIGHT, square = SQUARE, pitch = SQUARE, cap = CAP)

        fine.square shouldBe SQUARE
        coarse.cells.columns * coarse.cells.rows shouldBeLessThan fine.cells.columns * fine.cells.rows
        (coarse.square > SQUARE) shouldBe true
        val single = flickerLayout(width = WIDTH, height = HEIGHT, square = SQUARE, pitch = SQUARE, cap = 0)
        (single.square > coarse.square) shouldBe true
    }

    test("picks a level between flicker and clouds") {
        levelOf(flicker = 0f, cloud = 1f, structure = 0f, levels = LEVELS) shouldBe 0
        levelOf(flicker = 0f, cloud = 1f, structure = 1f, levels = LEVELS) shouldBe LEVELS - 1
        levelOf(flicker = 1f, cloud = 0f, structure = 0f, levels = LEVELS) shouldBe LEVELS - 1
        levelOf(flicker = HALF, cloud = HALF, structure = 2f, levels = LEVELS) shouldBeInRange 0..<LEVELS
    }

    test("spreads the alpha over the levels") {
        alphaOf(level = 0, levels = LEVELS, from = FAINT, to = STRONG) shouldBe FAINT
        alphaOf(level = LEVELS - 1, levels = LEVELS, from = FAINT, to = STRONG) shouldBe (STRONG plusOrMinus TOLERANCE)
        alphaOf(level = 0, levels = 1, from = FAINT, to = STRONG) shouldBe STRONG
    }

    test("moves the rows of the floor closer over a period") {
        retroDepthOf(row = 0, phase = 0.0, depth = 1f) shouldBe 1f
        retroDepthOf(row = 1, phase = QUARTER, depth = 1f) shouldBe (2f - QUARTER.toFloat() plusOrMinus TOLERANCE)
        retroShareOf(depth = 2f) shouldBe HALF
        retroShareOf(depth = 0f) shouldBe Float.POSITIVE_INFINITY
    }

    test("fades the floor in below the horizon") {
        retroFadeOf(share = 0f, fade = HALF) shouldBe 0f
        retroFadeOf(share = 1f, fade = HALF) shouldBe 1f
        retroFadeOf(share = 0f, fade = 0f) shouldBe 1f
    }

    test("draws enough lanes to reach the sides") {
        retroLanesOf(halfWidth = WIDTH, cell = PITCH, fade = 1f) shouldBe LANES
        retroLanesOf(halfWidth = WIDTH, cell = 0f, fade = 1f) shouldBe 0
        retroLanesOf(halfWidth = WIDTH, cell = FAINT, fade = 0f) shouldBe MAX_LANES
    }

    test("grows a pulse from the horizon towards the viewer") {
        retroPulseOf(progress = 0f, length = HALF) shouldBe 0f..0f
        val late = retroPulseOf(progress = 1f, length = HALF)
        late.endInclusive shouldBe 1f + HALF
        late.start shouldBe (1f + HALF) * HALF
    }

    test("lets the drops fall at different speeds") {
        matrixSpeedOf(unit = HALF, spread = 1f) shouldBe 1f
        matrixSpeedOf(unit = 0f, spread = 1f) shouldBe FAINT
        matrixSpeedOf(unit = 1f, spread = HALF) shouldBe 1f + HALF
        matrixHeadOf(phase = QUARTER, offset = 1f, span = SPAN) shouldBe SPAN * QUARTER.toFloat()
        matrixFadeOf(step = 0, trail = 1) shouldBe 1f
        matrixFadeOf(step = 1, trail = 1) shouldBe (QUARTER.toFloat() plusOrMinus TOLERANCE)
        matrixFadeOf(step = 0, trail = -2) shouldBe 1f
        matrixShareOf(column = 0, start = HALF) shouldBe HALF
        (matrixShareOf(column = SPAN, start = HALF) in 0f..1f) shouldBe true
    }
})
