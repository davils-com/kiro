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
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val MORPH: Float = 0.5f
private const val CREST: Double = 2.25
private const val REST: Double = 0.75
private const val QUARTER: Double = 0.125
private const val TOLERANCE: Float = 0.001f
private const val HALF: Float = 0.5f
private const val BAND: Int = 2
private const val FAR_COLUMN: Int = 5
private const val THIRD_DIAGONAL: Int = 3

private val linear = Easing { fraction -> fraction }

class CheckerMorphGeometryTest : FunSpec({
    test("rounds a square to a circle and back within its share of the wave") {
        morphOf(local = CREST, morph = MORPH, easing = linear) shouldBe (1f plusOrMinus TOLERANCE)
        morphOf(local = QUARTER, morph = MORPH, easing = linear) shouldBe (HALF plusOrMinus TOLERANCE)
        morphOf(local = REST, morph = MORPH, easing = linear) shouldBe 0f
        morphOf(local = CREST, morph = 0f, easing = linear) shouldBe 0f
    }

    test("picks every other square and bands the diagonals") {
        isCheckerCell(column = 0, row = 0) shouldBe true
        isCheckerCell(column = 1, row = 0) shouldBe false
        checkerBand(column = FAR_COLUMN, row = 1, band = BAND) shouldBe 1
        checkerBand(column = FAR_COLUMN, row = 1, band = 0) shouldBe THIRD_DIAGONAL
    }
})
