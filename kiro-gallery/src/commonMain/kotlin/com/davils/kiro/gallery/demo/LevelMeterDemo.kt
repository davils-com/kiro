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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.demo.signal.rememberDemoAudio
import com.davils.kiro.ui.audio.meter.LevelMeter
import com.davils.kiro.ui.audio.meter.MeterMode
import com.davils.kiro.ui.audio.meter.levelMeterStyle
import com.davils.kiro.ui.components.Orientation

@Composable
internal fun LevelMeterDemo() {
    val audio = rememberDemoAudio()
    val segmented = levelMeterStyle().copy(mode = MeterMode.Segmented)
    Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
        LevelMeter(levels = audio.levels, onResetClip = audio::resetClip)
        LevelMeter(levels = audio.levels, onResetClip = audio::resetClip, style = segmented)
        LevelMeter(
            levels = audio.levels,
            modifier = Modifier.width(360.dp),
            orientation = Orientation.Horizontal,
            onResetClip = audio::resetClip,
        )
    }
}
