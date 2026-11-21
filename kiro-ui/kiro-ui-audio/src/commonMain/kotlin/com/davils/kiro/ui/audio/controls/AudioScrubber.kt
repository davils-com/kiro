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

package com.davils.kiro.ui.audio.controls

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.progressOf
import com.davils.kiro.ui.audio.springing
import com.davils.kiro.ui.core.content.LocalContentColor
import kotlin.time.Duration

private const val IDLE: Float = -1f

private val stepKeys: Map<Key, Int> = mapOf(Key.DirectionRight to 1, Key.DirectionLeft to -1)
private val hoverEvents: Map<PointerEventType, Boolean> = mapOf(
    PointerEventType.Enter to true,
    PointerEventType.Exit to false,
)

/**
 * The bar that shows the position in the current track and how far it is loaded, and seeks by a tap, a drag, the
 * arrow keys and the actions of accessibility. While it is hovered or dragged it previews the time under the pointer.
 *
 * @param state the state of the player.
 * @param onAction receives the [AudioAction.Seek] actions.
 * @param modifier the modifier of the scrubber.
 * @param accent the color of the played part and of the thumb.
 * @param style the style of the audio controls.
 * @since 1.0.0
 */
@Composable
public fun AudioScrubber(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    modifier: Modifier = Modifier,
    accent: Color = LocalContentColor.current,
    style: AudioControlsStyle = audioControlsStyle(),
) {
    val latest by rememberUpdatedState(onAction)
    val duration = state.track?.duration ?: Duration.ZERO
    var isActive by remember { mutableStateOf(false) }
    val dragged = remember { mutableFloatStateOf(IDLE) }
    val hovered = remember { mutableFloatStateOf(IDLE) }
    val thumb = animateDpAsState(
        targetValue = style.thumbActiveSize.takeIf { isActive } ?: style.thumbSize,
        animationSpec = style.motion.springing(),
    )
    val seekTo = { fraction: Float -> latest(seekOf(duration = duration, fraction = fraction)) }
    val hovering = Modifier.pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                hoverEvents[event.type]?.let { isOver -> isActive = isOver }
                hovered.floatValue = fractionOf(event = event, width = size.width.toFloat())
            }
        }
    }
    val gestures = gesturesOf(duration = duration, dragged = dragged, seekTo = seekTo)
    val keys = Modifier.onKeyEvent { event ->
        val step = stepKeys[event.key]?.takeIf { event.type == KeyEventType.KeyDown }
        step?.let { direction -> latest(AudioAction.Seek(state.position + style.seekStep * direction)) }
        step != null
    }
    val label = style.labels.seek
    val measurer = rememberTextMeasurer()
    val described = Modifier.semantics {
        contentDescription = label
        progressBarRangeInfo = ProgressBarRangeInfo(current = state.progress, range = 0f..1f)
        setProgress { target ->
            seekTo(target)
            true
        }
    }
    val drawing = trackDrawing(
        state = state,
        marks = ScrubberMarks(dragged = dragged, hovered = hovered, thumb = thumb),
        colors = accent to LocalContentColor.current,
        preview = { time, look -> measurer.measure(text = style.time(time), style = look) },
        style = style,
    )
    Box(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(style.thumbActiveSize)
                .pointerHoverIcon(PointerIcon.Hand)
                .then(hovering)
                .then(gestures)
                .then(keys)
                .focusable()
                .then(described)
                .then(drawing),
        ),
    )
}

private fun gesturesOf(duration: Duration, dragged: MutableFloatState, seekTo: (Float) -> Unit): Modifier {
    val tapping = Modifier.pointerInput(duration) { detectTapGestures { offset -> seekTo(offset.x / size.width) } }
    val dragging = Modifier.pointerInput(duration) {
        detectHorizontalDragGestures(
            onDragStart = { offset -> dragged.floatValue = offset.x / size.width },
            onDragEnd = {
                seekTo(dragged.floatValue)
                dragged.floatValue = IDLE
            },
            onDragCancel = { dragged.floatValue = IDLE },
        ) { change, amount ->
            change.consume()
            val moved = dragged.floatValue + amount / size.width
            dragged.floatValue = moved.coerceIn(minimumValue = 0f, maximumValue = 1f)
        }
    }
    return tapping.then(dragging)
}

private fun seekOf(duration: Duration, fraction: Float): AudioAction {
    val clamped = fraction.coerceIn(minimumValue = 0f, maximumValue = 1f)
    return AudioAction.Seek(duration * clamped.toDouble())
}

private fun fractionOf(event: PointerEvent, width: Float): Float {
    val change = event.changes.firstOrNull()
    if (change == null || event.type == PointerEventType.Exit) return IDLE
    return change.position.x / width
}

private fun trackDrawing(
    state: AudioPlayerState,
    marks: ScrubberMarks,
    colors: Pair<Color, Color>,
    preview: (Duration, TextStyle) -> TextLayoutResult,
    style: AudioControlsStyle,
): Modifier = Modifier.drawBehind {
    val dragged = marks.dragged.floatValue
    val progress = dragged.takeIf { it >= 0f } ?: state.progress
    val duration = state.track?.duration ?: Duration.ZERO
    drawTrack(
        progress = progress,
        buffered = progressOf(position = state.buffered, duration = duration),
        thumb = marks.thumb.value.toPx(),
        colors = colors,
        style = style,
    )
    val pointed = dragged.takeIf { it >= 0f } ?: marks.hovered.floatValue
    if (pointed >= 0f) {
        val label = style.timeStyle.copy(color = colors.second)
        drawPreview(layout = preview(duration * pointed.toDouble(), label), at = pointed)
    }
}

private fun DrawScope.drawPreview(layout: TextLayoutResult, at: Float) {
    val room = (size.width - layout.size.width).coerceAtLeast(0f)
    val x = (size.width * at - layout.size.width / 2f).coerceIn(minimumValue = 0f, maximumValue = room)
    drawText(textLayoutResult = layout, topLeft = Offset(x = x, y = -layout.size.height.toFloat()))
}

private fun DrawScope.drawTrack(
    progress: Float,
    buffered: Float,
    thumb: Float,
    colors: Pair<Color, Color>,
    style: AudioControlsStyle,
) {
    val bar = style.trackHeight.toPx()
    val top = Offset(x = 0f, y = (size.height - bar) / 2f)
    val radius = CornerRadius(bar / 2f)
    val (accent, track) = colors
    drawRoundRect(
        color = track.copy(alpha = style.trackAlpha),
        topLeft = top,
        size = Size(width = size.width, height = bar),
        cornerRadius = radius,
    )
    drawRoundRect(
        color = track.copy(alpha = style.bufferAlpha),
        topLeft = top,
        size = Size(width = size.width * buffered, height = bar),
        cornerRadius = radius,
    )
    drawRoundRect(
        color = accent,
        topLeft = top,
        size = Size(width = size.width * progress, height = bar),
        cornerRadius = radius,
    )
    drawCircle(color = accent, radius = thumb / 2f, center = Offset(x = size.width * progress, y = size.height / 2f))
}
