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

package com.davils.kiro.ui.audio.ambient

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.audio.signal.AudioSpectrum
import com.davils.kiro.ui.audio.smoothThrough

/**
 * A spectrum wave as the visualizers on many desktops show it: the bands of a signal as smooth waves that roll with
 * the music, layered in front of each other like a mountain range, each layer a little lower and in a color of its
 * own. The waves either rise from the bottom or spread from the middle in both directions.
 *
 * @param spectrum the levels of the bands to show.
 * @param modifier the modifier of the waves.
 * @param style the style of the waves.
 * @since 1.0.0
 */
@Composable
public fun SpectrumWave(
    spectrum: AudioSpectrum,
    modifier: Modifier = Modifier,
    style: SpectrumWaveStyle = spectrumWaveStyle(),
) {
    Canvas(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(style.height)
                .semantics { contentDescription = style.description },
        ),
    ) {
        drawWaves(spectrum = spectrum, style = style)
    }
}

private fun DrawScope.drawWaves(spectrum: AudioSpectrum, style: SpectrumWaveStyle) {
    if (spectrum.magnitudes.isEmpty()) return
    val base = size.height.takeIf { style.baseline == Baseline.Bottom } ?: size.height / 2f
    for (layer in style.colors.indices.reversed()) {
        val share = (1f - style.falloff * (style.colors.size - 1 - layer)).coerceAtLeast(minimumValue = 0f)
        drawLayer(
            spectrum = spectrum,
            base = base,
            reach = base * share,
            color = style.colors[layer],
            style = style,
        )
        if (style.baseline == Baseline.Center) {
            scale(scaleX = 1f, scaleY = -1f, pivot = Offset(x = 0f, y = base)) {
                drawLayer(
                    spectrum = spectrum,
                    base = base,
                    reach = base * share,
                    color = style.colors[layer],
                    style = style,
                )
            }
        }
    }
}

private fun DrawScope.drawLayer(
    spectrum: AudioSpectrum,
    base: Float,
    reach: Float,
    color: Color,
    style: SpectrumWaveStyle,
) {
    val magnitudes = spectrum.magnitudes
    val step = size.width / (magnitudes.size - 1).coerceAtLeast(minimumValue = 1)
    val rest = style.rest.coerceIn(minimumValue = 0f, maximumValue = 1f)
    val points = (0 until magnitudes.size).map { band ->
        val level = rest + (1f - rest) * style.range.fractionOf(level = magnitudes[band])
        Offset(x = step * band, y = base - reach * level)
    }
    val edge = Path()
    edge.smoothThrough(points)
    val area = Path()
    area.addPath(edge)
    area.lineTo(x = size.width, y = base)
    area.lineTo(x = 0f, y = base)
    area.close()
    drawPath(path = area, color = color.copy(alpha = style.fillAlpha))
    drawPath(
        path = edge,
        color = color.copy(alpha = style.lineAlpha),
        style = Stroke(width = style.lineWidth.toPx(), join = StrokeJoin.Round),
    )
}
