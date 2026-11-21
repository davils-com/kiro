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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.art.dashesOf
import com.davils.kiro.ui.motion.color.band
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.pow

private const val VACANCY_SALT: Int = 0x3243f6a8

internal class IsometricCityRenderer(private val style: IsometricCityArtStyle) : ArtRenderer {
    private val top = Path()
    private val left = Path()
    private val right = Path()
    private var scale = -1f
    private var outline = Stroke()
    private var windows: PathEffect? = null
    private var hasDetail = false
    private var seed = 0
    private var low = 0f
    private var high = 0f
    private var half = 0f
    private var quarter = 0f
    private var origin = Offset.Zero
    private var colors: List<Color> = emptyList()
    private var fallback = Color.Unspecified
    private var shade = Color.Unspecified
    private var body = Color.Unspecified
    private var ink = Color.Unspecified

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        low = style.minHeight.toPx()
        high = style.maxHeight.toPx().coerceAtLeast(low)
        val grid = IsometricGrid.of(width = size.width, height = size.height, tile = style.tile.toPx(), reach = high)
        if (grid == null) return
        prepare(frame = frame, grid = grid)
        val phase = frame.time.phase(style.period)
        for (diagonal in grid.firstDiagonal..grid.lastDiagonal) {
            for (column in grid.firstColumnOf(diagonal)..grid.lastColumn step 2) {
                if (unitOf(x = column, y = diagonal, seed = seed + VACANCY_SALT) < style.vacancy) continue
                drawLot(grid = grid, column = column, diagonal = diagonal, phase = phase)
            }
        }
    }

    private fun DrawScope.prepare(frame: ArtFrame, grid: IsometricGrid) {
        hasDetail = frame.quality.density >= style.detailQuality.density
        seed = frame.seed.derive(style.salt).value
        half = grid.tile / 2f * (1f - style.inset)
        quarter = grid.quarter * (1f - style.inset)
        origin = Offset(x = grid.columnAt(size.width * style.originX), y = grid.diagonalAt(size.height * style.originY))
        colors = style.colors ?: frame.palette.accents
        fallback = frame.palette.ink
        shade = style.shade ?: frame.palette.shade
        ink = style.detail ?: frame.palette.ink
        body = style.base ?: frame.palette.background
        if (scale == density) return
        scale = density
        outline = Stroke(width = style.outlineWidth.toPx())
        windows = dashesOf(on = style.window.toPx(), off = style.windowGap.toPx())
    }

    private fun DrawScope.drawLot(grid: IsometricGrid, column: Int, diagonal: Int, phase: Double) {
        val x = grid.xOf(column)
        val y = grid.yOf(diagonal)
        val distance = gridDistance(
            column = column.toFloat(),
            diagonal = diagonal.toFloat(),
            originColumn = origin.x,
            originDiagonal = origin.y,
        )
        val wave = waveHeight(distance = distance, frequency = style.frequency, phase = phase)
        val level = blockHeight(wave = wave, jitter = style.jitter, column = column, diagonal = diagonal, seed = seed)
        val height = low + (high - low) * level
        if (!isVisible(x = x, y = y, height = height)) return
        val hue = colors.band(fraction = level.pow(style.colorBias), fallback = fallback)
        drawBlock(x = x, y = y, height = height, crown = lerp(start = body, stop = hue, fraction = style.tint * level))
        if (hasDetail) drawDetail(x = x, y = y, height = height)
    }

    private fun DrawScope.isVisible(x: Float, y: Float, height: Float): Boolean =
        x + half >= 0f && x - half <= size.width && y + quarter >= 0f && y - height - quarter <= size.height

    private fun DrawScope.drawBlock(x: Float, y: Float, height: Float, crown: Color) {
        val roof = y - height
        left.rewind()
        left.moveTo(x = x - half, y = roof)
        left.lineTo(x = x, y = roof + quarter)
        left.lineTo(x = x, y = y + quarter)
        left.lineTo(x = x - half, y = y)
        left.close()
        drawPath(path = left, color = lerp(start = body, stop = shade, fraction = style.leftShade))
        right.rewind()
        right.moveTo(x = x, y = roof + quarter)
        right.lineTo(x = x + half, y = roof)
        right.lineTo(x = x + half, y = y)
        right.lineTo(x = x, y = y + quarter)
        right.close()
        drawPath(path = right, color = lerp(start = body, stop = shade, fraction = style.rightShade))
        top.rewind()
        top.moveTo(x = x, y = roof - quarter)
        top.lineTo(x = x + half, y = roof)
        top.lineTo(x = x, y = roof + quarter)
        top.lineTo(x = x - half, y = roof)
        top.close()
        drawPath(path = top, color = crown)
    }

    private fun DrawScope.drawDetail(x: Float, y: Float, height: Float) {
        if (style.outlineAlpha > 0f) drawEdges()
        val floor = style.floor.toPx()
        if (style.windowAlpha <= 0f || floor <= 0f) return
        val storeys = (height / floor).toInt()
        val window = style.windowHeight.toPx()
        for (storey in 0 until storeys) {
            val z = (storey + 1) * floor - floor / 2f
            val middle = Offset(x = x, y = y + quarter - z)
            drawWindows(start = Offset(x = x - half, y = y - z), end = middle, width = window)
            drawWindows(start = middle, end = Offset(x = x + half, y = y - z), width = window)
        }
    }

    private fun DrawScope.drawEdges() {
        drawPath(path = left, color = ink, alpha = style.outlineAlpha, style = outline)
        drawPath(path = right, color = ink, alpha = style.outlineAlpha, style = outline)
        drawPath(path = top, color = ink, alpha = style.outlineAlpha, style = outline)
    }

    private fun DrawScope.drawWindows(start: Offset, end: Offset, width: Float) {
        drawLine(
            color = ink,
            start = start,
            end = end,
            strokeWidth = width,
            pathEffect = windows,
            alpha = style.windowAlpha,
        )
    }
}
