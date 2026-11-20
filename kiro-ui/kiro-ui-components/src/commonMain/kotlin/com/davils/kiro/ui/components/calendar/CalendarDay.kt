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

package com.davils.kiro.ui.components.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.style.Border

@Composable
internal fun CalendarDay(
    cell: DayCell,
    label: String,
    onSelect: () -> Unit,
    onFocused: () -> Unit,
    onKey: (Key) -> Boolean,
    style: CalendarStyle,
) {
    val source = remember { MutableInteractionSource() }
    val requester = remember { FocusRequester() }
    LaunchedEffect(cell.isFocusRequested) { if (cell.isFocusRequested) requester.requestFocus() }
    val state = source.collectInteractionState(isDisabled = cell.isDisabled)
    val colors = dayColorsOf(cell = cell, style = style.day).animate(state = state, animation = style.colorAnimation)
    val span = spanShapeOf(cell = cell, radius = style.cellRadius)
    val background = span?.let { shape ->
        Modifier.surface(
            shape = shape,
            color = style.day.rangeContainer,
        )
    } ?: Modifier
    val keys = Modifier.onKeyEvent { event -> event.type == KeyEventType.KeyDown && onKey(event.key) }
    val described = Modifier.semantics {
        contentDescription = label
        selected = cell.role != DayRole.None
    }
    val face = Modifier.surface(
        shape = dayShapeOf(cell = cell, radius = style.cellRadius),
        color = colors.container,
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    Box(modifier = Modifier.size(style.cellSize).then(background), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(style.cellSize)
                .alpha(disabledAlphaOf(isDisabled = cell.isDisabled, alpha = style.day.disabledAlpha))
                .then(face)
                .then(described)
                .focusRequester(requester)
                .onFocusChanged { focus -> if (focus.isFocused) onFocused() }
                .then(keys)
                .focusProperties { canFocus = cell.isTabbable }
                .hoverable(interactionSource = source, enabled = !cell.isDisabled)
                .pressableCursor(enabled = !cell.isDisabled)
                .clickable(
                    interactionSource = source,
                    indication = null,
                    enabled = !cell.isDisabled,
                    role = Role.Button,
                    onClick = onSelect,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = cell.date.day.toString(), style = style.day.textStyle, color = colors.content)
        }
    }
}
