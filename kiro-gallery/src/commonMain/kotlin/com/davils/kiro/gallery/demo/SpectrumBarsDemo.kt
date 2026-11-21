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
import com.davils.kiro.ui.audio.ambient.BarLook
import com.davils.kiro.ui.audio.ambient.Baseline
import com.davils.kiro.ui.audio.ambient.SpectrumBars
import com.davils.kiro.ui.audio.ambient.spectrumBarsStyle

@Composable
internal fun SpectrumBarsDemo() {
    val audio = rememberDemoAudio()
    val leds = spectrumBarsStyle().copy(look = BarLook.Segmented, reflection = 0f)
    val centered = spectrumBarsStyle().copy(baseline = Baseline.Center, gap = 0.5f)
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        SpectrumBars(spectrum = audio.spectrum(bands = 64))
        SpectrumBars(spectrum = audio.spectrum(bands = 32), style = leds)
        SpectrumBars(spectrum = audio.spectrum(bands = 96), style = centered)
    }
}
