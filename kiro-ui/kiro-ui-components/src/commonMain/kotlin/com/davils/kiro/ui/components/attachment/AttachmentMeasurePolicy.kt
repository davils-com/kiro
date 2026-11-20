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

package com.davils.kiro.ui.components.attachment

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import com.davils.kiro.ui.components.Orientation

internal class AttachmentMeasurePolicy(
    private val orientation: Orientation,
    private val gap: Dp,
    private val inset: Dp,
) : MeasurePolicy {
    override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult =
        when (orientation) {
            Orientation.Horizontal -> horizontal(measurables = measurables, constraints = constraints)
            Orientation.Vertical -> vertical(measurables = measurables, constraints = constraints)
        }

    private fun MeasureScope.horizontal(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
        val spacing = gap.roundToPx()
        val loose = Constraints(maxWidth = constraints.maxWidth, maxHeight = constraints.maxHeight)
        val fixed = measurables.filterNot { measurable -> measurable.layoutId == AttachmentSlot.Content }
        val fixedPlaceables = fixed.associateWith { measurable -> measurable.measure(loose) }
        val used = fixedPlaceables.values.sumOf { placeable -> placeable.width } +
            spacing *
            maxOf(
                measurables.size - 1,
                0,
            )
        val remaining = maxOf(
            constraints.maxWidth - used,
            0,
        ).takeIf { constraints.hasBoundedWidth } ?: Constraints.Infinity
        val placeables = measurables.map { measurable ->
            fixedPlaceables[measurable] ?: measurable.measure(loose.copy(maxWidth = remaining))
        }
        val content = placeables.sumOf { placeable -> placeable.width } + spacing * maxOf(placeables.size - 1, 0)
        val width = constraints.constrainWidth(maxOf(content, constraints.minWidth))
        val height = constraints.constrainHeight(placeables.maxOfOrNull { placeable -> placeable.height } ?: 0)
        val starts = placeables.runningFold(0) { x, placeable -> x + placeable.width + spacing }
        return layout(width, height) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(x = starts[index], y = (height - placeable.height) / 2)
            }
        }
    }

    private fun MeasureScope.vertical(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
        val spacing = gap.roundToPx()
        val width = constraints.maxWidth.takeIf { constraints.hasBoundedWidth } ?: constraints.minWidth
        val overlay = measurables.filter { measurable -> measurable.layoutId == AttachmentSlot.Actions }
        val flow = measurables - overlay.toSet()
        val flowPlaceables = flow.map { measurable ->
            measurable.measure(
                Constraints(minWidth = width, maxWidth = width),
            )
        }
        val overlayPlaceables = overlay.map { measurable -> measurable.measure(Constraints(maxWidth = width)) }
        val tops = flowPlaceables.runningFold(0) { y, placeable -> y + placeable.height + spacing }
        val height = constraints.constrainHeight(maxOf(tops.last() - spacing, 0))
        val margin = inset.roundToPx()
        return layout(width, height) {
            flowPlaceables.forEachIndexed { index, placeable -> placeable.placeRelative(x = 0, y = tops[index]) }
            overlayPlaceables.forEach { placeable ->
                placeable.placeRelative(
                    x = width - placeable.width - margin,
                    y = margin,
                )
            }
        }
    }
}
