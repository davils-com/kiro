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

package com.davils.kiro.ui.components.command

import com.davils.kiro.ui.icons.IconGraphic

internal class CommandGroupScope : CommandGroupScopeImpl {
    private val collected = mutableListOf<CommandItem>()

    val items: List<CommandItem>
        get() = collected.toList()

    override fun item(
        label: String,
        onSelect: () -> Unit,
        enabled: Boolean,
        icon: IconGraphic?,
        shortcut: String?,
        keywords: List<String>,
        detail: String?,
    ) {
        collected += CommandItem(
            label = label,
            onSelect = onSelect,
            isEnabled = enabled,
            icon = icon,
            shortcut = shortcut,
            keywords = keywords,
            detail = detail,
        )
    }

    override fun page(
        label: String,
        icon: IconGraphic?,
        keywords: List<String>,
        content: CommandSectionsScope.() -> Unit,
    ) {
        collected += CommandItem(
            label = label,
            onSelect = {},
            isEnabled = true,
            icon = icon,
            shortcut = null,
            keywords = keywords,
            page = SectionsScope().apply(content).sections,
        )
    }
}
