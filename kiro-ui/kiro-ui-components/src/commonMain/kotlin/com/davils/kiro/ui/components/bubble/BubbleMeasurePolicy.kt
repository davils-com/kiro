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

package com.davils.kiro.ui.components.bubble

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.constrainHeight
import com.davils.kiro.ui.components.message.MessageAlign
import kotlin.math.roundToInt

internal class BubbleMeasurePolicy(private val isEnd: Boolean, private val style: BubbleStyle) : MeasurePolicy {
    override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
        val reactions = measurables.mapNotNull { measurable ->
            (measurable.layoutId as? BubbleReactionsSlot)?.let { slot -> measurable to slot }
        }
        val flow = measurables - reactions.map { (measurable, _) -> measurable }.toSet()
        val limit =
            (constraints.maxWidth * style.maxWidthFraction).toInt().takeIf { constraints.hasBoundedWidth }
                ?: Constraints.Infinity
        val placeables = flow.map { measurable ->
            measurable.measure(
                Constraints(maxWidth = limit, maxHeight = constraints.maxHeight),
            )
        }
        val spacing = style.gap.roundToPx()
        val bubbleWidth = placeables.maxOfOrNull { placeable -> placeable.width } ?: 0
        val tops = placeables.runningFold(0) { y, placeable -> y + placeable.height + spacing }
        val bubbleHeight = maxOf(a = tops.last() - spacing, b = 0)
        val width = constraints.maxWidth.takeIf { constraints.hasBoundedWidth } ?: bubbleWidth
        val left = (width - bubbleWidth).takeIf { isEnd } ?: 0
        val badges = reactions.map { (measurable, slot) ->
            measurable.measure(
                Constraints(maxWidth = bubbleWidth),
            ) to slot
        }
        val inset = style.reactions.inset.roundToPx()
        return layout(width, constraints.constrainHeight(bubbleHeight)) {
            placeables.forEachIndexed { index, placeable ->
                val shift = (bubbleWidth - placeable.width).takeIf { isEnd } ?: 0
                placeable.placeRelative(x = left + shift, y = tops[index])
            }
            badges.forEach { (placeable, slot) ->
                placeable.placeRelative(
                    x = left + horizontalOf(slot = slot, placeable = placeable, width = bubbleWidth, inset = inset),
                    y = verticalOf(slot = slot, placeable = placeable, height = bubbleHeight),
                    zIndex = 1f,
                )
            }
        }
    }

    private fun horizontalOf(slot: BubbleReactionsSlot, placeable: Placeable, width: Int, inset: Int): Int {
        if (slot.align == MessageAlign.Start) return inset
        return width - inset - placeable.width
    }

    private fun verticalOf(slot: BubbleReactionsSlot, placeable: Placeable, height: Int): Int {
        val overlap = (placeable.height * style.reactions.overlap).roundToInt()
        if (slot.side == BubbleReactionsSide.Top) return -overlap
        return height - placeable.height + overlap
    }
}
