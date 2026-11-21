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

package com.davils.kiro.ui.window

import androidx.compose.runtime.Immutable

/**
 * The texts the chrome of a window announces, in English by default.
 *
 * @property minimize the spoken label of the button that minimizes the window.
 * @property maximize the spoken label of the button that maximizes the window.
 * @property restore the spoken label of the button that restores a maximized window.
 * @property close the spoken label of the button that closes the window.
 * @property notifications the spoken label of the notification bell.
 * @property badge the text of a badge with a count, which shows at most `limit`.
 * @since 1.0.0
 */
@Immutable
public class WindowLabels(
    public val minimize: String,
    public val maximize: String,
    public val restore: String,
    public val close: String,
    public val notifications: String,
    public val badge: (count: Int, limit: Int) -> String,
) {
    /**
     * Returns a copy of this [WindowLabels] with the given values replaced and every other value kept.
     *
     * @param minimize the new value of [minimize].
     * @param maximize the new value of [maximize].
     * @param restore the new value of [restore].
     * @param close the new value of [close].
     * @param notifications the new value of [notifications].
     * @param badge the new value of [badge].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        minimize: String = this.minimize,
        maximize: String = this.maximize,
        restore: String = this.restore,
        close: String = this.close,
        notifications: String = this.notifications,
        badge: (count: Int, limit: Int) -> String = this.badge,
    ): WindowLabels = WindowLabels(
        minimize = minimize,
        maximize = maximize,
        restore = restore,
        close = close,
        notifications = notifications,
        badge = badge,
    )

    override fun equals(other: Any?): Boolean = other is WindowLabels && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "WindowLabels(" +
        "minimize=$minimize, " +
        "maximize=$maximize, " +
        "restore=$restore, " +
        "close=$close, " +
        "notifications=$notifications, " +
        "badge=$badge)"

    private fun values(): List<Any?> = listOf(
        minimize,
        maximize,
        restore,
        close,
        notifications,
        badge,
    )
}
