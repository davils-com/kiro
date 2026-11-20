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

package com.davils.kiro.ui.components.resizable

import kotlin.math.abs

internal fun resized(
    sizes: List<Float>,
    handle: Int,
    delta: Float,
    limits: List<ClosedFloatingPointRange<Float>>,
): List<Float> {
    val before = (handle downTo 0).toList()
    val after = (handle + 1..sizes.lastIndex).toList()
    val growing = before.takeIf { delta >= 0f } ?: after
    val shrinking = after.takeIf { delta >= 0f } ?: before
    val room = minOf(
        growing.sumOf { index -> (limits[index].endInclusive - sizes[index]).coerceAtLeast(0f).toDouble() },
        shrinking.sumOf { index -> (sizes[index] - limits[index].start).coerceAtLeast(0f).toDouble() },
    ).toFloat()
    val amount = minOf(abs(delta), room)
    val grown = spread(
        sizes = sizes,
        order = growing,
        amount = amount,
    ) { index -> limits[index].endInclusive - sizes[index] }
    return spread(sizes = grown, order = shrinking, amount = -amount) { index -> limits[index].start - sizes[index] }
}

private fun spread(sizes: List<Float>, order: List<Int>, amount: Float, roomOf: (Int) -> Float): List<Float> {
    val updated = sizes.toMutableList()
    order.fold(amount) { left, index ->
        val taken = left.coerceIn(minOf(roomOf(index), 0f), maxOf(roomOf(index), 0f))
        updated[index] = sizes[index] + taken
        left - taken
    }
    return updated.toList()
}
