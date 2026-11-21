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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import com.davils.kiro.ui.components.menubar.native.NativeMenuEntry
import com.davils.kore.core.annotation.DavilsInternal

/**
 * Everything a [TrayHost] needs to show a tray icon, resolved for the platform and the theme of the system.
 *
 * @property icon the image of the icon.
 * @property tooltip the text shown while the pointer rests on the icon.
 * @property entries the entries of the menu of the icon; an empty list shows no menu.
 * @property state the state through which the application sends notifications.
 * @property onAction called when the user activates the icon.
 * @since 1.0.0
 */
@DavilsInternal
@Immutable
public class TraySpec(
    public val icon: Painter,
    public val tooltip: String,
    public val entries: List<NativeMenuEntry>,
    public val state: TrayState,
    public val onAction: () -> Unit,
) {
    /**
     * Returns a copy of this [TraySpec] with the given values replaced and every other value kept.
     *
     * @param icon the new value of [icon].
     * @param tooltip the new value of [tooltip].
     * @param entries the new value of [entries].
     * @param state the new value of [state].
     * @param onAction the new value of [onAction].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        icon: Painter = this.icon,
        tooltip: String = this.tooltip,
        entries: List<NativeMenuEntry> = this.entries,
        state: TrayState = this.state,
        onAction: () -> Unit = this.onAction,
    ): TraySpec = TraySpec(
        icon = icon,
        tooltip = tooltip,
        entries = entries,
        state = state,
        onAction = onAction,
    )

    override fun equals(other: Any?): Boolean = other is TraySpec && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TraySpec(" +
        "icon=$icon, " +
        "tooltip=$tooltip, " +
        "entries=$entries, " +
        "state=$state, " +
        "onAction=$onAction)"

    private fun values(): List<Any?> = listOf(
        icon,
        tooltip,
        entries,
        state,
        onAction,
    )
}
