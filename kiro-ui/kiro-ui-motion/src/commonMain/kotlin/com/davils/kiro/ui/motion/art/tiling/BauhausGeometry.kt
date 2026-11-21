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

private const val QUARTERS: Int = 4

internal fun weightedIndex(weights: FloatArray, unit: Float): Int {
    var total = 0f
    for (weight in weights) total += weight.coerceAtLeast(0f)
    val target = unit * total
    var before = 0f
    var chosen = -1
    for (index in weights.indices) {
        val weight = weights[index].coerceAtLeast(0f)
        if (weight > 0f && before <= target) chosen = index
        before += weight
    }
    return chosen
}

internal fun quarterOf(resting: Int, direction: Int, turns: Int): Int = (resting + direction * turns).mod(QUARTERS)
