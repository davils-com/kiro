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

package com.davils.kiro.ui.audio.meter

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

internal class MeterGeometry(
    val isVertical: Boolean,
    private val start: Float,
    private val length: Float,
    private val thickness: Float,
    slot: Float,
    private val gap: Float,
) {
    private val pitch: Float = slot + gap

    private val inset: Float = (slot - thickness) / 2f

    fun span(channel: Int, from: Float, to: Float): Rect {
        val cross = channel * pitch + inset
        val low = along(from)
        val high = along(to)
        if (isVertical) return Rect(left = cross, top = high, right = cross + thickness, bottom = low)
        return Rect(left = low, top = cross, right = high, bottom = cross + thickness)
    }

    fun bar(channel: Int): Rect = span(channel = channel, from = 0f, to = 1f)

    fun mark(fraction: Float, cross: Float): Offset {
        if (isVertical) return Offset(x = cross, y = along(fraction))
        return Offset(x = along(fraction), y = cross)
    }

    fun along(fraction: Float): Float {
        if (isVertical) return start + length * (1f - fraction)
        return start + length * fraction
    }

    fun fractionOf(distance: Float): Float = distance / length

    fun extentOf(channels: Int): Float = (pitch * channels - gap).coerceAtLeast(minimumValue = 0f)
}
