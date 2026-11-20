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

package com.davils.kiro.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.intl.LocaleList
import com.davils.kiro.ui.core.color.Colors
import com.davils.kiro.ui.core.elevation.Shadows
import com.davils.kiro.ui.core.focus.FocusRing
import com.davils.kiro.ui.core.motion.Motion
import com.davils.kiro.ui.core.shape.Radii
import com.davils.kiro.ui.core.spacing.Spacing
import com.davils.kiro.ui.core.style.BorderWidths
import com.davils.kiro.ui.core.style.ComponentStyles
import com.davils.kiro.ui.core.typography.Typography

/**
 * The theme of Kiro: the tokens every component derives its look from, and the styles of the components on top of them.
 * Every value a component draws, from a color down to the duration of a transition, comes from the tokens or from the
 * [components] of the theme, so a theme can change every detail of every component. Provide it to a composition with
 * [ThemeProvider] and read it with [current].
 *
 * @property colors the color roles.
 * @property typography the fonts, the type scale, letter spacing and line heights.
 * @property radii the corner radii.
 * @property spacing the spacing, of which every distance and size is a number of steps.
 * @property borders the widths of borders.
 * @property shadows the elevation, drawn in the shadow color of [colors].
 * @property motion durations, curves and springs of transitions.
 * @property focusRing the focus ring of controls.
 * @property icons how icons are drawn by default.
 * @property components the overrides of the styles of the components.
 * @property isDark whether the theme is dark, which some styles use to pick their tints.
 * @property localeList the languages text is shaped for, or null for the languages of the platform.
 * @since 1.0.0
 */
@Immutable
public class Theme(
    public val colors: Colors,
    public val typography: Typography = Typography(),
    public val radii: Radii = Radii.from(),
    public val spacing: Spacing = Spacing(),
    public val borders: BorderWidths = BorderWidths(),
    public val shadows: Shadows = Shadows.from(color = colors.shadow),
    public val motion: Motion = Motion(),
    public val focusRing: FocusRing = FocusRing(),
    public val icons: IconTokens = IconTokens(),
    public val components: ComponentStyles = ComponentStyles.Default,
    public val isDark: Boolean = false,
    public val localeList: LocaleList? = null,
) {
    /**
     * Returns a copy of this [Theme] with the given values replaced and every other value kept.
     *
     * @param colors the new value of [colors].
     * @param typography the new value of [typography].
     * @param radii the new value of [radii].
     * @param spacing the new value of [spacing].
     * @param borders the new value of [borders].
     * @param shadows the new value of [shadows].
     * @param motion the new value of [motion].
     * @param focusRing the new value of [focusRing].
     * @param icons the new value of [icons].
     * @param components the new value of [components].
     * @param isDark the new value of [isDark].
     * @param localeList the new value of [localeList].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        colors: Colors = this.colors,
        typography: Typography = this.typography,
        radii: Radii = this.radii,
        spacing: Spacing = this.spacing,
        borders: BorderWidths = this.borders,
        shadows: Shadows = this.shadows,
        motion: Motion = this.motion,
        focusRing: FocusRing = this.focusRing,
        icons: IconTokens = this.icons,
        components: ComponentStyles = this.components,
        isDark: Boolean = this.isDark,
        localeList: LocaleList? = this.localeList,
    ): Theme = Theme(
        colors = colors,
        typography = typography,
        radii = radii,
        spacing = spacing,
        borders = borders,
        shadows = shadows,
        motion = motion,
        focusRing = focusRing,
        icons = icons,
        components = components,
        isDark = isDark,
        localeList = localeList,
    )

    override fun equals(other: Any?): Boolean = other is Theme && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Theme(" +
        "colors=$colors, " +
        "typography=$typography, " +
        "radii=$radii, " +
        "spacing=$spacing, " +
        "borders=$borders, " +
        "shadows=$shadows, " +
        "motion=$motion, " +
        "focusRing=$focusRing, " +
        "icons=$icons, " +
        "components=$components, " +
        "isDark=$isDark, " +
        "localeList=${localeList ?: "null"})"

    private fun values(): List<Any?> = listOf(
        colors,
        typography,
        radii,
        spacing,
        borders,
        shadows,
        motion,
        focusRing,
        icons,
        components,
        isDark,
        localeList,
    )

    /**
     * The themes that ship with Kiro and the theme of the current composition.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The theme of the current composition: the theme of the nearest [ThemeProvider], or the light theme
         * outside of any provider.
         *
         * @since 1.0.0
         */
        public val current: Theme
            @Composable
            @ReadOnlyComposable
            get() = LocalTheme.current

        /**
         * Returns the light theme of Kiro with the given colors.
         *
         * @param colors the colors of the theme.
         * @return the light theme.
         * @since 1.0.0
         */
        public fun light(colors: Colors = Colors.light()): Theme = Theme(colors = colors)

        /**
         * Returns the dark theme of Kiro with the given colors.
         *
         * @param colors the colors of the theme.
         * @return the dark theme.
         * @since 1.0.0
         */
        public fun dark(colors: Colors = Colors.dark()): Theme = Theme(colors = colors, isDark = true)
    }
}
