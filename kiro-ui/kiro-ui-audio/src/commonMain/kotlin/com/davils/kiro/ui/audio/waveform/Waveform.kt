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

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import com.davils.kiro.ui.audio.signal.WaveformChannel
import com.davils.kiro.ui.audio.signal.WaveformPeaks
import kotlin.time.Duration

private const val IDLE: Float = -1f

private val stepKeys: Map<Key, Int> = mapOf(Key.DirectionRight to 1, Key.DirectionLeft to -1)

/**
 * The waveform of a recording as editing programs and the players of streaming services show it: the outline of its
 * peaks with the root mean square inside it, the played part in its own color, and a playhead. A tap or a drag seeks,
 * the arrow keys step, and under the pointer a line shows the time it points at.
 *
 * Kiro reads no file: the caller hands in the outline it reduced from the samples of the recording.
 *
 * @param peaks the outline of the recording.
 * @param modifier the modifier of the waveform.
 * @param progress the position of the playhead as a share of the recording, between `0` and `1`.
 * @param onSeek called with the position the user seeks to.
 * @param style the style of the waveform.
 * @since 1.0.0
 */
@Composable
public fun Waveform(
    peaks: WaveformPeaks,
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    onSeek: (Duration) -> Unit = {},
    style: WaveformStyle = waveformStyle(),
) {
    val seek by rememberUpdatedState(onSeek)
    val played = rememberUpdatedState(progress.coerceIn(minimumValue = 0f, maximumValue = 1f))
    val hovered = remember { mutableFloatStateOf(IDLE) }
    val measurer = rememberTextMeasurer()
    val seekTo = { fraction: Float ->
        seek(
            peaks.duration * fraction.coerceIn(minimumValue = 0f, maximumValue = 1f).toDouble(),
        )
    }
    val keys = Modifier.onKeyEvent { event ->
        val step = stepKeys[event.key]?.takeIf { event.type == KeyEventType.KeyDown }
        step?.let { direction -> seek(peaks.duration * played.value.toDouble() + style.seekStep * direction) }
        step != null
    }
    val drawing = drawingOf(peaks = peaks, played = played, hovered = hovered, measurer = measurer, style = style)
    Box(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(style.height)
                .pointerHoverIcon(PointerIcon.Hand)
                .then(hoverOf(hovered))
                .then(gesturesOf(peaks = peaks, hovered = hovered, seekTo = seekTo))
                .then(keys)
                .focusable()
                .then(drawing)
                .semantics {
                    contentDescription = style.description
                    progressBarRangeInfo = ProgressBarRangeInfo(current = played.value, range = 0f..1f)
                    setProgress { target ->
                        seekTo(target)
                        true
                    }
                },
        ),
    )
}

private fun hoverOf(hovered: MutableFloatState): Modifier = Modifier.pointerInput(hovered) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            val position = event.changes.firstOrNull()?.run { position.x }
            val isOver = event.type != PointerEventType.Exit && position != null
            hovered.floatValue = (position ?: 0f).div(size.width).takeIf { isOver } ?: IDLE
        }
    }
}

private fun gesturesOf(peaks: WaveformPeaks, hovered: MutableFloatState, seekTo: (Float) -> Unit): Modifier {
    val tapping = Modifier.pointerInput(peaks) { detectTapGestures { offset -> seekTo(offset.x / size.width) } }
    val dragging = Modifier.pointerInput(peaks) {
        detectHorizontalDragGestures { change, _ ->
            change.consume()
            val fraction = change.position.x / size.width
            hovered.floatValue = fraction.coerceIn(minimumValue = 0f, maximumValue = 1f)
            seekTo(fraction)
        }
    }
    return tapping.then(dragging)
}

private fun drawingOf(
    peaks: WaveformPeaks,
    played: State<Float>,
    hovered: MutableFloatState,
    measurer: TextMeasurer,
    style: WaveformStyle,
): Modifier = Modifier.drawWithCache {
    val isBars = style.look == WaveformLook.Bars
    val bar = style.barWidth.toPx()
    val look = Pair(first = bar + style.barGap.toPx(), second = bar).takeIf { isBars } ?: Pair(first = 1f, second = 1f)
    val lanes = lanesOf(peaks = peaks, layout = style.layout)
    val areas = areasOf(count = lanes.size, gap = style.channelGap.toPx())
    val envelopes = lanes.map { channels ->
        Envelope.of(
            channels = channels,
            window = 0f..1f,
            width = size.width,
            look = look,
        )
    }
    onDrawBehind {
        val split = size.width * played.value
        envelopes.forEachIndexed { lane, envelope ->
            drawLane(envelope = envelope, area = areas[lane], split = split, isBars = isBars, style = style)
        }
        drawMarks(
            split = split,
            hovered = hovered.floatValue,
            duration = peaks.duration,
            measurer = measurer,
            style = style,
        )
    }
}

private fun CacheDrawScope.areasOf(count: Int, gap: Float): List<Rect> {
    val lanes = count.coerceAtLeast(minimumValue = 1)
    val height = (size.height - gap * (lanes - 1)) / lanes
    return (0 until lanes).map { lane ->
        val top = (height + gap) * lane
        Rect(left = 0f, top = top, right = size.width, bottom = top + height)
    }
}

private fun DrawScope.drawLane(envelope: Envelope, area: Rect, split: Float, isBars: Boolean, style: WaveformStyle) {
    drawCenter(area = area, style = style)
    clipRect(right = split) {
        drawEnvelope(
            envelope = envelope,
            area = area,
            gain = 1f,
            colors = colorsOf(style.playedColor, style),
            isBars = isBars,
        )
    }
    clipRect(left = split) {
        drawEnvelope(
            envelope = envelope,
            area = area,
            gain = 1f,
            colors = colorsOf(style.unplayedColor, style),
            isBars = isBars,
        )
    }
}

private fun colorsOf(color: Color, style: WaveformStyle): Pair<Color, Color> =
    Pair(first = color.copy(alpha = style.peakAlpha), second = color)

private fun lanesOf(peaks: WaveformPeaks, layout: WaveformLayout): List<List<WaveformChannel>> = when (layout) {
    WaveformLayout.Combined -> listOf(peaks.channels)
    WaveformLayout.Stacked -> peaks.channels.map { channel -> listOf(channel) }
}

private fun DrawScope.drawCenter(area: Rect, style: WaveformStyle) {
    val width = style.centerWidth.toPx()
    if (width <= 0f) return
    drawLine(
        color = style.centerColor,
        start = Offset(x = area.left, y = area.center.y),
        end = Offset(x = area.right, y = area.center.y),
        strokeWidth = width,
    )
}

private fun DrawScope.drawMarks(
    split: Float,
    hovered: Float,
    duration: Duration,
    measurer: TextMeasurer,
    style: WaveformStyle,
) {
    val playhead = style.playheadWidth.toPx()
    if (playhead > 0f) {
        drawLine(
            color = style.playheadColor,
            start = Offset(x = split, y = 0f),
            end = Offset(x = split, y = size.height),
            strokeWidth = playhead,
        )
    }
    if (hovered < 0f) return
    val x = size.width * hovered
    drawLine(
        color = style.hoverColor,
        start = Offset(x = x, y = 0f),
        end = Offset(x = x, y = size.height),
        strokeWidth = style.centerWidth.toPx().coerceAtLeast(minimumValue = 1f),
    )
    val label = measurer.measure(
        text = style.time(duration * hovered.toDouble()),
        style = style.timeStyle.copy(color = style.timeColor),
    )
    val room = (size.width - label.size.width).coerceAtLeast(minimumValue = 0f)
    drawText(label, topLeft = Offset(x = (x + style.barGap.toPx()).coerceAtMost(maximumValue = room), y = 0f))
}
