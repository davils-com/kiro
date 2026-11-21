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
import com.davils.kiro.ui.motion.art.dashesOf
import com.davils.kiro.ui.motion.color.cyclic
import com.davils.kiro.ui.motion.noise.lerp
import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val TURN: Double = 360.0
private const val RADIANS: Double = PI / 180.0
private const val PLANET_SALT: Int = 0x632be59b
private const val SIZE_SALT: Int = 0x05ebca6b
private const val COLOR_SALT: Int = 0x4cf5ad43
private const val MOON_SALT: Int = 0x2127599b
private const val DETAIL_SALT: Int = 0x1d8e4e27

internal class OrbitRenderer(private val style: OrbitArtStyle) : ArtRenderer {
    private var scale = -1f
    private var dashes = Stroke()
    private var line = Stroke()
    private var seed = 0
    private var first = 0f
    private var colors: List<Color> = emptyList()
    private var ink = Color.Unspecified
    private var spot = Color.Unspecified

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        val radius = style.glowRadius.toPx()
        if (radius <= 0f || style.glowAlpha <= 0f) return
        val glow = style.glow ?: frame.palette.accent(0)
        val center = centerOf()
        val brush = Brush.radialGradient(
            0f to glow.copy(alpha = style.glowAlpha),
            1f to glow.copy(alpha = 0f),
            center = center,
            radius = radius,
        )
        drawCircle(brush = brush, radius = radius, center = center)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        prepare(frame)
        val center = centerOf()
        val spacing = style.ringSpacing.toPx()
        for (ring in 0 until style.rings) {
            val radius = first + ring * spacing
            if (radius <= 0f || !reaches(center = center, radius = radius)) continue
            val direction = ringDirection(ring = ring, isAlternating = style.isAlternating)
            val drift = frame.time.phase(keplerPeriod(style.drift, radius = radius, firstRadius = first))
            rotate(degrees = (direction * drift * TURN).toFloat(), pivot = center) {
                drawCircle(color = ink, radius = radius, center = center, alpha = style.ringAlpha, style = dashes)
            }
        }
        for (ring in 0 until style.rings) {
            val radius = first + ring * spacing
            if (radius <= 0f || !reaches(center = center, radius = radius)) continue
            drawPlanets(frame = frame, ring = ring, orbit = Body(center = center, radius = radius, degrees = 0.0))
        }
    }

    private fun DrawScope.prepare(frame: ArtFrame) {
        seed = frame.seed.derive(style.salt).value
        first = style.firstRadius.toPx()
        colors = style.colors ?: frame.palette.accents
        ink = style.ring ?: frame.palette.ink
        spot = style.highlight ?: frame.palette.highlight
        if (scale == density) return
        scale = density
        val effect = dashesOf(on = style.dash.toPx(), off = style.dashGap.toPx())
        dashes = Stroke(width = style.ringWidth.toPx(), pathEffect = effect)
        line = Stroke(width = style.ringWidth.toPx())
    }

    private fun DrawScope.centerOf(): Offset = Offset(x = size.width * style.centerX, y = size.height * style.centerY)

    private fun DrawScope.reaches(center: Offset, radius: Float): Boolean {
        val reach = radius + style.maxPlanet.toPx() + style.moonDistance.toPx() + style.moon.toPx()
        val isAcross = center.x + reach >= 0f && center.x - reach <= size.width
        return isAcross && center.y + reach >= 0f && center.y - reach <= size.height
    }

    private fun DrawScope.drawPlanets(frame: ArtFrame, ring: Int, orbit: Body) {
        val count = planetCount(ring = ring, least = style.minPlanets, most = style.maxPlanets, seed = seed)
        val direction = ringDirection(ring = ring, isAlternating = style.isAlternating)
        val turns = frame.time.phase(keplerPeriod(style.period, radius = orbit.radius, firstRadius = first))
        for (planet in 0 until count) {
            if (planet > 0 && unitOf(x = ring, y = planet, seed = seed + DETAIL_SALT) > frame.quality.density) continue
            val offset = (planet + unitOf(x = ring, y = planet, seed = seed + PLANET_SALT) / 2f) / count
            val degrees = ((offset + direction * turns) * TURN).mod(TURN)
            val body = Body(center = orbit.center, radius = orbit.radius, degrees = degrees, direction = direction)
            val share = unitOf(x = ring, y = planet, seed = seed + SIZE_SALT)
            val diameter = lerp(start = style.minPlanet.toPx(), end = style.maxPlanet.toPx(), fraction = share)
            val color = pickOf(size = colors.size.coerceAtLeast(1), x = ring, y = planet, seed = seed + COLOR_SALT)
            val tint = colors.cyclic(index = color, fallback = ink)
            drawTrail(body = body, diameter = diameter, color = tint, density = frame.quality.density)
            val position = body.at(body.degrees)
            drawCircle(color = tint, radius = diameter / 2f, center = position)
            drawShine(position = position, radius = diameter / 2f)
            if (unitOf(x = ring, y = planet, seed = seed + MOON_SALT) < style.moonChance) {
                val distance = diameter / 2f + style.moonDistance.toPx()
                val moon = Body(center = position, radius = distance, degrees = offset * TURN)
                val moonColor = colors.cyclic(index = color + 2, fallback = ink)
                drawMoon(frame = frame, moon = moon, direction = direction, color = moonColor)
            }
        }
    }

    private fun DrawScope.drawMoon(frame: ArtFrame, moon: Body, direction: Int, color: Color) {
        val angle = frame.time.phase(style.moonPeriod) * direction * TURN + moon.degrees
        drawCircle(color = ink, radius = moon.radius, center = moon.center, alpha = style.ringAlpha, style = line)
        drawCircle(color = color, radius = style.moon.toPx() / 2f, center = moon.at(angle))
    }

    private fun DrawScope.drawTrail(body: Body, diameter: Float, color: Color, density: Float) {
        if (style.trail <= 0f || style.trailAlpha <= 0f) return
        val segments = (style.trailSegments * density).toInt().coerceAtLeast(1)
        val piece = style.trail / segments
        val topLeft = Offset(x = body.center.x - body.radius, y = body.center.y - body.radius)
        val size = Size(width = body.radius * 2f, height = body.radius * 2f)
        for (segment in 0 until segments) {
            val fade = 1f - segment.toFloat() / segments
            drawArc(
                color = color,
                startAngle = body.degrees.toFloat() - body.direction * piece * (segment + 1),
                sweepAngle = piece * body.direction,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                alpha = style.trailAlpha * fade * fade,
                style = Stroke(width = diameter * fade, cap = StrokeCap.Butt),
            )
        }
    }

    private fun DrawScope.drawShine(position: Offset, radius: Float) {
        if (style.shine <= 0f || style.shineSize <= 0f) return
        val light = radius * style.shineSize
        val shift = (radius - light) / 2f
        val center = Offset(x = position.x - shift, y = position.y - shift)
        drawCircle(color = spot, radius = light, center = center, alpha = style.shine)
    }

    private class Body(val center: Offset, val radius: Float, val degrees: Double, val direction: Int = 1) {
        fun at(angle: Double): Offset {
            val radians = angle * RADIANS
            val x = center.x + (radius * cos(radians)).toFloat()
            val y = center.y + (radius * sin(radians)).toFloat()
            return Offset(x = x, y = y)
        }
    }
}
