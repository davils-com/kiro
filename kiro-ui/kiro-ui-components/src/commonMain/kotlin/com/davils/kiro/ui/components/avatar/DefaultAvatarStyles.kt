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

package com.davils.kiro.ui.components.avatar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.typography.TextSize

private val metricsBySize: Map<AvatarSize, AvatarMetrics> = mapOf(
    AvatarSize.Default to AvatarMetrics(
        size = 32.dp,
        badgeSize = 10.dp,
        badgeIconSize = 8.dp,
        countIconSize = 16.dp,
    ),
    AvatarSize.Sm to AvatarMetrics(size = 24.dp, badgeSize = 8.dp, badgeIconSize = null, countIconSize = 12.dp),
    AvatarSize.Lg to AvatarMetrics(size = 40.dp, badgeSize = 12.dp, badgeIconSize = 8.dp, countIconSize = 20.dp),
)

internal fun defaultAvatarStyle(theme: Theme, size: AvatarSize): AvatarStyle {
    val metrics = requireNotNull(metricsBySize[size]) { "No default style for the avatar size $size." }
    val colors = theme.colors
    val text = textSizeOf(theme, size)
    return AvatarStyle(
        size = metrics.size,
        shape = CircleShape,
        outline = Border(width = 1.dp, color = colors.border),
        outlineBlendMode = blendModeOf(theme),
        fallbackContainer = colors.muted,
        fallbackContent = colors.mutedForeground,
        fallbackTextStyle = theme.textStyleOf(size = text),
        badgeSize = metrics.badgeSize,
        badgeIconSize = metrics.badgeIconSize,
        badgeContainer = colors.primary,
        badgeContent = colors.primaryForeground,
        badgeRing = Border(width = 2.dp, color = colors.background),
        groupRing = Border(width = 2.dp, color = colors.background),
        groupOverlap = 8.dp,
        countIconSize = metrics.countIconSize,
    )
}

private fun textSizeOf(theme: Theme, size: AvatarSize): TextSize {
    if (size == AvatarSize.Sm) return theme.typography.scale.xs
    return theme.typography.scale.sm
}

private fun blendModeOf(theme: Theme): BlendMode {
    if (theme.isDark) return BlendMode.Lighten
    return BlendMode.Darken
}
