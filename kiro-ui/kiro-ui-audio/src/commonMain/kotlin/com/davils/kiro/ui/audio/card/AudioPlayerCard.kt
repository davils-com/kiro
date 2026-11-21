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

package com.davils.kiro.ui.audio.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.controls.AudioCover
import com.davils.kiro.ui.audio.controls.AudioTimeline
import com.davils.kiro.ui.audio.controls.CoverBackdrop
import com.davils.kiro.ui.audio.controls.TrackInfo
import com.davils.kiro.ui.audio.controls.TransportControls
import com.davils.kiro.ui.core.content.LocalContentColor

/**
 * A card player on the blurred backdrop of the cover: the cover, the track, the timeline and the playback stacked
 * and centered.
 *
 * @param state the state of the player.
 * @param onAction receives the actions of the controls; pass [AudioPlayerState.apply] to handle them in memory.
 * @param modifier the modifier of the card.
 * @param style the style of the card.
 * @param controlsStyle the style of the controls on the card.
 * @param cover the cover on top, the [AudioCover] of the current track by default.
 * @since 1.0.0
 */
@Composable
public fun AudioPlayerCard(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    modifier: Modifier = Modifier,
    style: AudioPlayerCardStyle = audioPlayerCardStyle(),
    controlsStyle: AudioControlsStyle = audioControlsStyle(),
    cover: @Composable (Modifier) -> Unit = { coverModifier ->
        AudioCover(track = state.track, isPlaying = state.isPlaying, modifier = coverModifier, style = controlsStyle)
    },
) {
    CoverBackdrop(
        track = state.track,
        modifier = modifier.width(style.width).clip(style.shape),
        style = controlsStyle,
    ) {
        Column(
            modifier = Modifier.padding(style.padding),
            verticalArrangement = Arrangement.spacedBy(style.gap),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            cover(Modifier.fillMaxWidth().aspectRatio(1f))
            TrackInfo(state = state, modifier = Modifier.fillMaxWidth(), isCentered = true, style = controlsStyle)
            val accent = LocalContentColor.current
            AudioTimeline(state = state, onAction = onAction, accent = accent, style = controlsStyle)
            TransportControls(state = state, onAction = onAction, accent = accent, style = controlsStyle)
        }
    }
}
