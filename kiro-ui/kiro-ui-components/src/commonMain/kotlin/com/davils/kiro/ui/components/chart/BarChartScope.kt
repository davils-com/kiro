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
 * The scope in which the content of a [BarChart] declares its bars.
 *
 * @since 1.0.0
 */
public interface BarChartScope : CartesianChartScope {
    /**
     * Declares the bars of one series. The bars of a category are placed in the order they are declared.
     *
     * @param key the key of the series in the values of each [ChartPoint].
     * @param radius the radius of the corners of each bar, limited to half its width and height.
     * @since 1.0.0
     */
    public fun bar(key: ChartKey, radius: Dp = 0.dp)
}
