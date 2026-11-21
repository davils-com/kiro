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
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import kotlin.math.sqrt

private const val TOLERANCE: Float = 0.001f
private const val YAW: Double = 0.7
private const val PITCH: Double = 0.4
private const val EYE: Float = 3f
private const val EYE_SHARE: Float = 1.5f
private const val HALF: Float = 0.5f
private const val AXES: Int = 3

private val corners: Map<WireframeSolid, Pair<Int, Int>> = mapOf(
    WireframeSolid.Cube to (8 to 12),
    WireframeSolid.Octahedron to (6 to 12),
    WireframeSolid.Icosahedron to (12 to 30),
    WireframeSolid.Dodecahedron to (20 to 30),
)

class WireframeGeometryTest : FunSpec({
    test("builds every solid with its corners on the unit sphere and its edges") {
        for ((solid, counts) in corners) {
            val vertices = solidVertices(solid)

            vertices.size / AXES shouldBe counts.first
            solidEdges(vertices).size / 2 shouldBe counts.second
            for (index in 0 until vertices.size / AXES) {
                lengthOf(vertices = vertices, index = index) shouldBe (1f plusOrMinus TOLERANCE)
            }
        }
    }

    test("turns the corners without moving them off the sphere") {
        val vertices = solidVertices(WireframeSolid.Icosahedron)
        val turned = FloatArray(vertices.size)
        rotateVertices(vertices = vertices, yaw = YAW, pitch = PITCH, into = turned)

        for (index in 0 until turned.size / AXES) {
            lengthOf(vertices = turned, index = index) shouldBe (1f plusOrMinus TOLERANCE)
        }
    }

    test("enlarges what lies near the eye") {
        perspectiveOf(depth = 1f, eye = EYE) shouldBe EYE_SHARE
        perspectiveOf(depth = 0f, eye = 0f) shouldBe 1f
        silhouetteOf(EYE) shouldBeGreaterThan 1f
        nearnessOf(0f) shouldBe HALF
        nearnessOf(-EYE) shouldBe 0f
    }
})

private fun lengthOf(vertices: FloatArray, index: Int): Float {
    val x = vertices[index * AXES]
    val y = vertices[index * AXES + 1]
    val z = vertices[index * AXES + 2]
    return sqrt(x * x + y * y + z * z)
}
