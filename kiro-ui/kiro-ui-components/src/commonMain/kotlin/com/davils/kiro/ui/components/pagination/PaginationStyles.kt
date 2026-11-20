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

package com.davils.kiro.ui.components.pagination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ChevronLeft
import com.davils.kiro.ui.icons.lucide.ChevronRight
import com.davils.kiro.ui.icons.lucide.Ellipsis
import com.davils.kiro.ui.icons.lucide.Lucide

private const val EDGE_STEPS: Float = 1.5f

public fun interface PaginationStyles {
    public fun resolve(theme: Theme): PaginationStyle

    public companion object {
        public val Default: PaginationStyles = PaginationStyles(::defaultPaginationStyle)
    }
}

public val PaginationStyleKey: StyleKey<PaginationStyles> = StyleKey(
    name = "pagination",
) { _ -> PaginationStyles.Default }

@Composable
@ReadOnlyComposable
public fun paginationStyle(): PaginationStyle = PaginationStyleKey.current.resolve(theme)

internal fun defaultPaginationStyle(theme: Theme): PaginationStyle = PaginationStyle(
    gap = theme.spacing.unit / 2,
    edgePadding = theme.spacing.unit * EDGE_STEPS,
    previousIcon = Lucide.ChevronLeft,
    nextIcon = Lucide.ChevronRight,
    ellipsisIcon = Lucide.Ellipsis,
    ellipsisBox = 32.dp,
    iconSize = 16.dp,
)
