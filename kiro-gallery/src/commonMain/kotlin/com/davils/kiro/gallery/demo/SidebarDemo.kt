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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.dropdownmenu.DropdownMenu
import com.davils.kiro.ui.components.label.Label
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.sidebar.Sidebar
import com.davils.kiro.ui.components.sidebar.SidebarBrand
import com.davils.kiro.ui.components.sidebar.SidebarCollapsible
import com.davils.kiro.ui.components.sidebar.SidebarContent
import com.davils.kiro.ui.components.sidebar.SidebarFooter
import com.davils.kiro.ui.components.sidebar.SidebarGroup
import com.davils.kiro.ui.components.sidebar.SidebarGroupLabel
import com.davils.kiro.ui.components.sidebar.SidebarHeader
import com.davils.kiro.ui.components.sidebar.SidebarInset
import com.davils.kiro.ui.components.sidebar.SidebarProvider
import com.davils.kiro.ui.components.sidebar.SidebarState
import com.davils.kiro.ui.components.sidebar.SidebarTrigger
import com.davils.kiro.ui.components.sidebar.tree.SidebarTree
import com.davils.kiro.ui.components.sidebar.tree.SidebarTreeScope
import com.davils.kiro.ui.components.sidebar.tree.rememberSidebarTreeState
import com.davils.kiro.ui.components.switch.Switch
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.lucide.Activity
import com.davils.kiro.ui.icons.lucide.Archive
import com.davils.kiro.ui.icons.lucide.Cable
import com.davils.kiro.ui.icons.lucide.ChevronsUpDown
import com.davils.kiro.ui.icons.lucide.CircleUser
import com.davils.kiro.ui.icons.lucide.FileText
import com.davils.kiro.ui.icons.lucide.Inbox
import com.davils.kiro.ui.icons.lucide.Layers
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Network
import com.davils.kiro.ui.icons.lucide.Router
import com.davils.kiro.ui.icons.lucide.Server
import com.davils.kiro.ui.icons.lucide.Settings
import com.davils.kiro.ui.icons.lucide.Shield

private const val RACKS: Int = 12
private const val HOME: String = "Overview"

@Composable
internal fun SidebarDemo() {
    var isIconMode by remember { mutableStateOf(true) }
    val tree = rememberSidebarTreeState(selected = HOME)
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Switch(checked = isIconMode, onCheckedChange = { checked -> isIconMode = checked })
            Label(text = "Collapse to icons")
        }
        Box(modifier = Modifier.size(width = 760.dp, height = 520.dp)) {
            SidebarProvider(state = remember { SidebarState() }) {
                val collapsible = SidebarCollapsible.Icon.takeIf { isIconMode } ?: SidebarCollapsible.Offcanvas
                Sidebar(collapsible = collapsible) {
                    SidebarHeader { Workspace() }
                    SidebarContent {
                        SidebarGroup {
                            SidebarGroupLabel(text = "Network")
                            SidebarTree(state = tree) { network() }
                        }
                    }
                    SidebarFooter { Account() }
                }
                SidebarInset {
                    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        SidebarTrigger()
                        Text(tree.selected ?: HOME)
                    }
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Content") }
                }
            }
        }
    }
}

@Composable
private fun Workspace() {
    var isOpen by remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = isOpen,
        onExpandedChange = { open -> isOpen = open },
        trigger = {
            SidebarBrand(
                title = "Davils",
                logo = Lucide.Network,
                subtitle = "Data center Frankfurt",
                onClick = { isOpen = true },
            ) {
                Icon(icon = Lucide.ChevronsUpDown, contentDescription = null)
            }
        },
    ) {
        label(text = "Workspaces")
        item(label = "Data center Frankfurt", onClick = {})
        item(label = "Campus Berlin", onClick = {})
        separator()
        item(label = "Add workspace", onClick = {})
    }
}

@Composable
private fun Account() {
    var isOpen by remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = isOpen,
        onExpandedChange = { open -> isOpen = open },
        trigger = {
            SidebarBrand(
                title = "Nils",
                logo = Lucide.CircleUser,
                subtitle = "Network engineer",
                onClick = { isOpen = true },
            )
        },
        placement = Placement(side = Side.Top, align = Align.Start),
    ) {
        item(label = "Account", onClick = {})
        item(label = "Log out", onClick = {})
    }
}

private fun SidebarTreeScope<String>.network() {
    item(value = HOME, label = HOME, icon = Lucide.Activity)
    item(value = "Alerts", label = "Alerts", icon = Lucide.Inbox, badge = "4")
    sub(value = "Inventory", label = "Inventory", icon = Lucide.Server) {
        sub(value = "Frankfurt", label = "Frankfurt", icon = Lucide.Layers) {
            sub(value = "Hall A", label = "Hall A") {
                repeat(RACKS) { rack -> item(value = "Rack A$rack", label = "Rack A$rack", icon = Lucide.Server) }
            }
            item(value = "Hall B", label = "Hall B")
        }
        item(value = "Berlin", label = "Berlin", icon = Lucide.Layers)
    }
    sub(value = "Routing", label = "Routing", icon = Lucide.Router) {
        item(value = "BGP", label = "BGP", badge = "12")
        item(value = "OSPF", label = "OSPF")
    }
    item(value = "Cabling", label = "Cabling", icon = Lucide.Cable)
    item(value = "Security", label = "Security", icon = Lucide.Shield)
    item(value = "Configs", label = "Configs", icon = Lucide.FileText)
    item(value = "Archive", label = "Archive", icon = Lucide.Archive)
    item(value = "Settings", label = "Settings", icon = Lucide.Settings)
}
