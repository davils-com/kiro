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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.demo.signal.rememberDemoAudio
import com.davils.kiro.ui.audio.ambient.RadialSpectrum
import com.davils.kiro.ui.audio.ambient.RadialSymmetry
import com.davils.kiro.ui.audio.ambient.radialSpectrumStyle
import com.davils.kiro.ui.audio.controls.AudioCover

@Composable
internal fun RadialSpectrumDemo() {
    val audio = rememberDemoAudio()
    val track = remember { sampleTracks().first() }
    Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
        RadialSpectrum(spectrum = audio.spectrum(bands = 48)) {
            AudioCover(track = track, isPlaying = true, modifier = Modifier.fillMaxSize(), shape = CircleShape)
        }
        RadialSpectrum(
            spectrum = audio.spectrum(bands = 96),
            style = radialSpectrumStyle().copy(symmetry = RadialSymmetry.Full),
        )
    }
}
