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

package com.davils.kiro.gallery.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.demo.demos
import com.davils.kiro.gallery.theme
import com.davils.kiro.ui.components.label.Label
import com.davils.kiro.ui.components.sidebar.Sidebar
import com.davils.kiro.ui.components.sidebar.SidebarBrand
import com.davils.kiro.ui.components.sidebar.SidebarContent
import com.davils.kiro.ui.components.sidebar.SidebarFooter
import com.davils.kiro.ui.components.sidebar.SidebarGroup
import com.davils.kiro.ui.components.sidebar.SidebarHeader
import com.davils.kiro.ui.components.sidebar.SidebarInput
import com.davils.kiro.ui.components.sidebar.tree.SidebarTree
import com.davils.kiro.ui.components.sidebar.tree.SidebarTreeState
import com.davils.kiro.ui.components.switch.Switch
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.lucide.Component
import com.davils.kiro.ui.icons.lucide.Lucide

@Composable
internal fun GalleryNavigation(tree: SidebarTreeState<GalleryNode>, isDark: Boolean, onDarkChange: (Boolean) -> Unit) {
    var query by remember { mutableStateOf("") }
    val matches = matchesOf(demos = demos, query = query)
    LaunchedEffect(query) { if (query.isNotBlank()) tree.expandAll() }
    Sidebar {
        SidebarHeader {
            SidebarBrand(title = "Kiro UI", logo = Lucide.Component, subtitle = "${demos.size} components")
            SidebarInput(
                value = query,
                onValueChange = { typed -> query = typed },
                placeholder = "Search components...",
            )
        }
        SidebarContent {
            SidebarGroup {
                if (matches.isEmpty()) {
                    Text(
                        text = "No component matches \"$query\".",
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        color = theme.colors.mutedForeground,
                    )
                }
                SidebarTree(state = tree) {
                    matches.forEach { (category, found) ->
                        sub(
                            value = GalleryNode.Category(category),
                            label = category.label,
                            icon = category.icon,
                            badge = "${found.size}",
                        ) {
                            found.forEach { demo -> item(value = GalleryNode.Demo(demo), label = demo.name) }
                        }
                    }
                }
            }
        }
        SidebarFooter {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Switch(checked = isDark, onCheckedChange = onDarkChange)
                Label(text = "Dark")
            }
        }
    }
}
