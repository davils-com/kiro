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

@Composable
public fun AvatarGroup(
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Default,
    style: AvatarStyle = avatarStyle(size),
    content: @Composable AvatarGroupScopeImpl.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(-style.groupOverlap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalAvatarRing provides style.groupRing) {
            AvatarGroupScope(rowScope = this, style = style).content()
        }
    }
}

@Composable
public fun AvatarGroupScopeImpl.AvatarGroupCount(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
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
