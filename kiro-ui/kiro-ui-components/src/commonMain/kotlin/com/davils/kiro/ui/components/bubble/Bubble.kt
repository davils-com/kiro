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

package com.davils.kiro.ui.components.bubble

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.message.LocalMessage
import com.davils.kiro.ui.components.message.MessageAlign
import com.davils.kiro.ui.components.message.MessageContext
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border

/**
 * A column of bubbles that fills the available width and keeps the group gap of the style between them.
 *
 * @param modifier the modifier of the group.
 * @param style the style whose group gap separates the bubbles.
 * @param content the bubbles of the group.
 * @since 1.0.0
 */
@Composable
public fun BubbleGroup(
    modifier: Modifier = Modifier,
    style: BubbleStyle = bubbleStyle(BubbleVariant.Default),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(style.groupGap),
        content = content,
    )
}

/**
 * A chat bubble: stacks its [BubbleContent] parts with the gap of the style, limits their width to a fraction of the
 * available width and places them at the start or the end of the row.
 *
 * Inside a message the bubble takes its alignment from the message, and a bubble of [BubbleVariant.Ghost] removes the
 * label padding of the message. [BubbleReactions] in the content are placed on the edge of the bubble instead of in
 * the stack.
 *
 * @param modifier the modifier of the bubble.
 * @param variant the variant of the bubble, which chooses its default style.
 * @param align the side of the row the bubble is placed on.
 * @param style the style of the bubble.
 * @param content the parts and reactions of the bubble.
 * @since 1.0.0
 */
@Composable
public fun Bubble(
    modifier: Modifier = Modifier,
    variant: BubbleVariant = BubbleVariant.Default,
    align: MessageAlign = LocalMessage.current?.align ?: MessageAlign.Start,
    style: BubbleStyle = bubbleStyle(variant),
    content: @Composable () -> Unit,
) {
    GhostRegistration(message = LocalMessage.current, isGhost = variant == BubbleVariant.Ghost)
    val isEnd = align == MessageAlign.End
    val context = remember(style, isEnd) { BubbleContext(style = style, isEnd = isEnd) }
    val policy = remember(style, isEnd) { BubbleMeasurePolicy(isEnd = isEnd, style = style) }
    CompositionLocalProvider(
        LocalBubble provides context,
        LocalContentColor provides style.content,
        LocalTextStyle provides style.textStyle,
    ) {
        Layout(content = content, modifier = modifier, measurePolicy = policy)
    }
}

/**
 * A part of a [Bubble]: its content in the shape, colors and padding of the style of the bubble.
 *
 * With [onClick] the part is clickable, shows the hover color of the style while the pointer is over it and the focus
 * border and ring while its focus is visible.
 *
 * @param modifier the modifier of the part.
 * @param onClick called when the part is clicked, or `null` for a part that cannot be clicked.
 * @param content the content of the part.
 * @throws IllegalArgumentException if it is not placed inside a [Bubble].
 * @since 1.0.0
 */
@Composable
public fun BubbleContent(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val style = requireNotNull(LocalBubble.current) { "A bubble content needs its bubble." }.style
    val source = remember { MutableInteractionSource() }
    val state = source.collectInteractionState()
    val isFocusVisible = state.isFocusVisible
    val target = style.hover.takeIf { onClick != null && state.isHovered } ?: style.container
    val container by animateColor(target, theme.colorTransition())
    val border = Border(width = 1.dp, color = style.focusBorder.takeIf { isFocusVisible } ?: style.border)
    val ring = Border(width = style.ringWidth, color = style.ring.takeIf { isFocusVisible } ?: Color.Transparent)
    val click = onClick?.let { action ->
        Modifier.pressableCursor().clickable(
            interactionSource = source,
            indication = null,
            role = Role.Button,
            onClick = action,
        )
    }
    Box(
        modifier = modifier.then(
            Modifier
                .surface(shape = style.shape, color = container, border = border, ring = ring)
                .hoverable(interactionSource = source)
                .then(click ?: Modifier)
                .padding(style.padding),
        ),
    ) {
        content()
    }
}

/**
 * A row of reactions, such as emoji, placed on the top or bottom edge of its [Bubble] so that it overlaps the edge.
 *
 * @param modifier the modifier of the row.
 * @param side the edge of the bubble the row is placed on.
 * @param align whether the row sits at the start or at the end of that edge.
 * @param content the reactions.
 * @throws IllegalArgumentException if it is not placed inside a [Bubble].
 * @since 1.0.0
 */
@Composable
public fun BubbleReactions(
    modifier: Modifier = Modifier,
    side: BubbleReactionsSide = BubbleReactionsSide.Bottom,
    align: MessageAlign = MessageAlign.End,
    content: @Composable RowScope.() -> Unit,
) {
    val style = requireNotNull(LocalBubble.current) { "Bubble reactions need their bubble." }.style.reactions
    val ring = Border(width = style.ringWidth, color = style.ring)
    CompositionLocalProvider(LocalContentColor provides style.content, LocalTextStyle provides style.textStyle) {
        Row(
            modifier = modifier.then(
                Modifier
                    .layoutId(BubbleReactionsSlot(side = side, align = align))
                    .surface(shape = style.shape, color = style.container, ring = ring)
                    .padding(style.padding),
            ),
            horizontalArrangement = Arrangement.spacedBy(style.gap),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

@Composable
private fun GhostRegistration(message: MessageContext?, isGhost: Boolean) {
    DisposableEffect(message, isGhost) {
        val counted = message?.takeIf { isGhost }
        counted?.let { context -> context.ghosts += 1 }
        onDispose { counted?.let { context -> context.ghosts -= 1 } }
    }
}
