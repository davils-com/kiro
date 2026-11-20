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
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the style of an [InputGroup] for a theme.
 *
 * Replace it in the components of a theme under [InputGroupStyleKey] to change the style of every input group.
 *
 * @since 1.0.0
 */
public fun interface InputGroupStyles {
    /**
     * Returns the style of an [InputGroup] in a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the input group.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): InputGroupStyle

    /**
     * Holds the default [InputGroupStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of an [InputGroup], derived from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: InputGroupStyles = InputGroupStyles(::defaultInputGroupStyle)
    }
}

/**
 * The key of the [InputGroupStyles] of an [InputGroup] in the components of a theme; its default is
 * [InputGroupStyles.Default].
 *
 * @since 1.0.0
 */
public val InputGroupStyleKey: StyleKey<InputGroupStyles> = StyleKey(
    name = "input-group",
) { _ -> InputGroupStyles.Default }

/**
 * Returns the style of an [InputGroup] in the theme of the composition, resolved by the [InputGroupStyles] under
 * [InputGroupStyleKey].
 *
 * @return the style of the input group.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun inputGroupStyle(): InputGroupStyle = InputGroupStyleKey.current.resolve(theme)
