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

package com.davils.kiro.ui.components.sidebar.tree

import com.davils.kiro.ui.icons.IconGraphic

/**
 * The scope in which the items and the branches of a [SidebarTree] are declared, in the order they are shown.
 *
 * @param T the type of the values that identify the nodes.
 * @since 1.0.0
 */
public interface SidebarTreeScope<T> {
    /**
     * Adds an item that is selected when it is clicked.
     *
     * @param value the value that identifies the item; it must be unique in the tree.
     * @param label the text of the item.
     * @param icon the icon in front of the label, or `null` for none.
     * @param badge the short text at the end of the item, or `null` for none.
     * @param onClick called when the item is clicked, after it is selected.
     * @since 1.0.0
     */
    public fun item(value: T, label: String, icon: IconGraphic? = null, badge: String? = null, onClick: () -> Unit = {})

    /**
     * Adds a branch that expands and collapses when it is clicked and holds the nodes that [content] declares.
     *
     * @param value the value that identifies the branch; it must be unique in the tree.
     * @param label the text of the branch.
     * @param icon the icon in front of the label, or `null` for none.
     * @param badge the short text at the end of the branch, or `null` for none.
     * @param content declares the children of the branch.
     * @since 1.0.0
     */
    public fun sub(
        value: T,
        label: String,
        icon: IconGraphic? = null,
        badge: String? = null,
        content: SidebarTreeScope<T>.() -> Unit,
    )
}
