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

package com.davils.kiro.ui.components.brand

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the style of a [BrandMark] from the tokens of a theme.
 *
 * Register an implementation under [BrandMarkStyleKey] in the components of a theme to change how every brand mark
 * looks.
 *
 * @since 1.0.0
 */
public fun interface BrandMarkStyles {
    /**
     * Returns the style of a [BrandMark] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the brand mark.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): BrandMarkStyle

    /**
     * Holds the default [BrandMarkStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [BrandMark], with a gradient from the brand color to the foreground color of the
         * theme.
         *
         * @since 1.0.0
         */
        public val Default: BrandMarkStyles = BrandMarkStyles(::defaultBrandMarkStyle)
    }
}

/**
 * The key of the styles of a [BrandMark] in the components of a theme, with [BrandMarkStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val BrandMarkStyleKey: StyleKey<BrandMarkStyles> = StyleKey(name = "brand-mark") { _ -> BrandMarkStyles.Default }

/**
 * Returns the style of a [BrandMark] in the current theme, with the overrides of its components.
 *
 * @return the style of the brand mark.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun brandMarkStyle(): BrandMarkStyle = BrandMarkStyleKey.current.resolve(theme)
