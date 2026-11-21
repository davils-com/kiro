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

package com.davils.kiro.ui.motion.art.geometry

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.cyclic
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

private const val TURN: Double = 360.0
private const val QUARTER: Double = 90.0
private const val RADIANS: Double = PI / 180.0

internal class RadarRenderer(private val style: RadarArtStyle) : ArtRenderer {
    private var line = Stroke()
    private var builtDensity = 0f
    private var wedge: Brush = Brush.sweepGradient(colors = listOf(Color.Transparent, Color.Transparent))
    private var wedgeCenter = Offset.Unspecified
    private var wedgeColor = Color.Unspecified

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        prepare()
        val center = centerOf()
        val radius = radiusOf()
        val ink = style.line ?: frame.palette.ink
        val rings = style.rings.coerceAtLeast(minimumValue = 1)
        for (ring in 1..rings) {
            val ringRadius = radius * ring / rings
            drawCircle(color = ink, radius = ringRadius, center = center, alpha = style.ringAlpha, style = line)
        }
        val reach = radius * style.crossReach
        drawHair(color = ink, start = center.copy(x = center.x - reach), end = center.copy(x = center.x + reach))
        drawHair(color = ink, start = center.copy(y = center.y - reach), end = center.copy(y = center.y + reach))
        for (tick in 0 until style.ticks) {
            val length = tickLength(
                index = tick,
                majorEvery = style.majorEvery,
                tick = style.tick.toPx(),
                majorTick = style.majorTick.toPx(),
            )
            val degrees = tick * TURN / style.ticks
            drawLine(
                color = ink,
                start = pointAt(center = center, distance = radius - length, degrees = degrees),
                end = pointAt(center = center, distance = radius, degrees = degrees),
                strokeWidth = line.width,
                alpha = style.tickAlpha,
            )
        }
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        prepare()
        val center = centerOf()
        val radius = radiusOf()
        val accent = style.accent ?: frame.palette.accent(-1)
        val phase = frame.time.phase(style.period)
        val degrees = phase * TURN - QUARTER
        if (style.wedge > 0f && style.wedgeAlpha > 0f) {
            drawWedge(center = center, radius = radius, accent = accent, degrees = degrees)
        }
        drawLine(
            color = accent,
            start = center,
            end = pointAt(center = center, distance = radius, degrees = degrees),
            strokeWidth = line.width,
            cap = StrokeCap.Round,
            alpha = style.beamAlpha,
        )
        drawBlips(frame = frame, center = center, radius = radius, phase = phase, accent = accent)
    }

    private fun DrawScope.prepare() {
        if (density == builtDensity) return
        builtDensity = density
        line = Stroke(width = style.lineWidth.toPx())
    }

    private fun DrawScope.centerOf(): Offset = Offset(x = size.width * style.centerX, y = size.height * style.centerY)

    private fun DrawScope.radiusOf(): Float = style.size * min(a = size.width, b = size.height)

    private fun DrawScope.drawHair(color: Color, start: Offset, end: Offset) {
        drawLine(color = color, start = start, end = end, strokeWidth = line.width, alpha = style.crossAlpha)
    }

    private fun DrawScope.drawWedge(center: Offset, radius: Float, accent: Color, degrees: Double) {
        if (center != wedgeCenter || accent != wedgeColor) {
            wedgeCenter = center
            wedgeColor = accent
            wedge = Brush.sweepGradient(
                0f to accent.copy(alpha = 0f),
                1f - style.wedge / TURN.toFloat() to accent.copy(alpha = 0f),
                1f to accent.copy(alpha = style.wedgeAlpha),
                center = center,
            )
        }
        rotate(degrees = degrees.toFloat(), pivot = center) {
            drawArc(
                brush = wedge,
                startAngle = -style.wedge,
                sweepAngle = style.wedge,
                useCenter = true,
                topLeft = Offset(x = center.x - radius, y = center.y - radius),
                size = Size(width = radius * 2f, height = radius * 2f),
            )
        }
    }

    private fun DrawScope.drawBlips(frame: ArtFrame, center: Offset, radius: Float, phase: Double, accent: Color) {
        if (style.fade <= 0f) return
        val seed = frame.seed.derive(style.salt).value
        val revolution = revolutionOf(time = frame.time, period = style.period)
        val count = (style.blips * frame.quality.density).roundToInt()
        val isHaloed = style.haloAlpha > 0f && frame.quality.density >= style.haloQuality.density
        val colors = style.colors.orEmpty()
        for (index in 0 until count) {
            val age = blipAge(index = index, revolution = revolution, phase = phase, seed = seed) / style.fade
            if (age >= 1f) continue
            val found = blipRevolution(index = index, revolution = revolution, phase = phase, seed = seed)
            val distance = blipDistance(index = index, revolution = found, seed = seed, inner = style.inner) * radius
            val degrees = blipAngle(index = index, revolution = found, seed = seed) * TURN - QUARTER
            val position = pointAt(center = center, distance = distance, degrees = degrees)
            val color = colors.cyclic(index = index, fallback = accent)
            val life = 1f - age
            val dot = style.blip.toPx() / 2f
            drawCircle(color = color, radius = dot, center = position, alpha = style.blipAlpha * life)
            if (isHaloed) drawHalo(color = color, position = position, life = life)
        }
    }

    private fun DrawScope.drawHalo(color: Color, position: Offset, life: Float) {
        val dot = style.blip.toPx() / 2f
        val grown = dot + (style.halo.toPx() - dot) * (1f - life * life)
        drawCircle(color = color, radius = grown, center = position, alpha = style.haloAlpha * life, style = line)
    }

    private fun pointAt(center: Offset, distance: Float, degrees: Double): Offset {
        val radians = degrees * RADIANS
        val x = center.x + (distance * cos(radians)).toFloat()
        return Offset(x = x, y = center.y + (distance * sin(radians)).toFloat())
    }
}
