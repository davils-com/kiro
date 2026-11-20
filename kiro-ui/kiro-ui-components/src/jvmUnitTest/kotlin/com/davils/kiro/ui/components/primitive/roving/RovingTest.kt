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

package com.davils.kiro.ui.components.primitive.roving

import androidx.compose.ui.input.key.Key
import com.davils.kiro.ui.components.Orientation
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

private const val LAST: Int = 3

class RovingTest : FunSpec({
    val enabled = listOf(true, false, true, true)

    context("moves") {
        test("follow the orientation and mirror from right to left") {
            rovingMoveOf(
                key = Key.DirectionRight,
                orientation = Orientation.Horizontal,
                isReversed = false,
            ) shouldBe RovingMove.Next
            rovingMoveOf(
                key = Key.DirectionRight,
                orientation = Orientation.Horizontal,
                isReversed = true,
            ) shouldBe RovingMove.Previous
            rovingMoveOf(
                key = Key.MoveEnd,
                orientation = Orientation.Horizontal,
                isReversed = true,
            ) shouldBe RovingMove.Last
            rovingMoveOf(
                key = Key.DirectionDown,
                orientation = Orientation.Vertical,
                isReversed = false,
            ) shouldBe RovingMove.Next
            rovingMoveOf(
                key = Key.DirectionRight,
                orientation = Orientation.Vertical,
                isReversed = false,
            ).shouldBeNull()
        }
    }

    context("targets") {
        test("skip disabled items and wrap around") {
            rovingTarget(move = RovingMove.Next, current = 0, isEnabled = enabled) shouldBe 2
            rovingTarget(move = RovingMove.Next, current = 3, isEnabled = enabled) shouldBe 0
            rovingTarget(move = RovingMove.Previous, current = 0, isEnabled = enabled) shouldBe LAST
            rovingTarget(move = RovingMove.First, current = 2, isEnabled = enabled) shouldBe 0
            rovingTarget(move = RovingMove.Last, current = 0, isEnabled = enabled) shouldBe LAST
            rovingTarget(move = RovingMove.Next, current = 0, isEnabled = listOf(false)).shouldBeNull()
        }
    }
})
