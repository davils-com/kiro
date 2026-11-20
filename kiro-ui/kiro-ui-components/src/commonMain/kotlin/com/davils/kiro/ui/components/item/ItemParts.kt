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

@Composable
public fun ItemScopeImpl.ItemMedia(
    modifier: Modifier = Modifier,
    variant: ItemMediaVariant = ItemMediaVariant.Default,
    content: @Composable BoxScope.() -> Unit,
) {
    val media = mediaModifierOf(modifier = modifier, variant = variant, style = style)
    CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.iconSize)) {
        Box(modifier = media, contentAlignment = Alignment.Center, content = content)
    }
}

@Composable
public fun ItemScopeImpl.ItemContent(
    modifier: Modifier = Modifier,
    content: @Composable ItemContentScopeImpl.() -> Unit,
) {
    val itemStyle = style
    Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(itemStyle.contentGap)) {
        ItemContentScope(columnScope = this, style = itemStyle).content()
    }
}

@Composable
public fun ItemContentScopeImpl.ItemTitle(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = style.titleStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
}

@Composable
public fun ItemContentScopeImpl.ItemDescription(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = style.descriptionStyle,
        color = style.descriptionColor,
        maxLines = DESCRIPTION_LINES,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
public fun ItemScopeImpl.ItemActions(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
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
