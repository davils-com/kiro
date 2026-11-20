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
import androidx.compose.ui.graphics.toArgb
import io.kotest.matchers.Matcher
import io.kotest.matchers.doubles.plusOrMinus
import kotlin.math.abs

private const val CHANNEL_MASK: Int = 0xFF
private const val RED_SHIFT: Int = 16
private const val GREEN_SHIFT: Int = 8
private const val HEX_RADIX: Int = 16
private const val COORDINATE_TOLERANCE: Double = 0.005
private const val HUE_TOLERANCE: Double = 1.5

internal fun hexDistance(actual: Color, expected: String): Int {
    val value = expected.removePrefix("#").toInt(radix = HEX_RADIX)
    val argb = actual.toArgb()
    val red = abs(channel(color = argb, shift = RED_SHIFT) - channel(color = value, shift = RED_SHIFT))
    val green = abs(channel(color = argb, shift = GREEN_SHIFT) - channel(color = value, shift = GREEN_SHIFT))
    val blue = abs(channel(color = argb, shift = 0) - channel(color = value, shift = 0))
    return listOf(red, green, blue).max()
}

internal fun nearCoordinate(expected: Double): Matcher<Double> = expected plusOrMinus COORDINATE_TOLERANCE

internal fun nearHue(expected: Double): Matcher<Double> = expected plusOrMinus HUE_TOLERANCE

private fun channel(color: Int, shift: Int): Int = color shr shift and CHANNEL_MASK
