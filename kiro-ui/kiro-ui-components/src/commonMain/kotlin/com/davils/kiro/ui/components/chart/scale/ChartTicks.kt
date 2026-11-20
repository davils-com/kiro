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

package com.davils.kiro.ui.components.chart.scale

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

private val steps: List<Double> = listOf(1.0, 2.0, 2.5, 5.0, 10.0)

internal object ChartTicks {
    fun nice(min: Double, max: Double, count: Int): List<Double> {
        val low = minOf(min, 0.0)
        val high = maxOf(max, 0.0)
        if (high - low <= 0.0) return listOf(0.0, 1.0)
        val intervals = maxOf(count - 1, 1)
        val rough = (high - low) / intervals
        val magnitude = 10.0.pow(floor(log10(rough)))
        val step = steps.first { candidate -> candidate * magnitude >= rough } * magnitude
        val first = floor(low / step) * step
        val last = ceil(high / step) * step
        val total = ((last - first) / step).toInt()
        return (0..total).map { index -> first + index * step }
    }
}
