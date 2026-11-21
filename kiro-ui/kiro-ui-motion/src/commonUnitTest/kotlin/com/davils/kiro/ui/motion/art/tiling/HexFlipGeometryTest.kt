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

import androidx.compose.animation.core.Easing
import androidx.compose.ui.graphics.Path
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.floats.shouldBeLessThan
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private const val WIDTH: Float = 320f
private const val HEIGHT: Float = 200f
private const val RADIUS: Float = 20f
private const val GAP: Float = 3.4641016f
private const val TOLERANCE: Float = 0.001f
private const val SHARE: Float = 0.5f
private const val SEED: Int = 11
private const val WAVES: Int = 64
private const val MIDDLE: Float = 0.5f
private const val EARLY: Float = 0.25f
private const val LATE: Float = 0.75f
private const val TURN: Float = 0.5f
private const val LOCAL: Double = 3.25
private const val SHADES: Int = 5
private const val LAST_SHADE: Int = 4
private const val TOO_FAR: Float = 2f

private val linear = Easing { fraction -> fraction }

class HexFlipGeometryTest : FunSpec({
    test("lays hexagons over the whole area with their distances from the origin") {
        val layout = HexLayout.of(width = WIDTH, height = HEIGHT, radius = RADIUS, originX = 0f, originY = 0f)

        layout.count shouldBeGreaterThan 1
        for (index in 0 until layout.count) {
            layout.distances[index] shouldBeLessThan TOO_FAR
            layout.xs[index] shouldBeGreaterThan -RADIUS
        }
        HexLayout.of(width = WIDTH, height = HEIGHT, radius = 0f, originX = 0f, originY = 0f) shouldBe HexLayout.Empty
        HexLayout.of(width = 0f, height = HEIGHT, radius = RADIUS, originX = 0f, originY = 0f) shouldBe HexLayout.Empty
        HexLayout.of(width = WIDTH, height = 0f, radius = RADIUS, originX = 0f, originY = 0f) shouldBe HexLayout.Empty
    }

    test("shows the back of a share of the cards") {
        val backs = (0 until WAVES).count { wave ->
            hexFace(column = 1, row = 2, wave = wave, share = SHARE, seed = SEED)
        }

        backs shouldBeGreaterThan 0
        hexFace(column = 1, row = 2, wave = 0, share = 0f, seed = SEED) shouldBe false
        hexFace(column = 1, row = 2, wave = 0, share = 1f, seed = SEED) shouldBe true
    }

    test("flips a card only when its face changes") {
        flipProgress(from = true, to = true, local = LOCAL, turn = TURN, easing = linear) shouldBe 1f
        val progress = flipProgress(from = false, to = true, local = LOCAL, turn = TURN, easing = linear)
        progress shouldBe (MIDDLE plusOrMinus TOLERANCE)
        flipFace(from = false, to = true, progress = EARLY) shouldBe false
        flipFace(from = false, to = true, progress = LATE) shouldBe true
    }

    test("squashes and leans a card while it turns") {
        flipSquash(0f) shouldBe 1f
        flipSquash(MIDDLE) shouldBe (0f plusOrMinus TOLERANCE)
        flipLean(0f) shouldBe 0f
        flipLean(MIDDLE) shouldBe 1f
    }

    test("shrinks a card by its gap and shades it from the origin") {
        cardRadius(radius = RADIUS, gap = GAP) shouldBe (RADIUS - 2f plusOrMinus TOLERANCE)
        cardRadius(radius = 1f, gap = RADIUS) shouldBe 0f
        shadeIndex(distance = 0f, shades = SHADES) shouldBe LAST_SHADE
        shadeIndex(distance = 1f, shades = SHADES) shouldBe 0
        shadeIndex(distance = TOO_FAR, shades = 0) shouldBe 0
    }

    test("draws a hexagon of a radius") {
        val path = Path()
        path.hexagon(radius = RADIUS)

        path.getBounds().height shouldBe (RADIUS * 2f plusOrMinus TOLERANCE)
    }
})
