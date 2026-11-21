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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.input.pointer.isMetaPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlin.math.pow
import kotlin.time.Duration.Companion.seconds

/**
 * A timeline as editing programs show it: a ruler with timecodes above the tracks of [state], each with its header
 * and its clips, and a playhead across all of them. The wheel scrolls the timeline and, with the control or command
 * key held, zooms it around the pointer. Everything the user does is only asked for through [onAction]; hand it to
 * [TimelineState.apply] to let the timeline follow.
 *
 * @param state the state of the timeline.
 * @param onAction receives what the user asks the timeline to do.
 * @param modifier the modifier of the timeline.
 * @param style the style of the timeline.
 * @param trackStyle the style of its tracks.
 * @param clipStyle the style of its clips.
 * @param rulerStyle the style of its ruler.
 * @since 1.0.0
 */
@Composable
public fun ClipTimeline(
    state: TimelineState,
    onAction: (TimelineAction) -> Unit,
    modifier: Modifier = Modifier,
    style: ClipTimelineStyle = clipTimelineStyle(),
    trackStyle: ClipTrackStyle = clipTrackStyle(),
    clipStyle: AudioClipStyle = audioClipStyle(),
    rulerStyle: TimeRulerStyle = timeRulerStyle(),
) {
    val act by rememberUpdatedState(onAction)
    val shape = RoundedCornerShape(style.radius)
    val wheeling = wheelOf(
        state = state,
        header = trackStyle.headerWidth,
        style = style,
        act = { action -> act(action) },
    )
    Column(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(color = style.background, shape = shape)
                .border(width = style.borderWidth, color = style.border, shape = shape)
                .then(wheeling)
                .then(playheadOf(state = state, header = trackStyle.headerWidth, style = style))
                .semantics { contentDescription = style.description },
        ),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(trackStyle.headerWidth)
                    .height(rulerStyle.height)
                    .background(rulerStyle.background),
            )
            TimeRuler(
                state = state,
                onAction = onAction,
                modifier = Modifier.weight(weight = 1f, fill = true),
                style = rulerStyle,
            )
        }
        state.lanes.forEach { lane ->
            key(lane.id.value) {
                ClipTrack(lane = lane, state = state, onAction = onAction, style = trackStyle, clipStyle = clipStyle)
            }
        }
    }
}

private fun wheelOf(
    state: TimelineState,
    header: Dp,
    style: ClipTimelineStyle,
    act: (TimelineAction) -> Unit,
): Modifier = Modifier.pointerInput(state, style, header) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            wheel(event = event, state = state, header = header.toPx(), style = style)?.let(act)
        }
    }
}

private fun playheadOf(state: TimelineState, header: Dp, style: ClipTimelineStyle): Modifier =
    Modifier.drawWithContent {
        drawContent()
        val left = header.toPx()
        val x = left + xOf(time = state.playhead - state.scroll, perSecond = state.zoom * density)
        if (x >= left) {
            drawLine(
                color = style.playheadColor,
                start = Offset(x = x, y = 0f),
                end = Offset(x = x, y = size.height),
                strokeWidth = style.playheadWidth.toPx(),
            )
        }
    }

private fun Density.wheel(
    event: PointerEvent,
    state: TimelineState,
    header: Float,
    style: ClipTimelineStyle,
): TimelineAction? {
    val isScroll = event.type == PointerEventType.Scroll
    val change = event.changes.firstOrNull()?.takeIf { isScroll } ?: return null
    change.consume()
    val delta = change.scrollDelta
    val modifiers = event.keyboardModifiers
    val isZooming = modifiers.isCtrlPressed || modifiers.isMetaPressed
    val anchor = timeAt(state = state, x = change.position.x - header, density = density)
    val zoom = TimelineAction.Zoom(zoom = state.zoom * style.zoomStep.pow(-delta.y), anchor = anchor)
    val steps = delta.x.takeIf { it != 0f } ?: delta.y
    val moved = steps * style.scrollStep.toPx() / (state.zoom * density)
    return zoom.takeIf { isZooming } ?: TimelineAction.Scroll(offset = state.scroll + moved.toDouble().seconds)
}
