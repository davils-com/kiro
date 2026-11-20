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

/**
 * The scope in which a [Command] menu, a tab or a page declares its groups and separators.
 *
 * @since 1.0.0
 */
public interface CommandSectionsScope {
    /**
     * Adds a group of items. While the query is not blank, a group without a matching item is hidden.
     *
     * @param heading the heading above the items, or `null` for none.
     * @param content declares the items of the group.
     * @since 1.0.0
     */
    public fun group(heading: String? = null, content: CommandGroupScope.() -> Unit)

    /**
     * Adds a line between two groups. Separators are hidden while the query is not blank.
     *
     * @since 1.0.0
     */
    public fun separator()
}
