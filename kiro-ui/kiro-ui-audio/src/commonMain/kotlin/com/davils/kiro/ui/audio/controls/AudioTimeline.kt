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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import kotlin.time.Duration

@Composable
internal fun AudioTimeline(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    accent: Color,
    style: AudioControlsStyle,
    modifier: Modifier = Modifier,
) {
    val secondary = LocalContentColor.current.copy(alpha = style.secondaryAlpha)
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(style.gap / 2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = style.time(state.position), style = style.timeStyle, color = secondary)
        AudioScrubber(
            state = state,
            onAction = onAction,
            modifier = Modifier.weight(1f),
            accent = accent,
            style = style,
        )
        Text(text = style.time(state.track?.duration ?: Duration.ZERO), style = style.timeStyle, color = secondary)
    }
}
