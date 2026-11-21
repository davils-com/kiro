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
import androidx.compose.ui.graphics.painter.Painter

/**
 * The images of a tray icon. On macOS the [template] image is shown when there is one: the system draws it in
 * the color of its menu bar, light or dark, as long as [com.davils.kiro.ui.window.application.DesktopEnvironment.prepare]
 * ran before the first window. Elsewhere, and on macOS without a template, [dark] is shown while the system uses a
 * dark theme and [light] otherwise.
 *
 * @property light the image shown while the system uses a light theme.
 * @property dark the image shown while the system uses a dark theme, [light] by default.
 * @property template the image macOS tints by itself, drawn in black with transparency, or `null` for none.
 * @since 1.0.0
 */
@Immutable
public class TrayIcon(
    public val light: Painter,
    public val dark: Painter = light,
    public val template: Painter? = null,
) {
    /**
     * Returns a copy of this [TrayIcon] with the given values replaced and every other value kept.
     *
     * @param light the new value of [light].
     * @param dark the new value of [dark].
     * @param template the new value of [template].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        light: Painter = this.light,
        dark: Painter = this.dark,
        template: Painter? = this.template,
    ): TrayIcon = TrayIcon(
        light = light,
        dark = dark,
        template = template,
    )

    override fun equals(other: Any?): Boolean = other is TrayIcon && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TrayIcon(" +
        "light=$light, " +
        "dark=$dark, " +
        "template=${template ?: "null"})"

    private fun values(): List<Any?> = listOf(
        light,
        dark,
        template,
    )
}
