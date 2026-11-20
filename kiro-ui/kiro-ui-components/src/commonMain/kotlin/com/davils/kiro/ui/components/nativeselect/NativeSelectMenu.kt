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

package com.davils.kiro.ui.components.nativeselect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.AnchorPositionProvider
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.primitive.surface.boxShadow
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private val MENU_PADDING = 5.dp

private val confirmKeys: Set<Key> = setOf(Key.Enter, Key.NumPadEnter, Key.Spacebar)

@Composable
internal fun <T> NativeSelectMenu(
    entries: List<NativeSelectEntry<T>>,
    selected: T,
    minWidth: Dp,
    style: NativeSelectMenuStyle,
    onSelect: (T) -> Unit,
    onDismiss: () -> Unit,
) {
    val offset = with(LocalDensity.current) { style.offset.roundToPx() }
    val provider = rememberMenuPosition(offset)
    val options = entries.filterIsInstance<NativeSelectEntry.Option<T>>()
    var highlighted by remember { mutableIntStateOf(indexOf(options = options, value = selected)) }
    val focus = remember { FocusRequester() }
    LaunchedEffect(focus) { focus.requestFocus() }
    val keys = Modifier.onKeyEvent { event ->
        val isDown = event.type == KeyEventType.KeyDown
        val step = arrowSteps[event.key]?.takeIf { isDown }
        val next = step?.let { delta -> stepFrom(options = options, index = highlighted, delta = delta) }
        next?.let { index -> highlighted = index }
        val chosen = options.getOrNull(highlighted)?.takeIf { isDown && event.key in confirmKeys }
        chosen?.let { option -> onSelect(option.value) }
        step != null || chosen != null
    }
    Popup(
        popupPositionProvider = provider,
        onDismissRequest = onDismiss,
        properties = PopupProperties(focusable = true),
    ) {
        Column(
            modifier = Modifier
                .widthIn(min = minWidth)
                .width(IntrinsicSize.Max)
                .boxShadow(shape = style.shape, shadow = style.shadow)
                .surface(shape = style.shape, color = style.container, ring = style.ring)
                .focusRequester(focus)
                .focusable()
                .then(keys)
                .padding(style.padding),
        ) {
            CompositionLocalProvider(LocalContentColor provides style.content) {
                entries.forEach { entry ->
                    when (entry) {
                        is NativeSelectEntry.Group -> {
                            Text(
                                text = entry.label,
                                modifier = Modifier.padding(style.groupPadding),
                                style = style.groupTextStyle,
                                color = style.groupColor,
                            )
                        }

                        is NativeSelectEntry.Option -> {
                            val index = options.indexOf(entry)
                            MenuOption(
                                option = entry,
                                isSelected = entry.value == selected,
                                isHighlighted = index == highlighted,
                                style = style,
                                onHover = { highlighted = index },
                                onSelect = { onSelect(entry.value) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuOption(
    option: NativeSelectEntry.Option<*>,
    isSelected: Boolean,
    isHighlighted: Boolean,
    style: NativeSelectMenuStyle,
    onHover: () -> Unit,
    onSelect: () -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    LaunchedEffect(isHovered) { if (isHovered) onHover() }
    val content = contentOf(isHighlighted = isHighlighted, style = style)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(disabledAlphaOf(isDisabled = !option.isEnabled, alpha = style.disabledAlpha))
            .surface(shape = style.itemShape, color = highlightOf(isHighlighted = isHighlighted, style = style))
            .hoverable(interactionSource = source, enabled = option.isEnabled)
            .pressableCursor(enabled = option.isEnabled)
            .clickable(
                interactionSource = source,
                indication = null,
                enabled = option.isEnabled,
                role = Role.DropdownList,
                onClick = onSelect,
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = option.label,
            modifier = Modifier.padding(style.itemPadding),
            style = style.itemTextStyle,
            color = content,
            maxLines = 1,
        )
        if (isSelected) {
            CompositionLocalProvider(
                LocalIconStyle provides IconStyle(
                    size = style.check.size,
                    color = style.check.color.takeOrElse { content },
                ),
            ) {
                Icon(
                    icon = style.check.icon,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd).padding(end = style.check.endPadding),
                )
            }
        }
    }
}

@Composable
private fun rememberMenuPosition(offset: Int): AnchorPositionProvider {
    val padding = with(LocalDensity.current) { MENU_PADDING.roundToPx() }
    return remember(key1 = offset, key2 = padding) {
        AnchorPositionProvider(
            side = Side.Bottom,
            align = Align.Center,
            sideOffset = offset,
            alignOffset = 0,
            padding = padding,
        )
    }
}

private fun highlightOf(isHighlighted: Boolean, style: NativeSelectMenuStyle): Color {
    if (isHighlighted) return style.highlight
    return Color.Transparent
}

private fun contentOf(isHighlighted: Boolean, style: NativeSelectMenuStyle): Color {
    if (isHighlighted) return style.highlightContent
    return style.content
}
