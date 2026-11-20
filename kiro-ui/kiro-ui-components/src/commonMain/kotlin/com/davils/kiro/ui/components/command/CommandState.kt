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

@Stable
public class CommandState(initialQuery: String = "", initialTab: Int = 0) {
    public var query: String by mutableStateOf(initialQuery)

    public var tab: Int by mutableIntStateOf(initialTab)
        private set

    public var path: List<String> by mutableStateOf(emptyList())
        private set

    public fun select(tab: Int) {
        this.tab = tab
        path = emptyList()
    }

    public fun open(page: String) {
        path = path + page
        query = ""
    }

    public fun back(): Boolean {
        if (path.isEmpty()) return false
        path = path.dropLast(1)
        return true
    }

    public fun reset() {
        query = ""
        path = emptyList()
    }
}

@Composable
public fun rememberCommandState(): CommandState = remember { CommandState() }
