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

/**
 * Stacks messages of a conversation in a column that fills the width of its parent, with the group gap of the style
 * between them.
 *
 * @param modifier the modifier of the column.
 * @param style the style that gives the gap between the messages.
 * @param content the messages.
 * @since 1.0.0
 */
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

/**
 * Shows one message of a conversation: an optional avatar beside a column of content, such as a header, bubbles and a
 * footer.
 *
 * The message fills the width of its parent. The avatar sits at the bottom of the side given by [align] and is lifted
 * above the footer when the message has a [MessageFooter].
 *
 * @param modifier the modifier of the message.
 * @param align the side that the avatar and the content are aligned to.
 * @param avatar the avatar of the sender, or `null` for none.
 * @param style the style of the message.
 * @param content the parts of the message, such as a [MessageHeader], bubbles and a [MessageFooter], with the text
 * style of the style.
 * @since 1.0.0
 */
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

/**
 * Shows a label above the content of a [Message], such as the name of the sender, in the label style and color of the
 * message.
 *
 * @param modifier the modifier of the header.
 * @param content the content of the header, in a row aligned to the start.
 * @throws IllegalArgumentException if the header is not placed inside a [Message].
 * @since 1.0.0
 */
@Composable
public fun MessageHeader(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    val context = requireNotNull(LocalMessage.current) { "A message header needs its message." }
    MessageLabel(modifier = modifier, context = context, horizontalArrangement = Arrangement.Start, content = content)
}

/**
 * Shows a label below the content of a [Message], such as the time it was sent, in the label style and color of the
 * message and aligned to its side.
 *
 * @param modifier the modifier of the footer.
 * @param content the content of the footer, in a row.
 * @throws IllegalArgumentException if the footer is not placed inside a [Message].
 * @since 1.0.0
 */
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
