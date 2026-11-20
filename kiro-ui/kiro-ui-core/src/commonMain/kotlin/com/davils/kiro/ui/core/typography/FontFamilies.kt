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

package com.davils.kiro.ui.core.typography

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontFamily

/**
 * The font families of a theme.
 *
 * [Bundled] stands for the variable fonts that ship with Kiro, which can only be loaded inside a composition: a
 * [com.davils.kiro.ui.core.ThemeProvider] replaces it with the loaded fonts before it hands the theme to its content.
 * Read outside of a provider, its families are the generic families of the platform.
 *
 * @property sans the family of body text and controls.
 * @property heading the family of headings.
 * @property mono the family of code and of tabular numbers.
 * @since 0.1.0
 */
@Immutable
public class FontFamilies(
    public val sans: FontFamily,
    public val heading: FontFamily,
    public val mono: FontFamily,
) {
    internal val isBundled: Boolean
        get() = this === Bundled

    /**
     * Returns a copy of these font families with the given families replaced and every other family kept. The copy
     * never stands for the bundled fonts.
     *
     * @param sans the new value of [sans].
     * @param heading the new value of [heading].
     * @param mono the new value of [mono].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(
        sans: FontFamily = this.sans,
        heading: FontFamily = this.heading,
        mono: FontFamily = this.mono,
    ): FontFamilies = FontFamilies(sans = sans, heading = heading, mono = mono)

    override fun equals(other: Any?): Boolean = other is FontFamilies && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String {
        if (isBundled) return "FontFamilies.Bundled"
        return "FontFamilies(sans=$sans, heading=$heading, mono=$mono)"
    }

    private fun values(): List<Any?> = listOf(sans, heading, mono, isBundled)

    /**
     * The font families every theme can use.
     *
     * @since 0.1.0
     */
    public companion object {
        /**
         * The fonts that ship with Kiro: Inter for body text, Archivo Expanded for headings and Geist Mono for code.
         *
         * @since 0.1.0
         */
        public val Bundled: FontFamilies = FontFamilies(
            sans = FontFamily.SansSerif,
            heading = FontFamily.SansSerif,
            mono = FontFamily.Monospace,
        )

        /**
         * The generic families of the platform, for applications that ship no fonts of their own.
         *
         * @since 0.1.0
         */
        public val System: FontFamilies = FontFamilies(
            sans = FontFamily.SansSerif,
            heading = FontFamily.SansSerif,
            mono = FontFamily.Monospace,
        )
    }
}
