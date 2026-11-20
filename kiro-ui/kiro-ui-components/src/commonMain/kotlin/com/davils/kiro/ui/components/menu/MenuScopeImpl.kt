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

package com.davils.kiro.ui.components.menu

import com.davils.kiro.ui.icons.IconGraphic

internal class MenuScopeImpl : MenuScope {
    private val collected = mutableListOf<MenuEntry>()

    val entries: List<MenuEntry> get() = collected.toList()

    override fun item(
        label: String,
        onClick: () -> Unit,
        enabled: Boolean,
        icon: IconGraphic?,
        shortcut: String?,
        variant: MenuItemVariant,
        isInset: Boolean,
    ) {
        collected += MenuEntry.Action(
            label = label,
            onClick = onClick,
            isEnabled = enabled,
            icon = icon,
            shortcut = shortcut,
            isDestructive = variant == MenuItemVariant.Destructive,
            isInset = isInset,
        )
    }

    override fun checkbox(
        label: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        enabled: Boolean,
        shortcut: String?,
        isInset: Boolean,
    ) {
        collected += MenuEntry.Check(
            label = label,
            isChecked = checked,
            onCheckedChange = onCheckedChange,
            isEnabled = enabled,
            shortcut = shortcut,
            isInset = isInset,
        )
    }

    override fun <T> radio(
        label: String,
        value: T,
        selected: T,
        onSelect: (T) -> Unit,
        enabled: Boolean,
        isInset: Boolean,
    ) {
        collected += MenuEntry.Radio(
            label = label,
            isSelected = value == selected,
            onSelect = { onSelect(value) },
            isEnabled = enabled,
            isInset = isInset,
        )
    }

    override fun label(text: String, isInset: Boolean) {
        collected += MenuEntry.Label(text = text, isInset = isInset)
    }

    override fun separator() {
        collected += MenuEntry.Separator
    }

    override fun sub(
        label: String,
        icon: IconGraphic?,
        enabled: Boolean,
        isInset: Boolean,
        content: MenuScope.() -> Unit,
    ) {
        collected += MenuEntry.Sub(
            label = label,
            icon = icon,
            isEnabled = enabled,
            isInset = isInset,
            entries = MenuScopeImpl().apply(content).entries,
        )
    }
}
