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

package com.davils.kiro.ui.motion.art.tiling

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private const val WIDTH: Float = 200f
private const val HEIGHT: Float = 120f
private const val CELL: Float = 40f
private const val STEPS: Int = 4
private const val TOLERANCE: Float = 0.001f
private const val PIECES: Int = 8
private const val HALF_CELL: Float = 20f

class WeaveLatticeTest : FunSpec({
    test("weaves every pattern into frames, lines and panels") {
        for (pattern in WeavePattern.entries) {
            val lattice = WeaveLattice.of(
                pattern = pattern,
                width = WIDTH,
                height = HEIGHT,
                cell = CELL,
                arcSteps = STEPS,
            )

            lattice.frames shouldBeGreaterThan 0
            lattice.count shouldBeGreaterThan lattice.frames
            lattice.panels.size shouldBeGreaterThan 0
        }
    }

    test("weaves nothing without a cell or an area") {
        val asanoha = WeavePattern.Asanoha
        val kagome = WeavePattern.Kagome
        val shippo = WeavePattern.Shippo
        val empty = WeaveLattice.Empty
        WeaveLattice.of(pattern = asanoha, width = WIDTH, height = HEIGHT, cell = 0f, arcSteps = STEPS) shouldBe empty
        WeaveLattice.of(pattern = kagome, width = 0f, height = HEIGHT, cell = CELL, arcSteps = STEPS) shouldBe empty
        WeaveLattice.of(pattern = shippo, width = WIDTH, height = 0f, cell = CELL, arcSteps = 0) shouldBe empty
        WeaveLattice.Empty.count shouldBe 0
    }

    test("shifts every other row of the triangles by half a cell") {
        vertexX(column = 1, row = 0, cell = CELL) shouldBe CELL
        vertexX(column = 0, row = 1, cell = CELL) shouldBe HALF_CELL
        vertexX(column = 0, row = -1, cell = CELL) shouldBe HALF_CELL
    }

    test("puts the points of a circle on its radius") {
        val points = circleOf(x = 0f, y = 0f, radius = CELL, pieces = PIECES)

        points.size shouldBe PIECES * 2
        for (piece in 0 until PIECES) {
            val x = points[piece * 2]
            val y = points[piece * 2 + 1]
            x * x + y * y shouldBe (CELL * CELL plusOrMinus CELL * TOLERANCE)
        }
    }

    test("collects frames before lines") {
        val builder = LatticeBuilder()
        builder.line(x1 = 1f, y1 = 1f, x2 = 2f, y2 = 2f)
        builder.frame(x1 = 0f, y1 = 0f, x2 = 1f, y2 = 1f)
        builder.panel(floatArrayOf(0f, 0f, 1f, 0f, 0f, 1f))
        val lattice = builder.build()

        lattice.frames shouldBe 1
        lattice.count shouldBe 2
        lattice.segments[0] shouldBe 0f
        lattice.panels.size shouldBe 1
    }
})
