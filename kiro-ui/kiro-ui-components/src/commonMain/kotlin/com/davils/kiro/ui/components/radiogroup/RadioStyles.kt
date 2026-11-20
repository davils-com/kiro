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

package com.davils.kiro.ui.components.radiogroup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the [RadioStyle] of a radio item from the tokens of a theme.
 *
 * The components of a theme hold one under each key of this type; override a key in the components of a theme to change
 * the style of every radio item that reads it.
 *
 * @since 1.0.0
 */
public fun interface RadioStyles {
    /**
     * Returns the style of a radio item for the tokens of a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): RadioStyle

    /**
     * Holds the [RadioStyles] that Kiro ships.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a radio item, derived only from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: RadioStyles = RadioStyles(::defaultRadioStyle)
    }
}

/**
 * The key of the styles of a radio item in the components of a theme, named `radio`; its default is
 * [RadioStyles.Default].
 *
 * @since 1.0.0
 */
public val RadioStyleKey: StyleKey<RadioStyles> = StyleKey(name = "radio") { _ -> RadioStyles.Default }

/**
 * Returns the style of a radio item in the theme of the composition, resolved through the styles that the components of
 * the theme hold under [RadioStyleKey].
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun radioStyle(): RadioStyle = RadioStyleKey.current.resolve(theme)
