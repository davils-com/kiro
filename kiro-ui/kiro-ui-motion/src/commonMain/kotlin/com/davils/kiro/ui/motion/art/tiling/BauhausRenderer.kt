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

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.sin

private const val QUARTER: Float = 90f
private const val HALF: Float = 180f
private const val QUADRANT: Float = 0.25f
private const val SHAPE_SALT: Int = 0x2545f491
private const val COLOR_SALT: Int = 0x6c8e9cf5
private const val ACCENT_SALT: Int = 0x1b873593
private const val HUE_SALT: Int = 0x5f356495
private const val TURN_SALT: Int = 0x3c6ef372
private const val DIRECTION_SALT: Int = 0x7f4a7c15
private const val MOTION_SALT: Int = 0x0b4f3a29

internal class BauhausRenderer(private val style: BauhausArtStyle) : ArtRenderer {
    private val shapes = BauhausShape.entries
    private val weights = FloatArray(shapes.size) { index -> style.weights[shapes[index]] ?: 0f }
    private val tilePath = Path()
    private val trianglePath = Path()
    private var cell = -1f
    private var ring = Stroke()
    private var outline = Stroke()
    private var colors: List<Color> = emptyList()
    private var ground = Color.Unspecified
    private var border = Color.Unspecified

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val gap = style.gap.toPx()
        val grid = TileGrid.of(width = size.width, height = size.height, pitch = style.cell.toPx() + gap)
        if (grid.columns == 0) return
        prepareShapes(cell = grid.pitch - gap, radius = style.radius.toPx(), outlineWidth = style.outlineWidth.toPx())
        colors = style.colors ?: frame.palette.accents
        ground = style.background ?: frame.palette.background
        border = style.outline ?: frame.palette.ink
        if (colors.isEmpty()) return
        val seed = frame.seed.derive(style.salt).value
        val phase = frame.time.phase(style.period)
        for (row in 0 until grid.rows) {
            for (column in 0 until grid.columns) {
                val topLeft = Offset(x = grid.leftOf(column) + gap / 2f, y = grid.topOf(row) + gap / 2f)
                val isMoving = unitOf(x = column, y = row, seed = seed + MOTION_SALT) < frame.quality.density
                drawTile(column = column, row = row, seed = seed, phase = phase.takeIf { isMoving }, topLeft = topLeft)
            }
        }
    }

    private fun prepareShapes(cell: Float, radius: Float, outlineWidth: Float) {
        if (cell == this.cell && outlineWidth == outline.width) return
        this.cell = cell
        val corner = CornerRadius(radius.coerceAtMost(cell / 2f))
        tilePath.rewind()
        tilePath.addRoundRect(RoundRect(left = 0f, top = 0f, right = cell, bottom = cell, cornerRadius = corner))
        trianglePath.rewind()
        trianglePath.moveTo(x = 0f, y = 0f)
        trianglePath.lineTo(x = cell, y = 0f)
        trianglePath.lineTo(x = 0f, y = cell)
        trianglePath.close()
        ring = Stroke(width = cell * style.ringWidth)
        outline = Stroke(width = outlineWidth)
    }

    private fun DrawScope.drawTile(column: Int, row: Int, seed: Int, phase: Double?, topLeft: Offset) {
        val base = colors[pickOf(size = colors.size, x = column, y = row, seed = seed + COLOR_SALT)]
        val tile = lerp(start = ground, stop = base, fraction = style.tileTint)
        val hue = colors[pickOf(size = colors.size, x = column, y = row, seed = seed + HUE_SALT)]
        var shapeColor = lerp(start = ground, stop = hue, fraction = style.shapeTint)
        if (unitOf(x = column, y = row, seed = seed + ACCENT_SALT) < style.accentShare) shapeColor = hue
        val kind = weightedIndex(weights, unit = unitOf(x = column, y = row, seed = seed + SHAPE_SALT))
        val direction = 1 - 2 * pickOf(size = 2, x = column, y = row, seed = seed + DIRECTION_SALT)
        val resting = pickOf(size = 4, x = column, y = row, seed = seed)
        var progress = 0f
        var quarter = resting
        if (phase != null) {
            val local = phase + unitOf(x = column, y = row, seed = seed + TURN_SALT) * style.stagger
            progress = turnProgress(phase = local, turn = style.turn, easing = style.easing)
            quarter = quarterOf(resting = resting, direction = direction, turns = wholeTurns(local))
        }
        val degrees = (quarter + direction * progress) * QUARTER
        val scale = 1f - style.pop * sin(PI * progress).toFloat()
        val pivot = Offset(x = cell / 2f, y = cell / 2f)
        withTransform(
            transformBlock = {
                translate(left = topLeft.x, top = topLeft.y)
                rotate(degrees = degrees, pivot = pivot)
                scale(scaleX = scale, scaleY = scale, pivot = pivot)
            },
        ) {
            drawPath(path = tilePath, color = tile)
            if (kind >= 0) {
                clipPath(path = tilePath) { drawShape(shape = shapes[kind], color = shapeColor) }
            }
            if (style.outlineAlpha > 0f && outline.width > 0f) {
                drawPath(path = tilePath, color = border, alpha = style.outlineAlpha, style = outline)
            }
        }
    }

    private fun DrawScope.drawShape(shape: BauhausShape, color: Color) {
        val center = Offset(x = cell / 2f, y = cell / 2f)
        when (shape) {
            BauhausShape.QuarterCircle -> drawQuarter(color = color, corner = Offset.Zero, start = 0f, radius = cell)

            BauhausShape.HalfCircle -> drawArc(
                color = color,
                startAngle = 0f,
                sweepAngle = HALF,
                useCenter = true,
                topLeft = Offset(x = 0f, y = -cell / 2f),
                size = Size(width = cell, height = cell),
            )

            BauhausShape.Circle -> drawCircle(color = color, radius = cell * style.circle, center = center)

            BauhausShape.Ring -> drawCircle(color = color, radius = cell * style.ring, center = center, style = ring)

            BauhausShape.Triangle -> drawPath(path = trianglePath, color = color)

            BauhausShape.Stripes -> drawStripes(color)

            BauhausShape.DoubleQuarter -> drawDoubleQuarter(color)

            BauhausShape.Dot -> drawCircle(color = color, radius = cell * style.dot, center = dotCenter())
        }
    }

    private fun DrawScope.drawStripes(color: Color) {
        val count = style.stripes.coerceAtLeast(1)
        val width = cell * style.stripeWidth
        for (index in 0 until count) {
            val top = cell * (2 * index + 1) / (2 * count) - width / 2f
            drawRect(color = color, topLeft = Offset(x = 0f, y = top), size = Size(width = cell, height = width))
        }
    }

    private fun DrawScope.drawDoubleQuarter(color: Color) {
        val radius = cell / 2f
        drawQuarter(color = color, corner = Offset.Zero, start = 0f, radius = radius)
        drawQuarter(color = color, corner = Offset(x = cell, y = cell), start = HALF, radius = radius)
    }

    private fun DrawScope.drawQuarter(color: Color, corner: Offset, start: Float, radius: Float) {
        drawArc(
            color = color,
            startAngle = start,
            sweepAngle = QUARTER,
            useCenter = true,
            topLeft = Offset(x = corner.x - radius, y = corner.y - radius),
            size = Size(width = radius * 2f, height = radius * 2f),
        )
    }

    private fun dotCenter(): Offset = Offset(x = cell - cell * QUADRANT, y = cell * QUADRANT)
}
