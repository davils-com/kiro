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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.command.Command
import com.davils.kiro.ui.components.command.CommandDialog
import com.davils.kiro.ui.components.command.CommandScope
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.lucide.FileText
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Network
import com.davils.kiro.ui.icons.lucide.RefreshCw
import com.davils.kiro.ui.icons.lucide.Server
import com.davils.kiro.ui.icons.lucide.Settings
import com.davils.kiro.ui.icons.lucide.Terminal

private const val INTERFACES: String = "Interfaces"
private const val ACTIONS: String = "Actions"
private const val SETTINGS: String = "Settings"

@Composable
internal fun CommandDemo() {
    var isOpen by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Command(modifier = Modifier.width(480.dp)) { searchEverywhere() }
        Button(onClick = { isOpen = true }, variant = ButtonVariant.Outline) { Text("Open command dialog") }
    }
    CommandDialog(open = isOpen, onOpenChange = { open -> isOpen = open }) { searchEverywhere() }
}

private fun CommandScope.searchEverywhere() {
    allTab(label = "All")
    tab(label = "Files", icon = Lucide.FileText, placeholder = "Search files...") {
        group(heading = "Recent files") {
            item(label = "core-01.cfg", onSelect = {}, icon = Lucide.FileText, detail = "configs")
            item(label = "vlans.yaml", onSelect = {}, icon = Lucide.FileText, detail = "inventory")
            item(label = "README.md", onSelect = {}, icon = Lucide.FileText, detail = "docs")
        }
    }
    tab(label = "Devices", icon = Lucide.Server, placeholder = "Search devices...") {
        group(heading = "Data center Frankfurt") {
            page(label = "core-01", icon = Lucide.Server, keywords = listOf("switch", "cisco")) {
                group(heading = INTERFACES) {
                    item(label = "Gi1/0/1", onSelect = {}, icon = Lucide.Network, detail = "up")
                    item(label = "Gi1/0/48", onSelect = {}, icon = Lucide.Network, detail = "uplink")
                }
                group(heading = ACTIONS) {
                    item(label = "Open terminal", onSelect = {}, icon = Lucide.Terminal, shortcut = "Ctrl+T")
                }
            }
            page(label = "edge-02", icon = Lucide.Server, keywords = listOf("router", "juniper")) {
                group(heading = INTERFACES) { item(label = "ge-0/0/0", onSelect = {}, icon = Lucide.Network) }
            }
        }
    }
    tab(label = ACTIONS, icon = Lucide.Settings) {
        group(heading = ACTIONS) {
            item(label = "Reload configuration", onSelect = {}, icon = Lucide.RefreshCw, shortcut = "Ctrl+R")
            item(label = SETTINGS, onSelect = {}, icon = Lucide.Settings, shortcut = "Ctrl+,")
        }
    }
}
