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

package com.davils.kiro.ui.components.sidebar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.primitive.motion.sizeTransition
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ChevronRight
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.PanelLeft

private const val LABEL_ALPHA: Float = 0.7f

public fun interface SidebarStyles {
    public fun resolve(theme: Theme): SidebarStyle

    public companion object {
        public val Default: SidebarStyles = SidebarStyles(::defaultSidebarStyle)
    }
}

public val SidebarStyleKey: StyleKey<SidebarStyles> = StyleKey(name = "sidebar") { _ -> SidebarStyles.Default }

@Composable
@ReadOnlyComposable
public fun sidebarStyle(): SidebarStyle = SidebarStyleKey.current.resolve(theme)

internal fun defaultSidebarStyle(theme: Theme): SidebarStyle {
    val sidebar = theme.colors.sidebar
    val scale = theme.typography.scale
    return SidebarStyle(
        width = 256.dp,
        iconWidth = 48.dp,
        container = sidebar.background,
        content = sidebar.foreground,
        border = sidebar.border,
        accent = sidebar.accent,
        accentContent = sidebar.accentForeground,
        ring = sidebar.ring,
        background = theme.colors.background,
        floatingPadding = 8.dp,
        floatingShape = RoundedCornerShape(theme.radii.lg),
        insetShape = RoundedCornerShape(theme.radii.xl),
        shadow = theme.shadows.sm,
        section = 8.dp,
        labelHeight = 32.dp,
        labelStyle = theme.textStyleOf(size = scale.xs, weight = FontWeight.Medium),
        labelColor = sidebar.foreground.copy(alpha = LABEL_ALPHA),
        buttonShape = RoundedCornerShape(theme.radii.md),
        buttonPadding = 8.dp,
        buttonGap = 8.dp,
        textStyle = theme.textStyleOf(size = scale.sm),
        smallTextStyle = theme.textStyleOf(size = scale.xs),
        iconSize = 16.dp,
        actionSize = 20.dp,
        badgeStyle = theme.textStyleOf(size = scale.xs, weight = FontWeight.Medium),
        subIndent = 14.dp,
        subPadding = 10.dp,
        subGap = 4.dp,
        subHeight = 28.dp,
        triggerIcon = Lucide.PanelLeft,
        toggleLabel = Messages.Sidebar.toggle,
        railWidth = 16.dp,
        widthAnimation = theme.sizeTransition(),
        chevron = Lucide.ChevronRight,
        brandSize = 32.dp,
        brandShape = RoundedCornerShape(theme.radii.lg),
        brandContainer = sidebar.primary,
        brandContent = sidebar.primaryForeground,
        brandTitleStyle = theme.textStyleOf(size = scale.sm, weight = FontWeight.SemiBold),
        brandSubtitleStyle = theme.textStyleOf(size = scale.xs),
        brandSubtitleColor = sidebar.foreground.copy(alpha = LABEL_ALPHA),
    )
}
