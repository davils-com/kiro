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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import com.davils.kiro.ui.audio.signal.AudioSpectrum
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.audio.signal.Samples
import com.davils.kiro.ui.audio.smoothThrough
import com.davils.kiro.ui.audio.stepsThrough
import com.davils.kiro.ui.audio.straightThrough
import kotlin.math.roundToInt

/**
 * A spectrum analyzer as equalizers and editing programs show it: the level of each band of a signal as a curve over
 * a gradient, on a grid of frequencies and levels, with the curve of the held peaks above it. Under the pointer a
 * crosshair shows the frequency and the level of the band below it.
 *
 * Kiro analyzes nothing: the caller hands in the bands it measured, smoothed and held.
 *
 * @param spectrum the levels of the bands to show.
 * @param modifier the modifier of the analyzer.
 * @param style the style of the analyzer.
 * @since 1.0.0
 */
@Composable
public fun SpectrumAnalyzer(
    spectrum: AudioSpectrum,
    modifier: Modifier = Modifier,
    style: SpectrumAnalyzerStyle = spectrumAnalyzerStyle(),
) {
    val pointer = remember { mutableStateOf<Offset?>(null) }
    val measurer = rememberTextMeasurer()
    val texts = remember(style, measurer) { AnalyzerTexts.of(measurer = measurer, style = style) }
    Canvas(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(style.height)
                .then(hoverOf(pointer))
                .semantics { contentDescription = style.description },
        ),
    ) {
        val gap = style.labelGap.toPx()
        val plot = Rect(
            left = texts.decibelWidth + gap,
            top = texts.lineHeight / 2f,
            right = size.width - texts.lastFrequencyWidth / 2f,
            bottom = size.height - texts.lineHeight - gap,
        )
        drawGrid(spectrum = spectrum, plot = plot, texts = texts, style = style)
        val points = pointsOf(magnitudes = spectrum.magnitudes, plot = plot, style = style)
        drawBands(points = points, plot = plot, style = style)
        spectrum.holds?.let { holds ->
            val held = pathOf(
                points = pointsOf(magnitudes = holds, plot = plot, style = style),
                plot = plot,
                style = style,
            )
            drawPath(path = held, color = style.holdColor, style = Stroke(width = style.holdWidth.toPx()))
        }
        pointer.value?.takeIf { plot.contains(it) }?.let { at ->
            drawCrosshair(at = at, plot = plot, spectrum = spectrum, measurer = measurer, style = style)
        }
    }
}

private fun hoverOf(pointer: MutableState<Offset?>): Modifier = Modifier.pointerInput(pointer) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            val position = event.changes.firstOrNull()?.position
            pointer.value = position.takeIf { event.type != PointerEventType.Exit }
        }
    }
}

private fun DrawScope.drawGrid(
    spectrum: AudioSpectrum,
    plot: Rect,
    texts: AnalyzerTexts,
    style: SpectrumAnalyzerStyle,
) {
    val width = style.gridWidth.toPx()
    val gap = style.labelGap.toPx()
    style.frequencyMarks.forEachIndexed { index, frequency ->
        val fraction = spectrum.range.fractionOf(frequency)
        if (fraction in 0f..1f) {
            val x = plot.left + plot.width * fraction
            drawLine(
                color = style.gridColor,
                start = Offset(x = x, y = plot.top),
                end = Offset(x = x, y = plot.bottom),
                strokeWidth = width,
            )
            val label = texts.frequencies[index]
            drawText(label, topLeft = Offset(x = x - label.size.width / 2f, y = plot.bottom + gap))
        }
    }
    style.decibelMarks.forEachIndexed { index, level ->
        val y = plot.bottom - plot.height * style.range.fractionOf(level)
        drawLine(
            color = style.gridColor,
            start = Offset(x = plot.left, y = y),
            end = Offset(x = plot.right, y = y),
            strokeWidth = width,
        )
        val label = texts.decibels[index]
        drawText(label, topLeft = Offset(x = plot.left - gap - label.size.width, y = y - label.size.height / 2f))
    }
}

private fun pointsOf(magnitudes: Samples, plot: Rect, style: SpectrumAnalyzerStyle): List<Offset> {
    val step = plot.width / (magnitudes.size - 1).coerceAtLeast(minimumValue = 1)
    return (0 until magnitudes.size).map { index ->
        val level = style.range.fractionOf(Decibels(magnitudes[index]))
        Offset(x = plot.left + step * index, y = plot.bottom - plot.height * level)
    }
}

private fun pathOf(points: List<Offset>, plot: Rect, style: SpectrumAnalyzerStyle): Path {
    val path = Path()
    when (style.curve) {
        SpectrumCurve.Smooth -> path.smoothThrough(points)

        SpectrumCurve.Linear -> path.straightThrough(points)

        SpectrumCurve.Steps -> path.stepsThrough(
            points = points,
            width = plot.width / points.size.coerceAtLeast(minimumValue = 1),
        )
    }
    return path
}

private fun DrawScope.drawBands(points: List<Offset>, plot: Rect, style: SpectrumAnalyzerStyle) {
    if (points.isEmpty()) return
    val line = pathOf(points = points, plot = plot, style = style)
    val area = Path()
    area.addPath(line)
    area.lineTo(x = plot.right, y = plot.bottom)
    area.lineTo(x = plot.left, y = plot.bottom)
    area.close()
    val fill = Brush.verticalGradient(
        colors = listOf(
            style.lineColor.copy(alpha = style.fillTopAlpha),
            style.lineColor.copy(alpha = style.fillBottomAlpha),
        ),
        startY = plot.top,
        endY = plot.bottom,
    )
    clipRect(left = plot.left, top = plot.top, right = plot.right, bottom = plot.bottom) {
        drawPath(path = area, brush = fill)
        drawPath(
            path = line,
            color = style.lineColor,
            style = Stroke(width = style.lineWidth.toPx(), join = StrokeJoin.Round),
        )
    }
}

private fun DrawScope.drawCrosshair(
    at: Offset,
    plot: Rect,
    spectrum: AudioSpectrum,
    measurer: TextMeasurer,
    style: SpectrumAnalyzerStyle,
) {
    val width = style.gridWidth.toPx()
    drawLine(
        color = style.crosshairColor,
        start = Offset(x = at.x, y = plot.top),
        end = Offset(x = at.x, y = plot.bottom),
        strokeWidth = width,
    )
    drawLine(
        color = style.crosshairColor,
        start = Offset(x = plot.left, y = at.y),
        end = Offset(x = plot.right, y = at.y),
        strokeWidth = width,
    )
    val fraction = (at.x - plot.left) / plot.width
    val bands = spectrum.magnitudes.size
    if (bands == 0) return
    val band = (fraction * (bands - 1)).roundToInt().coerceIn(minimumValue = 0, maximumValue = bands - 1)
    val text = style.readout(spectrum.range.frequencyAt(fraction), Decibels(spectrum.magnitudes[band]))
    val layout = measurer.measure(text = text, style = style.readoutStyle.copy(color = style.readoutColor))
    val padding = style.readoutPadding.toPx()
    val box = Size(width = layout.size.width + padding * 2f, height = layout.size.height + padding * 2f)
    val x = (at.x + padding).coerceAtMost(maximumValue = plot.right - box.width)
    val y = (at.y - box.height - padding).coerceAtLeast(minimumValue = plot.top)
    drawRoundRect(
        color = style.readoutContainer,
        topLeft = Offset(x = x, y = y),
        size = box,
        cornerRadius = CornerRadius(style.readoutRadius.toPx()),
    )
    drawText(layout, topLeft = Offset(x = x + padding, y = y + padding))
}
