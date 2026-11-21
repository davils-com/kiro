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

package com.davils.kiro.ui.motion.art.field

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.fractalNoise
import com.davils.kiro.ui.motion.noise.rangeOf
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.exp
import kotlin.math.pow

private const val LOOP: Double = 4096.0
private const val ROW: Float = 0.61f
private const val NORMALIZE: Float = 0.4f
private const val LACUNARITY: Float = 2f
private const val GAIN: Float = 0.5f
private const val CENTER: Float = 0.5f
private const val HALF: Float = 0.5f
private const val KNEE: Float = 0.2f
private const val KNEE_SPAN: Float = KNEE * 4f
private const val SALT_CENTER: Int = 17

internal class RidgeRenderer(private val style: RidgeArtStyle) : ArtRenderer {
    private val line = Path()
    private val area = Path()
    private val gradient = ColorGradient()
    private var heights = FloatArray(size = 0)

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val lines = style.lines.coerceAtLeast(minimumValue = 1)
        val colors = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = lines)
        val background = style.background ?: frame.palette.background
        val step = (style.step.toPx() / frame.quality.density).coerceAtLeast(minimumValue = 1f)
        val points = ceil(size.width / step).toInt() + 2
        val shift = looped(frame.time.phase(style.period))
        val morph = looped(frame.time.phase(style.evolution))
        val stroke = Stroke(width = style.lineWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        val amplitude = style.amplitude.toPx()
        val top = size.height * style.top
        val gap = size.height * (style.bottom - style.top) / (lines - 1).coerceAtLeast(minimumValue = 1)
        for (index in 0 until lines) {
            val base = top + gap * index
            if (base - amplitude > size.height + stroke.width) continue
            trace(index = index, base = base, points = points, step = step, shift = shift, morph = morph, frame = frame)
            if (style.hasFill) drawPath(path = area, color = background)
            val color = colors[colorIndex(index = index, lines = lines)]
            drawPath(path = line, color = color, alpha = style.lineAlpha, style = stroke)
        }
    }

    private fun DrawScope.trace(
        index: Int,
        base: Float,
        points: Int,
        step: Float,
        shift: Float,
        morph: Float,
        frame: ArtFrame,
    ) {
        if (heights.size < points) heights = FloatArray(size = points)
        val seed = frame.seed.value
        val center = CENTER + style.wander * rangeOf(from = -1f, to = 1f, x = index, seed = seed + SALT_CENTER)
        val spread = style.spread.coerceAtLeast(minimumValue = Float.MIN_VALUE)
        val amplitude = style.amplitude.toPx()
        for (point in 0 until points) {
            val across = (point - 1) * step / size.width
            val distance = (across - center) / spread
            val bell = style.flat + (1f - style.flat) * exp(-distance * distance / 2f)
            val noise = fractalNoise(
                x = across * style.frequency + shift,
                y = index * ROW,
                z = morph,
                seed = seed,
                octaves = style.octaves,
                lacunarity = LACUNARITY,
                gain = GAIN,
            )
            val height = softened((noise / NORMALIZE + 1f) / 2f)
            heights[point] = base - amplitude * bell * height.pow(style.sharpness)
        }
        line.rewind()
        area.rewind()
        line.smoothThrough(points = points, step = step)
        area.smoothThrough(points = points, step = step)
        area.lineTo(x = (points - 2) * step, y = size.height + amplitude)
        area.lineTo(x = -step, y = size.height + amplitude)
        area.close()
    }

    private fun Path.smoothThrough(points: Int, step: Float) {
        moveTo(x = -step, y = heights[0])
        for (point in 1 until points - 1) {
            val x = (point - 1) * step
            quadraticTo(
                x1 = x,
                y1 = heights[point],
                x2 = x + step * HALF,
                y2 = (heights[point] + heights[point + 1]) * HALF,
            )
        }
        lineTo(x = (points - 2) * step, y = heights[points - 1])
    }

    private fun colorIndex(index: Int, lines: Int): Int {
        val last = lines - 1
        return when (style.gradient) {
            RidgeGradient.BackToFront -> index
            RidgeGradient.FrontToBack -> last - index
            RidgeGradient.Mirrored -> last - abs(2 * index - last)
        }
    }
}

private fun softened(height: Float): Float {
    val clamped = height.coerceIn(minimumValue = 0f, maximumValue = 1f + KNEE)
    val over = (clamped - (1f - KNEE)).coerceAtLeast(minimumValue = 0f)
    return clamped - over * over / KNEE_SPAN
}

private fun looped(phase: Double): Float = phase.mod(LOOP).toFloat()
