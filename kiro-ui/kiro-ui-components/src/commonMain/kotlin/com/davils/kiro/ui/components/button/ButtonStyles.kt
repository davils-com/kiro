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

package com.davils.kiro.ui.components.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [ButtonStyle] of a [Button] for a theme, a variant and a size.
 *
 * Register an implementation under [ButtonStyleKey] in the components of a theme to change the style of every button.
 *
 * @since 1.0.0
 */
public fun interface ButtonStyles {
    /**
     * Returns the style of a button with the given variant and size.
     *
     * @param theme the theme whose tokens the style follows.
     * @param variant the variant of the button.
     * @param size the size of the button.
     * @return the style of the button.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, variant: ButtonVariant, size: ButtonSize): ButtonStyle

    /**
     * Holds the default styles of a button.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a button, derived from the tokens of the theme for every [ButtonVariant] and
         * [ButtonSize] that Kiro declares.
         *
         * @since 1.0.0
         */
        public val Default: ButtonStyles = ButtonStyles(::defaultButtonStyle)
    }
}

/**
 * Returns the style of a [Button] with the given variant and size, as the [ButtonStyles] of the current theme
 * resolve it.
 *
 * @param variant the variant of the button.
 * @param size the size of the button.
 * @return the style of the button.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun buttonStyle(variant: ButtonVariant, size: ButtonSize): ButtonStyle =
    ButtonStyleKey.current.resolve(theme, variant, size)

/**
 * The key of the [ButtonStyles] of a [Button] in the components of a theme; its default is [ButtonStyles.Default].
 *
 * @since 1.0.0
 */
public val ButtonStyleKey: StyleKey<ButtonStyles> = StyleKey(name = "button") { _ -> ButtonStyles.Default }
