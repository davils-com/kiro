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

package com.davils.kiro.ui.motion.art.field

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.warpedNoise
import kotlin.math.ceil
import kotlin.math.sqrt

private const val LOOP: Double = 4096.0
private const val HALF: Float = 0.5f

internal class ContourRenderer(private val style: ContourArtStyle) : ArtRenderer {
    private val path = Path()
    private val sink = ContourPathSink(path = path)
    private val gradient = ColorGradient()
    private var field = FloatArray(size = 0)

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val cell = (style.cell.toPx() / sqrt(frame.quality.density)).coerceAtLeast(minimumValue = 1f)
        val columns = ceil(size.width / cell).toInt() + 1
        val rows = ceil(size.height / cell).toInt() + 1
        if (field.size != columns * rows) field = FloatArray(size = columns * rows)
        sample(frame = frame, columns = columns, rows = rows, cell = cell)
        val levels = style.levels.coerceAtLeast(minimumValue = 1)
        val colors = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = levels)
        val thin = Stroke(width = style.lineWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        val thick = Stroke(width = style.indexWidth.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        sink.scale = cell
        val ink = style.ink ?: frame.palette.ink
        for (level in 0 until levels) {
            val threshold = thresholdOf(level = level, levels = levels)
            if (style.bandAlpha > 0f) {
                band(columns = columns, rows = rows, level = level, levels = levels, color = colors[level])
            }
            path.rewind()
            path.fillType = PathFillType.NonZero
            contourLines(field = field, columns = columns, rows = rows, level = threshold, sink = sink)
            val isIndex = style.indexEvery > 0 && (level + 1) % style.indexEvery == 0
            val stroke = thick.takeIf { isIndex } ?: thin
            val alpha = style.indexAlpha.takeIf { isIndex } ?: style.lineAlpha
            val color = lerp(start = ink, stop = colors[level], fraction = style.lineTint)
            drawPath(path = path, color = color, alpha = alpha, style = stroke)
        }
    }

    private fun DrawScope.band(columns: Int, rows: Int, level: Int, levels: Int, color: Color) {
        path.rewind()
        path.fillType = PathFillType.EvenOdd
        val lower = thresholdOf(level = level, levels = levels)
        contourBands(field = field, columns = columns, rows = rows, level = lower, sink = sink)
        if (level + 1 < levels) {
            val upper = thresholdOf(level = level + 1, levels = levels)
            contourBands(field = field, columns = columns, rows = rows, level = upper, sink = sink)
        }
        drawPath(path = path, color = color, alpha = style.bandAlpha)
    }

    private fun DrawScope.sample(frame: ArtFrame, columns: Int, rows: Int, cell: Float) {
        val phase = frame.time.phase(style.period).mod(LOOP)
        val morph = phase.toFloat()
        val drift = (phase * style.drift).mod(LOOP).toFloat()
        val scale = style.scale.toPx().coerceAtLeast(minimumValue = 1f)
        val seed = frame.seed.value
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                val noise = warpedNoise(
                    x = column * cell / scale + drift,
                    y = row * cell / scale,
                    z = morph,
                    seed = seed,
                    strength = style.warp,
                    octaves = style.octaves,
                )
                val height = HALF + noise * style.contrast * HALF
                field[row * columns + column] = height.coerceIn(minimumValue = 0f, maximumValue = 1f)
            }
        }
    }
}

private fun thresholdOf(level: Int, levels: Int): Float = (level + 1f) / (levels + 1)
