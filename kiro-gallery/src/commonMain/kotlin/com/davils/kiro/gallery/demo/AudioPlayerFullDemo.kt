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

package com.davils.kiro.gallery.demo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.full.AudioPlayerFull
import com.davils.kiro.ui.audio.rememberAudioPlayerState

@Composable
internal fun AudioPlayerFullDemo() {
    val state = rememberAudioPlayerState(queue = remember { sampleTracks() })
    LaunchedEffect(state) { state.simulatePlayback() }
    AudioPlayerFull(
        state = state,
        onAction = state::apply,
        modifier = Modifier.fillMaxWidth().height(520.dp),
    )
}
