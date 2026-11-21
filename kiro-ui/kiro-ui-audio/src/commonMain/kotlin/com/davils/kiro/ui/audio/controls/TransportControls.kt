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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.RepeatMode
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.palette.contentOn
import com.davils.kiro.ui.core.content.LocalContentColor

/**
 * The buttons of the playback: shuffle, previous, play and pause, next and repeat. Shuffle and repeat take the accent
 * while they are on.
 *
 * @param state the state of the player.
 * @param onAction receives the actions of the buttons.
 * @param modifier the modifier of the row of buttons.
 * @param accent the color of shuffle and repeat while they are on.
 * @param isCompact whether shuffle and repeat are left out.
 * @param style the style of the audio controls.
 * @since 1.0.0
 */
@Composable
public fun TransportControls(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    modifier: Modifier = Modifier,
    accent: Color = LocalContentColor.current,
    isCompact: Boolean = false,
    style: AudioControlsStyle = audioControlsStyle(),
) {
    val icons = style.icons
    val labels = style.labels
    val content = LocalContentColor.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(style.gap / 2, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!isCompact) {
            IconAction(
                icon = icons.shuffle,
                label = labels.shuffle,
                tint = accent.takeIf { state.isShuffled },
                style = style,
            ) { onAction(AudioAction.Shuffle) }
        }
        IconAction(icon = icons.previous, label = labels.previous, tint = null, style = style) {
            onAction(AudioAction.Previous)
        }
        PlayPauseButton(
            isPlaying = state.isPlaying,
            onToggle = { onAction(AudioAction.Pause.takeIf { state.isPlaying } ?: AudioAction.Play) },
            style = style,
            container = content,
            content = contentOn(backgrounds = listOf(content), content = style.lightContent to style.darkContent),
        )
        IconAction(icon = icons.next, label = labels.next, tint = null, style = style) {
            onAction(AudioAction.Next)
        }
        if (!isCompact) {
            val icon = icons.repeatOne.takeIf { state.repeat == RepeatMode.One } ?: icons.repeat
            IconAction(
                icon = icon,
                label = labels.repeat,
                tint = accent.takeIf { state.repeat != RepeatMode.Off },
                style = style,
            ) { onAction(AudioAction.Repeat) }
        }
    }
}
