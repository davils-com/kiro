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

package com.davils.kiro.ui.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.davils.kiro.ui.components.InlineScope
import com.davils.kiro.ui.components.buttongroup.LocalGroupItem
import com.davils.kiro.ui.components.buttongroup.joinedTo
import com.davils.kiro.ui.components.buttongroup.shapeOr
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.layout.InlineScopeImpl
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.state.InteractionState
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

/**
 * A button that runs an action when it is clicked, with a label and icons in a row.
 *
 * The button shows its hover, press, focus and disabled states through its style, moves down while it is pressed and
 * joins its neighbours when it sits in a button group.
 *
 * @param onClick called when the button is clicked.
 * @param modifier the modifier of the button.
 * @param variant the look of the button.
 * @param size the size of the button.
 * @param enabled whether the button can be clicked; a disabled button is drawn with the disabled alpha of its style.
 * @param style the style of the button, by default the style of the theme for [variant] and [size].
 * @param interactionSource the source of the interactions of the button, or `null` to use one of its own.
 * @param content the content of the button, such as a label and icons.
 * @since 1.0.0
 */
@Composable
public fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Default,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    style: ButtonStyle = buttonStyle(variant, size),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable InlineScope.() -> Unit,
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val state = source.collectInteractionState(isDisabled = !enabled)
    val colors = style.colors.animate(state = state, animation = style.colorAnimation)
    val offset = pressOffsetOf(state, style)
    val alpha = alphaOf(state, style)
    val layer = Modifier.graphicsLayer {
        translationY = offset.toPx()
        this.alpha = alpha
    }
    val groupItem = LocalGroupItem.current
    val shape = groupItem.shapeOr(style.shape)
    val surface = Modifier.surface(
        shape = shape,
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    val click = Modifier.pressableCursor(enabled = enabled).clickable(
        interactionSource = source,
        indication = null,
        enabled = enabled,
        role = Role.Button,
        onClick = onClick,
    )
    Row(
        modifier = modifier.then(
            Modifier
                .joinedTo(item = groupItem, overlap = style.borderWidth)
                .zIndex(layerOf(state))
                .then(layer)
                .height(style.height)
                .widthIn(min = style.minWidth)
                .then(surface)
                .hoverable(interactionSource = source, enabled = enabled)
                .then(click)
                .padding(horizontal = style.horizontalPadding),
        ),
        horizontalArrangement = Arrangement.spacedBy(style.gap, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colors.content,
            LocalTextStyle provides textStyleOf(state, style),
            LocalIconStyle provides IconStyle(size = style.iconSize),
        ) {
            InlineScopeImpl(rowScope = this, edgeInset = style.horizontalPadding - style.iconEdgePadding).content()
        }
    }
}

private fun layerOf(state: InteractionState): Float {
    if (!state.isFocusVisible) return 0f
    return 1f
}

private fun pressOffsetOf(state: InteractionState, style: ButtonStyle): Dp {
    if (!state.isPressed) return 0.dp
    return style.pressOffset
}

private fun alphaOf(state: InteractionState, style: ButtonStyle): Float {
    if (!state.isDisabled) return 1f
    return style.disabledAlpha
}

private fun textStyleOf(state: InteractionState, style: ButtonStyle): TextStyle {
    val isUnderlined = style.isUnderlinedOnHover && state.isHovered
    if (!isUnderlined) return style.textStyle
    return style.textStyle.copy(textDecoration = TextDecoration.Underline)
}
