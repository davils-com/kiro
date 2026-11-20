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

public fun interface DataTableStyles {
    public fun resolve(theme: Theme): DataTableStyle

    public companion object {
        public val Default: DataTableStyles = DataTableStyles(::defaultDataTableStyle)
    }
}

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

public val DataTableStyleKey: StyleKey<DataTableStyles> = StyleKey(name = "data-table") { _ -> DataTableStyles.Default }
