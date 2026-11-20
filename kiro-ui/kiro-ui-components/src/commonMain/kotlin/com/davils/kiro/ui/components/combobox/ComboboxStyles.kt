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

package com.davils.kiro.ui.components.combobox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.inputgroup.defaultInputGroupStyle
import com.davils.kiro.ui.components.menu.defaultMenuStyle
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.select.SelectSize
import com.davils.kiro.ui.components.select.defaultSelectStyle
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ChevronsUpDown
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Search

private const val SOFT: Float = 0.3f
private const val HALF: Float = 0.5f

/**
 * Resolves the [ComboboxStyle] of a [Combobox] for a theme.
 *
 * Register an implementation under [ComboboxStyleKey] in the components of a theme to change the style of every
 * combobox.
 *
 * @since 1.0.0
 */
public fun interface ComboboxStyles {
    /**
     * Returns the style of a combobox.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the combobox.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ComboboxStyle

    /**
     * Holds the default styles of a combobox.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a combobox, derived from the tokens of the theme and from the default styles of a
         * select, a menu and an input group.
         *
         * @since 1.0.0
         */
        public val Default: ComboboxStyles = ComboboxStyles(::defaultComboboxStyle)
    }
}

/**
 * Returns the style of a [Combobox], as the [ComboboxStyles] of the current theme resolve it.
 *
 * @return the style of the combobox.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun comboboxStyle(): ComboboxStyle = ComboboxStyleKey.current.resolve(theme)

internal fun defaultComboboxStyle(theme: Theme): ComboboxStyle {
    val search = defaultInputGroupStyle(theme)
    val soft = theme.colors.input.copy(alpha = SOFT)
    return ComboboxStyle(
        trigger = defaultSelectStyle(theme = theme, size = SelectSize.Default),
        triggerIcon = Lucide.ChevronsUpDown,
        minWidth = 200.dp,
        menu = defaultMenuStyle(theme).copy(gap = 8.dp),
        search = search.copy(
            colors = search.colors.copy(
                container = StateValue(default = soft),
                border = search.colors.border.copy(default = soft),
            ),
        ),
        searchIcon = Lucide.Search,
        searchIconAlpha = HALF,
        searchPlaceholder = Messages.Combobox.SEARCH,
        sideOffset = 6.dp,
        listMaxHeight = 240.dp,
        gap = 4.dp,
        emptyTextStyle = theme.textStyleOf(size = theme.typography.scale.sm),
        emptyColor = theme.colors.mutedForeground,
        emptyPadding = 16.dp,
    )
}

/**
 * The key of the [ComboboxStyles] of a [Combobox] in the components of a theme; its default is
 * [ComboboxStyles.Default].
 *
 * @since 1.0.0
 */
public val ComboboxStyleKey: StyleKey<ComboboxStyles> = StyleKey(name = "combobox") { _ -> ComboboxStyles.Default }
