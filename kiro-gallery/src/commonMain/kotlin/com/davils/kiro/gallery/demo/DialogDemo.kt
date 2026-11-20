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
import com.davils.kiro.ui.components.dialog.Dialog
import com.davils.kiro.ui.components.dialog.DialogDescription
import com.davils.kiro.ui.components.dialog.DialogFooter
import com.davils.kiro.ui.components.dialog.DialogHeader
import com.davils.kiro.ui.components.dialog.DialogTitle
import com.davils.kiro.ui.components.input.Input
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun DialogDemo() {
    var isOpen by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("Ada Lovelace") }
    Button(onClick = { isOpen = true }, variant = ButtonVariant.Outline) { Text("Edit profile") }
    Dialog(open = isOpen, onOpenChange = { open -> isOpen = open }) {
        DialogHeader {
            DialogTitle(text = "Edit profile")
            DialogDescription(text = "Make changes to your profile here. Click save when you are done.")
        }
        Input(value = name, onValueChange = { typed -> name = typed })
        DialogFooter { Button(onClick = { isOpen = false }) { Text("Save changes") } }
    }
}
