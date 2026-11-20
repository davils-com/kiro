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

package com.davils.kiro.ui.components.chart

/**
 * The scope in which the content of every chart declares its tooltip and its legend.
 *
 * @since 1.0.0
 */
public interface ChartScope {
    /**
     * Shows a tooltip with the values of a category while the pointer rests on it or while the chart has the focus,
     * where the arrow keys move from one category to the next. Without a call the chart shows no tooltip.
     *
     * @param indicator how each value is marked with the color of its series.
     * @param isLabelHidden whether the label of the category is left out.
     * @param isIndicatorHidden whether the marks are left out; an icon of a series is still shown.
     * @param format turns a value into its text, or `null` for numbers with at most two decimals.
     * @since 1.0.0
     */
    public fun tooltip(
        indicator: ChartTooltipIndicator = ChartTooltipIndicator.Dot,
        isLabelHidden: Boolean = false,
        isIndicatorHidden: Boolean = false,
        format: ((Double) -> String)? = null,
    )

    /**
     * Shows a legend with the color, or the icon, and the label of each series. Without a call the chart shows no
     * legend.
     *
     * @param position whether the legend is shown above or below the chart.
     * @param isIconHidden whether a series shows its color instead of its icon.
     * @since 1.0.0
     */
    public fun legend(position: ChartLegendPosition = ChartLegendPosition.Bottom, isIconHidden: Boolean = false)
}
