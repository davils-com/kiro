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

package com.davils.kiro.ui.window.controls

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.window.titlebar.TitleBarStyle
import com.davils.kiro.ui.window.titlebar.WindowActions

internal const val CONTROL_TAG_PREFIX: String = "window-"

@Composable
internal fun ControlButton(
    button: WindowButton,
    actions: WindowActions,
    style: TitleBarStyle,
    shape: Shape,
    glyphSize: Dp,
    strokeWidth: Dp,
    restoreShift: Float,
    modifier: Modifier = Modifier,
) {
    val source = remember { MutableInteractionSource() }
    val state = source.collectInteractionState()
    val colors = colorsOf(button = button, style = style).animate(state = state, animation = style.colorAnimation)
    val label = actions.labelOf(button = button, labels = style.labels)
    Box(
        modifier = modifier.then(
            Modifier
                .surface(shape = shape, color = colors.container)
                .hoverable(interactionSource = source)
                .clickable(
                    interactionSource = source,
                    indication = null,
                    role = Role.Button,
                ) { actions.perform(button) }
                .semantics { contentDescription = label }
                .testTag(CONTROL_TAG_PREFIX + button.name),
        ),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.size(glyphSize)) {
            drawGlyph(
                button = button,
                isMaximized = actions.isMaximized,
                color = colors.content,
                width = strokeWidth.toPx(),
                shift = restoreShift,
            )
        }
    }
}

private fun colorsOf(button: WindowButton, style: TitleBarStyle): StateColors {
    if (button == WindowButton.Close) return style.closeColors
    return style.controlColors
}
