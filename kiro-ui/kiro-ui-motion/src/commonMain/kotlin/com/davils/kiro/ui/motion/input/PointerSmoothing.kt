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

package com.davils.kiro.ui.motion.input

import androidx.compose.ui.geometry.Offset
import kotlin.math.pow

private const val REFERENCE_FRAME_NANOS: Double = 1_000_000_000.0 / 60.0
private const val NANOS_PER_SECOND: Float = 1_000_000_000f

internal const val SETTLED_DISTANCE: Float = 0.25f

internal fun smoothingShare(smoothing: Float, deltaNanos: Long): Float {
    val share = smoothing.coerceIn(minimumValue = 0f, maximumValue = 1f).toDouble()
    val frames = deltaNanos.coerceAtLeast(minimumValue = 0L) / REFERENCE_FRAME_NANOS
    return (1.0 - (1.0 - share).pow(frames)).toFloat()
}

internal fun approach(current: Offset, target: Offset, share: Float): Offset {
    val next = current + (target - current) * share
    if ((target - next).getDistance() < SETTLED_DISTANCE) return target
    return next
}

internal fun velocityOf(from: Offset, to: Offset, deltaNanos: Long): Offset {
    if (deltaNanos <= 0L) return Offset.Zero
    return (to - from) * (NANOS_PER_SECOND / deltaNanos)
}
