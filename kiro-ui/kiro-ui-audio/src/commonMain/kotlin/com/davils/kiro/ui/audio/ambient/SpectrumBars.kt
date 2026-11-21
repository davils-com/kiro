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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.audio.signal.AudioSpectrum
import kotlin.math.floor

/**
 * Spectrum bars as the visualizers on many desktops show them: a bar per band that jumps with the music, colored along
 * a gradient, with a cap that floats on the held peak of each band and falls back as the caller lets it, and a faded
 * reflection below the bars. The bars either grow from the bottom or from the middle in both directions.
 *
 * Kiro analyzes nothing: each band of [spectrum] becomes one bar, and the caller decides how many bands there are and
 * how they rise and fall.
 *
 * @param spectrum the levels and held peaks of the bands to show.
 * @param modifier the modifier of the bars.
 * @param style the style of the bars.
 * @since 1.0.0
 */
@Composable
public fun SpectrumBars(
    spectrum: AudioSpectrum,
    modifier: Modifier = Modifier,
    style: SpectrumBarsStyle = spectrumBarsStyle(),
) {
    Canvas(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(style.height)
                .semantics { contentDescription = style.description },
        ),
    ) {
        drawSpectrum(spectrum = spectrum, style = style)
    }
}

private fun DrawScope.drawSpectrum(spectrum: AudioSpectrum, style: SpectrumBarsStyle) {
    if (spectrum.magnitudes.isEmpty()) return
    when (style.baseline) {
        Baseline.Bottom -> {
            val reflection = size.height * style.reflection.coerceIn(minimumValue = 0f, maximumValue = 1f)
            val bars = size.height - reflection
            val fill = Brush.verticalGradient(colors = style.colors.gradient(), startY = bars, endY = 0f)
            drawBars(spectrum = spectrum, base = bars, reach = bars, brush = fill, style = style)
            if (reflection > 0f) {
                drawReflection(
                    spectrum = spectrum,
                    base = bars,
                    reflection = reflection,
                    style = style,
                )
            }
        }

        Baseline.Center -> {
            val middle = size.height / 2f
            val colors = style.colors.gradient()
            val fill = Brush.verticalGradient(
                colors = colors.reversed() + colors.drop(1),
                startY = 0f,
                endY = size.height,
            )
            drawBars(spectrum = spectrum, base = middle, reach = middle, brush = fill, style = style)
            scale(scaleX = 1f, scaleY = -1f, pivot = Offset(x = 0f, y = middle)) {
                drawBars(spectrum = spectrum, base = middle, reach = middle, brush = fill, style = style)
            }
        }
    }
}

private fun List<Color>.gradient(): List<Color> = takeIf { size > 1 } ?: this + this

private fun DrawScope.drawReflection(
    spectrum: AudioSpectrum,
    base: Float,
    reflection: Float,
    style: SpectrumBarsStyle,
) {
    val fade = Brush.verticalGradient(
        colors = listOf(style.colors.first().copy(alpha = style.reflectionAlpha), Color.Transparent),
        startY = base,
        endY = base + reflection,
    )
    scale(scaleX = 1f, scaleY = -1f, pivot = Offset(x = 0f, y = base)) {
        drawBars(spectrum = spectrum, base = base, reach = base, brush = fade, style = style, withCaps = false)
    }
}

private fun DrawScope.drawBars(
    spectrum: AudioSpectrum,
    base: Float,
    reach: Float,
    brush: Brush,
    style: SpectrumBarsStyle,
    withCaps: Boolean = true,
) {
    val bands = spectrum.magnitudes.size
    val slot = size.width / bands
    val width = slot * (1f - style.gap.coerceIn(minimumValue = 0f, maximumValue = 1f))
    val radius = CornerRadius(style.radius.toPx())
    for (band in 0 until bands) {
        val level = levelOf(value = spectrum.magnitudes[band], style = style)
        val height = reach * level
        val left = slot * band + (slot - width) / 2f
        when (style.look) {
            BarLook.Solid -> drawRoundRect(
                brush = brush,
                topLeft = Offset(x = left, y = base - height),
                size = Size(width = width, height = height),
                cornerRadius = radius,
            )

            BarLook.Segmented -> drawBlocks(
                left = left,
                width = width,
                base = base,
                height = height,
                brush = brush,
                style = style,
            )
        }
        val hold = spectrum.holds?.get(band)
        if (withCaps && hold != null && style.capHeight.toPx() > 0f) {
            val top = base - reach * levelOf(value = hold, style = style) - style.capGap.toPx() - style.capHeight.toPx()
            drawRoundRect(
                color = style.capColor,
                topLeft = Offset(x = left, y = top),
                size = Size(width = width, height = style.capHeight.toPx()),
                cornerRadius = radius,
            )
        }
    }
}

private fun DrawScope.drawBlocks(
    left: Float,
    width: Float,
    base: Float,
    height: Float,
    brush: Brush,
    style: SpectrumBarsStyle,
) {
    val block = style.segment.toPx().coerceAtLeast(minimumValue = 1f)
    val pitch = block + style.segmentGap.toPx()
    val fitting = floor((height + style.segmentGap.toPx()) / pitch).toInt()
    val count = fitting.coerceAtLeast(minimumValue = 1)
    val radius = CornerRadius(style.radius.toPx())
    for (index in 0 until count) {
        drawRoundRect(
            brush = brush,
            topLeft = Offset(x = left, y = base - index * pitch - block),
            size = Size(width = width, height = block),
            cornerRadius = radius,
        )
    }
}

private fun levelOf(value: Float, style: SpectrumBarsStyle): Float {
    val rest = style.rest.coerceIn(minimumValue = 0f, maximumValue = 1f)
    return rest + (1f - rest) * style.range.fractionOf(level = value)
}
