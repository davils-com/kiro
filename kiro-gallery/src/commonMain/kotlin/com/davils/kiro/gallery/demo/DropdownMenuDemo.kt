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
import com.davils.kiro.ui.components.dropdownmenu.DropdownMenu
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun DropdownMenuDemo() {
    var isOpen by remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = isOpen,
        onExpandedChange = { open -> isOpen = open },
        trigger = { Button(onClick = { isOpen = true }, variant = ButtonVariant.Outline) { Text("Open") } },
    ) {
        label(text = "My account")
        item(label = "Profile", onClick = {})
        item(label = "Billing", onClick = {})
        item(label = "Settings", onClick = {})
        separator()
        sub(label = "Invite users") {
            item(label = "Email", onClick = {})
            item(label = "Message", onClick = {})
            sub(label = "More") {
                item(label = "Calendar", onClick = {})
                sub(label = "Teams") {
                    item(label = "Network operations", onClick = {})
                    item(label = "Security", onClick = {})
                }
            }
        }
        separator()
        item(label = "Log out", onClick = {})
    }
}
