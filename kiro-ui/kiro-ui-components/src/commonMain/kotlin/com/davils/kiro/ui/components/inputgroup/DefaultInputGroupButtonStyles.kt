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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonStyle
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.button.defaultButtonStyle
import com.davils.kiro.ui.core.Theme

private val adjustments: Map<InputGroupButtonSize, (Theme, ButtonStyle) -> ButtonStyle> = mapOf(
    InputGroupButtonSize.Xs to ::extraSmall,
    InputGroupButtonSize.Sm to ::small,
    InputGroupButtonSize.IconXs to ::iconExtraSmall,
    InputGroupButtonSize.IconSm to ::iconSmall,
)

internal fun defaultInputGroupButtonStyle(
    theme: Theme,
    variant: ButtonVariant,
    size: InputGroupButtonSize,
): ButtonStyle {
    val adjust = requireNotNull(adjustments[size]) { "No default style for the input group button size $size." }
    return adjust(theme, defaultButtonStyle(theme, variant, ButtonSize.Default))
}

private fun extraSmall(theme: Theme, base: ButtonStyle): ButtonStyle = base.copy(
    height = 24.dp,
    horizontalPadding = 6.dp,
    gap = 4.dp,
    shape = RoundedCornerShape(theme.radii.lg - 3.dp),
    iconSize = 14.dp,
)

private fun small(theme: Theme, base: ButtonStyle): ButtonStyle =
    base.copy(gap = 8.dp, shape = RoundedCornerShape(theme.radii.lg))

private fun iconExtraSmall(theme: Theme, base: ButtonStyle): ButtonStyle = base.copy(
    height = 24.dp,
    minWidth = 24.dp,
    horizontalPadding = 0.dp,
    iconEdgePadding = 0.dp,
    gap = 8.dp,
    shape = RoundedCornerShape(theme.radii.lg - 3.dp),
)

private fun iconSmall(theme: Theme, base: ButtonStyle): ButtonStyle = base.copy(
    minWidth = 32.dp,
    horizontalPadding = 0.dp,
    iconEdgePadding = 0.dp,
    gap = 8.dp,
    shape = RoundedCornerShape(theme.radii.lg),
)
