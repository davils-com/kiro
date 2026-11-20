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

package com.davils.kiro.ui.components.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val DEFAULT_SECONDS: Int = 4

@Stable
public class ToasterState {
    private var next: Long = 0
    private var leaving: Set<ToastId> by mutableStateOf(emptySet())

    public var toasts: List<Toast> by mutableStateOf(emptyList())
        private set

    public fun show(
        title: String,
        description: String? = null,
        type: ToastType = ToastType.Default,
        action: ToastAction? = null,
        duration: Duration? = durationOf(type),
        progress: ToastProgress? = null,
        isDismissible: Boolean = type != ToastType.Loading,
        onDismiss: (() -> Unit)? = null,
    ): ToastId {
        next += 1
        val id = ToastId(next)
        val toast = Toast(
            id = id,
            title = title,
            description = description,
            type = type,
            action = action,
            duration = duration.takeIf { progress == null },
            progress = progress,
            isDismissible = isDismissible,
            onDismiss = onDismiss,
        )
        toasts = toasts + toast
        return id
    }

    public fun update(id: ToastId, change: Toast.() -> Toast) {
        toasts = toasts.map { toast -> changed(toast = toast, id = id, change = change) }
    }

    public fun dismiss(id: ToastId) {
        if (toasts.none { toast -> toast.id == id }) return
        leaving = leaving + id
    }

    public fun dismissAll() {
        leaving = toasts.map { toast -> toast.id }.toSet()
    }

    private fun changed(toast: Toast, id: ToastId, change: Toast.() -> Toast): Toast {
        if (toast.id != id) return toast
        val updated = toast.change()
        return updated.copy(id = id)
    }

    internal fun isLeaving(id: ToastId): Boolean = id in leaving

    internal fun remove(id: ToastId) {
        val toast = toasts.firstOrNull { candidate -> candidate.id == id } ?: return
        toasts = toasts - toast
        leaving = leaving - id
        toast.onDismiss?.invoke()
    }

    public companion object {
        public val DefaultDuration: Duration = DEFAULT_SECONDS.seconds

        public fun durationOf(type: ToastType): Duration? = DefaultDuration.takeUnless {
            type ==
                ToastType.Loading
        }
    }
}

@Composable
public fun rememberToaster(): ToasterState = remember { ToasterState() }
