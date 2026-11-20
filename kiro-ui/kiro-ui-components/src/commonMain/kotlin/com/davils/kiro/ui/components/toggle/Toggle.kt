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

package com.davils.kiro.ui.components.toggle

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.zIndex
import com.davils.kiro.ui.components.InlineScopeImpl
import com.davils.kiro.ui.components.buttongroup.LocalGroupItem
import com.davils.kiro.ui.components.buttongroup.joinedTo
import com.davils.kiro.ui.components.buttongroup.shapeOr
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.interaction.rememberPressPulse
import com.davils.kiro.ui.components.primitive.layout.InlineScope
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
public fun Toggle(
    pressed: Boolean,
    onPressedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    variant: ToggleVariant = ToggleVariant.Default,
    size: ToggleSize = ToggleSize.Default,
    enabled: Boolean = true,
    style: ToggleStyle = toggleStyle(variant, size),
    content: @Composable InlineScopeImpl.() -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    val raw = source.collectInteractionState(isDisabled = !enabled, isSelected = pressed)
    val isPulsing = rememberPressPulse(isPressed = raw.isPressed)
    val state = raw.copy(isPressed = raw.isPressed || isPulsing)
    val colors = paletteOf(pressed = pressed, style = style).animate(state = state, animation = style.colorAnimation)
    val groupItem = LocalGroupItem.current
    val surface = Modifier.surface(
        shape = groupItem.shapeOr(style.shape),
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    val toggle = Modifier.pressableCursor(enabled = enabled).toggleable(
        value = pressed,
        interactionSource = source,
        indication = null,
        enabled = enabled,
        role = Role.Button,
        onValueChange = onPressedChange,
    )
    Row(
        modifier = modifier
            .joinedTo(item = groupItem, overlap = style.borderWidth)
            .zIndex(layerOf(isFocusVisible = state.isFocusVisible))
            .alpha(disabledAlphaOf(isDisabled = !enabled, alpha = style.disabledAlpha))
            .height(style.height)
            .widthIn(min = style.minWidth)
            .then(surface)
            .hoverable(interactionSource = source, enabled = enabled)
            .then(toggle)
            .padding(horizontal = style.horizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(style.gap, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colors.content,
            LocalTextStyle provides style.textStyle,
            LocalIconStyle provides IconStyle(size = style.iconSize),
        ) {
            InlineScope(rowScope = this, edgeInset = style.horizontalPadding - style.iconEdgePadding).content()
        }
    }
}

private fun paletteOf(pressed: Boolean, style: ToggleStyle): StateColors {
    if (pressed) return style.checkedColors
    return style.colors
}

private fun layerOf(isFocusVisible: Boolean): Float {
    if (isFocusVisible) return 1f
    return 0f
}
