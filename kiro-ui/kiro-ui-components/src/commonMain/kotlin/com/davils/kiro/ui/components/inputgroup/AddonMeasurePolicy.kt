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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth

internal class AddonMeasurePolicy(
    private val gap: Dp,
    private val buttonOffset: Dp,
    private val align: InputGroupAlign,
) : MeasurePolicy {
    override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
        val loose = constraints.copy(minWidth = 0, minHeight = 0)
        val placeables = measurables.map { measurable -> measurable.measure(loose) }
        val spacing = gap.roundToPx()
        val shift = shiftOf(measurables, buttonOffset.roundToPx())
        val content = placeables.sumOf { placeable -> placeable.width } + spacing * maxOf(placeables.size - 1, 0)
        val width = constraints.constrainWidth(content - shift)
        val height = constraints.constrainHeight(placeables.maxOfOrNull { placeable -> placeable.height } ?: 0)
        val positions = placeables.runningFold(startOf(shift)) { x, placeable -> x + placeable.width + spacing }
        return layout(width, height) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(x = positions[index], y = (height - placeable.height) / 2)
            }
        }
    }

    private fun shiftOf(measurables: List<Measurable>, offset: Int): Int {
        val isInline = align == InputGroupAlign.InlineStart || align == InputGroupAlign.InlineEnd
        val hasButton = measurables.any { measurable -> measurable.layoutId == AddonButton }
        if (isInline && hasButton) return offset
        return 0
    }

    private fun startOf(shift: Int): Int {
        if (align == InputGroupAlign.InlineStart) return -shift
        return 0
    }
}
