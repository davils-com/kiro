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

package com.davils.kiro.ui.components.input

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the [InputStyle] of a text field from the tokens of a theme.
 *
 * The components of a theme hold one under each key of this type; override a key in the components of a theme to change
 * the style of every text field that reads it.
 *
 * @since 1.0.0
 */
public fun interface InputStyles {
    /**
     * Returns the style of a text field for the tokens of a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): InputStyle

    /**
     * Holds the [InputStyles] that Kiro ships.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of an [Input], derived only from the tokens of the theme; the field is one line high.
         *
         * @since 1.0.0
         */
        public val Input: InputStyles = InputStyles(::defaultInputStyle)

        /**
         * The default styles of a [Textarea], derived only from the tokens of the theme; the field is higher than an
         * [Input] and grows with its text.
         *
         * @since 1.0.0
         */
        public val Textarea: InputStyles = InputStyles(::defaultTextareaStyle)
    }
}

/**
 * The key of the styles of a single line field in the components of a theme, named `input`; its default is
 * [InputStyles.Input].
 *
 * @since 1.0.0
 */
public val InputStyleKey: StyleKey<InputStyles> = StyleKey(name = "input") { _ -> InputStyles.Input }

/**
 * The key of the styles of a multi line field in the components of a theme, named `textarea`; its default is
 * [InputStyles.Textarea].
 *
 * @since 1.0.0
 */
public val TextareaStyleKey: StyleKey<InputStyles> = StyleKey(name = "textarea") { _ -> InputStyles.Textarea }

/**
 * Returns the style of a single line field in the theme of the composition, resolved through the styles that the
 * components of the theme hold under [InputStyleKey].
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun inputStyle(): InputStyle = InputStyleKey.current.resolve(theme)

/**
 * Returns the style of a multi line field in the theme of the composition, resolved through the styles that the
 * components of the theme hold under [TextareaStyleKey].
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun textareaStyle(): InputStyle = TextareaStyleKey.current.resolve(theme)
