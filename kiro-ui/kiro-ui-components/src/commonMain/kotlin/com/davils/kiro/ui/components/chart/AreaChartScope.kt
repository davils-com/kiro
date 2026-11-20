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
 * The scope in which the content of an [AreaChart] declares its areas.
 *
 * @since 1.0.0
 */
public interface AreaChartScope : CartesianChartScope {
    /**
     * Declares an area for the values of one series. Areas are drawn in the order they are declared.
     *
     * @param key the key of the series in the values of each [ChartPoint].
     * @param curve how the line along the top of the area runs between the points.
     * @param fillAlpha the alpha of the fill, from 0 to 1; it is not used when [isGradient] is `true`.
     * @param strokeWidth the width of the line along the top of the area.
     * @param isGradient whether the fill fades out towards the bottom instead of having one alpha.
     * @since 1.0.0
     */
    public fun area(
        key: ChartKey,
        curve: ChartCurve = ChartCurve.Natural,
        fillAlpha: Float = 0.4f,
        strokeWidth: Dp = 1.dp,
        isGradient: Boolean = false,
    )
}
