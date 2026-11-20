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

import com.davils.kiro.ui.components.chart.ChartKey
import com.davils.kiro.ui.components.chart.ChartPoint
import com.davils.kiro.ui.components.chart.valueOf

internal object CartesianStacks {
    fun of(data: List<ChartPoint>, keys: List<ChartKey>, isStacked: Boolean): List<List<ChartSpan>> {
        if (!isStacked) {
            return keys.map { key ->
                data.map { point ->
                    ChartSpan(
                        start = 0.0,
                        end = point.valueOf(key),
                    )
                }
            }
        }
        val bases = DoubleArray(data.size)
        return keys.map { key ->
            data.mapIndexed { index, point ->
                val start = bases[index]
                bases[index] = start + point.valueOf(key)
                ChartSpan(start = start, end = bases[index])
            }
        }
    }
}
