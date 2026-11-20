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

package com.davils.kiro.ui.components.slider

import androidx.compose.ui.input.key.Key
import kotlin.math.abs
import kotlin.math.roundToInt

private const val PAGE_STEPS: Float = 10f
private const val CONTINUOUS_STEPS: Float = 100f

private val keyMoves: Map<Key, (Float, ClosedFloatingPointRange<Float>, Float) -> Float> = mapOf(
    Key.DirectionUp to { value, _, step -> value + step },
    Key.DirectionRight to { value, _, step -> value + step },
    Key.DirectionDown to { value, _, step -> value - step },
    Key.DirectionLeft to { value, _, step -> value - step },
    Key.PageUp to { value, _, step -> value + step * PAGE_STEPS },
    Key.PageDown to { value, _, step -> value - step * PAGE_STEPS },
    Key.MoveHome to { _, range, _ -> range.start },
    Key.MoveEnd to { _, range, _ -> range.endInclusive },
)

private val mirroredKeys: Map<Key, Key> = mapOf(
    Key.DirectionLeft to Key.DirectionRight,
    Key.DirectionRight to Key.DirectionLeft,
)

internal fun snap(value: Float, range: ClosedFloatingPointRange<Float>, step: Float): Float {
    val clamped = value.coerceIn(range)
    if (step <= 0f) return clamped
    val steps = ((clamped - range.start) / step).roundToInt()
    return (range.start + steps * step).coerceIn(range)
}

internal fun fractionOf(value: Float, range: ClosedFloatingPointRange<Float>): Float {
    val span = range.endInclusive - range.start
    if (span <= 0f) return 0f
    return ((value - range.start) / span).coerceIn(0f, 1f)
}

internal fun valueAt(fraction: Float, range: ClosedFloatingPointRange<Float>): Float =
    range.start + fraction * (range.endInclusive - range.start)

internal fun nearestIndex(values: List<Float>, value: Float): Int {
    val distances = values.map { candidate -> abs(candidate - value) }
    val closest = distances.min()
    val candidates = values.indices.filter { index -> distances[index] == closest }
    val last = candidates.last()
    if (value >= values[last]) return last
    return candidates.first()
}

internal fun moved(values: List<Float>, index: Int, value: Float, range: ClosedFloatingPointRange<Float>): List<Float> {
    val lower = values.getOrNull(index - 1) ?: range.start
    val upper = values.getOrNull(index + 1) ?: range.endInclusive
    val updated = values.toMutableList()
    updated[index] = value.coerceIn(lower, upper)
    return updated.toList()
}

internal fun keyTarget(
    key: Key,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    step: Float,
    isReversed: Boolean,
): Float? {
    val effective = mirroredKeys[key]?.takeIf { isReversed } ?: key
    return keyMoves[effective]?.invoke(value, range, keyStepOf(range = range, step = step))
}

internal fun fractionAt(position: Float, length: Float, thumb: Float): Float {
    val travel = length - thumb
    if (travel <= 0f) return 0f
    return ((position - thumb / 2) / travel).coerceIn(0f, 1f)
}

private fun keyStepOf(range: ClosedFloatingPointRange<Float>, step: Float): Float {
    if (step > 0f) return step
    return (range.endInclusive - range.start) / CONTINUOUS_STEPS
}
