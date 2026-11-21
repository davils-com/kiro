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
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.demo.signal.rememberDemoAudio
import com.davils.kiro.ui.audio.waveform.Waveform
import com.davils.kiro.ui.audio.waveform.WaveformLayout
import com.davils.kiro.ui.audio.waveform.WaveformLook
import com.davils.kiro.ui.audio.waveform.waveformStyle

@Composable
internal fun WaveformDemo() {
    val audio = rememberDemoAudio()
    val stacked = waveformStyle().copy(layout = WaveformLayout.Stacked, height = 128.dp)
    val bars = waveformStyle().copy(look = WaveformLook.Bars, height = 64.dp)
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Waveform(peaks = audio.peaks, progress = audio.progress, onSeek = audio::seek, style = stacked)
        Waveform(peaks = audio.peaks, progress = audio.progress, onSeek = audio::seek, style = bars)
    }
}
