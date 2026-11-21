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
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val HALF: Float = 0.5f
private const val ABOVE_CENTER: Float = 0.6f
private const val WIDE: Int = 4
private const val TOLERANCE: Float = 0.0001f
private const val HIGH: Float = 0.75f
private const val QUARTER: Float = 0.25f
private const val NEAR_TOP: Float = 0.4f
private const val NEAR_RIGHT: Float = 0.6f
private const val THIRD: Float = 3f
private const val FOURTH: Float = 4f
private const val PIECES: Int = 4
private const val HEXAGON: Int = 6

private fun linesOf(field: FloatArray, columns: Int, level: Float = HALF): RecordingSink {
    val sink = RecordingSink()
    contourLines(field = field, columns = columns, rows = field.size / columns, level = level, sink = sink)
    return sink
}

private fun bandsOf(field: FloatArray, columns: Int, level: Float = HALF): RecordingSink {
    val sink = RecordingSink()
    contourBands(field = field, columns = columns, rows = field.size / columns, level = level, sink = sink)
    return sink
}

class ContourGeometryTest : FunSpec({
    context("lines") {
        test("draws nothing in a cell that lies wholly below or above the level") {
            linesOf(floatArrayOf(0f, 0f, 0f, 0f), columns = 2).shapes.shouldBeEmpty()
            linesOf(floatArrayOf(1f, 1f, 1f, 1f), columns = 2).shapes.shouldBeEmpty()
        }

        test("cuts off a single corner between the crossings of its edges") {
            val sink = linesOf(floatArrayOf(1f, 0f, 0f, 0f), columns = 2)

            sink.shapes shouldContainExactly listOf(listOf(HALF to 0f, 0f to HALF))
        }

        test("interpolates the crossing along an edge") {
            val sink = linesOf(floatArrayOf(1f, 1f, 0f, 0f), columns = 2, level = HIGH)

            sink.shapes shouldContainExactly listOf(listOf(1f to QUARTER, 0f to QUARTER))
        }

        test("separates the corners below the level of a saddle whose center lies above it") {
            val sink = linesOf(floatArrayOf(1f, 0f, 0f, 1f), columns = 2)

            sink.shapes shouldContainExactly listOf(
                listOf(HALF to 0f, 1f to HALF),
                listOf(HALF to 1f, 0f to HALF),
            )
        }

        test("separates the corners above the level of a saddle whose center lies below it") {
            val sink = linesOf(floatArrayOf(1f, 0f, 0f, 1f), columns = 2, level = ABOVE_CENTER)

            sink.shapes shouldHaveSize 2
            sink.shapes[0][0].second shouldBe (NEAR_TOP plusOrMinus TOLERANCE)
            sink.shapes[1][1].first shouldBe (NEAR_RIGHT plusOrMinus TOLERANCE)
        }
    }

    context("bands") {
        test("fills nothing below the level") {
            bandsOf(floatArrayOf(0f, 0f, 0f, 0f), columns = 2).shapes.shouldBeEmpty()
        }

        test("merges a row of full cells into one rectangle") {
            val sink = bandsOf(FloatArray(size = WIDE * 2) { 1f }, columns = WIDE)

            sink.shapes shouldContainExactly listOf(listOf(0f to 0f, THIRD to 0f, THIRD to 1f, 0f to 1f))
            sink.closed shouldBe 1
        }

        test("fills the corner of a cell that rises above the level") {
            val sink = bandsOf(floatArrayOf(1f, 0f, 0f, 0f), columns = 2)

            sink.shapes shouldContainExactly listOf(listOf(0f to 0f, HALF to 0f, 0f to HALF))
        }

        test("closes a run of full cells before a partial one and after it") {
            val sink = bandsOf(floatArrayOf(1f, 1f, 0f, 1f, 1f, 1f, 1f, 1f, 1f, 1f), columns = 5)

            sink.shapes shouldHaveSize PIECES
            sink.shapes.first() shouldContainExactly listOf(0f to 0f, 1f to 0f, 1f to 1f, 0f to 1f)
            sink.shapes.last() shouldContainExactly listOf(THIRD to 0f, FOURTH to 0f, FOURTH to 1f, THIRD to 1f)
        }

        test("joins the corners of a saddle whose center lies above the level") {
            val sink = bandsOf(floatArrayOf(1f, 0f, 0f, 1f), columns = 2)

            sink.shapes shouldHaveSize 1
            sink.shapes.first() shouldHaveSize HEXAGON
        }

        test("keeps the corners of a saddle apart whose center lies below the level") {
            val sink = bandsOf(floatArrayOf(1f, 0f, 0f, 1f), columns = 2, level = ABOVE_CENTER)

            sink.shapes shouldHaveSize 2
            sink.closed shouldBe 2
        }
    }
})
