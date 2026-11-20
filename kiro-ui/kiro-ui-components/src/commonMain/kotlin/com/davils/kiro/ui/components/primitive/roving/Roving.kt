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

private val horizontalMoves: Map<Key, RovingMove> = mapOf(
    Key.DirectionRight to RovingMove.Next,
    Key.DirectionLeft to RovingMove.Previous,
    Key.MoveHome to RovingMove.First,
    Key.MoveEnd to RovingMove.Last,
)

private val verticalMoves: Map<Key, RovingMove> = mapOf(
    Key.DirectionDown to RovingMove.Next,
    Key.DirectionUp to RovingMove.Previous,
    Key.MoveHome to RovingMove.First,
    Key.MoveEnd to RovingMove.Last,
)

private val mirroredMoves: Map<RovingMove, RovingMove> = mapOf(
    RovingMove.Next to RovingMove.Previous,
    RovingMove.Previous to RovingMove.Next,
)

internal fun rovingMoveOf(key: Key, orientation: Orientation, isReversed: Boolean): RovingMove? = when (orientation) {
    Orientation.Horizontal -> horizontalMoves[key]?.let { move ->
        mirroredMoves[move]?.takeIf { isReversed } ?: move
    }

    Orientation.Vertical -> verticalMoves[key]
}

internal fun rovingTarget(move: RovingMove, current: Int, isEnabled: List<Boolean>): Int? {
    val count = isEnabled.size
    val order = when (move) {
        RovingMove.Next -> List(count) { step -> (current + step + 1) % count }
        RovingMove.Previous -> List(count) { step -> (current - step - 1 + count * 2) % count }
        RovingMove.First -> List(count) { step -> step }
        RovingMove.Last -> List(count) { step -> count - 1 - step }
    }
    return order.firstOrNull { index -> isEnabled[index] }
}
