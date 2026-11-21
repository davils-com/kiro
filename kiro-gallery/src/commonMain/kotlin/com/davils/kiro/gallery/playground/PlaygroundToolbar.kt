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

package com.davils.kiro.gallery.playground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.label.Label
import com.davils.kiro.ui.components.select.Select
import com.davils.kiro.ui.components.switch.Switch
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Pause
import com.davils.kiro.ui.icons.lucide.Play
import com.davils.kiro.ui.icons.lucide.RotateCcw

@Composable
internal fun PlaygroundToolbar(state: PlaygroundState) {
    val isPlaying = state.playback.isPlaying
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Select(selected = state.spec, onSelectedChange = state::select, modifier = Modifier.width(200.dp)) {
            artFamilies.forEach { (family, specs) ->
                group(label = family) { specs.forEach { spec -> option(value = spec, label = spec.name) } }
            }
        }
        Select(
            selected = state.palette,
            onSelectedChange = { preset -> state.palette = preset },
            modifier = Modifier.width(160.dp),
        ) {
            PalettePreset.entries.forEach { preset -> option(value = preset, label = preset.name) }
        }
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = state.isDiagnosticsShown, onCheckedChange = { shown -> state.isDiagnosticsShown = shown })
        Label(text = "Diagnostics")
        Button(onClick = state.playback::toggle, variant = ButtonVariant.Outline) {
            Icon(icon = Lucide.Pause.takeIf { isPlaying } ?: Lucide.Play, placement = IconPlacement.Leading)
            Text("Pause".takeIf { isPlaying } ?: "Play")
        }
        Button(onClick = state::reset, variant = ButtonVariant.Ghost) {
            Icon(icon = Lucide.RotateCcw, placement = IconPlacement.Leading)
            Text("Reset")
        }
    }
}
