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

package com.davils.kiro.ui.components.nativeselect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [NativeSelectStyle] of a [NativeSelect] for a theme and a size.
 *
 * Register an implementation under [NativeSelectStyleKey] in the components of a theme to change the style of every
 * native select.
 *
 * @since 1.0.0
 */
public fun interface NativeSelectStyles {
    /**
     * Returns the style of a native select with the given size.
     *
     * @param theme the theme whose tokens the style follows.
     * @param size the size of the native select.
     * @return the style of the native select.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, size: NativeSelectSize): NativeSelectStyle

    /**
     * Holds the default styles of a native select.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a native select, derived from the tokens of the theme for every [NativeSelectSize]
         * that Kiro declares.
         *
         * @since 1.0.0
         */
        public val Default: NativeSelectStyles = NativeSelectStyles(::defaultNativeSelectStyle)
    }
}

/**
 * The key of the [NativeSelectStyles] of a [NativeSelect] in the components of a theme; its default is
 * [NativeSelectStyles.Default].
 *
 * @since 1.0.0
 */
public val NativeSelectStyleKey: StyleKey<NativeSelectStyles> = StyleKey(
    name = "native-select",
) { _ -> NativeSelectStyles.Default }

/**
 * Returns the style of a [NativeSelect] with the given size, as the [NativeSelectStyles] of the current theme
 * resolve it.
 *
 * @param size the size of the native select.
 * @return the style of the native select.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun nativeSelectStyle(size: NativeSelectSize): NativeSelectStyle =
    NativeSelectStyleKey.current.resolve(theme, size)
