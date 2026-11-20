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

internal class TreeScope<T> : SidebarTreeScope<T> {
    private val collected = mutableListOf<TreeNode<T>>()

    val nodes: List<TreeNode<T>>
        get() = collected.toList()

    override fun item(value: T, label: String, icon: IconGraphic?, badge: String?, onClick: () -> Unit) {
        collected += TreeNode.Leaf(value = value, label = label, icon = icon, badge = badge, onClick = onClick)
    }

    override fun sub(
        value: T,
        label: String,
        icon: IconGraphic?,
        badge: String?,
        content: SidebarTreeScope<T>.() -> Unit,
    ) {
        val children = TreeScope<T>().apply(content).nodes
        collected += TreeNode.Branch(value = value, label = label, icon = icon, badge = badge, children = children)
    }
}
