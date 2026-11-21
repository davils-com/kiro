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

package com.davils.kiro.ui.motion.art.geometry

import kotlin.math.ceil
import kotlin.math.floor

internal fun firstLine(origin: Float, cell: Float): Int {
    if (cell <= 0f) return 0
    return ceil(-origin / cell).toInt()
}

internal fun lastLine(origin: Float, extent: Float, cell: Float): Int {
    if (cell <= 0f) return -1
    return floor((extent - origin) / cell).toInt()
}

internal fun isMajorLine(index: Int, major: Int): Boolean = major > 0 && index.mod(major) == 0

internal fun scanWeight(distance: Float, band: Float, reach: Float): Float {
    if (distance < -reach || distance > band) return 0f
    val share = distance.coerceAtLeast(minimumValue = 0f) / band.coerceAtLeast(minimumValue = Float.MIN_VALUE)
    return 1f - share.coerceAtMost(maximumValue = 1f)
}
