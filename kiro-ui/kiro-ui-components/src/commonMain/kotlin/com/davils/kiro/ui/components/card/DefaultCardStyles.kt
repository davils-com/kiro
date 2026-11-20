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

package com.davils.kiro.ui.components.card

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.typography.TextSize

private const val RING_ALPHA: Float = 0.1f
private const val FOOTER_ALPHA: Float = 0.5f

private val spacingBySize: Map<CardSize, Dp> = mapOf(
    CardSize.Default to 16.dp,
    CardSize.Sm to 12.dp,
)

internal fun defaultCardStyle(theme: Theme, size: CardSize): CardStyle {
    val spacing = requireNotNull(spacingBySize[size]) { "No default style for the card size $size." }
    val scale = theme.typography.scale
    val colors = theme.colors
    val titleSize = titleSizeOf(theme, size)
    return CardStyle(
        shape = RoundedCornerShape(theme.radii.xl),
        container = colors.card,
        content = colors.cardForeground,
        ring = Border(width = 1.dp, color = colors.foreground.copy(alpha = RING_ALPHA)),
        spacing = spacing,
        textStyle = theme.textStyleOf(size = scale.sm),
        headerGap = 4.dp,
        titleStyle = theme.textStyleOf(
            size = titleSize,
            weight = FontWeight.Medium,
            lineHeight = theme.typography.leading.snug,
            family = theme.typography.families.heading,
        ),
        descriptionStyle = theme.textStyleOf(size = scale.sm),
        descriptionColor = colors.mutedForeground,
        footerContainer = colors.muted.copy(alpha = FOOTER_ALPHA),
        footerBorder = Border(width = 1.dp, color = colors.border),
    )
}

private fun titleSizeOf(theme: Theme, size: CardSize): TextSize {
    if (size == CardSize.Sm) return theme.typography.scale.sm
    return theme.typography.scale.base
}
