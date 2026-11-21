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

package com.davils.kiro.ui.window.tray

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.isTraySupported
import androidx.compose.ui.window.rememberTrayState
import com.davils.kiro.ui.components.menubar.native.NativeEntries
import com.davils.kore.core.annotation.DavilsInternal
import com.davils.kore.core.annotation.DavilsPlatformBinding

@DavilsPlatformBinding
internal object NativeTrayHost : TrayHost {
    override val isSupported: Boolean
        get() = isTraySupported

    @Composable
    override fun Show(scope: ApplicationScope, spec: TraySpec) {
        val state = rememberTrayState()
        LaunchedEffect(spec.state, state) {
            spec.state.notifications.collect { notification -> state.sendNotification(notification.toCompose()) }
        }
        scope.Tray(icon = spec.icon, state = state, tooltip = spec.tooltip, onAction = spec.onAction) {
            NativeEntries(spec.entries)
        }
    }
}
