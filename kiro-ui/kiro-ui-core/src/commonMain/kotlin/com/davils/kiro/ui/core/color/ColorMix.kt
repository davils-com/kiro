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

package com.davils.kiro.ui.core.color

import androidx.compose.ui.graphics.Color
import kotlin.math.abs
import kotlin.math.sign

private const val ACHROMATIC_CHROMA: Double = 0.0001
private const val FULL_TURN: Double = 360.0
private const val HALF_TURN: Double = 180.0

/**
 * Mixes this color with another in the OKLCH color space, like the CSS function `color-mix(in oklch, ...)`.
 *
 * The lightness and chroma are mixed with premultiplied alpha, the hue along the shorter arc of the hue circle. A
 * gray has no hue of its own and takes the hue of the other color.
 *
 * @param other the color to mix in.
 * @param amount how much of [other] to mix in, from 0 for none to 1 for only [other]; values outside are clamped.
 * @return the mixed color.
 * @since 1.0.0
 */
public fun Color.mix(other: Color, amount: Float): Color {
    val weight = amount.toDouble().coerceIn(minimumValue = 0.0, maximumValue = 1.0)
    val start = toOklch()
    val end = other.toOklch()
    val alpha = start.alpha * (1 - weight) + end.alpha * weight
    if (alpha == 0.0) return Color.Transparent
    val lightness = (start.lightness * start.alpha * (1 - weight) + end.lightness * end.alpha * weight) / alpha
    val chroma = (start.chroma * start.alpha * (1 - weight) + end.chroma * end.alpha * weight) / alpha
    val hue = interpolateHue(start = start, end = end, weight = weight)
    return Oklch(lightness = lightness, chroma = chroma, hue = hue, alpha = alpha).toColor()
}

private fun interpolateHue(start: Oklch, end: Oklch, weight: Double): Double {
    val startHue = when (start.chroma < ACHROMATIC_CHROMA) {
        true -> end.hue
        false -> start.hue
    }
    val endHue = when (end.chroma < ACHROMATIC_CHROMA) {
        true -> startHue
        false -> end.hue
    }
    val difference = endHue - startHue
    val hue = startHue + (difference + shortestArcCorrection(difference)) * weight
    return (hue + FULL_TURN) % FULL_TURN
}

private fun shortestArcCorrection(difference: Double): Double {
    if (abs(difference) <= HALF_TURN) return 0.0
    return -sign(difference) * FULL_TURN
}
