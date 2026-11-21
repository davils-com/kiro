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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.color.cyclic
import com.davils.kiro.ui.motion.noise.lerp
import com.davils.kiro.ui.motion.noise.unitOf

internal class CheckerMorphRenderer(private val style: CheckerMorphArtStyle) : ArtRenderer {
    private var grid = TileGrid.Empty
    private var gridWidth = 0f
    private var outline = Stroke()
    private var builtSize = Size.Unspecified
    private var builtDensity = 0f
    private var ink = Color.Unspecified
    private var isOutlined = false

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
        prepare()
        if (style.gridAlpha <= 0f || grid.columns == 0) return
        val line = style.line ?: frame.palette.ink
        for (column in 0..grid.columns) {
            val x = grid.leftOf(column)
            drawGridLine(color = line, start = Offset(x = x, y = 0f), end = Offset(x = x, y = size.height))
        }
        for (row in 0..grid.rows) {
            val y = grid.topOf(row)
            drawGridLine(color = line, start = Offset(x = 0f, y = y), end = Offset(x = size.width, y = y))
        }
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        prepare()
        val seed = frame.seed.derive(style.salt).value
        val phase = frame.time.phase(style.period)
        val colors = frame.palette.colorsOr(style.colors)
        ink = style.line ?: frame.palette.ink
        isOutlined = style.outlineAlpha > 0f && frame.quality.density >= style.outlineQuality.density
        for (row in 0 until grid.rows) {
            for (column in 0 until grid.columns) {
                if (!isCheckerCell(column = column, row = row)) continue
                val lag = (column + row) * style.lag + unitOf(x = column, y = row, seed = seed) * style.jitter
                val morph = morphOf(local = phase - lag, morph = style.morph, easing = style.easing)
                val band = checkerBand(column = column, row = row, band = style.band)
                val tint = colors.cyclic(index = band, fallback = ink)
                drawShape(column = column, row = row, morph = morph, tint = tint)
            }
        }
    }

    private fun DrawScope.prepare() {
        if (size == builtSize && density == builtDensity) return
        builtSize = size
        builtDensity = density
        grid = TileGrid.of(width = size.width, height = size.height, pitch = style.cell.toPx())
        gridWidth = style.gridWidth.toPx()
        outline = Stroke(width = gridWidth)
    }

    private fun DrawScope.drawGridLine(color: Color, start: Offset, end: Offset) {
        drawLine(color = color, start = start, end = end, strokeWidth = gridWidth, alpha = style.gridAlpha)
    }

    private fun DrawScope.drawShape(column: Int, row: Int, morph: Float, tint: Color) {
        val cell = grid.pitch
        val full = cell * (1f - 2f * style.inset)
        val side = full * lerp(start = 1f, end = style.circle, fraction = morph)
        val corner = CornerRadius(side / 2f * morph)
        val topLeft = Offset(x = grid.leftOf(column) + (cell - side) / 2f, y = grid.topOf(row) + (cell - side) / 2f)
        val box = Size(width = side, height = side)
        val rest = style.fillAlpha * (1f - morph)
        drawRoundRect(color = ink, topLeft = topLeft, size = box, cornerRadius = corner, alpha = rest)
        if (morph <= 0f) return
        val crest = style.accentAlpha * morph
        drawRoundRect(color = tint, topLeft = topLeft, size = box, cornerRadius = corner, alpha = crest)
        if (!isOutlined) return
        drawRoundRect(
            color = tint,
            topLeft = topLeft,
            size = box,
            cornerRadius = corner,
            alpha = style.outlineAlpha * morph,
            style = outline,
        )
    }
}
