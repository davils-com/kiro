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

package com.davils.kiro.ui.components.button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.controlColors
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.mix
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.core.typography.TextSize

private const val HOVER_ALPHA: Float = 0.8f
private const val SECONDARY_HOVER_MIX: Float = 0.05f
private const val OUTLINE_DARK_ALPHA: Float = 0.3f
private const val OUTLINE_DARK_HOVER_ALPHA: Float = 0.25f
private const val GHOST_DARK_HOVER_ALPHA: Float = 0.5f
private const val DESTRUCTIVE_LIGHT: Float = 0.1f
private const val DESTRUCTIVE_DARK: Float = 0.2f
private const val DESTRUCTIVE_HOVER_LIGHT: Float = 0.2f
private const val DESTRUCTIVE_HOVER_DARK: Float = 0.3f
private const val DESTRUCTIVE_FOCUS_BORDER: Float = 0.4f
private const val DESTRUCTIVE_RING_LIGHT: Float = 0.2f
private const val DESTRUCTIVE_RING_DARK: Float = 0.4f
private const val DISABLED_ALPHA: Float = 0.5f

private val text = TextSize(fontSize = 14.sp, lineHeight = 20.sp)
private val smallText = TextSize(fontSize = 12.8.sp, lineHeight = 16.sp)
private val extraSmallText = TextSize(fontSize = 12.sp, lineHeight = 16.sp)

private val metricsBySize: Map<ButtonSize, ButtonMetrics> = mapOf(
    ButtonSize.Default to metrics(height = 32.dp, padding = 10.dp, iconEdge = 8.dp, gap = 6.dp),
    ButtonSize.Xs to metrics(height = 24.dp, padding = 8.dp, iconEdge = 6.dp, gap = 4.dp, cap = 10.dp)
        .copy(iconSize = 12.dp, textSize = extraSmallText),
    ButtonSize.Sm to metrics(height = 28.dp, padding = 10.dp, iconEdge = 6.dp, gap = 4.dp, cap = 12.dp)
        .copy(iconSize = 14.dp, textSize = smallText),
    ButtonSize.Lg to metrics(height = 36.dp, padding = 10.dp, iconEdge = 8.dp, gap = 6.dp),
    ButtonSize.Icon to square(height = 32.dp),
    ButtonSize.IconXs to square(height = 24.dp, cap = 10.dp).copy(iconSize = 12.dp),
    ButtonSize.IconSm to square(height = 28.dp, cap = 12.dp),
    ButtonSize.IconLg to square(height = 36.dp),
)

private val colorsByVariant: Map<ButtonVariant, (Theme) -> StateColors> = mapOf(
    ButtonVariant.Default to ::defaultColors,
    ButtonVariant.Outline to ::outlineColors,
    ButtonVariant.Secondary to ::secondaryColors,
    ButtonVariant.Ghost to ::ghostColors,
    ButtonVariant.Destructive to ::destructiveColors,
    ButtonVariant.Link to ::linkColors,
)

internal fun defaultButtonStyle(theme: Theme, variant: ButtonVariant, size: ButtonSize): ButtonStyle {
    val metrics = requireNotNull(metricsBySize[size]) { "No default style for the button size $size." }
    val colors = requireNotNull(colorsByVariant[variant]) { "No default style for the button variant $variant." }
    val radius = metrics.radiusCap?.let { cap -> minOf(theme.radii.md, cap) } ?: theme.radii.lg
    return ButtonStyle(
        height = metrics.height,
        minWidth = minWidthOf(metrics),
        horizontalPadding = metrics.padding,
        iconEdgePadding = metrics.iconEdgePadding,
        gap = metrics.gap,
        shape = RoundedCornerShape(radius),
        textStyle = theme.textStyleOf(size = metrics.textSize, weight = FontWeight.Medium),
        iconSize = metrics.iconSize,
        colors = colors(theme),
        borderWidth = 1.dp,
        ringWidth = theme.focusRing.width,
        isUnderlinedOnHover = variant == ButtonVariant.Link,
        pressOffset = 1.dp,
        disabledAlpha = DISABLED_ALPHA,
        colorAnimation = theme.colorTransition(),
    )
}

private fun metrics(height: Dp, padding: Dp, iconEdge: Dp, gap: Dp, cap: Dp? = null): ButtonMetrics = ButtonMetrics(
    height = height,
    isSquare = false,
    padding = padding,
    iconEdgePadding = iconEdge,
    gap = gap,
    radiusCap = cap,
    iconSize = 16.dp,
    textSize = text,
)

private fun square(height: Dp, cap: Dp? = null): ButtonMetrics =
    metrics(height = height, padding = 0.dp, iconEdge = 0.dp, gap = 0.dp, cap = cap).copy(isSquare = true)

private fun minWidthOf(metrics: ButtonMetrics): Dp {
    if (metrics.isSquare) return metrics.height
    return 0.dp
}

private fun defaultColors(theme: Theme): StateColors {
    val primary = theme.colors.primary
    return theme.controlColors(
        container = StateValue(default = primary, hovered = primary.copy(alpha = HOVER_ALPHA)),
        content = StateValue(default = theme.colors.primaryForeground),
    )
}

private fun outlineColors(theme: Theme): StateColors {
    val colors = theme.colors
    val input = colors.input
    val container = StateValue(default = colors.background, hovered = colors.muted, expanded = colors.muted)
    val darkContainer = StateValue(
        default = input,
        hovered = input.copy(alpha = OUTLINE_DARK_HOVER_ALPHA),
        expanded = colors.muted,
    )
    return theme.controlColors(
        container = darkAware(theme = theme, light = container, dark = darkContainer),
        content = StateValue(default = colors.foreground),
        border = darkAware(theme = theme, light = colors.border, dark = input.copy(alpha = OUTLINE_DARK_ALPHA)),
    )
}

private fun secondaryColors(theme: Theme): StateColors {
    val colors = theme.colors
    val hovered = colors.secondary.mix(colors.foreground, amount = SECONDARY_HOVER_MIX)
    return theme.controlColors(
        container = StateValue(default = colors.secondary, hovered = hovered, expanded = colors.secondary),
        content = StateValue(default = colors.secondaryForeground),
    )
}

private fun ghostColors(theme: Theme): StateColors {
    val muted = theme.colors.muted
    val hovered = muted.copy(alpha = theme.darkAware(light = 1f, dark = GHOST_DARK_HOVER_ALPHA))
    return theme.controlColors(
        container = StateValue(default = Color.Transparent, hovered = hovered, expanded = muted),
        content = StateValue(default = theme.colors.foreground),
    )
}

private fun destructiveColors(theme: Theme): StateColors {
    val destructive = theme.colors.destructive
    val container = destructive.copy(alpha = theme.darkAware(light = DESTRUCTIVE_LIGHT, dark = DESTRUCTIVE_DARK))
    val hovered = destructive.copy(
        alpha = theme.darkAware(light = DESTRUCTIVE_HOVER_LIGHT, dark = DESTRUCTIVE_HOVER_DARK),
    )
    return theme.controlColors(
        container = StateValue(default = container, hovered = hovered),
        content = StateValue(default = destructive),
        focusBorder = destructive.copy(alpha = DESTRUCTIVE_FOCUS_BORDER),
        focusRing = destructive.copy(
            alpha = theme.darkAware(light = DESTRUCTIVE_RING_LIGHT, dark = DESTRUCTIVE_RING_DARK),
        ),
    )
}

private fun linkColors(theme: Theme): StateColors = theme.controlColors(
    container = StateValue(default = Color.Transparent),
    content = StateValue(default = theme.colors.primary),
)

private fun <T> darkAware(theme: Theme, light: T, dark: T): T {
    if (theme.isDark) return dark
    return light
}
