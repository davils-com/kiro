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

package com.davils.kiro.ui.audio.waveform

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.audio.signal.WaveformChannel
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

internal class Envelope(
    val lows: FloatArray,
    val highs: FloatArray,
    val rms: FloatArray,
    val pitch: Float,
    val thickness: Float,
) {
    companion object {
        val Empty: Envelope = Envelope(
            lows = FloatArray(size = 0),
            highs = FloatArray(size = 0),
            rms = FloatArray(size = 0),
            pitch = 1f,
            thickness = 1f,
        )

        fun of(
            channels: List<WaveformChannel>,
            window: ClosedFloatingPointRange<Float>,
            width: Float,
            look: Pair<Float, Float>,
        ): Envelope {
            val buckets = channels.firstOrNull()?.size ?: 0
            val (pitch, thickness) = look
            if (buckets == 0 || width <= 0f || pitch <= 0f) return Empty
            val columns = ceil(width / pitch).toInt()
            val span = window.endInclusive - window.start
            val lows = FloatArray(columns)
            val highs = FloatArray(columns)
            val rms = FloatArray(columns)
            for (column in 0 until columns) {
                val from = window.start + span * (column * pitch / width)
                val to = window.start + span * ((column + 1) * pitch / width)
                val floored = floor(from * buckets).toInt()
                val first = floored.coerceIn(minimumValue = 0, maximumValue = buckets - 1)
                val ceiled = ceil(to * buckets).toInt()
                val last = max(a = first + 1, b = ceiled).coerceAtMost(maximumValue = buckets)
                channels.forEach { channel ->
                    gather(
                        channel = channel,
                        range = first until last,
                        column = column,
                        into = Triple(first = lows, second = highs, third = rms),
                    )
                }
            }
            return Envelope(lows = lows, highs = highs, rms = rms, pitch = pitch, thickness = thickness)
        }

        private fun gather(
            channel: WaveformChannel,
            range: IntRange,
            column: Int,
            into: Triple<FloatArray, FloatArray, FloatArray>,
        ) {
            val (lows, highs, rms) = into
            for (bucket in range) {
                lows[column] = min(a = lows[column], b = channel.minimums[bucket])
                highs[column] = max(a = highs[column], b = channel.maximums[bucket])
                rms[column] = max(a = rms[column], b = channel.rms[bucket])
            }
        }
    }
}

internal fun DrawScope.drawEnvelope(
    envelope: Envelope,
    area: Rect,
    gain: Float,
    colors: Pair<Color, Color>,
    isBars: Boolean,
) {
    val middle = area.center.y
    val half = area.height / 2f
    val (peak, inner) = colors
    val radius = CornerRadius(envelope.thickness / 2f).takeIf { isBars } ?: CornerRadius.Zero
    for (column in envelope.lows.indices) {
        val x = area.left + column * envelope.pitch
        val low = (envelope.lows[column] * gain).coerceIn(minimumValue = -1f, maximumValue = 0f)
        val high = (envelope.highs[column] * gain).coerceIn(minimumValue = 0f, maximumValue = 1f)
        val rms = (envelope.rms[column] * gain).coerceIn(minimumValue = 0f, maximumValue = 1f)
        val reach = max(a = high, b = abs(low))
        val top = middle - (reach.takeIf { isBars } ?: high) * half
        val bottom = middle - ((-reach).takeIf { isBars } ?: low) * half
        val outline = Size(width = envelope.thickness, height = max(a = bottom - top, b = 1f))
        drawRoundRect(color = peak, topLeft = Offset(x = x, y = top), size = outline, cornerRadius = radius)
        val core = Size(width = envelope.thickness, height = rms * half * 2f)
        drawRoundRect(
            color = inner,
            topLeft = Offset(x = x, y = middle - rms * half),
            size = core,
            cornerRadius = radius,
        )
    }
}
