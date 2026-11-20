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

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The scope in which the content of a [LineChart] declares its lines.
 *
 * @since 1.0.0
 */
public interface LineChartScope : CartesianChartScope {
    /**
     * Declares a line through the values of one series.
     *
     * @param key the key of the series in the values of each [ChartPoint].
     * @param curve how the line runs between the points.
     * @param strokeWidth the width of the line.
     * @param isDotShown whether a dot is drawn on each point.
     * @since 1.0.0
     */
    public fun line(
        key: ChartKey,
        curve: ChartCurve = ChartCurve.Natural,
        strokeWidth: Dp = 2.dp,
        isDotShown: Boolean = false,
    )
}
