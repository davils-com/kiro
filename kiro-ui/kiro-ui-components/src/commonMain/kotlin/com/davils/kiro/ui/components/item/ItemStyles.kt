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

package com.davils.kiro.ui.components.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the [ItemStyle] of each [ItemVariant] and [ItemSize] from the tokens of a theme.
 *
 * Register an implementation for [ItemStyleKey] in the components of a theme to change the style of every item.
 *
 * @since 1.0.0
 */
public fun interface ItemStyles {
    /**
     * Returns the style of an item of the given variant and size under a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @param variant the variant of the item.
     * @param size the size of the item.
     * @return the style of the item.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, variant: ItemVariant, size: ItemSize): ItemStyle

    /**
     * Holds the default item styles.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default item styles, derived from the colors, the typography, the radii and the focus ring of a theme.
         *
         * @since 1.0.0
         */
        public val Default: ItemStyles = ItemStyles(::defaultItemStyle)
    }
}

/**
 * The key of the item styles in the components of a theme; its default is [ItemStyles.Default].
 *
 * @since 1.0.0
 */
public val ItemStyleKey: StyleKey<ItemStyles> = StyleKey(name = "item") { _ -> ItemStyles.Default }

/**
 * Returns the item style of the current theme for a variant and a size, with the overrides of the theme for
 * [ItemStyleKey].
 *
 * @param variant the variant of the item.
 * @param size the size of the item.
 * @return the style of the item.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun itemStyle(variant: ItemVariant, size: ItemSize): ItemStyle =
    ItemStyleKey.current.resolve(theme, variant, size)
