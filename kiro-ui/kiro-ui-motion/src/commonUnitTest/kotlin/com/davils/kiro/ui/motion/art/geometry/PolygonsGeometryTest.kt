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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val TRIANGLE: Int = 3
private const val SQUARE: Int = 4
private const val OCTAGON: Int = 8
private const val RADIUS: Float = 10f
private const val TOLERANCE: Float = 0.001f
private const val HALF: Float = 0.5f
private const val QUARTER_TURN: Double = 0.25
private const val RINGS: Int = 6
private const val OFFSET: Int = 4

class PolygonsGeometryTest : FunSpec({
    test("grows the number of sides outwards and starts over") {
        polygonSides(ring = 0, least = TRIANGLE, most = OCTAGON) shouldBe TRIANGLE
        polygonSides(ring = 1, least = TRIANGLE, most = OCTAGON) shouldBe SQUARE
        polygonSides(ring = RINGS, least = TRIANGLE, most = OCTAGON) shouldBe TRIANGLE
        polygonSides(ring = 1, least = 0, most = 0) shouldBe TRIANGLE
        mostSides(least = OCTAGON, most = SQUARE) shouldBe OCTAGON
    }

    test("puts the corners of a polygon on its circle") {
        val corners = FloatArray(SQUARE * 2)
        polygonCorners(center = Offset.Zero, radius = RADIUS, sides = SQUARE, turn = QUARTER_TURN, into = corners)

        corners[0] shouldBe (0f plusOrMinus TOLERANCE)
        corners[1] shouldBe (RADIUS plusOrMinus TOLERANCE)
    }

    test("traces a part of a polygon and ends at the tip") {
        val corners = floatArrayOf(0f, 0f, RADIUS, 0f, RADIUS, RADIUS, 0f, RADIUS)
        val path = Path()

        path.polygonTrace(corners = corners, sides = SQUARE, progress = HALF * HALF / 2f) shouldBe
            Offset(x = RADIUS * HALF, y = 0f)
        path.polygonTrace(corners = corners, sides = SQUARE, progress = 1f) shouldBe Offset.Zero
        path.polygon(corners = corners, sides = SQUARE)
        path.getBounds().width shouldBe RADIUS
    }

    test("moves the trace around the rings") {
        traceRing(index = 1, rings = RINGS, offset = OFFSET) shouldBe OFFSET + 1
        traceRing(index = 2, rings = RINGS, offset = OFFSET) shouldBe 0
        traceRing(index = 2, rings = 0, offset = OFFSET) shouldBe 0
    }
})
