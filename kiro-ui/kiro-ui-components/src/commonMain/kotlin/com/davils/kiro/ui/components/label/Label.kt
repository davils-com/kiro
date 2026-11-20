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

package com.davils.kiro.ui.components.label

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import com.davils.kiro.ui.components.primitive.layout.lineBox
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme

private const val DISABLED_ALPHA: Float = 0.5f

@Composable
public fun Label(
    text: String,
    modifier: Modifier = Modifier,
    isDisabled: Boolean = false,
    style: LabelStyle = labelStyle(),
) {
    val alpha = alphaOf(isDisabled = isDisabled, style = style)
    Text(
        text = text,
        modifier = modifier.alpha(alpha).lineBox(style.textStyle),
        style = style.textStyle,
        maxLines = 1,
    )
}

internal fun defaultLabelStyle(theme: Theme): LabelStyle = LabelStyle(
    textStyle = theme.textStyleOf(
        size = theme.typography.scale.sm,
        weight = FontWeight.Medium,
        lineHeight = theme.typography.leading.none,
    ),
    disabledAlpha = DISABLED_ALPHA,
)

private fun alphaOf(isDisabled: Boolean, style: LabelStyle): Float {
    if (!isDisabled) return 1f
    return style.disabledAlpha
}
