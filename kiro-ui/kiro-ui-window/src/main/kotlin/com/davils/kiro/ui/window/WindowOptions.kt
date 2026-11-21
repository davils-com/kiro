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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent

/**
 * The options of a window that do not change how it looks: its icon, whether it shows and its keyboard handlers.
 *
 * @property icon the icon of the window in the task bar and the switcher, or `null` for the icon of the application.
 * @property isVisible whether the window shows.
 * @property onPreviewKeyEvent handles a key event before the focused content, and returns `true` if it consumed it.
 * @property onKeyEvent handles a key event that the focused content did not consume, and returns `true` if it
 * consumed it.
 * @since 1.0.0
 */
@Immutable
public class WindowOptions(
    public val icon: Painter? = null,
    public val isVisible: Boolean = true,
    public val onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
    public val onKeyEvent: (KeyEvent) -> Boolean = { false },
) {
    /**
     * Returns a copy of this [WindowOptions] with the given values replaced and every other value kept.
     *
     * @param icon the new value of [icon].
     * @param isVisible the new value of [isVisible].
     * @param onPreviewKeyEvent the new value of [onPreviewKeyEvent].
     * @param onKeyEvent the new value of [onKeyEvent].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        icon: Painter? = this.icon,
        isVisible: Boolean = this.isVisible,
        onPreviewKeyEvent: (KeyEvent) -> Boolean = this.onPreviewKeyEvent,
        onKeyEvent: (KeyEvent) -> Boolean = this.onKeyEvent,
    ): WindowOptions = WindowOptions(
        icon = icon,
        isVisible = isVisible,
        onPreviewKeyEvent = onPreviewKeyEvent,
        onKeyEvent = onKeyEvent,
    )

    override fun equals(other: Any?): Boolean = other is WindowOptions && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "WindowOptions(" +
        "icon=${icon ?: "null"}, " +
        "isVisible=$isVisible, " +
        "onPreviewKeyEvent=$onPreviewKeyEvent, " +
        "onKeyEvent=$onKeyEvent)"

    private fun values(): List<Any?> = listOf(
        icon,
        isVisible,
        onPreviewKeyEvent,
        onKeyEvent,
    )
}
