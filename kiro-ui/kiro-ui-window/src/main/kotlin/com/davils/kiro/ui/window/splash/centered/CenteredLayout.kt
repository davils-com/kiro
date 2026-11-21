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

package com.davils.kiro.ui.window.splash.centered

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.MultiContentMeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp

internal class CenteredLayout(private val padding: Dp, private val gap: Dp) : MultiContentMeasurePolicy {
    override fun MeasureScope.measure(measurables: List<List<Measurable>>, constraints: Constraints): MeasureResult {
        val width = constraints.maxWidth
        val height = constraints.maxHeight
        val inset = padding.roundToPx()
        val space = gap.roundToPx()
        val inner = Constraints(
            maxWidth = (width - inset * 2).coerceAtLeast(minimumValue = 0),
            maxHeight = (height - inset * 2).coerceAtLeast(minimumValue = 0),
        )
        val ring = measurables[RING].map { measurable -> measurable.measure(inner) }
        val details = measurables[DETAILS].map { measurable -> measurable.measure(inner) }
        val footer = measurables[FOOTER].map { measurable -> measurable.measure(inner) }
        val ringHeight = ring.tallest()
        val footerHeight = footer.tallest()
        val floor = height - inset - footerHeight - space.takeIf { footerHeight > 0 }.orNone()
        val bottom = height / 2 + ringHeight / 2 + space + details.tallest()
        val room = (height / 2 - inset - ringHeight / 2).coerceAtLeast(minimumValue = 0)
        val lift = (bottom - floor).coerceIn(minimumValue = 0, maximumValue = room)
        val middle = height / 2 - lift
        val backdrop = measurables[BACKDROP].map { measurable ->
            measurable.measure(Constraints.fixed(width = width, height = height + lift * 2))
        }
        val top = middle - ringHeight / 2
        return layout(width = width, height = height) {
            backdrop.forEach { placeable -> placeable.place(x = 0, y = -lift * 2) }
            footer.forEach { placeable ->
                placeable.place(x = (width - placeable.width) / 2, y = height - inset - placeable.height)
            }
            ring.forEach { placeable -> placeable.place(x = (width - placeable.width) / 2, y = top) }
            details.forEach { placeable ->
                placeable.place(x = (width - placeable.width) / 2, y = top + ringHeight + space)
            }
        }
    }

    private fun List<Placeable>.tallest(): Int = maxOfOrNull { placeable -> placeable.height } ?: 0

    private fun Int?.orNone(): Int = this ?: 0

    companion object {
        const val BACKDROP: Int = 0
        const val RING: Int = 1
        const val DETAILS: Int = 2
        const val FOOTER: Int = 3
    }
}
