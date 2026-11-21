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

package com.davils.kiro.ui.motion.shader

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.time.ArtTime
import kotlin.time.Duration

private const val CHANNELS: Int = 4
private const val ALPHA: Int = 3

internal fun ArtPalette.tintsOf(colors: List<Color>?): List<Color> = (colors ?: accents).ifEmpty { listOf(ink) }

internal fun UniformSink.color(name: String, color: Color) {
    val srgb = color.convert(ColorSpaces.Srgb)
    float4(name = name, x = srgb.red, y = srgb.green, z = srgb.blue, w = srgb.alpha)
}

internal fun FloatArray.putColor(index: Int, color: Color) {
    val srgb = color.convert(ColorSpaces.Srgb)
    val at = index * CHANNELS
    this[at] = srgb.red
    this[at + 1] = srgb.green
    this[at + 2] = srgb.blue
    this[at + ALPHA] = srgb.alpha
}

internal fun ArtTime.loopOf(period: Duration): Float {
    val turns = phase(period)
    return turns.mod(1.0).toFloat()
}
