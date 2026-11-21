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

package com.davils.kiro.ui.window.testing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.window.ApplicationScope
import com.davils.kiro.ui.window.tray.TrayHost
import com.davils.kiro.ui.window.tray.TrayNotification
import com.davils.kiro.ui.window.tray.TraySpec
import com.davils.kore.core.annotation.DavilsInternal

/**
 * A [TrayHost] for tests that shows no icon and records the [TraySpec] of every tray it was asked to show and every
 * notification sent to it.
 *
 * Provide it through [com.davils.kiro.ui.window.tray.LocalTrayHost] in a test of a composable that shows a tray.
 *
 * @property isSupported whether the mock reports a tray; a test sets it to `false` to act as a desktop without one.
 * @since 1.0.0
 */
@DavilsInternal
public class TrayHostMock(override val isSupported: Boolean = true) : TrayHost {
    private val specs = mutableListOf<TraySpec>()

    private val received = mutableListOf<TrayNotification>()

    /**
     * The distinct specs this host was asked to show, in the order they arrived.
     *
     * @since 1.0.0
     */
    public val shown: List<TraySpec>
        get() = specs.toList()

    /**
     * The notifications sent through the states of the shown trays, in the order they arrived.
     *
     * @since 1.0.0
     */
    public val notifications: List<TrayNotification>
        get() = received.toList()

    @Composable
    override fun Show(scope: ApplicationScope, spec: TraySpec) {
        SideEffect { record(spec) }
        LaunchedEffect(spec.state) {
            spec.state.notifications.collect { notification -> received += notification }
        }
    }

    private fun record(spec: TraySpec) {
        if (specs.lastOrNull() == spec) return
        specs += spec
    }
}
