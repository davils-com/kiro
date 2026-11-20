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

import androidx.compose.runtime.Immutable
import kotlin.time.Duration

@Immutable
public class Toast(
    public val id: ToastId,
    public val title: String,
    public val description: String?,
    public val type: ToastType,
    public val action: ToastAction?,
    public val duration: Duration?,
    public val progress: ToastProgress? = null,
    public val isDismissible: Boolean = true,
    public val onDismiss: (() -> Unit)? = null,
) {
    /**
     * Returns a copy of this [Toast] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        id: ToastId = this.id,
        title: String = this.title,
        description: String? = this.description,
        type: ToastType = this.type,
        action: ToastAction? = this.action,
        duration: Duration? = this.duration,
        progress: ToastProgress? = this.progress,
        isDismissible: Boolean = this.isDismissible,
        onDismiss: (() -> Unit)? = this.onDismiss,
    ): Toast = Toast(
        id = id,
        title = title,
        description = description,
        type = type,
        action = action,
        duration = duration,
        progress = progress,
        isDismissible = isDismissible,
        onDismiss = onDismiss,
    )

    override fun equals(other: Any?): Boolean = other is Toast && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Toast(" +
        "id=$id, " +
        "title=$title, " +
        "description=$description, " +
        "type=$type, " +
        "action=$action, " +
        "duration=$duration, " +
        "progress=$progress, " +
        "isDismissible=$isDismissible, " +
        "onDismiss=$onDismiss)"

    private fun values(): List<Any?> = listOf(
        id,
        title,
        description,
        type,
        action,
        duration,
        progress,
        isDismissible,
        onDismiss,
    )
}
