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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private const val DESCRIPTION_LINES: Int = 2

/**
 * Shows the media of an [Item], such as an icon or an image, at the start of its row.
 *
 * @param modifier the modifier of the media.
 * @param variant the kind of media; [ItemMediaVariant.Image] sizes the media to the image size of the style and
 * clips it to the image shape.
 * @param content the media, centered, with the icon size of the style.
 * @since 1.0.0
 */
@Composable
public fun ItemScope.ItemMedia(
    modifier: Modifier = Modifier,
    variant: ItemMediaVariant = ItemMediaVariant.Default,
    content: @Composable BoxScope.() -> Unit,
) {
    val media = mediaModifierOf(modifier = modifier, variant = variant, style = style)
    CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.iconSize)) {
        Box(modifier = media, contentAlignment = Alignment.Center, content = content)
    }
}

/**
 * Shows the text of an [Item], such as a title and a description, in a column that takes the remaining width of the
 * row.
 *
 * @param modifier the modifier of the column.
 * @param content the text of the item, such as [ItemTitle] and [ItemDescription].
 * @since 1.0.0
 */
@Composable
public fun ItemScope.ItemContent(modifier: Modifier = Modifier, content: @Composable ItemContentScope.() -> Unit) {
    val itemStyle = style
    Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(itemStyle.contentGap)) {
        ItemContentScopeImpl(columnScope = this, style = itemStyle).content()
    }
}

/**
 * Shows the title of an [Item] on one line, cut off with an ellipsis when it is too long.
 *
 * @param text the title.
 * @param modifier the modifier of the title.
 * @since 1.0.0
 */
@Composable
public fun ItemContentScope.ItemTitle(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = style.titleStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
}

/**
 * Shows the description of an [Item] in the description color of its style, on at most two lines, cut off with an
 * ellipsis when it is too long.
 *
 * @param text the description.
 * @param modifier the modifier of the description.
 * @since 1.0.0
 */
@Composable
public fun ItemContentScope.ItemDescription(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = style.descriptionStyle,
        color = style.descriptionColor,
        maxLines = DESCRIPTION_LINES,
        overflow = TextOverflow.Ellipsis,
    )
}

/**
 * Shows the actions of an [Item], such as buttons, in a row at the end of the item.
 *
 * @param modifier the modifier of the row.
 * @param content the actions.
 * @since 1.0.0
 */
@Composable
public fun ItemScope.ItemActions(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(style.actionsGap),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

private fun mediaModifierOf(modifier: Modifier, variant: ItemMediaVariant, style: ItemStyle): Modifier {
    if (variant != ItemMediaVariant.Image) return modifier
    val sized = modifier.size(style.imageSize)
    return sized.clip(style.imageShape)
}
