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
import com.davils.kiro.ui.components.InlineScope

/**
 * Collects the tabs of [Tabs].
 *
 * @param T the type of the values that identify the tabs.
 * @since 1.0.0
 */
public interface TabsScope<T> {
    /**
     * Adds a tab after the tabs added before it.
     *
     * @param value the value that identifies the tab; [Tabs] selects the tab whose value equals its `selected`.
     * @param enabled whether the tab can be selected.
     * @param content the content shown while the tab is selected, or `null` for none.
     * @param label the label of the tab, such as text and icons.
     * @since 1.0.0
     */
    public fun tab(
        value: T,
        enabled: Boolean = true,
        content: (@Composable () -> Unit)? = null,
        label: @Composable InlineScope.() -> Unit,
    )
}
