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

package com.davils.kiro.ui.audio.spectrum

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.audio.signal.Samples
import kotlin.math.floor
import kotlin.math.max

internal class HeatMap(private val state: SpectrogramState, private val style: SpectrogramStyle) {
    private val paint = Paint()
    private var image: ImageBitmap? = null
    private var painted: Long = 0L

    fun update(count: Long): ImageBitmap? {
        val newest = (count - 1L).takeIf { latest -> latest >= 0L }?.let(state::columnAt) ?: return null
        val bands = newest.size.coerceAtLeast(minimumValue = 1)
        val current = image?.takeIf { it.height == bands && painted <= count } ?: fresh(bands = bands, count = count)
        val canvas = Canvas(current)
        for (index in max(a = painted, b = count - state.capacity) until count) {
            val column = state.columnAt(index) ?: continue
            paintColumn(canvas = canvas, column = column, x = state.slotOf(index), bands = bands)
        }
        painted = count
        return current
    }

    private fun fresh(bands: Int, count: Long): ImageBitmap {
        val created = ImageBitmap(width = state.capacity, height = bands)
        paint.color = style.ramp.first()
        Canvas(
            created,
        ).drawRect(left = 0f, top = 0f, right = state.capacity.toFloat(), bottom = bands.toFloat(), paint = paint)
        image = created
        painted = max(a = 0L, b = count - state.capacity)
        return created
    }

    private fun paintColumn(canvas: Canvas, column: Samples, x: Int, bands: Int) {
        val left = x.toFloat()
        for (row in 0 until bands) {
            val band = (bands - 1 - row) * column.size / bands
            val level = column[band.coerceIn(minimumValue = 0, maximumValue = column.size - 1)]
            paint.color = rampAt(fraction = style.range.fractionOf(level = level), ramp = style.ramp)
            canvas.drawRect(left = left, top = row.toFloat(), right = left + 1f, bottom = row + 1f, paint = paint)
        }
    }
}

internal fun rampAt(fraction: Float, ramp: List<Color>): Color {
    val position = fraction.coerceIn(minimumValue = 0f, maximumValue = 1f) * (ramp.size - 1)
    val index = floor(position).toInt()
    val next = ramp.getOrNull(index + 1) ?: return ramp[index]
    return lerp(start = ramp[index], stop = next, fraction = position - index)
}
