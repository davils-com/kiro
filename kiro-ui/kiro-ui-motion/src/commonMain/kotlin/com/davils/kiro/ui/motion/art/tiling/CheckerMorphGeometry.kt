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

import androidx.compose.animation.core.Easing
import kotlin.math.abs
import kotlin.math.floor

internal fun morphOf(local: Double, morph: Float, easing: Easing): Float {
    if (morph <= 0f) return 0f
    val cycle = local - floor(local)
    val rise = 1f - abs(2f * (cycle / morph).toFloat() - 1f)
    return easing.transform(rise.coerceIn(minimumValue = 0f, maximumValue = 1f))
}

internal fun isCheckerCell(column: Int, row: Int): Boolean = (column + row).mod(2) == 0

internal fun checkerBand(column: Int, row: Int, band: Int): Int =
    (column + row) / 2 / band.coerceAtLeast(minimumValue = 1)
