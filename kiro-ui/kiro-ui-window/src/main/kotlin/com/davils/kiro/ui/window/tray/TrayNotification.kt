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

import androidx.compose.runtime.Immutable

/**
 * A notification that the operating system shows next to a tray icon.
 *
 * @property title the title of the notification.
 * @property message the text of the notification.
 * @property type how important the notification is, which decides its icon.
 * @since 1.0.0
 */
@Immutable
public class TrayNotification(
    public val title: String,
    public val message: String,
    public val type: TrayNotificationType = TrayNotificationType.None,
) {
    /**
     * Returns a copy of this [TrayNotification] with the given values replaced and every other value kept.
     *
     * @param title the new value of [title].
     * @param message the new value of [message].
     * @param type the new value of [type].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        title: String = this.title,
        message: String = this.message,
        type: TrayNotificationType = this.type,
    ): TrayNotification = TrayNotification(
        title = title,
        message = message,
        type = type,
    )

    override fun equals(other: Any?): Boolean = other is TrayNotification && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TrayNotification(" +
        "title=$title, " +
        "message=$message, " +
        "type=$type)"

    private fun values(): List<Any?> = listOf(
        title,
        message,
        type,
    )
}
