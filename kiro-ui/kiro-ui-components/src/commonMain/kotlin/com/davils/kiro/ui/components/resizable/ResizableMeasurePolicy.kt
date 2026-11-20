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

import androidx.compose.runtime.State
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.Orientation
import kotlin.math.roundToInt

private const val TOTAL: Float = 100f

internal class ResizableMeasurePolicy(
    private val sizes: State<List<Float>>,
    private val orientation: Orientation,
    private val thickness: Dp,
) : MeasurePolicy {
    override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
        val width = constraints.maxWidth
        val height = constraints.maxHeight
        val handle = thickness.roundToPx()
        val isHorizontal = orientation == Orientation.Horizontal
        val length = width.takeIf { isHorizontal } ?: height
        val cross = height.takeIf { isHorizontal } ?: width
        val shown = sizes.value
        val available = maxOf(a = length - handle * (shown.size - 1), b = 0)
        val lengths = lengthsOf(sizes = shown, available = available)
        val placeables = measurables.mapIndexed { index, measurable ->
            val main = lengths.getOrNull(index / 2).takeIf { index % 2 == 0 } ?: handle
            measurable.measure(fixed(isHorizontal = isHorizontal, main = main, cross = cross))
        }
        val offsets = placeables.runningFold(0) { offset, placeable -> offset + mainOf(placeable, isHorizontal) }
        return layout(width, height) {
            placeables.forEachIndexed { index, placeable ->
                val offset = offsets[index]
                placeable.placeRelative(
                    x = offset.takeIf { isHorizontal } ?: 0,
                    y = offset.takeUnless { isHorizontal } ?: 0,
                )
            }
        }
    }

    private fun lengthsOf(sizes: List<Float>, available: Int): List<Int> {
        val ends = sizes
            .runningFold(
                0f,
            ) { total, size -> total + size }
            .map { end -> (end / TOTAL * available).roundToInt() }
        return ends.zipWithNext { start, end -> end - start }
    }

    private fun fixed(isHorizontal: Boolean, main: Int, cross: Int): Constraints {
        if (isHorizontal) return Constraints.fixed(width = main, height = cross)
        return Constraints.fixed(width = cross, height = main)
    }

    private fun mainOf(placeable: Placeable, isHorizontal: Boolean): Int {
        if (isHorizontal) return placeable.width
        return placeable.height
    }
}
