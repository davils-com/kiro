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

package com.davils.kiro.ui.components.datatable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.table.defaultTableStyle
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ArrowDown
import com.davils.kiro.ui.icons.lucide.ArrowUp
import com.davils.kiro.ui.icons.lucide.ArrowUpDown
import com.davils.kiro.ui.icons.lucide.Lucide

/**
 * Resolves the style of a [DataTable] for a theme.
 *
 * Replace it in the components of a theme under [DataTableStyleKey] to change the style of every data table.
 *
 * @since 1.0.0
 */
public fun interface DataTableStyles {
    /**
     * Returns the style of a [DataTable] in a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the data table.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): DataTableStyle

    /**
     * Holds the default [DataTableStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [DataTable], derived from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: DataTableStyles = DataTableStyles(::defaultDataTableStyle)
    }
}

/**
 * Returns the style of a [DataTable] in the theme of the composition, resolved by the [DataTableStyles] under
 * [DataTableStyleKey].
 *
 * @return the style of the data table.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun dataTableStyle(): DataTableStyle = DataTableStyleKey.current.resolve(theme)

internal fun defaultDataTableStyle(theme: Theme): DataTableStyle = DataTableStyle(
    table = defaultTableStyle(theme),
    emptyHeight = 96.dp,
    unsortedIcon = Lucide.ArrowUpDown,
    ascendingIcon = Lucide.ArrowUp,
    descendingIcon = Lucide.ArrowDown,
)

/**
 * The key of the [DataTableStyles] of a [DataTable] in the components of a theme; its default is
 * [DataTableStyles.Default].
 *
 * @since 1.0.0
 */
public val DataTableStyleKey: StyleKey<DataTableStyles> = StyleKey(name = "data-table") { _ -> DataTableStyles.Default }
