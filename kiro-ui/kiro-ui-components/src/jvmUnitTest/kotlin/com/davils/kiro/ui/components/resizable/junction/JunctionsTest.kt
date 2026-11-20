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

package com.davils.kiro.ui.components.resizable.junction

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

private const val TOLERANCE: Float = 4f

private fun actions(id: Int): HandleActions = HandleActions(
    begin = { check(id >= 0) },
    dragTo = {},
    end = {},
    nudge = {},
)

private val column = HandleSpot(
    isVerticalLine = true,
    bounds = Rect(left = 99f, top = 0f, right = 101f, bottom = 200f),
    actions = actions(1),
)
private val leftRow = HandleSpot(
    isVerticalLine = false,
    bounds = Rect(left = 0f, top = 79f, right = 99f, bottom = 81f),
    actions = actions(2),
)
private val rightRow = HandleSpot(
    isVerticalLine = false,
    bounds = Rect(left = 101f, top = 79f, right = 300f, bottom = 81f),
    actions = actions(3),
)
private val crossing = HandleSpot(
    isVerticalLine = false,
    bounds = Rect(left = 0f, top = 149f, right = 300f, bottom = 151f),
    actions = actions(4),
)
private val apart = HandleSpot(
    isVerticalLine = false,
    bounds = Rect(left = 150f, top = 39f, right = 300f, bottom = 41f),
    actions = actions(5),
)

class JunctionsTest : FunSpec({
    context("junctions") {
        test("joins rows that end on both sides of a column at the same height") {
            val junctions = junctionsOf(spots = listOf(column, leftRow, rightRow), tolerance = TOLERANCE)

            junctions.size shouldBe 1
            junctions.first().point shouldBe Offset(x = 100f, y = 80f)
            junctions.first().columns shouldBe listOf(column.actions)
            junctions.first().rows shouldBe listOf(leftRow.actions, rightRow.actions)
        }

        test("finds lines that cross") {
            junctionsOf(
                spots = listOf(column, crossing),
                tolerance = TOLERANCE,
            ).single().point shouldBe Offset(x = 100f, y = 150f)
        }

        test("ignores rows that do not reach the column") {
            junctionsOf(spots = listOf(column, apart), tolerance = TOLERANCE).shouldBeEmpty()
        }
    }
})
