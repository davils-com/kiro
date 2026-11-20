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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.input.fieldColorsOf
import com.davils.kiro.ui.components.input.inputTextStyleOf
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme

private const val DISABLED_ALPHA: Float = 0.5f

internal fun defaultInputGroupStyle(theme: Theme): InputGroupStyle = InputGroupStyle(
    minHeight = 32.dp,
    shape = RoundedCornerShape(theme.radii.lg),
    colors = fieldColorsOf(theme),
    borderWidth = 1.dp,
    ringWidth = theme.focusRing.width,
    disabledAlpha = DISABLED_ALPHA,
    colorAnimation = theme.colorTransition(),
    control = InputGroupControlStyle(
        text = inputTextStyleOf(theme),
        horizontalPadding = 10.dp,
        verticalPadding = 4.dp,
        inlinePadding = 6.dp,
        blockPadding = 12.dp,
        textareaVerticalPadding = 8.dp,
        textareaMinHeight = 64.dp,
    ),
    addon = InputGroupAddonStyle(
        gap = 8.dp,
        inlinePadding = 8.dp,
        verticalPadding = 6.dp,
        blockHorizontalPadding = 10.dp,
        blockPadding = 8.dp,
        textStyle = theme.textStyleOf(size = theme.typography.scale.sm, weight = FontWeight.Medium),
        plainTextStyle = theme.textStyleOf(size = theme.typography.scale.sm),
        color = theme.colors.mutedForeground,
        iconSize = 16.dp,
        buttonOffset = 4.8.dp,
    ),
)
