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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.noise.unitOf

private const val CORNERS: Int = 4

internal class BlueprintRenderer(
    private val style: BlueprintArtStyle,
    private val measurer: TextMeasurer?,
) : ArtRenderer {
    private var band: Brush = Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Transparent))
    private var bandHeight = -1f
    private var bandColor = Color.Unspecified
    private var originX = 0f
    private var originY = 0f
    private var cell = 0f
    private var firstColumn = 0
    private var lastColumn = -1
    private var firstRow = 0
    private var lastRow = -1

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        prepare(frame)
        val ink = style.line ?: frame.palette.ink
        drawGrid(ink = ink)
        drawMarks(color = ink, scan = Float.NaN)
        drawBrackets(ink = ink)
        if (style.isRulerShown && measurer != null) drawRuler(measurer = measurer, ink = ink)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        prepare(frame)
        val accent = style.accent ?: frame.palette.accent(-1)
        val start = unitOf(x = 2, seed = frame.seed.derive(style.salt).value)
        val scan = style.easing.transform((frame.time.phase(style.scanPeriod) + start).mod(1.0).toFloat()) * size.height
        val height = style.scanBand.toPx()
        if (height > 0f && style.bandAlpha > 0f && frame.quality.density >= style.bandQuality.density) {
            drawBand(accent = accent, scan = scan, height = height)
        }
        drawLine(
            color = accent,
            start = Offset(x = 0f, y = scan),
            end = Offset(x = size.width, y = scan),
            strokeWidth = style.scanWidth.toPx(),
            alpha = style.scanAlpha,
        )
        drawMarks(color = accent, scan = scan)
    }

    private fun DrawScope.prepare(frame: ArtFrame) {
        val seed = frame.seed.derive(style.salt).value
        cell = style.cell.toPx()
        val span = cell * style.major.coerceAtLeast(minimumValue = 1)
        originX = unitOf(x = 0, seed = seed) * span
        originY = unitOf(x = 1, seed = seed) * span
        firstColumn = firstLine(origin = originX, cell = cell)
        lastColumn = lastLine(origin = originX, extent = size.width, cell = cell)
        firstRow = firstLine(origin = originY, cell = cell)
        lastRow = lastLine(origin = originY, extent = size.height, cell = cell)
    }

    private fun DrawScope.drawGrid(ink: Color) {
        val width = style.lineWidth.toPx()
        for (column in firstColumn..lastColumn) {
            val x = originX + column * cell
            drawLine(
                color = ink,
                start = Offset(x = x, y = 0f),
                end = Offset(x = x, y = size.height),
                strokeWidth = width,
                alpha = gridAlpha(column),
            )
        }
        for (row in firstRow..lastRow) {
            val y = originY + row * cell
            drawLine(
                color = ink,
                start = Offset(x = 0f, y = y),
                end = Offset(x = size.width, y = y),
                strokeWidth = width,
                alpha = gridAlpha(row),
            )
        }
    }

    private fun gridAlpha(index: Int): Float {
        if (isMajorLine(index = index, major = style.major)) return style.majorAlpha
        return style.minorAlpha
    }

    private fun DrawScope.drawMarks(color: Color, scan: Float) {
        val arm = style.mark.toPx()
        if (arm <= 0f || style.major <= 0) return
        val width = style.lineWidth.toPx()
        val reach = style.scanBand.toPx()
        for (row in firstRow..lastRow) {
            val y = originY + row * cell
            val isMajor = isMajorLine(index = row, major = style.major)
            val alpha = markAlpha(isMajor = isMajor, distance = scan - y, band = reach, arm = arm)
            if (alpha <= 0f) continue
            for (column in firstColumn..lastColumn) {
                if (!isMajorLine(index = column, major = style.major)) continue
                val x = originX + column * cell
                drawMark(color = color, center = Offset(x = x, y = y), arm = arm, width = width, alpha = alpha)
            }
        }
    }

    private fun DrawScope.drawMark(color: Color, center: Offset, arm: Float, width: Float, alpha: Float) {
        val left = center.copy(x = center.x - arm)
        val right = center.copy(x = center.x + arm)
        drawLine(color = color, start = left, end = right, strokeWidth = width, alpha = alpha)
        val top = center.copy(y = center.y - arm)
        val bottom = center.copy(y = center.y + arm)
        drawLine(color = color, start = top, end = bottom, strokeWidth = width, alpha = alpha)
    }

    private fun markAlpha(isMajor: Boolean, distance: Float, band: Float, arm: Float): Float {
        if (!isMajor) return 0f
        return litAlpha(distance = distance, band = band, arm = arm)
    }

    private fun litAlpha(distance: Float, band: Float, arm: Float): Float {
        if (distance.isNaN()) return style.markAlpha
        return style.litAlpha * scanWeight(distance = distance, band = band, reach = arm)
    }

    private fun DrawScope.drawBrackets(ink: Color) {
        val margin = style.margin.toPx()
        val arm = style.bracket.toPx()
        if (arm <= 0f || style.bracketAlpha <= 0f) return
        val width = style.bracketWidth.toPx()
        for (corner in 0 until CORNERS) {
            val right = corner % 2
            val bottom = corner / 2
            val x = margin + (size.width - margin * 2f) * right
            val y = margin + (size.height - margin * 2f) * bottom
            val start = Offset(x = x, y = y)
            val across = Offset(x = x + arm * (1 - right * 2), y = y)
            val down = Offset(x = x, y = y + arm * (1 - bottom * 2))
            drawLine(color = ink, start = start, end = across, strokeWidth = width, alpha = style.bracketAlpha)
            drawLine(color = ink, start = start, end = down, strokeWidth = width, alpha = style.bracketAlpha)
        }
    }

    private fun DrawScope.drawRuler(measurer: TextMeasurer, ink: Color) {
        val padding = style.labelPadding.toPx()
        val major = style.major.coerceAtLeast(minimumValue = 1)
        for (column in firstColumn..lastColumn) {
            if (!isMajorLine(index = column, major = major)) continue
            val topLeft = Offset(x = originX + column * cell + padding, y = padding)
            drawLabel(measurer = measurer, value = column / major, color = ink, topLeft = topLeft)
        }
        for (row in firstRow..lastRow) {
            if (!isMajorLine(index = row, major = major)) continue
            val topLeft = Offset(x = padding, y = originY + row * cell + padding)
            drawLabel(measurer = measurer, value = row / major, color = ink, topLeft = topLeft)
        }
    }

    private fun DrawScope.drawLabel(measurer: TextMeasurer, value: Int, color: Color, topLeft: Offset) {
        val text = style.label(value * style.labelStep)
        val layout = measurer.measure(text = text, style = style.labelStyle, density = this)
        drawText(textLayoutResult = layout, color = color, topLeft = topLeft, alpha = style.labelAlpha)
    }

    private fun DrawScope.drawBand(accent: Color, scan: Float, height: Float) {
        if (height != bandHeight || accent != bandColor) {
            bandHeight = height
            bandColor = accent
            band = Brush.verticalGradient(
                colors = listOf(accent.copy(alpha = 0f), accent.copy(alpha = style.bandAlpha)),
                startY = 0f,
                endY = height,
            )
        }
        translate(top = scan - height) {
            drawRect(brush = band, size = Size(width = size.width, height = height))
        }
    }
}
