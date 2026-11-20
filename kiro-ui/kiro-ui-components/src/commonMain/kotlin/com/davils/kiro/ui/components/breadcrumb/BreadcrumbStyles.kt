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

package com.davils.kiro.ui.components.breadcrumb

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ChevronRight
import com.davils.kiro.ui.icons.lucide.Ellipsis
import com.davils.kiro.ui.icons.lucide.Lucide

private const val LIGHT_MUTED_ALPHA: Float = 0.65f

public fun interface BreadcrumbStyles {
    public fun resolve(theme: Theme): BreadcrumbStyle

    public companion object {
        public val Default: BreadcrumbStyles = BreadcrumbStyles(::defaultBreadcrumbStyle)
    }
}

@Composable
@ReadOnlyComposable
public fun breadcrumbStyle(): BreadcrumbStyle = BreadcrumbStyleKey.current.resolve(theme)

internal fun defaultBreadcrumbStyle(theme: Theme): BreadcrumbStyle = BreadcrumbStyle(
    gap = 6.dp,
    itemGap = 4.dp,
    textStyle = theme.textStyleOf(size = theme.typography.scale.sm),
    color = theme.colors.mutedForeground.copy(alpha = theme.darkAware(light = LIGHT_MUTED_ALPHA, dark = 1f)),
    hoverColor = theme.colors.foreground,
    pageColor = theme.colors.foreground,
    separatorIcon = Lucide.ChevronRight,
    separatorSize = 14.dp,
    ellipsisIcon = Lucide.Ellipsis,
    ellipsisSize = 16.dp,
    ellipsisBox = 20.dp,
    ellipsisShape = RoundedCornerShape(theme.radii.md),
    ellipsisHover = theme.colors.accent,
)

public val BreadcrumbStyleKey: StyleKey<BreadcrumbStyles> = StyleKey(
    name = "breadcrumb",
) { _ -> BreadcrumbStyles.Default }
