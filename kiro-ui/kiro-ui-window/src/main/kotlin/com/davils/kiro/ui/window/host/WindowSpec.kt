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

package com.davils.kiro.ui.window.host

import androidx.compose.ui.window.WindowState
import com.davils.kiro.ui.window.WindowOptions
import com.davils.kore.core.annotation.DavilsInternal

/**
 * Everything a [WindowHost] needs to open a window of the operating system.
 *
 * @property onCloseRequest called when the user asks to close the window.
 * @property state the position, size and placement of the window.
 * @property title the title of the window, shown by the operating system in its switcher.
 * @property options the icon, the visibility and the keyboard handlers of the window.
 * @property isUndecorated whether the window opens without the frame of the operating system.
 * @property isTransparent whether the window has a transparent background, for rounded corners.
 * @property isResizable whether the user may resize the window.
 * @since 1.0.0
 */
@DavilsInternal
public class WindowSpec(
    public val onCloseRequest: () -> Unit,
    public val state: WindowState,
    public val title: String,
    public val options: WindowOptions,
    public val isUndecorated: Boolean,
    public val isTransparent: Boolean,
    public val isResizable: Boolean,
) {
    /**
     * Returns a copy of this [WindowSpec] with the given values replaced and every other value kept.
     *
     * @param onCloseRequest the new value of [onCloseRequest].
     * @param state the new value of [state].
     * @param title the new value of [title].
     * @param options the new value of [options].
     * @param isUndecorated the new value of [isUndecorated].
     * @param isTransparent the new value of [isTransparent].
     * @param isResizable the new value of [isResizable].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        onCloseRequest: () -> Unit = this.onCloseRequest,
        state: WindowState = this.state,
        title: String = this.title,
        options: WindowOptions = this.options,
        isUndecorated: Boolean = this.isUndecorated,
        isTransparent: Boolean = this.isTransparent,
        isResizable: Boolean = this.isResizable,
    ): WindowSpec = WindowSpec(
        onCloseRequest = onCloseRequest,
        state = state,
        title = title,
        options = options,
        isUndecorated = isUndecorated,
        isTransparent = isTransparent,
        isResizable = isResizable,
    )

    override fun equals(other: Any?): Boolean = other is WindowSpec && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "WindowSpec(" +
        "onCloseRequest=$onCloseRequest, " +
        "state=$state, " +
        "title=$title, " +
        "options=$options, " +
        "isUndecorated=$isUndecorated, " +
        "isTransparent=$isTransparent, " +
        "isResizable=$isResizable)"

    private fun values(): List<Any?> = listOf(
        onCloseRequest,
        state,
        title,
        options,
        isUndecorated,
        isTransparent,
        isResizable,
    )
}
