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

package com.davils.kiro.ui.components.empty

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [EmptyStyle] of a theme.
 *
 * The styles of a theme are found under [EmptyStyleKey]. Override them in [Theme.components] to change every empty
 * state of the theme.
 *
 * @since 1.0.0
 */
public fun interface EmptyStyles {
    /**
     * Returns the style of an empty state for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of an empty state.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): EmptyStyle

    /**
     * Holds the default [EmptyStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default empty state styles, which derive the style from the tokens of the theme alone.
         *
         * @since 1.0.0
         */
        public val Default: EmptyStyles = EmptyStyles(::defaultEmptyStyle)
    }
}

/**
 * Returns the style of an empty state in the current theme: the [EmptyStyles] under [EmptyStyleKey], changed by the
 * overrides of [Theme.components]. It is the default style of [Empty].
 *
 * @return the style of an empty state in the current theme.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun emptyStyle(): EmptyStyle = EmptyStyleKey.current.resolve(theme)

/**
 * The key of the empty state styles in [Theme.components]. Its default is [EmptyStyles.Default].
 *
 * @since 1.0.0
 */
public val EmptyStyleKey: StyleKey<EmptyStyles> = StyleKey(name = "empty") { _ -> EmptyStyles.Default }
