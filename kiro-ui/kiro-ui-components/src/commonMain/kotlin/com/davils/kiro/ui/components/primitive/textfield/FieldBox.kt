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

package com.davils.kiro.ui.components.primitive.textfield

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.davils.kiro.ui.components.input.InputOptions
import com.davils.kiro.ui.components.input.InputStyle
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.style.Border

@Composable
internal fun FieldBox(
    text: FieldText,
    modifier: Modifier,
    enabled: Boolean,
    isInvalid: Boolean,
    options: InputOptions,
    isSingleLine: Boolean,
    style: InputStyle,
) {
    val source = remember { MutableInteractionSource() }
    val state = source.collectFieldState(isDisabled = !enabled, isInvalid = isInvalid)
    val colors = style.colors.animate(state = state, animation = style.colorAnimation)
    val alpha = disabledAlphaOf(isDisabled = !enabled, alpha = style.disabledAlpha)
    val surface = Modifier.surface(
        shape = style.shape,
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    CompositionLocalProvider(LocalContentColor provides colors.content) {
        TextFieldCore(
            text = text,
            modifier = modifier
                .graphicsLayer { this.alpha = alpha }
                .fillMaxWidth()
                .heightIn(min = style.minHeight, max = style.maxHeight)
                .then(surface)
                .hoverable(interactionSource = source, enabled = enabled),
            enabled = enabled,
            options = options,
            isSingleLine = isSingleLine,
            padding = style.padding,
            style = style.text,
            interactionSource = source,
        )
    }
}

internal fun disabledAlphaOf(isDisabled: Boolean, alpha: Float): Float {
    if (!isDisabled) return 1f
    return alpha
}
