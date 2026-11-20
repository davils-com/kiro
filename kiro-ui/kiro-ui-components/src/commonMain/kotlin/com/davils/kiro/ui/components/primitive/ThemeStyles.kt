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

package com.davils.kiro.ui.components.primitive

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import com.davils.kiro.ui.components.primitive.motion.fadeTransition
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.core.typography.TextSize

private const val HALF: Float = 0.5f
private const val INVALID_RING_LIGHT: Float = 0.2f
private const val INVALID_RING_DARK: Float = 0.4f
private const val INVALID_BORDER_DARK: Float = 0.5f
private const val CHECK_DARK_FILL: Float = 0.3f

internal fun Theme.colorTransition(): AnimationSpec<Color> = transition()

internal fun <T> Theme.transition(): AnimationSpec<T> = fadeTransition()

internal fun Theme.checkColors(): StateColors {
    val fill = colors.input.copy(alpha = darkAware(light = 0f, dark = CHECK_DARK_FILL))
    return controlColors(
        container = StateValue(default = fill),
        content = StateValue(default = colors.foreground),
        border = colors.input,
    )
}

internal fun Theme.checkedColors(): StateColors = checkColors().copy(
    container = StateValue(default = colors.primary),
    content = StateValue(default = colors.primaryForeground),
    border = StateValue(default = colors.primary),
)

internal fun Theme.textStyleOf(
    size: TextSize,
    weight: FontWeight = FontWeight.Normal,
    lineHeight: TextUnit = size.lineHeight,
    family: FontFamily = typography.families.sans,
): TextStyle = TextStyle(
    fontFamily = family,
    fontSize = size.fontSize,
    lineHeight = lineHeight,
    fontWeight = weight,
    lineHeightStyle = LineHeightStyle(alignment = LineHeightStyle.Alignment.Center, trim = LineHeightStyle.Trim.None),
)

internal fun Theme.darkAware(light: Float, dark: Float): Float {
    if (isDark) return dark
    return light
}

internal fun Theme.controlColors(
    container: StateValue<Color>,
    content: StateValue<Color>,
    border: Color = Color.Transparent,
    focusBorder: Color = colors.ring,
    focusRing: Color = colors.ring.copy(alpha = HALF),
): StateColors {
    val destructive = colors.destructive
    return StateColors(
        container = container,
        content = content,
        border = StateValue(
            default = border,
            focusVisible = focusBorder,
            invalid = destructive.copy(alpha = darkAware(light = 1f, dark = INVALID_BORDER_DARK)),
        ),
        ring = StateValue(
            default = Color.Transparent,
            focusVisible = focusRing,
            invalid = destructive.copy(alpha = darkAware(light = INVALID_RING_LIGHT, dark = INVALID_RING_DARK)),
        ),
    )
}
