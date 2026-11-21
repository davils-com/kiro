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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.color.ColorGradient
import com.davils.kiro.ui.motion.color.colorsOr
import com.davils.kiro.ui.motion.noise.hashOf
import com.davils.kiro.ui.motion.noise.pickOf
import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

private const val CYCLES: Int = 4096
private const val MAX_GLYPHS: Int = 256
private const val ACTIVE_SALT: Int = 0x7f4a7c15
private const val SPEED_SALT: Int = 0x5bd1e995
private const val OFFSET_SALT: Int = 0x27d4eb2f
private const val CHANGE_SALT: Int = 0x165667b1
private const val BARS: Int = 3
private const val BAR_SLOTS: Int = 5
private const val BAR_BITS: Int = 2
private const val BAR_MASK: Int = 3
private const val BAR_SHIFT: Float = 0.4f
private const val BAR_CENTER: Float = 0.2f
private const val BAR_SHORT: Float = 0.6f
private const val HALF: Float = 0.5f
private val barStarts: FloatArray = floatArrayOf(0f, 0f, BAR_SHIFT, BAR_CENTER)
private val barLengths: FloatArray = floatArrayOf(1f, BAR_SHORT, BAR_SHORT, BAR_SHORT)

internal class MatrixRenderer(private val style: MatrixArtStyle, private val measurer: TextMeasurer?) : ArtRenderer {
    private val cache = GlyphCache(capacity = style.glyphs.length.coerceAtMost(maximumValue = MAX_GLYPHS))
    private val gradient = ColorGradient()

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val cell = style.cell.toPx()
        if (cell <= 0f || size.minDimension <= 0f) return
        val rows = ceil(size.height / cell).toInt()
        val seed = frame.seed.derive(style.salt).value
        val rain = Rain(
            cell = cell,
            rows = rows,
            seed = seed,
            changes = frame.time.phase(style.changePeriod * CYCLES) * CYCLES,
            trail = gradient.of(colors = frame.palette.colorsOr(style.colors), steps = style.trail.coerceAtLeast(1)),
            head = style.head ?: frame.palette.highlight,
        )
        val share = style.columnShare * sqrt(frame.quality.density)
        val start = unitOf(x = 0, seed = seed + ACTIVE_SALT)
        val span = rows + style.trail + style.pause
        for (column in 0 until ceil(size.width / cell).toInt()) {
            if (matrixShareOf(column = column, start = start) >= share) continue
            val speed = matrixSpeedOf(unit = unitOf(x = column, seed = seed + SPEED_SALT), spread = style.speedSpread)
            val phase = frame.time.phase(style.period * (span / speed.toDouble()))
            val head = matrixHeadOf(phase = phase, offset = unitOf(x = column, seed = seed + OFFSET_SALT), span = span)
            drawDrop(rain = rain, column = column, head = floor(head).toInt())
        }
    }

    private fun DrawScope.drawDrop(rain: Rain, column: Int, head: Int) {
        for (step in 0..style.trail) {
            val row = head - step
            if (row !in 0 until rain.rows) continue
            val tick = floor(rain.changes + unitOf(x = column, y = row, seed = rain.seed + CHANGE_SALT)).toInt()
            val glyph = pickOf(size = cache.capacity.coerceAtLeast(1), x = column, y = row, z = tick, seed = rain.seed)
            val topLeft = Offset(x = column * rain.cell, y = row * rain.cell)
            val color = rain.colorOf(step = step, trail = style.trail)
            val alpha = alphaOf(step = step)
            drawGlyph(glyph = glyph, topLeft = topLeft, cell = rain.cell, color = color, alpha = alpha)
        }
    }

    private fun alphaOf(step: Int): Float {
        if (step == 0) return style.headAlpha
        return style.trailAlpha * matrixFadeOf(step = step - 1, trail = style.trail)
    }

    private fun DrawScope.drawGlyph(glyph: Int, topLeft: Offset, cell: Float, color: Color, alpha: Float) {
        val text = measurer
        if (text == null || cache.capacity == 0) {
            drawBars(glyph = glyph, topLeft = topLeft, cell = cell, color = color, alpha = alpha)
            return
        }
        val layout = cache.glyph(index = glyph, density = this) { index -> measure(measurer = text, index = index) }
        val offset = Offset(
            x = topLeft.x + (cell - layout.size.width) / 2f,
            y = topLeft.y + (cell - layout.size.height) / 2f,
        )
        drawText(textLayoutResult = layout, color = color, topLeft = offset, alpha = alpha)
    }

    private fun DrawScope.measure(measurer: TextMeasurer, index: Int): TextLayoutResult =
        measurer.measure(text = style.glyphs[index].toString(), style = style.textStyle, density = this)

    private fun DrawScope.drawBars(glyph: Int, topLeft: Offset, cell: Float, color: Color, alpha: Float) {
        val width = cell * style.glyphWidth
        val height = cell * style.glyphHeight
        val thickness = height / BAR_SLOTS
        val left = topLeft.x + (cell - width) / 2f
        val top = topLeft.y + (cell - height) / 2f
        val pattern = hashOf(x = glyph)
        for (bar in 0 until BARS) {
            val kind = pattern ushr bar * BAR_BITS and BAR_MASK
            val y = top + (bar * 2 + HALF) * thickness
            val start = left + width * barStarts[kind] + thickness / 2f
            val length = (width * barLengths[kind] - thickness).coerceAtLeast(minimumValue = 0f)
            drawLine(
                color = color,
                start = Offset(x = start, y = y),
                end = Offset(x = start + length, y = y),
                strokeWidth = thickness,
                cap = StrokeCap.Round,
                alpha = alpha,
            )
        }
    }

    private class Rain(
        val cell: Float,
        val rows: Int,
        val seed: Int,
        val changes: Double,
        val trail: List<Color>,
        val head: Color,
    ) {
        fun colorOf(step: Int, trail: Int): Color {
            if (step == 0) return head
            return this.trail[(trail - step).coerceIn(minimumValue = 0, maximumValue = this.trail.size - 1)]
        }
    }
}
