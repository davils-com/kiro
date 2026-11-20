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

package com.davils.kiro.ui.components.separator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [SeparatorStyle] of a theme.
 *
 * The styles of a theme are found under [SeparatorStyleKey]. Override them in [Theme.components] to change every
 * separator of the theme.
 *
 * @since 1.0.0
 */
public fun interface SeparatorStyles {
    /**
     * Returns the style of a separator for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a separator.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SeparatorStyle

    /**
     * Holds the default [SeparatorStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default separator styles, which derive the style from the tokens of the theme alone.
         *
         * @since 1.0.0
         */
        public val Default: SeparatorStyles = SeparatorStyles(::defaultSeparatorStyle)
    }
}

/**
 * The key of the separator styles in [Theme.components]. Its default is [SeparatorStyles.Default].
 *
 * @since 1.0.0
 */
public val SeparatorStyleKey: StyleKey<SeparatorStyles> = StyleKey(name = "separator") { _ -> SeparatorStyles.Default }

/**
 * Returns the style of a separator in the current theme: the [SeparatorStyles] under [SeparatorStyleKey], changed by
 * the overrides of [Theme.components]. It is the default style of [Separator].
 *
 * @return the style of a separator in the current theme.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun separatorStyle(): SeparatorStyle = SeparatorStyleKey.current.resolve(theme)
