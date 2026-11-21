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

package com.davils.kiro.ui.motion.art.gradient

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

private const val TAU: Double = PI * 2
private const val FULL_TURN: Float = 360f
private const val HALF: Float = 0.5f
private const val LEAST: Float = 1f
private const val SALT_ORBIT: Int = 1
private const val SALT_TURN: Int = 2

internal class GradientRenderer(private val style: GradientArtStyle) : ArtRenderer {
    private val gradient = ColorGradient()
    private var source: List<Color> = emptyList()
    private var ground: Color = Color.Unspecified
    private var stops: List<Color> = emptyList()
    private var mirrored: List<Color> = emptyList()

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val steps = (style.steps * frame.quality.density).roundToInt().coerceAtLeast(minimumValue = 2)
        update(
            colors = frame.palette.colorsOr(style.colors),
            background = style.background ?: frame.palette.background,
            steps = steps,
        )
        val seed = frame.seed.value
        val orbit = TAU * (frame.time.phase(style.orbitPeriod) + unitOf(x = SALT_ORBIT, seed = seed))
        val distance = style.orbit.toPx()
        val middle = center + Offset(x = (cos(orbit) * distance).toFloat(), y = (sin(orbit) * distance).toFloat())
        val turn = (frame.time.phase(style.rotation) + unitOf(x = SALT_TURN, seed = seed)).mod(1.0).toFloat()
        when (style.kind) {
            GradientKind.Linear -> linear(middle = middle, turn = turn)
            GradientKind.Radial -> radial(middle = middle)
            GradientKind.Sweep -> sweep(middle = middle, turn = turn)
        }
    }

    private fun update(colors: List<Color>, background: Color, steps: Int) {
        val sampled = gradient.of(colors = colors, steps = steps)
        if (sampled === source && background == ground && stops.isNotEmpty()) return
        source = sampled
        ground = background
        stops = sampled.map { color -> lerp(start = background, stop = color, fraction = style.strength) }
        mirrored = stops + stops.asReversed()
    }

    private fun DrawScope.linear(middle: Offset, turn: Float) {
        val angle = TAU * turn
        val reach = sqrt(size.width * size.width + size.height * size.height) * HALF
        val direction = Offset(x = cos(angle).toFloat(), y = sin(angle).toFloat()) * reach
        drawRect(brush = Brush.linearGradient(colors = stops, start = middle - direction, end = middle + direction))
    }

    private fun DrawScope.radial(middle: Offset) {
        val radius = (size.maxDimension * style.reach).coerceAtLeast(minimumValue = LEAST)
        drawRect(brush = Brush.radialGradient(colors = stops, center = middle, radius = radius))
    }

    private fun DrawScope.sweep(middle: Offset, turn: Float) {
        val cover = (size.maxDimension + (middle - center).getDistance()) * 2f
        rotate(degrees = turn * FULL_TURN, pivot = middle) {
            drawRect(
                brush = Brush.sweepGradient(colors = mirrored, center = middle),
                topLeft = middle - Offset(x = cover, y = cover),
                size = Size(width = cover * 2f, height = cover * 2f),
            )
        }
    }
}
