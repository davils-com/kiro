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

package com.davils.kiro.ui.motion.art.field

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.floats.shouldBeLessThan
import io.kotest.matchers.shouldBe

private const val TOLERANCE: Float = 0.0001f
private const val RIGHT_ANGLE: Float = 90f
private const val ELEVATION: Float = 30f
private const val SIDE: Float = 10f
private const val RISE: Float = 5f

private fun lightAt(azimuth: Float, elevation: Float): FloatArray =
    FloatArray(size = 3).also { light -> lightOf(azimuth = azimuth, elevation = elevation, light = light) }

class LowPolyGeometryTest : FunSpec({
    val flat = floatArrayOf(0f, 0f, 0f, SIDE, 0f, 0f, 0f, SIDE, 0f)
    val towardLight = floatArrayOf(0f, 0f, RISE, SIDE, 0f, 0f, 0f, SIDE, RISE)
    val awayFromLight = floatArrayOf(0f, 0f, 0f, SIDE, 0f, RISE, 0f, SIDE, 0f)

    test("points the light straight down from above the surface") {
        val light = lightAt(azimuth = 0f, elevation = RIGHT_ANGLE)

        light[0] shouldBe (0f plusOrMinus TOLERANCE)
        light[2] shouldBe (1f plusOrMinus TOLERANCE)
    }

    test("leaves a flat facet unshaded whatever its winding") {
        val light = lightAt(azimuth = 0f, elevation = ELEVATION)

        shadeOf(points = flat, first = 0, second = 1, third = 2, light = light) shouldBe (0f plusOrMinus TOLERANCE)
        shadeOf(points = flat, first = 0, second = 2, third = 1, light = light) shouldBe (0f plusOrMinus TOLERANCE)
    }

    test("brightens a facet turned toward the light and darkens one turned away") {
        val light = lightAt(azimuth = 0f, elevation = ELEVATION)

        shadeOf(points = towardLight, first = 0, second = 1, third = 2, light = light) shouldBeGreaterThan 0f
        shadeOf(points = awayFromLight, first = 0, second = 1, third = 2, light = light) shouldBeLessThan 0f
    }

    test("leaves a degenerate facet unshaded") {
        val light = lightAt(azimuth = 0f, elevation = ELEVATION)

        shadeOf(points = flat, first = 0, second = 0, third = 1, light = light) shouldBe 0f
    }
})
