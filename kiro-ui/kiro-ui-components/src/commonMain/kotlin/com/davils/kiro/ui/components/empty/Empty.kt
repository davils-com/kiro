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

package com.davils.kiro.ui.components.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.content.LocalTextStyle

@Composable
public fun Empty(
    modifier: Modifier = Modifier,
    style: EmptyStyle = emptyStyle(),
    content: @Composable EmptyScope.() -> Unit,
) {
    val surface = Modifier.surface(shape = style.shape, color = Color.Transparent, border = style.border)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(surface)
            .padding(style.padding),
        verticalArrangement = Arrangement.spacedBy(style.gap, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CompositionLocalProvider(LocalTextStyle provides style.textStyle) {
            EmptyScopeImpl(columnScope = this, style = style).content()
        }
    }
}

internal fun defaultEmptyStyle(theme: Theme): EmptyStyle {
    val scale = theme.typography.scale
    val text = theme.textStyleOf(size = scale.sm)
    val title = theme.textStyleOf(
        size = scale.sm,
        weight = FontWeight.Medium,
        family = theme.typography.families.heading,
    )
    return EmptyStyle(
        shape = RoundedCornerShape(theme.radii.xl),
        border = null,
        padding = 24.dp,
        gap = 16.dp,
        textStyle = text.copy(textAlign = TextAlign.Center),
        headerGap = 8.dp,
        maxContentWidth = 384.dp,
        mediaBottomGap = 8.dp,
        iconMediaSize = 32.dp,
        iconMediaShape = RoundedCornerShape(theme.radii.lg),
        iconMediaContainer = theme.colors.muted,
        iconMediaContent = theme.colors.foreground,
        iconSize = 16.dp,
        titleStyle = title.copy(letterSpacing = theme.typography.tracking.tight, textAlign = TextAlign.Center),
        descriptionStyle = text.copy(lineHeight = theme.typography.leading.relaxed, textAlign = TextAlign.Center),
        descriptionColor = theme.colors.mutedForeground,
        contentGap = 10.dp,
    )
}
