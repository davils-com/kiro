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

package com.davils.kiro.ui.components.navigationmenu

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.menu.MenuController
import com.davils.kiro.ui.components.menu.MenuEntry
import com.davils.kiro.ui.components.menu.MenuNesting
import com.davils.kiro.ui.components.menu.MenuPanel
import com.davils.kiro.ui.components.menu.menuStyle
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.motion.sizeTransition
import com.davils.kiro.ui.components.primitive.motion.turnBy
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.OverlayPopup
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.hoverTracking
import com.davils.kiro.ui.components.primitive.overlay.panel
import com.davils.kiro.ui.components.primitive.roving.rovingMoveOf
import com.davils.kiro.ui.components.primitive.roving.rovingTarget
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle
import kotlinx.coroutines.delay

private const val HALF_TURN: Float = 180f

@Composable
public fun NavigationMenu(
    modifier: Modifier = Modifier,
    style: NavigationMenuStyle = navigationMenuStyle(),
    content: NavigationMenuScope.() -> Unit,
) {
    val entries = NavigationMenuScopeImpl().apply(content).entries
    var open by remember { mutableStateOf<Int?>(null) }
    var hovered by remember { mutableStateOf<Int?>(null) }
    var panelHovers by remember { mutableIntStateOf(0) }
    val onPanelHover = { isOver: Boolean -> panelHovers = (panelHovers + (1.takeIf { isOver } ?: -1)).coerceAtLeast(0) }
    var focused by remember { mutableIntStateOf(0) }
    val wanted = hovered?.takeIf { index -> entries[index].hasPanel } ?: open.takeIf { panelHovers > 0 }
    LaunchedEffect(wanted) {
        delay(style.openDelay.takeIf { wanted != null } ?: style.closeDelay)
        open = wanted
    }
    val requesters = remember(entries.size) { List(entries.size) { FocusRequester() } }
    val isReversed = LocalLayoutDirection.current == LayoutDirection.Rtl
    val keys = Modifier.onPreviewKeyEvent { event ->
        val target = targetOf(event = event, current = focused, count = entries.size, isReversed = isReversed)
        target?.let { index -> requesters[index].requestFocus() }
        val isClosing = event.type == KeyEventType.KeyDown && event.key == Key.Escape && open != null
        if (isClosing) open = null
        target != null || isClosing
    }
    Row(modifier = modifier.then(keys), verticalAlignment = Alignment.CenterVertically) {
        entries.forEachIndexed { index, entry ->
            Box {
                NavigationTrigger(
                    entry = entry,
                    isOpen = open == index,
                    focusRequester = requesters[index],
                    onFocused = { focused = index },
                    style = style,
                    onHover = { isOver -> hovered = index.takeIf { isOver } ?: hovered?.takeUnless { it == index } },
                    onClick = {
                        entry.onClick?.invoke()
                        open = index.takeIf { open != index && entry.hasPanel }
                    },
                )
                EntryPanel(
                    entry = entry,
                    isOpen = open == index,
                    style = style,
                    onHover = onPanelHover,
                    onClose = { if (open == index) open = null },
                )
            }
        }
    }
}

private fun targetOf(event: KeyEvent, current: Int, count: Int, isReversed: Boolean): Int? {
    if (event.type != KeyEventType.KeyDown) return null
    val move = rovingMoveOf(key = event.key, orientation = Orientation.Horizontal, isReversed = isReversed)
    return move?.let { step -> rovingTarget(move = step, current = current, isEnabled = List(count) { true }) }
}

@Composable
private fun EntryPanel(
    entry: NavigationEntry,
    isOpen: Boolean,
    style: NavigationMenuStyle,
    onHover: (Boolean) -> Unit,
    onClose: () -> Unit,
) {
    val menuPanel = entry.menu?.let { menu -> menuPanelOf(menu = menu, onHover = onHover, onClose = onClose) }
    val content = entry.content ?: menuPanel ?: return
    NavigationPanel(
        isOpen = isOpen,
        style = style,
        isFramed = entry.menu == null,
        onHover = onHover,
        onDismiss = onClose,
        content = content,
    )
}

private fun menuPanelOf(
    menu: List<MenuEntry>,
    onHover: (Boolean) -> Unit,
    onClose: () -> Unit,
): @Composable ColumnScope.() -> Unit = {
    val style = menuStyle()
    MenuPanel(
        entries = menu,
        controller = remember { MenuController() },
        minWidth = style.minWidth,
        panelStyle = style.panel,
        style = style,
        onCloseAll = onClose,
        isFocusRequested = false,
        nesting = MenuNesting(isSubFocusable = false, onHover = onHover),
    )
}

@Composable
private fun NavigationPanel(
    isOpen: Boolean,
    style: NavigationMenuStyle,
    isFramed: Boolean,
    onHover: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    OverlayPopup(
        isOpen = isOpen,
        placement = Placement(align = Align.Start, sideOffset = style.sideOffset),
        isFocusable = false,
        onDismiss = onDismiss,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides style.panel.content,
            LocalTextStyle provides style.linkTextStyle,
        ) {
            val frame = Modifier.panel(style.panel).takeIf { isFramed } ?: Modifier
            Column(modifier = Modifier.hoverTracking(onHover = onHover).then(frame), content = content)
        }
    }
}

@Composable
private fun NavigationTrigger(
    entry: NavigationEntry,
    isOpen: Boolean,
    focusRequester: FocusRequester,
    onFocused: () -> Unit,
    style: NavigationMenuStyle,
    onHover: (Boolean) -> Unit,
    onClick: () -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    LaunchedEffect(isHovered) { onHover(isHovered) }
    val turn = animateFloatAsState(
        targetValue = HALF_TURN.takeIf { isOpen } ?: 0f,
        animationSpec = theme.sizeTransition(),
    )
    val container = backgroundOf(isHovered = isHovered, isOpen = isOpen || entry.isActive, style = style)
    Row(
        modifier = Modifier
            .height(style.triggerHeight)
            .surface(shape = style.triggerShape, color = container)
            .focusRequester(focusRequester)
            .onFocusChanged { focus -> if (focus.isFocused) onFocused() }
            .hoverable(interactionSource = source)
            .then(pressable(source = source, onClick = onClick))
            .padding(horizontal = style.triggerHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(style.chevronGap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = entry.label, style = style.triggerTextStyle)
        if (entry.hasPanel) {
            CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.chevronSize)) {
                Icon(icon = style.chevron, contentDescription = null, modifier = Modifier.turnBy(turn))
            }
        }
    }
}

@Composable
public fun NavigationMenuLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    style: NavigationMenuStyle = navigationMenuStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val container = backgroundOf(isHovered = isHovered, isOpen = isActive, style = style)
    Column(
        modifier = modifier
            .surface(shape = style.linkShape, color = container)
            .hoverable(interactionSource = source)
            .then(pressable(source = source, onClick = onClick))
            .padding(style.linkPadding),
        verticalArrangement = Arrangement.spacedBy(style.linkGap),
        content = content,
    )
}

private fun backgroundOf(isHovered: Boolean, isOpen: Boolean, style: NavigationMenuStyle): Color {
    if (isHovered) return style.hover
    return style.open.takeIf { isOpen } ?: Color.Transparent
}

private fun pressable(source: MutableInteractionSource, onClick: () -> Unit): Modifier = Modifier
    .pressableCursor()
    .clickable(interactionSource = source, indication = null, role = Role.Button, onClick = onClick)
