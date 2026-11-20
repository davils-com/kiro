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
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cbrt
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sin

private const val DEGREES_PER_TURN: Double = 360.0
private const val HALF_TURN_DEGREES: Double = 180.0
private const val LINEAR_THRESHOLD: Double = 0.0031308
private const val ENCODED_THRESHOLD: Double = 0.04045
private const val LINEAR_SLOPE: Double = 12.92
private const val GAMMA: Double = 2.4
private const val ENCODED_SCALE: Double = 1.055
private const val ENCODED_OFFSET: Double = 0.055

/**
 * Returns the sRGB color of an OKLCH color, like the CSS function `oklch(...)`.
 *
 * @param lightness the perceived lightness, from 0 for black to 1 for white.
 * @param chroma the colorfulness, from 0 for a gray upwards.
 * @param hue the hue angle in degrees.
 * @param alpha the opacity, from 0 for transparent to 1 for opaque; values outside are clamped.
 * @return the color, with every channel that falls outside of sRGB clamped into it.
 * @since 1.0.0
 */
public fun oklch(lightness: Double, chroma: Double, hue: Double, alpha: Double = 1.0): Color =
    Oklch(lightness = lightness, chroma = chroma, hue = hue, alpha = alpha).toColor()

/**
 * Returns the sRGB color of this OKLCH color.
 *
 * @return the color, with every channel that falls outside of sRGB clamped into it.
 * @since 1.0.0
 */
public fun Oklch.toColor(): Color {
    val radians = hue * PI / HALF_TURN_DEGREES
    val a = chroma * cos(radians)
    val b = chroma * sin(radians)
    val long = (lightness + 0.3963377774 * a + 0.2158037573 * b).pow(3)
    val medium = (lightness - 0.1055613458 * a - 0.0638541728 * b).pow(3)
    val short = (lightness - 0.0894841775 * a - 1.2914855480 * b).pow(3)
    val red = 4.0767416621 * long - 3.3077115913 * medium + 0.2309699292 * short
    val green = -1.2684380046 * long + 2.6097574011 * medium - 0.3413193965 * short
    val blue = -0.0041960863 * long - 0.7034186147 * medium + 1.7076147010 * short
    return Color(
        red = encode(red).toFloat(),
        green = encode(green).toFloat(),
        blue = encode(blue).toFloat(),
        alpha = alpha.coerceIn(minimumValue = 0.0, maximumValue = 1.0).toFloat(),
    )
}

/**
 * Returns this color in the OKLCH color space.
 *
 * @return the OKLCH color, with a hue from 0 up to 360 degrees.
 * @since 1.0.0
 */
public fun Color.toOklch(): Oklch {
    val linearRed = decode(red.toDouble())
    val linearGreen = decode(green.toDouble())
    val linearBlue = decode(blue.toDouble())
    val long = cbrt(0.4122214708 * linearRed + 0.5363325363 * linearGreen + 0.0514459929 * linearBlue)
    val medium = cbrt(0.2119034982 * linearRed + 0.6806995451 * linearGreen + 0.1073969566 * linearBlue)
    val short = cbrt(0.0883024619 * linearRed + 0.2817188376 * linearGreen + 0.6299787005 * linearBlue)
    val lightness = 0.2104542553 * long + 0.7936177850 * medium - 0.0040720468 * short
    val a = 1.9779984951 * long - 2.4285922050 * medium + 0.4505937099 * short
    val b = 0.0259040371 * long + 0.7827717662 * medium - 0.8086757660 * short
    val degrees = atan2(y = b, x = a) * HALF_TURN_DEGREES / PI
    return Oklch(
        lightness = lightness,
        chroma = hypot(x = a, y = b),
        hue = (degrees + DEGREES_PER_TURN) % DEGREES_PER_TURN,
        alpha = alpha.toDouble(),
    )
}

private fun encode(linear: Double): Double {
    val clamped = linear.coerceIn(minimumValue = 0.0, maximumValue = 1.0)
    if (clamped <= LINEAR_THRESHOLD) return clamped * LINEAR_SLOPE
    return ENCODED_SCALE * clamped.pow(1 / GAMMA) - ENCODED_OFFSET
}

private fun decode(encoded: Double): Double {
    if (encoded <= ENCODED_THRESHOLD) return encoded / LINEAR_SLOPE
    return ((encoded + ENCODED_OFFSET) / ENCODED_SCALE).pow(GAMMA)
}
