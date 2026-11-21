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

package com.davils.kiro.ui.motion.art.grid

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.color.cyclic
import com.davils.kiro.ui.motion.noise.pickOf
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

private const val CYCLES: Int = 4096
private const val ROW_SALT: Int = 0x2c1b3c6d
private const val COLOR_SALT: Int = 0x297a2d39

internal class GridPatternRenderer(private val style: GridPatternArtStyle) : ArtRenderer {
    private val minor = ArrayList<Offset>()
    private val major = ArrayList<Offset>()

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        val cells = GridCells.of(width = size.width, height = size.height, pitch = style.cell.toPx())
        if (cells.columns == 0) return
        collect(cells = cells)
        val color = style.line ?: frame.palette.ink
        val isLines = style.kind == GridPatternKind.Lines
        val mode = pointModeOf(isLines = isLines)
        val width = strokeOf(isLines = isLines)
        val cap = capOf()
        for ((points, alpha) in listOf(minor to style.lineAlpha, major to style.majorAlpha)) {
            drawPoints(points = points, pointMode = mode, color = color, strokeWidth = width, cap = cap, alpha = alpha)
        }
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val cells = GridCells.of(width = size.width, height = size.height, pitch = style.cell.toPx())
        if (cells.columns == 0) return
        val seed = frame.seed.derive(style.salt).value
        val colors = frame.palette.colorsOr(style.colors)
        val count = (style.highlights * frame.quality.density).roundToInt()
        val turns = frame.time.phase(style.period * CYCLES) * CYCLES
        for (slot in 0 until count) {
            val local = turns + slot.toDouble() / count
            val cycle = floor(local).toInt()
            val rise = 1f - abs(2f * (local - cycle).toFloat() - 1f)
            val column = pickOf(size = cells.columns, x = slot, y = cycle, seed = seed)
            val row = pickOf(size = cells.rows, x = slot, y = cycle, seed = seed + ROW_SALT)
            val tint = pickOf(size = colors.size, x = slot, y = cycle, seed = seed + COLOR_SALT)
            val alpha = style.highlightAlpha * style.easing.transform(rise)
            val color = colors.cyclic(index = tint, fallback = frame.palette.ink)
            drawCell(cells = cells, column = column, row = row, color = color, alpha = alpha)
        }
        val pointer = frame.inputs.pointer ?: return
        drawPointer(cells = cells, pointer = pointer, color = style.pointer ?: frame.palette.highlight)
    }

    private fun DrawScope.drawPointer(cells: GridCells, pointer: Offset, color: Color) {
        val column = cells.columnAt(pointer.x)
        val row = cells.rowAt(pointer.y)
        drawCell(cells = cells, column = column, row = row, color = color, alpha = style.pointerAlpha)
        if (style.pointerOutlineAlpha <= 0f) return
        val inset = style.inset.toPx()
        val side = cells.pitch - inset * 2f
        drawRoundRect(
            color = color,
            topLeft = Offset(x = cells.xOf(column) + inset, y = cells.yOf(row) + inset),
            size = Size(width = side, height = side),
            cornerRadius = CornerRadius(style.radius.toPx()),
            alpha = style.pointerOutlineAlpha,
            style = Stroke(width = style.lineWidth.toPx()),
        )
    }

    private fun DrawScope.drawCell(cells: GridCells, column: Int, row: Int, color: Color, alpha: Float) {
        val inset = style.inset.toPx()
        val side = cells.pitch - inset * 2f
        if (side <= 0f || alpha <= 0f) return
        drawRoundRect(
            color = color,
            topLeft = Offset(x = cells.xOf(column) + inset, y = cells.yOf(row) + inset),
            size = Size(width = side, height = side),
            cornerRadius = CornerRadius(style.radius.toPx()),
            alpha = alpha,
        )
    }

    private fun DrawScope.collect(cells: GridCells) {
        minor.clear()
        major.clear()
        val middleColumn = cells.lineAt(position = size.width / 2f, start = cells.left)
        val middleRow = cells.lineAt(position = size.height / 2f, start = cells.top)
        if (style.kind == GridPatternKind.Lines) {
            collectLines(cells = cells, middleColumn = middleColumn, middleRow = middleRow)
            return
        }
        collectMarks(cells = cells, middleColumn = middleColumn, middleRow = middleRow, arm = armOf())
    }

    private fun DrawScope.armOf(): Float {
        if (style.kind == GridPatternKind.Crosses) return style.mark.toPx()
        return 0f
    }

    private fun DrawScope.collectLines(cells: GridCells, middleColumn: Int, middleRow: Int) {
        for (column in 1 until cells.columns) {
            val x = cells.xOf(column)
            val target = targetOf(index = column, middle = middleColumn)
            target.add(Offset(x = x, y = 0f))
            target.add(Offset(x = x, y = size.height))
        }
        for (row in 1 until cells.rows) {
            val y = cells.yOf(row)
            val target = targetOf(index = row, middle = middleRow)
            target.add(Offset(x = 0f, y = y))
            target.add(Offset(x = size.width, y = y))
        }
    }

    private fun collectMarks(cells: GridCells, middleColumn: Int, middleRow: Int, arm: Float) {
        for (column in 1 until cells.columns) {
            for (row in 1 until cells.rows) {
                val isStrong = isMajor(index = column, middle = middleColumn, every = style.majorEvery) &&
                    isMajor(index = row, middle = middleRow, every = style.majorEvery)
                val target = major.takeIf { isStrong } ?: minor
                val center = Offset(x = cells.xOf(column), y = cells.yOf(row))
                addMark(target = target, center = center, arm = arm)
            }
        }
    }

    private fun addMark(target: MutableList<Offset>, center: Offset, arm: Float) {
        if (arm <= 0f) {
            target.add(center)
            return
        }
        target.add(Offset(x = center.x - arm, y = center.y))
        target.add(Offset(x = center.x + arm, y = center.y))
        target.add(Offset(x = center.x, y = center.y - arm))
        target.add(Offset(x = center.x, y = center.y + arm))
    }

    private fun targetOf(index: Int, middle: Int): MutableList<Offset> {
        if (isMajor(index = index, middle = middle, every = style.majorEvery)) return major
        return minor
    }

    private fun pointModeOf(isLines: Boolean): PointMode {
        if (isLines || style.kind == GridPatternKind.Crosses) return PointMode.Lines
        return PointMode.Points
    }

    private fun DrawScope.strokeOf(isLines: Boolean): Float {
        if (isLines || style.kind == GridPatternKind.Crosses) return style.lineWidth.toPx()
        return style.mark.toPx() * 2f
    }

    private fun capOf(): StrokeCap {
        if (style.kind == GridPatternKind.Dots) return StrokeCap.Round
        return StrokeCap.Butt
    }
}
