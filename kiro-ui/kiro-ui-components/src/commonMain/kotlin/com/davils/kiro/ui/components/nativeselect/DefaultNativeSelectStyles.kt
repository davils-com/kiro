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

package com.davils.kiro.ui.components.nativeselect

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.controlColors
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.icons.lucide.Check
import com.davils.kiro.ui.icons.lucide.ChevronDown
import com.davils.kiro.ui.icons.lucide.Lucide

private const val DARK_FILL: Float = 0.3f
private const val DARK_HOVER: Float = 0.5f
private const val MENU_RING: Float = 0.1f
private const val DISABLED_ALPHA: Float = 0.5f

private val small: Set<NativeSelectSize> = setOf(NativeSelectSize.Sm)
private val known: Set<NativeSelectSize> = setOf(NativeSelectSize.Default, NativeSelectSize.Sm)

internal fun defaultNativeSelectStyle(theme: Theme, size: NativeSelectSize): NativeSelectStyle {
    require(size in known) { "No default style for the native select size $size." }
    val isSmall = size in small
    val radius = theme.radii.lg.takeUnless { isSmall } ?: minOf(theme.radii.md, 10.dp)
    val vertical = 4.dp.takeUnless { isSmall } ?: 2.dp
    return NativeSelectStyle(
        height = 32.dp.takeUnless { isSmall } ?: 28.dp,
        shape = RoundedCornerShape(radius),
        padding = PaddingValues(start = 10.dp, top = vertical, end = 32.dp, bottom = vertical),
        textStyle = theme.textStyleOf(size = theme.typography.scale.sm),
        colors = selectColorsOf(theme),
        borderWidth = 1.dp,
        ringWidth = theme.focusRing.width,
        icon = NativeSelectIcon(
            icon = Lucide.ChevronDown,
            size = 16.dp,
            color = theme.colors.mutedForeground,
            endPadding = 10.dp,
        ),
        menu = menuStyleOf(theme),
        disabledAlpha = DISABLED_ALPHA,
        colorAnimation = theme.colorTransition(),
    )
}

private fun selectColorsOf(theme: Theme): StateColors {
    val input = theme.colors.input
    val fill = input.copy(alpha = theme.darkAware(light = 0f, dark = DARK_FILL))
    val hovered = input.copy(alpha = DARK_HOVER).takeIf { theme.isDark }
    return theme.controlColors(
        container = StateValue(default = fill, hovered = hovered),
        content = StateValue(default = theme.colors.foreground),
        border = input,
    )
}

private fun menuStyleOf(theme: Theme): NativeSelectMenuStyle = NativeSelectMenuStyle(
    container = theme.colors.popover,
    content = theme.colors.popoverForeground,
    shape = RoundedCornerShape(theme.radii.lg),
    ring = Border(width = 1.dp, color = theme.colors.foreground.copy(alpha = MENU_RING)),
    shadow = theme.shadows.md,
    padding = 4.dp,
    offset = 4.dp,
    itemPadding = PaddingValues(start = 6.dp, top = 6.dp, end = 32.dp, bottom = 6.dp),
    itemShape = RoundedCornerShape(theme.radii.md),
    itemTextStyle = theme.textStyleOf(size = theme.typography.scale.sm),
    highlight = theme.colors.accent,
    highlightContent = theme.colors.accentForeground,
    groupPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp),
    groupTextStyle = theme.textStyleOf(size = theme.typography.scale.xs),
    groupColor = theme.colors.mutedForeground,
    check = NativeSelectIcon(icon = Lucide.Check, size = 16.dp, color = Color.Unspecified, endPadding = 8.dp),
    disabledAlpha = DISABLED_ALPHA,
)
