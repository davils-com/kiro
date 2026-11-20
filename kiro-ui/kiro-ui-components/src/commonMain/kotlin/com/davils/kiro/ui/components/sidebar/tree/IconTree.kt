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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.InlineScope
import com.davils.kiro.ui.components.dropdownmenu.DropdownMenu
import com.davils.kiro.ui.components.menu.MenuScope
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.sidebar.SidebarMenuButton
import com.davils.kiro.ui.components.sidebar.SidebarStyle
import com.davils.kiro.ui.components.text.Text

@Composable
internal fun <T> IconTree(
    nodes: List<TreeNode<T>>,
    state: SidebarTreeState<T>,
    modifier: Modifier,
    style: SidebarStyle,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(style.subGap)) {
        nodes.forEach { node -> key(node.value) { IconNode(node = node, state = state, style = style) } }
    }
}

@Composable
private fun <T> IconNode(node: TreeNode<T>, state: SidebarTreeState<T>, style: SidebarStyle) {
    val isActive = state.selected == node.value || state.parents.isAncestor(of = state.selected, candidate = node.value)
    when (node) {
        is TreeNode.Leaf<T> -> SidebarMenuButton(
            onClick = {
                state.select(node.value)
                node.onClick()
            },
            isActive = isActive,
            tooltip = node.label,
            style = style,
        ) { Glyph(node = node) }

        is TreeNode.Branch<T> -> Flyout(branch = node, state = state, isActive = isActive, style = style)
    }
}

@Composable
private fun <T> Flyout(
    branch: TreeNode.Branch<T>,
    state: SidebarTreeState<T>,
    isActive: Boolean,
    style: SidebarStyle,
) {
    var isOpen by remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = isOpen,
        onExpandedChange = { open -> isOpen = open },
        trigger = {
            SidebarMenuButton(onClick = { isOpen = true }, isActive = isActive, tooltip = branch.label, style = style) {
                Glyph(node = branch)
            }
        },
        placement = Placement(side = Side.End, align = Align.Start),
    ) {
        label(text = branch.label)
        menuOf(nodes = branch.children, state = state)
    }
}

@Composable
private fun <T> InlineScope.Glyph(node: TreeNode<T>) {
    val icon = node.icon
    if (icon == null) {
        Text(node.label.take(1))
        return
    }
    Icon(icon = icon, placement = IconPlacement.Inline)
}

private fun <T> MenuScope.menuOf(nodes: List<TreeNode<T>>, state: SidebarTreeState<T>) {
    nodes.forEach { node -> entryOf(node = node, state = state) }
}

private fun <T> MenuScope.entryOf(node: TreeNode<T>, state: SidebarTreeState<T>) {
    when (node) {
        is TreeNode.Leaf<T> -> item(
            label = node.label,
            onClick = {
                state.select(node.value)
                node.onClick()
            },
            icon = node.icon,
        )

        is TreeNode.Branch<T> -> sub(
            label = node.label,
            icon = node.icon,
        ) { menuOf(nodes = node.children, state = state) }
    }
}

private fun <T> Map<T, T>.isAncestor(of: T?, candidate: T): Boolean {
    val start = of ?: return false
    return generateSequence(this[start]) { parent -> this[parent] }.any { parent -> parent == candidate }
}
