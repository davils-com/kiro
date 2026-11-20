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

package com.davils.kiro.ui.components.alert

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.StatusColor
import com.davils.kiro.ui.core.style.Border

private const val DESCRIPTION_ALPHA: Float = 0.9f

private val colorsByVariant: Map<AlertVariant, (Theme) -> AlertColors> = mapOf(
    AlertVariant.Default to { theme ->
        AlertColors(
            content = theme.colors.cardForeground,
            description = theme.colors.mutedForeground,
            container = theme.colors.card,
            border = theme.colors.border,
        )
    },
    AlertVariant.Destructive to { theme ->
        val destructive = theme.colors.destructive
        AlertColors(
            content = destructive,
            description = destructive.copy(alpha = DESCRIPTION_ALPHA),
            container = theme.colors.card,
            border = theme.colors.border,
        )
    },
    AlertVariant.Info to { theme -> statusColorsOf(theme.colors.status.info) },
    AlertVariant.Success to { theme -> statusColorsOf(theme.colors.status.success) },
    AlertVariant.Warning to { theme -> statusColorsOf(theme.colors.status.warning) },
)

private fun statusColorsOf(status: StatusColor): AlertColors = AlertColors(
    content = status.foreground,
    description = status.foreground.copy(alpha = DESCRIPTION_ALPHA),
    container = status.surface,
    border = status.border,
)

internal fun defaultAlertStyle(theme: Theme, variant: AlertVariant): AlertStyle {
    val colorsOf = requireNotNull(colorsByVariant[variant]) { "No default style for the alert variant $variant." }
    val colors = colorsOf(theme)
    val text = theme.typography.scale.sm
    return AlertStyle(
        shape = RoundedCornerShape(theme.radii.lg),
        container = colors.container,
        content = colors.content,
        border = Border(width = 1.dp, color = colors.border),
        horizontalPadding = 10.dp,
        verticalPadding = 8.dp,
        gap = 2.dp,
        iconGap = 8.dp,
        iconSize = 16.dp,
        iconOffset = 2.dp,
        textStyle = theme.textStyleOf(size = text),
        titleStyle = theme.textStyleOf(size = text, weight = FontWeight.Medium),
        descriptionStyle = theme.textStyleOf(size = text),
        descriptionColor = colors.description,
        actionInset = 8.dp,
        actionReserve = 72.dp,
    )
}
