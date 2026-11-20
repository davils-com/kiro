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

@file:OptIn(DavilsInternal::class)

package com.davils.kiro.ui.components.menubar.native

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuScope
import com.davils.kore.core.annotation.DavilsInternal
import com.davils.kore.core.annotation.DavilsPlatformBinding

@DavilsPlatformBinding
internal object NativeMenubar {
    @Composable
    fun Emit(window: FrameWindowScope, menus: List<NativeMenuEntry.Menu>) {
        val current by rememberUpdatedState(menus)
        window.MenuBar {
            current.forEach { menu ->
                Menu(text = menu.label, enabled = menu.isEnabled) { menu.entries.forEach { entry -> Entry(entry) } }
            }
        }
    }

    @Composable
    private fun MenuScope.Entry(entry: NativeMenuEntry) {
        when (entry) {
            is NativeMenuEntry.Item -> Item(
                text = entry.label,
                enabled = entry.isEnabled,
                shortcut = entry.shortcut,
                onClick = entry.onClick,
            )

            is NativeMenuEntry.Checkbox -> CheckboxItem(
                text = entry.label,
                checked = entry.isChecked,
                enabled = entry.isEnabled,
                shortcut = entry.shortcut,
                onCheckedChange = entry.onCheckedChange,
            )

            is NativeMenuEntry.Radio -> RadioButtonItem(
                text = entry.label,
                selected = entry.isSelected,
                enabled = entry.isEnabled,
                onClick = entry.onSelect,
            )

            is NativeMenuEntry.Menu -> Menu(text = entry.label, enabled = entry.isEnabled) {
                entry.entries.forEach { child -> Entry(child) }
            }

            NativeMenuEntry.Separator -> Separator()
        }
    }
}
