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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.cyclic

private const val QUARTER: Float = 90f
private const val HALF: Float = 180f
private const val THREE_QUARTERS: Float = 270f

internal class TruchetRenderer(private val style: TruchetArtStyle) : ArtRenderer {
    private val discs = Path()
    private val band = Path()
    private val corners = Path()
    private val middle = Path()
    private val fills = listOf(band, discs, middle, corners)
    private var stroke = Stroke()
    private var cell = 0f
    private var isFilled = false
    private var seed = 0
    private var lineAlpha = 1f

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val grid = TileGrid.of(width = size.width, height = size.height, pitch = style.cell.toPx())
        if (grid.columns == 0) return
        prepare(cell = grid.pitch, width = style.strokeWidth.toPx(), frame = frame)
        val phase = frame.time.phase(style.period)
        val pointer = frame.inputs.pointer
        val radius = style.pointerRadius.toPx()
        val colors = style.colors ?: frame.palette.accents
        val line = style.line ?: frame.palette.ink
        for (row in 0 until grid.rows) {
            for (column in 0 until grid.columns) {
                val topLeft = Offset(x = grid.leftOf(column), y = grid.topOf(row))
                var lead = 0f
                if (pointer != null) {
                    val dx = pointer.x - topLeft.x - cell / 2f
                    val dy = pointer.y - topLeft.y - cell / 2f
                    lead = pointerLead(dx = dx, dy = dy, radius = radius, lead = style.pointerLead)
                }
                val local = phase - (column + row) * style.lag + lead
                val band = truchetBand(column = column, row = row, band = style.band)
                val accent = accentOf(band = band, every = style.accentEvery)
                var color = line
                lineAlpha = style.lineAlpha
                if (accent >= 0) {
                    color = colors.cyclic(index = accent, fallback = line)
                    lineAlpha = style.accentAlpha
                }
                drawTile(column = column, row = row, local = local, topLeft = topLeft, color = color)
            }
        }
    }

    private fun prepare(cell: Float, width: Float, frame: ArtFrame) {
        isFilled = frame.quality.density >= style.fillQuality.density && style.fillAlpha > 0f
        seed = frame.seed.derive(style.salt).value
        if (cell == this.cell && width == stroke.width) return
        this.cell = cell
        stroke = Stroke(width = width, cap = style.cap)
        val radius = cell / 2f
        val start = Rect(center = Offset.Zero, radius = radius)
        val end = Rect(center = Offset(x = cell, y = cell), radius = radius)
        discs.rewind()
        discs.moveTo(x = 0f, y = 0f)
        discs.lineTo(x = radius, y = 0f)
        discs.arcTo(rect = start, startAngleDegrees = 0f, sweepAngleDegrees = QUARTER, forceMoveTo = false)
        discs.close()
        discs.moveTo(x = cell, y = cell)
        discs.lineTo(x = radius, y = cell)
        discs.arcTo(rect = end, startAngleDegrees = HALF, sweepAngleDegrees = QUARTER, forceMoveTo = false)
        discs.close()
        band.rewind()
        band.moveTo(x = radius, y = 0f)
        band.lineTo(x = cell, y = 0f)
        band.lineTo(x = cell, y = radius)
        band.arcTo(rect = end, startAngleDegrees = THREE_QUARTERS, sweepAngleDegrees = -QUARTER, forceMoveTo = false)
        band.lineTo(x = 0f, y = cell)
        band.lineTo(x = 0f, y = radius)
        band.arcTo(rect = start, startAngleDegrees = QUARTER, sweepAngleDegrees = -QUARTER, forceMoveTo = false)
        band.close()
        corners.rewind()
        corners.moveTo(x = 0f, y = 0f)
        corners.lineTo(x = radius, y = 0f)
        corners.lineTo(x = 0f, y = radius)
        corners.close()
        corners.moveTo(x = cell, y = cell)
        corners.lineTo(x = radius, y = cell)
        corners.lineTo(x = cell, y = radius)
        corners.close()
        middle.rewind()
        middle.moveTo(x = radius, y = 0f)
        middle.lineTo(x = cell, y = 0f)
        middle.lineTo(x = cell, y = radius)
        middle.lineTo(x = radius, y = cell)
        middle.lineTo(x = 0f, y = cell)
        middle.lineTo(x = 0f, y = radius)
        middle.close()
    }

    private fun DrawScope.drawTile(column: Int, row: Int, local: Double, topLeft: Offset, color: Color) {
        val wave = wholeTurns(local)
        val from = truchetOrientation(column = column, row = row, wave = wave - 1, flip = style.flip, seed = seed)
        val to = truchetOrientation(column = column, row = row, wave = wave, flip = style.flip, seed = seed)
        val isLine = truchetLine(
            variant = style.variant,
            column = column,
            row = row,
            share = style.lineShare,
            seed = seed,
        )
        var progress = turnProgress(phase = local, turn = style.turn, easing = style.easing)
        var orientation = from
        var direction = truchetDirection(column = column, row = row, wave = wave, seed = seed)
        if (from == to || progress >= 1f) {
            orientation = to
            progress = 0f
            direction = 0
        }
        val isEven = (column + row + orientation).mod(2) == 0
        val pivot = Offset(x = cell / 2f, y = cell / 2f)
        if (isFilled) {
            val first = fillOf(isLine = isLine, isEven = isEven)
            val resting = orientation * QUARTER
            drawFill(topLeft = topLeft, degrees = resting, path = first, alpha = 1f - progress, color = color)
            val second = fillOf(isLine = isLine, isEven = !isEven)
            val turned = (orientation + direction) * QUARTER
            drawFill(topLeft = topLeft, degrees = turned, path = second, alpha = progress, color = color)
        }
        withTransform(
            transformBlock = {
                translate(left = topLeft.x, top = topLeft.y)
                rotate(degrees = (orientation + direction * progress) * QUARTER, pivot = pivot)
            },
        ) {
            drawMotif(isLine = isLine, color = color)
        }
    }

    private fun fillOf(isLine: Boolean, isEven: Boolean): Path = fills[isLine.toBit() * 2 + isEven.toBit()]

    private fun DrawScope.drawFill(topLeft: Offset, degrees: Float, path: Path, alpha: Float, color: Color) {
        if (alpha <= 0f) return
        withTransform(
            transformBlock = {
                translate(left = topLeft.x, top = topLeft.y)
                rotate(degrees = degrees, pivot = Offset(x = cell / 2f, y = cell / 2f))
            },
        ) {
            drawPath(path = path, color = color, alpha = style.fillAlpha * alpha)
        }
    }

    private fun DrawScope.drawMotif(isLine: Boolean, color: Color) {
        val radius = cell / 2f
        if (isLine) {
            drawCut(color = color, start = Offset(x = radius, y = 0f), end = Offset(x = 0f, y = radius))
            drawCut(color = color, start = Offset(x = cell, y = radius), end = Offset(x = radius, y = cell))
            return
        }
        drawPipe(color = color, start = 0f, topLeft = Offset(x = -radius, y = -radius))
        drawPipe(color = color, start = HALF, topLeft = Offset(x = radius, y = radius))
    }

    private fun DrawScope.drawCut(color: Color, start: Offset, end: Offset) {
        drawLine(
            color = color,
            start = start,
            end = end,
            strokeWidth = stroke.width,
            cap = style.cap,
            alpha = lineAlpha,
        )
    }

    private fun DrawScope.drawPipe(color: Color, start: Float, topLeft: Offset) {
        drawArc(
            color = color,
            startAngle = start,
            sweepAngle = QUARTER,
            useCenter = false,
            topLeft = topLeft,
            size = Size(width = cell, height = cell),
            alpha = lineAlpha,
            style = stroke,
        )
    }
}
