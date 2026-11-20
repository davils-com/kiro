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

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type

internal class TreeKeys<T>(
    private val nodes: List<TreeNode<T>>,
    private val state: SidebarTreeState<T>,
    private val focused: T?,
    isReversed: Boolean,
    private val onFocus: (T) -> Unit,
) {
    private val inwardKey: Key = Key.DirectionLeft.takeIf { isReversed } ?: Key.DirectionRight

    private val outwardKey: Key = Key.DirectionRight.takeIf { isReversed } ?: Key.DirectionLeft

    private val moves: Map<Key, (Int, List<TreeNode<T>>) -> Boolean> = mapOf(
        Key.DirectionDown to { index, visible -> focus(visible.getOrNull(index + 1)) },
        Key.DirectionUp to { index, visible -> focus(visible.getOrNull(index - 1)) },
        inwardKey to { index, visible -> inward(visible[index]) },
        outwardKey to { index, visible -> outward(visible[index]) },
    )

    fun handle(event: KeyEvent): Boolean {
        val move = moves[event.key]?.takeIf { event.type == KeyEventType.KeyDown } ?: return false
        val visible = visibleOf(nodes = nodes, expanded = state.expanded)
        val index = visible.indexOfFirst { node -> node.value == focused }
        return index >= 0 && move(index, visible)
    }

    private fun focus(node: TreeNode<T>?): Boolean {
        node?.let { target -> onFocus(target.value) }
        return node != null
    }

    private fun inward(node: TreeNode<T>): Boolean = (node as? TreeNode.Branch<T>)?.let(::enter) == true

    private fun enter(branch: TreeNode.Branch<T>): Boolean {
        if (state.isExpanded(branch.value)) return focus(branch.children.firstOrNull())
        state.expand(branch.value)
        return true
    }

    private fun outward(node: TreeNode<T>): Boolean {
        val isOpen = node is TreeNode.Branch<T> && state.isExpanded(node.value)
        if (isOpen) {
            state.collapse(node.value)
            return true
        }
        val parent = state.parents[node.value]
        parent?.let(onFocus)
        return parent != null
    }
}
