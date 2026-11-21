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

package com.davils.kiro.ui.audio.palette

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toPixelMap
import kotlin.math.roundToInt

private const val SAMPLES: Int = 24
private const val HUES: Int = 12
private const val LEVELS: Int = 3
private const val VISIBLE: Float = 0.5f
private const val GREY: Float = 0.08f
private const val TURN: Float = 360f
private const val SECTOR: Float = 60f
private const val CONTRAST_OFFSET: Float = 0.05f

internal fun swatchesOf(cover: ImageBitmap): List<Swatch> {
    val pixels = cover.toPixelMap()
    val stepX = (cover.width / SAMPLES).coerceAtLeast(1)
    val stepY = (cover.height / SAMPLES).coerceAtLeast(1)
    val samples = (0 until cover.height step stepY).flatMap { y ->
        (0 until cover.width step stepX).map { x -> pixels[x, y] }
    }
    val visible = samples.filter { color -> color.alpha >= VISIBLE }
    return visible.groupBy(::bucketOf).values.map { group -> Swatch(color = averageOf(group), count = group.size) }
}

internal fun saturationOf(color: Color): Float {
    val channels = channelsOf(color)
    val high = channels.max()
    val low = channels.min()
    if (high <= 0f) return 0f
    return (high - low) / high
}

internal fun contentOn(backgrounds: List<Color>, content: Pair<Color, Color>): Color {
    val (light, dark) = content
    val lightContrast = backgrounds.minOf { background -> contrastOf(first = light, second = background) }
    val darkContrast = backgrounds.minOf { background -> contrastOf(first = dark, second = background) }
    return light.takeIf { lightContrast >= darkContrast } ?: dark
}

private fun contrastOf(first: Color, second: Color): Float {
    val levels = listOf(first.luminance(), second.luminance())
    val high = levels.max()
    val low = levels.min()
    return (high + CONTRAST_OFFSET) / (low + CONTRAST_OFFSET)
}

private fun bucketOf(color: Color): Int {
    val level = (color.luminance() * (LEVELS - 1)).roundToInt()
    if (saturationOf(color) < GREY) return -1 - level
    val hue = (hueOf(color) / TURN * HUES).toInt() % HUES
    return hue * LEVELS + level
}

private fun hueOf(color: Color): Float {
    val channels = channelsOf(color)
    val high = channels.max()
    val range = high - channels.min()
    if (range <= 0f) return 0f
    val sector = sectorOf(color = color, high = high, range = range)
    return (sector * SECTOR + TURN) % TURN
}

private fun sectorOf(color: Color, high: Float, range: Float): Float {
    val sectors = listOf(
        color.red to (color.green - color.blue) / range,
        color.green to (color.blue - color.red) / range + 2f,
        color.blue to (color.red - color.green) / range + 4f,
    )
    return sectors.first { (channel, _) -> channel == high }.second
}

private fun channelsOf(color: Color): List<Float> = listOf(color.red, color.green, color.blue)

private fun averageOf(colors: List<Color>): Color = Color(
    red = meanOf(colors) { color -> color.red },
    green = meanOf(colors) { color -> color.green },
    blue = meanOf(colors) { color -> color.blue },
)

private fun meanOf(colors: List<Color>, channel: (Color) -> Float): Float {
    val values = colors.map(channel)
    return values.sum() / values.size
}
