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
import io.kotest.matchers.shouldBe

private const val ORIGIN: Float = 6f
private const val CELL: Float = 10f
private const val EXTENT: Float = 100f
private const val LAST: Int = 9
private const val MAJOR: Int = 5
private const val BAND: Float = 20f
private const val REACH: Float = 4f
private const val HALF: Float = 0.5f

class BlueprintGeometryTest : FunSpec({
    test("finds the lines of the grid inside the area") {
        firstLine(origin = ORIGIN, cell = CELL) shouldBe 0
        firstLine(origin = ORIGIN, cell = 0f) shouldBe 0
        lastLine(origin = ORIGIN, extent = EXTENT, cell = CELL) shouldBe LAST
        lastLine(origin = ORIGIN, extent = EXTENT, cell = 0f) shouldBe -1
    }

    test("marks every major line") {
        isMajorLine(index = MAJOR, major = MAJOR) shouldBe true
        isMajorLine(index = -MAJOR, major = MAJOR) shouldBe true
        isMajorLine(index = 1, major = MAJOR) shouldBe false
        isMajorLine(index = 0, major = 0) shouldBe false
    }

    test("lights what the scan line just passed") {
        scanWeight(distance = -REACH * 2, band = BAND, reach = REACH) shouldBe 0f
        scanWeight(distance = -1f, band = BAND, reach = REACH) shouldBe 1f
        scanWeight(distance = BAND * HALF, band = BAND, reach = REACH) shouldBe HALF
        scanWeight(distance = BAND * 2, band = BAND, reach = REACH) shouldBe 0f
        scanWeight(distance = 0f, band = 0f, reach = REACH) shouldBe 1f
    }
})
