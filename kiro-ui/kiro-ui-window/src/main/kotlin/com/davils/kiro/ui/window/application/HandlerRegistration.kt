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
import androidx.compose.runtime.DisposableEffect
import com.davils.kore.core.annotation.DavilsInternal

@Composable
internal fun HandlerRegistration(
    handlers: DesktopHandlers,
    isActive: Boolean,
    install: DesktopHandlers.() -> Unit,
    reset: DesktopHandlers.() -> Unit,
) {
    DisposableEffect(handlers, isActive) {
        val registered = handlers.takeIf { isActive }
        registered?.install()
        onDispose { registered?.reset() }
    }
}

@Composable
internal fun <T : Any> DockValue(dock: DockApi, value: T?, write: DockApi.(T?) -> Unit) {
    DisposableEffect(dock, value) {
        dock.write(value)
        onDispose { dock.write(null) }
    }
}
