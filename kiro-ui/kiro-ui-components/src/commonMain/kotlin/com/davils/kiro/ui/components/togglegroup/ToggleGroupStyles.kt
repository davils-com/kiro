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

package com.davils.kiro.ui.components.togglegroup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.components.toggle.ToggleSize
import com.davils.kiro.ui.components.toggle.ToggleVariant
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the [ToggleGroupStyle] of a [ToggleGroup] of a variant and a size from a theme; the value of
 * [ToggleGroupStyleKey] in the component styles of a theme.
 *
 * @since 1.0.0
 */
public fun interface ToggleGroupStyles {
    /**
     * Returns the style of a [ToggleGroup] of a variant and a size for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @param variant the variant of the toggles.
     * @param size the size of the toggles.
     * @return the style of the toggle group.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, variant: ToggleVariant, size: ToggleSize): ToggleGroupStyle

    /**
     * Holds the default styles of a [ToggleGroup].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a [ToggleGroup], derived only from the tokens of the theme and the default style of a
         * toggle.
         *
         * @since 1.0.0
         */
        public val Default: ToggleGroupStyles = ToggleGroupStyles(::defaultToggleGroupStyle)
    }
}

/**
 * The key of the styles of a [ToggleGroup] in the component styles of a theme.
 *
 * @since 1.0.0
 */
public val ToggleGroupStyleKey: StyleKey<ToggleGroupStyles> = StyleKey(
    name = "toggle-group",
) { _ -> ToggleGroupStyles.Default }

/**
 * Returns the style of a [ToggleGroup] of a variant and a size in the theme of the composition, with the overrides of
 * its component styles.
 *
 * @param variant the variant of the toggles.
 * @param size the size of the toggles.
 * @return the style of the toggle group.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun toggleGroupStyle(
    variant: ToggleVariant = ToggleVariant.Default,
    size: ToggleSize = ToggleSize.Default,
): ToggleGroupStyle = ToggleGroupStyleKey.current.resolve(theme, variant, size)
