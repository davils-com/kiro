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
 * The scope in which the content of a [RadarChart] declares its polygons, its grid and the labels of its spokes.
 *
 * @since 1.0.0
 */
public interface RadarChartScope : ChartScope {
    /**
     * Shows a grid behind the polygons, in the polar grid color of the [ChartStyle], with a ring at each tick of the
     * values.
     *
     * @param isCircle whether the rings are circles instead of polygons through the spokes.
     * @param isSpokeShown whether a line is drawn from the center along each spoke.
     * @since 1.0.0
     */
    public fun grid(isCircle: Boolean = false, isSpokeShown: Boolean = true)

    /**
     * Shows the label of each [ChartPoint] at the outer end of its spoke.
     *
     * @param format turns the label of a point into the text shown.
     * @since 1.0.0
     */
    public fun angleAxis(format: (String) -> String = { label -> label })

    /**
     * Declares a filled polygon through the values of one series.
     *
     * @param key the key of the series in the values of each [ChartPoint].
     * @param fillAlpha the alpha of the fill, from 0 to 1.
     * @param strokeWidth the width of the outline, or zero for none.
     * @param isDotShown whether a dot is drawn on each corner.
     * @since 1.0.0
     */
    public fun radar(key: ChartKey, fillAlpha: Float = 0.6f, strokeWidth: Dp = 0.dp, isDotShown: Boolean = false)
}
