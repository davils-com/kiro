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

package com.davils.kiro.ui.components.toggle

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.controlColors
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.mix
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.core.typography.TextSize

private const val DISABLED_ALPHA: Float = 0.5f
private const val PRESS_MIX_LIGHT: Float = 0.08f
private const val PRESS_MIX_DARK: Float = 0.15f

private val text = TextSize(fontSize = 14.sp, lineHeight = 20.sp)

private val metricsBySize: Map<ToggleSize, ToggleMetrics> = mapOf(
    ToggleSize.Default to ToggleMetrics(
        height = 32.dp,
        iconEdgePadding = 8.dp,
        radiusCap = null,
        iconSize = 16.dp,
        textSize = text,
    ),
    ToggleSize.Sm to ToggleMetrics(
        height = 28.dp,
        iconEdgePadding = 6.dp,
        radiusCap = 12.dp,
        iconSize = 14.dp,
        textSize = TextSize(fontSize = 12.8.sp, lineHeight = 16.sp),
    ),
    ToggleSize.Lg to ToggleMetrics(
        height = 36.dp,
        iconEdgePadding = 8.dp,
        radiusCap = null,
        iconSize = 16.dp,
        textSize = text,
    ),
)

private val bordered: Set<ToggleVariant> = setOf(ToggleVariant.Outline)

internal fun defaultToggleStyle(theme: Theme, variant: ToggleVariant, size: ToggleSize): ToggleStyle {
    val metrics = requireNotNull(metricsBySize[size]) { "No default style for the toggle size $size." }
    require(variant in knownVariants) { "No default style for the toggle variant $variant." }
    val isBordered = variant in bordered
    val radius = metrics.radiusCap?.let { cap -> minOf(a = theme.radii.md, b = cap) } ?: theme.radii.lg
    return ToggleStyle(
        height = metrics.height,
        minWidth = metrics.height,
        horizontalPadding = 10.dp,
        iconEdgePadding = metrics.iconEdgePadding,
        gap = 4.dp,
        shape = RoundedCornerShape(radius),
        textStyle = theme.textStyleOf(size = metrics.textSize, weight = FontWeight.Medium),
        iconSize = metrics.iconSize,
        colors = theme.controlColors(
            container = StateValue(
                default = Color.Transparent,
                pressed = pressedOf(theme = theme, base = theme.colors.muted),
            ),
            content = StateValue(default = theme.colors.foreground),
            border = borderOf(isBordered = isBordered, theme = theme),
        ),
        checkedColors = checkedColorsOf(theme = theme, isBordered = isBordered),
        borderWidth = borderWidthOf(isBordered),
        ringWidth = theme.focusRing.width,
        disabledAlpha = DISABLED_ALPHA,
        colorAnimation = theme.colorTransition(),
    )
}

private val knownVariants: Set<ToggleVariant> = setOf(ToggleVariant.Default, ToggleVariant.Outline)

private fun checkedColorsOf(theme: Theme, isBordered: Boolean): StateColors {
    val colors = theme.colors
    return theme.controlColors(
        container = StateValue(
            default = colors.secondary,
            pressed = pressedOf(theme = theme, base = colors.secondary),
        ),
        content = StateValue(default = colors.secondaryForeground),
        border = borderOf(isBordered = isBordered, theme = theme),
    )
}

private fun pressedOf(theme: Theme, base: Color): Color {
    val amount = PRESS_MIX_DARK.takeIf { theme.isDark } ?: PRESS_MIX_LIGHT
    return base.mix(theme.colors.foreground, amount = amount)
}

private fun borderOf(isBordered: Boolean, theme: Theme): Color {
    if (isBordered) return theme.colors.input
    return Color.Transparent
}

private fun borderWidthOf(isBordered: Boolean): Dp {
    if (isBordered) return 1.dp
    return 0.dp
}
