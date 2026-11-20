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

package com.davils.kiro.ui.components.badge

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.controlColors
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StateColors

private const val HOVER_ALPHA: Float = 0.8f
private const val GHOST_DARK_HOVER_ALPHA: Float = 0.5f
private const val DESTRUCTIVE_LIGHT: Float = 0.1f
private const val DESTRUCTIVE_DARK: Float = 0.2f
private const val DESTRUCTIVE_HOVER: Float = 0.2f
private const val DESTRUCTIVE_RING_LIGHT: Float = 0.2f
private const val DESTRUCTIVE_RING_DARK: Float = 0.4f

private val colorsByVariant: Map<BadgeVariant, (Theme) -> StateColors> = mapOf(
    BadgeVariant.Default to ::defaultColors,
    BadgeVariant.Secondary to ::secondaryColors,
    BadgeVariant.Destructive to ::destructiveColors,
    BadgeVariant.Outline to ::outlineColors,
    BadgeVariant.Ghost to ::ghostColors,
    BadgeVariant.Link to ::linkColors,
)

internal fun defaultBadgeStyle(theme: Theme, variant: BadgeVariant): BadgeStyle {
    val colors = requireNotNull(colorsByVariant[variant]) { "No default style for the badge variant $variant." }
    return BadgeStyle(
        height = 20.dp,
        horizontalPadding = 8.dp,
        iconEdgePadding = 6.dp,
        gap = 4.dp,
        shape = RoundedCornerShape(theme.radii.xl4),
        textStyle = theme.textStyleOf(size = theme.typography.scale.xs, weight = FontWeight.Medium),
        iconSize = 12.dp,
        colors = colors(theme),
        borderWidth = 1.dp,
        ringWidth = theme.focusRing.width,
        isUnderlinedOnHover = variant == BadgeVariant.Link,
        colorAnimation = theme.colorTransition(),
    )
}

private fun defaultColors(theme: Theme): StateColors {
    val primary = theme.colors.primary
    return theme.controlColors(
        container = StateValue(default = primary, hovered = primary.copy(alpha = HOVER_ALPHA)),
        content = StateValue(default = theme.colors.primaryForeground),
    )
}

private fun secondaryColors(theme: Theme): StateColors {
    val secondary = theme.colors.secondary
    return theme.controlColors(
        container = StateValue(default = secondary, hovered = secondary.copy(alpha = HOVER_ALPHA)),
        content = StateValue(default = theme.colors.secondaryForeground),
    )
}

private fun destructiveColors(theme: Theme): StateColors {
    val destructive = theme.colors.destructive
    val container = destructive.copy(alpha = theme.darkAware(light = DESTRUCTIVE_LIGHT, dark = DESTRUCTIVE_DARK))
    return theme.controlColors(
        container = StateValue(default = container, hovered = destructive.copy(alpha = DESTRUCTIVE_HOVER)),
        content = StateValue(default = destructive),
        focusRing = destructive.copy(
            alpha = theme.darkAware(light = DESTRUCTIVE_RING_LIGHT, dark = DESTRUCTIVE_RING_DARK),
        ),
    )
}

private fun outlineColors(theme: Theme): StateColors {
    val colors = theme.colors
    return theme.controlColors(
        container = StateValue(default = Color.Transparent, hovered = colors.muted),
        content = StateValue(default = colors.foreground, hovered = colors.mutedForeground),
        border = colors.border,
    )
}

private fun ghostColors(theme: Theme): StateColors {
    val colors = theme.colors
    val hovered = colors.muted.copy(alpha = theme.darkAware(light = 1f, dark = GHOST_DARK_HOVER_ALPHA))
    return theme.controlColors(
        container = StateValue(default = Color.Transparent, hovered = hovered),
        content = StateValue(default = colors.foreground, hovered = colors.mutedForeground),
    )
}

private fun linkColors(theme: Theme): StateColors = theme.controlColors(
    container = StateValue(default = Color.Transparent),
    content = StateValue(default = theme.colors.primary),
)
