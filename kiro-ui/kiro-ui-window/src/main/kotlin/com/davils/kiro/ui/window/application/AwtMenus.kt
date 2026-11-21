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

package com.davils.kiro.ui.window.application

import com.davils.kiro.ui.components.menubar.native.NativeMenuEntry
import com.davils.kore.core.annotation.DavilsInternal
import com.davils.kore.core.annotation.DavilsPlatformBinding
import java.awt.CheckboxMenuItem
import java.awt.Menu
import java.awt.MenuItem
import java.awt.PopupMenu
import java.awt.event.ItemEvent

@DavilsPlatformBinding
internal object AwtMenus {
    fun popupOf(entries: List<NativeMenuEntry>): PopupMenu {
        val popup = PopupMenu()
        fill(menu = popup, entries = entries)
        return popup
    }

    private fun fill(menu: Menu, entries: List<NativeMenuEntry>) {
        entries.forEach { entry -> add(menu = menu, entry = entry) }
    }

    private fun add(menu: Menu, entry: NativeMenuEntry) {
        when (entry) {
            is NativeMenuEntry.Item -> menu.add(itemOf(entry))
            is NativeMenuEntry.Checkbox -> menu.add(checkboxOf(entry))
            is NativeMenuEntry.Radio -> menu.add(radioOf(entry))
            is NativeMenuEntry.Menu -> menu.add(submenuOf(entry))
            NativeMenuEntry.Separator -> menu.addSeparator()
        }
    }

    private fun itemOf(entry: NativeMenuEntry.Item): MenuItem {
        val item = MenuItem(entry.label)
        item.isEnabled = entry.isEnabled
        item.addActionListener { entry.onClick() }
        return item
    }

    private fun checkboxOf(entry: NativeMenuEntry.Checkbox): MenuItem {
        val item = CheckboxMenuItem(entry.label, entry.isChecked)
        item.isEnabled = entry.isEnabled
        item.addItemListener { event -> entry.onCheckedChange(event.stateChange == ItemEvent.SELECTED) }
        return item
    }

    private fun radioOf(entry: NativeMenuEntry.Radio): MenuItem {
        val item = CheckboxMenuItem(entry.label, entry.isSelected)
        item.isEnabled = entry.isEnabled
        item.addItemListener { entry.onSelect() }
        return item
    }

    private fun submenuOf(entry: NativeMenuEntry.Menu): MenuItem {
        val submenu = Menu(entry.label)
        submenu.isEnabled = entry.isEnabled
        fill(menu = submenu, entries = entry.entries)
        return submenu
    }
}
