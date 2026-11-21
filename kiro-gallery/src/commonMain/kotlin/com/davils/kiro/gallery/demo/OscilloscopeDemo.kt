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
import com.davils.kiro.ui.audio.scope.Oscilloscope
import com.davils.kiro.ui.audio.scope.ScopeLayout
import com.davils.kiro.ui.audio.scope.oscilloscopeStyle

@Composable
internal fun OscilloscopeDemo() {
    val audio = rememberDemoAudio()
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Oscilloscope(scope = audio.scope)
        Oscilloscope(scope = audio.scope, style = oscilloscopeStyle().copy(layout = ScopeLayout.Split))
    }
}
