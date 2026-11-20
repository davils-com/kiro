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
import com.davils.kiro.ui.components.alertdialog.AlertDialog
import com.davils.kiro.ui.components.alertdialog.AlertDialogAction
import com.davils.kiro.ui.components.alertdialog.AlertDialogCancel
import com.davils.kiro.ui.components.alertdialog.AlertDialogDescription
import com.davils.kiro.ui.components.alertdialog.AlertDialogFooter
import com.davils.kiro.ui.components.alertdialog.AlertDialogHeader
import com.davils.kiro.ui.components.alertdialog.AlertDialogTitle
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun AlertDialogDemo() {
    var isOpen by remember { mutableStateOf(false) }
    Button(onClick = { isOpen = true }, variant = ButtonVariant.Outline) { Text("Show dialog") }
    AlertDialog(open = isOpen, onOpenChange = { open -> isOpen = open }) {
        AlertDialogHeader {
            AlertDialogTitle(text = "Are you absolutely sure?")
            AlertDialogDescription(text = "This action cannot be undone. This will permanently delete your account.")
        }
        AlertDialogFooter {
            AlertDialogCancel(onClick = { isOpen = false }) { Text("Cancel") }
            AlertDialogAction(onClick = { isOpen = false }) { Text("Continue") }
        }
    }
}
