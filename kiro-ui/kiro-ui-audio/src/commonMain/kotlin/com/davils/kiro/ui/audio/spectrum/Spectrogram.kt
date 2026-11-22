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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.davils.kiro.ui.audio.signal.FrequencyRange
import kotlin.math.roundToInt

/**
 * A spectrogram as restoration and editing programs show it: the history of the bands of a signal as a heat map that
 * scrolls from right to left, the newest spectrum at the right edge, its levels colored along a ramp from quiet to
 * loud, with the frequencies named on the left.
 *
 * @param state the history to show; push a spectrum into it whenever the caller measured one.
 * @param modifier the modifier of the spectrogram.
 * @param style the style of the spectrogram.
 * @since 1.0.0
 */
@Composable
public fun Spectrogram(
    state: SpectrogramState,
    modifier: Modifier = Modifier,
    style: SpectrogramStyle = spectrogramStyle(),
) {
    val heatMap = remember(state, style) { HeatMap(state = state, style = style) }
    val measurer = rememberTextMeasurer()
    val labels = remember(style, measurer) {
        val look = style.labelStyle.copy(color = style.labelColor)
        style.frequencyMarks.map { mark -> measurer.measure(text = style.frequency(mark), style = look) }
    }
    Canvas(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(style.height)
                .semantics { contentDescription = style.description },
        ),
    ) {
        val count = state.count
        val left = (labels.maxOfOrNull { label -> label.size.width } ?: 0) + style.labelGap.toPx()
        val area = RoundRect(
            left = left,
            top = 0f,
            right = size.width,
            bottom = size.height,
            cornerRadius = CornerRadius(style.radius.toPx()),
        )
        val outline = Path()
        outline.addRoundRect(area)
        clipPath(outline) {
            drawRect(
                color = style.ramp.first(),
                topLeft = Offset(x = left, y = 0f),
                size = size.copy(width = size.width - left),
            )
            val image = heatMap.update(count)
            if (image != null) drawRing(image = image, count = count, left = left, capacity = state.capacity)
        }
        val range = state.range
        if (range != null) drawMarks(range = range, labels = labels, left = left, style = style)
    }
}

private fun DrawScope.drawMarks(
    range: FrequencyRange,
    labels: List<TextLayoutResult>,
    left: Float,
    style: SpectrogramStyle,
) {
    style.frequencyMarks.forEachIndexed { index, frequency ->
        val fraction = range.fractionOf(frequency)
        if (fraction in 0f..1f) {
            val y = size.height * (1f - fraction)
            drawLine(
                color = style.gridColor,
                start = Offset(x = left, y = y),
                end = Offset(x = size.width, y = y),
                strokeWidth = style.gridWidth.toPx(),
            )
            val label = labels[index]
            val room = (size.height - label.size.height).coerceAtLeast(minimumValue = 0f)
            val top = (y - label.size.height / 2f).coerceIn(minimumValue = 0f, maximumValue = room)
            drawText(label, topLeft = Offset(x = 0f, y = top))
        }
    }
}

private fun DrawScope.drawRing(image: ImageBitmap, count: Long, left: Float, capacity: Int) {
    val next = (count % capacity).toInt()
    val width = size.width - left
    val older = capacity - next
    val split = (left + width * older / capacity).roundToInt()
    val height = size.height.roundToInt()
    drawImage(
        image = image,
        srcOffset = IntOffset(x = next, y = 0),
        srcSize = IntSize(width = older, height = image.height),
        dstOffset = IntOffset(x = left.roundToInt(), y = 0),
        dstSize = IntSize(width = split - left.roundToInt(), height = height),
        filterQuality = FilterQuality.Low,
    )
    if (next == 0) return
    drawImage(
        image = image,
        srcOffset = IntOffset(x = 0, y = 0),
        srcSize = IntSize(width = next, height = image.height),
        dstOffset = IntOffset(x = split, y = 0),
        dstSize = IntSize(width = size.width.roundToInt() - split, height = height),
        filterQuality = FilterQuality.Low,
    )
}
