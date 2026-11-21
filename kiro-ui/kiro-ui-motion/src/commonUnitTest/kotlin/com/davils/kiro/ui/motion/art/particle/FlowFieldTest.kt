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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.ui.geometry.Offset
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.math.hypot

private const val TOLERANCE: Float = 0.001f
private const val STEPS: Int = 12
private const val SEGMENT: Float = 3f
private const val SCALE: Float = 120f
private const val RADIUS: Float = 40f
private const val FAR: Float = 2000f
private const val NEAR: Float = 10f
private const val SEED: Int = 3
private const val START_X: Float = 5f
private const val START_Y: Float = 7f
private const val PROBE_X: Float = 30f
private const val PROBE_Y: Float = 40f

private fun fieldOf(kind: FlowFieldKind): FlowField = FlowField(kind = kind, octaves = 2, turns = 1f).also { field ->
    field.scale = SCALE
    field.seed = SEED
}

private fun angleOf(kind: FlowFieldKind): Float = fieldOf(kind).angleAt(x = PROBE_X, y = PROBE_Y)

class FlowFieldTest : FunSpec({
    test("traces a streamline of steps of equal length") {
        FlowFieldKind.entries.forEach { kind ->
            val points = FloatArray(size = (STEPS + 1) * 2)
            val angles = FloatArray(size = STEPS)
            val field = fieldOf(kind)
            field.trace(
                start = Offset(x = START_X, y = START_Y),
                steps = STEPS,
                segment = SEGMENT,
                points = points,
                angles = angles,
            )

            points[0] shouldBe START_X
            points[1] shouldBe START_Y
            for (step in 0 until STEPS) {
                val index = step * 2
                val dx = points[index + 2] - points[index]
                val dy = points[index + 2 + 1] - points[index + 1]
                hypot(x = dx, y = dy) shouldBe (SEGMENT plusOrMinus TOLERANCE)
            }
        }
    }

    test("points the same way for the same seed and depth") {
        angleOf(FlowFieldKind.Angle) shouldBe angleOf(FlowFieldKind.Angle)
        angleOf(FlowFieldKind.Curl) shouldNotBe angleOf(FlowFieldKind.Angle)
    }

    test("swirls around a pointer near it and leaves the field far from it alone") {
        val field = fieldOf(FlowFieldKind.Angle)
        val calm = field.angleAt(x = NEAR, y = 0f)
        val distant = field.angleAt(x = FAR, y = 0f)
        field.touch(pointer = Offset.Zero, strength = 1f, radius = RADIUS)

        field.angleAt(x = NEAR, y = 0f) shouldNotBe calm
        field.angleAt(x = FAR, y = 0f) shouldBe (distant plusOrMinus TOLERANCE)

        field.touch(pointer = null, strength = 1f, radius = RADIUS)
        field.angleAt(x = NEAR, y = 0f) shouldBe calm
    }
})
