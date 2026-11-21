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
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.color.cyclic
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.min

private const val FULL_TURN: Double = PI * 2.0
private const val SHIFT_SALT: Int = 0x4f1bbcdc

internal class LissajousRenderer(private val style: LissajousArtStyle) : ArtRenderer {
    private val faint = Path()
    private val drawn = Path()
    private var points = FloatArray(0)
    private var curve: Harmonograph? = null
    private var builtSeed = 0
    private var line = Stroke()
    private var builtDensity = 0f

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        if (style.guideAlpha <= 0f) return
        val ink = style.line ?: frame.palette.ink
        val width = style.lineWidth.toPx()
        val center = centerOf()
        val across = Offset(x = size.width, y = center.y)
        drawLine(color = ink, start = center.copy(x = 0f), end = across, strokeWidth = width, alpha = style.guideAlpha)
        val down = Offset(x = center.x, y = size.height)
        drawLine(color = ink, start = center.copy(y = 0f), end = down, strokeWidth = width, alpha = style.guideAlpha)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val seed = frame.seed.derive(style.salt).value
        val curve = curveOf(seed)
        val steps = (style.steps * frame.quality.density).toInt().coerceAtLeast(minimumValue = 2)
        if (points.size < steps * 2) points = FloatArray(steps * 2)
        if (density != builtDensity) {
            builtDensity = density
            line = Stroke(width = style.lineWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        }
        val colors = frame.palette.colorsOr(style.colors)
        val start = unitOf(x = 0, seed = seed + SHIFT_SALT) + frame.time.phase(style.driftPeriod)
        val drawing = frame.time.phase(style.drawPeriod)
        val count = style.curves.coerceAtLeast(minimumValue = 0)
        for (index in 0 until count) {
            curve.fill(shift = (start + index * style.spread) * FULL_TURN, steps = steps, into = points)
            place(steps = steps)
            val color = colors.cyclic(index = colors.size - 1 - index, fallback = frame.palette.ink)
            val cycle = (drawing + index.toDouble() / count).mod(1.0)
            drawCurve(steps = steps, color = color, pen = style.pen ?: color, cycle = cycle)
        }
    }

    private fun curveOf(seed: Int): Harmonograph {
        val built = curve
        if (built != null && seed == builtSeed) return built
        val least = style.minFrequency
        val most = style.maxFrequency
        val first = lissajousFrequency(index = 0, least = least, most = most, seed = seed)
        val made = Harmonograph(
            frequencyX = first,
            frequencyY = lissajousPartner(first = first, index = 0, seed = seed),
            detune = style.detune,
            twist = style.twist,
            damping = style.damping,
            turns = style.turns,
        )
        builtSeed = seed
        curve = made
        return made
    }

    private fun DrawScope.centerOf(): Offset = Offset(x = size.width * style.centerX, y = size.height * style.centerY)

    private fun DrawScope.place(steps: Int) {
        val center = centerOf()
        val reach = style.size * min(a = size.width, b = size.height)
        val reachX = reach * style.stretch
        for (step in 0 until steps) {
            points[step * 2] = center.x + points[step * 2] * reachX
            points[step * 2 + 1] = center.y + points[step * 2 + 1] * reach
        }
    }

    private fun DrawScope.drawCurve(steps: Int, color: Color, pen: Color, cycle: Double) {
        faint.polyline(points = points, count = steps)
        drawPath(path = faint, color = color, alpha = style.faintAlpha, style = line)
        val progress = drawingOf(cycle = cycle, share = style.drawShare)
        val alpha = style.lineAlpha * fadeOf(cycle = cycle, share = style.drawShare)
        val tip = drawn.partial(points = points, count = steps, progress = progress)
        drawPath(path = drawn, color = color, alpha = alpha, style = line)
        if (progress >= 1f) return
        drawCircle(color = pen, radius = style.headGlow.toPx() / 2f, center = tip, alpha = style.glowAlpha)
        drawCircle(color = pen, radius = style.head.toPx() / 2f, center = tip)
    }
}
