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

package com.davils.kiro.ui.components.message

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

@Composable
public fun MessageGroup(
    modifier: Modifier = Modifier,
    style: MessageStyle = messageStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(style.groupGap),
        content = content,
    )
}

@Composable
public fun Message(
    modifier: Modifier = Modifier,
    align: MessageAlign = MessageAlign.Start,
    avatar: (@Composable () -> Unit)? = null,
    style: MessageStyle = messageStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val context = remember(align, style) { MessageContext(align = align, style = style) }
    val isEnd = align == MessageAlign.End
    CompositionLocalProvider(LocalMessage provides context, LocalTextStyle provides style.textStyle) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(style.gap)) {
            if (!isEnd) avatar?.let { slot -> MessageAvatar(avatar = slot, context = context) }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(style.contentGap),
                horizontalAlignment = Alignment.End.takeIf { isEnd } ?: Alignment.Start,
                content = content,
            )
            if (isEnd) avatar?.let { slot -> MessageAvatar(avatar = slot, context = context) }
        }
    }
}

@Composable
public fun MessageHeader(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    val context = requireNotNull(LocalMessage.current) { "A message header needs its message." }
    MessageLabel(modifier = modifier, context = context, horizontalArrangement = Arrangement.Start, content = content)
}

@Composable
public fun MessageFooter(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    val context = requireNotNull(LocalMessage.current) { "A message footer needs its message." }
    DisposableEffect(context) {
        context.hasFooter = true
        onDispose { context.hasFooter = false }
    }
    val arrangement = Arrangement.End.takeIf { context.align == MessageAlign.End } ?: Arrangement.Start
    MessageLabel(modifier = modifier, context = context, horizontalArrangement = arrangement, content = content)
}

@Composable
private fun MessageLabel(
    modifier: Modifier,
    context: MessageContext,
    horizontalArrangement: Arrangement.Horizontal,
    content: @Composable RowScope.() -> Unit,
) {
    val style = context.style
    val padding = 0.dp.takeIf { context.ghosts > 0 } ?: style.labelPadding
    CompositionLocalProvider(LocalTextStyle provides style.labelStyle, LocalContentColor provides style.labelColor) {
        Row(
            modifier = modifier.padding(horizontal = padding),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

@Composable
private fun RowScope.MessageAvatar(avatar: @Composable () -> Unit, context: MessageContext) {
    val style = context.style
    val lift = style.avatarLift.takeIf { context.hasFooter } ?: 0.dp
    Box(
        modifier = Modifier
            .align(Alignment.Bottom)
            .offset(y = -lift)
            .defaultMinSize(minWidth = style.avatarMinSize)
            .clip(style.avatarShape)
            .surface(shape = style.avatarShape, color = style.avatarContainer),
        contentAlignment = Alignment.Center,
    ) {
        avatar()
    }
}
