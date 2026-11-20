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

package com.davils.kiro.ui.components.accordion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.roving.rovingMoveOf
import com.davils.kiro.ui.components.primitive.roving.rovingTarget
import com.davils.kiro.ui.components.primitive.selection.toggledIn
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.focus.collectIsFocusVisibleAsState
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
public fun <T> Accordion(
    expanded: Set<T>,
    onExpandedChange: (Set<T>) -> Unit,
    modifier: Modifier = Modifier,
    isMultiple: Boolean = false,
    style: AccordionStyle = accordionStyle(),
    content: AccordionScope<T>.() -> Unit,
) {
    val entries = AccordionScopeImpl<T>().apply(content).entries
    val requesters = remember(entries.size) { List(entries.size) { FocusRequester() } }
    val enabled = entries.map { entry -> entry.isEnabled }
    Column(modifier = modifier.fillMaxWidth()) {
        entries.forEachIndexed { index, entry ->
            AccordionItem(
                entry = entry,
                isExpanded = entry.value in expanded,
                isLast = index == entries.lastIndex,
                focusRequester = requesters[index],
                onToggle = {
                    onExpandedChange(
                        toggledIn(selected = expanded, value = entry.value, isMultiple = isMultiple),
                    )
                },
                onMove = { key ->
                    val move = rovingMoveOf(key = key, orientation = Orientation.Vertical, isReversed = false)
                    val target = move?.let { step -> rovingTarget(move = step, current = index, isEnabled = enabled) }
                    target?.let { focused -> requesters[focused].requestFocus() }
                    target != null
                },
                style = style,
            )
        }
    }
}

@Composable
private fun <T> AccordionItem(
    entry: AccordionEntry<T>,
    isExpanded: Boolean,
    isLast: Boolean,
    focusRequester: FocusRequester,
    onToggle: () -> Unit,
    onMove: (Key) -> Boolean,
    style: AccordionStyle,
) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val isFocusVisible by source.collectIsFocusVisibleAsState()
    val border = animateColor(focusColorOf(isFocusVisible, style.focusBorder), style.colorAnimation)
    val ring = animateColor(focusColorOf(isFocusVisible, style.focusRing), style.colorAnimation)
    val keys = Modifier.onKeyEvent { event -> event.type == KeyEventType.KeyDown && onMove(event.key) }
    val face = Modifier.surface(
        shape = style.triggerShape,
        color = Color.Transparent,
        border = Border(width = style.focusBorder.width, color = border.value),
        ring = Border(width = style.focusRing.width, color = ring.value),
    )
    val click = Modifier.pressableCursor(enabled = entry.isEnabled).clickable(
        interactionSource = source,
        indication = null,
        enabled = entry.isEnabled,
        role = Role.Button,
        onClick = onToggle,
    )
    Column(modifier = Modifier.fillMaxWidth().dividedBelow(isShown = !isLast, border = style.divider)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(disabledAlphaOf(isDisabled = !entry.isEnabled, alpha = style.disabledAlpha))
                .then(face)
                .focusRequester(focusRequester)
                .then(keys)
                .hoverable(interactionSource = source, enabled = entry.isEnabled)
                .then(click)
                .padding(vertical = style.triggerPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            CompositionLocalProvider(LocalTextStyle provides triggerTextOf(isHovered = isHovered, style = style)) {
                Row(modifier = Modifier.weight(1f), content = entry.trigger)
            }
            CompositionLocalProvider(
                LocalIconStyle provides IconStyle(size = style.iconSize, color = style.iconColor),
            ) {
                Icon(icon = iconOf(isExpanded = isExpanded, style = style), contentDescription = null)
            }
        }
        AnimatedVisibility(visible = isExpanded, enter = style.motion.enter, exit = style.motion.exit) {
            CompositionLocalProvider(LocalTextStyle provides style.contentTextStyle) {
                Column(modifier = Modifier.padding(bottom = style.contentPadding), content = entry.content)
            }
        }
    }
}

private fun Modifier.dividedBelow(isShown: Boolean, border: Border): Modifier {
    if (!isShown) return this
    return drawBehind {
        val y = size.height - border.width.toPx() / 2
        drawLine(
            color = border.color,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = border.width.toPx(),
        )
    }
}

private fun focusColorOf(isFocusVisible: Boolean, border: Border): Color {
    if (isFocusVisible) return border.color
    return Color.Transparent
}

private fun triggerTextOf(isHovered: Boolean, style: AccordionStyle): TextStyle {
    val isUnderlined = isHovered && style.isUnderlinedOnHover
    if (!isUnderlined) return style.triggerTextStyle
    return style.triggerTextStyle.copy(textDecoration = TextDecoration.Underline)
}

private fun iconOf(isExpanded: Boolean, style: AccordionStyle) =
    style.collapseIcon.takeIf { isExpanded } ?: style.expandIcon
