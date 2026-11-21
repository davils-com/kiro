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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import com.davils.kiro.gallery.demo.signal.rememberDemoAudio
import com.davils.kiro.ui.audio.timeline.ClipTimeline
import com.davils.kiro.ui.audio.timeline.TimelineAction
import com.davils.kiro.ui.audio.timeline.rememberTimelineState

@Composable
internal fun ClipTimelineDemo() {
    val audio = rememberDemoAudio()
    val state = rememberTimelineState(lanes = emptyList())
    LaunchedEffect(audio.stems) { state.load(demoLanes(audio.stems)) }
    LaunchedEffect(audio) { snapshotFlow { audio.position }.collect { at -> state.apply(TimelineAction.Seek(at)) } }
    ClipTimeline(
        state = state,
        onAction = { action ->
            state.apply(action)
            if (action is TimelineAction.Seek) audio.seek(action.position)
        },
    )
}
