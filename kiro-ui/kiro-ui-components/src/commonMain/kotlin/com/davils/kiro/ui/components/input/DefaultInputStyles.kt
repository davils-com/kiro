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

package com.davils.kiro.ui.components.input

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.controlColors
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.mix
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StateColors

private const val DARK_FILL: Float = 0.3f
private const val DISABLED_BORDER_DARK: Float = 0.3f
private const val DISABLED_FILL_LIGHT_MIX: Float = 0.04f
private const val SELECTION_ALPHA: Float = 0.3f
private const val DISABLED_ALPHA: Float = 1f

internal fun defaultInputStyle(theme: Theme): InputStyle = InputStyle(
    minHeight = 32.dp,
    maxHeight = 32.dp,
    shape = RoundedCornerShape(theme.radii.lg),
    padding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
    text = inputTextStyleOf(theme),
    colors = fieldColorsOf(theme),
    borderWidth = 1.dp,
    ringWidth = theme.focusRing.width,
    disabledAlpha = DISABLED_ALPHA,
    colorAnimation = theme.colorTransition(),
)

internal fun defaultTextareaStyle(theme: Theme): InputStyle = defaultInputStyle(theme).copy(
    minHeight = 64.dp,
    maxHeight = Dp.Unspecified,
    padding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
)

internal fun inputTextStyleOf(theme: Theme): InputTextStyle = InputTextStyle(
    textStyle = theme.textStyleOf(size = theme.typography.scale.sm),
    placeholderColor = theme.colors.mutedForeground,
    cursorColor = theme.colors.foreground,
    selectionColors = TextSelectionColors(
        handleColor = theme.colors.primary,
        backgroundColor = theme.colors.primary.copy(alpha = SELECTION_ALPHA),
    ),
)

internal fun fieldColorsOf(theme: Theme): StateColors {
    val colors = theme.colors
    val input = colors.input
    val fill = input.copy(alpha = theme.darkAware(light = 0f, dark = DARK_FILL))
    val lightFill = colors.muted.mix(colors.foreground, amount = DISABLED_FILL_LIGHT_MIX)
    val disabledFill = input.takeIf { theme.isDark } ?: lightFill
    val disabledBorder = input.copy(alpha = DISABLED_BORDER_DARK).takeIf { theme.isDark } ?: colors.border
    val base = theme.controlColors(
        container = StateValue(default = fill, disabled = disabledFill),
        content = StateValue(default = colors.foreground, disabled = colors.mutedForeground),
        border = input,
    )
    return base.copy(border = base.border.copy(disabled = disabledBorder))
}
