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
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

/**
 * A track of a timeline as editing programs show it: a header with the name of the track and its switches for mute,
 * solo and record, and a lane with its clips placed by the zoom and the scroll position of the timeline. A tap on the
 * empty lane clears the selection.
 *
 * @param lane the track to show.
 * @param state the state of the timeline the track belongs to.
 * @param onAction receives what the user asks the track and its clips to do.
 * @param modifier the modifier of the track.
 * @param style the style of the track.
 * @param clipStyle the style of the clips of the track.
 * @param controls the switches of the header; by default mute, solo and record.
 * @since 1.0.0
 */
@Composable
public fun ClipTrack(
    lane: TrackLane,
    state: TimelineState,
    onAction: (TimelineAction) -> Unit,
    modifier: Modifier = Modifier,
    style: ClipTrackStyle = clipTrackStyle(),
    clipStyle: AudioClipStyle = audioClipStyle(),
    controls: @Composable RowScope.() -> Unit = { LaneToggles(lane = lane, onAction = onAction, style = style) },
) {
    val act by rememberUpdatedState(onAction)
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(style.height)
                .drawBehind {
                    val y = size.height - style.separatorWidth.toPx() / 2f
                    drawLine(
                        color = style.separatorColor,
                        start = Offset(x = 0f, y = y),
                        end = Offset(x = size.width, y = y),
                        strokeWidth = style.separatorWidth.toPx(),
                    )
                },
        ),
    ) {
        TrackHeader(name = lane.name, style = style, controls = controls)
        Box(
            modifier = Modifier
                .weight(weight = 1f, fill = true)
                .fillMaxHeight()
                .background(style.laneColor)
                .clipToBounds()
                .pointerInput(lane.id) { detectTapGestures { act(TimelineAction.Select(null)) } },
        ) {
            lane.clips.forEach { clip ->
                key(clip.id.value) {
                    AudioClip(
                        clip = clip,
                        zoom = state.zoom,
                        onAction = onAction,
                        modifier = Modifier
                            .padding(vertical = style.clipInset)
                            .offset {
                                val x = xOf(time = clip.start - state.scroll, perSecond = state.zoom * density)
                                IntOffset(x = x.roundToInt(), y = 0)
                            },
                        isSelected = state.selection == clip.id,
                        style = clipStyle,
                    )
                }
            }
        }
    }
}

@Composable
private fun TrackHeader(name: String, style: ClipTrackStyle, controls: @Composable RowScope.() -> Unit) {
    Column(
        modifier = Modifier
            .width(style.headerWidth)
            .fillMaxHeight()
            .background(style.headerColor)
            .padding(style.headerPadding),
        verticalArrangement = Arrangement.spacedBy(style.headerGap),
    ) {
        BasicText(
            text = name,
            style = style.nameStyle.copy(color = style.nameColor),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(style.headerGap), content = controls)
    }
}

@Composable
private fun LaneToggles(lane: TrackLane, onAction: (TimelineAction) -> Unit, style: ClipTrackStyle) {
    LaneToggle(
        letter = style.mute,
        label = style.muteLabel,
        isOn = lane.isMuted,
        color = style.muteColor,
        onClick = { onAction(TimelineAction.Mute(lane.id)) },
        style = style,
    )
    LaneToggle(
        letter = style.solo,
        label = style.soloLabel,
        isOn = lane.isSoloed,
        color = style.soloColor,
        onClick = { onAction(TimelineAction.Solo(lane.id)) },
        style = style,
    )
    LaneToggle(
        letter = style.arm,
        label = style.armLabel,
        isOn = lane.isArmed,
        color = style.armColor,
        onClick = { onAction(TimelineAction.Arm(lane.id)) },
        style = style,
    )
}
