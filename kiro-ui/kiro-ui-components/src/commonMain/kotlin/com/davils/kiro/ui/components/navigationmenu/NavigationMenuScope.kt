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

package com.davils.kiro.ui.components.navigationmenu

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import com.davils.kiro.ui.components.menu.MenuScope

/**
 * The scope in which a [NavigationMenu] declares its entries, in the order they are shown.
 *
 * @since 1.0.0
 */
public interface NavigationMenuScope {
    /**
     * Adds a trigger that opens a framed panel with content of its own, such as a grid of [NavigationMenuLink]s.
     *
     * @param label the text of the trigger.
     * @param content the content of the panel, laid out in a column.
     * @since 1.0.0
     */
    public fun item(label: String, content: @Composable ColumnScope.() -> Unit)

    /**
     * Adds a trigger without a panel that acts as a link.
     *
     * @param label the text of the trigger.
     * @param onClick called when the trigger is clicked.
     * @param isActive whether the link leads to the current page, which the trigger shows with the open background.
     * @since 1.0.0
     */
    public fun link(label: String, onClick: () -> Unit, isActive: Boolean = false)

    /**
     * Adds a trigger that opens a menu of actions, separators and submenus.
     *
     * @param label the text of the trigger.
     * @param content declares the entries of the menu.
     * @since 1.0.0
     */
    public fun menu(label: String, content: MenuScope.() -> Unit)
}
