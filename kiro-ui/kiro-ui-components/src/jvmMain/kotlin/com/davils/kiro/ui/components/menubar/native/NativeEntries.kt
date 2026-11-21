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

@file:OptIn(DavilsInternal::class)

package com.davils.kiro.ui.components.menubar.native

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.MenuScope
import com.davils.kore.core.annotation.DavilsInternal

/**
 * Adds [entries] to a menu that the operating system draws, such as a menu of the menu bar of macOS or the menu of a
 * tray icon, with their submenus.
 *
 * @param entries the entries to add, in order.
 * @since 1.0.0
 */
@DavilsInternal
@Composable
public fun MenuScope.NativeEntries(entries: List<NativeMenuEntry>) {
    NativeMenubar.Entries(scope = this, entries = entries)
}
