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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.art.TriangleBatch
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.hashOf
import com.davils.kiro.ui.motion.noise.perlinNoise
import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.unitOf
import com.davils.kiro.ui.motion.time.loopOf
import kotlin.math.PI
import kotlin.math.ceil
import kotlin.math.roundToInt
import kotlin.math.sqrt

private const val TAU: Double = PI * 2
private const val HALF: Float = 0.5f
private const val THIRD: Float = 1f / 3f
private const val AXES: Int = 3
private const val RING: Int = 1
private const val CYCLES: Int = 2
private const val GRADIENT_STEPS: Int = 48
private const val SALT_JITTER_X: Int = 1
private const val SALT_JITTER_Y: Int = 2
private const val SALT_CYCLES: Int = 3
private const val SALT_OFFSET_X: Int = 4
private const val SALT_OFFSET_Y: Int = 5
private const val SALT_SPLIT: Int = 6

internal class LowPolyRenderer(private val style: LowPolyArtStyle) : ArtRenderer {
    private val gradient = ColorGradient()
    private val batch = TriangleBatch()
    private val edges = Path()
    private val light = FloatArray(size = AXES)
    private var points = FloatArray(size = 0)
    private var colors: List<Color> = emptyList()
    private var ground: Color = Color.Unspecified

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val cell = (style.cell.toPx() / sqrt(frame.quality.density)).coerceAtLeast(minimumValue = 1f)
        val columns = ceil(size.width / cell).toInt() + RING * 2 + 1
        val rows = ceil(size.height / cell).toInt() + RING * 2 + 1
        if (points.size < columns * rows * AXES) points = FloatArray(size = columns * rows * AXES)
        place(frame = frame, columns = columns, rows = rows, cell = cell)
        val azimuth = (TAU * frame.time.phase(style.lightPeriod).mod(1.0)).toFloat()
        lightOf(azimuth = azimuth, elevation = style.elevation, light = light)
        colors = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = GRADIENT_STEPS)
        ground = style.background ?: frame.palette.background
        edges.rewind()
        for (row in 0 until rows - 1) {
            for (column in 0 until columns - 1) {
                quad(frame = frame, column = column, row = row, columns = columns)
            }
        }
        with(batch) { flush(blendMode = BlendMode.SrcOver) }
        if (style.edgeAlpha <= 0f) return
        drawPath(
            path = edges,
            color = style.edge ?: frame.palette.background,
            alpha = style.edgeAlpha,
            style = Stroke(width = style.edgeWidth.toPx(), join = StrokeJoin.Round),
        )
    }

    private fun DrawScope.place(frame: ArtFrame, columns: Int, rows: Int, cell: Float) {
        val seed = frame.seed.value
        val phase = frame.time.phase(style.driftPeriod)
        val spread = style.jitter * cell * 2f
        val sway = style.drift * cell
        val scale = style.reliefScale.toPx().coerceAtLeast(minimumValue = 1f)
        val height = style.relief * cell
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                val index = (row * columns + column) * AXES
                val key = hashOf(x = column, y = row, seed = seed)
                val homeX = (column - RING) * cell + (unitOf(x = key, y = SALT_JITTER_X) - HALF) * spread
                val homeY = (row - RING) * cell + (unitOf(x = key, y = SALT_JITTER_Y) - HALF) * spread
                val cycles = 1 + pickOf(size = CYCLES, x = key, y = SALT_CYCLES)
                val swayX = loopOf(phase = phase, cycles = cycles, offset = unitOf(x = key, y = SALT_OFFSET_X))
                val swayY = loopOf(phase = phase, cycles = cycles, offset = unitOf(x = key, y = SALT_OFFSET_Y))
                points[index] = homeX + sway * swayX
                points[index + 1] = homeY + sway * swayY
                points[index + 2] = perlinNoise(x = homeX / scale, y = homeY / scale, seed = seed) * height
            }
        }
    }

    private fun DrawScope.quad(frame: ArtFrame, column: Int, row: Int, columns: Int) {
        val topLeft = row * columns + column
        val topRight = topLeft + 1
        val bottomLeft = topLeft + columns
        val bottomRight = bottomLeft + 1
        val isFalling = pickOf(size = 2, x = column, y = row, z = SALT_SPLIT, seed = frame.seed.value) == 0
        if (isFalling) {
            facet(first = topLeft, second = topRight, third = bottomRight)
            facet(first = topLeft, second = bottomRight, third = bottomLeft)
        }
        if (!isFalling) {
            facet(first = topLeft, second = topRight, third = bottomLeft)
            facet(first = topRight, second = bottomRight, third = bottomLeft)
        }
    }

    private fun DrawScope.facet(first: Int, second: Int, third: Int) {
        val a = Offset(x = points[first * AXES], y = points[first * AXES + 1])
        val b = Offset(x = points[second * AXES], y = points[second * AXES + 1])
        val c = Offset(x = points[third * AXES], y = points[third * AXES + 1])
        val isBefore = maxOf(a = a.x, b = b.x, c = c.x) < 0f || maxOf(a = a.y, b = b.y, c = c.y) < 0f
        val isAfter = minOf(a = a.x, b = b.x, c = c.x) > size.width || minOf(a = a.y, b = b.y, c = c.y) > size.height
        val isOutside = isBefore || isAfter
        if (isOutside) return
        val middle = (a + b + c) * THIRD
        val width = size.width.coerceAtLeast(minimumValue = 1f)
        val height = size.height.coerceAtLeast(minimumValue = 1f)
        val tone = ((middle.x / width + middle.y / height) * HALF).coerceIn(minimumValue = 0f, maximumValue = 1f)
        val shade = shadeOf(points = points, first = first, second = second, third = third, light = light)
        val amount = (style.tint * (1f + style.contrast * shade)).coerceIn(minimumValue = 0f, maximumValue = 1f)
        val color = lerp(start = ground, stop = colors[(tone * (GRADIENT_STEPS - 1)).roundToInt()], fraction = amount)
        with(batch) { reserve(count = AXES, blendMode = BlendMode.SrcOver) }
        batch.triangle(
            first = batch.vertex(position = a, color = color),
            second = batch.vertex(position = b, color = color),
            third = batch.vertex(position = c, color = color),
        )
        edges.moveTo(x = a.x, y = a.y)
        edges.lineTo(x = b.x, y = b.y)
        edges.lineTo(x = c.x, y = c.y)
        edges.close()
    }
}
