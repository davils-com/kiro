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
 * The scope in which a [Command] menu declares its tabs and, when it has no tabs, its groups and separators.
 *
 * @since 1.0.0
 */
public interface CommandScope : CommandSectionsScope {
    /**
     * Adds a tab with groups of its own. The tabs are shown when the menu has two or more of them.
     *
     * @param label the text of the tab.
     * @param icon the icon before the label, or `null` for none.
     * @param placeholder the text the empty search field shows while this tab is selected, or `null` to keep the
     * placeholder of the menu.
     * @param content declares the groups and separators of the tab.
     * @since 1.0.0
     */
    public fun tab(
        label: String,
        icon: IconGraphic? = null,
        placeholder: String? = null,
        content: CommandSectionsScope.() -> Unit,
    )

    /**
     * Adds a first tab that shows the groups of every other tab together.
     *
     * @param label the text of the tab, or `null` for the English default.
     * @param icon the icon before the label, or `null` for none.
     * @since 1.0.0
     */
    public fun allTab(label: String? = null, icon: IconGraphic? = null)
}
