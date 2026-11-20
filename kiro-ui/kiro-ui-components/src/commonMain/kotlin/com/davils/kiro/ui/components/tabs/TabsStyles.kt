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

package com.davils.kiro.ui.components.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [TabsStyle] of a theme and a variant.
 *
 * The styles of a theme are found under [TabsStyleKey]. Override them in [Theme.components] to change all tabs of the
 * theme.
 *
 * @since 1.0.0
 */
public fun interface TabsStyles {
    /**
     * Returns the style of tabs for a theme and a variant.
     *
     * @param theme the theme whose tokens the style follows.
     * @param variant the variant of the tabs.
     * @return the style of tabs of that variant.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, variant: TabsVariant): TabsStyle

    /**
     * Holds the default [TabsStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default tabs styles, which derive the style of each variant of Kiro from the tokens of the theme alone.
         *
         * @since 1.0.0
         */
        public val Default: TabsStyles = TabsStyles(::defaultTabsStyle)
    }
}

/**
 * The key of the tabs styles in [Theme.components]. Its default is [TabsStyles.Default].
 *
 * @since 1.0.0
 */
public val TabsStyleKey: StyleKey<TabsStyles> = StyleKey(name = "tabs") { _ -> TabsStyles.Default }

/**
 * Returns the style of tabs in the current theme: the [TabsStyles] under [TabsStyleKey], changed by the overrides of
 * [Theme.components]. It is the default style of [Tabs].
 *
 * @param variant the variant of the tabs.
 * @return the style of tabs of that variant.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun tabsStyle(variant: TabsVariant): TabsStyle = TabsStyleKey.current.resolve(theme, variant)
