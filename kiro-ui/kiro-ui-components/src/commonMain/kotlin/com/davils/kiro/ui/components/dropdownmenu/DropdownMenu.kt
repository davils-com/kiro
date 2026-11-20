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

package com.davils.kiro.ui.components.dropdownmenu

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import com.davils.kiro.ui.components.menu.MenuController
import com.davils.kiro.ui.components.menu.MenuPanel
import com.davils.kiro.ui.components.menu.MenuScope
import com.davils.kiro.ui.components.menu.MenuScopeImpl
import com.davils.kiro.ui.components.menu.MenuStyle
import com.davils.kiro.ui.components.menu.menuStyle
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.OverlayPopup
import com.davils.kiro.ui.components.primitive.overlay.Placement

/**
 * A menu that opens in a popup next to its trigger and lists the entries that [content] adds.
 *
 * The menu is at least as wide as the trigger and as [MenuStyle.minWidth], and it takes the focus when it opens.
 * Choosing an item, pressing escape or clicking outside the menu asks to close it through [onExpandedChange].
 *
 * @param expanded whether the menu is open.
 * @param onExpandedChange called with `false` when the menu asks to close.
 * @param trigger the content the menu opens next to, such as a button that sets [expanded].
 * @param modifier the modifier of the box around the trigger.
 * @param placement where the menu opens relative to the trigger.
 * @param style the style of the menu.
 * @param content adds the entries of the menu.
 * @since 1.0.0
 */
@Composable
public fun DropdownMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    trigger: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    placement: Placement = Placement(align = Align.Start),
    style: MenuStyle = menuStyle(),
    content: MenuScope.() -> Unit,
) {
    val entries = MenuScopeImpl().apply(content).entries
    var anchorWidth by remember { mutableIntStateOf(0) }
    val anchor = with(LocalDensity.current) { anchorWidth.toDp() }
    Box(modifier = modifier.onSizeChanged { size -> anchorWidth = size.width }) {
        trigger()
        OverlayPopup(
            isOpen = expanded,
            placement = placement,
            isFocusable = true,
            onDismiss = { onExpandedChange(false) },
        ) {
            MenuPanel(
                entries = entries,
                controller = remember { MenuController() },
                minWidth = maxOf(a = style.minWidth, b = anchor),
                panelStyle = style.panel,
                style = style,
                onCloseAll = { onExpandedChange(false) },
                isFocusRequested = true,
            )
        }
    }
}
