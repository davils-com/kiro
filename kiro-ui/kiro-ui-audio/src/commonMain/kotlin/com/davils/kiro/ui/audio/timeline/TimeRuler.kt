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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

/**
 * The ruler above the tracks of a timeline: major ticks with a timecode, minor ticks between them, both spaced to suit
 * the zoom, and the head of the playhead. A tap or a drag moves the playhead.
 *
 * @param state the state of the timeline, whose zoom, scroll position and playhead the ruler shows.
 * @param onAction receives the [TimelineAction.Seek] actions.
 * @param modifier the modifier of the ruler.
 * @param style the style of the ruler.
 * @since 1.0.0
 */
@Composable
public fun TimeRuler(
    state: TimelineState,
    onAction: (TimelineAction) -> Unit,
    modifier: Modifier = Modifier,
    style: TimeRulerStyle = timeRulerStyle(),
) {
    val act by rememberUpdatedState(onAction)
    val measurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(style.height)
                .pointerHoverIcon(PointerIcon.Hand)
                .then(seekingOf(state = state, act = { action -> act(action) }))
                .semantics { contentDescription = style.description },
        ),
    ) {
        drawRect(color = style.background)
        val perSecond = state.zoom * density
        drawTicks(scroll = state.scroll, perSecond = perSecond, measurer = measurer, style = style)
        drawHead(x = xOf(time = state.playhead - state.scroll, perSecond = perSecond), style = style)
    }
}

private fun seekingOf(state: TimelineState, act: (TimelineAction) -> Unit): Modifier {
    val tapping = Modifier.pointerInput(state) {
        detectTapGestures { offset -> act(TimelineAction.Seek(timeAt(state = state, x = offset.x, density = density))) }
    }
    val dragging = Modifier.pointerInput(state) {
        detectHorizontalDragGestures { change, _ ->
            change.consume()
            act(TimelineAction.Seek(timeAt(state = state, x = change.position.x, density = density)))
        }
    }
    return tapping.then(dragging)
}

internal fun timeAt(state: TimelineState, x: Float, density: Float): Duration =
    state.scroll + (x / (state.zoom * density)).toDouble().seconds

internal fun xOf(time: Duration, perSecond: Float): Float = (time.toDouble(DurationUnit.SECONDS) * perSecond).toFloat()

private fun DrawScope.drawTicks(scroll: Duration, perSecond: Float, measurer: TextMeasurer, style: TimeRulerStyle) {
    val spacing = style.minimumSpacing.toPx()
    val interval = style.intervals.firstOrNull { interval -> xOf(time = interval, perSecond = perSecond) >= spacing }
        ?: style.intervals.lastOrNull()
        ?: return
    val step = interval.toDouble(DurationUnit.SECONDS)
    val first = floor(scroll.toDouble(DurationUnit.SECONDS) / step).toLong()
    val count = ceil(size.width / xOf(time = interval, perSecond = perSecond)).toLong() + 1L
    val look = style.labelStyle.copy(color = style.labelColor)
    for (index in first..first + count) {
        val time = (step * index).seconds
        val x = xOf(time = time - scroll, perSecond = perSecond)
        drawTick(x = x, length = style.majorTick.toPx(), style = style)
        val label = measurer.measure(text = style.time(time), style = look)
        drawText(label, topLeft = Offset(x = x + style.labelPadding.toPx(), y = 0f))
        for (minor in 1..style.minorCount) {
            val offset = xOf(time = interval, perSecond = perSecond) * minor / (style.minorCount + 1)
            drawTick(x = x + offset, length = style.minorTick.toPx(), style = style)
        }
    }
}

private fun DrawScope.drawTick(x: Float, length: Float, style: TimeRulerStyle) {
    drawLine(
        color = style.tickColor,
        start = Offset(x = x, y = size.height - length),
        end = Offset(x = x, y = size.height),
        strokeWidth = style.tickWidth.toPx(),
    )
}

private fun DrawScope.drawHead(x: Float, style: TimeRulerStyle) {
    val half = style.playheadSize.toPx() / 2f
    val head = Path()
    head.moveTo(x = x - half, y = size.height - half * 2f)
    head.lineTo(x = x + half, y = size.height - half * 2f)
    head.lineTo(x = x, y = size.height)
    head.close()
    drawPath(path = head, color = style.playheadColor)
}
