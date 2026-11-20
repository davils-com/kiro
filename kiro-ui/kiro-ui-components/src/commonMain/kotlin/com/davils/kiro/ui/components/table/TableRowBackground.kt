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

package com.davils.kiro.ui.components.table

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor

@Composable
internal fun TableRowBackground(index: Int, row: TableRowSpec, style: TableStyle) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val color = animateColor(colorOf(row = row, isHovered = isHovered, style = style), style.colorAnimation)
    val click = row.onClick?.let { onClick ->
        Modifier.pressableCursor().clickable(
            interactionSource = source,
            indication = null,
            role = Role.Button,
            onClick = onClick,
        )
    } ?: Modifier
    Box(
        modifier = Modifier
            .layoutId(TableSlot.Background(index))
            .drawBehind { drawRow(fill = color.value, row = row, style = style) }
            .semantics { selected = row.isSelected }
            .hoverable(interactionSource = source)
            .then(click),
    )
}

private fun DrawScope.drawRow(fill: Color, row: TableRowSpec, style: TableStyle) {
    drawRect(color = fill)
    val stroke = style.border.width.toPx()
    if (row.hasTopBorder) {
        drawLine(
            color = style.border.color,
            start = Offset(x = 0f, y = stroke / 2),
            end = Offset(x = size.width, y = stroke / 2),
            strokeWidth = stroke,
        )
    }
    if (row.hasBottomBorder) {
        val y = size.height - stroke / 2
        drawLine(
            color = style.border.color,
            start = Offset(x = 0f, y = y),
            end = Offset(x = size.width, y = y),
            strokeWidth = stroke,
        )
    }
}

private fun colorOf(row: TableRowSpec, isHovered: Boolean, style: TableStyle): Color {
    if (row.isSelected) return style.selectedColor
    return style.hoverColor.takeIf { isHovered } ?: idleOf(row = row, style = style)
}

private fun idleOf(row: TableRowSpec, style: TableStyle): Color {
    if (row.kind == TableRowKind.Foot) return style.footerColor
    return Color.Transparent
}
