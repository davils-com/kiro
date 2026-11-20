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

package com.davils.kiro.ui.components.radiogroup

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.primitive.checkColors
import com.davils.kiro.ui.components.primitive.checkedColors
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.roving.rovingKeys
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StateColors

private const val DISABLED_ALPHA: Float = 0.5f

@Composable
public fun <T> RadioGroup(
    selected: T?,
    onSelectedChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isInvalid: Boolean = false,
    orientation: Orientation = Orientation.Vertical,
    style: RadioStyle = radioStyle(),
    content: @Composable RadioGroupScope<T>.() -> Unit,
) {
    val registry = remember { RadioRegistry<T>() }
    val group = RadioGroupState(
        selected = selected,
        onSelect = onSelectedChange,
        isEnabled = enabled,
        isInvalid = isInvalid,
        registry = registry,
    )
    val keys = Modifier.rovingKeys(
        orientation = orientation,
        isReversed = LocalLayoutDirection.current == LayoutDirection.Rtl,
        isEnabled = { registry.enabled.toList() },
        current = { registry.values.indexOf(registry.focused ?: selected) },
        onTarget = { index ->
            val value = registry.values[index]
            registry.requested = value
            onSelectedChange(value)
        },
    )
    val arranged = Arrangement.spacedBy(style.groupGap)
    val grouped = modifier.selectableGroup().then(keys)
    when (orientation) {
        Orientation.Vertical -> Column(modifier = grouped, verticalArrangement = arranged) {
            group.content()
        }

        Orientation.Horizontal -> Row(
            modifier = grouped,
            horizontalArrangement = arranged,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            group.content()
        }
    }
}

@Composable
public fun <T> RadioGroupScope<T>.RadioGroupItem(
    value: T,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: RadioStyle = radioStyle(),
) {
    val group = this as RadioGroupState<T>
    val registry = group.registry
    val source = remember { MutableInteractionSource() }
    val requester = remember { FocusRequester() }
    val isChecked = group.selected == value
    val isEnabled = enabled && group.isEnabled
    DisposableEffect(value, isEnabled) {
        registry.register(value = value, isEnabled = isEnabled)
        onDispose { registry.unregister(value) }
    }
    LaunchedEffect(registry.requested == value) { if (registry.requested == value) requester.requestFocus() }
    val isFallback = group.selected == null && registry.values.firstOrNull() == value
    val isTabbable = isChecked || isFallback
    val state = source.collectInteractionState(isDisabled = !isEnabled, isInvalid = group.isInvalid)
    val colors = paletteOf(isChecked = isChecked, style = style).animate(
        state = state,
        animation = style.colorAnimation,
    )
    val surface = Modifier.surface(
        shape = CircleShape,
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    Box(
        modifier = modifier
            .alpha(disabledAlphaOf(isDisabled = !isEnabled, alpha = style.disabledAlpha))
            .size(style.size)
            .then(surface)
            .focusRequester(requester)
            .onFocusChanged { focus -> if (focus.isFocused) registry.focused = value }
            .focusProperties { canFocus = isTabbable }
            .pressableCursor(enabled = isEnabled)
            .selectable(
                selected = isChecked,
                interactionSource = source,
                indication = null,
                enabled = isEnabled,
                role = Role.RadioButton,
                onClick = { group.onSelect(value) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isChecked) {
            Box(modifier = Modifier.size(style.dotSize).surface(shape = CircleShape, color = colors.content))
        }
    }
}

internal fun defaultRadioStyle(theme: Theme): RadioStyle = RadioStyle(
    size = 16.dp,
    dotSize = 8.dp,
    groupGap = 8.dp,
    colors = theme.checkColors(),
    checkedColors = theme.checkedColors(),
    borderWidth = 1.dp,
    ringWidth = theme.focusRing.width,
    disabledAlpha = DISABLED_ALPHA,
    colorAnimation = theme.colorTransition(),
)

private fun paletteOf(isChecked: Boolean, style: RadioStyle): StateColors {
    if (isChecked) return style.checkedColors
    return style.colors
}
