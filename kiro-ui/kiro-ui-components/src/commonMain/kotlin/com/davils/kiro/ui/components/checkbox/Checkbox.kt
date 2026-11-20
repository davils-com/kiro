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

package com.davils.kiro.ui.components.checkbox

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.checkColors
import com.davils.kiro.ui.components.primitive.checkedColors
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle
import com.davils.kiro.ui.icons.lucide.Check
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Minus

private const val DISABLED_ALPHA: Float = 0.5f

@Composable
public fun Checkbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isInvalid: Boolean = false,
    isIndeterminate: Boolean = false,
    style: CheckboxStyle = checkboxStyle(),
    interactionSource: MutableInteractionSource? = null,
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val isMarked = checked || isIndeterminate
    val state = source.collectInteractionState(isDisabled = !enabled, isInvalid = isInvalid)
    val palette = paletteOf(isMarked = isMarked, style = style)
    val colors = palette.animate(state = state, animation = style.colorAnimation)
    val surface = Modifier.surface(
        shape = style.shape,
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    Box(
        modifier = modifier
            .alpha(disabledAlphaOf(isDisabled = !enabled, alpha = style.disabledAlpha))
            .size(style.size)
            .then(surface)
            .pressableCursor(enabled = enabled)
            .triStateToggleable(
                state = toggleableStateOf(checked = checked, isIndeterminate = isIndeterminate),
                interactionSource = source,
                indication = null,
                enabled = enabled,
                role = Role.Checkbox,
                onClick = { onCheckedChange(!checked) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        markOf(checked = checked, isIndeterminate = isIndeterminate, indicator = style.indicator)?.let { mark ->
            CompositionLocalProvider(
                LocalContentColor provides colors.content,
                LocalIconStyle provides IconStyle(size = style.indicator.size),
            ) {
                Icon(icon = mark, contentDescription = null)
            }
        }
    }
}

internal fun defaultCheckboxStyle(theme: Theme): CheckboxStyle = CheckboxStyle(
    size = 16.dp,
    shape = RoundedCornerShape(4.dp),
    colors = theme.checkColors(),
    checkedColors = theme.checkedColors(),
    borderWidth = 1.dp,
    ringWidth = theme.focusRing.width,
    indicator = CheckboxIndicator(
        size = 14.dp,
        checkedIcon = Lucide.Check,
        indeterminateIcon = Lucide.Minus,
    ),
    disabledAlpha = DISABLED_ALPHA,
    colorAnimation = theme.colorTransition(),
)

private fun paletteOf(isMarked: Boolean, style: CheckboxStyle): StateColors {
    if (isMarked) return style.checkedColors
    return style.colors
}

private fun toggleableStateOf(checked: Boolean, isIndeterminate: Boolean): ToggleableState {
    if (isIndeterminate) return ToggleableState.Indeterminate
    return ToggleableState(checked)
}

private fun markOf(checked: Boolean, isIndeterminate: Boolean, indicator: CheckboxIndicator): IconGraphic? {
    if (isIndeterminate) return indicator.indeterminateIcon
    return indicator.checkedIcon.takeIf { checked }
}
