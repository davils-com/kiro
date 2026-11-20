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

import kotlin.math.roundToInt

private const val HALF: Float = 0.5f

internal class CategoryScale(
    private val count: Int,
    private val start: Float,
    private val end: Float,
    private val isBand: Boolean,
) {
    val step: Float
        get() {
            if (isBand) return (end - start) / maxOf(count, 1)
            return (end - start) / maxOf(count - 1, 1)
        }

    val bandwidth: Float
        get() = step.takeIf { isBand } ?: 0f

    fun center(index: Int): Float {
        if (isBand) return start + step * (index + HALF)
        return pointOf(index)
    }

    fun indexAt(position: Float): Int? {
        if (count == 0) return null
        val raw = (position - center(0)) / step.takeIf { value -> value != 0f }.orOne()
        return raw.roundToInt().coerceIn(0, count - 1)
    }

    private fun pointOf(index: Int): Float {
        if (count == 1) return (start + end) / 2
        return start + step * index
    }

    private fun Float?.orOne(): Float = this ?: 1f
}
