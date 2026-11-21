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

package com.davils.kiro.ui.motion.art.line

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.color.cyclic
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin

private const val HALF: Float = 0.5f
private const val JITTER: Float = 0.6f
private const val SHORTEST: Double = 0.1
private const val PERIOD_SALT: Int = 0x68e31da4
private const val OFFSET_SALT: Int = 0x1b56c4e9

internal class BeamsRenderer(private val style: BeamsArtStyle) : ArtRenderer {
    private val curves = ArrayList<Path>()
    private val measures = ArrayList<PathMeasure>()
    private val piece = Path()
    private var built = Size.Zero
    private var builtSeed = 0

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        val seed = frame.seed.derive(style.salt).value
        build(count = countOf(frame), seed = seed)
        if (style.guideAlpha <= 0f) return
        val stroke = Stroke(width = style.lineWidth.toPx())
        val color = style.guide ?: frame.palette.ink
        for (curve in curves) drawPath(path = curve, color = color, alpha = style.guideAlpha, style = stroke)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val seed = frame.seed.derive(style.salt).value
        build(count = countOf(frame), seed = seed)
        val colors = frame.palette.colorsOr(style.colors)
        val glow = Stroke(width = style.glowWidth.toPx(), cap = StrokeCap.Round)
        val beam = Stroke(width = style.beamWidth.toPx(), cap = StrokeCap.Round)
        for (index in curves.indices) {
            val spread = 1.0 + style.periodSpread * (unitOf(x = index, seed = seed + PERIOD_SALT) * 2.0 - 1.0)
            val phase = frame.time.phase(style.period * spread.coerceAtLeast(minimumValue = SHORTEST))
            val progress = (phase + unitOf(x = index, seed = seed + OFFSET_SALT)).mod(1.0).toFloat()
            val color = colors.cyclic(index = index, fallback = frame.palette.ink)
            drawBeam(index = index, progress = progress, color = color, strokes = glow to beam)
        }
    }

    private fun DrawScope.drawBeam(index: Int, progress: Float, color: Color, strokes: Pair<Stroke, Stroke>) {
        val measure = measures[index]
        val length = measure.length
        val window = beamWindowOf(progress = progress, segment = style.segment)
        if (window.start >= window.endInclusive) return
        piece.rewind()
        measure.getSegment(
            startDistance = length * window.start,
            stopDistance = length * window.endInclusive,
            destination = piece,
            startWithMoveTo = true,
        )
        val brush = Brush.linearGradient(
            colors = listOf(color.copy(alpha = 0f), color),
            start = measure.getPosition(distance = length * window.start),
            end = measure.getPosition(distance = length * window.endInclusive),
        )
        val fade = sin(progress * PI).toFloat()
        if (style.glowAlpha > 0f) {
            drawPath(path = piece, brush = brush, alpha = style.glowAlpha * fade, style = strokes.first)
        }
        drawPath(path = piece, brush = brush, alpha = style.beamAlpha * fade, style = strokes.second)
    }

    private fun countOf(frame: ArtFrame): Int = (style.beams * frame.quality.density).roundToInt()

    private fun DrawScope.build(count: Int, seed: Int) {
        if (built == size && builtSeed == seed && curves.size == count) return
        built = size
        builtSeed = seed
        curves.clear()
        measures.clear()
        for (index in 0 until count) {
            val jitter = (unitOf(x = index, seed = seed) - HALF) * JITTER
            val position = (index + HALF + jitter) / count
            val path = Path()
            beamCurveOf(position = position, spread = style.spread, bend = style.bend, size = size).into(path)
            val measure = PathMeasure()
            measure.setPath(path = path, forceClosed = false)
            curves.add(path)
            measures.add(measure)
        }
    }
}
