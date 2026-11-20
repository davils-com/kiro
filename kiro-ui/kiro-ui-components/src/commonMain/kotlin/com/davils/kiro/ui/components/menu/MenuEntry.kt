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

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.icons.IconGraphic

@Immutable
internal sealed interface MenuEntry {
    @Immutable
    sealed interface Interactive : MenuEntry {
        val label: String
        val isEnabled: Boolean
        val isInset: Boolean
    }

    @Immutable
    data class Action(
        override val label: String,
        val onClick: () -> Unit,
        override val isEnabled: Boolean,
        val icon: IconGraphic?,
        val shortcut: String?,
        val isDestructive: Boolean,
        override val isInset: Boolean,
    ) : Interactive

    @Immutable
    data class Check(
        override val label: String,
        val isChecked: Boolean,
        val onCheckedChange: (Boolean) -> Unit,
        override val isEnabled: Boolean,
        val shortcut: String?,
        override val isInset: Boolean,
    ) : Interactive

    @Immutable
    data class Radio(
        override val label: String,
        val isSelected: Boolean,
        val onSelect: () -> Unit,
        override val isEnabled: Boolean,
        override val isInset: Boolean,
        val isClosing: Boolean = false,
    ) : Interactive

    @Immutable
    data class Sub(
        override val label: String,
        val icon: IconGraphic?,
        override val isEnabled: Boolean,
        override val isInset: Boolean,
        val entries: List<MenuEntry>,
    ) : Interactive

    @Immutable
    data class Label(val text: String, val isInset: Boolean) : MenuEntry

    @Immutable
    data object Separator : MenuEntry
}
