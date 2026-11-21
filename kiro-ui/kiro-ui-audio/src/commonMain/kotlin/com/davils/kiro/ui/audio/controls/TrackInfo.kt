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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.resizing
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor

/**
 * The title and the artist of the current track, sliding in the direction of the playback when the track changes and
 * scrolling when they are too long.
 *
 * @param state the state of the player.
 * @param modifier the modifier of the track information.
 * @param isCentered whether the texts are centered.
 * @param isArtistShown whether the artist shows below the title.
 * @param style the style of the audio controls.
 * @since 1.0.0
 */
@Composable
public fun TrackInfo(
    state: AudioPlayerState,
    modifier: Modifier = Modifier,
    isCentered: Boolean = false,
    isArtistShown: Boolean = true,
    style: AudioControlsStyle = audioControlsStyle(),
) {
    val direction = state.direction
    val spec = style.motion.resizing<IntOffset>()
    val fade = style.motion.resizing<Float>()
    val share = style.slideShare
    AnimatedContent(
        targetState = state.track,
        modifier = modifier,
        transitionSpec = {
            val entering = slideInHorizontally(spec) { width -> (width * share).toInt() * direction } + fadeIn(fade)
            val leaving = slideOutHorizontally(spec) { width -> -(width * share).toInt() * direction } + fadeOut(fade)
            entering.togetherWith(leaving)
        },
        contentAlignment = Alignment.CenterStart,
    ) { track ->
        val alignment = Alignment.CenterHorizontally.takeIf { isCentered } ?: Alignment.Start
        val textAlign = TextAlign.Center.takeIf { isCentered } ?: TextAlign.Start
        Column(horizontalAlignment = alignment) {
            Text(
                text = track?.title ?: style.labels.nothingPlaying,
                modifier = Modifier.basicMarquee(),
                style = style.titleStyle.copy(textAlign = textAlign),
                maxLines = 1,
            )
            if (isArtistShown && track != null) {
                Text(
                    text = track.artist,
                    modifier = Modifier.basicMarquee(),
                    style = style.artistStyle.copy(textAlign = textAlign),
                    color = LocalContentColor.current.copy(alpha = style.secondaryAlpha),
                    maxLines = 1,
                )
            }
        }
    }
}
