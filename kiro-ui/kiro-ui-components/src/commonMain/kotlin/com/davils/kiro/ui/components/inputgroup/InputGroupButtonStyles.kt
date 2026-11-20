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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.button.ButtonStyle
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the style of an [InputGroupButton] for a theme.
 *
 * Replace it in the components of a theme under [InputGroupButtonStyleKey] to change the style of every input group
 * button.
 *
 * @since 1.0.0
 */
public fun interface InputGroupButtonStyles {
    /**
     * Returns the style of an [InputGroupButton] in a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @param variant the variant of the button.
     * @param size the size of the button.
     * @return the style of the input group button.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, variant: ButtonVariant, size: InputGroupButtonSize): ButtonStyle

    /**
     * Holds the default [InputGroupButtonStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of an [InputGroupButton], derived from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: InputGroupButtonStyles = InputGroupButtonStyles(::defaultInputGroupButtonStyle)
    }
}

/**
 * The key of the [InputGroupButtonStyles] of an [InputGroupButton] in the components of a theme; its default is
 * [InputGroupButtonStyles.Default].
 *
 * @since 1.0.0
 */
public val InputGroupButtonStyleKey: StyleKey<InputGroupButtonStyles> = StyleKey(
    name = "input-group-button",
) { _ -> InputGroupButtonStyles.Default }

/**
 * Returns the style of an [InputGroupButton] in the theme of the composition, resolved by the [InputGroupButtonStyles]
 * under [InputGroupButtonStyleKey].
 *
 * @param variant the variant of the button.
 * @param size the size of the button.
 * @return the style of the input group button.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun inputGroupButtonStyle(variant: ButtonVariant, size: InputGroupButtonSize): ButtonStyle =
    InputGroupButtonStyleKey.current.resolve(theme, variant, size)
