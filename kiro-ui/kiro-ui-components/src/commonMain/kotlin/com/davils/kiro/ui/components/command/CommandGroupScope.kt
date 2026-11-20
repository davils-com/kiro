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

/**
 * The scope in which a group of a [Command] menu declares its items.
 *
 * @since 1.0.0
 */
public interface CommandGroupScope {
    /**
     * Adds a command to the group.
     *
     * @param label the text of the command; the query is matched against it.
     * @param onSelect called when the command is run.
     * @param enabled whether the command can be highlighted and run.
     * @param icon the icon before the label, or `null` for none.
     * @param shortcut the keyboard shortcut shown at the end of the row, or `null` for none.
     * @param keywords further terms the query is matched against.
     * @param detail a text shown at the end of the row when there is no [shortcut], or `null` for none.
     * @since 1.0.0
     */
    public fun item(
        label: String,
        onSelect: () -> Unit,
        enabled: Boolean = true,
        icon: IconGraphic? = null,
        shortcut: String? = null,
        keywords: List<String> = emptyList(),
        detail: String? = null,
    )

    /**
     * Adds an item that opens a page of its own with further groups, and adds its label to the path of the menu.
     *
     * @param label the text of the item and the name of the page in the path; the query is matched against it.
     * @param icon the icon before the label, or `null` for none.
     * @param keywords further terms the query is matched against.
     * @param content declares the groups and separators of the page.
     * @since 1.0.0
     */
    public fun page(
        label: String,
        icon: IconGraphic? = null,
        keywords: List<String> = emptyList(),
        content: CommandSectionsScope.() -> Unit,
    )
}
