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

package com.davils.kiro.ui.components.combobox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.command.search.SearchField
import com.davils.kiro.ui.components.command.search.SearchList
import com.davils.kiro.ui.components.command.search.SearchLook
import com.davils.kiro.ui.components.command.search.SearchSection
import com.davils.kiro.ui.components.command.search.ranked
import com.davils.kiro.ui.components.menu.MenuController
import com.davils.kiro.ui.components.menu.MenuEntry
import com.davils.kiro.ui.components.menu.MenuKeys
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.OverlayPopup
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.panel
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private val openKeys: Set<Key> = setOf(Key.DirectionDown, Key.DirectionUp, Key.Enter, Key.Spacebar)

@Composable
public fun <T> Combobox(
    items: List<T>,
    selected: T?,
    onSelectedChange: (T) -> Unit,
    label: (T) -> String,
    modifier: Modifier = Modifier,
    placeholder: String = Messages.Combobox.placeholder,
    emptyText: String = Messages.Combobox.empty,
    style: ComboboxStyle = comboboxStyle(),
) {
    var isOpen by remember { mutableStateOf(false) }
    var anchorWidth by remember { mutableIntStateOf(0) }
    val keys = Modifier.onKeyEvent { event ->
        val isOpening = !isOpen && event.type == KeyEventType.KeyDown && event.key in openKeys
        if (isOpening) isOpen = true
        isOpening
    }
    Box(modifier = modifier.onSizeChanged { size -> anchorWidth = size.width }) {
        ComboboxTrigger(
            text = selected?.let(label),
            placeholder = placeholder,
            isOpen = isOpen,
            style = style,
            modifier = keys,
        ) { isOpen = !isOpen }
        OverlayPopup(
            isOpen = isOpen,
            placement = Placement(align = Align.Start, sideOffset = style.sideOffset),
            isFocusable = true,
            onDismiss = { isOpen = false },
        ) {
            ComboboxPanel(
                items = items,
                selected = selected,
                label = label,
                width = with(LocalDensity.current) { anchorWidth.toDp() },
                texts = ComboboxTexts(search = style.searchPlaceholder, empty = emptyText),
                style = style,
                onPick = onSelectedChange,
            ) { isOpen = false }
        }
    }
}

@Composable
private fun ComboboxTrigger(
    text: String?,
    placeholder: String,
    isOpen: Boolean,
    style: ComboboxStyle,
    modifier: Modifier,
    onToggle: () -> Unit,
) {
    val trigger = style.trigger
    val source = remember { MutableInteractionSource() }
    val state = source.collectInteractionState(isDisabled = false, isExpanded = isOpen)
    val colors = trigger.colors.animate(state = state, animation = trigger.colorAnimation)
    val face = Modifier.surface(
        shape = trigger.shape,
        color = colors.container,
        border = Border(width = trigger.borderWidth, color = colors.border),
        ring = Border(width = trigger.ringWidth, color = colors.ring),
    )
    val press = Modifier
        .pressableCursor()
        .clickable(interactionSource = source, indication = null, role = Role.DropdownList, onClick = onToggle)
    Row(
        modifier = Modifier
            .widthIn(min = style.minWidth)
            .height(trigger.height)
            .then(face)
            .hoverable(interactionSource = source)
            .then(press)
            .then(modifier)
            .padding(start = trigger.startPadding, end = trigger.endPadding),
        horizontalArrangement = Arrangement.spacedBy(trigger.gap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TriggerValue(text = text, placeholder = placeholder, content = colors.content, style = style)
    }
}

@Composable
private fun RowScope.TriggerValue(text: String?, placeholder: String, content: Color, style: ComboboxStyle) {
    val trigger = style.trigger
    Text(
        text = text ?: placeholder,
        modifier = Modifier.weight(1f),
        style = trigger.textStyle,
        color = trigger.placeholderColor.takeIf { text == null } ?: content,
        maxLines = 1,
    )
    CompositionLocalProvider(
        LocalIconStyle provides IconStyle(size = trigger.iconSize, color = trigger.iconColor),
    ) {
        Icon(icon = style.triggerIcon, contentDescription = null)
    }
}

@Composable
private fun <T> ComboboxPanel(
    items: List<T>,
    selected: T?,
    label: (T) -> String,
    width: Dp,
    texts: ComboboxTexts,
    style: ComboboxStyle,
    onPick: (T) -> Unit,
    onClose: () -> Unit,
) {
    var query by remember { mutableStateOf("") }
    val focus = remember { FocusRequester() }
    val controller = remember { MenuController() }
    val shown = ranked(items = items, query = query) { item -> listOf(label(item)) }
    val entries = shown.map { item ->
        optionOf(
            item = item,
            isSelected = item == selected,
            label = label,
        ) { onPick(item) }
    }
    LaunchedEffect(Unit) { focus.requestFocus() }
    LaunchedEffect(
        query,
    ) { controller.highlighted = initialHighlight(shown = shown, selected = selected, query = query) }
    val keys = MenuKeys(
        entries = entries,
        controller = controller,
        isReversed = false,
        onCloseAll = onClose,
        onCloseSelf = null,
        isTextInput = true,
    )
    CompositionLocalProvider(LocalContentColor provides style.menu.panel.content) {
        Column(
            modifier = Modifier
                .widthIn(min = maxOf(width, style.minWidth))
                .width(IntrinsicSize.Max)
                .panel(style.menu.panel)
                .onPreviewKeyEvent(keys::handle),
            verticalArrangement = Arrangement.spacedBy(style.gap),
        ) {
            SearchField(
                query = query,
                onQueryChange = { typed -> query = typed },
                placeholder = texts.search,
                focus = focus,
                style = style.search,
                icon = style.searchIcon,
                iconAlpha = style.searchIconAlpha,
            )
            SearchList(
                sections = listOf(SearchSection.Group(heading = null, entries = entries)),
                controller = controller,
                emptyText = texts.empty,
                look = lookOf(style),
                onCloseAll = onClose,
            )
        }
    }
}

private fun <T> initialHighlight(shown: List<T>, selected: T?, query: String): Int? {
    if (shown.isEmpty()) return null
    val current = shown.indexOf(selected).takeIf { it >= 0 && query.isBlank() }
    return current ?: 0
}

private fun <T> optionOf(
    item: T,
    isSelected: Boolean,
    label: (T) -> String,
    onPick: () -> Unit,
): MenuEntry.Interactive = MenuEntry.Radio(
    label = label(item),
    isSelected = isSelected,
    onSelect = onPick,
    isEnabled = true,
    isInset = false,
    isClosing = true,
)

private fun lookOf(style: ComboboxStyle): SearchLook = SearchLook(
    item = style.menu,
    listMaxHeight = style.listMaxHeight,
    groupPadding = 0.dp,
    headingStyle = style.menu.labelStyle,
    headingColor = style.menu.labelColor,
    headingHorizontalPadding = style.menu.itemHorizontalPadding,
    headingVerticalPadding = style.menu.itemVerticalPadding,
    emptyStyle = style.emptyTextStyle,
    emptyColor = style.emptyColor,
    emptyPadding = style.emptyPadding,
    separatorColor = style.menu.separatorColor,
)
