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

package com.davils.kiro.ui.motion.mask

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

private const val DEGREES_PER_HALF_TURN: Float = 180f
private const val MINIMUM_RADIUS: Float = 0.01f

internal fun brushOf(mask: Mask, size: Size): Brush? = when (mask) {
    Mask.None -> null
    is Mask.Linear -> stopsBrush(mask.stops) { stops -> linearBrush(angle = mask.angle, size = size, stops = stops) }
    is Mask.Radial -> stopsBrush(mask.stops) { stops -> radialBrush(mask = mask, size = size, stops = stops) }
    is Mask.Vignette -> vignetteBrush(mask = mask, size = size)
}

private fun stopsBrush(stops: List<MaskStop>, gradient: (Array<Pair<Float, Color>>) -> Brush): Brush? {
    val colorStops = stops.sortedBy { stop -> stop.position }.map(::colorStopOf)
    if (colorStops.size < 2) return colorStops.firstOrNull()?.let { (_, color) -> SolidColor(color) }
    return gradient(colorStops.toTypedArray())
}

private fun colorStopOf(stop: MaskStop): Pair<Float, Color> =
    stop.position.coerceIn(minimumValue = 0f, maximumValue = 1f) to shown(stop.alpha)

private fun shown(alpha: Float): Color = Color.Black.copy(alpha = alpha.coerceIn(minimumValue = 0f, maximumValue = 1f))

private fun linearBrush(angle: Float, size: Size, stops: Array<Pair<Float, Color>>): Brush {
    val radians = angle / DEGREES_PER_HALF_TURN * PI.toFloat()
    val direction = Offset(x = cos(radians), y = sin(radians))
    val reach = abs(size.width / 2f * direction.x) + abs(size.height / 2f * direction.y)
    val center = Offset(x = size.width / 2f, y = size.height / 2f)
    return Brush.linearGradient(
        colorStops = stops,
        start = center - direction * reach,
        end = center + direction * reach,
    )
}

private fun radialBrush(mask: Mask.Radial, size: Size, stops: Array<Pair<Float, Color>>): Brush = Brush.radialGradient(
    colorStops = stops,
    center = Offset(x = mask.center.x * size.width, y = mask.center.y * size.height),
    radius = (mask.radius * size.maxDimension).coerceAtLeast(minimumValue = MINIMUM_RADIUS),
)

private fun vignetteBrush(mask: Mask.Vignette, size: Size): Brush {
    val extent = mask.extent.coerceIn(minimumValue = 0f, maximumValue = 1f)
    return Brush.radialGradient(
        0f to shown(alpha = 1f),
        extent to shown(alpha = 1f),
        1f to shown(alpha = 1f - mask.strength),
        center = Offset(x = size.width / 2f, y = size.height / 2f),
        radius = (hypot(x = size.width, y = size.height) / 2f).coerceAtLeast(minimumValue = MINIMUM_RADIUS),
    )
}
