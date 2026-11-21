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
import io.kotest.matchers.shouldBe

private const val LIGHT_WEIGHT: Float = 1f
private const val HEAVY_WEIGHT: Float = 3f
private const val JUST_BELOW: Float = 0.24f
private const val JUST_ABOVE: Float = 0.26f
private const val HALF: Float = 0.5f
private const val LAST: Int = 3
private const val TURNS: Int = 2

class BauhausGeometryTest : FunSpec({
    test("picks an index in proportion to its weight") {
        val weights = floatArrayOf(LIGHT_WEIGHT, 0f, HEAVY_WEIGHT)

        weightedIndex(weights = weights, unit = 0f) shouldBe 0
        weightedIndex(weights = weights, unit = JUST_BELOW) shouldBe 0
        weightedIndex(weights = weights, unit = JUST_ABOVE) shouldBe 2
        weightedIndex(weights = weights, unit = 1f) shouldBe 2
    }

    test("ignores negative weights and has no pick without weight") {
        weightedIndex(weights = floatArrayOf(-1f, 2f), unit = 0f) shouldBe 1
        weightedIndex(weights = floatArrayOf(0f, -LIGHT_WEIGHT), unit = HALF) shouldBe -1
        weightedIndex(weights = floatArrayOf(), unit = HALF) shouldBe -1
    }

    test("counts quarters around the circle") {
        quarterOf(resting = LAST, direction = 1, turns = TURNS) shouldBe 1
        quarterOf(resting = 0, direction = -1, turns = 1) shouldBe LAST
    }
})
