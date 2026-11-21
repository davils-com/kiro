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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.demo.signal.rememberDemoAudio
import com.davils.kiro.ui.audio.timeline.AudioClip
import com.davils.kiro.ui.audio.timeline.rememberTimelineState

@Composable
internal fun AudioClipDemo() {
    val audio = rememberDemoAudio()
    val state = rememberTimelineState(lanes = emptyList(), zoom = 20f)
    LaunchedEffect(audio.stems) { state.load(demoLanes(audio.stems)) }
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.height(112.dp)) {
        listOf("pad", "bass-chorus", "riser").forEach { id ->
            val clip = state.lanes.flatMap { lane -> lane.clips }.firstOrNull { clip -> clip.id.value == id }
            clip?.let { shown ->
                AudioClip(
                    clip = shown,
                    zoom = state.zoom,
                    onAction = state::apply,
                    isSelected = state.selection == shown.id,
                )
            }
        }
    }
}
