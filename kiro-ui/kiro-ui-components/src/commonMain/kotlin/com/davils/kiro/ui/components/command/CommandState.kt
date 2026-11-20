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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * The state of a [Command] menu: the query, the selected tab and the path of the open pages.
 *
 * @param initialQuery the query the menu starts with.
 * @param initialTab the index of the tab the menu starts with.
 * @since 1.0.0
 */
@Stable
public class CommandState(initialQuery: String = "", initialTab: Int = 0) {
    /**
     * The text of the search field, which filters and ranks the commands.
     *
     * @since 1.0.0
     */
    public var query: String by mutableStateOf(initialQuery)

    /**
     * The index of the selected tab; change it with [select].
     *
     * @since 1.0.0
     */
    public var tab: Int by mutableIntStateOf(initialTab)
        private set

    /**
     * The labels of the open pages, from the outermost to the one shown; empty on the first page.
     *
     * @since 1.0.0
     */
    public var path: List<String> by mutableStateOf(emptyList())
        private set

    /**
     * Selects a tab and returns to its first page.
     *
     * @param tab the index of the tab.
     * @since 1.0.0
     */
    public fun select(tab: Int) {
        this.tab = tab
        path = emptyList()
    }

    /**
     * Opens a page of the shown page and clears the query.
     *
     * @param page the label of the item that opens the page.
     * @since 1.0.0
     */
    public fun open(page: String) {
        path = path + page
        query = ""
    }

    /**
     * Closes the page shown and returns to the page it was opened from.
     *
     * @return `true` if a page was closed, `false` if the first page was shown already.
     * @since 1.0.0
     */
    public fun back(): Boolean {
        if (path.isEmpty()) return false
        path = path.dropLast(1)
        return true
    }

    /**
     * Clears the query and returns to the first page, keeping the selected tab.
     *
     * @since 1.0.0
     */
    public fun reset() {
        query = ""
        path = emptyList()
    }
}

/**
 * Creates and remembers a [CommandState] with an empty query on the first tab.
 *
 * @return the remembered state.
 * @since 1.0.0
 */
@Composable
public fun rememberCommandState(): CommandState = remember { CommandState() }
