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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
public fun EmptyScope.EmptyHeader(modifier: Modifier = Modifier, content: @Composable EmptyScope.() -> Unit) {
    val emptyStyle = style
    Column(
        modifier = modifier.widthIn(max = emptyStyle.maxContentWidth),
        verticalArrangement = Arrangement.spacedBy(emptyStyle.headerGap),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmptyScopeImpl(columnScope = this, style = emptyStyle).content()
    }
}

@Composable
public fun EmptyScope.EmptyMedia(
    modifier: Modifier = Modifier,
    variant: EmptyMediaVariant = EmptyMediaVariant.Default,
    content: @Composable BoxScope.() -> Unit,
) {
    val spaced = modifier.padding(bottom = style.mediaBottomGap)
    when (variant) {
        EmptyMediaVariant.Default -> Box(modifier = spaced, contentAlignment = Alignment.Center, content = content)
        EmptyMediaVariant.Icon -> IconMedia(modifier = spaced, style = style, content = content)
    }
}

@Composable
public fun EmptyScope.EmptyTitle(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = style.titleStyle)
}

@Composable
public fun EmptyScope.EmptyDescription(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = style.descriptionStyle, color = style.descriptionColor)
}

@Composable
public fun EmptyScope.EmptyContent(modifier: Modifier = Modifier, content: @Composable EmptyScope.() -> Unit) {
    val emptyStyle = style
    Column(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = emptyStyle.maxContentWidth),
        verticalArrangement = Arrangement.spacedBy(emptyStyle.contentGap),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmptyScopeImpl(columnScope = this, style = emptyStyle).content()
    }
}

@Composable
private fun IconMedia(modifier: Modifier, style: EmptyStyle, content: @Composable BoxScope.() -> Unit) {
    val surface = Modifier.surface(shape = style.iconMediaShape, color = style.iconMediaContainer)
    CompositionLocalProvider(
        LocalContentColor provides style.iconMediaContent,
        LocalIconStyle provides IconStyle(size = style.iconSize),
    ) {
        Box(
            modifier = modifier
                .size(style.iconMediaSize)
                .then(surface),
            contentAlignment = Alignment.Center,
            content = content,
        )
    }
}
