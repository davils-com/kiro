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

package com.davils.kiro.ui.motion.art.grid

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.color.cyclic
import com.davils.kiro.ui.motion.noise.pickOf
import kotlin.math.PI
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sin

private const val CYCLES: Int = 4096
private const val STOPS: Int = 10
private const val HALF: Float = 0.5f
private const val COLOR_SALT: Int = 0x6b43a9b5

internal class RetroGridRenderer(private val style: RetroGridArtStyle) : ArtRenderer {
    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        val horizon = size.height * style.horizon
        drawGlow(horizon = horizon, color = style.glow ?: frame.palette.highlight)
        val cell = style.cell.toPx()
        val lanes = retroLanesOf(halfWidth = size.width / 2f, cell = cell, fade = style.fade)
        if (lanes == 0 || horizon >= size.height) return
        val color = style.line ?: frame.palette.ink
        val stops = Array(STOPS + 1) { stop ->
            val share = stop.toFloat() / STOPS
            share to color.copy(alpha = style.lineAlpha * retroFadeOf(share = share, fade = style.fade))
        }
        val brush = Brush.verticalGradient(colorStops = stops, startY = horizon, endY = size.height)
        val vanish = Offset(x = size.width / 2f, y = horizon)
        val width = style.lineWidth.toPx()
        for (lane in -lanes..lanes) {
            val end = Offset(x = vanish.x + lane * cell, y = size.height)
            drawLine(brush = brush, start = vanish, end = end, strokeWidth = width)
        }
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val horizon = size.height * style.horizon
        val floor = size.height - horizon
        if (floor <= 0f) return
        drawRows(frame = frame, horizon = horizon, floor = floor)
        drawPulses(frame = frame, horizon = horizon, floor = floor)
    }

    private fun DrawScope.drawGlow(horizon: Float, color: Color) {
        val height = size.height * style.glowHeight
        if (height <= 0f || style.glowAlpha <= 0f) return
        val brush = Brush.verticalGradient(
            0f to color.copy(alpha = 0f),
            HALF to color.copy(alpha = style.glowAlpha),
            1f to color.copy(alpha = 0f),
            startY = horizon - height,
            endY = horizon + height,
        )
        drawRect(
            brush = brush,
            topLeft = Offset(x = 0f, y = horizon - height),
            size = Size(width = size.width, height = height * 2f),
        )
    }

    private fun DrawScope.drawRows(frame: ArtFrame, horizon: Float, floor: Float) {
        val color = style.line ?: frame.palette.ink
        val width = style.lineWidth.toPx()
        val count = (style.maxRows * frame.quality.density).roundToInt()
        val phase = frame.time.phase(style.period)
        for (row in 0 until count) {
            val share = retroShareOf(retroDepthOf(row = row, phase = phase, depth = style.depth))
            if (share > 1f) continue
            val y = horizon + floor * share
            val alpha = style.lineAlpha * retroFadeOf(share = share, fade = style.fade)
            drawLine(
                color = color,
                start = Offset(x = 0f, y = y),
                end = Offset(x = size.width, y = y),
                strokeWidth = width,
                alpha = alpha,
            )
        }
    }

    private fun DrawScope.drawPulses(frame: ArtFrame, horizon: Float, floor: Float) {
        val cell = style.cell.toPx()
        if (cell <= 0f || style.pulseAlpha <= 0f) return
        val count = (style.pulses * frame.quality.density).roundToInt()
        val lanes = ceil(size.width / 2f / cell).toInt()
        val seed = frame.seed.derive(style.salt).value
        val colors = frame.palette.colorsOr(style.colors)
        val turns = frame.time.phase(style.pulsePeriod * CYCLES) * CYCLES
        val ground = Ground(center = size.width / 2f, horizon = horizon, floor = floor, cell = cell)
        for (pulse in 0 until count) {
            val local = turns + pulse.toDouble() / count
            val cycle = floor(local).toInt()
            val lane = pickOf(size = lanes * 2 + 1, x = pulse, y = cycle, seed = seed) - lanes
            val tint = pickOf(size = colors.size, x = pulse, y = cycle, seed = seed + COLOR_SALT)
            drawPulse(
                ground = ground,
                lane = lane,
                progress = (local - cycle).toFloat(),
                color = colors.cyclic(index = tint, fallback = frame.palette.ink),
            )
        }
    }

    private fun DrawScope.drawPulse(ground: Ground, lane: Int, progress: Float, color: Color) {
        val reach = retroPulseOf(progress = progress, length = style.pulseLength)
        val tail = ground.at(lane = lane, share = reach.start)
        val head = ground.at(lane = lane, share = reach.endInclusive)
        val brush = Brush.linearGradient(colors = listOf(color.copy(alpha = 0f), color), start = tail, end = head)
        drawLine(
            brush = brush,
            start = tail,
            end = head,
            strokeWidth = style.pulseWidth.toPx(),
            cap = StrokeCap.Round,
            alpha = style.pulseAlpha * sin(progress * PI).toFloat(),
        )
    }

    private class Ground(val center: Float, val horizon: Float, val floor: Float, val cell: Float) {
        fun at(lane: Int, share: Float): Offset = Offset(x = center + lane * cell * share, y = horizon + floor * share)
    }
}
