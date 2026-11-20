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

package com.davils.kiro.ui.components.tabs

import androidx.compose.runtime.Composable
import com.davils.kiro.ui.components.InlineScopeImpl

internal class TabsScope<T> : TabsScopeImpl<T> {
    private val collected = mutableListOf<TabEntry<T>>()

    val entries: List<TabEntry<T>> get() = collected.toList()

    override fun tab(
        value: T,
        enabled: Boolean,
        content: (@Composable () -> Unit)?,
        label: @Composable InlineScopeImpl.() -> Unit,
    ) {
        collected += TabEntry(value = value, isEnabled = enabled, content = content, label = label)
    }
}
