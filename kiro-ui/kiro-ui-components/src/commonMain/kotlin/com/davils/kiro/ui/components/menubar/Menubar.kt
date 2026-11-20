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

package com.davils.kiro.ui.components.menubar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.components.menu.MenuController
import com.davils.kiro.ui.components.menu.MenuNesting
import com.davils.kiro.ui.components.menu.MenuPanel
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.OverlayPopup
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.focus.collectIsFocusVisibleAsState

@Composable
public fun Menubar(
    modifier: Modifier = Modifier,
    style: MenubarStyle = menubarStyle(),
    content: MenubarScope.() -> Unit,
) {
    val menus = MenubarScopeImpl().apply(content).menus
    var open by remember { mutableStateOf<Int?>(null) }
    var focused by remember { mutableStateOf<Int?>(null) }
    var pointed by remember { mutableStateOf<Int?>(null) }
    val controllers = remember(menus.size) { List(menus.size) { MenuController() } }
    val requesters = remember(menus.size) { List(menus.size) { FocusRequester() } }
    val show = { index: Int? ->
        index?.takeUnless { it == open }?.let { opening -> controllers[opening].reset() }
        open = index
    }
    val bar = MenubarKeys(
        menus = menus,
        controllers = controllers,
        isReversed = LocalLayoutDirection.current == LayoutDirection.Rtl,
        state = MenubarState(
            open = { open },
            focused = { focused },
            onOpen = show,
            onFocus = { index -> focused = index },
        ),
    )
    LaunchedEffect(focused) { focused?.let { index -> requesters[index].requestFocus() } }
    Row(
        modifier = modifier
            .height(style.height)
            .surface(shape = style.shape, color = Color.Transparent, border = style.border)
            .padding(style.padding)
            .onPreviewKeyEvent(bar::handle),
        horizontalArrangement = Arrangement.spacedBy(style.gap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        menus.forEachIndexed { index, menu ->
            Box {
                MenubarTrigger(
                    menu = menu,
                    isOpen = open == index,
                    focusRequester = requesters[index],
                    style = style,
                    onHover = { isOver ->
                        pointed = index.takeIf { isOver } ?: pointed?.takeUnless { it == index }
                        if (isOver && open != null && menu.isEnabled) show(index)
                    },
                    onClick = { show(index.takeUnless { open == index }) },
                )
                MenubarContent(
                    menu = menu,
                    controller = controllers[index],
                    isOpen = open == index,
                    style = style,
                    onDismiss = { if (open == index && pointed != index) show(null) },
                    onCloseAll = { show(null) },
                )
            }
        }
    }
}

@Composable
private fun MenubarContent(
    menu: MenubarMenu,
    controller: MenuController,
    isOpen: Boolean,
    style: MenubarStyle,
    onDismiss: () -> Unit,
    onCloseAll: () -> Unit,
) {
    val placement = Placement(
        align = Align.Start,
        sideOffset = style.sideOffset,
        alignOffset = style.alignOffset,
    )
    OverlayPopup(isOpen = isOpen, placement = placement, isFocusable = false, onDismiss = onDismiss) {
        MenuPanel(
            entries = menu.entries,
            controller = controller,
            minWidth = style.menuMinWidth,
            panelStyle = style.menu.panel,
            style = style.menu,
            onCloseAll = onCloseAll,
            isFocusRequested = false,
            nesting = MenuNesting(onOutside = onDismiss),
        )
    }
}

@Composable
private fun MenubarTrigger(
    menu: MenubarMenu,
    isOpen: Boolean,
    focusRequester: FocusRequester,
    style: MenubarStyle,
    onHover: (Boolean) -> Unit,
    onClick: () -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val isFocusVisible by source.collectIsFocusVisibleAsState()
    LaunchedEffect(isHovered) { onHover(isHovered) }
    val isActive = isOpen || isHovered || isFocusVisible
    val click = Modifier.pressableCursor(enabled = menu.isEnabled).clickable(
        interactionSource = source,
        indication = null,
        enabled = menu.isEnabled,
        role = Role.Button,
        onClick = onClick,
    )
    val container = style.triggerActive.takeIf { isActive } ?: Color.Transparent
    Text(
        text = menu.label,
        modifier = Modifier
            .alpha(disabledAlphaOf(isDisabled = !menu.isEnabled, alpha = style.menu.disabledAlpha))
            .surface(shape = style.triggerShape, color = container)
            .focusRequester(focusRequester)
            .hoverable(interactionSource = source, enabled = menu.isEnabled)
            .then(click)
            .padding(horizontal = style.triggerHorizontalPadding, vertical = style.triggerVerticalPadding),
        style = style.triggerTextStyle,
    )
}
