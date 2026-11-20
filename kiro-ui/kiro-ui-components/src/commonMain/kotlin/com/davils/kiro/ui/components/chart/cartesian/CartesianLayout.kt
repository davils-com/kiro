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

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Density
import com.davils.kiro.ui.components.chart.ChartPoint
import com.davils.kiro.ui.components.chart.ChartStyle
import com.davils.kiro.ui.components.chart.scale.CategoryScale
import com.davils.kiro.ui.components.chart.scale.ChartTicks
import com.davils.kiro.ui.components.chart.scale.LinearScale
import com.davils.kiro.ui.components.chart.spec.CartesianSpec

internal class CartesianLayout(
    private val measurer: TextMeasurer,
    private val style: ChartStyle,
    private val density: Density,
) {
    fun of(size: Size, data: List<ChartPoint>, spec: CartesianSpec, frame: CartesianFrame): CartesianGeometry {
        val axes = spec.axes
        val spans = CartesianStacks.of(
            data = data,
            keys = spec.marks.map { mark -> mark.key },
            isStacked = frame.isStacked,
        )
        val flat = spans.flatten()
        val low = flat.minOfOrNull { span -> minOf(span.start, span.end) } ?: 0.0
        val high = flat.maxOfOrNull { span -> maxOf(span.start, span.end) } ?: 0.0
        val ticks = ChartTicks.nice(min = low, max = high, count = axes.tickCount)
        val categoryLabels = data.map { point -> measurer.measure(axes.category(point.label), style.textStyle) }
        val valueLabels = ticks.map { tick -> measurer.measure(axes.value(tick), style.textStyle) }
        val plot = plotOf(
            size = size,
            spec = spec,
            frame = frame,
            categoryWidth = widest(categoryLabels),
            valueWidth = widest(valueLabels),
        )
        val categories = categoriesOf(plot = plot, count = data.size, frame = frame)
        val values = valuesOf(plot = plot, low = ticks.first(), high = ticks.last(), isVertical = frame.isVertical)
        return CartesianGeometry(
            count = data.size,
            plot = plot,
            categories = categories,
            values = values,
            ticks = ticks,
            frame = frame,
            spans = spans,
            categoryLabels = categoryLabels.takeIf { axes.isCategoryShown }.orEmpty(),
            valueLabels = valueLabels.takeIf { axes.isValueShown }.orEmpty(),
        )
    }

    private fun plotOf(
        size: Size,
        spec: CartesianSpec,
        frame: CartesianFrame,
        categoryWidth: Float,
        valueWidth: Float,
    ): Rect = with(
        density,
    ) {
        val margin = style.margin.toPx()
        val tick = style.tickMargin.toPx()
        val sample = measurer.measure("0", style.textStyle)
        val line = sample.size.height.toFloat()
        val inset = style.pointInset.toPx().takeIf { !frame.isBand } ?: 0f
        val axes = spec.axes
        val sideAxis = (valueWidth.takeIf { frame.isVertical } ?: categoryWidth) + tick
        val bottomAxis = line + tick
        val isSideShown = axes.isValueShown.takeIf { frame.isVertical } ?: axes.isCategoryShown
        val isBottomShown = axes.isCategoryShown.takeIf { frame.isVertical } ?: axes.isValueShown
        Rect(
            left = margin + inset + sideAxis.takeIf { isSideShown }.orZero(),
            top = margin,
            right = size.width - margin - inset,
            bottom = size.height - margin - bottomAxis.takeIf { isBottomShown }.orZero(),
        )
    }

    private fun categoriesOf(plot: Rect, count: Int, frame: CartesianFrame): CategoryScale {
        if (frame.isVertical) {
            return CategoryScale(
                count = count,
                start = plot.left,
                end = plot.right,
                isBand = frame.isBand,
            )
        }
        return CategoryScale(count = count, start = plot.top, end = plot.bottom, isBand = frame.isBand)
    }

    private fun valuesOf(plot: Rect, low: Double, high: Double, isVertical: Boolean): LinearScale {
        if (isVertical) {
            return LinearScale(
                domainStart = low,
                domainEnd = high,
                rangeStart = plot.bottom,
                rangeEnd = plot.top,
            )
        }
        return LinearScale(domainStart = low, domainEnd = high, rangeStart = plot.left, rangeEnd = plot.right)
    }

    private fun widest(labels: List<TextLayoutResult>): Float =
        labels.maxOfOrNull { label -> label.size.width.toFloat() } ?: 0f

    private fun Float?.orZero(): Float = this ?: 0f
}
