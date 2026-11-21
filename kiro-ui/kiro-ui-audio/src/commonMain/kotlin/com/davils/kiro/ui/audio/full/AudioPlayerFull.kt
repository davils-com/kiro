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

package com.davils.kiro.ui.audio.full

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.controls.AudioCover
import com.davils.kiro.ui.audio.controls.AudioTimeline
import com.davils.kiro.ui.audio.controls.CoverBackdrop
import com.davils.kiro.ui.audio.controls.IconAction
import com.davils.kiro.ui.audio.controls.TrackInfo
import com.davils.kiro.ui.audio.controls.TransportControls
import com.davils.kiro.ui.audio.controls.VolumeControl
import com.davils.kiro.ui.audio.fading
import com.davils.kiro.ui.audio.resizing
import com.davils.kiro.ui.core.content.LocalContentColor

/**
 * A now-playing view on the blurred backdrop of the cover: a large cover with the track, the timeline, the playback
 * and the volume below it, and the queue, which a button shows and hides, beside it.
 *
 * @param state the state of the player.
 * @param onAction receives the actions of the controls; pass [AudioPlayerState.apply] to handle them in memory.
 * @param modifier the modifier of the view; it fills the space it is given.
 * @param isQueueInitiallyShown whether the queue shows at first.
 * @param style the style of the view.
 * @param controlsStyle the style of the controls in the view.
 * @param cover the large cover, the [AudioCover] of the current track by default.
 * @param queue the queue beside the controls, a list of the tracks of the state that selects the clicked one by
 * default.
 * @since 1.0.0
 */
@Composable
public fun AudioPlayerFull(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    modifier: Modifier = Modifier,
    isQueueInitiallyShown: Boolean = false,
    style: AudioPlayerFullStyle = audioPlayerFullStyle(),
    controlsStyle: AudioControlsStyle = audioControlsStyle(),
    cover: @Composable (Modifier) -> Unit = { coverModifier ->
        AudioCover(track = state.track, isPlaying = state.isPlaying, modifier = coverModifier, style = controlsStyle)
    },
    queue: @Composable () -> Unit = {
        AudioQueue(state = state, onAction = onAction, style = style, controlsStyle = controlsStyle)
    },
) {
    var isQueueShown by remember { mutableStateOf(isQueueInitiallyShown) }
    val motion = controlsStyle.motion
    CoverBackdrop(track = state.track, modifier = modifier.fillMaxSize(), style = controlsStyle) {
        Row(modifier = Modifier.fillMaxSize().padding(style.padding)) {
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(style.gap, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(modifier = Modifier.weight(1f, fill = false), contentAlignment = Alignment.Center) {
                    cover(
                        Modifier
                            .sizeIn(maxWidth = style.coverSize, maxHeight = style.coverSize)
                            .aspectRatio(1f, matchHeightConstraintsFirst = true),
                    )
                }
                FullControls(
                    state = state,
                    onAction = onAction,
                    isQueueShown = isQueueShown,
                    style = style,
                    controlsStyle = controlsStyle,
                ) { isQueueShown = !isQueueShown }
            }
            AnimatedVisibility(
                visible = isQueueShown,
                enter = expandHorizontally(motion.resizing()) + fadeIn(motion.fading()),
                exit = shrinkHorizontally(motion.resizing()) + fadeOut(motion.fading()),
            ) {
                queue()
            }
        }
    }
}

@Composable
private fun FullControls(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    isQueueShown: Boolean,
    style: AudioPlayerFullStyle,
    controlsStyle: AudioControlsStyle,
    onQueueToggle: () -> Unit,
) {
    Column(
        modifier = Modifier.width(style.coverSize),
        verticalArrangement = Arrangement.spacedBy(style.controlsGap),
    ) {
        val accent = LocalContentColor.current
        TrackInfo(
            state = state,
            modifier = Modifier.fillMaxWidth(),
            isCentered = true,
            style = controlsStyle,
        )
        AudioTimeline(state = state, onAction = onAction, accent = accent, style = controlsStyle)
        TransportControls(
            state = state,
            onAction = onAction,
            modifier = Modifier.fillMaxWidth(),
            accent = accent,
            style = controlsStyle,
        )
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            VolumeControl(
                state = state,
                onAction = onAction,
                modifier = Modifier.weight(1f),
                style = controlsStyle,
            )
            IconAction(
                icon = controlsStyle.icons.queue,
                label = controlsStyle.labels.queue,
                tint = accent.takeIf { isQueueShown },
                style = controlsStyle,
                onClick = onQueueToggle,
            )
        }
    }
}
