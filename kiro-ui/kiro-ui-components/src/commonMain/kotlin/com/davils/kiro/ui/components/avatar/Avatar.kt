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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

internal val LocalAvatarRing = staticCompositionLocalOf<Border?> { null }

@Composable
public fun Avatar(
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Default,
    image: Painter? = null,
    contentDescription: String? = null,
    style: AvatarStyle = avatarStyle(size),
    badge: (@Composable () -> Unit)? = null,
    fallback: @Composable BoxScope.() -> Unit = {},
) {
    val ring = Modifier.surface(shape = style.shape, color = style.fallbackContainer, ring = LocalAvatarRing.current)
    Box(modifier = modifier.size(style.size)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(ring)
                .clip(style.shape)
                .outline(style),
            contentAlignment = Alignment.Center,
        ) {
            AvatarContent(image = image, contentDescription = contentDescription, style = style, fallback = fallback)
        }
        badge?.let { slot -> AvatarBadge(style = style, content = slot) }
    }
}

@Composable
private fun AvatarContent(
    image: Painter?,
    contentDescription: String?,
    style: AvatarStyle,
    fallback: @Composable BoxScope.() -> Unit,
) {
    if (image != null) {
        Image(
            painter = image,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        return
    }
    CompositionLocalProvider(
        LocalContentColor provides style.fallbackContent,
        LocalTextStyle provides style.fallbackTextStyle,
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = fallback)
    }
}

@Composable
private fun BoxScope.AvatarBadge(style: AvatarStyle, content: @Composable () -> Unit) {
    val surface = Modifier.surface(shape = style.shape, color = style.badgeContainer, ring = style.badgeRing)
    Box(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .size(style.badgeSize)
            .then(surface),
        contentAlignment = Alignment.Center,
    ) {
        style.badgeIconSize?.let { iconSize ->
            CompositionLocalProvider(
                LocalContentColor provides style.badgeContent,
                LocalIconStyle provides IconStyle(size = iconSize),
                content = content,
            )
        }
    }
}

private fun Modifier.outline(style: AvatarStyle): Modifier = drawWithContent {
    drawContent()
    val width = style.outline.width.toPx()
    drawCircle(
        color = style.outline.color,
        radius = size.minDimension / 2 - width / 2,
        style = Stroke(width),
        blendMode = style.outlineBlendMode,
    )
}
