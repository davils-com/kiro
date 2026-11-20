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

package com.davils.kiro.ui.components.switch

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StateColors

@Composable
public fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: SwitchSize = SwitchSize.Default,
    enabled: Boolean = true,
    isInvalid: Boolean = false,
    style: SwitchStyle = switchStyle(size),
    interactionSource: MutableInteractionSource? = null,
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val state = source.collectInteractionState(isDisabled = !enabled, isInvalid = isInvalid)
    val colors = paletteOf(checked = checked, style = style).animate(state = state, animation = style.colorAnimation)
    val travel = style.width - style.borderWidth * 2 - style.thumb.size
    val offset = animateDpAsState(targetValue = offsetOf(checked = checked, travel = travel), style.thumbAnimation)
    val thumbColor = animateColor(
        targetValue = thumbColorOf(checked = checked, style = style),
        animationSpec = style.colorAnimation,
    )
    val surface = Modifier.surface(
        shape = CircleShape,
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    val toggle = Modifier.pressableCursor(enabled = enabled).toggleable(
        value = checked,
        interactionSource = source,
        indication = null,
        enabled = enabled,
        role = Role.Switch,
        onValueChange = onCheckedChange,
    )
    Box(
        modifier = modifier
            .alpha(disabledAlphaOf(isDisabled = !enabled, alpha = style.disabledAlpha))
            .size(width = style.width, height = style.height)
            .then(surface)
            .then(toggle)
            .padding(style.borderWidth),
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(x = offset.value.roundToPx(), y = 0) }
                .size(style.thumb.size)
                .surface(shape = CircleShape, color = thumbColor.value, border = style.thumb.border),
        )
    }
}

private fun paletteOf(checked: Boolean, style: SwitchStyle): StateColors {
    if (checked) return style.checkedColors
    return style.colors
}

private fun offsetOf(checked: Boolean, travel: Dp): Dp {
    if (checked) return travel
    return 0.dp
}

private fun thumbColorOf(checked: Boolean, style: SwitchStyle): Color {
    if (checked) return style.thumb.checkedColor
    return style.thumb.color
}
