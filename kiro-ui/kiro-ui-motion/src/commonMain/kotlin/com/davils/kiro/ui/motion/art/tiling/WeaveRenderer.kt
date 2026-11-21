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

package com.davils.kiro.ui.motion.art.tiling

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.color.cyclic
import com.davils.kiro.ui.motion.noise.lerp
import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.smoothstep
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

private const val RADIANS: Double = PI / 180.0
private const val PANEL_SALT: Int = 0x3c6ef372
private const val TINT_SALT: Int = 0x5be0cd19
private const val START_SALT: Int = 0x1f83d9ab

internal class WeaveRenderer(private val style: WeaveArtStyle) : ArtRenderer {
    private val frames = Path()
    private val lines = Path()
    private val panel = Path()
    private var lattice = WeaveLattice.Empty
    private var projections = FloatArray(0)
    private var low = 0f
    private var high = 0f
    private var frameStroke = Stroke()
    private var lineStroke = Stroke()
    private var builtSize = Size.Unspecified
    private var builtDensity = 0f
    private var accent = Color.Unspecified
    private var isGlowing = false

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        prepare()
        drawPanels(frame)
        val ink = style.line ?: frame.palette.ink
        drawPath(path = lines, color = ink, alpha = style.lineAlpha, style = lineStroke)
        drawPath(path = frames, color = ink, alpha = style.frameAlpha, style = frameStroke)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        prepare()
        val half = style.band.toPx() / 2f
        if (half <= 0f || lattice.count == 0) return
        val start = unitOf(x = 0, seed = frame.seed.derive(style.salt).value + START_SALT)
        val phase = frame.time.phase(style.period) + start
        accent = style.accent ?: frame.palette.accent(-1)
        isGlowing = style.glowAlpha > 0f && frame.quality.density >= style.glowQuality.density
        for (band in 0 until style.bands) {
            val travel = style.easing.transform((phase + band.toDouble() / style.bands).mod(1.0).toFloat())
            drawBand(position = lerp(start = low - half, end = high + half, fraction = travel), half = half)
        }
    }

    private fun DrawScope.drawBand(position: Float, half: Float) {
        val width = style.highlightWidth.toPx()
        val glow = style.glowWidth.toPx()
        val segments = lattice.segments
        for (index in 0 until lattice.count) {
            val distance = abs(projections[index] - position)
            if (distance >= half) continue
            val weight = 1f - smoothstep(distance / half)
            val start = Offset(x = segments[index * 4], y = segments[index * 4 + 1])
            val end = Offset(x = segments[index * 4 + 2], y = segments[index * 4 + 3])
            if (isGlowing) {
                drawLit(start = start, end = end, color = accent, width = glow, alpha = style.glowAlpha * weight)
            }
            drawLit(start = start, end = end, color = accent, width = width, alpha = style.highlightAlpha * weight)
        }
    }

    private fun DrawScope.drawLit(start: Offset, end: Offset, color: Color, width: Float, alpha: Float) {
        drawLine(color = color, start = start, end = end, strokeWidth = width, cap = StrokeCap.Round, alpha = alpha)
    }

    private fun DrawScope.drawPanels(frame: ArtFrame) {
        if (style.panelShare <= 0f || style.panelAlpha <= 0f) return
        val seed = frame.seed.derive(style.salt).value
        val colors = frame.palette.colorsOr(style.colors)
        val ink = frame.palette.ink
        for (index in lattice.panels.indices) {
            if (unitOf(x = index, seed = seed + PANEL_SALT) >= style.panelShare) continue
            val corners = lattice.panels[index]
            panel.rewind()
            panel.moveTo(x = corners[0], y = corners[1])
            for (corner in 1 until corners.size / 2) panel.lineTo(x = corners[corner * 2], y = corners[corner * 2 + 1])
            panel.close()
            val pick = pickOf(size = colors.size, x = index, seed = seed + TINT_SALT)
            val tint = colors.cyclic(index = pick, fallback = ink)
            drawPath(path = panel, color = tint, alpha = style.panelAlpha)
        }
    }

    private fun DrawScope.prepare() {
        if (size == builtSize && density == builtDensity) return
        builtSize = size
        builtDensity = density
        lattice = WeaveLattice.of(
            pattern = style.pattern,
            width = size.width,
            height = size.height,
            cell = style.cell.toPx(),
            arcSteps = style.arcSteps,
        )
        frameStroke = Stroke(width = style.frameWidth.toPx(), cap = StrokeCap.Round)
        lineStroke = Stroke(width = style.lineWidth.toPx(), cap = StrokeCap.Round)
        build(path = frames, from = 0, until = lattice.frames)
        build(path = lines, from = lattice.frames, until = lattice.count)
        project()
    }

    private fun build(path: Path, from: Int, until: Int) {
        path.rewind()
        val segments = lattice.segments
        for (index in from until until) {
            path.moveTo(x = segments[index * 4], y = segments[index * 4 + 1])
            path.lineTo(x = segments[index * 4 + 2], y = segments[index * 4 + 3])
        }
    }

    private fun project() {
        val radians = style.angle * RADIANS
        val dx = cos(radians).toFloat()
        val dy = sin(radians).toFloat()
        val segments = lattice.segments
        projections = FloatArray(lattice.count) { index ->
            val x = (segments[index * 4] + segments[index * 4 + 2]) / 2f
            val y = (segments[index * 4 + 1] + segments[index * 4 + 3]) / 2f
            x * dx + y * dy
        }
        low = projections.minOrNull() ?: 0f
        high = projections.maxOrNull() ?: 0f
    }
}
