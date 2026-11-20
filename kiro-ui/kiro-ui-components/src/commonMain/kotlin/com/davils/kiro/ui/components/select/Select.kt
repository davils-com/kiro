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

package com.davils.kiro.ui.components.select

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import com.davils.kiro.ui.components.menu.MenuController
import com.davils.kiro.ui.components.menu.MenuEntry
import com.davils.kiro.ui.components.menu.MenuPanel
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.OverlayPopup
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private val openKeys: Set<Key> = setOf(Key.DirectionDown, Key.DirectionUp)

/**
 * A field that shows the label of the selected option and opens a menu of options to choose from.
 *
 * A click on the field, or the Up or Down arrow key, opens the menu, which is at least as wide as the field; choosing
 * an option calls [onSelectedChange] and closes the menu. Without a selected option the field shows the placeholder
 * declared in [content].
 *
 * @param T the type of the values of the options.
 * @param selected the value of the selected option, or `null` if no option is selected.
 * @param onSelectedChange called with the value of the option that is chosen.
 * @param modifier the modifier of the field.
 * @param size the size of the field, which chooses its default style.
 * @param enabled whether the field can be opened and focused.
 * @param isInvalid whether the field shows the invalid state.
 * @param style the style of the select.
 * @param content declares the placeholder, options, groups and separators through [SelectScope].
 * @since 1.0.0
 */
@Composable
public fun <T> Select(
    selected: T?,
    onSelectedChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    size: SelectSize = SelectSize.Default,
    enabled: Boolean = true,
    isInvalid: Boolean = false,
    style: SelectStyle = selectStyle(size),
    content: SelectScope<T>.() -> Unit,
) {
    val scope = SelectScopeImpl(selected = selected, onSelect = onSelectedChange).apply(content)
    var isOpen by remember { mutableStateOf(false) }
    var anchorWidth by remember { mutableIntStateOf(0) }
    val source = remember { MutableInteractionSource() }
    val state = source.collectInteractionState(isDisabled = !enabled, isExpanded = isOpen, isInvalid = isInvalid)
    val colors = style.colors.animate(state = state, animation = style.colorAnimation)
    val face = Modifier.surface(
        shape = style.shape,
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    val open = Modifier.pressableCursor(enabled = enabled).clickable(
        interactionSource = source,
        indication = null,
        enabled = enabled,
        role = Role.DropdownList,
    ) {
        isOpen = true
    }
    val keys = Modifier.onKeyEvent { event ->
        val isOpening = event.type == KeyEventType.KeyDown && event.key in openKeys && enabled
        if (isOpening) isOpen = true
        isOpening
    }
    Box(modifier = modifier.onSizeChanged { measured -> anchorWidth = measured.width }) {
        Row(
            modifier = Modifier
                .alpha(disabledAlphaOf(isDisabled = !enabled, alpha = style.disabledAlpha))
                .fillMaxWidth()
                .height(style.height)
                .then(face)
                .hoverable(interactionSource = source, enabled = enabled)
                .then(open)
                .then(keys)
                .padding(start = style.startPadding, end = style.endPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SelectValue(
                label = scope.selectedLabel,
                placeholder = scope.placeholder,
                content = colors.content,
                style = style,
            )
        }
        SelectMenu(
            entries = scope.entries,
            isOpen = isOpen,
            anchorWidth = anchorWidth,
            style = style,
        ) { isOpen = false }
    }
}

@Composable
private fun SelectValue(label: String?, placeholder: String, content: Color, style: SelectStyle) {
    Text(
        text = label ?: placeholder,
        modifier = Modifier.padding(end = style.gap),
        style = style.textStyle,
        color = style.placeholderColor.takeIf { label == null } ?: content,
        maxLines = 1,
    )
    CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.iconSize, color = style.iconColor)) {
        Icon(icon = style.icon, contentDescription = null)
    }
}

@Composable
private fun SelectMenu(
    entries: List<MenuEntry>,
    isOpen: Boolean,
    anchorWidth: Int,
    style: SelectStyle,
    onClose: () -> Unit,
) {
    val anchor = with(LocalDensity.current) { anchorWidth.toDp() }
    OverlayPopup(
        isOpen = isOpen,
        placement = Placement(align = Align.Center),
        isFocusable = true,
        onDismiss = onClose,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides style.textStyle,
            LocalContentColor provides style.menu.panel.content,
        ) {
            MenuPanel(
                entries = entries,
                controller = remember { MenuController().apply { highlighted = selectedIndexOf(entries) } },
                minWidth = maxOf(a = style.minWidth, b = anchor),
                panelStyle = style.menu.panel,
                style = style.menu,
                onCloseAll = onClose,
                isFocusRequested = true,
            )
        }
    }
}

private fun selectedIndexOf(entries: List<MenuEntry>): Int? =
    entries.indexOfFirst { entry -> (entry as? MenuEntry.Radio)?.isSelected == true }.takeIf { index -> index >= 0 }
