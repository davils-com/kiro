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

/**
 * Collects the entries of a menu: items, checkboxes, radio items, labels, separators and submenus, in the order in
 * which they are added.
 *
 * @since 1.0.0
 */
public interface MenuScope {
    /**
     * Adds an item that runs an action and then closes the menu.
     *
     * @param label the text of the item.
     * @param onClick called when the item is chosen.
     * @param enabled whether the item can be highlighted and chosen.
     * @param icon the icon before the label, or `null` for none.
     * @param shortcut the keyboard shortcut shown at the end of the item, or `null` for none; it is only shown, not
     * bound.
     * @param variant the look of the item.
     * @param isInset whether the label is indented to line up with items that show an icon.
     * @since 1.0.0
     */
    public fun item(
        label: String,
        onClick: () -> Unit,
        enabled: Boolean = true,
        icon: IconGraphic? = null,
        shortcut: String? = null,
        variant: MenuItemVariant = MenuItemVariant.Default,
        isInset: Boolean = false,
    )

    /**
     * Adds an item that can be checked and unchecked; choosing it keeps the menu open.
     *
     * @param label the text of the item.
     * @param checked whether the item is checked.
     * @param onCheckedChange called with the new checked state when the item is chosen.
     * @param enabled whether the item can be highlighted and chosen.
     * @param shortcut the keyboard shortcut shown at the end of the item, or `null` for none; it is only shown, not
     * bound.
     * @param isInset whether the label is indented to line up with items that show an icon.
     * @since 1.0.0
     */
    public fun checkbox(
        label: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        enabled: Boolean = true,
        shortcut: String? = null,
        isInset: Boolean = false,
    )

    /**
     * Adds one item of a group of radio items, of which one is selected; choosing it keeps the menu open.
     *
     * @param T the type of the values of the group.
     * @param label the text of the item.
     * @param value the value this item stands for.
     * @param selected the value that is selected in the group; the item is selected when it equals [value].
     * @param onSelect called with [value] when the item is chosen.
     * @param enabled whether the item can be highlighted and chosen.
     * @param isInset whether the label is indented to line up with items that show an icon.
     * @since 1.0.0
     */
    public fun <T> radio(
        label: String,
        value: T,
        selected: T,
        onSelect: (T) -> Unit,
        enabled: Boolean = true,
        isInset: Boolean = false,
    )

    /**
     * Adds a label that names the entries below it; it cannot be highlighted or chosen.
     *
     * @param text the text of the label.
     * @param isInset whether the text is indented to line up with items that show an icon.
     * @since 1.0.0
     */
    public fun label(text: String, isInset: Boolean = false)

    /**
     * Adds a line that separates the entries above it from the entries below it.
     *
     * @since 1.0.0
     */
    public fun separator()

    /**
     * Adds an item that opens a submenu with its own entries.
     *
     * @param label the text of the item.
     * @param icon the icon before the label, or `null` for none.
     * @param enabled whether the item can be highlighted and its submenu opened.
     * @param isInset whether the label is indented to line up with items that show an icon.
     * @param content adds the entries of the submenu.
     * @since 1.0.0
     */
    public fun sub(
        label: String,
        icon: IconGraphic? = null,
        enabled: Boolean = true,
        isInset: Boolean = false,
        content: MenuScope.() -> Unit,
    )
}
