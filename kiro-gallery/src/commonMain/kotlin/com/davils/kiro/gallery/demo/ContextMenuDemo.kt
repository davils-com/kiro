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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.contextmenu.ContextMenu
import com.davils.kiro.ui.components.menu.MenuItemVariant
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun ContextMenuDemo() {
    ContextMenu(
        menu = {
            item(label = "Back", onClick = {}, shortcut = "Alt+←")
            item(label = "Forward", onClick = {}, shortcut = "Alt+→")
            item(label = "Reload", onClick = {}, shortcut = "Ctrl+R")
            separator()
            sub(label = "More tools") {
                item(label = "Save page as…", onClick = {})
                item(label = "Create shortcut…", onClick = {})
                sub(label = "Developer") {
                    item(label = "Inspect", onClick = {})
                    item(label = "Console", onClick = {})
                    sub(label = "Network") {
                        item(label = "Throttle to 3G", onClick = {})
                        item(label = "Go offline", onClick = {})
                    }
                }
            }
            separator()
            item(label = "Delete", onClick = {}, variant = MenuItemVariant.Destructive)
        },
    ) {
        Box(
            modifier = Modifier.size(width = 300.dp, height = 150.dp),
            contentAlignment = Alignment.Center,
        ) { Text("Right click here") }
    }
}
