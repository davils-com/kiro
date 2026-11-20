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

package com.davils.kiro.ui.components.select

import com.davils.kiro.ui.components.menu.MenuEntry

internal class SelectScopeImpl<T>(private val selected: T?, private val onSelect: (T) -> Unit) : SelectScope<T> {
    private val collected = mutableListOf<MenuEntry>()
    private val labels = mutableMapOf<T, String>()
    private val placeholders = mutableListOf<String>()

    val entries: List<MenuEntry> get() = collected.toList()

    val placeholder: String get() = placeholders.lastOrNull().orEmpty()

    val selectedLabel: String? get() = selected?.let { value -> labels[value] }

    override fun placeholder(text: String) {
        placeholders += text
    }

    override fun option(value: T, label: String, enabled: Boolean) {
        labels[value] = label
        collected += MenuEntry.Radio(
            label = label,
            isSelected = value == selected,
            onSelect = { onSelect(value) },
            isEnabled = enabled,
            isInset = false,
            isClosing = true,
        )
    }

    override fun group(label: String, content: SelectScope<T>.() -> Unit) {
        collected += MenuEntry.Label(text = label, isInset = false)
        content()
    }

    override fun separator() {
        collected += MenuEntry.Separator
    }
}
