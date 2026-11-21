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

package com.davils.kiro.ui.window.tray

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * The state of a [Tray], through which an application sends notifications to the operating system.
 *
 * A notification sent before a tray shows this state waits until one does; each notification is shown once.
 *
 * @since 1.0.0
 */
@Stable
public class TrayState {
    private val pending = Channel<TrayNotification>(capacity = Channel.BUFFERED)

    internal val notifications: Flow<TrayNotification>
        get() = pending.receiveAsFlow()

    /**
     * Sends a notification that the operating system shows next to the tray icon.
     *
     * @param notification the notification to show.
     * @since 1.0.0
     */
    public fun sendNotification(notification: TrayNotification) {
        pending.trySend(notification)
    }
}

/**
 * Creates a [TrayState] that is remembered across compositions.
 *
 * @return the remembered state.
 * @since 1.0.0
 */
@Composable
public fun rememberTrayState(): TrayState = remember { TrayState() }
