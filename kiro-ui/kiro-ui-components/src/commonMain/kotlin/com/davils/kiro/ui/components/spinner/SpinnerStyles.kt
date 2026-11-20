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

package com.davils.kiro.ui.components.spinner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the [SpinnerStyle] from the tokens of a theme.
 *
 * Register an implementation for [SpinnerStyleKey] in the components of a theme to change the style of every spinner.
 *
 * @since 1.0.0
 */
public fun interface SpinnerStyles {
    /**
     * Returns the style of a spinner under a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a spinner.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SpinnerStyle

    /**
     * Holds the default spinner styles.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default spinner styles: the circle loader icon of Lucide, which turns unless the motion of a theme is
         * reduced.
         *
         * @since 1.0.0
         */
        public val Default: SpinnerStyles = SpinnerStyles(::defaultSpinnerStyle)
    }
}

/**
 * The key of the spinner styles in the components of a theme; its default is [SpinnerStyles.Default].
 *
 * @since 1.0.0
 */
public val SpinnerStyleKey: StyleKey<SpinnerStyles> = StyleKey(name = "spinner") { _ -> SpinnerStyles.Default }

/**
 * Returns the spinner style of the current theme, with the overrides of the theme for [SpinnerStyleKey].
 *
 * @return the style of a spinner.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun spinnerStyle(): SpinnerStyle = SpinnerStyleKey.current.resolve(theme)
