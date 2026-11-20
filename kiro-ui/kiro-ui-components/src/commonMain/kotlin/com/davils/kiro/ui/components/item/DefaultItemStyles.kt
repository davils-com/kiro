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

package com.davils.kiro.ui.components.item

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.controlColors
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.core.typography.TextSize

private const val MUTED_ALPHA: Float = 0.5f

private val metricsBySize: Map<ItemSize, ItemMetrics> = mapOf(
    ItemSize.Default to ItemMetrics(
        gap = 10.dp,
        horizontalPadding = 12.dp,
        verticalPadding = 10.dp,
        imageSize = 40.dp,
        contentGap = 4.dp,
        isCompact = false,
    ),
    ItemSize.Sm to ItemMetrics(
        gap = 10.dp,
        horizontalPadding = 12.dp,
        verticalPadding = 10.dp,
        imageSize = 32.dp,
        contentGap = 4.dp,
        isCompact = false,
    ),
    ItemSize.Xs to ItemMetrics(
        gap = 8.dp,
        horizontalPadding = 10.dp,
        verticalPadding = 8.dp,
        imageSize = 24.dp,
        contentGap = 0.dp,
        isCompact = true,
    ),
)

private val colorsByVariant: Map<ItemVariant, (Theme) -> StateColors> = mapOf(
    ItemVariant.Default to { theme ->
        colorsOf(
            theme = theme,
            container = Color.Transparent,
            border = Color.Transparent,
        )
    },
    ItemVariant.Outline to { theme ->
        colorsOf(
            theme = theme,
            container = Color.Transparent,
            border = theme.colors.border,
        )
    },
    ItemVariant.Muted to { theme ->
        colorsOf(theme = theme, container = theme.colors.muted.copy(alpha = MUTED_ALPHA), border = Color.Transparent)
    },
)

internal fun defaultItemStyle(theme: Theme, variant: ItemVariant, size: ItemSize): ItemStyle {
    val metrics = requireNotNull(metricsBySize[size]) { "No default style for the item size $size." }
    val colors = requireNotNull(colorsByVariant[variant]) { "No default style for the item variant $variant." }
    val scale = theme.typography.scale
    return ItemStyle(
        shape = RoundedCornerShape(theme.radii.lg),
        colors = colors(theme),
        borderWidth = 1.dp,
        ringWidth = theme.focusRing.width,
        gap = metrics.gap,
        horizontalPadding = metrics.horizontalPadding,
        verticalPadding = metrics.verticalPadding,
        textStyle = theme.textStyleOf(size = scale.sm),
        mediaGap = 8.dp,
        imageSize = metrics.imageSize,
        imageShape = RoundedCornerShape(theme.radii.sm),
        iconSize = 16.dp,
        contentGap = metrics.contentGap,
        titleStyle = theme.textStyleOf(
            size = scale.sm,
            weight = FontWeight.Medium,
            lineHeight = theme.typography.leading.snug,
        ),
        descriptionStyle = theme.textStyleOf(
            size = descriptionSizeOf(theme, metrics),
            lineHeight = theme.typography.leading.normal,
        ),
        descriptionColor = theme.colors.mutedForeground,
        actionsGap = 8.dp,
        colorAnimation = theme.colorTransition(),
    )
}

private fun descriptionSizeOf(theme: Theme, metrics: ItemMetrics): TextSize {
    if (metrics.isCompact) return theme.typography.scale.xs
    return theme.typography.scale.sm
}

private fun colorsOf(theme: Theme, container: Color, border: Color): StateColors = theme.controlColors(
    container = StateValue(default = container, hovered = theme.colors.muted),
    content = StateValue(default = theme.colors.foreground),
    border = border,
)
