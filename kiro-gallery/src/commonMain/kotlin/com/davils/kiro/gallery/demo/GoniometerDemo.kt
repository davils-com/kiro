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
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.demo.signal.rememberDemoAudio
import com.davils.kiro.ui.audio.meter.CorrelationMeter
import com.davils.kiro.ui.audio.scope.Goniometer
import com.davils.kiro.ui.audio.scope.GoniometerStyle
import com.davils.kiro.ui.audio.scope.ScopeGrid
import com.davils.kiro.ui.audio.scope.ScopeTrace
import com.davils.kiro.ui.audio.scope.goniometerStyle

@Composable
internal fun GoniometerDemo() {
    val audio = rememberDemoAudio()
    val lines: GoniometerStyle = goniometerStyle().copy(grid = ScopeGrid.Square, trace = ScopeTrace.Lines)
    Row(horizontalArrangement = Arrangement.spacedBy(32.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Goniometer(scope = audio.scope)
            CorrelationMeter(correlation = audio.scope.correlation)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Goniometer(scope = audio.scope, style = lines)
            CorrelationMeter(correlation = audio.scope.correlation)
        }
    }
}
