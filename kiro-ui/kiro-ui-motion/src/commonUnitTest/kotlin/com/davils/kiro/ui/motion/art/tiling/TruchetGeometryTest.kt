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

import androidx.compose.animation.core.LinearEasing
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val WIDTH: Float = 100f
private const val HEIGHT: Float = 50f
private const val PITCH: Float = 30f
private const val COLUMNS: Int = 4
private const val ROWS: Int = 2
private const val WAVES: Int = 40
private const val TOLERANCE: Float = 0.0001f
private const val TURNS: Int = 3
private const val QUARTER_PAST: Double = 3.25
private const val THREE_QUARTERS_PAST: Double = 3.75
private const val BEFORE_START: Double = -0.25
private const val HALF: Float = 0.5f
private const val COLUMN: Int = 1
private const val ROW: Int = 2
private const val SEED: Int = 3
private const val BAND_COLUMN: Int = 2
private const val BAND_ROW: Int = 3
private const val SUM: Int = 5
private const val RADIUS: Float = 10f

class TruchetGeometryTest : FunSpec({
    test("covers the area with a centered grid of whole tiles") {
        val grid = TileGrid.of(width = WIDTH, height = HEIGHT, pitch = PITCH)

        grid.columns shouldBe COLUMNS
        grid.rows shouldBe ROWS
        grid.leftOf(column = 0) shouldBe (WIDTH - COLUMNS * PITCH) / 2f
        grid.topOf(row = 1) shouldBe (HEIGHT - ROWS * PITCH) / 2f + PITCH
    }

    test("has no tiles without an area or a pitch") {
        TileGrid.of(width = 0f, height = HEIGHT, pitch = PITCH) shouldBe TileGrid.Empty
        TileGrid.of(width = WIDTH, height = 0f, pitch = PITCH) shouldBe TileGrid.Empty
        TileGrid.of(width = WIDTH, height = HEIGHT, pitch = 0f) shouldBe TileGrid.Empty
    }

    test("counts whole turns and eases the turn at the start of each") {
        wholeTurns(phase = QUARTER_PAST) shouldBe TURNS
        wholeTurns(phase = BEFORE_START) shouldBe -1
        turnProgress(phase = QUARTER_PAST, turn = HALF, easing = LinearEasing) shouldBe (HALF plusOrMinus TOLERANCE)
        turnProgress(phase = THREE_QUARTERS_PAST, turn = HALF, easing = LinearEasing) shouldBe 1f
        turnProgress(phase = QUARTER_PAST, turn = 0f, easing = LinearEasing) shouldBe 1f
    }

    test("turns booleans into bits") {
        true.toBit() shouldBe 1
        false.toBit() shouldBe 0
    }

    test("keeps or flips the resting orientation by its share") {
        val resting = truchetOrientation(column = COLUMN, row = ROW, wave = 0, flip = 0f, seed = SEED)
        val orientations = { flip: Float ->
            val waves = 0 until WAVES
            waves.map { wave -> truchetOrientation(column = COLUMN, row = ROW, wave = wave, flip = flip, seed = SEED) }
        }

        orientations(0f).toSet() shouldBe setOf(resting)
        orientations(1f).toSet() shouldBe setOf(1 - resting)
        orientations(HALF).toSet() shouldBe setOf(0, 1)
    }

    test("turns both ways") {
        val directions = (0 until WAVES).map { wave -> truchetDirection(column = 0, row = 0, wave = wave, seed = 1) }

        directions shouldContainAll listOf(-1, 1)
    }

    test("picks lines by variant") {
        truchetLine(variant = TruchetVariant.Arcs, column = 0, row = 0, share = 1f, seed = 0) shouldBe false
        truchetLine(variant = TruchetVariant.Lines, column = 0, row = 0, share = 0f, seed = 0) shouldBe true
        truchetLine(variant = TruchetVariant.Mixed, column = 0, row = 0, share = 1f, seed = 0) shouldBe true
        truchetLine(variant = TruchetVariant.Mixed, column = 0, row = 0, share = 0f, seed = 0) shouldBe false
    }

    test("bands the diagonals") {
        truchetBand(column = BAND_COLUMN, row = BAND_ROW, band = 2) shouldBe 2
        truchetBand(column = -1, row = 0, band = 2) shouldBe -1
        truchetBand(column = BAND_COLUMN, row = BAND_ROW, band = 0) shouldBe SUM
    }

    test("leads most at the pointer and not beyond its reach") {
        pointerLead(dx = 0f, dy = 0f, radius = RADIUS, lead = HALF) shouldBe HALF
        pointerLead(dx = RADIUS, dy = 0f, radius = RADIUS, lead = HALF) shouldBe 0f
        pointerLead(dx = 0f, dy = 0f, radius = 0f, lead = HALF) shouldBe 0f
    }
})
