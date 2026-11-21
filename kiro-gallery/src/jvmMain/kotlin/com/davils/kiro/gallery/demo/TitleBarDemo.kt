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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.menubar.Menubar
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Search
import com.davils.kiro.ui.window.action.NotificationBell
import com.davils.kiro.ui.window.action.TitleBarButton
import com.davils.kiro.ui.window.titlebar.TitleBar
import com.davils.kiro.ui.window.titlebar.WindowTitle
import com.davils.kiro.ui.window.titlebar.titleBarStyle

@Composable
internal fun TitleBarDemo() {
    var unread by remember { mutableStateOf(3) }
    val actions = remember { PreviewActions() }
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        DesktopPlatform.entries.filter { platform -> platform != DesktopPlatform.Other }.forEach { platform ->
            TitleBar(actions = actions, style = titleBarStyle(platform)) {
                start {
                    Menubar {
                        menu("File") { item("New", onClick = {}) }
                        menu("Edit") { item("Undo", onClick = {}) }
                        menu("View") { item("Zoom", onClick = {}) }
                    }
                }
                center { WindowTitle("Quarterly report — ${platform.name}") }
                end {
                    TitleBarButton(icon = Lucide.Search, contentDescription = "Search", onClick = {})
                    NotificationBell(count = unread, onClick = { unread = 0 })
                }
            }
        }
    }
}
