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
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Plus

@Composable
internal fun ButtonDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Buttons(enabled = true)
        Buttons(enabled = false)
    }
}

@Composable
private fun Buttons(enabled: Boolean) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {}, enabled = enabled) { Text("Button") }
        Button(onClick = {}, enabled = enabled, variant = ButtonVariant.Secondary) { Text("Secondary") }
        Button(onClick = {}, enabled = enabled, variant = ButtonVariant.Outline) { Text("Outline") }
        Button(onClick = {}, enabled = enabled, variant = ButtonVariant.Ghost) { Text("Ghost") }
        Button(onClick = {}, enabled = enabled, variant = ButtonVariant.Destructive) { Text("Destructive") }
        Button(onClick = {}, enabled = enabled, variant = ButtonVariant.Link) { Text("Link") }
        Button(
            onClick = {},
            enabled = enabled,
            size = ButtonSize.Icon,
        ) { Icon(icon = Lucide.Plus, placement = IconPlacement.Inline) }
    }
}
