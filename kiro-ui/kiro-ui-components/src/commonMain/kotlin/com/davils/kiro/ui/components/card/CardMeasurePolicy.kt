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

package com.davils.kiro.ui.components.card

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth

internal const val CARD_FOOTER: String = "card-footer"

internal class CardMeasurePolicy(private val spacing: Dp) : MeasurePolicy {
    override fun MeasureScope.measure(measurables: List<Measurable>, constraints: Constraints): MeasureResult {
        val gap = spacing.roundToPx()
        val loose = constraints.copy(minHeight = 0)
        val placeables = measurables.map { measurable -> measurable.measure(loose) }
        val isFooterLast = measurables.lastOrNull()?.layoutId == CARD_FOOTER
        val gaps = gap * (placeables.size - 1).coerceAtLeast(0)
        val content = placeables.sumOf { placeable -> placeable.height } + gaps
        val width = constraints.constrainWidth(placeables.maxOfOrNull { placeable -> placeable.width } ?: 0)
        val height = constraints.constrainHeight(gap + content + bottomOf(isFooterLast = isFooterLast, gap = gap))
        return layout(width, height) {
            var top = gap
            placeables.forEach { placeable ->
                placeable.placeRelative(0, top)
                top += placeable.height + gap
            }
        }
    }

    private fun bottomOf(isFooterLast: Boolean, gap: Int): Int {
        if (isFooterLast) return 0
        return gap
    }
}
