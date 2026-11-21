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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.Track
import com.davils.kiro.ui.audio.controls.AudioCover
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.scrollarea.ScrollArea
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor

@Composable
internal fun AudioQueue(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    style: AudioPlayerFullStyle,
    controlsStyle: AudioControlsStyle,
) {
    ScrollArea(modifier = Modifier.width(style.queueWidth).fillMaxHeight()) {
        Column(verticalArrangement = Arrangement.spacedBy(style.queueGap)) {
            Text(
                text = controlsStyle.labels.queue,
                style = controlsStyle.titleStyle,
                modifier = Modifier.padding(style.queueRowPadding),
            )
            state.queue.forEach { track ->
                QueueRow(
                    track = track,
                    isCurrent = track.id == state.current,
                    style = style,
                    controlsStyle = controlsStyle,
                ) { onAction(AudioAction.Select(track.id)) }
            }
        }
    }
}

@Composable
private fun QueueRow(
    track: Track,
    isCurrent: Boolean,
    style: AudioPlayerFullStyle,
    controlsStyle: AudioControlsStyle,
    onClick: () -> Unit,
) {
    val content = LocalContentColor.current
    val secondary = content.copy(alpha = controlsStyle.secondaryAlpha)
    val highlight = Modifier.surface(
        shape = style.queueRowShape,
        color = content.copy(alpha = style.activeAlpha).takeIf { isCurrent } ?: Color.Transparent,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(highlight)
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(style.queueRowPadding),
        horizontalArrangement = Arrangement.spacedBy(style.queueRowGap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AudioCover(
            track = track,
            isPlaying = true,
            modifier = Modifier.size(style.queueCoverSize),
            style = controlsStyle,
            shape = style.queueRowShape,
            elevation = style.queueCoverElevation,
        )
        Box(modifier = Modifier.weight(1f)) {
            Column {
                Text(text = track.title, style = controlsStyle.titleStyle, maxLines = 1)
                Text(text = track.artist, style = controlsStyle.artistStyle, color = secondary, maxLines = 1)
            }
        }
        Text(text = controlsStyle.time(track.duration), style = controlsStyle.timeStyle, color = secondary)
    }
}
