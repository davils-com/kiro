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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * Holds the selected node and the expanded branches of a [SidebarTree].
 *
 * @param T the type of the values that identify the nodes.
 * @param selected the value of the node that is selected at first, or `null` for none.
 * @param expanded the values of the branches that are expanded at first.
 * @since 1.0.0
 */
@Stable
public class SidebarTreeState<T>(selected: T? = null, expanded: Set<T> = emptySet()) {
    /**
     * The value of the selected node, or `null` while none is selected.
     *
     * @since 1.0.0
     */
    public var selected: T? by mutableStateOf(selected)
        private set

    /**
     * The values of the expanded branches.
     *
     * @since 1.0.0
     */
    public var expanded: Set<T> by mutableStateOf(expanded)
        private set

    internal var parents: Map<T, T> = emptyMap()
    internal var branches: Set<T> = emptySet()

    /**
     * Selects a node and expands the branches above it, so that it can be seen.
     *
     * @param value the value of the node.
     * @since 1.0.0
     */
    public fun select(value: T) {
        selected = value
        reveal(value)
    }

    /**
     * Returns whether a branch is expanded.
     *
     * @param value the value of the branch.
     * @return `true` if the branch is expanded.
     * @since 1.0.0
     */
    public fun isExpanded(value: T): Boolean = value in expanded

    /**
     * Expands a branch.
     *
     * @param value the value of the branch.
     * @since 1.0.0
     */
    public fun expand(value: T) {
        expanded = expanded + value
    }

    /**
     * Collapses a branch.
     *
     * @param value the value of the branch.
     * @since 1.0.0
     */
    public fun collapse(value: T) {
        expanded = expanded - value
    }

    /**
     * Collapses a branch if it is expanded and expands it if it is collapsed.
     *
     * @param value the value of the branch.
     * @since 1.0.0
     */
    public fun toggle(value: T) {
        expanded = (expanded - value).takeIf { value in expanded } ?: expanded + value
    }

    /**
     * Expands every branch of the tree that shows this state.
     *
     * @since 1.0.0
     */
    public fun expandAll() {
        expanded = expanded + branches
    }

    /**
     * Collapses every branch.
     *
     * @since 1.0.0
     */
    public fun collapseAll() {
        expanded = emptySet()
    }

    internal fun reveal(value: T) {
        val ancestors = generateSequence(parents[value]) { parent -> parents[parent] }.toSet()
        if (!expanded.containsAll(ancestors)) expanded = expanded + ancestors
    }
}

/**
 * Creates a [SidebarTreeState] and remembers it across recompositions.
 *
 * @param T the type of the values that identify the nodes.
 * @param selected the value of the node that is selected at first, or `null` for none; later values are ignored.
 * @param expanded the values of the branches that are expanded at first; later values are ignored.
 * @return the remembered state.
 * @since 1.0.0
 */
@Composable
public fun <T> rememberSidebarTreeState(selected: T? = null, expanded: Set<T> = emptySet()): SidebarTreeState<T> =
    remember { SidebarTreeState(selected = selected, expanded = expanded) }
