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

package com.davils.kiro.ui.components.chart.spec

import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.chart.ChartCurve
import com.davils.kiro.ui.components.chart.ChartKey

internal sealed interface CartesianMark {
    val key: ChartKey

    data class Bar(override val key: ChartKey, val radius: Dp) : CartesianMark

    data class Line(
        override val key: ChartKey,
        val curve: ChartCurve,
        val strokeWidth: Dp,
        val isDotShown: Boolean,
    ) : CartesianMark

    data class Area(
        override val key: ChartKey,
        val curve: ChartCurve,
        val fillAlpha: Float,
        val strokeWidth: Dp,
        val isGradient: Boolean,
    ) : CartesianMark
}
