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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.floor

private const val ORDER_SALT: Int = 0x3a8f05c5

internal class PolygonsRenderer(private val style: PolygonsArtStyle) : ArtRenderer {
    private val outline = Path()
    private val trace = Path()
    private val corners = FloatArray(mostSides(least = style.minSides, most = style.maxSides) * 2)
    private var line = Stroke()
    private var pen = Stroke()
    private var builtDensity = 0f
    private var seed = 0

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        prepare(frame)
        if (style.guideAlpha <= 0f) return
        val ink = style.line ?: frame.palette.ink
        val center = centerOf()
        for (ring in 0 until style.rings) {
            val radius = radiusOf(ring)
            if (!isVisible(center = center, radius = radius)) continue
            drawCircle(color = ink, radius = radius, center = center, alpha = style.guideAlpha, style = line)
        }
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        prepare(frame)
        val center = centerOf()
        val ink = style.line ?: frame.palette.ink
        val isDotted = style.dot.toPx() > 0f && frame.quality.density >= style.dotQuality.density
        for (ring in 0 until style.rings) {
            val radius = radiusOf(ring)
            if (!isVisible(center = center, radius = radius)) continue
            val sides = cornersOf(frame = frame, ring = ring, center = center)
            outline.polygon(corners = corners, sides = sides)
            drawPath(path = outline, color = ink, alpha = style.lineAlpha, style = line)
            if (isDotted) drawDots(sides = sides, color = ink)
        }
        drawTrace(frame = frame, center = center, accent = style.accent ?: frame.palette.accent(-1))
    }

    private fun DrawScope.prepare(frame: ArtFrame) {
        seed = frame.seed.derive(style.salt).value
        if (density == builtDensity) return
        builtDensity = density
        line = Stroke(width = style.lineWidth.toPx(), join = StrokeJoin.Round)
        pen = Stroke(width = style.traceWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    }

    private fun DrawScope.centerOf(): Offset = Offset(x = size.width * style.centerX, y = size.height * style.centerY)

    private fun DrawScope.radiusOf(ring: Int): Float = style.firstRadius.toPx() + ring * style.ringSpacing.toPx()

    private fun DrawScope.isVisible(center: Offset, radius: Float): Boolean {
        if (radius <= 0f) return false
        val reach = radius + style.head.toPx()
        val isAcross = center.x + reach >= 0f && center.x - reach <= size.width
        return isAcross && center.y + reach >= 0f && center.y - reach <= size.height
    }

    private fun DrawScope.cornersOf(frame: ArtFrame, ring: Int, center: Offset): Int {
        val sides = polygonSides(ring = ring, least = style.minSides, most = style.maxSides)
        val direction = ringDirection(ring = ring, isAlternating = style.isAlternating)
        val turns = frame.time.phase(style.period * (1.0 + ring * style.slowdown))
        val start = unitOf(x = ring, seed = seed).toDouble()
        val turn = start + direction * turns
        polygonCorners(center = center, radius = radiusOf(ring), sides = sides, turn = turn, into = corners)
        return sides
    }

    private fun DrawScope.drawDots(sides: Int, color: Color) {
        for (corner in 0 until sides) {
            val position = Offset(x = corners[corner * 2], y = corners[corner * 2 + 1])
            drawCircle(color = color, radius = style.dot.toPx() / 2f, center = position, alpha = style.dotAlpha)
        }
    }

    private fun DrawScope.drawTrace(frame: ArtFrame, center: Offset, accent: Color) {
        if (style.rings <= 0) return
        val total = frame.time.phase(style.tracePeriod * style.rings) * style.rings
        val index = floor(total).toInt()
        val cycle = total - index
        val offset = pickOf(size = style.rings, x = 0, seed = seed + ORDER_SALT)
        val ring = traceRing(index = index, rings = style.rings, offset = offset)
        if (style.traceAlpha <= 0f || !isVisible(center = center, radius = radiusOf(ring))) return
        val sides = cornersOf(frame = frame, ring = ring, center = center)
        val progress = drawingOf(cycle = cycle, share = style.drawShare)
        val alpha = style.traceAlpha * fadeOf(cycle = cycle, share = style.drawShare)
        val tip = trace.polygonTrace(corners = corners, sides = sides, progress = progress)
        drawPath(path = trace, color = accent, alpha = alpha, style = pen)
        if (progress < 1f) drawCircle(color = accent, radius = style.head.toPx() / 2f, center = tip, alpha = alpha)
    }
}
