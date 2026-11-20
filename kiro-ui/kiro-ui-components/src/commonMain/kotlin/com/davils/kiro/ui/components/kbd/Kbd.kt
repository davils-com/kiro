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

package com.davils.kiro.ui.components.kbd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

/**
 * Shows a key of the keyboard, such as `Ctrl` or `K`, as a small label on a muted container.
 *
 * @param modifier the modifier of the key.
 * @param style the style of the key.
 * @param content the text or icon of the key, centered in a row.
 * @since 1.0.0
 */
@Composable
public fun Kbd(modifier: Modifier = Modifier, style: KbdStyle = kbdStyle(), content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = modifier.then(
            Modifier
                .height(style.height)
                .widthIn(min = style.minWidth)
                .surface(shape = style.shape, color = style.container)
                .padding(horizontal = style.horizontalPadding),
        ),
        horizontalArrangement = Arrangement.spacedBy(style.gap, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides style.content,
            LocalTextStyle provides style.textStyle,
            LocalIconStyle provides IconStyle(size = style.iconSize),
        ) {
            content()
        }
    }
}

/**
 * Places keys of the keyboard next to each other, such as the keys of a shortcut, with the group gap of the style
 * between them.
 *
 * @param modifier the modifier of the group.
 * @param style the style of the keys, which gives the gap between them.
 * @param content the keys, such as [Kbd]s and the text between them.
 * @since 1.0.0
 */
@Composable
public fun KbdGroup(
    modifier: Modifier = Modifier,
    style: KbdStyle = kbdStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(style.groupGap),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

internal fun defaultKbdStyle(theme: Theme): KbdStyle = KbdStyle(
    height = 20.dp,
    minWidth = 20.dp,
    horizontalPadding = 4.dp,
    gap = 4.dp,
    groupGap = 4.dp,
    shape = RoundedCornerShape(theme.radii.sm),
    container = theme.colors.muted,
    content = theme.colors.mutedForeground,
    textStyle = theme.textStyleOf(size = theme.typography.scale.xs, weight = FontWeight.Medium),
    iconSize = 12.dp,
)
