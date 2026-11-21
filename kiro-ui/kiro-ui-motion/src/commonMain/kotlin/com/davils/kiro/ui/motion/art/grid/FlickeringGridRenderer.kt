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

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.noise.unitOf
import com.davils.kiro.ui.motion.noise.valueNoise
import kotlin.math.floor

private const val CYCLES: Int = 4096
private const val DRIFT_CYCLES: Int = 1024
private const val ACCENT_SALT: Int = 0x3b9aca07
private const val LEVEL_SALT: Int = 0x1b873593

internal class FlickeringGridRenderer(private val style: FlickeringGridArtStyle) : ArtRenderer {
    private val levels = style.levels.coerceAtLeast(minimumValue = 1)
    private val buckets = List(levels * 2) { Path() }
    private val filled = BooleanArray(levels * 2)

    override val hasStatic: Boolean
        get() = true

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        drawRect(color = style.background ?: frame.palette.background)
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        val square = style.square.toPx()
        val layout = flickerLayout(
            width = size.width,
            height = size.height,
            square = square,
            pitch = square + style.gap.toPx(),
            cap = (style.maxCells * frame.quality.density * frame.quality.density).toInt(),
        )
        if (layout.cells.columns == 0) return
        for (bucket in buckets) bucket.rewind()
        filled.fill(element = false)
        val seed = frame.seed.derive(style.salt).value
        val clock = Clock(
            ticks = frame.time.phase(style.period * CYCLES) * CYCLES,
            drift = (frame.time.phase(style.drift * DRIFT_CYCLES) * DRIFT_CYCLES).toFloat(),
            field = style.fieldSize.toPx().coerceAtLeast(minimumValue = 1f),
            seed = seed,
        )
        for (row in 0 until layout.cells.rows) {
            for (column in 0 until layout.cells.columns) {
                addSquare(layout = layout, column = column, row = row, clock = clock)
            }
        }
        drawBuckets(color = style.color ?: frame.palette.ink, accent = style.accent ?: frame.palette.highlight)
    }

    private fun addSquare(layout: FlickerLayout, column: Int, row: Int, clock: Clock) {
        val cells = layout.cells
        val left = cells.xOf(column) + (cells.pitch - layout.square) / 2f
        val top = cells.yOf(row) + (cells.pitch - layout.square) / 2f
        val tick = floor(clock.ticks + unitOf(x = column, y = row, seed = clock.seed)).toInt()
        val flicker = unitOf(x = column, y = row, z = tick, seed = clock.seed + LEVEL_SALT)
        val cloud = valueNoise(x = left / clock.field + clock.drift, y = top / clock.field, seed = clock.seed)
        val level = levelOf(flicker = flicker, cloud = cloud, structure = style.structure, levels = levels)
        val isAccent = unitOf(x = column, y = row, seed = clock.seed + ACCENT_SALT) < style.accentShare
        var index = level
        if (isAccent) index += levels
        val path = buckets[index]
        filled[index] = true
        path.moveTo(x = left, y = top)
        path.lineTo(x = left + layout.square, y = top)
        path.lineTo(x = left + layout.square, y = top + layout.square)
        path.lineTo(x = left, y = top + layout.square)
        path.close()
    }

    private fun DrawScope.drawBuckets(color: Color, accent: Color) {
        for (index in buckets.indices) {
            if (!filled[index]) continue
            val alpha = alphaOf(level = index % levels, levels = levels, from = style.minAlpha, to = style.maxAlpha)
            val tint = tintOf(index = index, color = color, accent = accent)
            drawPath(path = buckets[index], color = tint, alpha = alpha)
        }
    }

    private fun tintOf(index: Int, color: Color, accent: Color): Color {
        if (index < levels) return color
        return accent
    }

    private class Clock(val ticks: Double, val drift: Float, val field: Float, val seed: Int)
}
