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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.toggle.Toggle
import com.davils.kiro.ui.components.toggle.ToggleVariant

@Composable
internal fun ToggleDemo() {
    var isBold by remember { mutableStateOf(false) }
    var isItalic by remember { mutableStateOf(true) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Toggle(pressed = isBold, onPressedChange = { pressed -> isBold = pressed }) { Text("Bold") }
        Toggle(
            pressed = isItalic,
            onPressedChange = { pressed -> isItalic = pressed },
            variant = ToggleVariant.Outline,
        ) { Text("Italic") }
    }
}
