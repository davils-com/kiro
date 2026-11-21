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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

private const val TAU: Float = (PI * 2).toFloat()
private const val LOOP: Double = 4096.0
private const val HALF: Float = 0.5f
private const val GRADIENT_STEPS: Int = 64
private const val SALT_OFFSET: Int = 1
private const val SALT_X: Int = 2
private const val SALT_Y: Int = 3
private const val SALT_TONE: Int = 4

internal class FlowFieldRenderer(private val style: FlowFieldArtStyle) : ArtRenderer {
    private val steps = style.steps.coerceAtLeast(minimumValue = 1)
    private val trail = style.trail.coerceIn(minimumValue = 1, maximumValue = steps)
    private val field = FlowField(kind = style.kind, octaves = style.octaves, turns = style.turns)
    private val points = FloatArray(size = (steps + 1) * 2)
    private val angles = FloatArray(size = steps)
    private val gradient = ColorGradient()
    private var colors: List<Color> = emptyList()
    private var width = 1f

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        if (size.minDimension <= 0f) return
        colors = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = GRADIENT_STEPS)
        width = style.lineWidth.toPx()
        field.scale = style.scale.toPx().coerceAtLeast(minimumValue = 1f)
        val evolution = frame.time.phase(style.evolution)
        field.depth = evolution.mod(LOOP).toFloat()
        field.seed = frame.seed.value
        val radius = style.pointerRadius.toPx()
        field.touch(pointer = frame.inputs.pointer, strength = style.pointerStrength, radius = radius)
        val life = frame.time.phase(style.life)
        val lines = (style.lines * frame.quality.density).roundToInt()
        for (index in 0 until lines) {
            line(index = index, life = life, seed = frame.seed.value)
        }
    }

    private fun DrawScope.line(index: Int, life: Double, seed: Int) {
        val shifted = life + unitOf(x = index, y = SALT_OFFSET, seed = seed)
        val cycle = floor(shifted)
        val head = ((shifted - cycle) * (steps + trail)).toFloat()
        val upper = head.coerceAtMost(maximumValue = steps.toFloat())
        val lower = (head - trail).coerceAtLeast(minimumValue = 0f)
        if (upper <= lower) return
        val turn = cycle.mod(LOOP).toInt()
        val margin = style.segment.toPx() * trail
        val start = Offset(
            x = unitOf(x = index, y = turn, z = SALT_X, seed = seed) * (size.width + margin * 2) - margin,
            y = unitOf(x = index, y = turn, z = SALT_Y, seed = seed) * (size.height + margin * 2) - margin,
        )
        val reached = ceil(upper).toInt()
        field.trace(start = start, steps = reached, segment = style.segment.toPx(), points = points, angles = angles)
        val tone = unitOf(x = index, y = turn, z = SALT_TONE, seed = seed)
        for (step in floor(lower).toInt() until reached) {
            val from = (lower - step).coerceAtLeast(minimumValue = 0f)
            val to = (upper - step).coerceAtMost(maximumValue = 1f)
            val along = (head - step - HALF) / trail
            val alpha = style.alpha * (1f - style.fade * along).coerceIn(minimumValue = 0f, maximumValue = 1f)
            segment(step = step, from = from, to = to, alpha = alpha, tone = tone)
        }
    }

    private fun DrawScope.segment(step: Int, from: Float, to: Float, alpha: Float, tone: Float) {
        val x = points[step * 2]
        val y = points[step * 2 + 1]
        val dx = points[step * 2 + 2] - x
        val dy = points[step * 2 + 3] - y
        val color = colors[colorIndex(step = step, x = x, y = y, tone = tone)]
        drawLine(
            color = color,
            start = Offset(x = x + dx * from, y = y + dy * from),
            end = Offset(x = x + dx * to, y = y + dy * to),
            strokeWidth = width,
            alpha = alpha,
        )
    }

    private fun DrawScope.colorIndex(step: Int, x: Float, y: Float, tone: Float): Int {
        val fraction = when (style.colorMode) {
            FlowColorMode.Angle -> abs((angles[step] / TAU).mod(1f) * 2f - 1f)
            FlowColorMode.Position -> (x / size.width + y / size.height) * HALF
            FlowColorMode.Line -> tone
        }
        val index = (fraction * (GRADIENT_STEPS - 1)).roundToInt()
        return index.coerceIn(minimumValue = 0, maximumValue = GRADIENT_STEPS - 1)
    }
}
