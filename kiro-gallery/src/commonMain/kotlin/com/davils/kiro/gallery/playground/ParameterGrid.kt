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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.theme
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.motion.color.ArtPalette

private const val COLUMNS: Int = 3

@Composable
internal fun ParameterGrid(state: PlaygroundState, palette: ArtPalette) {
    val current = theme
    val spec = state.spec
    val values = spec.settingsOf(theme = current, palette = palette, settings = state.settings)
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        spec.parameters.chunked(COLUMNS).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp), verticalAlignment = Alignment.Bottom) {
                row.forEach { parameter ->
                    ParameterControl(
                        parameter = parameter,
                        setting = values.getValue(parameter.name),
                        modifier = Modifier.weight(1f),
                    ) { setting -> state.change(name = parameter.name, setting = setting) }
                }
                repeat(COLUMNS - row.size) { Spacer(modifier = Modifier.weight(1f)) }
            }
        }
        if (spec.fixed.isNotEmpty()) {
            Text(
                text = "Kept at their defaults here: ${spec.fixed.joinToString()}",
                color = current.colors.mutedForeground,
            )
        }
    }
}
