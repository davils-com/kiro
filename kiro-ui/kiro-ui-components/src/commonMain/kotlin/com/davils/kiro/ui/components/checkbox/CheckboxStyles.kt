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

package com.davils.kiro.ui.components.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [CheckboxStyle] of a [Checkbox] for a theme.
 *
 * Register your own under [CheckboxStyleKey] in the component styles of a theme to change every checkbox.
 *
 * @since 1.0.0
 */
public fun interface CheckboxStyles {
    /**
     * Returns the style of a checkbox.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a checkbox.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): CheckboxStyle

    /**
     * Holds the default checkbox styles.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default checkbox styles, derived from the colors, the focus ring and the motion of the theme, with a
         * check and a minus as marks.
         *
         * @since 1.0.0
         */
        public val Default: CheckboxStyles = CheckboxStyles(::defaultCheckboxStyle)
    }
}

/**
 * Returns the checkbox style of the current theme.
 *
 * @return the style that the [CheckboxStyles] registered under [CheckboxStyleKey] resolve.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun checkboxStyle(): CheckboxStyle = CheckboxStyleKey.current.resolve(theme)

/**
 * The key of the checkbox styles in the component styles of a theme, named `checkbox`.
 *
 * @since 1.0.0
 */
public val CheckboxStyleKey: StyleKey<CheckboxStyles> = StyleKey(name = "checkbox") { _ -> CheckboxStyles.Default }
