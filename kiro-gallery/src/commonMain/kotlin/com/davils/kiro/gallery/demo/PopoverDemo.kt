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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.popover.Popover
import com.davils.kiro.ui.components.popover.PopoverDescription
import com.davils.kiro.ui.components.popover.PopoverHeader
import com.davils.kiro.ui.components.popover.PopoverTitle
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun PopoverDemo() {
    var isOpen by remember { mutableStateOf(false) }
    Popover(
        expanded = isOpen,
        onExpandedChange = { open -> isOpen = open },
        trigger = { Button(onClick = { isOpen = true }, variant = ButtonVariant.Outline) { Text("Open popover") } },
    ) {
        PopoverHeader {
            PopoverTitle(text = "Dimensions")
            PopoverDescription(text = "Set the dimensions for the layer.")
        }
    }
}
