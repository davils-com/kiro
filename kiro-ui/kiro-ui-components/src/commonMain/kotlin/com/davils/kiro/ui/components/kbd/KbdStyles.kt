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

package com.davils.kiro.ui.components.kbd

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [KbdStyle] of a theme.
 *
 * The styles of a theme are found under [KbdStyleKey]. Override them in [Theme.components] to change every keyboard key
 * of the theme.
 *
 * @since 1.0.0
 */
public fun interface KbdStyles {
    /**
     * Returns the style of a keyboard key for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a keyboard key.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): KbdStyle

    /**
     * Holds the default [KbdStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default keyboard key styles, which derive the style from the tokens of the theme alone.
         *
         * @since 1.0.0
         */
        public val Default: KbdStyles = KbdStyles(::defaultKbdStyle)
    }
}

/**
 * The key of the keyboard key styles in [Theme.components]. Its default is [KbdStyles.Default].
 *
 * @since 1.0.0
 */
public val KbdStyleKey: StyleKey<KbdStyles> = StyleKey(name = "kbd") { _ -> KbdStyles.Default }

/**
 * Returns the style of a keyboard key in the current theme: the [KbdStyles] under [KbdStyleKey], changed by the
 * overrides of [Theme.components]. It is the default style of [Kbd] and [KbdGroup].
 *
 * @return the style of a keyboard key in the current theme.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun kbdStyle(): KbdStyle = KbdStyleKey.current.resolve(theme)
