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

package com.davils.kiro.ui.motion.power

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val TOLERANCE: Double = 0.125
private const val UNSET: Long = Long.MIN_VALUE

internal const val SKIPPED_FRAME: Long = -1L

internal class FrameGate(maxFrameRate: Int?) {
    private val interval: Long = (frameIntervalOf(maxFrameRate).inWholeNanoseconds * (1.0 - TOLERANCE)).toLong()
    private var last: Long = UNSET

    fun admit(nanos: Long): Long {
        val previous = last
        if (previous != UNSET && nanos - previous < interval) return SKIPPED_FRAME
        last = nanos
        return deltaOf(previous = previous, nanos = nanos)
    }
}

internal fun frameIntervalOf(maxFrameRate: Int?): Duration {
    val rate = maxFrameRate?.takeIf { value -> value > 0 } ?: return Duration.ZERO
    return 1.seconds / rate
}

private fun deltaOf(previous: Long, nanos: Long): Long {
    if (previous == UNSET) return 0L
    return nanos - previous
}
