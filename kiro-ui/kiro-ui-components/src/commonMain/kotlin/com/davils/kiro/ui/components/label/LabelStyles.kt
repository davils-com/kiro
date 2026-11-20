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

package com.davils.kiro.ui.components.label

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the style of a [Label] from the tokens of a theme.
 *
 * Register an implementation under [LabelStyleKey] in the components of a theme to change how every label looks.
 *
 * @since 1.0.0
 */
public fun interface LabelStyles {
    /**
     * Returns the style of a [Label] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the label.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): LabelStyle

    /**
     * Holds the default [LabelStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [Label], in the small text size and a medium weight of the theme.
         *
         * @since 1.0.0
         */
        public val Default: LabelStyles = LabelStyles(::defaultLabelStyle)
    }
}

/**
 * The key of the styles of a [Label] in the components of a theme, with [LabelStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val LabelStyleKey: StyleKey<LabelStyles> = StyleKey(name = "label") { _ -> LabelStyles.Default }

/**
 * Returns the style of a [Label] in the current theme, with the overrides of its components.
 *
 * @return the style of the label.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun labelStyle(): LabelStyle = LabelStyleKey.current.resolve(theme)
