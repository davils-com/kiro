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

package com.davils.kiro.ui.motion.art.tiling

import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.smoothstep
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.sqrt

private const val DIRECTION_SALT: Int = 0x5bd1e995
private const val LINE_SALT: Int = 0x27d4eb2f
private const val RESTING_SALT: Int = 0x165667b1

internal fun truchetOrientation(column: Int, row: Int, wave: Int, flip: Float, seed: Int): Int {
    val resting = pickOf(size = 2, x = column, y = row, seed = seed + RESTING_SALT)
    if (unitOf(x = column, y = row, z = wave, seed = seed) < flip) return 1 - resting
    return resting
}

internal fun truchetDirection(column: Int, row: Int, wave: Int, seed: Int): Int {
    if (pickOf(size = 2, x = column, y = row, z = wave, seed = seed + DIRECTION_SALT) == 0) return -1
    return 1
}

internal fun truchetLine(variant: TruchetVariant, column: Int, row: Int, share: Float, seed: Int): Boolean =
    when (variant) {
        TruchetVariant.Arcs -> false
        TruchetVariant.Lines -> true
        TruchetVariant.Mixed -> unitOf(x = column, y = row, seed = seed + LINE_SALT) < share
    }

internal fun accentOf(band: Int, every: Int): Int {
    if (every <= 0 || band.mod(every) != 0) return -1
    return band.floorDiv(every)
}

internal fun truchetBand(column: Int, row: Int, band: Int): Int = (column + row).floorDiv(band.coerceAtLeast(1))

internal fun pointerLead(dx: Float, dy: Float, radius: Float, lead: Float): Float {
    if (radius <= 0f) return 0f
    return lead * smoothstep(1f - sqrt(dx * dx + dy * dy) / radius)
}
