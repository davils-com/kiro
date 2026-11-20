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

package com.davils.kiro.ui.components.toggle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the [ToggleStyle] of a toggle from the tokens of a theme.
 *
 * The components of a theme hold one under each key of this type; override a key in the components of a theme to change
 * the style of every toggle that reads it.
 *
 * @since 1.0.0
 */
public fun interface ToggleStyles {
    /**
     * Returns the style of a toggle for the tokens of a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @param variant the variant of the toggle.
     * @param size the size of the toggle.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, variant: ToggleVariant, size: ToggleSize): ToggleStyle

    /**
     * Holds the [ToggleStyles] that Kiro ships.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a toggle, derived only from the tokens of the theme; they know the variants of
         * [ToggleVariant] and the sizes of [ToggleSize] and throw an [IllegalArgumentException] for any other.
         *
         * @since 1.0.0
         */
        public val Default: ToggleStyles = ToggleStyles(::defaultToggleStyle)
    }
}

/**
 * The key of the styles of a toggle in the components of a theme, named `toggle`; its default is
 * [ToggleStyles.Default].
 *
 * @since 1.0.0
 */
public val ToggleStyleKey: StyleKey<ToggleStyles> = StyleKey(name = "toggle") { _ -> ToggleStyles.Default }

/**
 * Returns the style of a toggle in the theme of the composition, resolved through the styles that the components of the
 * theme hold under [ToggleStyleKey].
 *
 * @param variant the variant of the toggle.
 * @param size the size of the toggle.
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun toggleStyle(variant: ToggleVariant, size: ToggleSize): ToggleStyle =
    ToggleStyleKey.current.resolve(theme, variant, size)
