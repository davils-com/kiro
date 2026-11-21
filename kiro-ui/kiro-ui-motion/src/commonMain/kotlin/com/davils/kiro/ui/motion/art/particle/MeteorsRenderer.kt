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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sin

private const val DEGREES: Float = 180f
private const val HALF: Float = 0.5f
private const val SPREAD: Float = 0.7f
private const val GRADIENT_STEPS: Int = 32
private const val LOOP: Double = 4096.0
private const val SALT_PACE: Int = 1
private const val SALT_LANE: Int = 2
private const val SALT_TONE: Int = 3
private const val SALT_STAR_X: Int = 4
private const val SALT_STAR_Y: Int = 5
private const val SALT_STAR_GLOW: Int = 6

internal class MeteorsRenderer(private val style: MeteorsArtStyle) : ArtRenderer {
    private val gradient = ColorGradient()
    private var colors: List<Color> = emptyList()

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        stars(frame = frame)
        colors = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = GRADIENT_STEPS)
        val radians = style.angle / DEGREES * PI.toFloat()
        val direction = Offset(x = cos(radians), y = sin(radians))
        val count = (style.count * frame.quality.density).roundToInt().coerceAtLeast(minimumValue = 1)
        val phase = frame.time.phase(style.period)
        for (lane in 0 until count) {
            val pace = HALF + unitOf(x = lane, y = SALT_PACE, seed = frame.seed.value)
            val shifted = phase / pace + lane.toDouble() / count
            val cycle = floor(shifted)
            val progress = flightOf(local = (shifted - cycle).toFloat(), duty = style.duty)
            if (progress >= 1f) continue
            val turn = cycle.mod(LOOP).toInt()
            meteor(frame = frame, lane = lane, cycle = turn, progress = progress, direction = direction)
        }
    }

    private fun DrawScope.stars(frame: ArtFrame) {
        val count = (style.stars * frame.quality.density).roundToInt()
        val color = style.star ?: frame.palette.ink
        val radius = style.starRadius.toPx()
        val seed = frame.seed.value
        for (index in 0 until count) {
            drawCircle(
                color = color,
                radius = radius,
                center = Offset(
                    x = unitOf(x = index, y = SALT_STAR_X, seed = seed) * size.width,
                    y = unitOf(x = index, y = SALT_STAR_Y, seed = seed) * size.height,
                ),
                alpha = style.starAlpha * unitOf(x = index, y = SALT_STAR_GLOW, seed = seed),
            )
        }
    }

    private fun DrawScope.meteor(frame: ArtFrame, lane: Int, cycle: Int, progress: Float, direction: Offset) {
        val seed = frame.seed.value
        val travel = size.maxDimension * style.travel
        val across = Offset(x = -direction.y, y = direction.x)
        val spread = (unitOf(x = lane, y = cycle, z = SALT_LANE, seed = seed) - HALF) * size.maxDimension * SPREAD
        val start = center - direction * (travel * HALF) + across * spread
        val head = start + direction * (travel * progress)
        val envelope = envelopeOf(progress = progress)
        val end = head - direction * (style.tail.toPx() * envelope)
        val left = minOf(a = head.x, b = end.x)
        val right = maxOf(a = head.x, b = end.x)
        val top = minOf(a = head.y, b = end.y)
        val bottom = maxOf(a = head.y, b = end.y)
        val isOutside = right < 0f || bottom < 0f || left > size.width || top > size.height
        if (isOutside) return
        val tone = style.toneFrom + (1f - style.toneFrom) * unitOf(x = lane, y = cycle, z = SALT_TONE, seed = seed)
        val color = colors[(tone.coerceIn(minimumValue = 0f, maximumValue = 1f) * (GRADIENT_STEPS - 1)).roundToInt()]
        val alpha = style.alpha * envelope
        drawLine(
            brush = Brush.linearGradient(
                colors = listOf(color.copy(alpha = alpha), color.copy(alpha = 0f)),
                start = head,
                end = end,
            ),
            start = head,
            end = end,
            strokeWidth = style.width.toPx(),
            cap = StrokeCap.Round,
        )
        drawCircle(color = color, radius = style.headRadius.toPx(), center = head, alpha = alpha)
    }
}
