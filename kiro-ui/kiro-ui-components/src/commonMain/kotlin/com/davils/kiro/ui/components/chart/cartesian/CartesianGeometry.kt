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

package com.davils.kiro.ui.components.chart.cartesian

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.text.TextLayoutResult
import com.davils.kiro.ui.components.chart.scale.CategoryScale
import com.davils.kiro.ui.components.chart.scale.LinearScale

internal class CartesianGeometry(
    val count: Int,
    val plot: Rect,
    val categories: CategoryScale,
    val values: LinearScale,
    val ticks: List<Double>,
    val frame: CartesianFrame,
    val spans: List<List<ChartSpan>>,
    val categoryLabels: List<TextLayoutResult>,
    val valueLabels: List<TextLayoutResult>,
) {
    fun indexAt(position: Offset): Int? = categories.indexAt(position.x.takeIf { frame.isVertical } ?: position.y)

    fun pointOf(index: Int, value: Double): Offset {
        val along = categories.center(index)
        val across = values.map(value)
        if (frame.isVertical) return Offset(along, across)
        return Offset(across, along)
    }

    fun anchorOf(index: Int): Offset {
        val along = categories.center(index)
        if (frame.isVertical) return Offset(along, plot.center.y)
        return Offset(plot.center.x, along)
    }
}
