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

import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer

internal class AnalyzerTexts(val frequencies: List<TextLayoutResult>, val decibels: List<TextLayoutResult>) {
    val lineHeight: Float = (frequencies + decibels).maxOfOrNull { text -> text.size.height }?.toFloat() ?: 0f

    val decibelWidth: Float = decibels.maxOfOrNull { text -> text.size.width }?.toFloat() ?: 0f

    val lastFrequencyWidth: Float = frequencies.lastOrNull()?.run { size.width.toFloat() } ?: 0f

    companion object {
        fun of(measurer: TextMeasurer, style: SpectrumAnalyzerStyle): AnalyzerTexts {
            val look = style.labelStyle.copy(color = style.labelColor)
            return AnalyzerTexts(
                frequencies = style.frequencyMarks.map { mark ->
                    measurer.measure(
                        text = style.frequency(mark),
                        style = look,
                    )
                },
                decibels = style.decibelMarks.map { mark ->
                    measurer.measure(
                        text = style.decibels(mark),
                        style = look,
                    )
                },
            )
        }
    }
}
