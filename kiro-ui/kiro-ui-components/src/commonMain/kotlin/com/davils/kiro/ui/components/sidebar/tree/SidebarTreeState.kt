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

@Stable
public class SidebarTreeState<T>(selected: T? = null, expanded: Set<T> = emptySet()) {
    public var selected: T? by mutableStateOf(selected)
        private set

    public var expanded: Set<T> by mutableStateOf(expanded)
        private set

    internal var parents: Map<T, T> = emptyMap()
    internal var branches: Set<T> = emptySet()

    public fun select(value: T) {
        selected = value
        reveal(value)
    }

    public fun isExpanded(value: T): Boolean = value in expanded

    public fun expand(value: T) {
        expanded = expanded + value
    }

    public fun collapse(value: T) {
        expanded = expanded - value
    }

    public fun toggle(value: T) {
        expanded = (expanded - value).takeIf { value in expanded } ?: expanded + value
    }

    public fun expandAll() {
        expanded = expanded + branches
    }

    public fun collapseAll() {
        expanded = emptySet()
    }

    internal fun reveal(value: T) {
        val ancestors = generateSequence(parents[value]) { parent -> parents[parent] }.toSet()
        if (!expanded.containsAll(ancestors)) expanded = expanded + ancestors
    }
}

@Composable
public fun <T> rememberSidebarTreeState(selected: T? = null, expanded: Set<T> = emptySet()): SidebarTreeState<T> =
    remember { SidebarTreeState(selected = selected, expanded = expanded) }
