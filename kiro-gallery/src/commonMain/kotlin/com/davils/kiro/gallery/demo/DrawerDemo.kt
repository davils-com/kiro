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
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.drawer.Drawer
import com.davils.kiro.ui.components.drawer.DrawerDescription
import com.davils.kiro.ui.components.drawer.DrawerFooter
import com.davils.kiro.ui.components.drawer.DrawerHeader
import com.davils.kiro.ui.components.drawer.DrawerTitle
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.text.Text

private val goals: List<String> = listOf(
    "Ping every switch",
    "Back up configurations",
    "Rotate credentials",
    "Audit VLANs",
)

@Composable
internal fun DrawerDemo() {
    var side by remember { mutableStateOf(Side.Bottom) }
    var isOpen by remember { mutableStateOf(false) }
    val openAt = { edge: Side ->
        side = edge
        isOpen = true
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { openAt(Side.Bottom) }, variant = ButtonVariant.Outline) { Text("Open drawer") }
        listOf(Side.Top, Side.Start, Side.End).forEach { edge ->
            Button(
                onClick = { openAt(edge) },
                variant = ButtonVariant.Outline,
            ) { Text("Open ${edge.name.lowercase()}") }
        }
    }
    Drawer(open = isOpen, onOpenChange = { open -> isOpen = open }, side = side) {
        DrawerHeader {
            DrawerTitle(text = "Nightly maintenance")
            DrawerDescription(text = "Drag the handle up to see every task, down to close.")
        }
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            goals.forEach { goal -> Text(goal) }
        }
        DrawerFooter { Button(onClick = { isOpen = false }) { Text("Submit") } }
    }
}
