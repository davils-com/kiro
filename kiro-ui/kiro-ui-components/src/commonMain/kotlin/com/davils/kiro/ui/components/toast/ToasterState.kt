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

/**
 * Holds the toasts of a [Toaster] and shows, updates and dismisses them.
 *
 * Create it with [rememberToaster] and hand it to a [Toaster]. Its functions may be called from any code that holds
 * it, such as event handlers and coroutines.
 *
 * @since 1.0.0
 */
@Stable
public class ToasterState {
    private var next: Long = 0
    private var leaving: Set<ToastId> by mutableStateOf(emptySet())

    /**
     * The toasts of the toaster, the oldest first, including toasts that are leaving.
     *
     * @since 1.0.0
     */
    public var toasts: List<Toast> by mutableStateOf(emptyList())
        private set

    /**
     * Shows a new toast.
     *
     * @param title the title of the toast.
     * @param description the text below the title, or `null` for none.
     * @param type the type of the toast.
     * @param action the action button of the toast, or `null` for none.
     * @param duration how long the toast stays, or `null` to keep it until it is dismissed; it is ignored while the
     * toast
     * has a progress bar.
     * @param progress the progress bar of the toast, or `null` for none.
     * @param isDismissible whether the toast can be closed with its close button or swiped away.
     * @param onDismiss called when the toast has left the toaster, or `null`.
     * @return the id of the new toast.
     * @since 1.0.0
     */
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

    /**
     * Replaces a toast with the result of [change]; the toast keeps its id. Nothing happens if there is no toast with
     * the
     * id.
     *
     * @param id the id of the toast.
     * @param change returns the new toast from the current one, such as with [Toast.copy].
     * @since 1.0.0
     */
    public fun update(id: ToastId, change: Toast.() -> Toast) {
        toasts = toasts.map { toast -> changed(toast = toast, id = id, change = change) }
    }

    /**
     * Lets a toast leave with its exit animation; it is removed afterwards. Nothing happens if there is no toast with
     * the
     * id.
     *
     * @param id the id of the toast.
     * @since 1.0.0
     */
    public fun dismiss(id: ToastId) {
        if (toasts.none { toast -> toast.id == id }) return
        leaving = leaving + id
    }

    /**
     * Lets every toast leave with its exit animation.
     *
     * @since 1.0.0
     */
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

    /**
     * Holds the default durations of toasts.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * How long a toast stays by default: four seconds.
         *
         * @since 1.0.0
         */
        public val DefaultDuration: Duration = DEFAULT_SECONDS.seconds

        /**
         * Returns the default duration of a toast of a type: [DefaultDuration], or `null` for [ToastType.Loading],
         * which stays
         * until it is dismissed.
         *
         * @param type the type of the toast.
         * @return the default duration, or `null`.
         * @since 1.0.0
         */
        public fun durationOf(type: ToastType): Duration? = DefaultDuration.takeUnless {
            type ==
                ToastType.Loading
        }
    }
}

/**
 * Creates and remembers a [ToasterState] that keeps its toasts across recompositions.
 *
 * @return the state of the toaster.
 * @since 1.0.0
 */
@Composable
public fun rememberToaster(): ToasterState = remember { ToasterState() }
