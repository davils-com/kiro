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

package com.davils.kiro.ui.motion.color

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.core.color.mix
import com.davils.kiro.ui.core.color.toOklch
import kotlin.math.abs
import kotlin.math.floor

private const val MAX_HUE_ARC: Double = 120.0
private const val FULL_TURN: Double = 360.0

internal class ColorGradient {
    private var source: List<Color> = emptyList()
    private var cached: List<Color> = emptyList()

    fun of(colors: List<Color>, steps: Int): List<Color> {
        val count = steps.coerceAtLeast(minimumValue = 1)
        if (colors == source && cached.size == count) return cached
        val last = (count - 1).coerceAtLeast(minimumValue = 1).toFloat()
        source = colors
        cached = List(count) { index -> gradientAt(colors = colors, fraction = index / last) }
        return cached
    }
}

internal fun gradientAt(colors: List<Color>, fraction: Float): Color {
    if (colors.size < 2) return colors.first()
    val position = fraction.coerceIn(minimumValue = 0f, maximumValue = 1f) * (colors.size - 1)
    val lower = floor(position).toInt()
    val index = lower.coerceAtMost(maximumValue = colors.size - 2)
    return blend(start = colors[index], end = colors[index + 1], amount = position - index)
}

internal fun blend(start: Color, end: Color, amount: Float): Color {
    val from = start.toOklch()
    val to = end.toOklch()
    val difference = abs(from.hue - to.hue)
    val arc = minOf(a = difference, b = FULL_TURN - difference)
    if (arc <= MAX_HUE_ARC) return start.mix(other = end, amount = amount)
    return lerp(start = start, stop = end, fraction = amount)
}
