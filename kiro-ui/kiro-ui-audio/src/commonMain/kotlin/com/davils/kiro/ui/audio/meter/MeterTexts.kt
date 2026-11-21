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

package com.davils.kiro.ui.audio.meter

import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.audio.signal.Decibels

private const val WIDEST_READOUT: Float = -88.8f

internal class MeterTexts(
    val label: TextStyle,
    val names: List<TextLayoutResult>,
    val marks: List<TextLayoutResult>,
    val readoutWidth: Float,
) {
    val lineHeight: Float = names.maxOfOrNull { name -> name.size.height }?.toFloat() ?: 0f

    val nameWidth: Float = names.maxOfOrNull { name -> name.size.width }?.toFloat() ?: 0f

    private val markWidth: Float = marks.maxOfOrNull { mark -> mark.size.width }?.toFloat() ?: 0f

    private val markHeight: Float = marks.maxOfOrNull { mark -> mark.size.height }?.toFloat() ?: 0f

    fun Density.sizeOf(isVertical: Boolean, count: Int, style: LevelMeterStyle): Pair<Dp, Dp> {
        val gaps = style.channelGap * (count - 1).coerceAtLeast(minimumValue = 0)
        val scale = style.scaleGap + style.tickLength + style.scaleGap
        if (isVertical) {
            val slot = maxOf(a = style.thickness, b = readoutWidth.toDp())
            return Pair(first = slot * count + gaps + scale + markWidth.toDp(), second = style.length)
        }
        return Pair(first = style.length, second = style.thickness * count + gaps + scale + markHeight.toDp())
    }

    companion object {
        fun of(measurer: TextMeasurer, style: LevelMeterStyle, count: Int): MeterTexts {
            val label = style.labelStyle.copy(color = style.labelColor)
            val scale = style.scaleStyle.copy(color = style.labelColor)
            val names = (0 until count).map { index ->
                measurer.measure(
                    text = style.channel(index, count),
                    style = label,
                )
            }
            val marks = style.marks.map { mark -> measurer.measure(text = style.scaleLabel(mark), style = scale) }
            val widest = measurer.measure(text = style.readout(Decibels(WIDEST_READOUT)), style = label)
            return MeterTexts(label = label, names = names, marks = marks, readoutWidth = widest.size.width.toFloat())
        }
    }
}
