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

package com.davils.kiro.ui.components.field

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight

internal class FieldRowMeasurePolicy(private val gap: Dp, private val nudge: Dp) : MeasurePolicy {
    override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
        val spacing = gap.roundToPx()
        val width = constraints.maxWidth
        val loose = Constraints(maxWidth = width, maxHeight = constraints.maxHeight)
        val flexible = measurables.filter { measurable -> measurable.layoutId is FieldSlot }
        val fixed = measurables.filterNot { measurable -> measurable.layoutId is FieldSlot }
        val fixedPlaceables = fixed.associateWith { measurable -> measurable.measure(loose) }
        val used = fixedPlaceables.values.sumOf { placeable -> placeable.width } +
            spacing *
            maxOf(
                measurables.size - 1,
                0,
            )
        val share = maxOf(width - used, 0) / maxOf(flexible.size, 1)
        val flexiblePlaceables = flexible.associateWith { measurable ->
            measurable.measure(
                loose.copy(maxWidth = share),
            )
        }
        val placeables = measurables.map { measurable ->
            fixedPlaceables[measurable] ?: flexiblePlaceables.getValue(
                measurable,
            )
        }
        val height = constraints.constrainHeight(placeables.maxOfOrNull { placeable -> placeable.height } ?: 0)
        val isTop = measurables.any { measurable -> measurable.layoutId == FieldSlot.Content }
        val positions = placeables.runningFold(0) { x, placeable -> x + placeable.width + spacing }
        val shift = nudge.roundToPx()
        return layout(width, height) {
            placeables.forEachIndexed { index, placeable ->
                val isControl = measurables[index].layoutId !is FieldSlot
                placeable.placeRelative(
                    x = positions[index],
                    y = topOf(
                        placeable = placeable,
                        height = height,
                        isTop = isTop,
                        shift = shift.takeIf { isControl } ?: 0,
                    ),
                )
            }
        }
    }

    private fun topOf(placeable: Placeable, height: Int, isTop: Boolean, shift: Int): Int {
        if (isTop) return shift
        return (height - placeable.height) / 2
    }
}
