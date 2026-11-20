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

package com.davils.kiro.ui.components.tabs

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.controlColors
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StateColors

private const val IDLE_ALPHA: Float = 0.6f
private const val DARK_ACTIVE: Float = 0.3f
private const val DISABLED_ALPHA: Float = 0.5f

private val known: Set<TabsVariant> = setOf(TabsVariant.Default, TabsVariant.Line)

internal fun defaultTabsStyle(theme: Theme, variant: TabsVariant): TabsStyle {
    require(variant in known) { "No default style for the tabs variant $variant." }
    val isLine = variant == TabsVariant.Line
    val colors = theme.colors
    return TabsStyle(
        gap = 8.dp,
        listContainer = colors.muted.takeUnless { isLine } ?: Color.Transparent,
        listShape = RoundedCornerShape(theme.radii.lg).takeUnless { isLine } ?: RectangleShape,
        listPadding = 3.dp,
        listHeight = 32.dp,
        listGap = 4.dp.takeIf { isLine } ?: 0.dp,
        triggerHeight = 25.dp,
        triggerShape = RoundedCornerShape(theme.radii.md),
        triggerHorizontalPadding = 6.dp,
        triggerIconEdgePadding = 4.dp,
        triggerGap = 6.dp,
        textStyle = theme.textStyleOf(size = theme.typography.scale.sm, weight = FontWeight.Medium),
        iconSize = 16.dp,
        colors = triggerColorsOf(theme = theme, isLine = isLine),
        borderWidth = 1.dp,
        ringWidth = theme.focusRing.width,
        activeShadow = theme.shadows.sm.takeUnless { isLine } ?: BoxShadow.None,
        indicator = TabsIndicator(color = colors.foreground, thickness = 2.dp, offset = 5.dp, verticalOffset = 4.dp)
            .takeIf { isLine },
        contentTextStyle = theme.textStyleOf(size = theme.typography.scale.sm),
        disabledAlpha = DISABLED_ALPHA,
        colorAnimation = theme.colorTransition(),
    )
}

private fun triggerColorsOf(theme: Theme, isLine: Boolean): StateColors {
    val colors = theme.colors
    val idle = colors.mutedForeground.takeIf { theme.isDark } ?: colors.foreground.copy(alpha = IDLE_ALPHA)
    val active = activeContainerOf(theme = theme).takeUnless { isLine } ?: Color.Transparent
    val activeBorder = colors.input.takeIf { theme.isDark && !isLine } ?: Color.Transparent
    val base = theme.controlColors(
        container = StateValue(default = Color.Transparent, selected = active),
        content = StateValue(default = idle, hovered = colors.foreground, selected = colors.foreground),
    )
    return base.copy(border = base.border.copy(selected = activeBorder))
}

private fun activeContainerOf(theme: Theme): Color {
    if (theme.isDark) return theme.colors.input.copy(alpha = DARK_ACTIVE)
    return theme.colors.background
}
