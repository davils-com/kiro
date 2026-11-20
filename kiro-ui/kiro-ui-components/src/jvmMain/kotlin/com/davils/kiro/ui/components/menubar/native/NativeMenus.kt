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

package com.davils.kiro.ui.components.menubar.native

import com.davils.kiro.ui.components.menu.MenuEntry
import com.davils.kiro.ui.components.menubar.MenubarMenu

private val nothing: () -> Unit = {}

internal fun nativeMenusOf(menus: List<MenubarMenu>): List<NativeMenuEntry.Menu> = menus.map { menu ->
    NativeMenuEntry.Menu(label = menu.label, isEnabled = menu.isEnabled, entries = nativeEntriesOf(menu.entries))
}

private fun nativeEntriesOf(entries: List<MenuEntry>): List<NativeMenuEntry> = entries.map(::nativeEntryOf)

private fun nativeEntryOf(entry: MenuEntry): NativeMenuEntry = when (entry) {
    is MenuEntry.Action -> NativeMenuEntry.Item(
        label = entry.label,
        isEnabled = entry.isEnabled,
        shortcut = entry.shortcut?.let(ShortcutParser::parse),
        onClick = entry.onClick,
    )

    is MenuEntry.Check -> NativeMenuEntry.Checkbox(
        label = entry.label,
        isChecked = entry.isChecked,
        isEnabled = entry.isEnabled,
        shortcut = entry.shortcut?.let(ShortcutParser::parse),
        onCheckedChange = entry.onCheckedChange,
    )

    is MenuEntry.Radio -> NativeMenuEntry.Radio(
        label = entry.label,
        isSelected = entry.isSelected,
        isEnabled = entry.isEnabled,
        onSelect = entry.onSelect,
    )

    is MenuEntry.Sub -> NativeMenuEntry.Menu(
        label = entry.label,
        isEnabled = entry.isEnabled,
        entries = nativeEntriesOf(entry.entries),
    )

    is MenuEntry.Label -> NativeMenuEntry.Item(
        label = entry.text,
        isEnabled = false,
        shortcut = null,
        onClick = nothing,
    )

    MenuEntry.Separator -> NativeMenuEntry.Separator
}
