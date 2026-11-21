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

package com.davils.kiro.ui.motion.art.grid

import com.davils.kiro.ui.motion.noise.smoothstep
import kotlin.math.sqrt

private const val CLOUD_LOW: Float = 0.25f
private const val CLOUD_RANGE: Float = 0.5f

internal class FlickerLayout(val cells: GridCells, val square: Float)

internal fun flickerLayout(width: Float, height: Float, square: Float, pitch: Float, cap: Int): FlickerLayout {
    val cells = GridCells.of(width = width, height = height, pitch = pitch)
    val count = cells.columns.toLong() * cells.rows
    val limit = cap.coerceAtLeast(minimumValue = 1)
    if (count <= limit) return FlickerLayout(cells = cells, square = square)
    val scale = sqrt(count.toFloat() / limit)
    val coarse = GridCells.of(width = width, height = height, pitch = pitch * scale)
    return FlickerLayout(cells = coarse, square = square * scale)
}

internal fun levelOf(flicker: Float, cloud: Float, structure: Float, levels: Int): Int {
    val shaped = smoothstep((cloud - CLOUD_LOW) / CLOUD_RANGE)
    val share = structure.coerceIn(minimumValue = 0f, maximumValue = 1f)
    val value = flicker + (shaped - flicker) * share
    return (value * levels).toInt().coerceIn(minimumValue = 0, maximumValue = levels - 1)
}

internal fun alphaOf(level: Int, levels: Int, from: Float, to: Float): Float {
    if (levels <= 1) return to
    return from + (to - from) * level / (levels - 1)
}
