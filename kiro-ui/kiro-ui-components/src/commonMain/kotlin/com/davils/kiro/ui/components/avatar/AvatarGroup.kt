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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

/**
 * Lays out avatars in a row that overlap by the group overlap of the style, each with the group ring of the style
 * around it.
 *
 * @param modifier the modifier of the row.
 * @param size the size of the avatars, which picks the default style.
 * @param style the style of the group, which gives the overlap and the ring and is handed to the content through
 * [AvatarGroupScope.style].
 * @param content the avatars of the group, in an [AvatarGroupScope] that hands them the style.
 * @since 1.0.0
 */
@Composable
public fun AvatarGroup(
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Default,
    style: AvatarStyle = avatarStyle(size),
    content: @Composable AvatarGroupScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(-style.groupOverlap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalAvatarRing provides style.groupRing) {
            AvatarGroupScopeImpl(rowScope = this, style = style).content()
        }
    }
}

/**
 * Shows a count, such as `+3`, of the avatars that an [AvatarGroup] leaves out, in the shape, size, fallback colors and
 * group ring of the style of the group.
 *
 * @param modifier the modifier of the count.
 * @param content the content of the count, centered, with the fallback content color and text style and the count
 * icon size of the style.
 * @since 1.0.0
 */
@Composable
public fun AvatarGroupScope.AvatarGroupCount(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    val surface = Modifier.surface(shape = style.shape, color = style.fallbackContainer, ring = style.groupRing)
    CompositionLocalProvider(
        LocalContentColor provides style.fallbackContent,
        LocalTextStyle provides style.fallbackTextStyle,
        LocalIconStyle provides IconStyle(size = style.countIconSize),
    ) {
        Box(
            modifier = modifier
                .size(style.size)
                .then(surface),
            contentAlignment = Alignment.Center,
            content = content,
        )
    }
}
