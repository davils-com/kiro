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

package com.davils.kiro.ui.components.codeblock

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.motion.fadeBy
import com.davils.kiro.ui.components.primitive.motion.fadeTransition
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.style.Border

/**
 * A bordered block for code, made of an optional [CodeBlockHeader], a [CodeBlockContent] and an optional
 * [CodeBlockFooter].
 *
 * While its content shows focus, the border takes the focus color and the focus ring appears.
 *
 * @param modifier the modifier of the code block.
 * @param variant the variant of the code block, which picks its default [style].
 * @param size the size of the code block, which picks its default [style].
 * @param actions the actions that float over the top end corner while the code block is hovered or holds the focus,
 * such as a [CodeBlockCopyButton], or `null` for none.
 * @param style the look of the code block and its parts.
 * @param content the parts of the code block.
 * @since 1.0.0
 */
@Composable
public fun CodeBlock(
    modifier: Modifier = Modifier,
    variant: CodeBlockVariant = CodeBlockVariant.Default,
    size: CodeBlockSize = CodeBlockSize.Default,
    actions: (@Composable RowScope.() -> Unit)? = null,
    style: CodeBlockStyle = codeBlockStyle(variant, size),
    content: @Composable CodeBlockScope.() -> Unit,
) {
    val slots = remember(style) { CodeBlockSlots(style) }
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    var hasFocus by remember { mutableStateOf(false) }
    val transition = theme.colorTransition()
    val border by animateColor(
        style.focusBorder.takeIf { slots.isFocusVisible } ?: style.border.color,
        transition,
    )
    val ring by animateColor(style.ring.color.takeIf { slots.isFocusVisible } ?: Color.Transparent, transition)
    val surface = Modifier.surface(
        shape = style.shape,
        color = style.container,
        border = Border(width = style.border.width, color = border),
        ring = Border(width = style.ring.width, color = ring),
    )
    CompositionLocalProvider(LocalContentColor provides style.content, LocalCodeBlock provides slots) {
        Box(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .then(surface)
                    .hoverable(interactionSource = source)
                    .onFocusChanged { focus -> hasFocus = focus.hasFocus },
            ),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) { slots.content() }
            actions?.let { slot ->
                FloatingActions(
                    isShown = isHovered || hasFocus,
                    style = style,
                    modifier = Modifier.align(Alignment.TopEnd),
                    content = slot,
                )
            }
        }
    }
}

@Composable
private fun FloatingActions(
    isShown: Boolean,
    style: CodeBlockStyle,
    modifier: Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val alpha = animateFloatAsState(1f.takeIf { isShown } ?: 0f, animationSpec = theme.fadeTransition())
    Row(
        modifier = modifier.then(
            Modifier
                .padding(style.gap)
                .fadeBy(alpha)
                .surface(shape = style.shape, color = style.actionSurface, ring = style.actionRing),
        ),
        horizontalArrangement = Arrangement.spacedBy(style.gap / 2),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}
