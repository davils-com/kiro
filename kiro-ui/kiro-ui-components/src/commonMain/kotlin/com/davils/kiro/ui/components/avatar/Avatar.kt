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

/**
 * Shows a person or an entity as a round image, or as a fallback, such as initials or an icon, when there is no image.
 *
 * The image is cropped to the shape of the style and drawn with an outline. An optional badge sits at the bottom end.
 * Inside an [AvatarGroup], the avatar also gets the group ring of the group.
 *
 * @param modifier the modifier of the avatar.
 * @param size the size of the avatar, which picks the default style.
 * @param image the image of the avatar, or `null` to show [fallback].
 * @param contentDescription the text that accessibility services read for [image].
 * @param style the style of the avatar.
 * @param badge the content of the badge, or `null` for no badge; the badge is drawn whenever it is given, its content
 * only when the style has a badge icon size.
 * @param fallback the content shown when there is no image, with the fallback content color and text style.
 * @since 1.0.0
 */
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
