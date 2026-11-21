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

package com.davils.kiro.ui.motion.art.geometry

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private const val WIDTH: Float = 200f
private const val HEIGHT: Float = 100f
private const val TILE: Float = 40f
private const val REACH: Float = 60f
private const val TOLERANCE: Float = 0.0001f
private const val QUARTERS: Float = 4f
private const val COLUMN: Int = 3
private const val DIAGONAL: Int = 5
private const val FREQUENCY: Float = 0.25f
private const val HALF_PERIOD: Double = 0.5
private const val HALF: Float = 0.5f
private const val SEED: Int = 2
private const val OTHER_SEED: Int = 9
private const val SQUARE_ROOT_OF_TWO: Float = 1.4142f
private const val ROUGHLY: Float = 0.001f

class IsometricGeometryTest : FunSpec({
    test("covers the area with diagonals and columns of blocks") {
        val grid = requireNotNull(IsometricGrid.of(width = WIDTH, height = HEIGHT, tile = TILE, reach = REACH))

        grid.quarter shouldBe TILE / QUARTERS
        grid.firstDiagonal shouldBe -1
        (grid.yOf(diagonal = grid.lastDiagonal) > HEIGHT + REACH) shouldBe true
        (grid.xOf(column = grid.lastColumn) > WIDTH) shouldBe true
        (grid.xOf(column = grid.firstColumn) < 0f) shouldBe true
    }

    test("maps screen positions back to the grid and starts each diagonal on its parity") {
        val grid = requireNotNull(IsometricGrid.of(width = WIDTH, height = HEIGHT, tile = TILE, reach = REACH))

        grid.columnAt(x = grid.xOf(column = COLUMN)) shouldBe COLUMN.toFloat()
        grid.diagonalAt(y = grid.yOf(diagonal = DIAGONAL)) shouldBe DIAGONAL.toFloat()
        (grid.firstColumnOf(diagonal = DIAGONAL) + DIAGONAL).mod(2) shouldBe 0
        (grid.firstColumnOf(diagonal = 2) + 2).mod(2) shouldBe 0
    }

    test("has no grid without an area or a tile") {
        IsometricGrid.of(width = 0f, height = HEIGHT, tile = TILE, reach = REACH) shouldBe null
        IsometricGrid.of(width = WIDTH, height = 0f, tile = TILE, reach = REACH) shouldBe null
        IsometricGrid.of(width = WIDTH, height = HEIGHT, tile = 0f, reach = REACH) shouldBe null
    }

    test("crests at the origin at the start and runs outwards") {
        waveHeight(distance = 0f, frequency = FREQUENCY, phase = 0.0) shouldBe (1f plusOrMinus TOLERANCE)
        waveHeight(distance = 2f, frequency = FREQUENCY, phase = 0.0) shouldBe (0f plusOrMinus TOLERANCE)
        waveHeight(distance = 2f, frequency = FREQUENCY, phase = HALF_PERIOD) shouldBe (1f plusOrMinus TOLERANCE)
    }

    test("mixes the wave with the height of the block itself") {
        val own = blockHeight(wave = HALF, jitter = 1f, column = 1, diagonal = DIAGONAL, seed = SEED)

        blockHeight(wave = HALF, jitter = 0f, column = 1, diagonal = DIAGONAL, seed = SEED) shouldBe HALF
        own shouldNotBe blockHeight(wave = HALF, jitter = 1f, column = 1, diagonal = DIAGONAL, seed = OTHER_SEED)
    }

    test("measures distances in tiles") {
        val diagonal = gridDistance(column = 0f, diagonal = 2f, originColumn = 0f, originDiagonal = 0f)

        diagonal shouldBe (SQUARE_ROOT_OF_TWO plusOrMinus ROUGHLY)
        gridDistance(column = 1f, diagonal = 1f, originColumn = 0f, originDiagonal = 0f) shouldBe 1f
    }
})
