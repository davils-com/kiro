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
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.sheet.Sheet
import com.davils.kiro.ui.components.sheet.SheetDescription
import com.davils.kiro.ui.components.sheet.SheetFooter
import com.davils.kiro.ui.components.sheet.SheetHeader
import com.davils.kiro.ui.components.sheet.SheetTitle
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun SheetDemo() {
    var side by remember { mutableStateOf(Side.End) }
    var isOpen by remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Side.entries.forEach { edge ->
            Button(
                onClick = {
                    side = edge
                    isOpen = true
                },
                variant = ButtonVariant.Outline,
            ) { Text("Open ${edge.name.lowercase()}") }
        }
    }
    Sheet(open = isOpen, onOpenChange = { open -> isOpen = open }, side = side) {
        SheetHeader {
            SheetTitle(text = "Edit profile")
            SheetDescription(text = "Make changes to your profile here.")
        }
        SheetFooter { Button(onClick = { isOpen = false }) { Text("Save changes") } }
    }
}
