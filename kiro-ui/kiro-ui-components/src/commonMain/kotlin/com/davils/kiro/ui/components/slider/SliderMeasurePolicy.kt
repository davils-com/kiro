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

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import com.davils.kiro.ui.components.Orientation
import kotlin.math.roundToInt

internal class SliderMeasurePolicy(
    private val fractions: List<Float>,
    private val orientation: Orientation,
    private val thumbSize: Dp,
    private val minLength: Dp,
) : MeasurePolicy {
    override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
        val thumb = thumbSize.roundToPx()
        val placeables = measurables.map { measurable -> measurable.measure(Constraints.fixed(thumb, thumb)) }
        val fallback = minLength.roundToPx()
        return when (orientation) {
            Orientation.Horizontal -> {
                val length = lengthOf(max = constraints.maxWidth, min = constraints.minWidth, fallback = fallback)
                layout(width = length, height = constraints.constrainHeight(thumb)) {
                    placeables.forEachIndexed { index, placeable ->
                        placeable.placeRelative(x = offsetOf(fractions[index], length - thumb), y = 0)
                    }
                }
            }

            Orientation.Vertical -> {
                val length = lengthOf(max = constraints.maxHeight, min = constraints.minHeight, fallback = fallback)
                layout(width = constraints.constrainWidth(thumb), height = length) {
                    placeables.forEachIndexed { index, placeable ->
                        placeable.placeRelative(x = 0, y = offsetOf(1f - fractions[index], length - thumb))
                    }
                }
            }
        }
    }

    private fun lengthOf(max: Int, min: Int, fallback: Int): Int {
        if (max != Constraints.Infinity) return max
        return maxOf(a = min, b = fallback)
    }

    private fun offsetOf(fraction: Float, travel: Int): Int = (fraction * travel).roundToInt()
}
