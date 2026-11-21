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
import com.davils.kiro.ui.motion.noise.lerp
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

private const val TAU: Float = (PI * 2).toFloat()
private const val HALF: Float = 0.5f
private const val GRADIENT_STEPS: Int = 32
private const val SALT_X: Int = 1
private const val SALT_Y: Int = 2
private const val SALT_DEPTH: Int = 3
private const val SALT_HEADING: Int = 4
private const val SALT_PACE: Int = 5
private const val SALT_OFFSET: Int = 6

internal class ParticlesRenderer(private val style: ParticlesArtStyle) : ArtRenderer {
    private val gradient = ColorGradient()
    private var xs = FloatArray(size = 0)
    private var ys = FloatArray(size = 0)
    private var nearness = FloatArray(size = 0)

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val count = countOf(frame = frame)
        if (xs.size < count) {
            xs = FloatArray(size = count)
            ys = FloatArray(size = count)
            nearness = FloatArray(size = count)
        }
        place(frame = frame, count = count)
        if (frame.quality >= style.linkQuality && style.linkDistance.toPx() > 0f) {
            links(color = style.link ?: frame.palette.ink, count = count)
        }
        dots(frame = frame, count = count)
    }

    private fun DrawScope.countOf(frame: ArtFrame): Int {
        val distance = style.distance.toPx().coerceAtLeast(minimumValue = 1f)
        val share = size.width * size.height / (distance * distance)
        val count = (share * frame.quality.density).roundToInt()
        return count.coerceIn(minimumValue = 0, maximumValue = style.maxCount.coerceAtLeast(minimumValue = 0))
    }

    private fun DrawScope.place(frame: ArtFrame, count: Int) {
        val seed = frame.seed.value
        val parallax = style.parallax.toPx()
        val margin = style.maxRadius.toPx() + parallax
        val spanX = size.width + margin * 2
        val spanY = size.height + margin * 2
        val shift = shiftOf(pointer = frame.inputs.pointer, parallax = parallax)
        val travel = frame.time.phase(style.period) * style.drift.toPx()
        for (index in 0 until count) {
            val near = unitOf(x = index, y = SALT_DEPTH, seed = seed)
            val heading = unitOf(x = index, y = SALT_HEADING, seed = seed) * TAU
            val pace = travel * (1f + near) * HALF
            val homeX = unitOf(x = index, y = SALT_X, seed = seed) * spanX
            val homeY = unitOf(x = index, y = SALT_Y, seed = seed) * spanY
            xs[index] = wrapOf(value = homeX + cos(heading) * pace, span = spanX) - margin - shift.x * near
            ys[index] = wrapOf(value = homeY + sin(heading) * pace, span = spanY) - margin - shift.y * near
            nearness[index] = near
        }
    }

    private fun DrawScope.shiftOf(pointer: Offset?, parallax: Float): Offset {
        if (pointer == null || size.width <= 0f || size.height <= 0f) return Offset.Zero
        return Offset(
            x = (pointer.x / size.width - HALF) * 2f * parallax,
            y = (pointer.y / size.height - HALF) * 2f * parallax,
        )
    }

    private fun DrawScope.links(color: Color, count: Int) {
        val reach = style.linkDistance.toPx()
        val width = style.linkWidth.toPx()
        for (first in 0 until count) {
            for (second in first + 1 until count) {
                val dx = xs[second] - xs[first]
                val dy = ys[second] - ys[first]
                if (abs(dx) > reach || abs(dy) > reach) continue
                val closeness = (1f - sqrt(dx * dx + dy * dy) / reach).coerceAtLeast(minimumValue = 0f)
                drawLine(
                    color = color,
                    start = Offset(x = xs[first], y = ys[first]),
                    end = Offset(x = xs[second], y = ys[second]),
                    strokeWidth = width,
                    alpha = style.linkAlpha * closeness,
                )
            }
        }
    }

    private fun DrawScope.dots(frame: ArtFrame, count: Int) {
        val colors = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = GRADIENT_STEPS)
        val twinkle = frame.time.phase(style.twinklePeriod)
        val minRadius = style.minRadius.toPx()
        val maxRadius = style.maxRadius.toPx()
        val seed = frame.seed.value
        for (index in 0 until count) {
            val near = nearness[index]
            val radius = lerp(start = minRadius, end = maxRadius, fraction = near)
            val x = xs[index]
            val y = ys[index]
            val isOutside = x < -radius || y < -radius || x > size.width + radius || y > size.height + radius
            if (isOutside) continue
            val pace = HALF + unitOf(x = index, y = SALT_PACE, seed = seed)
            val glint = waveOf(phase = twinkle, rate = pace, offset = unitOf(x = index, y = SALT_OFFSET, seed = seed))
            val alpha = lerp(start = style.minAlpha, end = style.maxAlpha, fraction = near)
            drawCircle(
                color = colors[(near * (GRADIENT_STEPS - 1)).roundToInt()],
                radius = radius,
                center = Offset(x = x, y = y),
                alpha = alpha * (1f - style.twinkle * glint),
            )
        }
    }
}
