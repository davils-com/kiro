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

package com.davils.kiro.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconGraphic

@Composable
internal fun Modifier.initialFocus(): Modifier {
    val focus = remember { FocusRequester() }
    LaunchedEffect(focus) { focus.requestFocus() }
    return focusRequester(focus).focusable()
}

@Composable
internal fun BoxScope.CloseButton(icon: IconGraphic, label: String, offset: Dp, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(offset)
            .semantics { contentDescription = label },
        variant = ButtonVariant.Ghost,
        size = ButtonSize.IconSm,
    ) {
        Icon(icon = icon, placement = IconPlacement.Inline)
    }
}

@Composable
internal fun BleedingFooter(style: DialogStyle, modifier: Modifier, content: @Composable RowScope.() -> Unit) {
    val padding = style.panel.padding
    val shape = RoundedCornerShape(bottomStart = style.radius, bottomEnd = style.radius)
    Box(modifier = modifier.bleed(padding)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = style.footerContainer, shape = shape)
                .topLine(style.footerBorder)
                .padding(padding),
            horizontalArrangement = Arrangement.spacedBy(style.footerGap, Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            content = content,
        )
    }
}

private fun Modifier.topLine(border: Border): Modifier = drawBehind {
    val stroke = border.width.toPx()
    drawLine(
        color = border.color,
        start = Offset(0f, stroke / 2),
        end = Offset(size.width, stroke / 2),
        strokeWidth = stroke,
    )
}

private fun Modifier.bleed(amount: Dp): Modifier = layout { measurable, constraints ->
    val extra = amount.roundToPx()
    val wide = constraints.maxWidth + extra * 2
    val placeable = measurable.measure(Constraints(minWidth = wide, maxWidth = wide))
    layout(constraints.maxWidth, maxOf(placeable.height - extra, 0)) { placeable.place(-extra, 0) }
}
