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

package com.davils.kiro.ui.components.primitive.overlay

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.surface.boxShadow
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border

private const val RING_ALPHA: Float = 0.1f

/**
 * Draws the panel of an overlay behind the content: the shadow, the fill and the ring of [style], and pads the content
 * by its padding.
 *
 * The panel does not set the content color; the component that draws it provides [PanelStyle.content].
 *
 * @param style the style of the panel.
 * @return this modifier with the panel added.
 * @since 1.0.0
 */
public fun Modifier.panel(style: PanelStyle): Modifier {
    val shadowed = boxShadow(shape = style.shape, shadow = style.shadow)
    val filled = shadowed.surface(shape = style.shape, color = style.container, ring = style.ring)
    return filled.padding(style.padding)
}

internal fun Theme.popoverPanel(padding: Dp = 10.dp, radius: Dp = radii.lg): PanelStyle = PanelStyle(
    container = colors.popover,
    content = colors.popoverForeground,
    shape = RoundedCornerShape(radius),
    ring = Border(width = 1.dp, color = colors.foreground.copy(alpha = RING_ALPHA)),
    shadow = shadows.md,
    padding = padding,
)
