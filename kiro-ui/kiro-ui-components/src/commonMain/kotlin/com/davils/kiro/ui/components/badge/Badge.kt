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

package com.davils.kiro.ui.components.badge

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.davils.kiro.ui.components.InlineScope
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
 * Shows a small label, such as a status or a count, in a rounded container whose colors follow its interaction
 * state.
 *
 * With an [onClick] the badge is hoverable and clickable with the role of a button; without one it only displays its
 * content.
 *
 * @param modifier the modifier of the badge.
 * @param variant the variant whose default style the badge uses.
 * @param onClick called when the badge is clicked, or `null` for a badge that cannot be clicked.
 * @param style the style of the badge.
 * @param interactionSource the source of the interactions of the badge, or `null` to use one of its own.
 * @param content the text and icons of the badge, in a row.
 * @since 1.0.0
 */
@Composable
public fun Badge(
    modifier: Modifier = Modifier,
    variant: BadgeVariant = BadgeVariant.Default,
    onClick: (() -> Unit)? = null,
    style: BadgeStyle = badgeStyle(variant),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable InlineScope.() -> Unit,
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val state = source.collectInteractionState()
    val colors = style.colors.animate(state = state, animation = style.colorAnimation)
    val surface = Modifier.surface(
        shape = style.shape,
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    Row(
        modifier = modifier.then(
            Modifier
                .height(style.height)
                .then(surface)
                .interactive(source = source, onClick = onClick)
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

private fun Modifier.interactive(source: MutableInteractionSource, onClick: (() -> Unit)?): Modifier {
    if (onClick == null) return this
    val hovering = hoverable(interactionSource = source)
    return hovering.pressableCursor().clickable(
        interactionSource = source,
        indication = null,
        role = Role.Button,
        onClick = onClick,
    )
}

private fun textStyleOf(state: InteractionState, style: BadgeStyle): TextStyle {
    val isUnderlined = style.isUnderlinedOnHover && state.isHovered
    if (!isUnderlined) return style.textStyle
    return style.textStyle.copy(textDecoration = TextDecoration.Underline)
}
