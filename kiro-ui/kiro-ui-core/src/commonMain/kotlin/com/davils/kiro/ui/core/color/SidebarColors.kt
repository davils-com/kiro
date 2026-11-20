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

package com.davils.kiro.ui.core.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * The colors of a sidebar, which may differ from the colors of the page it sits on.
 *
 * @property background the color of the sidebar.
 * @property foreground the color of text and icons in the sidebar.
 * @property primary the color of the active item.
 * @property primaryForeground the color of text and icons on [primary].
 * @property accent the color of hovered items.
 * @property accentForeground the color of text and icons on [accent].
 * @property border the color of the borders of the sidebar.
 * @property ring the color of the focus ring inside the sidebar.
 * @since 1.0.0
 */
@Immutable
public class SidebarColors(
    public val background: Color,
    public val foreground: Color,
    public val primary: Color,
    public val primaryForeground: Color,
    public val accent: Color,
    public val accentForeground: Color,
    public val border: Color,
    public val ring: Color,
) {
    /**
     * Returns a copy of this [SidebarColors] with the given values replaced and every other value kept.
     *
     * @param background the new value of [background].
     * @param foreground the new value of [foreground].
     * @param primary the new value of [primary].
     * @param primaryForeground the new value of [primaryForeground].
     * @param accent the new value of [accent].
     * @param accentForeground the new value of [accentForeground].
     * @param border the new value of [border].
     * @param ring the new value of [ring].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        background: Color = this.background,
        foreground: Color = this.foreground,
        primary: Color = this.primary,
        primaryForeground: Color = this.primaryForeground,
        accent: Color = this.accent,
        accentForeground: Color = this.accentForeground,
        border: Color = this.border,
        ring: Color = this.ring,
    ): SidebarColors = SidebarColors(
        background = background,
        foreground = foreground,
        primary = primary,
        primaryForeground = primaryForeground,
        accent = accent,
        accentForeground = accentForeground,
        border = border,
        ring = ring,
    )

    override fun equals(other: Any?): Boolean = other is SidebarColors && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SidebarColors(" +
        "background=$background, " +
        "foreground=$foreground, " +
        "primary=$primary, " +
        "primaryForeground=$primaryForeground, " +
        "accent=$accent, " +
        "accentForeground=$accentForeground, " +
        "border=$border, " +
        "ring=$ring)"

    private fun values(): List<Any?> = listOf(
        background,
        foreground,
        primary,
        primaryForeground,
        accent,
        accentForeground,
        border,
        ring,
    )
}
