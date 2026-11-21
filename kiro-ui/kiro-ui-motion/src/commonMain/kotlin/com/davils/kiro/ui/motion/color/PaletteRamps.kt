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
import com.davils.kiro.ui.core.color.mix
import com.davils.kiro.ui.core.color.oklch

/**
 * Returns colors from [start] to [end] in even steps, mixed in the OKLCH color space so that the steps look evenly
 * spaced and keep their saturation.
 *
 * @param start the first color.
 * @param end the last color.
 * @param steps the number of colors, at least `2`.
 * @return the colors from [start] to [end].
 * @throws IllegalArgumentException if [steps] is less than `2`.
 * @since 1.0.0
 */
public fun rampOf(start: Color, end: Color, steps: Int): List<Color> {
    require(steps >= 2) { "A ramp needs at least two steps, but got $steps." }
    val last = (steps - 1).toFloat()
    return List(steps) { index -> start.mix(other = end, amount = index / last) }
}

/**
 * Returns colors at even angles around the hue circle, with one lightness and chroma.
 *
 * @param hue the hue of the first color, in degrees.
 * @param lightness the OKLCH lightness of every color, between `0` and `1`.
 * @param chroma the OKLCH chroma of every color.
 * @param count the number of colors.
 * @return the colors.
 * @since 1.0.0
 */
public fun wheelOf(hue: Double, lightness: Double, chroma: Double, count: Int): List<Color> {
    val step = FULL_TURN / count
    return List(count) { index ->
        oklch(lightness = lightness, chroma = chroma, hue = (hue + step * index) % FULL_TURN)
    }
}

private const val FULL_TURN: Double = 360.0
