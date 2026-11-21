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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.hashOf
import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.unitOf
import com.davils.kiro.ui.motion.time.loopOf
import kotlin.math.ceil
import kotlin.math.roundToInt
import kotlin.math.sqrt

private const val HALF: Float = 0.5f
private const val GRADIENT_STEPS: Int = 48
private const val CYCLES: Int = 2
private const val RING: Int = 1
private const val CORNERS: Int = 3
private const val SALT_JITTER_X: Int = 1
private const val SALT_JITTER_Y: Int = 2
private const val SALT_CYCLES_X: Int = 3
private const val SALT_CYCLES_Y: Int = 4
private const val SALT_OFFSET_X: Int = 5
private const val SALT_OFFSET_Y: Int = 6
private const val SALT_TONE: Int = 7
private const val SALT_TINT: Int = 8

internal class VoronoiRenderer(private val style: VoronoiArtStyle) : ArtRenderer {
    private val gradient = ColorGradient()
    private val shard = VoronoiShard()
    private val fill = Path()
    private val edges = Path()
    private var seeds = FloatArray(size = 0)
    private var tones = FloatArray(size = 0)
    private var tints = FloatArray(size = 0)

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val cell = (style.cell.toPx() / sqrt(frame.quality.density)).coerceAtLeast(minimumValue = 1f)
        val columns = ceil(size.width / cell).toInt() + RING * 2
        val rows = ceil(size.height / cell).toInt() + RING * 2
        val count = columns * rows
        if (tones.size < count) {
            seeds = FloatArray(size = count * 2)
            tones = FloatArray(size = count)
            tints = FloatArray(size = count)
        }
        place(frame = frame, columns = columns, rows = rows, cell = cell)
        val pointer = frame.inputs.pointer
        val lit = pointer?.let { at -> nearestOf(x = at.x, y = at.y, seeds = seeds, count = count) } ?: -1
        val colors = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = GRADIENT_STEPS)
        val margin = style.lineWidth.toPx() * 2f
        edges.rewind()
        for (index in 0 until count) {
            shard.reset(left = -margin, top = -margin, right = size.width + margin, bottom = size.height + margin)
            shardOf(index = index, seeds = seeds, count = count, shard = shard)
            if (shard.size < CORNERS) continue
            fill.rewind()
            trace(path = fill)
            trace(path = edges)
            val alpha = style.pointerAlpha.takeIf { index == lit } ?: tints[index]
            drawPath(path = fill, color = colors[(tones[index] * (GRADIENT_STEPS - 1)).roundToInt()], alpha = alpha)
        }
        val line = style.line ?: frame.palette.ink
        drawPath(
            path = edges,
            color = line,
            alpha = style.lineAlpha,
            style = Stroke(width = style.lineWidth.toPx(), join = StrokeJoin.Round),
        )
        dots(color = line, count = count)
    }

    private fun DrawScope.place(frame: ArtFrame, columns: Int, rows: Int, cell: Float) {
        val seed = frame.seed.value
        val phase = frame.time.phase(style.period)
        val wander = style.drift * cell
        val spread = style.jitter * cell * 2f
        val width = size.width.coerceAtLeast(minimumValue = 1f)
        val height = size.height.coerceAtLeast(minimumValue = 1f)
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                val index = row * columns + column
                val key = hashOf(x = column, y = row, seed = seed)
                val homeX = (column - RING + HALF) * cell + (unitOf(x = key, y = SALT_JITTER_X) - HALF) * spread
                val homeY = (row - RING + HALF) * cell + (unitOf(x = key, y = SALT_JITTER_Y) - HALF) * spread
                val swayX = loopOf(
                    phase = phase,
                    cycles = 1 + pickOf(size = CYCLES, x = key, y = SALT_CYCLES_X),
                    offset = unitOf(x = key, y = SALT_OFFSET_X),
                )
                val swayY = loopOf(
                    phase = phase,
                    cycles = 1 + pickOf(size = CYCLES, x = key, y = SALT_CYCLES_Y),
                    offset = unitOf(x = key, y = SALT_OFFSET_Y),
                )
                seeds[index * 2] = homeX + wander * swayX
                seeds[index * 2 + 1] = homeY + wander * swayY
                val diagonal = ((homeX / width + homeY / height) * HALF).coerceIn(minimumValue = 0f, maximumValue = 1f)
                val tone = diagonal + (unitOf(x = key, y = SALT_TONE) - HALF) * style.toneJitter
                tones[index] = tone.coerceIn(minimumValue = 0f, maximumValue = 1f)
                tints[index] = style.tintAlpha * (1f - style.tintVariance * unitOf(x = key, y = SALT_TINT))
            }
        }
    }

    private fun trace(path: Path) {
        val points = shard.points
        path.moveTo(x = points[0], y = points[1])
        for (corner in 1 until shard.size) path.lineTo(x = points[corner * 2], y = points[corner * 2 + 1])
        path.close()
    }

    private fun DrawScope.dots(color: Color, count: Int) {
        val radius = style.seedRadius.toPx()
        if (radius <= 0f || style.seedAlpha <= 0f) return
        for (index in 0 until count) {
            val center = Offset(x = seeds[index * 2], y = seeds[index * 2 + 1])
            val isInside = center.x >= 0f && center.y >= 0f && center.x <= size.width && center.y <= size.height
            if (isInside) drawCircle(color = color, radius = radius, center = center, alpha = style.seedAlpha)
        }
    }
}
