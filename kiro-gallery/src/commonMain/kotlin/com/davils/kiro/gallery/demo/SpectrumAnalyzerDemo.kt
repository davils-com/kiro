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
import com.davils.kiro.ui.audio.spectrum.SpectrumAnalyzer
import com.davils.kiro.ui.audio.spectrum.SpectrumCurve
import com.davils.kiro.ui.audio.spectrum.spectrumAnalyzerStyle

@Composable
internal fun SpectrumAnalyzerDemo() {
    val audio = rememberDemoAudio()
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SpectrumAnalyzer(spectrum = audio.spectrum(bands = 120))
        SpectrumAnalyzer(
            spectrum = audio.spectrum(bands = 31),
            style = spectrumAnalyzerStyle().copy(curve = SpectrumCurve.Steps),
        )
    }
}
