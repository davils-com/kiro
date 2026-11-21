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

package com.davils.kiro.ui.audio.bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.controls.AudioCover
import com.davils.kiro.ui.audio.controls.AudioTimeline
import com.davils.kiro.ui.audio.controls.AudioVisualizer
import com.davils.kiro.ui.audio.controls.IconAction
import com.davils.kiro.ui.audio.controls.TrackInfo
import com.davils.kiro.ui.audio.controls.TransportControls
import com.davils.kiro.ui.audio.controls.VolumeControl
import com.davils.kiro.ui.audio.palette.rememberCoverPalette
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor

/**
 * A full-width player bar, as at the bottom of a music application: the cover, the track and a visualizer on the
 * start, the playback and the timeline in the center, the queue button and the volume on the end. The bar and its
 * accent take the colors of the cover.
 *
 * @param state the state of the player.
 * @param onAction receives the actions of the controls; pass [AudioPlayerState.apply] to handle them in memory.
 * @param modifier the modifier of the bar.
 * @param onQueueClick called when the queue button is clicked; without it the button is left out.
 * @param style the style of the bar.
 * @param controlsStyle the style of the controls in the bar.
 * @param cover the cover on the start, the [AudioCover] of the current track by default.
 * @since 1.0.0
 */
@Composable
public fun AudioPlayerBar(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    modifier: Modifier = Modifier,
    onQueueClick: (() -> Unit)? = null,
    style: AudioPlayerBarStyle = audioPlayerBarStyle(),
    controlsStyle: AudioControlsStyle = audioControlsStyle(),
    cover: @Composable (Modifier) -> Unit = { coverModifier ->
        AudioCover(
            track = state.track,
            isPlaying = state.isPlaying,
            modifier = coverModifier,
            style = controlsStyle,
            shape = RoundedCornerShape(style.coverRadius),
            elevation = style.coverElevation,
        )
    },
) {
    val palette = rememberCoverPalette(track = state.track, style = controlsStyle)
    val face = Modifier.surface(
        shape = style.shape,
        color = lerp(start = style.container, stop = palette.dominant, fraction = style.tint),
        border = style.border,
    )
    CompositionLocalProvider(LocalContentColor provides style.content) {
        Row(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .height(style.height)
                    .then(face)
                    .padding(horizontal = style.padding),
            ),
            horizontalArrangement = Arrangement.spacedBy(style.gap),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(style.gap),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                cover(Modifier.size(style.coverSize))
                TrackInfo(state = state, modifier = Modifier.weight(1f, fill = false), style = controlsStyle)
                AudioVisualizer(isPlaying = state.isPlaying, color = palette.vibrant, style = controlsStyle)
            }
            Column(modifier = Modifier.weight(style.centerWeight), horizontalAlignment = Alignment.CenterHorizontally) {
                TransportControls(state = state, onAction = onAction, accent = palette.vibrant, style = controlsStyle)
                AudioTimeline(state = state, onAction = onAction, accent = palette.vibrant, style = controlsStyle)
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(style.gap / 2, Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                onQueueClick?.let { click ->
                    IconAction(
                        icon = controlsStyle.icons.queue,
                        label = controlsStyle.labels.queue,
                        tint = null,
                        style = controlsStyle,
                        onClick = click,
                    )
                }
                VolumeControl(state = state, onAction = onAction, style = controlsStyle, accent = palette.vibrant)
            }
        }
    }
}
