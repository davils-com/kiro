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

package com.davils.kiro.ui.audio.mini

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.controls.AudioCover
import com.davils.kiro.ui.audio.controls.AudioVisualizer
import com.davils.kiro.ui.audio.controls.PlayPauseButton
import com.davils.kiro.ui.audio.controls.TrackInfo
import com.davils.kiro.ui.audio.palette.rememberCoverPalette
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor

private const val FULL_TURN: Float = 360f

/**
 * A small pill player: a round cover, the title, a visualizer and a play button ringed by the progress of the track.
 * The pill and the ring take the colors of the cover.
 *
 * @param state the state of the player.
 * @param onAction receives [AudioAction.Play] and [AudioAction.Pause]; pass [AudioPlayerState.apply] to handle them
 * in memory.
 * @param modifier the modifier of the pill.
 * @param style the style of the pill.
 * @param controlsStyle the style of the controls in the pill.
 * @param cover the round cover on the start, the [AudioCover] of the current track by default.
 * @since 1.0.0
 */
@Composable
public fun AudioPlayerMini(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    modifier: Modifier = Modifier,
    style: AudioPlayerMiniStyle = audioPlayerMiniStyle(),
    controlsStyle: AudioControlsStyle = audioControlsStyle(),
    cover: @Composable (Modifier) -> Unit = { coverModifier ->
        AudioCover(
            track = state.track,
            isPlaying = state.isPlaying,
            modifier = coverModifier,
            style = controlsStyle,
            shape = style.coverShape,
            elevation = style.coverElevation,
        )
    },
) {
    val palette = rememberCoverPalette(track = state.track, style = controlsStyle)
    val inner = style.height - style.padding * 2
    val face = Modifier.surface(
        shape = style.shape,
        color = lerp(start = style.container, stop = palette.dominant, fraction = style.tint),
        border = style.border,
    )
    CompositionLocalProvider(LocalContentColor provides style.content) {
        Row(
            modifier = modifier.then(
                Modifier
                    .widthIn(max = style.maxWidth)
                    .height(style.height)
                    .then(face)
                    .padding(horizontal = style.padding),
            ),
            horizontalArrangement = Arrangement.spacedBy(style.gap),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            cover(Modifier.size(inner))
            TrackInfo(state = state, modifier = Modifier.weight(1f), isArtistShown = false, style = controlsStyle)
            AudioVisualizer(isPlaying = state.isPlaying, color = palette.vibrant, style = controlsStyle)
            PlayWithProgress(
                state = state,
                onAction = onAction,
                ring = palette.vibrant,
                size = inner,
                style = style,
                controlsStyle = controlsStyle,
            )
        }
    }
}

@Composable
private fun PlayWithProgress(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    ring: Color,
    size: Dp,
    style: AudioPlayerMiniStyle,
    controlsStyle: AudioControlsStyle,
) {
    Box(
        modifier = Modifier.size(size).drawBehind { drawRing(progress = state.progress, color = ring, style = style) },
        contentAlignment = Alignment.Center,
    ) {
        PlayPauseButton(
            isPlaying = state.isPlaying,
            onToggle = { onAction(AudioAction.Pause.takeIf { state.isPlaying } ?: AudioAction.Play) },
            style = controlsStyle,
            container = style.playContainer,
            content = style.playContent,
            size = size - style.ringGap,
        )
    }
}

private fun DrawScope.drawRing(progress: Float, color: Color, style: AudioPlayerMiniStyle) {
    val stroke = style.ringWidth.toPx()
    val arc = Size(width = size.width - stroke, height = size.height - stroke)
    val origin = Offset(x = stroke / 2f, y = stroke / 2f)
    val line = Stroke(stroke)
    drawArc(
        color = color.copy(alpha = style.ringAlpha),
        startAngle = 0f,
        sweepAngle = FULL_TURN,
        useCenter = false,
        topLeft = origin,
        size = arc,
        style = line,
    )
    drawArc(
        color = color,
        startAngle = style.ringStart,
        sweepAngle = FULL_TURN * progress,
        useCenter = false,
        topLeft = origin,
        size = arc,
        style = line,
    )
}
