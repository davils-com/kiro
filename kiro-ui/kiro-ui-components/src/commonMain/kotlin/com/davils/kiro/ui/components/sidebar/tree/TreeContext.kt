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

import androidx.compose.ui.focus.FocusRequester
import com.davils.kiro.ui.components.sidebar.SidebarStyle

internal data class TreeContext<T>(
    val state: SidebarTreeState<T>,
    val style: SidebarStyle,
    val focusers: MutableMap<T, FocusRequester>,
    val onFocused: (T) -> Unit,
)
