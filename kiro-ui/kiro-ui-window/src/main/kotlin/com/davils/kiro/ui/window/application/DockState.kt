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

package com.davils.kiro.ui.window.application

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.davils.kore.core.annotation.DavilsInternal

/**
 * The state of a [Dock], through which an application asks the user for attention.
 *
 * @since 1.0.0
 */
@Stable
public class DockState {
    private var dock: DockApi? = null

    /**
     * Asks the user to look at the application, such as by bouncing its icon in the dock of macOS or flashing it in
     * the taskbar of Windows. Nothing happens while no [Dock] shows this state or where the platform does not support
     * it.
     *
     * @param isCritical whether the request lasts until the user activates the application, rather than once.
     * @since 1.0.0
     */
    public fun requestAttention(isCritical: Boolean = false) {
        dock?.requestAttention(isCritical)
    }

    internal fun attach(dock: DockApi) {
        this.dock = dock
    }

    internal fun detach(dock: DockApi) {
        if (this.dock !== dock) return
        this.dock = null
    }
}

/**
 * Creates a [DockState] that is remembered across compositions.
 *
 * @return the remembered state.
 * @since 1.0.0
 */
@Composable
public fun rememberDockState(): DockState = remember { DockState() }
