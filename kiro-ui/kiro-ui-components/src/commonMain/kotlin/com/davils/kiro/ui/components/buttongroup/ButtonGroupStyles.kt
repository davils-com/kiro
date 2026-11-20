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

package com.davils.kiro.ui.components.buttongroup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [ButtonGroupStyle] of a theme.
 *
 * The styles of a theme are found under [ButtonGroupStyleKey]. Override them in [Theme.components] to change every
 * button group of the theme.
 *
 * @since 1.0.0
 */
public fun interface ButtonGroupStyles {
    /**
     * Returns the style of a button group for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a button group.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ButtonGroupStyle

    /**
     * Holds the default [ButtonGroupStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default button group styles, which derive the style from the tokens of the theme alone.
         *
         * @since 1.0.0
         */
        public val Default: ButtonGroupStyles = ButtonGroupStyles(::defaultButtonGroupStyle)
    }
}

/**
 * Returns the style of a button group in the current theme: the [ButtonGroupStyles] under [ButtonGroupStyleKey],
 * changed by the overrides of [Theme.components]. It is the default style of [ButtonGroup], [ButtonGroupText] and
 * [ButtonGroupSeparator].
 *
 * @return the style of a button group in the current theme.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun buttonGroupStyle(): ButtonGroupStyle = ButtonGroupStyleKey.current.resolve(theme)

/**
 * The key of the button group styles in [Theme.components]. Its default is [ButtonGroupStyles.Default].
 *
 * @since 1.0.0
 */
public val ButtonGroupStyleKey: StyleKey<ButtonGroupStyles> = StyleKey(
    name = "button-group",
) { _ -> ButtonGroupStyles.Default }
