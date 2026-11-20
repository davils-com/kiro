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
 * The color roles of a theme. Every color that a component of Kiro draws comes from one of these roles, directly or
 * through the style of the component, so replacing a role recolors every component that uses it.
 *
 * @property background the color of the page behind every surface.
 * @property foreground the color of text and icons on [background].
 * @property card the color of raised surfaces such as cards.
 * @property cardForeground the color of text and icons on [card].
 * @property popover the color of floating surfaces such as popovers, menus and tooltips.
 * @property popoverForeground the color of text and icons on [popover].
 * @property primary the color of the most important action and of selected controls.
 * @property primaryForeground the color of text and icons on [primary].
 * @property secondary the color of secondary actions.
 * @property secondaryForeground the color of text and icons on [secondary].
 * @property muted the color of subdued surfaces such as tracks and placeholders.
 * @property mutedForeground the color of subdued text such as descriptions and hints.
 * @property accent the color of hovered and highlighted items.
 * @property accentForeground the color of text and icons on [accent].
 * @property destructive the color of destructive actions and of invalid input.
 * @property border the color of borders and separators.
 * @property input the color of the borders and fills of input controls.
 * @property ring the color of the focus ring.
 * @property brand the color of the brand, for marks and logos.
 * @property line the color of fine lines such as grid lines and dividers inside content.
 * @property scrim the color laid over the page behind a modal surface, such as a dialog or a sheet.
 * @property shadow the color of every shadow, which the elevation of the theme fades to its strength.
 * @property knob the color of the knob of a control, such as the thumb of a slider.
 * @property chart the colors of the series of a chart.
 * @property sidebar the colors of a sidebar.
 * @property status the colors of the status of a message, such as an alert or a toast.
 * @since 0.1.0
 */
@Immutable
public class Colors(
    public val background: Color,
    public val foreground: Color,
    public val card: Color,
    public val cardForeground: Color,
    public val popover: Color,
    public val popoverForeground: Color,
    public val primary: Color,
    public val primaryForeground: Color,
    public val secondary: Color,
    public val secondaryForeground: Color,
    public val muted: Color,
    public val mutedForeground: Color,
    public val accent: Color,
    public val accentForeground: Color,
    public val destructive: Color,
    public val border: Color,
    public val input: Color,
    public val ring: Color,
    public val brand: Color,
    public val line: Color,
    public val scrim: Color,
    public val shadow: Color,
    public val knob: Color,
    public val chart: ChartColors,
    public val sidebar: SidebarColors,
    public val status: StatusColors,
) {
    /**
     * Returns a copy of this [Colors] with the given values replaced and every other value kept.
     *
     * @param background the new value of [background].
     * @param foreground the new value of [foreground].
     * @param card the new value of [card].
     * @param cardForeground the new value of [cardForeground].
     * @param popover the new value of [popover].
     * @param popoverForeground the new value of [popoverForeground].
     * @param primary the new value of [primary].
     * @param primaryForeground the new value of [primaryForeground].
     * @param secondary the new value of [secondary].
     * @param secondaryForeground the new value of [secondaryForeground].
     * @param muted the new value of [muted].
     * @param mutedForeground the new value of [mutedForeground].
     * @param accent the new value of [accent].
     * @param accentForeground the new value of [accentForeground].
     * @param destructive the new value of [destructive].
     * @param border the new value of [border].
     * @param input the new value of [input].
     * @param ring the new value of [ring].
     * @param brand the new value of [brand].
     * @param line the new value of [line].
     * @param scrim the new value of [scrim].
     * @param shadow the new value of [shadow].
     * @param knob the new value of [knob].
     * @param chart the new value of [chart].
     * @param sidebar the new value of [sidebar].
     * @param status the new value of [status].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(
        background: Color = this.background,
        foreground: Color = this.foreground,
        card: Color = this.card,
        cardForeground: Color = this.cardForeground,
        popover: Color = this.popover,
        popoverForeground: Color = this.popoverForeground,
        primary: Color = this.primary,
        primaryForeground: Color = this.primaryForeground,
        secondary: Color = this.secondary,
        secondaryForeground: Color = this.secondaryForeground,
        muted: Color = this.muted,
        mutedForeground: Color = this.mutedForeground,
        accent: Color = this.accent,
        accentForeground: Color = this.accentForeground,
        destructive: Color = this.destructive,
        border: Color = this.border,
        input: Color = this.input,
        ring: Color = this.ring,
        brand: Color = this.brand,
        line: Color = this.line,
        scrim: Color = this.scrim,
        shadow: Color = this.shadow,
        knob: Color = this.knob,
        chart: ChartColors = this.chart,
        sidebar: SidebarColors = this.sidebar,
        status: StatusColors = this.status,
    ): Colors = Colors(
        background = background,
        foreground = foreground,
        card = card,
        cardForeground = cardForeground,
        popover = popover,
        popoverForeground = popoverForeground,
        primary = primary,
        primaryForeground = primaryForeground,
        secondary = secondary,
        secondaryForeground = secondaryForeground,
        muted = muted,
        mutedForeground = mutedForeground,
        accent = accent,
        accentForeground = accentForeground,
        destructive = destructive,
        border = border,
        input = input,
        ring = ring,
        brand = brand,
        line = line,
        scrim = scrim,
        shadow = shadow,
        knob = knob,
        chart = chart,
        sidebar = sidebar,
        status = status,
    )

    override fun equals(other: Any?): Boolean = other is Colors && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Colors(" +
        "background=$background, " +
        "foreground=$foreground, " +
        "card=$card, " +
        "cardForeground=$cardForeground, " +
        "popover=$popover, " +
        "popoverForeground=$popoverForeground, " +
        "primary=$primary, " +
        "primaryForeground=$primaryForeground, " +
        "secondary=$secondary, " +
        "secondaryForeground=$secondaryForeground, " +
        "muted=$muted, " +
        "mutedForeground=$mutedForeground, " +
        "accent=$accent, " +
        "accentForeground=$accentForeground, " +
        "destructive=$destructive, " +
        "border=$border, " +
        "input=$input, " +
        "ring=$ring, " +
        "brand=$brand, " +
        "line=$line, " +
        "scrim=$scrim, " +
        "shadow=$shadow, " +
        "knob=$knob, " +
        "chart=$chart, " +
        "sidebar=$sidebar, " +
        "status=$status)"

    private fun values(): List<Any?> = listOf(
        background,
        foreground,
        card,
        cardForeground,
        popover,
        popoverForeground,
        primary,
        primaryForeground,
        secondary,
        secondaryForeground,
        muted,
        mutedForeground,
        accent,
        accentForeground,
        destructive,
        border,
        input,
        ring,
        brand,
        line,
        scrim,
        shadow,
        knob,
        chart,
        sidebar,
        status,
    )

    /**
     * The color schemes that ship with Kiro.
     *
     * @since 0.1.0
     */
    public companion object {
        /**
         * Returns the light color scheme of Kiro: dark text on white surfaces with a violet primary color.
         *
         * @return the light color scheme.
         * @since 0.1.0
         */
        public fun light(): Colors = ColorPresets.light

        /**
         * Returns the dark color scheme of Kiro: light text on near black surfaces with a violet primary color.
         *
         * @return the dark color scheme.
         * @since 0.1.0
         */
        public fun dark(): Colors = ColorPresets.dark
    }
}
